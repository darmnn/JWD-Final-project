package by.tc.photobook.controller.command.impl;

import java.io.IOException;



import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class LoadProfilePage implements Command
{
	private static final String PROFILE_PAGE_PATH = "/WEB-INF/jsp/profile_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PHOTOS_ATTRIBUTE = "photos";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String EDIT_PARAM = "edit";
	private static final String ADD_PHOTO_PARAM = "add_photo";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PROFILE_PAGE_WITH_MESSAGE = "Controller?command=loadpprofilepage&message=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String NO_PHOTOS_MESSAGE = "message.no_photos";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null || 
				session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String message = request.getParameter(MESSAGE_PARAM);
			
	    	if(message != null)
	    	{
	    		session.setAttribute(URL_ATTRIBUTE, LOAD_PROFILE_PAGE_WITH_MESSAGE+message);
	    	}
	    	else
	    	{
	    		session.setAttribute(URL_ATTRIBUTE, LOAD_PROFILE_PAGE);
	    	}
	    	session.setAttribute(PARAM_ATTRIBUTE, null);
			
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
				
			if(user.getisPhotographer())
			{
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				PhotosService photosService = serviceProvider.getPhotosService();
				List<Photo> userPhotos = null;
			
				try
				{
					userPhotos = photosService.takeUserPhotos(user.getUsername());
					
					if(userPhotos == null || userPhotos.isEmpty())
					{
						request.setAttribute(MESSAGE_PARAM, NO_PHOTOS_MESSAGE);
					}
					else
					{
						request.setAttribute(PHOTOS_ATTRIBUTE, userPhotos);
					}
				}
				catch(ServiceException e)
				{
					request.setAttribute(MESSAGE_PARAM, e.getDescription());
					
				}
				finally
				{
					if(request.getParameter(EDIT_PARAM) != null)
					{
						request.setAttribute(EDIT_PARAM, true);
					}
					
					if(request.getParameter(ADD_PHOTO_PARAM) != null)
					{
						request.setAttribute(ADD_PHOTO_PARAM, true);
					}
					
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
					requestDispatcher.forward(request, response);
				}
			}
			else
			{
				if(request.getParameter(EDIT_PARAM) != null)
				{
					request.setAttribute(EDIT_PARAM, true);
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
		
	}
}
