package by.tc.photobook.service.impl;

import java.util.List;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.UserService;
import by.tc.photobook.service.validation.UserValidator;
import by.tc.photobook.service.validation.ValidationException;
import by.tc.photobook.service.validation.ValidatorProvider;

/**
 * The implementation of operations with information about users
 * 
 * @author Darya Minina
 */
public class UserServiceImpl implements UserService
{
	private static final String INVALID_LOGIN_PASSWORD_MESSAGE = "message.invalid_login_password";
	private static final String UNLOCK_ACTION = "unlock";
	
	/**
	 * Adds a new user
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	@Override
	public boolean registration(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		
		try
		{
			userValidator.checkRegData(userInfo);
			
			DAOProvider daoProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoProvider.getUserdao();
			
			userDAO.registration(userInfo);
			return true;
		}
		catch(ValidationException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
	}
	
	/**
	 * Logs in a user into a system
	 * 
	 * @param userInfo {@link UserInfo} information about new user
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	@Override
	public UserInfo authorization(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		UserInfo authorizedUser = null;
		
		try
		{
			userValidator.checkAuthData(userInfo);
			DAOProvider daoProvider = DAOProvider.getInstance();
			UserDAO userDAO = daoProvider.getUserdao();
			authorizedUser = userDAO.authorization(userInfo);
		}
		catch(ValidationException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
				
		if(authorizedUser == null)
		{
			throw new ServiceException(INVALID_LOGIN_PASSWORD_MESSAGE);
				
		}
		
		return authorizedUser;
	}
	
	/**
	 * Updates a user's profile description
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new description of a profile
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	@Override
	public boolean updateProfileDesc(String username, String newProfileDesc) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			userDAO.updateProfileDesc(username, newProfileDesc);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	/**
	 * Updates a user's profile picture
	 * 
	 * @param username username of the user
	 * @param newProfileDesc new profile picture
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	@Override
	public boolean updateProfilePic(String username, String newProfilePicPath) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			userDAO.updateProfilePic(username, newProfilePicPath);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
	
	/**
	 * Get all information about the user by his username
	 * 
	 * @param username username of the user
	 * @return information about the user {@link UserInfo}
	 * @throws ServiceException
	 */
	public UserInfo getInfoByUsername(String username) throws ServiceException
	{
		UserInfo user = null;
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			user = userDAO.getInfoByUsername(username);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return user;
	}
	
	/**
	 * Get list of all users of the app
	 * 
	 * @return list of the users {@link UserInfo}
	 * @throws ServiceException
	 */
	public List<UserInfo> getAllUsers() throws ServiceException
	{
		List<UserInfo> allUsers = null;
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			allUsers = userDAO.getAllUsers();
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return allUsers;
	}
	
	/**
	 * Block or unlock a user depending on the action
	 * 
	 * @param userId the id of the user to block or unlock
	 * @param action block or unlock
	 * @return true if the operation is successful
	 * @throws ServiceException
	 */
	public boolean blockUnlock(int userId, String actionString) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		int action;
		
		if(actionString.equals(UNLOCK_ACTION))
		{
			action = 1;
		}
		else
		{
			action = 2;
		}
		
		try
		{
			userDAO.blockUnlock(userId, action);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e.getDescription(), e);
		}
		
		return true;
	}
}
