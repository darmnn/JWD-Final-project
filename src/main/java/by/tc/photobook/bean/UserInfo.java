package by.tc.photobook.bean;

import java.util.Objects;

public class UserInfo 
{
	private String username;
	private String email;
	private String password;
	private boolean isPhotographer;
	
	public UserInfo(String username, String email, String password, boolean isPhotographer)
	{
		this.username = username;
		this.email = email;
		this.password = password;
		this.isPhotographer = isPhotographer;
	}
	
	public UserInfo(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public boolean isPhotographer()
	{
		return isPhotographer;
	}
	
	@Override
	public String toString()
	{
		return "UserInfo :" +
				"\n username: " + username +
				"\n email: " + email +
				"\n password: " + password +
				"\n isPhotographer: " + isPhotographer;
	}
	
	@Override
	public int hashCode()
	{
		return Objects.hash(username, email, password, isPhotographer);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        UserInfo userinfo = (UserInfo)o;
        
        return username.equals(userinfo.username) && email.equals(userinfo.email)
        		&& password.equals(userinfo.password) && userinfo.isPhotographer == this.isPhotographer;
	}
}
