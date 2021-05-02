package by.tc.photobook.bean;

import java.io.Serializable;
import java.util.Objects;

/** 
 * Class that describes a user and provides all necessary information about one user
 * @author Darya Minina
*/
public class UserInfo implements Serializable
{
	private static final long serialVersionUID = 3493166085402697331L;
	
	private int id;
	private String username;
	private String email;
	private String password;
	private boolean isPhotographer;
	private boolean isAdmin;
	private String profileDecs;
	private String profilePicPath;
	private Integer totalRating;
	private int state;
	
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
	
	public UserInfo(int id, String username, String email, String password, boolean isPhotographer, boolean isAdmin,
            String profileDecs, String profilePicPath, Integer totalRating, int state) 
	{
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.isPhotographer = isPhotographer;
		this.isAdmin = isAdmin;
		this.profileDecs = profileDecs;
		this.profilePicPath = profilePicPath;
		this.totalRating = totalRating;
		this.state = state;
	}
	
	public UserInfo() {}

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
    
    public boolean getisAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
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

    public void setTotalRating(int totalRating) {
        this.totalRating = totalRating;
    }
    
    public int getState()
    {
    	return state;
    }
    
    public void setState(int state)
    {
    	this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserInfo userInfo = (UserInfo) o;
        return id == userInfo.id && isPhotographer == userInfo.isPhotographer && isAdmin == userInfo.isAdmin && state == userInfo.state && username.equals(userInfo.username) && email.equals(userInfo.email) && password.equals(userInfo.password) && profileDecs.equals(userInfo.profileDecs) && profilePicPath.equals(userInfo.profilePicPath) && Objects.equals(totalRating, userInfo.totalRating);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id + state + ((username == null) ? 3 : username.hashCode()) + ((email == null) ? 3 : email.hashCode()) +
    			((password == null) ? 3 : password.hashCode()) + ((isPhotographer == false) ? 3 : 10 +
    			((isAdmin == false) ? 3 : 10)) + ((profileDecs == null) ? 3 : profileDecs.hashCode()) + 
    			((profilePicPath == null) ? 3 : profilePicPath.hashCode()) + ((totalRating == null) ? 3 : totalRating));
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isPhotographer=" + isPhotographer +
                ", isAdmin=" + isAdmin +
                ", profileDecs='" + profileDecs + '\'' +
                ", profilePicPath='" + profilePicPath + '\'' +
                ", totalRating=" + totalRating +
                ", state=" + state +
                '}';
    }
}
