package by.tc.photobook.controller.command.impl;

import java.io.IOException;



import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class SavePhotoshootEdit implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PHOTOSHOOT_OPTION_PARAM = "photoshoot_option";
	private static final String PHOTOSHOOT_TYPE_PARAM = "photoshoot_type";
	private static final String PRICE_PARAM = "price";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	private static final String LOAD_PHOTOSHOOT_EDIT_PAGE = "Controller?command=loadphotoshootpage&option_to_edit=";
	private static final String NULL_PARAMS_ERROR = "message.empty_photoshoot_params";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session != null && session.getAttribute(AUTH_ATTRIBUTE) != null && 
				session.getAttribute(USER_ATTRIBUTE) != null)
		{
			int photoshootOption = Integer.parseInt(request.getParameter(PHOTOSHOOT_OPTION_PARAM));
			
			if(!request.getParameter(PHOTOSHOOT_TYPE_PARAM).equals("Тип") && 
					!request.getParameter(PHOTOSHOOT_TYPE_PARAM).equals("Type") && 
					!request.getParameter(PRICE_PARAM).isEmpty())
			{
				int photoshootType = Integer.parseInt(request.getParameter(PHOTOSHOOT_TYPE_PARAM));
				double price = Double.parseDouble(request.getParameter(PRICE_PARAM));
				
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
				
				try
				{
					phOptionsService.editPhotoshootOption(photoshootOption, photoshootType, price);
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
				}
				catch(ServiceException e)
				{
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription());
				}
			}
			else
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_EDIT_PAGE+photoshootOption+"&message="+NULL_PARAMS_ERROR);
			}
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
	}
}
