package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.tc.photobook.controller.command.Command;

public class LoadPhoto implements Command 
{
	private static String LOAD_NEW_PHOTO_PROFILE_PAGE = "Controller?command=loadprofilepage&add_photo=true";
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.sendRedirect(LOAD_NEW_PHOTO_PROFILE_PAGE);
	}
}
