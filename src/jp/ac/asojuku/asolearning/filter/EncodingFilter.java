package jp.ac.asojuku.asolearning.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName="encoding_filter", urlPatterns="/*")
public class EncodingFilter implements Filter {

	/** エンコード */
    private final static String encoding = "UTF-8";


	@Override
	public void destroy() {
		;//無処理
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

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