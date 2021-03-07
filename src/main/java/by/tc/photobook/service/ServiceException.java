package by.tc.photobook.service;

public class ServiceException extends Exception
{
	private static final long serialVersionUID = -3549843709850795399L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String message) {
		super(message);
	}
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String message, Exception e) {
		super(message, e);
	}
}
