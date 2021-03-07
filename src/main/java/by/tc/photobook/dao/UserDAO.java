package by.tc.photobook.dao;

import by.tc.photobook.bean.UserInfo;

public interface UserDAO 
{
	public boolean registration(UserInfo userInfo) throws DAOException;
	public UserInfo authorization(UserInfo userInfo) throws DAOException;
}
