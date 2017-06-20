/**
 *
 */
package jp.ac.asojuku.asolearning.judge;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.config.MessageProperty;
import jp.ac.asojuku.asolearning.dao.ResultDao;
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
import jp.ac.asojuku.asolearning.util.CompressUtils;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.XmlReader;

/**
 * javaのプログラムの判定を行うクラス
 * @author nishino
 *
 */
public class JavaCProgramJudge implements Judge {

	Logger logger = LoggerFactory.getLogger(JavaCProgramJudge.class);
	private final String CHECK_EXT1 = "java";
	private final String CHECK_EXT2 = "c";
	private final String CHECK_EXT3 = "cpp";
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
	private final int PROCESS_TIMEOUT = 10;

	@Override
	public JudgeResultJson judge(TaskTblEntity taskEntity,String dirName, String fileName,int userId,Connection con) throws IllegalJudgeFileException, AsoLearningSystemErrException {
		JudgeResultJson json = new JudgeResultJson();

		///////////////////////////////////////
		//ファイルの検査
		String srcFile = dirName+"/"+fileName;
		if( !FileUtils.checkFileExt(new File(srcFile), CHECK_EXT1) &&
				!FileUtils.checkFileExt(new File(srcFile), CHECK_EXT2) &&
				!FileUtils.checkFileExt(new File(srcFile), CHECK_EXT3) ){
			throw new IllegalJudgeFileException();
		}

		Date now = new Date();

		try{
			String resultDir = dirName+RESULT;//AppSettingProperty.getInstance().getResultDirectory();
			String classDir = dirName+CLASSES;

			ResultTblEntity resultEntity = getResultTblEntity(taskEntity,userId);

			Set<TestcaseTableEntity> testCaseSet = taskEntity.getTestcaseTableSet();

			boolean compileError = false;
			//テストケースごとに実行し、点数を集計
			for( TestcaseTableEntity testcase : testCaseSet){
				///////////////////////////////////////
				//シェルの実行（コンパイルと実行と品質解析）
				execShell(testcase,dirName,fileName,resultDir,classDir);

				///////////////////////////////////////
				//エラー情報をチェック
				String erroInfo = getCompileErrorInfo(resultDir);
				compileError = StringUtils.isNoneEmpty(erroInfo);

				///////////////////////////////////////
				//正解かどうかのチェック
				boolean result = checkAnswer(testcase,resultDir);

				///////////////////////////////////////
				//結果をEntityを登録
				resultEntity.addResultTestcaseTbl(getResultTestcaseTblEntity(testcase,result,compileError,resultDir,erroInfo));
			}

			///////////////////////////////////////
			//ソースの品質情報をパース
			resultEntity.addResultMetricsTbl(getResultMetricsTblEntity(resultDir,fileName));

			///////////////////////////////////////
			//総合得点を計算
			resultEntity.setTotalScore( getTotalScore(resultEntity) );

			///////////////////////////////////////
			//課題提出フラグと提出日時をセット
			json.allOK = isAllOK(resultEntity);
			resultEntity.setHanded((json.allOK==true?1:0));
			resultEntity.setHandedTimestamp((json.allOK==true?now:null));

			///////////////////////////////////////
			//ソースファイルの内容を圧縮してセット
			resultEntity.setAnswer(getCompressedSourceCode(srcFile));

			///////////////////////////////////////
			//DBに書き込み
			resultEntity.setTaskTbl(taskEntity);
			setResultToDB(con,userId,resultEntity);

			json.score = resultEntity.getTotalScore();


		}catch (InterruptedException | IOException  | JAXBException e) {
			logger.warn("判定結果エラー：", e);
			throw new AsoLearningSystemErrException(e);
		} catch (SQLException e) {
			logger.warn("SQLエラー：", e);
			throw new AsoLearningSystemErrException(e);
		} catch (Exception e) {
			logger.warn("不明エラー：", e);
			throw new AsoLearningSystemErrException(e);
		} finally {
			///////////////////////////////////////
			//すべて終わったら判定ファイルは削除
			FileUtils.delete(dirName);

		}

		return json;
	}

	/**
	 * ソースコードを読み取り圧縮してBase64文字列に変換する
	 * @param srcFile
	 * @return
	 * @throws IOException
	 */
	private String getCompressedSourceCode(String srcFile) throws IOException{

		//ソースを取得
		List<String> srcList = FileUtils.readLine(srcFile);

		StringBuilder sb = new StringBuilder();
		for( String str : srcList ){
			sb.append(str).append("\n");
		}

		//ソースを圧縮
		return CompressUtils.encode(sb.toString());
	}

	/**
	 * すべて正解か？
	 * @param resultEntity
	 * @return
	 */
	private boolean isAllOK(ResultTblEntity resultEntity){

		if( resultEntity.getResultTestcaseTblSet() == null ){
			return false;
		}
		boolean isAllOk = true;

		for( ResultTestcaseTblEntity testCase : resultEntity.getResultTestcaseTblSet() ){

			if( testCase.getScore() == 0){
				//0点ということは不正解
				isAllOk = false;
				break;
			}
		}

		return isAllOk;
	}

	/**
	 * すでに結果データがある場合は取得する
	 *
	 * @param taskEntity
	 * @param userId
	 * @return
	 */
	private ResultTblEntity getResultTblEntity(TaskTblEntity taskEntity,int userId){

		ResultTblEntity resultEntity = new ResultTblEntity();

		if( taskEntity.getResultTblSet().size() > 0 ){
			for( ResultTblEntity wkResult : taskEntity.getResultTblSet() ){

				if(wkResult.getUserTbl().getUserId() == userId){
					resultEntity = wkResult;
					break;
				}
			}
		}

		return resultEntity;
	}

	/**
	 * 結果情報をDBへ書き込む
	 *
	 * @param con
	 * @param userId
	 * @param resultEntity
	 * @throws SQLException
	 */
	private void setResultToDB(Connection con,int userId,ResultTblEntity resultEntity) throws SQLException{

		ResultDao dao = new ResultDao(con);

		dao.insertOrupdateTaskResult(userId, resultEntity);
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
		//入力ファイルの古パスを取得
		String inputDir = AppSettingProperty.getInstance().getInputDirectory();
		String inputFileName = (testcase.getInputFileName()==null ? "":inputDir+"/"+testcase.getInputFileName());

		//引数をゲット
		List<String> paramList = FileUtils.readLine(inputFileName);
		String[] args = {"","","","",""};
		for( int i = 0 ; i < args.length && i < paramList.size(); i++ ){
			args[i] = paramList.get(i);
			//logger.info("args["+i+"]="+paramList.get(i));
		}

		//引数は常に5個渡す
		ProcessBuilder pb =
				new ProcessBuilder(shellPath,dirName,fileName,resultDir,className,
						args[0],args[1],args[2],args[3],args[4],String.valueOf(PROCESS_TIMEOUT));
		Process process = pb.start();

		logger.trace("バッチ実行開始：");

		//バッチ実行タイムアウトは１０秒
		ret = process.waitFor();

		if( ret != 0){
			//処理失敗
			logger.warn("実行に失敗しました。[" + dirName+"/"+fileName+"]");
			throw new AsoLearningSystemErrException("実行に失敗しました");
		}

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
	 * @throws AsoLearningSystemErrException
	 */
	private ResultTestcaseTblEntity getResultTestcaseTblEntity(TestcaseTableEntity testcase,boolean result,boolean compileError,String resultDir,String errorMsg) throws AsoLearningSystemErrException{
		ResultTestcaseTblEntity resultTestcaseEntity = new ResultTestcaseTblEntity();

		//テストIDをセット
		resultTestcaseEntity.setTestcaseId(testcase.getTestcaseId());

		///////////////////////////////////////////
		//コンパイルエラーの場合はコンパイルエラーのメッセージを入れる
		if( compileError ){
			resultTestcaseEntity.setScore(0);
			resultTestcaseEntity.setMessage("コンパイルエラー："+errorMsg);
			return resultTestcaseEntity;
		}

		///////////////////////////////////////////
		//実行できた場合は結果を元にメッセージを入れる
		if( result ){
			//正解だった場合は配点をセット
			resultTestcaseEntity.setScore(testcase.getAllmostOfMarks());
		}else{
			//不正解の場合は０点
			resultTestcaseEntity.setScore(0);

			errorMsg = getErrorMessage(testcase,resultDir);
		}
		resultTestcaseEntity.setMessage(errorMsg);

		return resultTestcaseEntity;
	}

	/**
	 * 実行結果を表示
	 * @param resultDir
	 * @return
	 * @throws AsoLearningSystemErrException
	 */
	private String getErrorMessage(TestcaseTableEntity testcase,String resultDir) throws AsoLearningSystemErrException{

		StringBuffer sb = new StringBuffer();

		sb.append(MessageProperty.getInstance().getProperty(MessageProperty.JUDGE_RET_NOTMATCH));

		//入力ファイルの古パスを取得
		String inputDir = AppSettingProperty.getInstance().getInputDirectory();
		String inputFileName = (testcase.getInputFileName()==null ? "":inputDir+"/"+testcase.getInputFileName());

		//引数をゲット
		List<String> paramList = FileUtils.readLine(inputFileName);
		//正解ファイルのパス
		String answerDir = AppSettingProperty.getInstance().getAnswerDirectory();
		String answerPath = answerDir + "/" + testcase.getOutputFileName();
		List<String> answerList = FileUtils.readLine(answerPath);
		//回答ファイルのパス
		String resultPath = resultDir + "/" + RESULT_FILENAME;
		List<String> resultList = FileUtils.readLine(resultPath);

		if( paramList.size() > 0 ){
			sb.append("\n\n*****[実行引数]*****\n");
			for( String arg : paramList ){
				sb.append(arg);
				sb.append(" ");
			}
		}
		sb.append("\n\n*****[実行結果]*****\n");
		for( String msg : resultList ){
			sb.append(msg);
			sb.append("\n");
		}
		sb.append("\n*****[正解出力]*****\n");
		for( String msg : answerList ){
			sb.append(msg);
			sb.append("\n");
		}
		sb.append("************************\n");

		return sb.toString();
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

		String metricsPath = resultDir + CCCC+"/"+className+".xml";
		//XML解析
		XmlReader<CCCC_ProjectElement> xml =
				new XmlReader<CCCC_ProjectElement>(metricsPath,CCCC_ProjectElement.class);

		CCCC_ProjectElement element = (CCCC_ProjectElement)xml.readListXml();

		int maxMvg = 0;
		int maxLoc = 0;
		int totalMvg = 0;
		int totalLoc = 0;
		int count = 0;

		for( MemberFunction memberFunction : element.procedural_detail.member_function ){

			//最大行数を更新
			if( memberFunction.lines_of_code.value > maxLoc ){
				maxLoc = memberFunction.lines_of_code.value;
			}

			//最大複雑度を更新
			if( memberFunction.McCabes_cyclomatic_complexity.value > maxMvg ){
				maxMvg = memberFunction.McCabes_cyclomatic_complexity.value;
			}

			//合計値を入れていく
			totalLoc += memberFunction.lines_of_code.value;
			totalMvg += memberFunction.McCabes_cyclomatic_complexity.value;

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

		score = 12.5f - (float)(MaxMvg - MAX_MVG_SCORE_MAX) * 1.25f;
		if( score > MAX_SCORE){
			score = 12.5f;
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

		score = 12.5f - (float)(avrMvg - AVR_MVG_SCORE_MAX) * 1.25f;
		if( score > MAX_SCORE){
			score = 12.5f;
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

		score = 12.5f - (float)(MaxLoc - MAX_LOC_SCORE_MAX)/5f;
		if( score > MAX_SCORE){
			score = 12.5f;
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

		score = 12.5f - (float)(avrLoc - AVR_LOC_SCORE_MAX)/5f;
		if( score > MAX_SCORE){
			score = 12.5f;
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
