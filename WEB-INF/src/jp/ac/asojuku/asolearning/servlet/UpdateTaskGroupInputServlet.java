/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.util.Digest;
import jp.ac.asojuku.asolearning.util.TimestampUtil;

/**
 * 公開設定一括
 * @author nishino
 *
 */
@WebServlet(name="UpdateTaskGroupInputServlet",urlPatterns={"/popupUpdatePublicTask"})
public class UpdateTaskGroupInputServlet extends BaseServlet {
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
		String taskIds = req.getParameter("taskIds");
		//配列にする
		String[] arryTaskId = taskIds.split(",");
		//タスクをListに変換
		List<String> taskList = Arrays.asList(arryTaskId);
		//セッションに保存する際に、値が被らないように現在時刻のハッシュ値をキーとして保存する
		String name = Digest.create(RequestConst.REQUEST_TASKGRP_ID+TimestampUtil.currentString());
		//セッションへタスクIDをセット
		HttpSession session = req.getSession(false);
		if( session != null ){
			session.setAttribute(name, taskList);
			req.setAttribute(RequestConst.REQUEST_TASKGRP_ID, name);
		}

		//画面遷移
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"pop_updateTask.jsp");
		rd.forward(req, resp);
	}
}
