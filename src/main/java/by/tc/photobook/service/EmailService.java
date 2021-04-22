package by.tc.photobook.service;

/**
 * The interface to send emails to users
 * 
 * @author Darya Minina
 */
public interface EmailService 
{
	public boolean sendEmail(String emailTo) throws ServiceException;
}
