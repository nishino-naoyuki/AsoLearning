/**
 *
 */
package jp.ac.asojuku.asolearning.servlet;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.bo.ResultBo;
import jp.ac.asojuku.asolearning.bo.impl.ResultBoImpl;
import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.json.ResultCommentJson;
import jp.ac.asojuku.asolearning.param.RequestConst;
import jp.ac.asojuku.asolearning.util.HtmlUtil;

/**
 * コメントを更新する（Ajax）
 * @author nishino
 *
 */
@WebServlet(name="UpdateResultComment",urlPatterns={"/updatecomment"})
public class UpdateResultComment extends BaseServlet {


	Logger logger = LoggerFactory.getLogger(UpdateResultComment.class);

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
			Integer resultId = getIntParam(RequestConst.REQUEST_RESULT_ID,req);
			String comment = req.getParameter(RequestConst.REQUEST_RESULT_COMMENT);

			LogonInfoDTO login = getUserInfoDtoFromSession(req);

			//////////////////////////////
			//ソースコードを取得
			ResultBo resultBo = new ResultBoImpl();

			resultBo.setComment(login, resultId, comment);

			ResultCommentJson jsons = new ResultCommentJson();

			jsons.comment = HtmlUtil.nl2be( comment );

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
