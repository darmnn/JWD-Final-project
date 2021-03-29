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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_PROFILE_PAGE_WITH_MESSAGE = "Controller?command=loadpprofilepage&message=";
	private static final String LOAD_EDIT_PROFILE_PAGE = "Controller?command=loadprofilepage&edit=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String NO_PHOTOS_MESSAGE = "No photos uploaded yet";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
		
		String message = request.getParameter(MESSAGE_PARAM);
		String edit = request.getParameter(EDIT_PARAM);
		String addPhoto = request.getParameter(ADD_PHOTO_PARAM);
		
		if(message != null && edit != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_PROFILE_PAGE_WITH_MESSAGE+message+"&edit="+edit);
    	}
    	if(message == null && edit != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_EDIT_PROFILE_PAGE+edit);
    	}
    	if(edit != null && message != null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_PROFILE_PAGE_WITH_MESSAGE+message);
    	}
    	if(edit == null && message == null)
    	{
    		session.setAttribute(URL_ATTRIBUTE, LOAD_PROFILE_PAGE);
    	}
    	session.setAttribute(PARAM_ATTRIBUTE, null);
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			
			if(user.getisPhotographer())
			{
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				PhotosService photosService = serviceProvider.getPhotosService();
				List<Photo> userPhotos = null;
			
				try
				{
					userPhotos = photosService.takeUserPhotos(user);
					
					if(userPhotos == null || userPhotos.isEmpty())
					{
						request.setAttribute(MESSAGE_PARAM, NO_PHOTOS_MESSAGE);
					}
    		
					if(edit != null)
					{
						request.setAttribute(EDIT_PARAM, true);
					}
					
					if(addPhoto != null)
					{
						request.setAttribute(ADD_PHOTO_PARAM, true);
					}
					
					request.setAttribute(PHOTOS_ATTRIBUTE, userPhotos);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
					requestDispatcher.forward(request, response);
				}
				catch(ServiceException e)
				{
					request.setAttribute(MESSAGE_PARAM, e.getMessage());
					if(edit != null)
					{
						request.setAttribute(EDIT_PARAM, true);
					}
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
					requestDispatcher.forward(request, response);
				}
			}
			else
			{
				if(edit != null)
				{
					request.setAttribute(EDIT_PARAM, true);
				}
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
			
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
