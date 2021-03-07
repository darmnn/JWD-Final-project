package by.tc.photobook.service.impl;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOException;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.UserService;

public class UserServiceImpl implements UserService
{
	@Override
	public boolean registration(UserInfo userInfo) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
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
	
	@Override
	public UserInfo authorization(UserInfo userInfo) throws ServiceException
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		UserInfo authorizedUser = null;
		
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
		
		return authorizedUser;
	}
}
