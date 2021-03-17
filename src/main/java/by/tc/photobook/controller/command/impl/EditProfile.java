package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.controller.command.Command;

public class EditProfile implements Command 
{
	private static String LOAD_EDIT_PROFILE_PAGE = "Controller?command=loadprofilepage&edit=true";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.sendRedirect(LOAD_EDIT_PROFILE_PAGE);
	}
}
