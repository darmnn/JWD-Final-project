package by.tc.photobook.dao.impl;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.tc.photobook.bean.ClientInfo;
import by.tc.photobook.bean.PhotographerInfo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLUserDAO implements UserDAO
{
	private static final String ADD_NEW_USER = "INSERT INTO users (username, password, email, role) "
					+ "VALUES (?, ?, ?, ?)";
	private static final String FIND_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
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
			
			preparedStatement = connection.prepareStatement(ADD_NEW_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getEmail());
			if(userInfo.isPhotographer())
			{
				preparedStatement.setInt(4, role_ph);
			}
			else
			{
				preparedStatement.setInt(4, role_client);
			}
			
			try
			{
				preparedStatement.executeUpdate();
			}
			catch(SQLException ex)
			{
				throw new DAOException("User with this username already existst!");
			}
			
		}
		catch(SQLException e)
		{
			throw new DAOException("Connection error: " + e.getMessage());
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
					e.printStackTrace();
				}
			}
			if(connection != null)
			{
				
				try
				{
					connection.close();
					connectionPool.releaseConnection(connection);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
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
			
			preparedStatement = connection.prepareStatement(FIND_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				int userRole = resultSet.getInt(5);
				
				if(userRole == 2)
				{
					authorizedUser = new PhotographerInfo(resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), true, resultSet.getString(6), resultSet.getString(7), 
							resultSet.getInt(8));
				}
				else
				{
					authorizedUser = new ClientInfo(resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), false, resultSet.getString(6), resultSet.getString(7));
				}
			}
		}
		catch(SQLException e)
		{
			throw new DAOException("Connection error: " + e.getMessage());
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
					e.printStackTrace();
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
					e.printStackTrace();
				}
			}
			
			if(connection != null)
			{
				try
				{
					connection.close();
					connectionPool.releaseConnection(connection);
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return authorizedUser;
	}
}
