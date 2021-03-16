package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class Registration implements Command
{
	private static final String USERNAME_PARAM = "username";
	private static final String EMAIL_PARAM = "email";
	private static final String PASSWORD_PARAM = "password";
	private static final String CHECKBOX_PARAM = "checkbox";
	private static final String CHECKBOX_ON = "on";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	private static final String REG_PAGE_PATH = "/WEB-INF/jsp/registration.jsp";
	private static final String MESSAGE_ATTRIBUTE = "message";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String username = request.getParameter(USERNAME_PARAM);
        String email = request.getParameter(EMAIL_PARAM);
        String password = request.getParameter(PASSWORD_PARAM);
        String checkValue = request.getParameter(CHECKBOX_PARAM);
        boolean isPhotographer = false;
        
        if(checkValue != null && checkValue.equals(CHECKBOX_ON)) 
        {
        	isPhotographer = true;
        }
        
        UserInfo userInfo = new UserInfo(username, email, password, isPhotographer);
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        
        try
        {
        	if(userService.registration(userInfo))
        	{
        		response.sendRedirect(LOAD_MAIN_PAGE);
        	}
        }
        catch(ServiceException e)
        {
        	request.setAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher(REG_PAGE_PATH);
            requestDispatcher.forward(request, response);
        }
    }
}
