package by.tc.photobook.service;

import java.util.ResourceBundle;

public class EmailResourceManager 
{
	private static final String BUNDLE_NAME = "email";
	private static final EmailResourceManager instance = new EmailResourceManager();

	private ResourceBundle bundle = ResourceBundle.getBundle(BUNDLE_NAME);

	public static EmailResourceManager getInstance() {
		return instance;
	}

	public String getValue(String key) {
		return bundle.getString(key);
	}
}
