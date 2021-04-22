package by.tc.photobook.dao.connection;

import java.util.ResourceBundle;

/**
 * Class that gets all parameters needed to connect to database from resource bundle
 * 
 * @author Darya Minina
 */
public class DBResourceManager 
{
	private static final DBResourceManager instance = new DBResourceManager();
	
	private ResourceBundle bundle = ResourceBundle.getBundle("db");
	
	public static DBResourceManager getInstance()
	{
		return instance;
	}
	
	public String getValue(String key)
	{
		return bundle.getString(key);
	}
}
