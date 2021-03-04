package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.controller.command.Command;

public class LoadRegPage implements Command 
{
	private static final String REG_PAGE_PATH = "/WEB-INF/jsp/registration.jsp";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(REG_PAGE_PATH);
        requestDispatcher.forward(request, response);
    }
}
