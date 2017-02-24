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
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * 課題検索
 * @author nishino
 *
 */
@WebServlet(name="TaskSearchStartServlet",urlPatterns={"/tasksearch"})
public class TaskSearchStartServlet extends BaseServlet {

	private final String DISPNO = "00604";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {


		//////////////////////////////
		//学科一覧を取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();
		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

		///////////////////////////////////////////
		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/tc_taskSearch.jsp");
		rd.forward(req, resp);
	}


}
