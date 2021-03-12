package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.controller.command.Command;

public class Logout implements Command 
{
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	private static final String AUTH_ATTRIBUTE = "auth";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session != null)
		{
			session.removeAttribute(AUTH_ATTRIBUTE);
		}
		
		response.sendRedirect(LOAD_MAIN_PAGE);
	}
}
