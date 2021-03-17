package by.tc.photobook.controller.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class CharsetFilter implements Filter
{
	private static final String CHARSET_SET_MESSAGE = "Charset was set";
	private static final String ENCODING_PARAM = "characterEncoding";
	
	private String encoding;
	private ServletContext context;
	
	public void destroy()
	{
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, 
	ServletException
	{
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		context.log(CHARSET_SET_MESSAGE);
		chain.doFilter(request, response);
	}
	
	public void init(FilterConfig filterConfig) throws ServletException
	{
		encoding = filterConfig.getInitParameter(ENCODING_PARAM);
		context = filterConfig.getServletContext();
	}
}
