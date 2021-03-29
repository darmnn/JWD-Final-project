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
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
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
			throw new DAOException(e);
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
			throw new DAOException(e);
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
		return allTypes;
	}
}
