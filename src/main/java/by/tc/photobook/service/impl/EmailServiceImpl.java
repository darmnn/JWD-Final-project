package by.tc.photobook.service.impl;

import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import by.tc.photobook.service.EmailService;
import by.tc.photobook.service.ServiceException;


public class EmailServiceImpl implements EmailService
{
	public boolean sendEmail(String emailTo, final String emailFrom, String subject, String message, final String password) throws ServiceException
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");		
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        Session session = Session.getInstance(props,new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
  	 	         return new PasswordAuthentication("darya.minina.11@gmail.com", "Snow877#"); 
            }
        });

        try {
 
     	      /*  Create an instance of MimeMessage, 
     	          it accept MIME types and headers 
     	      */
     
            MimeMessage emailMessage = new MimeMessage(session);
            emailMessage.setFrom(new InternetAddress("darya.minina.11@gmail.com"));
            emailMessage.addRecipient(Message.RecipientType.TO,new InternetAddress("darmnn@yandex.com"));
            emailMessage.setSubject(subject);
            emailMessage.setText(message);

            /* Transport class is used to deliver the message to the recipients */
           
            Transport.send(emailMessage);
        }
        catch(Exception e) 
        {
    	     throw new ServiceException("Error while sending email!", e);
        }
        
		return true;
	}
}
