package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SavePhotoshoot implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PHOTOSHOOT_TYPE_PARAM = "photoshoot_type";
	private static final String PRICE_PARAM = "price";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	private static final String NULL_PARAMS_ERROR = "message.empty_photoshoot_params";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session != null && session.getAttribute(AUTH_ATTRIBUTE) != null && 
				session.getAttribute(USER_ATTRIBUTE) != null)
		{
			if(!request.getParameter(PHOTOSHOOT_TYPE_PARAM).equals("Тип") && 
					!request.getParameter(PHOTOSHOOT_TYPE_PARAM).equals("Type") && 
					!request.getParameter(PRICE_PARAM).isEmpty())
			{
				UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
				
				
				int photoshootType = Integer.parseInt(request.getParameter(PHOTOSHOOT_TYPE_PARAM));
				double price = Double.parseDouble(request.getParameter(PRICE_PARAM));
				
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
				
				try 
				{
					phOptionsService.addPhotoshootOption(user.getId(), photoshootType, price);
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
				} 
				catch (ServiceException e) 
				{
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription());
				}
			}
			else
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+NULL_PARAMS_ERROR);
			}
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
	}
}
