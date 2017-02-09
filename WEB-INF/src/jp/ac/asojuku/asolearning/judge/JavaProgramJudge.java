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

			ResultTblEntity restulEntity = new ResultTblEntity();
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
				restulEntity.addResultTestcaseTbl(getResultTestcaseTblEntity(testcase,result,erroInfo));
			}

			///////////////////////////////////////
			//ソースの品質情報をパース
			restulEntity.addResultMetricsTbl(getResultMetricsTblEntity(resultDir,fileName));

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
			resultTestcaseEntity.setScore(testcase.getAllmostOfMarks());
		}else{
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

		//Entityにセット
		metricsEntity.setMaxMvg(maxMvg);
		metricsEntity.setMaxLoc(maxLoc);
		metricsEntity.setAvrMvg( divIntForFloat(totalMvg,count) );
		metricsEntity.setAvrLoc( divIntForFloat(totalLoc,count) );
		//点数をセット

		return metricsEntity;
	}

	private Float divIntForFloat(int a,int b){
		return (float)a/(float)b;
	}
}
