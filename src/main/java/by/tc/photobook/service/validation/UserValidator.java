package by.tc.photobook.service.validation;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.ServiceException;

public interface UserValidator 
{
	public boolean checkAuthData(UserInfo userInfo) throws ServiceException;
	public boolean checkRegData(UserInfo userInfo) throws ServiceException;
}
