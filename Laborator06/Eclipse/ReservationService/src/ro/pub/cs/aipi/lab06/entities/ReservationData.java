package ro.pub.cs.aipi.lab06.entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="ReservationData")
public class ReservationData {

    private int         clientId;
    private Interval    interval;
    private int         numberOfSeats;
    
    public ReservationData() {
        clientId            = -1;
        interval            = null;
        numberOfSeats       = -1;
    }
    
    public ReservationData(int clientId, Interval interval, int numberOfSeats) {
        this.clientId       = clientId;
        this.interval       = new Interval(interval.getStartingTime(),interval.getEndingTime());
        this.numberOfSeats  = numberOfSeats;
    }
    
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
    
    public int getClientId() {
        return clientId;
    }
    
    public void setInterval(Interval interval) {
        this.interval = new Interval(interval.getStartingTime(),interval.getEndingTime());
    }
    
    public Interval getInterval() {
        return interval;
    }
    
    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }
    
    public int getNumberOfSeats() {
        return numberOfSeats;
    }

}
