/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.config.AppSettingProperty;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.dto.TaskTestCaseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.FileUtils;
import jp.ac.asojuku.asolearning.util.TimestampUtil;

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

	private final String DISPNO = "display00502";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	private String taskNameHash;

	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		try{
			//パラメータの取得
			TaskDto dto = getParams(req);
			//テストケースの情報をセット
			taskNameHash = Digest.create(dto.getTaskName()+TimestampUtil.currentString());
			List<TaskTestCaseDto> testCaseList = getTaskTestCaseDtoList(req);
			//公開情報をセット
			List<TaskPublicDto> taskPublicList = getTaskPublicDtoList(req);

			//エラーチェック

			//画面遷移

		} catch (IllegalStateException e) {
			logger.error("IllegalStateException：",e);
		} catch (ServletException e) {
			logger.error("ServletException：",e);
		}
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
	private List<TaskPublicDto> getTaskPublicDtoList(HttpServletRequest req) throws AsoLearningSystemErrException, IllegalStateException, IOException, ServletException{
		List<TaskPublicDto> taskpublicList = new ArrayList<TaskPublicDto>();

		//学科データを取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();

		for(CourseDto course : list ){
			TaskPublicDto dto = new TaskPublicDto();

			int courseId = course.getId();
			Integer status = getIntParamFromPart(req,courseId+"-course");

			dto.setCourseId(courseId);
			dto.setCourseName(course.getName());
			dto.setStatus(TaskPublicStateId.valueOf(status));
			dto.setPublicDatetime(null);
			dto.setEndDatetime(null);
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
			TaskTestCaseDto testcase = new TaskTestCaseDto();

			//入力ファイル
			Part ipart = req.getPart("inputfile["+i+"]");
			if(ipart != null ){
		        String name = getFileName(ipart);

		        testcase.setInputFileName(taskNameHash+"/"+name);

		        //テンポラリに一旦書き込み
		        ipart.write(tempDir+"/"+name);
			}

			//出力ファイル
			Part opart = req.getPart("outputfile["+i+"]");
			if(opart != null ){
		        String name = getFileName(opart);

		        testcase.setOutputFileName(taskNameHash+"/"+name);

		        //テンポラリに一旦書き込み
		        ipart.write(tempDir+"/"+name);
			}
		}

		return testCaseList;
	}
}
