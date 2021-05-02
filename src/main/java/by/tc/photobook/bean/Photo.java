package by.tc.photobook.bean;

import java.io.Serializable;

import java.time.LocalDate;

/** 
 * Class that describes a photo
 * @author Darya Minina
*/
public class Photo implements Serializable
{ 
	private static final long serialVersionUID = -2504730301599204099L;
	
	private int id;
    private String photographer;
    private LocalDate date;
    private String imagePath;
    private int rating;

    public Photo(int id, String photographer, LocalDate date, String imagePath, int rating) {
        this.id = id;
        this.photographer = photographer;
        this.date = date;
        this.imagePath = imagePath;
        this.rating = rating;
    }
    
    public Photo(String photographer, LocalDate date, String imagePath, int rating) {
        this.photographer = photographer;
        this.date = date;
        this.imagePath = imagePath;
        this.rating = rating;
    }
    
    public Photo() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return id == photo.id && rating == photo.rating && photographer.equals(photo.photographer) && date.equals(photo.date) && imagePath.equals(photo.imagePath);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id + rating + ((photographer == null) ? 3 : photographer.hashCode()) + ((date == null) ? 3 : date.hashCode()) +
    			((imagePath == null) ? 3 : imagePath.hashCode()));
    }

    @Override
    public String toString() {
        return "Photo:" +
                "id=" + id +
                ", photographer=" + photographer + 
                ", date=" + date +
                ", imagePath=" + imagePath +
                ", rating=" + rating +
                ';';
    }
}