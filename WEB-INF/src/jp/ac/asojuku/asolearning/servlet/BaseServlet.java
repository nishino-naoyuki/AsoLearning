package jp.ac.asojuku.asolearning.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.exception.AsoLearningSystemErrException;
import jp.ac.asojuku.asolearning.param.SessionConst;
import jp.ac.asojuku.asolearning.permit.PermissionChecker;

public abstract class BaseServlet extends HttpServlet {

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//////////////////////////
		//権限チェック
		if(!checkPermit(req)){
			fowardPermitError(req,resp);
			return;
		}

		try {
			doGetMain(req,resp);
		} catch (AsoLearningSystemErrException e) {
			fowardSystemError(req,resp,e);
		} catch (Exception e){
			fowardSystemError(req,resp,e);
		}
	}

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//////////////////////////
		//権限チェック
		if(!checkPermit(req)){
			fowardPermitError(req,resp);
			return;
		}

		try {
			doPostMain(req,resp);
		} catch (AsoLearningSystemErrException e) {
			fowardSystemError(req,resp,e);
		} catch (Exception e){
			fowardSystemError(req,resp,e);
		}
	}

	/**
	 * システムエラー画面を表示する
	 * @param req
	 * @param resp
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 */
	private void fowardSystemError(HttpServletRequest req,HttpServletResponse resp,Exception e) throws ServletException, IOException{

		Logger logger = LoggerFactory.getLogger(this.getClass().getName());

		logger.error("システムエラーが発生しました:",e);
		RequestDispatcher rd = req.getRequestDispatcher("view/error/systemerror.jsp");
		rd.forward(req, resp);
	}


	/**
	 * 権限エラー画面を表示する
	 * @param req
	 * @param resp
	 * @param e
	 * @throws ServletException
	 * @throws IOException
	 */
	private void fowardPermitError(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException{

		RequestDispatcher rd = req.getRequestDispatcher("view/error/permision_err.jsp");
		rd.forward(req, resp);
	}

	protected void doGetMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,AsoLearningSystemErrException
	{
		//405エラーチック名画面へ遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/error/405error.jsp");
		rd.forward(req, resp);
	}

	protected void doPostMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,AsoLearningSystemErrException
	{
		//405エラーチック名画面へ遷移
		RequestDispatcher rd = req.getRequestDispatcher("view/error/405error.jsp");
		rd.forward(req, resp);

	}

	/**
	 * セッションからログイン情報を取得する
	 * 存在しない場合はNULLが返る
	 * @param req
	 * @return
	 */
	protected LogonInfoDTO getUserInfoDtoFromSession(HttpServletRequest req){

		//セッションからログイン情報を取得
		HttpSession session = req.getSession(false);

		if( session == null ){
			return null;
		}

		LogonInfoDTO loginInfo =
				(LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);

		return loginInfo;
	}

	/**
	 * Multipartから文字列パラメータを取得する
	 * @param req
	 * @param paramName
	 * @return
	 * @throws AsoLearningSystemErrException
	 * @throws IllegalStateException
	 * @throws IOException
	 * @throws ServletException
	 */
	protected String getStringParamFromPart(HttpServletRequest req,String paramName) throws AsoLearningSystemErrException, IllegalStateException, IOException, ServletException{
		String sparam = null;
		Part part;

		part = req.getPart(paramName);
		String contentType = part.getContentType();

        if ( contentType == null) {
            try(InputStream inputStream = part.getInputStream()) {
                BufferedReader bufReader = new BufferedReader(new InputStreamReader(inputStream));
                sparam = bufReader.lines().collect(Collectors.joining(System.getProperty("line.separator")));

            } catch (IOException e) {
            	throw new AsoLearningSystemErrException(e);
            }
        }

        return sparam;

	}

	/**
	 * 数値データを取得する
	 * @param req
	 * @param paramName
	 * @return
	 * @throws IllegalStateException
	 * @throws AsoLearningSystemErrException
	 * @throws IOException
	 * @throws ServletException
	 */
	protected Integer getIntParamFromPart(HttpServletRequest req,String paramName) throws IllegalStateException, AsoLearningSystemErrException, IOException, ServletException{
		Integer iparam = null;

		String sparam = getStringParamFromPart(req,paramName);
		try{
			iparam = Integer.parseInt(sparam);

		}catch(NumberFormatException e){
			iparam = null;
		}

		return iparam;
	}


	/**
	 * アップロードされたファイル名をヘッダ情報より取得する
	 * @param part
	 * @return
	 */
	protected String getFileName(Part part) {
        String name = null;
        for (String dispotion : part.getHeader("Content-Disposition").split(";")) {
            if (dispotion.trim().startsWith("filename")) {
                name = dispotion.substring(dispotion.indexOf("=") + 1).replace("\"", "").trim();
                name = name.substring(name.lastIndexOf("\\") + 1);
                break;
            }
        }
        return name;
    }

	/**
	 * 権限チェック
	 * @param req
	 * @return
	 */
	private boolean checkPermit(HttpServletRequest req){
		boolean isPermitOK = true;
		String dispNo = getDisplayNo();
		LogonInfoDTO loginInfo = getUserInfoDtoFromSession(req);

		if( loginInfo == null ){
			return true;
		}

		if( !PermissionChecker.check(dispNo, loginInfo.getRoleId())){
			//権限なし
			Logger logger = LoggerFactory.getLogger(this.getClass().getName());
			logger.error("権限エラーが発生しました:"+dispNo+" "+loginInfo.getRoleId());
			isPermitOK = false;
		}

		return isPermitOK;
	}


	/**
	 * 数値型の情報をリクエストパラメータより取得する
	 * @param pramName
	 * @param req
	 * @return
	 */
	protected Integer getIntParam(String pramName,HttpServletRequest req){
		Integer intParam = null;

		String intString = req.getParameter(pramName);

		try{
			intParam = Integer.parseInt(intString);
		}catch(NumberFormatException e){
			Logger logger = LoggerFactory.getLogger(this.getClass().getName());
			logger.trace("数値パラメータの取得に失敗:name["+pramName+"] value:"+intString);
			intParam = null;
		}

		return intParam;
	}
	protected abstract String getDisplayNo();
}
