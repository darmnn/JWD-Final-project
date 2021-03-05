package by.tc.photobook.bean;

import java.util.Objects;

public class PhotographerInfo extends UserInfo
{
	private String profileDecs;
	private String profilePicPath;
	private int totalRating;
	
	public PhotographerInfo(String username, String email, String password, boolean isPhotographer, 
			String profileDesc, String profilePicPath, int totalRating)
	{
		super(username, email, password, isPhotographer);
		
		this.profileDecs = profileDesc;
		this.profilePicPath = profilePicPath;
		this.totalRating = totalRating;
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
        if (!super.equals(o)) return false;
        PhotographerInfo that = (PhotographerInfo) o;
        return totalRating == that.totalRating && profileDecs.equals(that.profileDecs) && profilePicPath.equals(that.profilePicPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), profileDecs, profilePicPath, totalRating);
    }

    @Override
    public String toString() 
    {
        return "PhotographerInfo: " +
                "\n profileDecs: " + profileDecs +
                "\n profilePicPath: " + profilePicPath +
                "\n totalRating: " + totalRating;
    }
}
