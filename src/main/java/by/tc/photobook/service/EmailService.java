package by.tc.photobook.service;

public interface EmailService 
{
	public boolean sendEmail(String emailTo, String emailFrom, String subject, String message, String password) throws ServiceException;
}
