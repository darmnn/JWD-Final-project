package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoadReportPage implements Command
{
	private static final String COMMENT_ID_PARAM = "comment_id";
	private static final String PHOTO_PARAM = "photo";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String USER_PARAM = "user";
	private static final String REPORT_PAGE_PATH = "/WEB-INF/jsp/report_page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String commentId = request.getParameter(COMMENT_ID_PARAM);
			String photoInfo = request.getParameter(PHOTO_PARAM);

			request.setAttribute(COMMENT_ID_PARAM, commentId);
			request.setAttribute(PHOTO_PARAM, photoInfo);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(REPORT_PAGE_PATH);
			requestDispatcher.forward(request, response);
		}
	}
	
}
