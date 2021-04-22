package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.Order;
import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.OrdersDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

/**
 * The implementation of orders dao class that works with orders table in database
 * 
 * @author Darya Minina
 */
public class SQLOrdersDAO implements OrdersDAO 
{
	private static final Logger log = Logger.getLogger(SQLPhotosDAO.class);
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	private static final String ACCEPT = "accept";
	private static final int STATUS_ACCEPTED = 2;
	private static final int STATUS_DECLINED = 3;
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private static final String SERVER_ERROR = "message.server_error";
	private static final String ERROR_WHILE_ADDING_ORDER = "message.error_while_adding_order";
	private static final String ERROR_WHILE_PROCESSING_ORDER = "message.error_while_processing_order";
	private static final String ERROR_WHILE_DELETING_ORDER = "message.error_while_canceling_order";
	
	private static final String ADD_ORDER = "INSERT INTO orders(client_id, photoshoot_option, date_time, status) "
			+ "VALUES(?, ?, ?, 1)";
	private static final String GET_ORDERS_BY_PHOTOGRAPHER = "SELECT orders.order_id, users.username, orders.photoshoot_option, "
			+ "photoshoot_options.photographer, photoshoot_types.type, photoshoot_options.price, orders.date_time, "
			+ "order_statuses.status FROM orders JOIN order_statuses ON orders.status=order_statuses.id_status "
			+ "JOIN users ON orders.client_id=users.id_user JOIN photoshoot_options ON "
			+ "orders.photoshoot_option=photoshoot_options.id JOIN photoshoot_types ON "
			+ "photoshoot_options.photoshoot_type=photoshoot_types.id WHERE photoshoot_options.photographer=?";
	private static final String GET_ORDERS_BY_CLIENT = "SELECT orders.order_id, orders.photoshoot_option, "
			+ "users.username, photoshoot_types.type, photoshoot_options.price, orders.date_time, "
			+ "order_statuses.status FROM orders JOIN order_statuses ON orders.status=order_statuses.id_status JOIN "
			+ "photoshoot_options ON orders.photoshoot_option=photoshoot_options.id JOIN users ON "
			+ "photoshoot_options.photographer=users.id_user JOIN photoshoot_types ON "
			+ "photoshoot_options.photoshoot_type=photoshoot_types.id WHERE orders.client_id=?";
	private static final String PROCESS_ORDER = "UPDATE orders SET status=? WHERE order_id=?";
	private static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id=?";
	
	/**
	 * Add one order to user's orders
	 * 
	 * @param user user who makes an order
	 * @param photoshootOption the id of the photoshoot option to order
	 * @param date {@link LocalDate} date of the order
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean addOrder(int user, int photoshootOption, LocalDate date) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(ADD_ORDER);
			preparedStatement.setInt(1, user);
			preparedStatement.setInt(2, photoshootOption);
			preparedStatement.setDate(3, Date.valueOf(date));
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_ADDING_ORDER);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_ADDING_ORDER, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Takes all photographer's orders
	 * 
	 * @param photographerId the id of the photographer
	 * @return list of the orders {@link Order}
	 * @throws DAOException
	 */
	public List<Order> getOrdersByPhotographer(int photographerId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		List<Order> allOrders = new ArrayList<Order>();
		
		try 
		{
			preparedStatement = connection.prepareStatement(GET_ORDERS_BY_PHOTOGRAPHER);
			preparedStatement.setInt(1, photographerId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				PhotoshootOption phOption = new PhotoshootOption(resultSet.getInt(3), null, resultSet.getString(5), 
						resultSet.getDouble(6));
				Order order = new Order(resultSet.getInt(1), resultSet.getString(2), phOption, 
						resultSet.getDate(7).toLocalDate(), resultSet.getString(8));
				allOrders.add(order);
			}
		} 
		catch (SQLException e)
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return allOrders;
	}
	
	/**
	 * Takes all client's orders
	 * 
	 * @param clientId the id of the client
	 * @return list of the orders {@link Order}
	 * @throws DAOException
	 */
	public boolean processOrder(int orderId, String action) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(PROCESS_ORDER);
			
			if(action.equals(ACCEPT))
			{
				preparedStatement.setInt(1, STATUS_ACCEPTED);
			}
			else
			{
				preparedStatement.setInt(1, STATUS_DECLINED);
			}
			
			preparedStatement.setInt(2, orderId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_PROCESSING_ORDER);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_PROCESSING_ORDER, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Marks an order as declined or accepted depending on the action parameter
	 * 
	 * @param orderId the id of the order to process
	 * @param action decline or accept
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public List<Order> getOrdersByClient(int clientId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		List<Order> allOrders = new ArrayList<Order>();
		
		try
		{
			preparedStatement = connection.prepareStatement(GET_ORDERS_BY_CLIENT);
			preparedStatement.setInt(1, clientId);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				PhotoshootOption phOption = new PhotoshootOption(resultSet.getInt(2), resultSet.getString(3), 
						resultSet.getString(4), resultSet.getDouble(5));
				Order order = new Order(resultSet.getInt(1), null, phOption, 
						resultSet.getDate(6).toLocalDate(), resultSet.getString(7));
				allOrders.add(order);
			}
			
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return allOrders;
	}
	
	/**
	 * Cancels user's order
	 * 
	 * @param orderId the id of the order to cancel
	 * @return true if the operation was successful
	 * @throws DAOException
	 */
	public boolean cancelOrder(int orderId) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			 connection = connectionPool.getConnection();
		}
		catch (ConnectionException e) 
		{
			throw new DAOException(SERVER_ERROR, e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(DELETE_ORDER);
			preparedStatement.setInt(1, orderId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_DELETING_ORDER);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_DELETING_ORDER, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Closes result set, prepared statement and connection
	 * 
	 * @param resultSet {@link ResultSet}
	 * @param preparedStatement {@link PreparedStatement}
	 * @param connection {@link Connection}
	 * @return true if the operation was successful
	 */
	private void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Connection connection)
	{
		if(resultSet != null)
		{
			try 
			{
				resultSet.close();
			} 
			catch (SQLException e) 
			{
				log.error(ERROR_WHILE_CLOSING_RESULTSET + e.getMessage());
			}
		}
		
		if(preparedStatement != null)
		{
			try 
			{
				preparedStatement.close();
			} 
			catch (SQLException e) 
			{
				log.error(ERROR_WHILE_CLOSING_STATEMENT + e.getMessage());
			}
		}
		
		if(connection != null)
		{
			connectionPool.releaseConnection(connection);
		}
	}
}
