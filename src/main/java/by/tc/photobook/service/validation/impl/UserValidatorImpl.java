package by.tc.photobook.service.validation.impl;

import java.util.regex.Matcher;


import java.util.regex.Pattern;

import by.tc.photobook.bean.UserInfo;
import by.tc.photobook.service.validation.UserValidator;
import by.tc.photobook.service.validation.ValidationException;

/**
 * Class that checks the accuracy of the input data
 * 
 * @author Darya Minina
 */
public class UserValidatorImpl implements UserValidator
{
	private static final String EMPTY_USERNAME = "message.empty_username";
	private static final String EMPTY_PASSWORD = "message.empty_password";
	private static final String EMPTY_EMAIL = "message.empty_email";
	private static final String SHORT_PASSWORD = "message.short_password";
	private static final String LONG_PASSWORD = "message.long_password";
	private static final String LONG_USERNAME = "message.long_username";
	private static final String INVALID_USERNAME = "message.invalid_username";
	private static final String INVALID_EMAIL = "message.invalid_email";
	private static final String INVALID_TEXT = "message.invalid_text";
	
	private static final String CHARACTER_OR_NUMBER = "^[а-яА-Яa-zA-Z0-9]+$";
	private static final String EMAIL_FORMAT = "([.[^@\\s]]+)@([.[^@\\s]]+)\\.([a-z]+)";
	private static final String ONLY_LETTERS = "^[\\\\p{L} .'-]+$";
	
	private static final int MIN_LENGTH = 6;
	private static final int MAX_LENGTH = 30;
	
	/**
	 * Checks the input user's data while authorization
	 * 
	 * @param userInfo {@link UserInfo}
	 * @return true if everything is correct
	 * @throws ValidationException
	 */
	@Override
	public boolean checkAuthData(UserInfo userInfo) throws ValidationException
	{
		if(userInfo.getUsername().isEmpty() || userInfo.getUsername() == null)
		{
			throw new ValidationException(EMPTY_USERNAME);
		}
		
		if(userInfo.getPassword().isEmpty() || userInfo.getPassword() == null)
		{
			throw new ValidationException(EMPTY_PASSWORD);
		}
		
		if(userInfo.getPassword().length() < MIN_LENGTH)
		{
			throw new ValidationException(SHORT_PASSWORD);
		}
		
		if(userInfo.getPassword().length() > MAX_LENGTH)
		{
			throw new ValidationException(LONG_PASSWORD);
		}
		
		if(userInfo.getUsername().length() > MAX_LENGTH)
		{
			throw new ValidationException(LONG_USERNAME);
		}
		
		Pattern usernamePattern = Pattern.compile(CHARACTER_OR_NUMBER);
		Matcher usernameMatcher = usernamePattern.matcher(userInfo.getUsername());
		
		if(!usernameMatcher.find())
		{
			throw new ValidationException(INVALID_USERNAME);
		}
		
		return true;
	}
	
	/**
	 * Checks the input user's data while registration
	 * 
	 * @param userInfo {@link UserInfo}
	 * @return true if everything is correct
	 * @throws ValidationException
	 */
	public boolean checkRegData(UserInfo userInfo) throws ValidationException
	{
		if(checkAuthData(userInfo))
		{
			if(userInfo.getEmail().isEmpty() || userInfo.getEmail() == null)
			{
				throw new ValidationException(EMPTY_EMAIL);
			}
			
			Pattern emailPattern = Pattern.compile(EMAIL_FORMAT);
			Matcher emailMatcher = emailPattern.matcher(userInfo.getEmail());
			
			if(!emailMatcher.find())
			{
				throw new ValidationException(INVALID_EMAIL);
			}
		}
		
		return true;
	}
	
	/**
	 * Checks if some text contains only letters
	 * 
	 * @param text text to validate
	 * @return true if text contains only letters
	 * @throws ValidationException
	 */
	public boolean checkText(String text) throws ValidationException
	{
		Pattern onlyLetters = Pattern.compile(ONLY_LETTERS);
		Matcher onlyLettersMatcher = onlyLetters.matcher(text);
		
		if(!onlyLettersMatcher.find())
		{
			throw new ValidationException(INVALID_TEXT);
		}
		
		
		return true;
	}
}
