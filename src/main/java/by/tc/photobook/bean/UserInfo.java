package by.tc.photobook.bean;

import java.io.Serializable;
import java.util.Objects;

public class UserInfo implements Serializable
{
	private static final long serialVersionUID = 3493166085402697331L;
	
	private int id;
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
	
	public UserInfo(int id, String username, String email, String password, boolean isPhotographer, 
            String profileDecs, String profilePicPath, Integer totalRating) 
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isPhotographer = isPhotographer;
		this.profileDecs = profileDecs;
		this.profilePicPath = profilePicPath;
		this.totalRating = totalRating;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setPhotographer(boolean photographer) {
        isPhotographer = photographer;
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

    public int getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id && isPhotographer == userInfo.isPhotographer && totalRating == userInfo.totalRating && username.equals(userInfo.username) && email.equals(userInfo.email) && password.equals(userInfo.password) && Objects.equals(profileDecs, userInfo.profileDecs) && Objects.equals(profilePicPath, userInfo.profilePicPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, email, password, isPhotographer, profileDecs, profilePicPath, totalRating);
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isPhotographer=" + isPhotographer +
                ", profileDecs='" + profileDecs + '\'' +
                ", profilePicPath='" + profilePicPath + '\'' +
                ", totalRating=" + totalRating +
                '}';
    }
}
