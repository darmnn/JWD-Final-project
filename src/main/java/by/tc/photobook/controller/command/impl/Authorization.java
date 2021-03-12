package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class Authorization implements Command
{
	private static final String USERNAME_PARAM = "username";
	private static final String PASSWORD_PARAM = "password";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String AUTH_PAGE_PATH = "/WEB-INF/jsp/authorization.jsp";
	
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter(USERNAME_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        
        UserInfo userInfo = new UserInfo(username, password);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        
        UserInfo authorizedUser = null;
        
        try
        {
        	authorizedUser = userService.authorization(userInfo);
        	
        	HttpSession session = request.getSession(true);
        	session.setAttribute(AUTH_ATTRIBUTE, true);
        	session.setAttribute("user", authorizedUser);
        	
        	response.sendRedirect(LOAD_MAIN_PAGE);
        }
        catch(ServiceException e)
        {
        	request.setAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher(AUTH_PAGE_PATH);
            requestDispatcher.forward(request, response);
        }
    }
}
