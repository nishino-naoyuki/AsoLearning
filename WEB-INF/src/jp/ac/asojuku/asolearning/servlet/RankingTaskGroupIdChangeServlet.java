/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.TaskBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskBoImpl;
import jp.ac.asojuku.asolearning.dto.TaskDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.SelectTaskJson;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * AJAX：ランキング画面で学科の選択が変わった場合
 * @author nishino
 *
 */
@WebServlet(name="RankingTaskGroupIdChangeServlet",urlPatterns={"/rankingtgroupchange"})
public class RankingTaskGroupIdChangeServlet extends BaseServlet {


	Logger logger = LoggerFactory.getLogger(RankingTaskGroupIdChangeServlet.class);

	private final String DISPNO = "00201";
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


        OutputStream out = null;

        try{
			//////////////////////////////
			//パラメータを取得
			Integer taskGrpId = getIntParam(RequestConst.REQUEST_TASKGRP_LIST,req);


			//////////////////////////////
			//課題一覧を取得
			TaskBo taskBo = new TaskBoImpl();

			List<TaskDto> taskList = taskBo.getTaskListByTaskGrpId(taskGrpId);

			SelectTaskJson[] jsons = new SelectTaskJson[taskList.size()];

			int idx = 0;
			for( TaskDto taskDto : taskList ){
				SelectTaskJson json = new SelectTaskJson();

				json.itemValue = String.valueOf(taskDto.getTaskId());
				json.itemLabel = taskDto.getTaskName();

				jsons[idx] = json;
				idx++;
			}
	        ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(jsons);

	        //logger.trace("jsonString:{}",jsonString);
			//////////////////////////////////////////////
	        //JSON出力処理を行う
	        resp.setContentType("application/json; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(jsonString.getBytes());
	        out.flush();

        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}

}
