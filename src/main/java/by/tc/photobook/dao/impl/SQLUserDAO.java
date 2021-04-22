package by.tc.photobook.dao.impl;

import java.sql.Connection;




import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.dao.connection.ConnectionException;
import by.tc.photobook.dao.connection.ConnectionPool;

/**
 * The implementation of user dao class that works with users table in database
 * 
 * @author Darya Minina
 */
public class SQLUserDAO implements UserDAO
{
	private static final Logger log = Logger.getLogger(SQLUserDAO.class);
	
	private static final String ERROR_WHILE_CLOSING_STATEMENT = "Error while closing statement: ";
	private static final String ERROR_WHILE_CLOSING_RESULTSET = "Error while closing result set: ";
	private static final String ERROR_WHILE_FINDING_USER = "Error while finding user! ";
	
	private static final String SERVER_ERROR = "message.server_error";
	private static final String NO_SUCH_USER = "message.no_such_user";
	private static final String USER_ALREADY_EXISTS_ERROR = "message.user_already_exists";
	private static final String ERROR_WHILE_UPD_MESSAGE = "message.updating_profile_error";
	private static final String ERROR_WHILE_UNLOCK="message.error_unlocking";
	private static final String ERROR_WHILE_BLOCK="message.error_blocking";
	private static final String YOU_ARE_BLOCKED = "message.you_are_blocked";
	private static final String USER_BLOCKED = "message.user_blocked";
	
	private static final String ADD_NEW_USER = "INSERT INTO users (username, password, email, role) "
					+ "VALUES (?, ?, ?, ?)";
	private static final String FIND_USER = "SELECT users.id_user, users.username, users.password, users.email, "
			+ "user_roles.role, users.profile_desc, users.profile_pic, users.total_rating, users.state FROM users JOIN user_roles "
			+ "ON users.role=user_roles.id_role WHERE users.username = ? AND users.password = ?";
	private static final String UPDATE_PROFILE_DESC = "UPDATE users SET profile_desc = ? WHERE username = ?";
	private static final String UPDATE_PROFILE_PIC = "UPDATE users SET profile_pic = ? WHERE username = ?";
	private static final String FIND_USER_BY_USERNAME = "SELECT users.id_user, users.username, users.password, "
			+ "users.email, user_roles.role, users.profile_desc, users.profile_pic, users.total_rating, users.state FROM users "
			+ "JOIN user_roles ON users.role=user_roles.id_role WHERE username=?";
	private static final String GET_ALL_USERS = "SELECT users.id_user, users.username, users.password, users.email,"
			+ " user_roles.role, users.profile_desc, users.profile_pic, users.total_rating, users.state FROM users JOIN user_roles"
			+ " ON users.role=user_roles.id_role";
	private static final String BLOCK_UNLOCK = "UPDATE users SET state=? WHERE id_user=?";
	
	private static final String ROLE_PHOTOGRAPHER = "фотограф";
	private static final String ROLE_ADMIN = "администратор";
	private static int role_client = 1;
	private static int role_ph = 2;
	
	private final ConnectionPool connectionPool = ConnectionPool.getInstance();
	
	/**
	 * Adds a new user
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	@Override
	public boolean registration(UserInfo userInfo) throws DAOException
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
			preparedStatement = connection.prepareStatement(ADD_NEW_USER);
			
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			preparedStatement.setString(3, userInfo.getEmail());
			
			if(userInfo.getisPhotographer())
			{
				preparedStatement.setInt(4, role_ph);
			}
			else
			{
				preparedStatement.setInt(4, role_client);
			}
			
			preparedStatement.executeUpdate();
		}
		catch(SQLException ex)
		{
			log.error(ex);
			throw new DAOException(USER_ALREADY_EXISTS_ERROR, ex);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		
		return true;
	}
	
	/**
	 * Logs in a user into a system
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	@Override
	public UserInfo authorization(UserInfo userInfo) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserInfo authorizedUser = null;
		
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
			preparedStatement = connection.prepareStatement(FIND_USER);
			preparedStatement.setString(1, userInfo.getUsername());
			preparedStatement.setString(2, userInfo.getPassword());
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				String userRole = resultSet.getString(5);
				
				if(userRole.equals(ROLE_PHOTOGRAPHER))
				{
					authorizedUser = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), true, false, resultSet.getString(6), resultSet.getString(7), 
							resultSet.getInt(8), resultSet.getInt(9));
				}
				else
				{
					if(userRole.equals(ROLE_ADMIN))
					{
						authorizedUser = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, true, resultSet.getString(6), resultSet.getString(7), 
								resultSet.getInt(8), resultSet.getInt(9));
					}
					else
					{
						authorizedUser = new UserInfo(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, false, resultSet.getString(6), resultSet.getString(7), null,  resultSet.getInt(9));
					}
				}
				
				if(authorizedUser.getState() == 2)
				{
					throw new DAOException(YOU_ARE_BLOCKED);
				}
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return authorizedUser;
	}
	
	
	/**
	 * Updates a user's profile description
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new description of a profile
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	@Override
	public boolean updateProfileDesc(String username, String newProfileDesc) throws DAOException
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
			preparedStatement = connection.prepareStatement(UPDATE_PROFILE_DESC);
			preparedStatement.setString(1, newProfileDesc);
			preparedStatement.setString(2, username);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_UPD_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_UPD_MESSAGE, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		return true;
	}
	
	/**
	 * Updates a user's profile picture
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new profile picture
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	@Override
	public boolean updateProfilePic(String username, String newProfilePicPath) throws DAOException
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
			preparedStatement = connection.prepareStatement(UPDATE_PROFILE_PIC);
			preparedStatement.setString(1, newProfilePicPath);
			preparedStatement.setString(2, username);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				throw new DAOException(ERROR_WHILE_UPD_MESSAGE);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(ERROR_WHILE_UPD_MESSAGE, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
		}
		return true;
	}
	
	/**
	 * Get all information about the user by his username
	 * 
	 * @param username username of the user
	 * @return information about the user {@link UserInfo}
	 * @throws DAOException
	 */
	public UserInfo getInfoByUsername(String username) throws DAOException
	{
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		UserInfo user = null;
		
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
			preparedStatement = connection.prepareStatement(FIND_USER_BY_USERNAME);
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				String userRole = resultSet.getString(5);
				
				if(userRole.equals(ROLE_PHOTOGRAPHER))
				{
					
					user = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), true, false, resultSet.getString(6), resultSet.getString(7), 
							resultSet.getInt(8), resultSet.getInt(9));
				}
				else
				{
					if(userRole.equals(ROLE_ADMIN))
					{
						user = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, true, resultSet.getString(6), resultSet.getString(7), 
								resultSet.getInt(8), resultSet.getInt(9));
					}
					else
					{
						user = new UserInfo(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, false, resultSet.getString(6), resultSet.getString(7), null,  resultSet.getInt(9));
					}
				}
				
			}
			if(user == null)
			{
				throw new DAOException(NO_SUCH_USER);
			}
			if(user.getState() == 2)
			{
				throw new DAOException(USER_BLOCKED);
			}
		} 
		catch (SQLException e) 
		{
			log.error(ERROR_WHILE_FINDING_USER + e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return user;
	}
	
	/**
	 * Get list of all users of the app
	 * 
	 * @return list of the users {@link UserInfo}
	 * @throws DAOException
	 */
	public List<UserInfo> getAllUsers() throws DAOException
	{
		List<UserInfo> allUsers = new ArrayList<UserInfo>();
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
			preparedStatement = connection.prepareStatement(GET_ALL_USERS);
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next())
			{
				UserInfo user;
				String userRole = resultSet.getString(5);
				
				if(userRole.equals(ROLE_PHOTOGRAPHER))
				{
					
					user = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
							resultSet.getString(3), true, false, resultSet.getString(6), resultSet.getString(7), 
							resultSet.getInt(8), resultSet.getInt(9));
				}
				else
				{
					if(userRole.equals(ROLE_ADMIN))
					{
						user = new UserInfo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, true, resultSet.getString(6), resultSet.getString(7), 
								resultSet.getInt(8), resultSet.getInt(9));
					}
					else
					{
						user = new UserInfo(resultSet.getInt(1),resultSet.getString(2), resultSet.getString(4),
								resultSet.getString(3), false, false, resultSet.getString(6), resultSet.getString(7), null, resultSet.getInt(9));
					}
				}
				allUsers.add(user);
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(resultSet, preparedStatement, connection);
		}
		
		return allUsers;
	}
	
	/**
	 * Block or unlock a user depending on the action
	 * 
	 * @param userId the id of the user to block or unlock
	 * @param action block or unlock
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean blockUnlock(int userId, int action) throws DAOException
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
			preparedStatement = connection.prepareStatement(BLOCK_UNLOCK);
			preparedStatement.setInt(1, action);
			preparedStatement.setInt(2, userId);
			
			if(preparedStatement.executeUpdate() < 1)
			{
				if(action == 1)
				{
					throw new DAOException(ERROR_WHILE_UNLOCK);
				}
				else
				{
					throw new DAOException(ERROR_WHILE_BLOCK);
				}
			}
		}
		catch(SQLException e)
		{
			log.error(e);
			throw new DAOException(SERVER_ERROR, e);
		}
		finally
		{
			closeAll(null, preparedStatement, connection);
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
