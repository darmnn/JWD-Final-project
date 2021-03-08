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
	@Override
	public boolean registration(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		try
		{
			if(userValidator.checkRegData(userInfo))
			{
				try
				{
					boolean successfulRegistration = userDAO.registration(userInfo);
					return successfulRegistration;
				}
				catch(DAOException e)
				{
					throw new ServiceException("Error while registration: " + e.getMessage());
				}
			}
		}
		catch(ServiceException e)
		{
			throw new ServiceException(e);
		}
		
		return false;
	}
	
	@Override
	public UserInfo authorization(UserInfo userInfo) throws ServiceException
	{
		ValidatorProvider validatorProvider = ValidatorProvider.getInstance(); 
		UserValidator userValidator = validatorProvider.getUserValidator();
		
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		UserInfo authorizedUser = null;
		
		try
		{
			if(userValidator.checkAuthData(userInfo))
			{
				try
				{
					authorizedUser = userDAO.authorization(userInfo);
				}
				catch(DAOException e)
				{
					throw new ServiceException("Error while authorization: " + e.getMessage());
				}
				
				if(authorizedUser == null)
				{
					throw new ServiceException("Invalid login or password!");
				}
			}
		}
		catch(ServiceException e)
		{
			throw new ServiceException(e);
		}
		
		return authorizedUser;
	}
}
