package ro.pub.cs.aipi.lab06.entities;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PersistentReservationData {

    private ArrayList<Integer> customerIds;       
    private ArrayList<GregorianCalendar> startingIntervals;    
    private ArrayList<GregorianCalendar> endingIntervals;
    private ArrayList<Integer> numberOfSeats;    
    
    public PersistentReservationData() {
        customerIds       = new ArrayList<>();
        startingIntervals = new ArrayList<>();
        endingIntervals   = new ArrayList<>();
        numberOfSeats     = new ArrayList<>();
    }
    
    @XmlElement
    public ArrayList<Integer> getCustomerIds() {
        return customerIds;
    }
    
    public void setCustomerIds(ArrayList<Integer> customerIds) {
        this.customerIds = customerIds;
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
    
    @XmlElement
    public ArrayList<Integer> getNumberOfSeats() {
        return numberOfSeats;
    }
    
    public void setNumberOfSeats(ArrayList<Integer> numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public int getDimension() {
        return customerIds.size();
    }

}
