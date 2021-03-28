package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class SaveNewPhoto implements Command
{
	private static String NEW_PHOTO = "new_photo";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String NO_UPD_MESSAGE = "Error while loading new picture!";
	private static final String IMAGE_FOLDER = "images/";
	
	private static final int START_RATING = 0;
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String photoPath = request.getParameter(NEW_PHOTO);
		photoPath = IMAGE_FOLDER + photoPath;
		
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
			
			LocalDate date = LocalDate.now();
			Photo newPhoto = new Photo(username, date, photoPath, START_RATING);
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        PhotosService photosService = serviceProvider.getPhotosService();
	        
	        boolean uploaded = false;
	        try
	        {
	        	uploaded = photosService.addNewPhoto(newPhoto);
	        	if(!uploaded)
	        	{
	        		response.sendRedirect(LOAD_MAIN_PAGE+NO_UPD_MESSAGE);
	        	}
	        	
	        	response.sendRedirect(LOAD_PROFILE_PAGE);
	        }
	        catch(ServiceException e)
	        {
	        	response.sendRedirect(LOAD_MAIN_PAGE+e.getMessage());
	        }
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
