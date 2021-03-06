package by.tc.photobook.bean;

import java.io.Serializable;

import java.time.LocalDate;

/** 
 * Class that describes an order to photoshoot from some photographer
 * @author Darya Minina
*/
public class Order implements Serializable
{
	private static final long serialVersionUID = 4806355737773490301L;
	
	private int id;
	private String client;
	private PhotoshootOption phOption;
	private String status;
	private LocalDate date;
	
	public Order(int id, String client, PhotoshootOption phOption, LocalDate date, String status) 
    {
        this.id = id;
        this.client = client;
        this.phOption = phOption;
        this.status = status;
        this.date = date;
    }
	
	public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public PhotoshootOption getPhOption() {
        return phOption;
    }

    public void setPhOption(PhotoshootOption phOption) {
        this.phOption = phOption;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id && status == order.status && client.equals(order.client) && phOption.equals(order.phOption) && date.equals(order.date);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (id + ((client == null) ? 3 : client.hashCode()) + ((phOption == null) ? 3 : phOption.hashCode()) +
    			((date == null) ? 3 : date.hashCode()) + ((status == null) ? 3 : status.hashCode()));
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", phOption=" + phOption +
                ", date=" + date +
                ", status=" + status +
                '}';
    }
}
