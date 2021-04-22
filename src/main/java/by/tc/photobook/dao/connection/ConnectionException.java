package by.tc.photobook.dao.connection;

/**
 * Class that handles exceptions in connection pool
 * 
 * @author Darya Minina
 */
public class ConnectionException extends Exception
{
	private static final long serialVersionUID = -744983591406056354L;

	public ConnectionException() {
		super();
	}
	
	public ConnectionException(String message) {
		super(message);
	}
	
	public ConnectionException(Exception e) {
		super(e);
	}
	
	public ConnectionException(String message, Exception e) {
		super(message, e);
	}
}
