package by.tc.photobook.bean;

import java.io.Serializable;
import java.util.Objects;

public class PhotoshootOption implements Serializable
{
	private static final long serialVersionUID = -6356175759430040066L;
	
	private int id;
    private String photographer;
    private String type;
    private double price;

    public PhotoshootOption(int id, String photographer, String type, double price) 
    {
        this.id = id;
        this.photographer = photographer;
        this.type = type;
        this.price = price;
    }

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoshootOption that = (PhotoshootOption) o;
        return id == that.id && Double.compare(that.price, price) == 0 && photographer.equals(that.photographer) && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, photographer, type, price);
    }

    @Override
    public String toString() {
        return "PhotoshootOption{" +
                "id=" + id +
                ", photographer='" + photographer + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}