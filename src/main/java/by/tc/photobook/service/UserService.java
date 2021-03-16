package by.tc.photobook.service;

import by.tc.photobook.bean.UserInfo;

public interface UserService 
{
	public boolean registration(UserInfo userInfo) throws ServiceException;
	public UserInfo authorization(UserInfo userInfo) throws ServiceException;
	public boolean updateProfileDesc(String username, String newProfileDesc) throws ServiceException;
}
