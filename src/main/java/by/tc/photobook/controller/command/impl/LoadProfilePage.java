package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;

public class LoadProfilePage implements Command
{
	private static final String MAIN_PAGE_PATH = "/WEB-INF/jsp/main_page.jsp";
	private static final String PROFILE_PAGE_PATH = "/WEB-INF/jsp/profile_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String MESSAGE_ATTRIBUTE = "message";
	private static final String SESSION_ERROR_MESSAGE = "Session is expired, please log in!";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
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
    		
					request.setAttribute("photos", userPhotos);
					RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
					requestDispatcher.forward(request, response);
				}
				catch(ServiceException e)
				{
					request.setAttribute(MESSAGE_ATTRIBUTE, e.getMessage());
		        	RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
		        	requestDispatcher.forward(request, response);
				}
			}
			else
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
			
		}
		else
		{
			request.setAttribute(MESSAGE_ATTRIBUTE, SESSION_ERROR_MESSAGE);
        	RequestDispatcher requestDispatcher = request.getRequestDispatcher(MAIN_PAGE_PATH);
            requestDispatcher.forward(request, response);
		}
	}
}
