package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import jakarta.servlet.http.*;
import jakarta.servlet.*;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class SaveChanges implements Command
{
	private static String NEW_PROFILE_DESC_PARAM = "new_profile_desc";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	private static final String NO_UPD_MESSAGE = "Error while updating profile description! Changes are not saved";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String newProfileDesc = request.getParameter(NEW_PROFILE_DESC_PARAM);
		
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        UserService userService = serviceProvider.getUserService();
	        
	        boolean updatedDesc = false;
	        try
	        {
	        	updatedDesc = userService.updateProfileDesc(username, newProfileDesc);
	        	if(!updatedDesc)
	        	{
	        		response.sendRedirect(LOAD_MAIN_PAGE+NO_UPD_MESSAGE);
	        	}
	        	
	        	user.setProfileDecs(newProfileDesc);
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