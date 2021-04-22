package by.tc.photobook.dao;

import java.util.List;


import by.tc.photobook.bean.UserInfo;

/**
 * The interface for working with database table that stored all users and information about them
 * 
 * @author Darya Minina
 */
public interface UserDAO 
{
	/**
	 * Adds a new user
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean registration(UserInfo userInfo) throws DAOException;
	
	/**
	 * Logs in a user into a system
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public UserInfo authorization(UserInfo userInfo) throws DAOException;
	
	/**
	 * Updates a user's profile description
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new description of a profile
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean updateProfileDesc(String username, String newProfileDesc) throws DAOException;
	
	/**
	 * Updates a user's profile picture
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new profile picture
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean updateProfilePic(String username, String newProfilePicPath) throws DAOException;
	
	/**
	 * Get all information about the user by his username
	 * 
	 * @param username username of the user
	 * @return information about the user {@link UserInfo}
	 * @throws DAOException
	 */
	public UserInfo getInfoByUsername(String username) throws DAOException;
	
	/**
	 * Get list of all users of the app
	 * 
	 * @return list of the users {@link UserInfo}
	 * @throws DAOException
	 */
	public List<UserInfo> getAllUsers() throws DAOException;
	
	/**
	 * Block or unlock a user depending on the action
	 * 
	 * @param userId the id of the user to block or unlock
	 * @param action block or unlock
	 * @return true if the operation is successful
	 * @throws DAOException
	 */
	public boolean blockUnlock(int userId, int action) throws DAOException;
}
