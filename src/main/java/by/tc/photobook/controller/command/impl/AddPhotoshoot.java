package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AddPhotoshoot implements Command 
{
	private static final String LOAD_NEW_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage&add_photoshoot=true";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		response.sendRedirect(LOAD_NEW_PHOTOSHOOT_PAGE);
	}
}
