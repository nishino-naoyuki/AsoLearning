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
 * お知らせ情報表示
 * @author nishino
 *
 */
@WebServlet(name="CreateInfoInputServlet",urlPatterns={"/tc_createInfo"})
public class CreateInfoInputServlet extends BaseServlet {

	private final String DISPNO = "01602";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doGetMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		//学科データを取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();

		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"tc_createInfo.jsp");
		rd.forward(req, resp);
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {
		doPostMain(req, resp);
	}

}
