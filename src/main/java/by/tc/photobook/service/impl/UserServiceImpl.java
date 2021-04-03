package by.tc.photobook.service.impl;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.UserService;
import by.tc.photobook.service.validation.UserValidator;
import by.tc.photobook.service.validation.ValidationException;
import by.tc.photobook.service.validation.ValidatorProvider;

public class UserServiceImpl implements UserService
{
	private static final String INVALID_LOGIN_PASSWORD_MESSAGE = "message.invalid_login_password";
	
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
}
