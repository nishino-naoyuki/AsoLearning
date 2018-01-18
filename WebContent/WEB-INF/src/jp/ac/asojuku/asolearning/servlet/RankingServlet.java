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
import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.TaskGroupBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.ResultBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.TaskGroupBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.RankingDto;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.dto.TaskGroupDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * ランキングサーブレット
 * @author nishino
 *
 */
@WebServlet(name="RankingServlet",urlPatterns={"/ranking"})
public class RankingServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(RankingServlet.class);

	private final String DISPNO = "00201";
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
		Integer taskGrpId = getIntParam(RequestConst.REQUEST_TASKGRP_LIST,req);

		req.setAttribute(RequestConst.REQUEST_COURSE_ID, courseId);
		req.setAttribute(RequestConst.REQUEST_TASK_ID, taskId);
		req.setAttribute(RequestConst.REQUEST_TASKGRP_ID, taskGrpId);

		//////////////////////////////
		//学科一覧を取得
		CourseBo coursBo = new CourseBoImpl();

		List<CourseDto> list = coursBo.getCourseAllList();
		req.setAttribute(RequestConst.REQUEST_COURSE_LIST, list);

		//////////////////////////////
		//課題一グループ覧を取得
		TaskGroupBo taskGrpBo = new TaskGroupBoImpl();

		List<TaskGroupDto> taskGrpList = taskGrpBo.getTaskGroupList("");
		req.setAttribute(RequestConst.REQUEST_TASKGRP_LIST, taskGrpList);

		//////////////////////////////
		//課題一覧を取得
		TaskBo taskBo = new TaskBoImpl();

		List<TaskDto> taskList = taskBo.getTaskListByCouseId(courseId);
		req.setAttribute(RequestConst.REQUEST_TASK_LIST, taskList);

		//////////////////////////////
		//ランキング情報を取得
		ResultBo retBo = new ResultBoImpl();

		List<RankingDto> rankingList = retBo.getRanking(courseId, taskId,taskGrpId);
		req.setAttribute(RequestConst.REQUEST_RANKING_LIST, rankingList);

		//////////////////////////////
		//画面転送
		RequestDispatcher rd = req.getRequestDispatcher(getJspDir()+"st_ranking.jsp");
		rd.forward(req, resp);
	}
	@Override
	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		doGetMain(req, resp);
	}


}
