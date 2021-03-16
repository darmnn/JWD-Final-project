package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.controller.command.Command;

public class EditProfile implements Command 
{
	private static String EDIT_ATTR = "edit";
	private static String PROFILE_PAGE_PATH = "/WEB-INF/jsp/profile_page.jsp";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		request.setAttribute(EDIT_ATTR, true);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
        requestDispatcher.forward(request, response);
	}
}
