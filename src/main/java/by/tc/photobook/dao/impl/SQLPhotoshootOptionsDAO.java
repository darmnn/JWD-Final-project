package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.PhotoshootOption;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.PhotoshootOptionsDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotoshootOptionsDAO implements PhotoshootOptionsDAO
{
	private static final String GET_PHOTOSHOOT_OPTIONS = "SELECT photoshoot_options.id, users.username, "
			+ "photoshoot_types.type, photoshoot_options.price FROM photoshoot_options JOIN users on "
			+ "users.id_user=photoshoot_options.photographer JOIN photoshoot_types on "
			+ "photoshoot_types.id=photoshoot_options.photoshoot_type WHERE photographer=?";
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	private static final Logger log = Logger.getLogger(SQLPhotoshootOptionsDAO.class);
	
	public List<PhotoshootOption> getPhotoshootOptions(int photographer) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<PhotoshootOption> allOptions = new ArrayList<PhotoshootOption>();
		
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
			preparedStatement = connection.prepareStatement(GET_PHOTOSHOOT_OPTIONS);
			preparedStatement.setInt(1, photographer);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				allOptions.add(new PhotoshootOption(resultSet.getInt(1), resultSet.getString(2), 
						resultSet.getString(3) ,resultSet.getDouble(4)));
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
		
		return allOptions;
	}
}
