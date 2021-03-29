package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.net.URLEncoder;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChangeLocale implements Command
{
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String COMMAND_PARAM = "command";
	private static final String LOCALE_ATTRIBUTE = "locale";
	
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