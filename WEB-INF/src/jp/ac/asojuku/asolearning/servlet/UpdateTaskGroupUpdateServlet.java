/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.CourseBo;
import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.CourseBoImpl;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.CourseDto;
import jp.ac.asojuku.asolearning.dto.TaskPublicDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.param.TaskPublicStateId;

/**
 * @author nishino
 *
 */
@WebServlet(name="UpdateTaskGroupUpdateServlet",urlPatterns={"/updateTaskGrpUpd"})
public class UpdateTaskGroupUpdateServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(UpdateTaskInputServlet.class);

	private final String DISPNO = "00601";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

		String errMsg = "処理が正常に終了しました";

		try{
			///////////////////////////////////
			//学科データを取得
			CourseBo coursBo = new CourseBoImpl();

			List<CourseDto> courselist = coursBo.getCourseAllList();

			////////////////////////////////////
			//パラメータの取得
			String taskIdsSessionName = req.getParameter(RequestConst.REQUEST_TASKGRP_ID);
			//公開情報をセット
			List<TaskPublicDto> taskPublicList = getTaskPublicDtoList(req,courselist);

			//セッションから対象の課題IDを取得
			HttpSession session = req.getSession(false);
			if( session == null ){
				//エラーを通知
				throw new AsoLearningSystemErrException("sessionがnull");
			}

			List<String> taskList = (List<String>)session.getAttribute(taskIdsSessionName);


			TaskBo taskBo = new TaskBoImpl();

			//////////////////////////////////
			//更新処理
			taskBo.updatePublicState(taskList, taskPublicList);

		}catch(Exception e){
			errMsg = "処理に失敗しました";
		}

        OutputStream out = null;

        try{
			//////////////////////////////////////////////
	        //出力処理を行う
	        resp.setContentType("text/plain; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(errMsg.getBytes());
	        out.flush();
        }finally{
        	if(out != null){
        		out.close();
        	}
        }
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
			Integer status = getIntParam(courseId+"-course",req);

			dto.setCourseId(courseId);
			dto.setCourseName(course.getName());
			dto.setStatus(TaskPublicStateId.valueOf(status));
			//dto.setPublicDatetime(getStringParamFromPart(req,courseId+"-startterm")); //TODO:未対応
			dto.setEndDatetime(req.getParameter(courseId+"-endterm"));

			taskpublicList.add(dto);
		}

		return taskpublicList;
	}
}
