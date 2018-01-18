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

import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.bo.impl.ResultBoImpl;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;

/**
 * 結果情報の削除
 * @author nishino
 *
 */
@WebServlet(name="DeleteResultServlet",urlPatterns={"/delResult"})
public class DeleteResultServlet extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(DeleteResultServlet.class);

	private final String DISPNO = "00601";
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
			String errMsg = "処理が正常に終了しました";
			//////////////////////////////////////
			//パラメータ取得
			String taskIds = req.getParameter("taskIds");
			Integer userId = getIntParam("userId",req);

			logger.debug("taskIds:"+taskIds);

			//配列にする
			String[] arryTaskId = taskIds.split(",");

			//リストを作成する
			List<Integer> taskList = new ArrayList<Integer>();
			try{
				for( String taskId : arryTaskId){
					taskList.add(Integer.parseInt(taskId));
				}
				//////////////////////////////////////
				//削除処理
				ResultBo retBo = new ResultBoImpl();

				retBo.delete(taskList, userId);

			}catch(NumberFormatException e){
				logger.warn("数値が指定されませんでした："+taskIds);
				errMsg = "処理に失敗しました";
			}catch(Exception e){
				errMsg = "処理に失敗しました";
			}

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

}
