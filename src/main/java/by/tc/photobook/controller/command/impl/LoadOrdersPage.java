package by.tc.photobook.controller.command.impl;

import java.io.IOException;


import java.util.List;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class LoadOrdersPage implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String URL_ATTRIBUTE = "url";
	private static final String PARAM_ATTRIBUTE = "parameter";
	private static final String MESSAGE_PARAM = "message";
	private static final String ORDERS_PARAM = "orders";
	private static final String ORDERS_PAGE_PATH = "/WEB-INF/jsp/orders_page.jsp";
	private static final String USER_ATTRIBUTE = "user";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_ORDER_PAGE = "Controller?command=loadorderspage";
	private static final String LOAD_ORDER_PAGE_WITH_MESSAGE = "Controller?command=loadorderspage&message=";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		if(session == null || session.getAttribute(AUTH_ATTRIBUTE) == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String message = request.getParameter(MESSAGE_PARAM);
			if(message != null)
			{
				session.setAttribute(URL_ATTRIBUTE, LOAD_ORDER_PAGE_WITH_MESSAGE+message);
				request.setAttribute(MESSAGE_PARAM, message);
			}
			else
			{
				session.setAttribute(URL_ATTRIBUTE, LOAD_ORDER_PAGE);
			}
			session.setAttribute(PARAM_ATTRIBUTE, null);
			
			UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			OrdersService ordersService = serviceProvider.getOrdersService();
			
			try
			{
				List<Order> allOrders = ordersService.getOrdersByClient(user.getId());
				request.setAttribute(ORDERS_PARAM, allOrders);
			}
			catch(ServiceException e)
			{
				request.setAttribute(MESSAGE_PARAM, e.getDescription());
			}
			finally
			{
				RequestDispatcher requestDispatcher = request.getRequestDispatcher(ORDERS_PAGE_PATH);
				requestDispatcher.forward(request, response);
			}
		}
		
	}
}
