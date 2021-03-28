package by.tc.photobook.controller.command.impl;

import java.io.IOException;


import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditProfile implements Command 
{
	private static String LOAD_EDIT_PROFILE_PAGE = "Controller?command=loadprofilepage&edit=true";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.sendRedirect(LOAD_EDIT_PROFILE_PAGE);
	}
}
