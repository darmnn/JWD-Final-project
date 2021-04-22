package by.tc.photobook.controller.filter;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

/**
 * Filter for setting the encoding
 * 
 * @author Darya Minina
 * @see Filter
 */
public class CharsetFilter implements Filter
{
	private static final String CHARSET_SET_MESSAGE = "Charset was set";
	private static final String ENCODING_PARAM = "characterEncoding";
	
	private String encoding;
	private ServletContext context;
	
	/**
	 * Encoding destroy
	 * @see Filter
	 */
	public void destroy()
	{
		
	}
	
	/**
	 * Set encoding
	 * 
	 * @param request  {@link ServletRequest}
	 * @param response {@link ServletResponse}
	 * @param chain    {@link FilterChain}
	 * @throws ServletException
	 * @throws IOException
	 * @see Filter
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, 
	ServletException
	{
		request.setCharacterEncoding(encoding);
		response.setCharacterEncoding(encoding);
		context.log(CHARSET_SET_MESSAGE);
		chain.doFilter(request, response);
	}
	
	/**
	 * Initialization of the filter
	 * 
	 * @param fConfig {@link FilterConfig}
	 * @see Filter
	 */
	public void init(FilterConfig filterConfig) throws ServletException
	{
		encoding = filterConfig.getInitParameter(ENCODING_PARAM);
		context = filterConfig.getServletContext();
	}
}