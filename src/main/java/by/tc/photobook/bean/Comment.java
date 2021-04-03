package by.tc.photobook.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Comment implements Serializable
{
	private static final long serialVersionUID = 6932883176165943198L;
	
	private String text;
	private String author;
	private LocalDate date;
	private String authorPic;
	
	public Comment(String text, String author, LocalDate date, String authorPic) {
        this.text = text;
        this.author = author;
        this.date = date;
        this.authorPic = authorPic;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return text.equals(comment.text) && author.equals(comment.author) && date.equals(comment.date) && Objects.equals(authorPic, comment.authorPic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text, author, date, authorPic);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", date=" + date +
                ", authorPic='" + authorPic + '\'' +
                '}';
    }
}
