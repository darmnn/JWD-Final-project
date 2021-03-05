package by.tc.photobook.bean;

import java.util.Objects;

public class ClientInfo extends UserInfo
{
	private String profileDecs;
	private String profilePicPath;
	
	public ClientInfo(String username, String email, String password, boolean isPhotographer, 
			String profileDesc, String profilePicPath)
	{
		super(username, email, password, isPhotographer);
		
		this.profileDecs = profileDesc;
		this.profilePicPath = profilePicPath;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ClientInfo that = (ClientInfo) o;
        return profileDecs.equals(that.profileDecs) && profilePicPath.equals(that.profilePicPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profileDecs, profilePicPath);
    }

    @Override
    public String toString() {
        return "ClientInfo:" +
                "\n profileDecs: " + profileDecs +
                "\n profilePicPath: " + profilePicPath;
    }
}
