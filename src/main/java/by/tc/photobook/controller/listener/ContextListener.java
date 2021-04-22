package by.tc.photobook.controller.listener;


import org.apache.log4j.Logger;


import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;

/**
 * Context application listener
 * 
 * @author Darya Minina
 * @see ServletContextListener
 */
public class ContextListener implements ServletContextListener
{
	private static final Logger log = Logger.getLogger(ContextListener.class);
	
	/**
	 * Initializing connection pool when servlet context is initialized
	 * 
	 * @param servletContextEvent {@link ServletContextEvent}
	 * @throws RuntimeException if {@link ConnectionException} occurs
	 */
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try 
		{
			ConnectionPool.getInstance().init();
		} 
		catch (ConnectionException e) 
		{
			log.error(e);
		}
	}
	
	/**
	 * Destroying connection pool when servlet context is destroyed
	 * 
	 * @param servletContextEvent {@link ServletContextEvent}
	 * @throws RuntimeException if {@link ConnectionException} occurs
	 */
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		try 
		{
			ConnectionPool.getInstance().destroy();
		} 
		catch (ConnectionException e) 
		{
			log.error(e);
		}
	}
}
