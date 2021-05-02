package by.tc.photobook.bean;

import java.io.Serializable;




/** 
 * Class that describes a report to an inappropriate photo or comment
 * @author Darya Minina
*/
public class Complaint implements Serializable
{
	private static final long serialVersionUID = 4087889626402998840L;
	
	private int id;
    private String text;
    private String user;
    private Integer commentId;
    private String commentText;
    private Photo photo;
    private int state;

    public Complaint(int id, String text, String user, Integer commentId, String commentText, Photo photo, int state) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.commentId = commentId;
        this.commentText = commentText;
        this.photo = photo;
        this.state = state;
    }
    
    public Complaint(int id, String text, String user, Photo photo, int state) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.photo = photo;
        this.state = state;
    }

    public Complaint(String text, String user, Integer commentId, Photo photo) {
        this.text = text;
        this.user = user;
        this.commentId = commentId;
        this.photo = photo;
    }
    
    public Complaint() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
    
    public String getCommentText()
    {
    	return commentText;
    }
    
    public void setCommentText(String commentText)
    {
    	this.commentText = commentText;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Complaint complaint = (Complaint) o;
        return id == complaint.id && commentId == complaint.commentId && photo.equals(complaint.photo) && state == complaint.state && text.equals(complaint.text) && user.equals(complaint.user);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id + state + ((text == null) ? 3 : text.hashCode()) + ((user == null) ? 3 : user.hashCode()) +
    			((commentId == null) ? 3 : commentId.hashCode()) + ((commentText == null) ? 3 : commentText.hashCode()) +
    			((photo == null) ? 3 : photo.hashCode()));
    }

    @Override
    public String toString() {
        return "Complaint{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", user='" + user + '\'' +
                ", commentId=" + commentId +
                ", photo=" + photo +
                ", state=" + state +
                '}';
    }
}
