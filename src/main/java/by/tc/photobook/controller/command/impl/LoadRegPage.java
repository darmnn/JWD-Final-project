package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;


import by.tc.photobook.controller.command.Command;

public class LoadRegPage implements Command 
{
	private static final String REG_PAGE_PATH = "/WEB-INF/jsp/registration.jsp";
	private static final String MESSAGE_PARAM = "message";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String LOAD_REG_PAGE_WITH_MESSAGE = "Controller?command=loadregpage&message=";
	private static final String LOAD_REG_PAGE = "Controller?command=loadregpage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
		HttpSession session = request.getSession(true);
		String message = request.getParameter(MESSAGE_PARAM);
		
		if(message != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_REG_PAGE_WITH_MESSAGE+message);
    	}
    	else
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_REG_PAGE);
    	}
		session.setAttribute(PARAM_ATTRIBUTE, null);
		
		request.setAttribute(MESSAGE_PARAM, message);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(REG_PAGE_PATH);
        requestDispatcher.forward(request, response);
    }
}
