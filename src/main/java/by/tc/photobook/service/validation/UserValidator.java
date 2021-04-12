package by.tc.photobook.service.validation;

import by.tc.photobook.bean.UserInfo;


public interface UserValidator 
{
	public boolean checkAuthData(UserInfo userInfo) throws ValidationException;
	public boolean checkRegData(UserInfo userInfo) throws ValidationException;
}
