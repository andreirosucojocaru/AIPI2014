package ro.pub.cs.aipi.lab06.entities;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersistentInterval {

    private int numberOfSeats;
    private ArrayList<GregorianCalendar> startingIntervals;    
    private ArrayList<GregorianCalendar> endingIntervals;
    
    public PersistentInterval() {
        numberOfSeats     = -1;
        startingIntervals = new ArrayList<>();
        endingIntervals   = new ArrayList<>();
    }

    @XmlElement
    public int getNumberOfSeats() {
        return numberOfSeats;
    }
   
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }    
    
    @XmlElement
    public ArrayList<GregorianCalendar> getStartingIntervals() {
        return startingIntervals;
    }
   
    public void setStartingIntervals(ArrayList<GregorianCalendar> startingIntervals) {
        this.startingIntervals = startingIntervals;
    }
    
    @XmlElement
    public ArrayList<GregorianCalendar> getEndingIntervals() {
        return endingIntervals;
    }
   
    public void setEndingIntervals(ArrayList<GregorianCalendar> endingIntervals) {
        this.endingIntervals = endingIntervals;
    } 
    
    public int getDimension() {
        return startingIntervals.size();
    }

}
