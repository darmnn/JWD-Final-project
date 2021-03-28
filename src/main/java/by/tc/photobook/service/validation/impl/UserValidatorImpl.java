package by.tc.photobook.service.validation.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.ServiceException;
import by.tc.photobook.service.validation.UserValidator;

public class UserValidatorImpl implements UserValidator
{
	private static final String EMPTY_USERNAME = "Enter username!";
	private static final String EMPTY_PASSWORD = "Enter password!";
	private static final String EMPTY_EMAIL = "Enter email!";
	private static final String SHORT_PASSWORD = "The password is too short!";
	private static final String LONG_PASSWORD = "The password is too long!";
	private static final String LONG_USERNAME = "The username is too long!";
	private static final String INVALID_USERNAME = "Username must contain only letters and numbers!";
	private static final String INVALID_EMAIL = "Invalid format of email!";
	
	private static final String NOT_CHARACTER_OR_NUMBER = "\\W";
	private static final String EMAIL_FORMAT = "([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)";
	
	private static final int MIN_LENGTH = 6;
	private static final int MAX_LENGTH = 30;
	
	@Override
	public boolean checkAuthData(UserInfo userInfo) throws ServiceException
	{
		System.out.println(userInfo.getUsername());
		
		if(userInfo.getUsername().isEmpty() || userInfo.getUsername() == null)
		{
			throw new ServiceException(EMPTY_USERNAME);
		}
		
		if(userInfo.getPassword().isEmpty() || userInfo.getPassword() == null)
		{
			throw new ServiceException(EMPTY_PASSWORD);
		}
		
		if(userInfo.getPassword().length() < MIN_LENGTH)
		{
			throw new ServiceException(SHORT_PASSWORD);
		}
		
		if(userInfo.getPassword().length() > MAX_LENGTH)
		{
			throw new ServiceException(LONG_PASSWORD);
		}
		
		if(userInfo.getUsername().length() > MAX_LENGTH)
		{
			throw new ServiceException(LONG_USERNAME);
		}
		
		/*Pattern usernamePattern = Pattern.compile(NOT_CHARACTER_OR_NUMBER);
		Matcher usernameMatcher = usernamePattern.matcher(userInfo.getUsername());
		
		if(usernameMatcher.find())
		{
			throw new ServiceException(INVALID_USERNAME);
		}*/
		
		return true;
	}
	
	public boolean checkRegData(UserInfo userInfo) throws ServiceException
	{
		if(checkAuthData(userInfo))
		{
			if(userInfo.getEmail().isEmpty() || userInfo.getEmail() == null)
			{
				throw new ServiceException(EMPTY_EMAIL);
			}
			
			Pattern emailPattern = Pattern.compile(EMAIL_FORMAT);
			Matcher emailMatcher = emailPattern.matcher(userInfo.getEmail());
			
			if(!emailMatcher.find())
			{
				throw new ServiceException(INVALID_EMAIL);
			}
		}
		
		return true;
	}
}
