package by.tc.photobook.bean;

import java.util.Date;
import java.util.Objects;

public class Photo
{
    String photographer;
    Date date;
    String imagePath;
    int rating;

    public Photo(String photographer, Date date, String imagePath, int rating)
    {
        this.photographer = photographer;
        this.date = date;
        this.imagePath = imagePath;
        this.rating = rating;
    }

    public String getPhotographer() {
        return photographer;
    }

    public void setPhotographer(String photographer) {
        this.photographer = photographer;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
        return rating == photo.rating && photographer.equals(photo.photographer) && date.equals(photo.date) && imagePath.equals(photo.imagePath);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(photographer, date, imagePath, rating);
    }

    @Override
    public String toString()
    {
        return "Photo{" +
                "photographer='" + photographer + '\'' +
                ", date=" + date +
                ", imagePath='" + imagePath + '\'' +
                ", rating=" + rating +
                '}';
    }
}