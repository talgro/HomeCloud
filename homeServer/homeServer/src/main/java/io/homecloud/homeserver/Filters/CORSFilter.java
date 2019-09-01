package io.homecloud.homeserver.Filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CORSFilter implements Filter {
	/**
	 * Default constructor.
	 */
	public CORSFilter() {
		
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		//System.out.println("CORSFilter HTTP Request: " + request.getMethod());

		// Authorize (allow) all domains to consume the content
		((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Origin", "*");
		((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Methods","GET, POST, PUT, DELETE, OPTIONS");
		((HttpServletResponse) servletResponse).setHeader("Access-Control-Max-Age","3600");
		((HttpServletResponse) servletResponse).setHeader("Access-Control-Allow-Headers","*");
		((HttpServletResponse) servletResponse).addHeader("Access-Control-Expose-Headers","*");
		//HttpServletResponse resp = (HttpServletResponse) servletResponse;

		// For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
		if (request.getMethod().equals("OPTIONS")) {
			((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_OK);
			return;
			//resp.setStatus(HttpServletResponse.SC_ACCEPTED);
			//return;
		}
		// pass the request along the filter chain
		chain.doFilter(request, servletResponse);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		
	}
}
