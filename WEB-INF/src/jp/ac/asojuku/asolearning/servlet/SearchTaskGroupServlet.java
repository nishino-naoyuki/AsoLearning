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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.TaskGroupBo;
import jp.ac.asojuku.asolearning.bo.impl.TaskGroupBoImpl;
import jp.ac.asojuku.asolearning.dto.TaskGroupDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.TaskGroupSearchResultJson;

/**
 * @author nishino
 *
 */
@WebServlet(name="SearchTaskGroupServlet",urlPatterns={"/searchTaskGrp"})
public class SearchTaskGroupServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(SearchTaskGroupServlet.class);

	private final String DISPNO = "00801";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}

	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {


        OutputStream out = null;

        try{
			//////////////////////////////
			//パラメータを取得
        	String groupName = req.getParameter("groupname");

			//////////////////////////////
			//課題一覧を取得
			TaskGroupBo taskGrpBo = new TaskGroupBoImpl();

			List<TaskGroupDto> taskGrpList = taskGrpBo.getTaskGroupList(groupName);

			List<TaskGroupSearchResultJson> jsonList = new ArrayList<>();
			for(TaskGroupDto dto : taskGrpList){
				TaskGroupSearchResultJson json = new TaskGroupSearchResultJson();

				json.value = dto.getName();

				jsonList.add(json);

			}

			TaskGroupSearchResultJson[] jsons = jsonList.toArray(new TaskGroupSearchResultJson[0]);

	        ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(jsons);

	        logger.trace("jsonString:{}",jsonString);
			//////////////////////////////////////////////
	        //JSON出力処理を行う
	        resp.setContentType("application/json; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(jsonString.getBytes());
	        out.flush();
        }catch(Exception e){

        	logger.warn(e.getMessage());

        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}
}
