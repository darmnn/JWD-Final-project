package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class OrderPhotoshoot implements Command 
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_ORDER_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshoots&option_to_order=";
	private static final String PHOTOSHOOT_OPTION_PARAM = "photoshoot_option";
	private static final String PHOTOGRAPHER_PARAM = "photographer";
	private static final String PHOTOGRAPHER_ATTR = "&photographer=";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null 
				|| session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String photographer = request.getParameter(PHOTOGRAPHER_PARAM);
			String photoshootOptionToOrder = request.getParameter(PHOTOSHOOT_OPTION_PARAM);
			response.sendRedirect(LOAD_ORDER_PHOTOSHOOT_PAGE+photoshootOptionToOrder+PHOTOGRAPHER_ATTR+photographer);
		}
	}
}
