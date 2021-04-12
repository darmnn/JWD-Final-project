package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.PhotoshootType;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.PhotoshootTypesDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotoshootTypesDAO implements PhotoshootTypesDAO
{
	private static final String GET_ALL_PHOTOSHOOT_TYPES = "SELECT * FROM photoshoot_types";
	private static final String ADD_NEW_PHOTOSHOOT_TYPE = "INSERT INTO photoshoot_types(type) VALUES(?)";
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	private static final String ERROR_WHILE_ADDING = "message.error_adding_type";
	private static final String SERVER_ERROR = "message.server_error";
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static final Logger log = Logger.getLogger(SQLPhotoshootOptionsDAO.class);
	
	public List<PhotoshootType> takeAll() throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<PhotoshootType> allTypes = new ArrayList<PhotoshootType>();
		
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
			preparedStatement = connection.prepareStatement(GET_ALL_PHOTOSHOOT_TYPES);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				allTypes.add(new PhotoshootType(resultSet.getInt(1), resultSet.getString(2)));
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
		return allTypes;
	}
	
	public boolean addNewType(String type) throws DAOException
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
			preparedStatement = connection.prepareStatement(ADD_NEW_PHOTOSHOOT_TYPE);
			preparedStatement.setString(1, type);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_ADDING);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_ADDING, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
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
