/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.entity.ResultMetricsTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTblEntity;
import jp.ac.asojuku.asolearning.entity.ResultTestcaseTblEntity;
import jp.ac.asojuku.asolearning.entity.TaskTblEntity;
import jp.ac.asojuku.asolearning.entity.TestcaseTableEntity;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.exception.IllegalJudgeFileException;
import jp.ac.asojuku.asolearning.jaxb.model.CCCC_ProjectElement;
import jp.ac.asojuku.asolearning.jaxb.model.MemberFunction;
import jp.ac.asojuku.asolearning.json.JudgeResultJson;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.XmlReader;

/**
 * javaのプログラムの判定を行うクラス
 * @author nishino
 *
 */
public class JavaProgramJudge implements Judge {

	Logger logger = LoggerFactory.getLogger(JavaProgramJudge.class);
	private final String CHECK_EXT = "java";
	private final String RESULT = "/result";
	private final String CLASSES = "/classes";
	private final String CCCC = "/cccc";
	private final String ERROR_FILENAME = "error.txt";
	private final String RESULT_FILENAME = "result.txt";
	private final int MAX_SCORE = 10;
	private final int MAX_MVG_SCORE_MAX = 10;
	private final int MAX_LOC_SCORE_MAX = 100;
	private final int AVR_MVG_SCORE_MAX = 5;
	private final int AVR_LOC_SCORE_MAX = 50;

	@Override
	public JudgeResultJson judge(TaskTblEntity taskEntity,String dirName, String fileName) throws IllegalJudgeFileException, AsoLearningSystemErrException {
		JudgeResultJson json = new JudgeResultJson();

		///////////////////////////////////////
		//ファイルの検査
		if( !FileUtils.checkFileExt(new File(dirName+"/"+fileName), CHECK_EXT)){
			throw new IllegalJudgeFileException();
		}

		try{
			String resultDir = dirName+RESULT;//AppSettingProperty.getInstance().getResultDirectory();
			String classDir = dirName+CLASSES;

			ResultTblEntity resultEntity = new ResultTblEntity();
			Set<TestcaseTableEntity> testCaseSet = taskEntity.getTestcaseTableSet();

			//テストケースごとに実行し、点数を集計
			for( TestcaseTableEntity testcase : testCaseSet){
				///////////////////////////////////////
				//シェルの実行（コンパイルと実行と品質解析）
				execShell(testcase,dirName,fileName,resultDir,classDir);

				///////////////////////////////////////
				//エラー情報をチェック
				String erroInfo = getCompileErrorInfo(resultDir);

				///////////////////////////////////////
				//正解かどうかのチェック
				boolean result = checkAnswer(testcase,resultDir);

				///////////////////////////////////////
				//結果をEntityを登録
				resultEntity.addResultTestcaseTbl(getResultTestcaseTblEntity(testcase,result,erroInfo));
			}

			///////////////////////////////////////
			//ソースの品質情報をパース
			resultEntity.addResultMetricsTbl(getResultMetricsTblEntity(resultDir,fileName));

			///////////////////////////////////////
			//総合得点を計算
			resultEntity.setTotalScore( getTotalScore(resultEntity) );

			///////////////////////////////////////
			//結果をDBに書き込む

		}catch (InterruptedException | IOException e) {
			logger.warn("判定結果エラー：", e);
		} catch (JAXBException e) {
			logger.warn("判定結果エラー：", e);
		}

		return json;
	}

	/**
	 * シェルの実行を行う
	 * @param testcase
	 * @param dirName
	 * @param fileName
	 * @param resultDir
	 * @param classDir
	 * @throws AsoLearningSystemErrException
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void execShell(TestcaseTableEntity testcase,String dirName, String fileName,String resultDir,String classDir) throws AsoLearningSystemErrException, IOException, InterruptedException{

		int ret;

		String shellPath = AppSettingProperty.getInstance().getShellPath();

		//必要なフォルダを作成
		makeDirs(classDir,resultDir);

		//実行クラス名（＝ファイル名の拡張子を除いたもの）を取得
		String className = FileUtils.getPreffix(fileName);

		ProcessBuilder pb = new ProcessBuilder(shellPath,dirName,fileName,resultDir,className,testcase.getInputFileName());
		Process process = pb.start();

		logger.trace("バッチ実行開始：");

		ret = process.waitFor();

		logger.trace("バッチ実行終了：" + ret);

	}

	/**
	 *
	 * @param resultDir
	 * @return
	 */
	private String getCompileErrorInfo(String resultDir){
		String fileContentStr = "";

		try{
			// 指定のファイル URL のファイルをバイト列として読み込む
			byte[] fileContentBytes = Files.readAllBytes(Paths.get(resultDir+"/"+ERROR_FILENAME));
			// 読み込んだバイト列を UTF-8 でデコードして文字列にする
			fileContentStr = new String(fileContentBytes, StandardCharsets.UTF_8);
		}catch(IOException e){
			logger.error("コンパイルエラーファイルを読めません：",e);
		}

		return fileContentStr;
	}

	/**
	 * ディレクトリを作成する
	 * @param classDir
	 * @param resultDir
	 */
	private void makeDirs(String classDir,String resultDir){

		//classとresultのフォルダ名を作成する
		FileUtils.makeDir(classDir);
		FileUtils.makeDir(resultDir);

	}

	/**
	 * 答えを確認する
	 *
	 * 答えは、実行した結果のresult.txtと問題作成時に登録された
	 * 結果ファイルが一致するかどうかで判断する
	 *
	 * @param testcase
	 * @param resultDir
	 * @throws AsoLearningSystemErrException
	 */
	private boolean checkAnswer(TestcaseTableEntity testcase,String resultDir) throws AsoLearningSystemErrException{

		//回答があるフォルダを取得
		String answerDir = AppSettingProperty.getInstance().getAnswerDirectory();

		//正解ファイルのパス
		String answerPath = answerDir + "/" + testcase.getOutputFileName();
		//回答ファイルのパス
		String resultPath = resultDir + "/" + RESULT_FILENAME;

		//ファイルの一致をみる
		return FileUtils.fileCompare(answerPath, resultPath);
	}

	/**
	 * テストケースの結果を作成する
	 * @param testcase
	 * @param result
	 * @param errorMsg
	 * @return
	 */
	private ResultTestcaseTblEntity getResultTestcaseTblEntity(TestcaseTableEntity testcase,boolean result,String errorMsg){
		ResultTestcaseTblEntity resultTestcaseEntity = new ResultTestcaseTblEntity();

		resultTestcaseEntity.setTestcaseId(testcase.getTestcaseId());
		if( result ){
			//正解だった場合は配点をセット
			resultTestcaseEntity.setScore(testcase.getAllmostOfMarks());
		}else{
			//不正解の場合は０点
			resultTestcaseEntity.setScore(0);
		}
		resultTestcaseEntity.setMessage(errorMsg);

		return resultTestcaseEntity;
	}

	/**
	 * 品質計測の結果を設定する
	 * @param resultDir
	 * @param fileName
	 * @return
	 * @throws JAXBException
	 * @throws IOException
	 */
	private ResultMetricsTblEntity getResultMetricsTblEntity(String resultDir,String fileName) throws JAXBException, IOException{
		ResultMetricsTblEntity metricsEntity = new ResultMetricsTblEntity();

		//実行クラス名（＝ファイル名の拡張子を除いたもの）を取得
		String className = FileUtils.getPreffix(fileName);

		String metricsPath = resultDir + CCCC+"/"+className;
		//XML解析
		XmlReader<CCCC_ProjectElement> xml =
				new XmlReader<CCCC_ProjectElement>(metricsPath,CCCC_ProjectElement.class);

		CCCC_ProjectElement element = (CCCC_ProjectElement)xml.readListXml();

		int maxMvg = 0;
		int maxLoc = 0;
		int totalMvg = 0;
		int totalLoc = 0;
		int count = 0;

		for( MemberFunction memberFunction : element.getProcedural_detail().getMemberFunctionList() ){

			//最大行数を更新
			if( memberFunction.getLines_of_code().getValue() > maxLoc ){
				maxLoc = memberFunction.getLines_of_code().getValue();
			}

			//最大複雑度を更新
			if( memberFunction.getMcCabes_cyclomatic_complexity().getValue() > maxMvg ){
				maxMvg = memberFunction.getMcCabes_cyclomatic_complexity().getValue();
			}

			//合計値を入れていく
			totalLoc += memberFunction.getLines_of_code().getValue();
			totalMvg += memberFunction.getMcCabes_cyclomatic_complexity().getValue();

			count++;
		}

		float avrMvg = divIntForFloat(totalMvg,count);
		float avrLoc = divIntForFloat(totalLoc,count);
		//Entityにセット
		metricsEntity.setMaxMvg( maxMvg );
		metricsEntity.setMaxLoc( maxLoc );
		metricsEntity.setAvrMvg( avrMvg );
		metricsEntity.setAvrLoc( avrLoc );
		//点数をセット
		metricsEntity.setMaxMvgScore(getMaxMvgScore(maxMvg));
		metricsEntity.setMaxLocScore(getMaxLocScore(maxLoc));
		metricsEntity.setAvrMvgScore(getAvrMvgScore(avrMvg));
		metricsEntity.setAvrLocScore(getAvrLocScore(avrLoc));

		return metricsEntity;
	}

	/**
	 * intの割り算をFloatで返す
	 * @param a
	 * @param b
	 * @return
	 */
	private Float divIntForFloat(int a,int b){
		return (float)a/(float)b;
	}

	/**
	 * 最高複雑度
	 * 	10以下で25点。
	 * １０から１づつ増えるたびに-1.25点
	 * つまり２０の場合は12.5.点、３０の場合は0点となる
	 *
	 * @param MaxMvg
	 * @return
	 */
	private Float getMaxMvgScore(int MaxMvg){
		float score = 0;

		score = 25.0f - (float)(MaxMvg - MAX_MVG_SCORE_MAX) * 1.25f;
		if( score > MAX_SCORE){
			score = 25;
		}else if( score < 0 ){
			score = 0;
		}

		return score;
	}

	/**
	 * 平均複雑度
	 * 	5以下で25点。
	 * 以降１づつ増えるたびに-1.25点
	 * つまり１０の場合は18.5点、２０の場合は6.25点となる
	 *
	 * @param MaxMvg
	 * @return
	 */
	private Float getAvrMvgScore(float avrMvg){
		float score = 0;

		score = 25.0f - (float)(avrMvg - AVR_MVG_SCORE_MAX) * 1.25f;
		if( score > MAX_SCORE){
			score = 25;
		}else if( score < 0 ){
			score = 0;
		}

		return score;
	}
	/**
	 * 最高行数
	 * １００以下で25点。　５行づつに-1点
	 * @param MaxLoc
	 * @return
	 */
	private Float getMaxLocScore(int MaxLoc){
		float score = 0;

		score = 25.0f - (float)(MaxLoc - MAX_LOC_SCORE_MAX)/5f;
		if( score > MAX_SCORE){
			score = 25;
		}else if( score < 0 ){
			score = 0;
		}

		return score;
	}
	/**
	 * 平均行数
	 * ５０以下で25点。　５行づつに-1点
	 * @param avrLoc
	 * @return
	 */
	private Float getAvrLocScore(float avrLoc){
		float score = 0;

		score = 25.0f - (float)(avrLoc - AVR_LOC_SCORE_MAX)/5f;
		if( score > MAX_SCORE){
			score = 25;
		}else if( score < 0 ){
			score = 0;
		}

		return score;
	}

	/**
	 * 合計得点
	 * @param testResultCaseSet
	 * @param metricsSet
	 * @return
	 */
	private Float getTotalScore(ResultTblEntity restulEntity){

		float totalScore= 0;
		Set<ResultTestcaseTblEntity> testResultCaseSet = restulEntity.getResultTestcaseTblSet();
		Set<ResultMetricsTblEntity> metricsSet = restulEntity.getResultMetricsTblSet();

		//テストケースでの判定
		for( ResultTestcaseTblEntity resultCase : testResultCaseSet){
			totalScore += resultCase.getScore();
		}

		//品質の特典
		for( ResultMetricsTblEntity resultCase : metricsSet){

			totalScore += resultCase.getAvrLocScore();
			totalScore += resultCase.getAvrMvgScore();
			totalScore += resultCase.getMaxLocScore();
			totalScore += resultCase.getMaxMvgScore();
		}

		return totalScore;
	}
}
