package by.tc.photobook.dao;

/**
 * Class that handles exceptions on DAO layer
 * 
 * @author Darya Minina
 */
public class DAOException extends Exception
{
	private static final long serialVersionUID = -5026629693456812271L;
	private String description;

	public DAOException() {
		super();
	}
	
	public DAOException(String description) {
		super();
		this.description = description;
	}
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(String description, Exception e) {
		super(e);
		this.description = description;
	}
	
	public String getDescription()
	{
		return description;
	}
}
