package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.util.List;

import by.tc.photobook.bean.Complaint;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ComplaintsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoadComplaintsPage implements Command
{
	private static final String COMPLAINTS_PAGE_PATH = "/WEB-INF/jsp/complaints_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String COMPLAINTS_PARAM = "complaints";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_COMPLAINTS_PAGE = "Controller?command=loadcomplaintspage";
	private static final String MESSAGE_PARAM = "message";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null 
				|| session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			session.setAttribute(URL_ATTRIBUTE, LOAD_COMPLAINTS_PAGE);
			session.setAttribute(PARAM_ATTRIBUTE, null);
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			ComplaintsService complaintsService = serviceProvider.getComplaintsService();
			List<Complaint> allComplaints;
			
			try
			{
				allComplaints = complaintsService.getAllComplaints();
				request.setAttribute(COMPLAINTS_PARAM, allComplaints);
			}
			catch (ServiceException e) 
			{
				request.setAttribute(MESSAGE_PARAM, e.getDescription());
			}
			finally
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(COMPLAINTS_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
	}

}
