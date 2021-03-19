package by.tc.photobook.dao.connection;

import java.sql.Connection;



import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public final class ConnectionPool 
{
	private static final Logger log = Logger.getLogger(ConnectionPool.class);
	
	private static final String TAKING_CONNECTION_ERROR_MESSAGE = "Error while taking connection!";
	private static final String CREATION_ERROR = "Error while creating connection pool: ";
	private static final String INIT_ERROR = "Error while initializing connection pool: ";
	
	private String driver;
	private String url;
	private String user;
	private String password;
	private int poolSize;
	
	private BlockingQueue<Connection> connectionQueue;
	private BlockingQueue<Connection> usedConnections;
	
	private static ConnectionPool instance = null;
	
	static
	{
		try
		{
			instance = new ConnectionPool();
		}
		catch(ConnectionException e)
		{
			log.error(CREATION_ERROR + e);
		}
	}
	
	private ConnectionPool() throws ConnectionException
	{
		DBResourceManager dbResourceManager = DBResourceManager.getInstance();
		this.driver = dbResourceManager.getValue(DBParameter.DB_DRIVER);
		this.url = dbResourceManager.getValue(DBParameter.DB_URL);
		this.user = dbResourceManager.getValue(DBParameter.DB_USER);
		this.password = dbResourceManager.getValue(DBParameter.DB_PASSWORD);
		this.poolSize = Integer.parseInt(dbResourceManager.getValue(DBParameter.DB_POOL_SIZE));
	}
	
	public void init() throws ConnectionException
	{
		try
		{
			Class.forName(driver);
			connectionQueue = new ArrayBlockingQueue<Connection>(poolSize);
			usedConnections = new ArrayBlockingQueue<Connection>(poolSize);
			
			for(int i = 0; i < poolSize; i++)
			{
				Connection connection = DriverManager.getConnection(url, user, password);
				connectionQueue.add(connection);
			}
		}
		catch(ClassNotFoundException | SQLException e)
		{
			log.error(INIT_ERROR + e);
			throw new ConnectionException(e);
		}
	}
	
	public static ConnectionPool getInstance()
	{
		return instance;
	}
	
	public Connection getConnection() throws ConnectionException
	{
		Connection connection = null;
		
		try 
		{
            connection = connectionQueue.take();
            usedConnections.add(connection);
        } 
		catch (InterruptedException e) 
		{
			log.error(TAKING_CONNECTION_ERROR_MESSAGE + e);
			throw new ConnectionException(TAKING_CONNECTION_ERROR_MESSAGE, e);
        }
        return connection;
	}
	
	public boolean releaseConnection(Connection connection)
	{
		if(connection != null)
		{
			usedConnections.remove(connection);
			return connectionQueue.add(connection);
		}
		
		return false;
	}
	
	public void destroy() throws ConnectionException
	{
		//to do
	}
}
