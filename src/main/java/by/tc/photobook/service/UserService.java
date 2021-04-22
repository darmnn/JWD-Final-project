package by.tc.photobook.service;

import java.util.List;


import by.tc.photobook.bean.UserInfo;

/**
 * The interface to operate with information about users
 * 
 * @author Darya Minina
 */
public interface UserService 
{
	/**
	 * Adds a new user
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean registration(UserInfo userInfo) throws ServiceException;
	
	/**
	 * Logs in a user into a system
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public UserInfo authorization(UserInfo userInfo) throws ServiceException;
	
	/**
	 * Updates a user's profile description
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new description of a profile
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean updateProfileDesc(String username, String newProfileDesc) throws ServiceException;
	
	/**
	 * Updates a user's profile picture
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new profile picture
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean updateProfilePic(String username, String newProfilePicPath) throws ServiceException;
	
	/**
	 * Get all information about the user by his username
	 * 
	 * @param username username of the user
	 * @return information about the user {@link UserInfo}
	 * @throws ServiceException
	 */
	public UserInfo getInfoByUsername(String username) throws ServiceException;
	
	/**
	 * Get list of all users of the app
	 * 
	 * @return list of the users {@link UserInfo}
	 * @throws ServiceException
	 */
	public List<UserInfo> getAllUsers() throws ServiceException;
	
	/**
	 * Block or unlock a user depending on the action
	 * 
	 * @param userId the id of the user to block or unlock
	 * @param action block or unlock
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean blockUnlock(int userId, String action) throws ServiceException;
}
