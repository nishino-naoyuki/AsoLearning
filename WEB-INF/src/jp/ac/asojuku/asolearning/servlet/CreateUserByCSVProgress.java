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
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.ac.asojuku.asolearning.dto.CSVProgressDto;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.SessionConst;

/**
 * CSV処理の途中経過を返す
 * @author nishino
 *
 */
@WebServlet(name="CreateUserByCSVProgress",urlPatterns={"/csvprogress"})
public class CreateUserByCSVProgress extends BaseServlet {

	Logger logger = LoggerFactory.getLogger(CreateUserByCSVProgress.class);

	private final String DISPNO = "01001";
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
        	out = resp.getOutputStream();
			//////////////////////////////
			//パラメータを取得
			String uuid = req.getParameter("uuid");

			HttpSession session = req.getSession(false);
			CSVProgressDto progress = (CSVProgressDto)session.getAttribute(uuid+SessionConst.SESSION_CSV_PROGRESS);

			ObjectMapper mapper = new ObjectMapper();
	        String jsonString = mapper.writeValueAsString(progress);

	        logger.trace("jsonString:{}",jsonString);

	        resp.setContentType("application/json; charset=utf-8");
	        out.write(jsonString.getBytes());
	        out.flush();
        }finally{
        	if(out != null){
        		out.close();
        	}
        }
	}

}
