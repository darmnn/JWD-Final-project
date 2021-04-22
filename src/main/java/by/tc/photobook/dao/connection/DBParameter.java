package by.tc.photobook.dao.connection;

/**
 * Class that stores all parameters for connection to database
 * 
 * @author Darya Minina
 */
public class DBParameter 
{
	private DBParameter() {}
	
	public static final String DB_DRIVER = "db.driver";
	public static final String DB_URL = "db.url";
	public static final String DB_USER = "db.user";
	public static final String DB_PASSWORD = "db.password";
	public static final String DB_POOL_SIZE = "db.poolsize";
}
