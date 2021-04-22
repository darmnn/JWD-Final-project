package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.util.List;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Command loads information about all users of an app
 * 
 * @author Darya Minina
 * @see Command
 */
public class LoadAllUsers implements Command
{
	private static final String ALL_USERS_PAGE_PATH = "/WEB-INF/jsp/all_users_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_ALL_USERS_PAGE = "Controller?command=loadalluserspage";
	private static final String ALL_USERS_ATTRIBUTE = "users";
	private static final String MESSAGE_PARAM = "message";
	
	/**
	 * Executes the loading users command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null 
				|| session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			session.setAttribute(URL_ATTRIBUTE, LOAD_ALL_USERS_PAGE);
			session.setAttribute(PARAM_ATTRIBUTE, null);
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			UserService userService = serviceProvider.getUserService();
			
			try 
			{
				List<UserInfo> allUsers = userService.getAllUsers();
				request.setAttribute(ALL_USERS_ATTRIBUTE, allUsers);
				
			} 
			catch (ServiceException e) 
			{
				request.setAttribute(MESSAGE_PARAM, e.getDescription());
			}
			finally
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(ALL_USERS_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
	}
}
