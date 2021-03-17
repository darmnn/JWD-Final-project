package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.controller.command.Command;

public class ChangeLocale implements Command
{
	private static final String URL_ATTRIBUTE = "url";
	private static final String COMMAND_PARAM = "command";
	private static final String LOCALE_ATTRIBUTE = "locale";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);
		String url = (String) session.getAttribute(URL_ATTRIBUTE);
		session.removeAttribute(URL_ATTRIBUTE);
		String newLocale = request.getParameter(COMMAND_PARAM);
		
		session.setAttribute(LOCALE_ATTRIBUTE, newLocale);
		response.sendRedirect(url);
	}
}
