package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class DeletePhoto implements Command
{
	private static final String MESSAGE_PARAM = "message";
	private static final String PHOTOID_PARAM = "photo_id";
	private static final String USER_PARAM = "user";
	private static final String ERROR_MESSAGE = "Your photo wasn't deleted! Session is expired, please log in";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(USER_PARAM) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+ERROR_MESSAGE);
		}
		
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
    		response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE + e.getMessage());
    	}
	}
}
