package by.tc.photobook.bean;

import java.util.Objects;

public class PhotoshootType
{
    int id;
    String type;

    public PhotoshootType(int id, String type) 
    {
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhotoshootType that = (PhotoshootType) o;
        return id == that.id && type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type);
    }

    @Override
    public String toString() {
        return "PhotoshootType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
