package by.tc.photobook.controller.command.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.bean.PhotoshootType;
import by.tc.photobook.bean.Timetable;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.PhotoshootOptionsService;
import by.tc.photobook.service.PhotoshootTypesService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

/**
 * Command displays a page with all the photoshoot options by an authorized user if the user is photographer
 * 
 * @author Darya Minina
 * @see Command
 */
public class LoadPhotoshootsPage implements Command
{
	private static final String PHOTOSHOOT_PAGE_PATH = "/WEB-INF/jsp/photoshoot_page.jsp";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String PH_OPTIONS_PARAM = "photoshoot_options";
	private static final String URL_ATTRIBUTE = "url";
	private static final String ADD_PHOTOSHOOT_ATTRIBUTE = "add_photoshoot";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String PHOTOSHOOT_TYPES_ATTR = "photoshoot_types";
	private static final String ORDERS_ATTRIBUTE = "orders";
	private static final String MESSAGE_PARAM = "message";
	private static final String PHOTOSHOOT_OPTION_PARAM = "option_to_edit";
	private static final String ADD_TYPE_PARAM = "add_type";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
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
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null 
				|| session.getAttribute(USER_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String message = request.getParameter(MESSAGE_PARAM);
			if(message != null)
			{
				session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+message);
			}
			else
			{
				session.setAttribute(URL_ATTRIBUTE, LOAD_PHOTOSHOOT_PAGE);
			}
			session.setAttribute(PARAM_ATTRIBUTE, null);
			
			UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			PhotoshootOptionsService phOptionsService = serviceProvider.getPhotoshootOptionsService();
			OrdersService ordersService = serviceProvider.getOrdersService();
			List<PhotoshootOption> allOptions = null;
			List<Order> allOrders = null;
			
			try
			{
				allOptions = phOptionsService.getPhotoshootOptions(user.getId());
				request.setAttribute(PH_OPTIONS_PARAM, allOptions);
				
				if(request.getParameter(ADD_PHOTOSHOOT_ATTRIBUTE) != null || request.getParameter(PHOTOSHOOT_OPTION_PARAM) != null)
				{
					PhotoshootTypesService phTypesService = serviceProvider.getPhotoshootTypesService();
					List<PhotoshootType> allTypes = phTypesService.takeAll();
					request.setAttribute(PHOTOSHOOT_TYPES_ATTR, allTypes);
				}
				
				allOrders = ordersService.getOrdersByPhotographer(user.getId());
				request.setAttribute(ORDERS_ATTRIBUTE, allOrders);
				
				Timetable[] timetable = ordersService.getTimetable();
				ArrayList<HashMap<Integer, Order>> daysOfMonths = new ArrayList<HashMap<Integer, Order>>();
				for(int i = 0; i < timetable.length; i++)
				{
					daysOfMonths.add(ordersService.getBusyDaysOfMonth(user.getId(), timetable[i].getDate()));
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
				if(request.getParameter(ADD_PHOTOSHOOT_ATTRIBUTE) != null)
				{
					request.setAttribute(ADD_PHOTOSHOOT_ATTRIBUTE, true);
				}
				
				if(request.getParameter(PHOTOSHOOT_OPTION_PARAM) != null)
				{
					String phOptionToEdit = request.getParameter(PHOTOSHOOT_OPTION_PARAM);
					request.setAttribute(PHOTOSHOOT_OPTION_PARAM, phOptionToEdit);
				}
				
				if(request.getParameter(ADD_TYPE_PARAM) != null)
				{
					request.setAttribute(ADD_TYPE_PARAM, true);
				}
				
				request.setAttribute(MESSAGE_PARAM, message);
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(PHOTOSHOOT_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
	}
}
