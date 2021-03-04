package by.tc.photobook.dao;

import by.tc.photobook.bean.UserInfo;

public interface UserDAO 
{
	public boolean registration(UserInfo userInfo);
	public boolean authorization(UserInfo userInfo);
}
