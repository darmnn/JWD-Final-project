package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotoshootTypesDAOTest 
{
	private static final String ADD_NEW_PHOTOSHOOT_TYPE = "INSERT INTO photoshoot_types(type) VALUES(?)";
	
	@Test
	public void addnewType() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		String type = "new type";
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_PHOTOSHOOT_TYPE);
		preparedStatement.setString(1, type);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
