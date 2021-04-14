package by.tc.photobook.service.impl;

import java.io.*;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import org.apache.log4j.Logger;

import by.tc.photobook.service.EmailResourceManager;
import by.tc.photobook.service.EmailService;
import by.tc.photobook.service.ServiceException;


public class EmailServiceImpl implements EmailService
{
	private final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private final String MAIL_SMTP_HOST = "mail.smtp.host";
	private final String MAIL_SMTP_PORT = "mail.smtp.port";
	private final String MAIL_USERNAME = "mail.username";
	private final String MAIL_PASSWORD = "mail.password";
	private final String MAIL_PROTOCOL = "mail.protocol";
	
	private static final EmailResourceManager emailResourceManager = EmailResourceManager.getInstance();
	private final static Logger log = Logger.getLogger(EmailServiceImpl.class);
	
	public boolean sendEmail(String emailTo) throws ServiceException
	{
		Properties props = new Properties();
		props.put(MAIL_PROTOCOL, emailResourceManager.getValue(MAIL_PROTOCOL));
		props.put(MAIL_SMTP_AUTH, emailResourceManager.getValue(MAIL_SMTP_AUTH));
		props.put(MAIL_SMTP_STARTTLS_ENABLE, emailResourceManager.getValue(MAIL_SMTP_STARTTLS_ENABLE));
		props.put(MAIL_SMTP_HOST, emailResourceManager.getValue(MAIL_SMTP_HOST));
		props.put(MAIL_SMTP_PORT, emailResourceManager.getValue(MAIL_SMTP_PORT));
        
		Session mailSession = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(mailSession);
		Transport transport = null;
		try
		{
			message.setFrom(new InternetAddress(emailResourceManager.getValue(MAIL_USERNAME)));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(emailTo));
		
			message.setSubject("Photobook order");
			message.setText("Your order was processed! Check out results in photobook");

			transport = mailSession.getTransport();
			transport.connect(emailResourceManager.getValue(MAIL_USERNAME), emailResourceManager.getValue(MAIL_PASSWORD));

			transport.sendMessage(message, message.getAllRecipients());
		}
		catch(MessagingException e)
		{
			log.error(e);
			throw new ServiceException("Error while sending email", e);
		}
		finally 
		{
			try 
			{
				if (transport != null) 
				{
					transport.close();
				}
			} 
			catch (MessagingException e) 
			{
				log.error("Error while closing mail transport", e);
			}
		}
		
		return true;
	}
}
