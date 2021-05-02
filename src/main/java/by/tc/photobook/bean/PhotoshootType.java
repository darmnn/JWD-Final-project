package by.tc.photobook.bean;

import java.io.Serializable;


/** 
 * Class that describes a type of available photoshoot type
 * @author Darya Minina
*/
public class PhotoshootType implements Serializable
{
	private static final long serialVersionUID = -883185574489861788L;
	
	int id;
    String type;

    public PhotoshootType(int id, String type) 
    {
        this.id = id;
        this.type = type;
    }
    
    public PhotoshootType() {}

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
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id +((type == null) ? 3 : type.hashCode()));
    }

    @Override
    public String toString() {
        return "PhotoshootType{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}
