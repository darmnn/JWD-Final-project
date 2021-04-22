package by.tc.photobook.controller.command.impl;

import java.io.IOException;


import java.net.URLEncoder;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Command changes interface language of all app to the language passed as a parameter to http request
 * 
 * @author Darya Minina
 * @see Command
 */
public class ChangeLocale implements Command
{
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String COMMAND_PARAM = "command";
	private static final String LOCALE_ATTRIBUTE = "locale";
	
	/**
	 * Executes the changing locale command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);
		String url = (String) session.getAttribute(URL_ATTRIBUTE);
		String parameter = (String) session.getAttribute(PARAM_ATTRIBUTE);
		
		session.removeAttribute(URL_ATTRIBUTE);
		if(parameter != null)
		{
			session.removeAttribute(PARAM_ATTRIBUTE);
			
		}
		String newLocale = request.getParameter(COMMAND_PARAM);
		
		session.setAttribute(LOCALE_ATTRIBUTE, newLocale);
		if(parameter != null)
		{
			response.sendRedirect(url + URLEncoder.encode(parameter, "UTF-8"));
		}
		else
		{
			response.sendRedirect(url);
		}
		
	}
}