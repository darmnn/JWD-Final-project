package by.tc.photobook.service.impl;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.dao.DAOProvider;
import by.tc.photobook.dao.UserDAO;
import by.tc.photobook.service.UserService;

public class UserServiceImpl implements UserService
{
	@Override
	public boolean registration(UserInfo userInfo)
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		return userDAO.registration(userInfo);
	}
	
	@Override
	public UserInfo authorization(UserInfo userInfo)
	{
		DAOProvider daoProvider = DAOProvider.getInstance();
		UserDAO userDAO = daoProvider.getUserdao();
		
		return userDAO.authorization(userInfo);
	}
}
