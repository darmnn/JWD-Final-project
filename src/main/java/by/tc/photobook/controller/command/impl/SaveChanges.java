package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;

public class SaveChanges implements Command
{
	private static String NEW_PROFILE_DESC_PARAM = "new_profile_desc";
	private static String NEW_PROFILE_PIC_PARAM = "new_profile_pic";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		String newProfileDesc = request.getParameter(NEW_PROFILE_DESC_PARAM);
		String newProfilePicPath = request.getParameter(NEW_PROFILE_PIC_PARAM);
		
		HttpSession session = request.getSession();
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo)session.getAttribute(USER_ATTRIBUTE);
			String username = user.getUsername();
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
