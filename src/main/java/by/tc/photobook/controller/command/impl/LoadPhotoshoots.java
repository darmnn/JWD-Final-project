package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.bean.Timetable;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Command displays a page with all the photoshoot options from a specific photographer
 * 
 * @author Darya Minina
 * @see Command
 */
public class LoadPhotoshoots implements Command
{
	private static final String PHOTOSHOOT_PAGE_PATH = "/WEB-INF/jsp/photoshoots.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PH_OPTIONS_PARAM = "photoshoot_options";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String PHOTOGRAPHER_PARAM = "photographer";
	private static final String ORDER_OPTION_PARAM = "option_to_order";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshoots&photographer=";
	private static final String TIMETABLE_PARAM = "timetable";
	private static final String DAYS_PARAM = "days";
	
	/**
	 * Executes the loading photoshoots page command
	 * 
	 * @param request  {@link HttpServletRequest}
	 * @param response {@link HttpServletResponse}
	 * @throws ServletException
	 * @throws IOException
	 */
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
			Integer photographer = Integer.parseInt(request.getParameter(PHOTOGRAPHER_PARAM));
			
			session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTOSHOOT_PAGE);
			session.setAttribute(PARAM_ATTRIBUTE, photographer.toString());
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
			OrdersService ordersService = serviceProvider.getOrdersService();
			List<PhotoshootOption> allOptions = null;
				
			try
			{
				allOptions = phOptionsService.getPhotoshootOptions(photographer);
				request.setAttribute(PH_OPTIONS_PARAM, allOptions);
				
				Timetable[] timetable = ordersService.getTimetable();
				ArrayList<HashMap<Integer, Order>> daysOfMonths = new ArrayList<HashMap<Integer, Order>>();
				for(int i = 0; i < timetable.length; i++)
				{
					daysOfMonths.add(ordersService.getBusyDaysOfMonth(photographer, timetable[i].getDate()));
				}
				
				request.setAttribute(DAYS_PARAM, daysOfMonths);
				request.setAttribute(TIMETABLE_PARAM, timetable);
			}
			catch(ServiceException e)
			{
				request.setAttribute(MESSAGE_PARAM, e.getDescription());
			}
			finally
			{
				String message = request.getParameter(MESSAGE_PARAM);
				if(message != null)
				{
					request.setAttribute(MESSAGE_PARAM, message);
				}
					
				String orderOption = request.getParameter(ORDER_OPTION_PARAM);
				if(orderOption != null)
				{
					request.setAttribute(ORDER_OPTION_PARAM, orderOption);
				}
		
				request.setAttribute(PHOTOGRAPHER_PARAM, photographer);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PHOTOSHOOT_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
	}
}
