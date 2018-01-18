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

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * @author nishino
 *
 */
@WebServlet(name="SearchUserStartServlet",urlPatterns={"/usersearch"})
public class SearchUserStartServlet extends BaseServlet {

	private final String DISPNO = "00801";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {


		//////////////////////////////
		//パラメータを取得
		Integer courseId = getIntParam(RequestConst.REQUEST_COURSE_ID,req);
		Integer taskId = getIntParam(RequestConst.REQUEST_TASK_ID,req);

		req.setAttribute(RequestConst.REQUEST_COURSE_ID, courseId);
		req.setAttribute(RequestConst.REQUEST_TASK_ID, taskId);
		//////////////////////////////
		//学科一覧を取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();
		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

		//////////////////////////////
		//課題一覧を取得
		TaskBo taskBo = new TaskBoImpl();

		List<TaskDto> taskList = taskBo.getTaskListByCouseId(courseId);
		req.setAttribute(RequestConst.REQUEST_TASK_LIST, taskList);

		///////////////////////////////////////////
		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"tc_searchUser.jsp");
		rd.forward(req, resp);
	}

}
