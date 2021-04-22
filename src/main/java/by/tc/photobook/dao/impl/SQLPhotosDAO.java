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
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.PhotosDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

/**
 * The implementation of photos dao class that works with photos table in database
 * 
 * @author Darya Minina
 */
public class SQLPhotosDAO implements PhotosDAO
{
	private static final Logger log = Logger.getLogger(SQLPhotosDAO.class);
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	
	private static final String SERVER_ERROR = "message.server_error";
	private static final String ERROR_WHILE_UPLOADING_MESSAGE = "message.uplod_photo_error";
	private static final String ERROR_WHILE_LOADING_MESSAGE = "message.loading_photos_error";
	private static final String UPDATE_RATING_ERROR = "message.rating_error";
	private static final String ERROR_WHILE_DELETING = "message.error_while_deleting_photo";
	
	private static final String GET_ALL_PHOTOS = "SELECT photos.photo_id, users.username, photos.date_time, photos.image, "
			+ "photos.rating FROM photos JOIN users ON users.id_user = photos.photographer";
	private static final String GET_USER_PHOTOS = "SELECT photos.photo_id, users.username, photos.date_time, photos.image, "
			+ "photos.rating FROM photos JOIN users ON users.id_user = photos.photographer WHERE users.username = ?";
	private static final String GET_USER_ID_BY_NAME = "SELECT id_user FROM users WHERE username=?";
	private static final String UPLOAD_NEW_PHOTO = "INSERT INTO photos(photographer, date_time, image, rating) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_PHOTO_RATING = "UPDATE photos SET rating=? WHERE photo_id=?";
	private static final String DELETE_PHOTO = "DELETE FROM photos WHERE photo_id= ?";
	private static final String UPDATE_USER_RATING = "UPDATE users SET total_rating=? WHERE username=?";

	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	/**
	 * Takes all photos from all photographers in the app
	 * 
	 * @return list of photos {@link Photo}
	 * @throws DAOException
	 */
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
			throw new DAOException(SERVER_ERROR, e);
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
			log.error(e);
			throw new DAOException(ERROR_WHILE_LOADING_MESSAGE, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
	
		return allPhotos;
	}
	
	/**
	 * Takes all photos from one user
	 * 
	 * @param username username of the author of photos
	 * @return list of photos {@link Photo}
	 * @throws DAOException
	 */
	@Override
	public List<Photo> takeUserPhotos(String username) throws DAOException
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
			throw new DAOException(SERVER_ERROR, e);
		}
			
		try
		{
			preparedStatement = connection.prepareStatement(GET_USER_PHOTOS);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				userPhotos.add(new Photo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3).toLocalDate(), 
						resultSet.getString(4), resultSet.getInt(5)));
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_LOADING_MESSAGE, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return userPhotos;
	}
	
	/**
	 * Loads new photo
	 * 
	 * @param newPhoto {@link Photo} the description of the new photo
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
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
			throw new DAOException(SERVER_ERROR, e);
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
				throw new DAOException(ERROR_WHILE_UPLOADING_MESSAGE);
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
			log.error(e);
			throw new DAOException(ERROR_WHILE_UPLOADING_MESSAGE, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
	
		return true;
	}
	
	/**
	 * Updates photo's rating when user rates it
	 * 
	 * @param photoId the id of the photo to rate
	 * @param newRating the result rating of the photo
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
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
			throw new DAOException(SERVER_ERROR, e);
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
			log.error(e);
			throw new DAOException(UPDATE_RATING_ERROR, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Deletes a photo
	 * 
	 * @param photoId the id of the photo to delete
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
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
			throw new DAOException(SERVER_ERROR, e);
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
			log.error(e);
			throw new DAOException(ERROR_WHILE_DELETING, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Updates user's total rating according to the rates of all his photos
	 * 
	 * @param username the username of the user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean updateUserRating(String username) throws DAOException
	{
		List<Photo> allUserPhotos = takeUserPhotos(username);
		if(allUserPhotos != null && !allUserPhotos.isEmpty())
		{
			int[] ratingValues = new int[allUserPhotos.size()];
			int sum = 0;
			
			for(int i = 0; i < ratingValues.length; i++)
			{
				ratingValues[i] = allUserPhotos.get(i).getRating();
				sum += ratingValues[i];
			}
			
			int averageUserRating = sum/ratingValues.length;
			
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
				preparedStatement = connection.prepareStatement(UPDATE_USER_RATING);
				preparedStatement.setInt(1, averageUserRating);
				preparedStatement.setString(2, username);
				
				if(preparedStatement.executeUpdate() < 1)
				{
					throw new DAOException(UPDATE_RATING_ERROR);
				}
			} 
			catch (SQLException e) 
			{
				log.error(e);
				throw new DAOException(SERVER_ERROR, e);
			}
			finally
			{
				closeAll(null, preparedStatement, connection);
			}
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
