package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLOrdersDAOTest 
{
	private static final String ADD_ORDER = "INSERT INTO orders(client_id, photoshoot_option, date_time, status) "
			+ "VALUES(?, ?, ?, 1)";
	private static final String PROCESS_ORDER = "UPDATE orders SET status=? WHERE order_id=?";
	private static final String ACCEPT = "accept";
	private static final int STATUS_ACCEPTED = 2;
	private static final int STATUS_DECLINED = 3;
	private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
	
	@Test
	public void addOrderTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		int userId = 1;
		int photoshootOption = 2;
		LocalDate date = LocalDate.now();
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(ADD_ORDER);
		preparedStatement.setInt(1, userId);
		preparedStatement.setInt(2, photoshootOption);
		preparedStatement.setDate(3, Date.valueOf(date));
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void processOrder() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		String action = "accept";
		int orderId = 2;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(PROCESS_ORDER);
		
		if(action.equals(ACCEPT))
		{
			preparedStatement.setInt(1, STATUS_ACCEPTED);
		}
		else
		{
			preparedStatement.setInt(1, STATUS_DECLINED);
		}
		
		preparedStatement.setInt(2, orderId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void cancelOrder() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		int orderId = 3;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER);
		preparedStatement.setInt(1, orderId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
