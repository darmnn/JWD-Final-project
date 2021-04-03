package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class CancelOrder implements Command
{
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String ORDER_PARAM = "order";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
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
			int orderId = Integer.parseInt(request.getParameter(ORDER_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			OrdersService ordersService = serviceProvider.getOrdersService();
			
			try 
			{
				ordersService.cancelOrder(orderId);
				response.sendRedirect(LOAD_ORDER_PAGE);
			} 
			catch (ServiceException e) 
			{
				response.sendRedirect(LOAD_ORDER_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
		
		
	}
}
