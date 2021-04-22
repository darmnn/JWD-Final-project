package by.tc.photobook.controller.command.impl;

import java.io.IOException;



import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotoshootTypesService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * The command adds new type of photoshoots provided by a user
 * 
 * @author Darya Minina
 * @see Command
 */
public class AddPhotoshootType implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String TYPE_NAME_PARAM = "photoshoot_type";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String EMPTY_TYPE = "message.empty_photoshoot_type";
	
	/**
	 * Executes the adding photoshoot type command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String newPhotoshootType = request.getParameter(TYPE_NAME_PARAM);
			if(newPhotoshootType == null || newPhotoshootType.isEmpty())
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+EMPTY_TYPE);
			}
			else
			{
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				PhotoshootTypesService phTypesService = serviceProvider.getPhotoshootTypesService();
				try 
				{
					phTypesService.addNewType(newPhotoshootType);
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
				} 
				catch (ServiceException e) 
				{
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription());
				}
			}
		}
	}
}
