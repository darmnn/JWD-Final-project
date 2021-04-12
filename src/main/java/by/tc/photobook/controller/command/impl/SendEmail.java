package by.tc.photobook.controller.command.impl;

import java.io.IOException;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.controller.command.Command;
import by.tc.photobook.service.EmailService;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.ServiceProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class SendEmail implements Command
{
	private static final String SESSION_EXPIRED_MESSAGE="message.session_expired";
	private static final String LOAD_MAIN_PAGE_WITH_MESSAGE = "Controller?command=loadmainpage&message=";
	private static final String AUTH_ATTRIBUTE = "auth";
	private static final String USER_ATTRIBUTE = "user";
	private static final String SUBJECT_PARAM = "subject";
	private static final String MAIL_PARAM = "message";
	private static final String PASSWORD_PARAM = "password";
	private static final String EMAIL_PARAM = "email_to";
	
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
			String emailTo = request.getParameter(MAIL_PARAM);
			String subject = request.getParameter(SUBJECT_PARAM);
			String message = request.getParameter(MAIL_PARAM);
			String password = request.getParameter(PASSWORD_PARAM);
			UserInfo user = (UserInfo) session.getAttribute(USER_ATTRIBUTE);
			
			ServiceProvider serviceProvider = ServiceProvider.getInstance();
			EmailService emailService = serviceProvider.getEmailService();
			try 
			{
				emailService.sendEmail(emailTo, user.getEmail(), subject, message, password);
			} 
			catch (ServiceException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
