package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class BlockUnlock implements Command
{
	private static final String ACTION_PARAM = "action";
	private static final String USER_ID_PARAM = "userid";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_ALL_USERS_PAGE = "Controller?command=loadalluserspage";
	private static final String LOAD_ALL_USERS_PAGE_WITH_MESSAGE = "Controller?command=loadalluserspage&message=";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String action = request.getParameter(ACTION_PARAM);
			int userId = Integer.parseInt(request.getParameter(USER_ID_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			UserService userService = serviceProvider.getUserService();
			
			try 
			{
				userService.blockUnlock(userId, action);
				response.sendRedirect(LOAD_ALL_USERS_PAGE);
			} 
			catch (ServiceException e) 
			{
				response.sendRedirect(LOAD_ALL_USERS_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
	}
}
