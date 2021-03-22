package by.tc.photobook.dao.impl;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLUserDAO implements UserDAO
{
	private static final Logger log = Logger.getLogger(SQLUserDAO.class);
	
	private static final String USER_ALREADY_EXISTS_ERROR = "User with this username already existst!";
	private static final String ERROR_WHILE_AUTH_MESSAGE = "Sorry! Server error while authorization occured, try again later.";
	private static final String ERROR_WHILE_UPD_MESSAGE = "Sorry! Server error occured, your changes are not saved!";
	private static final String ERROR_WHILE_CLOSING_CONNECTION = "Error while closing connection: ";
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private static final String ADD_NEW_USER = "INSERT INTO users (username, password, email, role) "
					+ "VALUES (?, ?, ?, ?)";
	private static final String FIND_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
	private static final String UPDATE_PROFILE_DESC = "UPDATE users SET profile_desc = ? WHERE username = ?";
	private static final String UPDATE_PROFILE_PIC = "UPDATE users SET profile_pic = ? WHERE username = ?";
	private static int role_client = 1; //to do - load from db
	private static int role_ph = 2;
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	@Override
	public boolean registration(UserInfo userInfo) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connection = connectionPool.getConnection();
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(ADD_NEW_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getEmail());
			
			if(userInfo.getisPhotographer())
			{
				preparedStatement.setInt(4, role_ph);
			}
			else
			{
				preparedStatement.setInt(4, role_client);
			}
			
			preparedStatement.executeUpdate();
		}
		catch(SQLException ex)
		{
			log.error(ex.getMessage());
			throw new DAOException(USER_ALREADY_EXISTS_ERROR, ex);
		}
		finally
		{
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
		
		return true;
	}
	
	@Override
	public UserInfo authorization(UserInfo userInfo) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserInfo authorizedUser = null;
		
		try 
		{
			connection = connectionPool.getConnection();
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(e);
		}
		
		try
		{	
			preparedStatement = connection.prepareStatement(FIND_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				int userRole = resultSet.getInt(5);
				
				if(userRole == 2)
				{
					authorizedUser = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), true, resultSet.getString(6), resultSet.getString(7), 
							resultSet.getInt(8));
				}
				else
				{
					authorizedUser = new UserInfo(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), false, resultSet.getString(6), resultSet.getString(7), null);
				}
			}
		}
		catch(SQLException e)
		{
			log.error(e.getMessage());
			throw new DAOException(ERROR_WHILE_AUTH_MESSAGE, e);
		}
		finally
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
		
		return authorizedUser;
	}
	
	@Override
	public boolean updateProfileDesc(String username, String newProfileDesc) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connection = connectionPool.getConnection();
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(e);
		}
		
		try
		{	
			preparedStatement = connection.prepareStatement(UPDATE_PROFILE_DESC);
			
			preparedStatement.setString(1, newProfileDesc);
			preparedStatement.setString(2, username);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_UPD_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			log.error(e.getMessage());
			throw new DAOException(ERROR_WHILE_UPD_MESSAGE, e);
		}
		finally
		{
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
		return true;
	}
	
	@Override
	public boolean updateProfilePic(String username, String newProfilePicPath) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try 
		{
			connection = connectionPool.getConnection();
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(e);
		}
		
		try
		{	
			preparedStatement = connection.prepareStatement(UPDATE_PROFILE_PIC);
			
			preparedStatement.setString(1, newProfilePicPath);
			preparedStatement.setString(2, username);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_UPD_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			log.error(e.getMessage());
			throw new DAOException(ERROR_WHILE_UPD_MESSAGE, e);
		}
		finally
		{
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
		return true;
	}
}
