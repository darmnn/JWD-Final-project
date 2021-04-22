package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.bean.Photo;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotosDAOTest 
{
	private static final String GET_USER_ID_BY_NAME = "SELECT id_user FROM users WHERE username=?";
	private static final String UPLOAD_NEW_PHOTO = "INSERT INTO photos(photographer, date_time, image, rating) VALUES(?, ?, ?, ?)";
	private static final String UPDATE_PHOTO_RATING = "UPDATE photos SET rating=? WHERE photo_id=?";
	
	@Test
	public void addNewPhotoTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		String username = "existingUser";
		Photo newPhoto = new Photo(username, LocalDate.now(), "images/4.jpg", 0);
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_ID_BY_NAME);
		preparedStatement.setString(1, username);
		ResultSet resultSet = preparedStatement.executeQuery();
		
		int userId = 0;
		while(resultSet.next())
			userId = resultSet.getInt(1);
		
		preparedStatement = connection.prepareStatement(UPLOAD_NEW_PHOTO);
		preparedStatement.setInt(1, userId);
		preparedStatement.setDate(2, Date.valueOf(newPhoto.getDate()));
		preparedStatement.setString(3, newPhoto.getImagePath());
		preparedStatement.setInt(4, newPhoto.getRating());
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void updatePhotoRating() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		int photoId = 40;
		int newRating = 5;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHOTO_RATING);
		preparedStatement.setInt(1, newRating);
		preparedStatement.setInt(2, photoId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
}
