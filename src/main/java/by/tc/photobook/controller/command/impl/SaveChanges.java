package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;

public class SaveChanges implements Command
{
	private static String NEW_PROFILE_DESC_PARAM = "new_profile_desc";
	private static String NEW_PROFILE_PIC_PARAM = "new_profile_pic";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_MAIN_PAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PROFILE_PAGE = "Controller?command=loadprofilepage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String newProfileDesc = request.getParameter(NEW_PROFILE_DESC_PARAM);
		String newProfilePicPath = request.getParameter(NEW_PROFILE_PIC_PARAM);
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
	        UserService userService = serviceProvider.getUserService();
	        
	        boolean updated = false;
	        try
	        {
	        	updated = userService.updateProfileDesc(username, newProfileDesc);
	        	if(!updated)
	        	{
	        		response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
	        	}
	        	
	        	user.setProfileDecs(newProfileDesc);
	        	response.sendRedirect(LOAD_PROFILE_PAGE);
	        }
	        catch(ServiceException e)
	        {
	        	response.sendRedirect(LOAD_MAIN_PAGE + e.getMessage());
	        }
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
