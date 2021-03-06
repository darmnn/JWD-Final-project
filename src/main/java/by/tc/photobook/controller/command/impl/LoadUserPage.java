package by.tc.photobook.controller.command.impl;

import java.io.IOException;


import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotosService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

/**
 * Command displays a page with all the information about one user by his username
 * 
 * @author Darya Minina
 * @see Command
 */
public class LoadUserPage implements Command
{
	private static final String GET_USER_PARAM = "user";
	private static final String USER_PARAM = "this_user";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String PHOTOS_PARAM = "photos";
	private static final String LOAD_USER_PAGE = "Controller?command=loaduserpage&user=";
	private static final String USER_PAGE_PATH = "/WEB-INF/jsp/user_page.jsp";
	private static final String NO_PHOTOS_MESSAGE = "message.no_photos";
	
	/**
	 * Executes the loading user page command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		String username = request.getParameter(GET_USER_PARAM);
		HttpSession session = request.getSession(true);
		session.setAttribute(URL_ATTRIBUTE, LOAD_USER_PAGE);
		session.setAttribute(PARAM_ATTRIBUTE, username);
		
		ServiceProvider serviceProvider = ServiceProvider.getInstance();
		UserService userService = serviceProvider.getUserService();
		
		try 
		{
			UserInfo user = userService.getInfoByUsername(username);
			request.setAttribute(USER_PARAM, user);
			
			if(user.getisPhotographer())
			{
				PhotosService photosService = serviceProvider.getPhotosService();
				List<Photo> userPhotos = null;
				userPhotos = photosService.takeUserPhotos(user.getUsername());
				if(userPhotos == null || userPhotos.isEmpty())
				{
					request.setAttribute(MESSAGE_PARAM, NO_PHOTOS_MESSAGE);
				}
				
				request.setAttribute(PHOTOS_PARAM, userPhotos);	
			}
		} 
		catch (ServiceException e) 
		{
			request.setAttribute(MESSAGE_PARAM, e.getDescription());
		}
		finally
		{
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(USER_PAGE_PATH);
			requestDispatcher.forward(request, response);
		}
	}
}
