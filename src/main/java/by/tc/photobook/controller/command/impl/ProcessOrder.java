package by.tc.photobook.controller.command.impl;

import java.io.IOException;



import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class ProcessOrder implements Command
{
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	private static final String ACTION_PARAM = "action";
	private static final String ORDER_PARAM = "order";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	
	public void execute(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		
		if(session == null)
		{
			response.sendRedirect(LOAD_MAIN_PAGE_WITH_MESSAGE+SESSION_EXPIRED_MESSAGE);
		}
		else
		{
			String action = request.getParameter(ACTION_PARAM);
			int orderId = Integer.parseInt(request.getParameter(ORDER_PARAM));
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			OrdersService orderService = serviceProvider.getOrdersService();
			
			try 
			{
				orderService.processOrder(orderId, action);
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
			} 
			catch (ServiceException e)
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
	}
}
