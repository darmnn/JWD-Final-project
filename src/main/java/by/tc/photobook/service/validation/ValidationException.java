package by.tc.photobook.service.validation;

public class ValidationException extends Exception
{
	private static final long serialVersionUID = -2438196178842268215L;
	private String description;

	public ValidationException() {
		super();
	}
	
	public ValidationException(String description) {
		super();
		this.description = description;
	}
	
	public ValidationException(Exception e) {
		super(e);
	}
	
	public ValidationException(String description, Exception e) {
		super(e);
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
}
