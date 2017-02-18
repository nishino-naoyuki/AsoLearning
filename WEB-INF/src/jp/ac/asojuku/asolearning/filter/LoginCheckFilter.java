/**
 *
 */
package jp.ac.asojuku.asolearning.filter;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.ac.asojuku.asolearning.dto.LogonInfoDTO;
import jp.ac.asojuku.asolearning.param.SessionConst;

/**
 * ログインのチェックを行うフィルター
 * ログインしていない場合は、ログイン画面へリダイレクトする
 * @author nishino
 *
 */
@WebFilter(filterName="LoginCheckFilter", urlPatterns="/*")
public class LoginCheckFilter implements Filter{
	//チェック除外画面
	private String excludeDispList[] =
		{
			"/login","/auth","/logout"
		};

	@Override
	public void destroy() {
		// do nothing

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//リクエストのサーブレットパスを取得
		String servletPath = ((HttpServletRequest)request).getServletPath();

		//除外画面に含まれている場合はチェックしない
		if( Arrays.asList(excludeDispList).contains(servletPath)){
			chain.doFilter(request, response);
			return;
		}

		//ログインセッションを取得し、存在しない場合は、ログイン画面に飛ばす
		HttpSession session = ((HttpServletRequest)request).getSession(false);

		if( session == null ){
			//セッションがない場合はログイン画面へ
			((HttpServletResponse)response).sendRedirect("login");
			return;
		}
		LogonInfoDTO loginInfo =
				(LogonInfoDTO)session.getAttribute(SessionConst.SESSION_LOGININFO);

		if( loginInfo == null ){
			//ログイン画面へ転送
			((HttpServletResponse)response).sendRedirect("login");
		}else{
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// do nothing

	}

}
