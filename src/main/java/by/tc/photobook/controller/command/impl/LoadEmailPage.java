package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoadEmailPage implements Command
{
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String URL_ATTRIBUTE = "url";
	private static final String EMAIL_PARAM = "email_to";
	private static final String LOAD_EMAIL_PAGE = "Controller?command=loademailpage&email_to=";
	private static final String EMAIL_PAGE_PATH = "/WEB-INF/jsp/mail_page.jsp";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null || 
				session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String emailTo = request.getParameter(EMAIL_PARAM);
			session.setAttribute(URL_ATTRIBUTE, LOAD_EMAIL_PAGE+emailTo);
			
			request.setAttribute(EMAIL_PARAM, emailTo);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(EMAIL_PAGE_PATH);
			requestDispatcher.forward(request, response);
		}
	}
}
