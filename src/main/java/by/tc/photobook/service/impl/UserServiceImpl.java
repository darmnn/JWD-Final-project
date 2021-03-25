package by.tc.photobook.service.impl;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.UserService;
import by.tc.photobook.service.validation.UserValidator;
import by.tc.photobook.service.validation.ValidatorProvider;

public class UserServiceImpl implements UserService
{
	private static final String INVALID_LOGIN_PASSWORD_MESSAGE = "Invalid login or password!";
	private static final String NO_UPDATE_ERROR = "Server error! Changes are not saved";
	private static final String LOADING_USER_ERROR = "Error while loading user data!";
	
	@Override
	public boolean registration(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		userValidator.checkRegData(userInfo);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			boolean successfulRegistration = userDAO.registration(userInfo);
			return successfulRegistration;
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
	}
	
	@Override
	public UserInfo authorization(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		userValidator.checkAuthData(userInfo);
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		UserInfo authorizedUser = null;
		
		try
		{
			authorizedUser = userDAO.authorization(userInfo);
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
				
		if(authorizedUser == null)
		{
			throw new ServiceException(INVALID_LOGIN_PASSWORD_MESSAGE);
				
		}
		
		return authorizedUser;
	}
	
	@Override
	public boolean updateProfileDesc(String username, String newProfileDesc) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		boolean updated = false;
		try
		{
			updated = userDAO.updateProfileDesc(username, newProfileDesc);
			if(!updated)
			{
				throw new ServiceException(NO_UPDATE_ERROR);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		
		return updated;
	}
	
	@Override
	public boolean updateProfilePic(String username, String newProfilePicPath) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		boolean updated = false;
		try
		{
			updated = userDAO.updateProfilePic(username, newProfilePicPath);
			if(!updated)
			{
				throw new ServiceException(NO_UPDATE_ERROR);
			}
		}
		catch(DAOException e)
		{
			throw new ServiceException(e);
		}
		
		return updated;
	}
	
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
			throw new ServiceException(e);
		}
				
		if(user == null)
		{
			throw new ServiceException(LOADING_USER_ERROR);
				
		}
		
		return user;
	}
}
