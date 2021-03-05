package by.tc.photobook.service;

import by.tc.photobook.bean.UserInfo;

public interface UserService 
{
	public boolean registration(UserInfo userInfo);
	public UserInfo authorization(UserInfo userInfo);
}
