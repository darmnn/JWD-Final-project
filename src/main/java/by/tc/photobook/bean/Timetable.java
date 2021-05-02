package by.tc.photobook.bean;

import java.io.Serializable;

import java.time.LocalDate;
import java.util.Objects;

/** 
 * Class that describes monthly timetable of a photographer, where his busy days are showna
 * @author Darya Minina
*/
public class Timetable implements Serializable
{
	private static final long serialVersionUID = -5317795226797312705L;
	
	private LocalDate date;
	private int lengthOfMonth;
	private LocalDate firstDayOfMonth;
	
	public Timetable(LocalDate date, int lengthOfMonth) {
        this.date = date;
        this.lengthOfMonth = lengthOfMonth;
        this.firstDayOfMonth = date.withDayOfMonth(1);
    }
	
	public Timetable(LocalDate date, int lengthOfMonth, LocalDate firstDayOfMonth) {
        this.date = date;
        this.lengthOfMonth = lengthOfMonth;
        this.firstDayOfMonth = firstDayOfMonth;
    }
	
	public Timetable() {}

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getLengthOfMonth() {
        return lengthOfMonth;
    }

    public void setLengthOfMonth(int lengthOfMonth) {
        this.lengthOfMonth = lengthOfMonth;
    }
    
    public LocalDate getFirstDayOfMonth() {
        return firstDayOfMonth;
    }

    public void setFirstDayOfMonth(LocalDate firstDayOfMonth) {
        this.firstDayOfMonth = firstDayOfMonth;
    }

    @Override
    public boolean equals(Object o) 
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Timetable timetable = (Timetable) o;
        return lengthOfMonth == timetable.lengthOfMonth && Objects.equals(date, timetable.date) && Objects.equals(firstDayOfMonth, timetable.firstDayOfMonth);
    }

    @Override
    public int hashCode() 
    {
    	final int prime = 72;
    	return prime * (lengthOfMonth + ((date == null) ? 3 : date.hashCode()) + ((firstDayOfMonth == null) ? 3 : firstDayOfMonth.hashCode()));
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "date=" + date +
                ", lengthOfMonth=" + lengthOfMonth +
                ", firstDayOfMonth=" + firstDayOfMonth +
                '}';
    }
}
