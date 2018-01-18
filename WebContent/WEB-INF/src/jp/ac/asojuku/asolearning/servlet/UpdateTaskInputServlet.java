/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * @author nishino
 *
 */
@WebServlet(name="UpdateTaskInputServlet",urlPatterns={"/tc_updateTask"})
public class UpdateTaskInputServlet extends BaseServlet {
	Logger logger = LoggerFactory.getLogger(UpdateTaskInputServlet.class);

	private final String DISPNO = "00601";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		////////////////////////////////////
		//学科データを取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();

		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

		////////////////////////////////////
		//パラメータの取得
		Integer taskId = this.getIntParam("taskId", req);

		if( taskId == null ){
			logger.warn("不正な課題IDが指定されています!");
			throw new AsoLearningSystemErrException("不正な課題IDが指定されています!");
		}

		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);
		////////////////////////////////////
		//課題の取得
		TaskBo taskBo = new TaskBoImpl();

		TaskDto taskDto = taskBo.getTaskDetailById(taskId, loginInfo);


		req.setAttribute(RequestConst.REQUEST_TASK_DTO, taskDto);
//		req.setAttribute(RequestConst.REQUEST_PUBLICSTATE, taskPublicList);
//		req.setAttribute(RequestConst.REQUEST_TESTCASE, testCaseList);

		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"tc_updateTask.jsp");
		rd.forward(req, resp);
	}

}
