package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class DeletePhoto implements Command
{
	private static final String PHOTOID_PARAM = "photo_id";
	private static final String USER_PARAM = "user";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			int photoId = Integer.parseInt(request.getParameter(PHOTOID_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	    	PhotosService photosService = serviceProvider.getPhotosService();
	    	
	    	try
	    	{
	    		photosService.deletePhoto(photoId);
	    		response.sendRedirect(LOAD_MAIN_PAGE);
	    	}
	    	catch(ServiceException e)
	    	{
	    		response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE + e.getDescription());
	    	}
		}
		
		
	}
}