package by.tc.photobook.bean;

import java.util.Objects;

public class UserInfo 
{
	private String username;
	private String email;
	private String password;
	private boolean isPhotographer;
	private String profileDecs;
	private String profilePicPath;
	private Integer totalRating;
	
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
	
	public UserInfo(String username, String email, String password, boolean isPhotographer, 
            String profileDecs, String profilePicPath, Integer totalRating) 
	{
		this.username = username;
		this.email = email;
		this.password = password;
		this.isPhotographer = isPhotographer;
		this.profileDecs = profileDecs;
		this.profilePicPath = profilePicPath;
		this.totalRating = totalRating;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean getisPhotographer() {
		return isPhotographer;
	}

	public void setIsPhotographer(boolean isPhotographer) {
		this.isPhotographer = isPhotographer;
	}

	public String getProfileDecs() {
		return profileDecs;
	}

	public void setProfileDecs(String profileDecs) {
		this.profileDecs = profileDecs;
	}

	public String getProfilePicPath() {
		return profilePicPath;
	}

	public void setProfilePicPath(String profilePicPath) {
		this.profilePicPath = profilePicPath;
	}

	public Integer getTotalRating() {
		return totalRating;
	}

	public void setTotalRating(Integer totalRating) {
		this.totalRating = totalRating;
	}
	
	@Override
	public String toString()
	{
		return "UserInfo :" +
				"\n username: " + username +
				"\n email: " + email +
				"\n password: " + password +
				"\n isPhotographer: " + isPhotographer +
				"\n profileDecs: " + profileDecs +
				"\n profilePicPath: " + profilePicPath +
				"\n totalRating: " + totalRating;
	}
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return isPhotographer == userInfo.isPhotographer && totalRating == userInfo.totalRating && 
        		username.equals(userInfo.username) && email.equals(userInfo.email) && 
        		password.equals(userInfo.password) && profileDecs.equals(userInfo.profileDecs) && 
        		profilePicPath.equals(userInfo.profilePicPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, email, password, isPhotographer, profileDecs, profilePicPath, totalRating);
    }
}
