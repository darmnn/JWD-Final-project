package by.tc.photobook.controller.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class ContextListener implements ServletContextListener
{
	public void contextInitialized(ServletContextEvent servletContextEvent)
	{
		try 
		{
			ConnectionPool.getInstance().init();
		} 
		catch (ConnectionException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void contextDestroyed(ServletContextEvent servletContextEvent)
	{
		try 
		{
			ConnectionPool.getInstance().destroy();
		}
		catch (ConnectionException e) 
		{
			e.printStackTrace();
		}
	}
}
