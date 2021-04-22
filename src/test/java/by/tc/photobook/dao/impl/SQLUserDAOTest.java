package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLUserDAOTest 
{
	private static final String ADD_NEW_USER = "INSERT INTO users (username, password, email, role) "
			+ "VALUES (?, ?, ?, ?)";
	private static final String FIND_USER = "SELECT users.id_user, users.username, users.password, users.email, "
			+ "user_roles.role, users.profile_desc, users.profile_pic, users.total_rating FROM users JOIN user_roles "
			+ "ON users.role=user_roles.id_role WHERE users.username = ? AND users.password = ?";
	private static final String UPDATE_PROFILE_DESC = "UPDATE users SET profile_desc = ? WHERE username = ?";
	private static int role_client = 1;
	private static int role_ph = 2;
	
	//adding new user test - user doesn't exist yet
	@Test
	public void registrationTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		UserInfo user = new UserInfo("newUser", "newUser@gmail.com", "user123", true);
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_USER);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		preparedStatement.setString(3, user.getEmail());
		
		if(user.getisPhotographer())
		{
			preparedStatement.setInt(4, role_ph);
		}
		else
		{
			preparedStatement.setInt(4, role_client);
		}
		
		Assert.assertTrue( preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	//authorization test - user already exists
	@Test
	public void authorizationTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		UserInfo user = new UserInfo("existingUser", "existingpassword456");
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(FIND_USER);
		preparedStatement.setString(1, user.getUsername());
		preparedStatement.setString(2, user.getPassword());
		ResultSet resultSet = preparedStatement.executeQuery();
		
		Assert.assertTrue(resultSet.next());
		connectionPool.destroy();
	}
	
	@Test
	public void updateProfileDescTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		String username = "existingUser";
		String newProfileDesc = "new profile description";
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PROFILE_DESC);
		preparedStatement.setString(1, newProfileDesc);
		preparedStatement.setString(2, username);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
