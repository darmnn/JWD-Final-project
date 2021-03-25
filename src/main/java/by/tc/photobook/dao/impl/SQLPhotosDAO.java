package by.tc.photobook.dao.impl;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotosDAO implements PhotosDAO
{
	private static final Logger log = Logger.getLogger(SQLPhotosDAO.class);
	
	private static final String USER_NOT_FOUND_MESSAGE = "User is not found!";
	private static final String ERROR_WHILE_LOADING_MESSAGE = "Sorry! Server error while loading photos occured!";
	private static final String ERROR_WHILE_DELETING = "Sorry! Server error while deleting photo occured!";
	private static final String ERROR_WHILE_UPLOADING_MESSAGE = "Sorry! Server error while uploading new photo occured!";
	private static final String ERROR_WHILE_CLOSING_CONNECTION = "Error while closing connection: ";
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	private static final String UPDATE_RATING_ERROR = "Error while updating rating";
	
	private static final String GET_ALL_PHOTOS = "SELECT photos.photo_id, users.username, photos.date_time, photos.image, "
			+ "photos.rating FROM photos JOIN users ON users.id_user = photos.photographer";
	private static final String GET_USER_PHOTOS = "SELECT photos.photo_id, users.username, photos.date_time, photos.image, "
			+ "photos.rating FROM photos JOIN users ON users.id_user = photos.photographer WHERE users.username = ?";
	private static final String GET_USER_ID_BY_NAME = "SELECT id_user FROM users WHERE username=?";
	private static final String UPLOAD_NEW_PHOTO = "INSERT INTO photos(photographer, date_time, image, rating) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_PHOTO_RATING = "UPDATE photos SET rating=? WHERE photo_id=?";
	private static final String DELETE_PHOTO = "DELETE FROM photos WHERE photo_id= ?";

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
		} 
		catch (ConnectionException e) 
		{
			throw new DAOException(e);
		}
		
		try
		{
			preparedStatement = connection.prepareStatement(GET_ALL_PHOTOS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				allPhotos.add(new Photo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), 
						resultSet.getString(4), resultSet.getInt(5)));
			}
		}
		catch(SQLException e)
		{
			log.error(ERROR_WHILE_LOADING_MESSAGE + e.getMessage());
			throw new DAOException(ERROR_WHILE_LOADING_MESSAGE, e);
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
	
		return allPhotos;
	}
	
	@Override
	public List<Photo> takeUserPhotos(UserInfo user) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Photo> userPhotos = new ArrayList<Photo>();
		
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
			preparedStatement = connection.prepareStatement(GET_USER_PHOTOS);
			preparedStatement.setString(1, user.getUsername());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				userPhotos.add(new Photo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), 
						resultSet.getString(4), resultSet.getInt(5)));
			}
		}
		catch(SQLException e)
		{
			log.error(ERROR_WHILE_LOADING_MESSAGE + e.getMessage());
			throw new DAOException(ERROR_WHILE_LOADING_MESSAGE, e);
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
		
		return userPhotos;
	}
	
	public boolean addNewPhoto(Photo newPhoto) throws DAOException
	{
		String username = newPhoto.getPhotographer();
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
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
			preparedStatement = connection.prepareStatement(GET_USER_ID_BY_NAME);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			
			int userId;
			if(resultSet.next())
			{
				userId = resultSet.getInt(1);
			}
			else
			{
				throw new DAOException(USER_NOT_FOUND_MESSAGE);
			}
			
			
			preparedStatement = connection.prepareStatement(UPLOAD_NEW_PHOTO);
			preparedStatement.setInt(1, userId);
			preparedStatement.setDate(2, Date.valueOf(newPhoto.getDate()));
			preparedStatement.setString(3, newPhoto.getImagePath());
			preparedStatement.setInt(4, newPhoto.getRating());
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_UPLOADING_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			log.error(ERROR_WHILE_UPLOADING_MESSAGE + e.getMessage());
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
	
		return true;
	}
	
	public boolean updatePhotoRating(int photoId, int newRating) throws DAOException
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
			preparedStatement = connection.prepareStatement(UPDATE_PHOTO_RATING);
			preparedStatement.setInt(1, newRating);
			preparedStatement.setInt(2, photoId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(UPDATE_RATING_ERROR);
			}
		} 
		catch (SQLException e) 
		{
			log.error(UPDATE_RATING_ERROR + e.getMessage());
			throw new DAOException(e);
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
	
	public boolean deletePhoto(int photoId) throws DAOException
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
			preparedStatement = connection.prepareStatement(DELETE_PHOTO);
			preparedStatement.setInt(1, photoId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_DELETING);
			}
		}
		catch(SQLException e)
		{
			log.error(ERROR_WHILE_DELETING+e.getMessage());
			throw new DAOException(e);
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
