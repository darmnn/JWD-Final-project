package by.tc.photobook.dao;

public class DAOException extends Exception
{
	private static final long serialVersionUID = -5026629693456812271L;

	public DAOException() {
		super();
	}
	
	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(Exception e) {
		super(e);
	}
	
	public DAOException(String message, Exception e) {
		super(message, e);
	}
}
