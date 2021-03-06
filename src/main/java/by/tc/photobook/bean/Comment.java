package by.tc.photobook.bean;

import java.io.Serializable;

import java.time.LocalDate;

/** 
 * Class that describes a comment to a photo
 * @author Darya Minina
*/
public class Comment implements Serializable
{
	private static final long serialVersionUID = 6932883176165943198L;
	
	private int id;
	private String text;
	private String author;
	private LocalDate date;
	private String authorPic;
	
	public Comment(int id, String text, String author, LocalDate date, String authorPic) {
		this.id = id;
        this.text = text;
        this.author = author;
        this.date = date;
        this.authorPic = authorPic;
    }
	
	public Comment(String text, String author, LocalDate date, String authorPic) {
        this.text = text;
        this.author = author;
        this.date = date;
        this.authorPic = authorPic;
    }
	
	public Comment()
	{
		
	}

	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getAuthorPic() {
        return authorPic;
    }

    public void setAuthorPic(String authorPic) {
        this.authorPic = authorPic;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return id == comment.id && text.equals(comment.text) && author.equals(comment.author) && date.equals(comment.date) && authorPic.equals(comment.authorPic);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id + ((text == null) ? 3 : text.hashCode()) + ((author == null) ? 3 : author.hashCode()) +
    			((date == null) ? 3 : date.hashCode()) + ((authorPic == null) ? 3 : authorPic.hashCode()));
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", authorPic='" + authorPic + '\'' +
                '}';
    }
}
