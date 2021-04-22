package by.tc.photobook.service.validation;

import by.tc.photobook.bean.UserInfo;

/**
 * Interface to check the accuracy of the input data
 * 
 * @author Darya Minina
 */
public interface UserValidator 
{
	/**
	 * Checks the input user's data while authorization
	 * 
	 * @param userInfo {@link UserInfo}
	 * @return true if everything is correct
	 * @throws ValidationException
	 */
	public boolean checkAuthData(UserInfo userInfo) throws ValidationException;
	
	/**
	 * Checks the input user's data while registration
	 * 
	 * @param userInfo {@link UserInfo}
	 * @return true if everything is correct
	 * @throws ValidationException
	 */
	public boolean checkRegData(UserInfo userInfo) throws ValidationException;
}
