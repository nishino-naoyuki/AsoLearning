/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.err.ActionErrors;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.TimestampUtil;
import jp.ac.asojuku.asolearning.validator.TaskValidator;

/**
 * 課題作成確認画面
 * @author nishino
 *
 */
@WebServlet(name="CreateTaskConfirmServlet",urlPatterns={"/tc_confirmTask"})
@MultipartConfig(location="/tmp", maxFileSize=1048576)
public class CreateTaskConfirmServlet extends BaseServlet {
	private final int TESTCASE_MAX = 10;
	Logger logger = LoggerFactory.getLogger(CreateTaskConfirmServlet.class);

	private final String DISPNO = "00502";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	private String taskNameHash;
	private ActionErrors errors;

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		errors = new ActionErrors();
		try{

			//学科データを取得
			CourseBo coursBo = new CourseBoImpl();

			List<CourseDto> courselist = coursBo.getCourseAllList();

			//パラメータの取得
			TaskDto dto = getParams(req);
			//テストケースの情報をセット
			taskNameHash = Digest.create(dto.getTaskName()+TimestampUtil.currentString());
			List<TaskTestCaseDto> testCaseList = getTaskTestCaseDtoList(req);
			//公開情報をセット
			List<TaskPublicDto> taskPublicList = getTaskPublicDtoList(req,courselist);

			//dtoにセット
			dto.setTaskPublicList(taskPublicList);
			dto.setTaskTestCaseDtoList(testCaseList);

			//エラーチェック
			checkError(dto);

			RequestDispatcher rd = null;
			if( errors.isHasErr() ){
				//エラーがある場合は、リクエストにセット
				setToRequest(req,dto,courselist);
				//画面遷移
				rd = req.getRequestDispatcher("view/tc_createTask.jsp");
			}else{
				//エラーが無い場合はセッションにセット
				setToSession(req,dto);
				rd = req.getRequestDispatcher("view/tc_creatTaskConfirm.jsp");
			}
			rd.forward(req, resp);


		} catch (IllegalStateException e) {
			logger.error("IllegalStateException：",e);
		} catch (ServletException e) {
			logger.error("ServletException：",e);
		}
	}

	private void setToRequest(HttpServletRequest req,TaskDto dto,List<CourseDto> courselist){

		req.setAttribute(RequestConst.REQUEST_TASK_DTO, dto);
		req.setAttribute(RequestConst.REQUEST_ERRORS, errors);
		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, courselist);
	}

	private void setToSession(HttpServletRequest req,TaskDto dto){

		HttpSession session = req.getSession(false);

		if( session != null ){
			session.setAttribute(RequestConst.REQUEST_TASK_DTO, dto);
		}
	}
	/**
	 * エラーチェック
	 * @param dto
	 * @param testCaseList
	 * @param taskPublicList
	 * @throws AsoLearningSystemErrException
	 */
	private void checkError(TaskDto dto) throws AsoLearningSystemErrException{

		//Validatorでエラーチェック
		TaskValidator.taskName(dto.getTaskName(), errors);
		TaskValidator.question(dto.getQuestion(), errors);
		TaskValidator.publicStateList(dto.getTaskPublicList(), errors);
		TaskValidator.testcaseList(dto.getTaskTestCaseDtoList(), errors);
	}

	/**
	 * パラメータをセット
	 * @param req
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 * @throws AsoLearningSystemErrException
	 */
	private TaskDto getParams(HttpServletRequest req) throws IllegalStateException, IOException, ServletException, AsoLearningSystemErrException{
		TaskDto dto = new TaskDto();

		//課題名を取得
		dto.setTaskName(getStringParamFromPart(req,"taskname"));
		//問題文を取得
		dto.setQuestion(getStringParamFromPart(req,"question"));

		return dto;
	}

	/**
	 * 公開情報をセット
	 * @param req
	 * @return
	 * @throws AsoLearningSystemErrException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 */
	private List<TaskPublicDto> getTaskPublicDtoList(HttpServletRequest req,List<CourseDto> courselist) throws AsoLearningSystemErrException, IllegalStateException, IOException, ServletException{
		List<TaskPublicDto> taskpublicList = new ArrayList<TaskPublicDto>();

		for(CourseDto course : courselist ){
			TaskPublicDto dto = new TaskPublicDto();

			int courseId = course.getId();
			Integer status = getIntParamFromPart(req,courseId+"-course");

			dto.setCourseId(courseId);
			dto.setCourseName(course.getName());
			dto.setStatus(TaskPublicStateId.valueOf(status));
			//dto.setPublicDatetime(getStringParamFromPart(req,courseId+"-startterm")); //TODO:今は未対応
			dto.setEndDatetime(getStringParamFromPart(req,courseId+"-endterm"));

			taskpublicList.add(dto);
		}

		return taskpublicList;
	}
	/**
	 * アップロードされたファイルをテンポラリフォルダにアップする
	 * 本当のフォルダにコピーするのは、エラーが無い時
	 *
	 * @param req
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 * @throws AsoLearningSystemErrException
	 */
	private List<TaskTestCaseDto> getTaskTestCaseDtoList(HttpServletRequest req) throws IllegalStateException, IOException, ServletException, AsoLearningSystemErrException{
		List<TaskTestCaseDto> testCaseList = new ArrayList<TaskTestCaseDto>();
		String tempDir = AppSettingProperty.getInstance().getTempDirectory();

		//入力フォルダを作成
		tempDir = tempDir + "/"+taskNameHash;
		FileUtils.makeDir(tempDir);

		for( int i = 0; i < TESTCASE_MAX; i++ ){
			testCaseList.add(getTaskTestCaseDto(i,tempDir,req));
		}

		return testCaseList;
	}

	/**
	 * @param index
	 * @param tempDir
	 * @param req
	 * @return
	 * @throws IllegalStateException
	 * @throws AsoLearningSystemErrException
	 * @throws IOException
	 * @throws ServletException
	 */
	private TaskTestCaseDto getTaskTestCaseDto(int index,String tempDir,HttpServletRequest req) throws IllegalStateException, AsoLearningSystemErrException, IOException, ServletException{
		TaskTestCaseDto testcase = new TaskTestCaseDto();

		//入力ファイル
		Part ipart = req.getPart("inputfile["+index+"]");
		if(ipart != null ){
	        String name = getFileName(ipart);
	        if( StringUtils.isNotEmpty(name)){
		        testcase.setInputFileName(taskNameHash+"/"+name);
		        //テンポラリに一旦書き込み
		        ipart.write(tempDir+"/"+name);
	        }
		}

		//出力ファイル
		Part opart = req.getPart("outputfile["+index+"]");
		if(opart != null ){
	        String name = getFileName(opart);
	        if( StringUtils.isNotEmpty(name)){
		        testcase.setOutputFileName(taskNameHash+"/"+name);
		        //テンポラリに一旦書き込み
		        opart.write(tempDir+"/"+name);
	        }
		}

		//配点
		Part aPart = req.getPart("haiten["+index+"]");
		if( aPart != null){
			testcase.setAllmostOfMarks(getIntParamFromPart(req,"haiten["+index+"]"));
		}

		return testcase;
	}
}
