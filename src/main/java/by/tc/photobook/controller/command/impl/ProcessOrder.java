package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.EmailService;
import by.tc.photobook.service.OrdersService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import by.tc.photobook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

public class ProcessOrder implements Command
{
	private static final String LOAD_PHOTOSHOOT_PAGE = "Controller?command=loadphotoshootpage";
	private static final String LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE = "Controller?command=loadphotoshootpage&message=";
	private static final String ACTION_PARAM = "action";
	private static final String ORDER_PARAM = "order";
	private static final String CLIENT_PARAM = "client";
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
			UserService userService = serviceProvider.getUserService();
			EmailService emailService = serviceProvider.getEmailService();
			
			try 
			{
				orderService.processOrder(orderId, action);
				UserInfo client = userService.getInfoByUsername(request.getParameter(CLIENT_PARAM));
				emailService.sendEmail(client.getEmail());
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE);
			} 
			catch (ServiceException e)
			{
				response.sendRedirect(LOAD_PHOTOSHOOT_PAGE_WITH_MESSAGE+e.getDescription());
			}
		}
	}
}
