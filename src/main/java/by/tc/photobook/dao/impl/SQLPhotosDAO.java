package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotosDAO implements PhotosDAO
{
	private static final String GET_ALL_PHOTOS = "SELECT * FROM photos";
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	@Override
	public List<Photo> takeAll() throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Photo> allPhotos = new ArrayList<Photo>();
		
		try
		{
			connection = connectionPool.getConnection();
			preparedStatement = connection.prepareStatement(GET_ALL_PHOTOS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				allPhotos.add(new Photo(resultSet.getString(4)));
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
		
		
		return allPhotos;
	}
}
