package by.tc.photobook.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;

import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

public class SQLPhotoshootOptionsDAOTest 
{
	private static final String ADD_NEW_PHOTOSHOOT = "INSERT INTO "
			+ "photoshoot_options(photographer, photoshoot_type, price) VALUES(?, ?, ?)";
	private static final String DELETE_PHOTOSHOOT_OPTION = "DELETE FROM photoshoot_options WHERE photoshoot_options.id=?";
	private static final String UPDATE_PHOTOSHOOT_OPTION = "UPDATE photoshoot_options SET photoshoot_type=?,"
			+ " price=? WHERE id=?";
	
	@Test
	public void addPhotoshootOptionTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		int photographerId = 1;
		int photoshootTypeID = 1;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_PHOTOSHOOT);
		preparedStatement.setInt(1, photographerId);
		preparedStatement.setInt(2, photoshootTypeID);
		preparedStatement.setDouble(3, 250);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void editPhotoshootTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		
		int photoshootOptionId = 2;
		int photoshootType = 3;
		double price = 110.5;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHOTOSHOOT_OPTION);
		preparedStatement.setInt(1, photoshootType);
		preparedStatement.setDouble(2, price);
		preparedStatement.setInt(3, photoshootOptionId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
	
	@Test
	public void deletePhotoshootTest() throws ConnectionException, SQLException
	{
		final ConnectionPool connectionPool = ConnectionPool.getInstance();
		connectionPool.init();
		int photoshootOptionId = 4;
		
		Connection connection = connectionPool.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PHOTOSHOOT_OPTION);
		preparedStatement.setInt(1, photoshootOptionId);
		
		Assert.assertTrue(preparedStatement.executeUpdate() == 1);
		connectionPool.destroy();
	}
}
