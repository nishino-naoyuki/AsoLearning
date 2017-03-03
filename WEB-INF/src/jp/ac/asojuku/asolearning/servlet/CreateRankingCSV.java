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

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.bo.impl.ResultBoImpl;
import jp.ac.asojuku.asolearning.dto.RankingDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.RequestConst;

/**
 * ランキングのCSVを作成する
 * @author nishino
 *
 */
@WebServlet(name="CreateRankingCSV",urlPatterns={"/creRankCsv"})
public class CreateRankingCSV extends BaseServlet {
	Logger logger = LoggerFactory.getLogger(CreateRankingCSV.class);

	private final String DISPNO = "01201";
	@Override
	protected String getDisplayNo() {
		return DISPNO;
	}
	/* (非 Javadoc)
	 * @see jp.ac.asojuku.asolearning.servlet.BaseServlet#doPostMain(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, AsoLearningSystemErrException {

        OutputStream out = null;

        try{
			//////////////////////////////
			//パラメータを取得
			Integer courseId = getIntParam(RequestConst.REQUEST_COURSE_ID,req);
			Integer taskId = getIntParam(RequestConst.REQUEST_TASK_ID,req);

			//////////////////////////////
			//ランキング情報を取得
			ResultBo retBo = new ResultBoImpl();

			List<RankingDto> rankingList = retBo.getRanking(courseId, taskId);

			if( CollectionUtils.isEmpty(rankingList) ){

				//////////////////////////////////////////////
		        //出力処理を行う
		        resp.setContentType("text/plain; charset=utf-8");
		        out = resp.getOutputStream();
		        out.write("error:nothing".getBytes());
		        out.flush();
		        logger.warn("出力対象がありません（ランキングCSV）");
		        return;
			}

			//////////////////////////////////////////////
	        //CSV処理を行う
			String fileName = retBo.createRankingCSV(rankingList);

			//////////////////////////////////////////////
	        //出力処理を行う
	        resp.setContentType("text/plain; charset=utf-8");
	        out = resp.getOutputStream();
	        out.write(fileName.getBytes());
	        out.flush();

        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}

}
