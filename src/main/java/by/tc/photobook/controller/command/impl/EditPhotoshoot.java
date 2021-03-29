package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class EditPhotoshoot implements Command
{
	private static final String LOAD_EDIT_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage&option_to_edit=";
	private static final String PHOTOSHOOT_OPTION_PARAM = "photoshoot_option";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String photoshootOptionToEdit = request.getParameter(PHOTOSHOOT_OPTION_PARAM);
		response.sendRedirect(LOAD_EDIT_PHOTOSHOOT_PAGE+photoshootOptionToEdit);
	}
}
