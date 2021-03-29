package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeletePhotoshoot implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PHOTOSHOOT_OPTION_PARAM = "photoshoot_option";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session != null && session.getAttribute(AUTH_ATTRIBUTE) != null && 
				session.getAttribute(USER_ATTRIBUTE) != null)
		{
			int photoshootOption = Integer.parseInt(request.getParameter(PHOTOSHOOT_OPTION_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
			
			try 
			{
				phOptionsService.deletePhotoshootOption(photoshootOption);
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
			} 
			catch (ServiceException e) 
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getMessage());
			}
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
