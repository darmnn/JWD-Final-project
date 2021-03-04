package by.tc.photobook.dao.impl;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DriverLoader;
import by.tc.photobook.dao.UserDAO;

public class SQLUserDAO implements UserDAO
{
	private static final String ADD_NEW_USER = "INSERT INTO users (username, password, email, role) "
					+ "VALUES (?, ?, ?, ?)";
	private static final String FIND_USER = "SELECT * FROM users WHERE username = ? AND password = ?";
	private static int role_client = 1; //to do - load from db
	private static int role_ph = 2;
	
	static
	{
		DriverLoader.getInstance();
	}
	
	@Override
	public boolean registration(UserInfo userInfo)
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/photobook_db", "root", "Snow877#");
			
			preparedStatement = connection.prepareStatement(ADD_NEW_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getEmail());
			if(userInfo.isPhotographer())
			{
				preparedStatement.setInt(4, role_ph);
			}
			else
			{
				preparedStatement.setInt(4, role_client);
			}
			preparedStatement.executeUpdate();
			
			//statement = connection.createStatement();
			//int countRaws = statement.executeUpdate("INSERT INTO users (username, password, email, role) "
			//		+ "VALUES (\"vasya\", \"123\", \"aaa@mail.ru\", 2)");
			//System.out.print(countRaws);
			
			/*statement = connection.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM users WHERE username = \'vasya\' AND password"
					+ "=\'123\'");
			while(resultSet.next())
			{
				System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " +
						resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getInt(5));
			}*/
			
		}
		catch(SQLException e)
		{
			System.out.print("connection error!!!!!!!" + e);
		}
		finally
		{
			if(preparedStatement != null)
			{
				try {
					preparedStatement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(connection != null)
			{
				
				try
				{
					connection.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}
	
	@Override
	public boolean authorization(UserInfo userInfo)
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try
		{
			connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/photobook_db", "root", "Snow877#");
			
			preparedStatement = connection.prepareStatement(FIND_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				System.out.print(resultSet.getInt(1) + " " + resultSet.getString(2) + " " +
						resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getInt(5));
			}
		}
		catch(SQLException e)
		{
			System.out.print("connection error!!!!!!!" + e);
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
					// TODO Auto-generated catch block
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(connection != null)
			{
				try
				{
					connection.close();
				}
				catch(SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
		return true;
	}
}
