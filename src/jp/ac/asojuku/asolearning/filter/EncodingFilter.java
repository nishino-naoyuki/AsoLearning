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

import jp.ac.asojuku.asolearning.util.FileUtils;

@WebFilter("/*")
public class EncodingFilter implements Filter {

	/** エンコード */
    private final static String encoding = "UTF-8";
	private String excludeExtList[] =
		{
			"js","css","png","gif","jpg","ico"
		};


	@Override
	public void destroy() {
		;//無処理
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		//リクエストのサーブレットパスを取得
		String servletPath = ((HttpServletRequest)request).getServletPath();

		//js,cs,png,gif,ico,jpgは除外
		if( Arrays.asList(excludeExtList).contains(FileUtils.getExt(servletPath))){
			System.out.println("exclude js,cs,png,gif,ico,jpg!");
			chain.doFilter(request, response);
			return;
		}

		//日本語が文字化けしないようにする
		request.setCharacterEncoding(encoding);
		response.setContentType("text/html; charset=" + encoding);


		chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		;//無処理

	}

}