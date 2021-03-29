package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.util.List;

import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoadPhotoshootsPage implements Command
{
	private static final String PHOTOSHOOT_PAGE_PATH = "/WEB-INF/jsp/photoshoot_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PH_OPTIONS_PARAM = "photoshoot_options";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String LOAD_MAIN_PAGE_WITH_ERROR = "Controller?command=loadmainpage&message=Session is expired!";
	private static final String LOAD_PROFILE_PAGE_WITH_MESSAGE = "Controller?command=loadpprofilepage&message=";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
		
		session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTOSHOOT_PAGE);
		session.setAttribute(PARAM_ATTRIBUTE, null);
		
		if(session.getAttribute(AUTH_ATTRIBUTE) != null && session.getAttribute(USER_ATTRIBUTE) != null)
		{
			UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
			List<PhotoshootOption> allOptions = null;
			
			try
			{
				allOptions = phOptionsService.getPhotoshootOptions(user.getId());
				request.setAttribute(PH_OPTIONS_PARAM, allOptions);
			}
			catch(ServiceException e)
			{
				request.setAttribute(MESSAGE_PARAM, e.getMessage());
			}
			finally
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PHOTOSHOOT_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
		else
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_ERROR);
		}
	}
}
