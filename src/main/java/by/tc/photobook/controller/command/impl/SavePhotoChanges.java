package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class SavePhotoChanges implements Command 
{
	private static String NEW_PROFILE_PIC_PARAM = "new_profile_pic";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String IMAGE_FOLDER = "images/";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String newProfilePicPath = request.getParameter(NEW_PROFILE_PIC_PARAM);
		newProfilePicPath = IMAGE_FOLDER + newProfilePicPath;
		
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null || 
				session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        UserService userService = serviceProvider.getUserService();
	        
	        try
	        {
	        	userService.updateProfilePic(username, newProfilePicPath);
	        	user.setProfilePicPath(newProfilePicPath);
	        	response.sendRedirect(LOAD_PROFILE_PAGE);
	        }
	        catch(ServiceException e)
	        {
	        	response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+e.getDescription());
	        }
		}
	}
}
