package by.tc.photobook.controller.command.impl;

import java.io.IOException;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class SaveOrder implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PHOTOSHOOT_OPTION_PARAM = "photoshoot_option";
	private static final String PHOTOGRAPHER_PARAM = "photographer";
	private static final String DATE_PARAM = "date";
	private static final String DATE_FORMAT = "yyyy-MM-d";
	private static final String DATE_ERROR_MESSAGE = "message.invalid_date";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshoots&message=";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshoots&photographer=";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String PHOTOGRAPHER_ATTRIBUTE="&photographer=";
	
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null || 
				session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
			int photographer = Integer.parseInt(request.getParameter(PHOTOGRAPHER_PARAM));
			int photoshootOption = Integer.parseInt(request.getParameter(PHOTOSHOOT_OPTION_PARAM));
			String dateString  = request.getParameter(DATE_PARAM);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			LocalDate date = LocalDate.parse(dateString, formatter);
			
			if(date.isBefore(LocalDate.now()))
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+DATE_ERROR_MESSAGE+PHOTOGRAPHER_ATTRIBUTE+photographer);
			}
			else
			{
				ServiceProvider serviceProvider = ServiceProvider.getInstance();
				OrdersService ordersService = serviceProvider.getOrdersService();
				
				try 
				{
					ordersService.addOrder(user.getId(), photoshootOption, date);
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE+photographer);
				} 
				catch (ServiceException e) 
				{
					response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription()+PHOTOGRAPHER_ATTRIBUTE+photographer);
				}
			}
		}
	}
}
