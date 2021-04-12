package by.tc.photobook.service;

public interface EmailService 
{
	public boolean sendEmail(String emailTo) throws ServiceException;
}
