package by.tc.photobook.service;

/**
 * Class that handles exceptions on service layer
 * 
 * @author Darya Minina
 */
public class ServiceException extends Exception
{
	private static final long serialVersionUID = -3549843709850795399L;
	private String description;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String description) {
		super();
		this.description = description;
	}
	
	public ServiceException(Exception e) {
		super(e);
	}
	
	public ServiceException(String description, Exception e) {
		super(e);
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
}
