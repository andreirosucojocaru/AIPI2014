package ro.pub.cs.aipi.lab04.reservation;

import java.io.Serializable;

import ro.pub.cs.aipi.lab04.reservation.Interval;

public class ReservationInformation implements Serializable {

    private static final long serialVersionUID = 2048L;

    private int         clientId;
    private Interval    interval;
    private int         numberOfSeats;
    
    public ReservationInformation() {
        clientId            = -1;
        interval            = null;
        numberOfSeats       = -1;
    }
    
    public ReservationInformation(int clientId, Interval interval, int numberOfSeats) {
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
