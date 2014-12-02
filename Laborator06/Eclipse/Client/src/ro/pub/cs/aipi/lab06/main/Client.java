package ro.pub.cs.aipi.lab06.main;

import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import ro.pub.cs.aipi.lab06.general.Constants;
import ro.pub.cs.aipi.lab06.reservation.Interval;
import ro.pub.cs.aipi.lab06.reservation.Reservation;
import ro.pub.cs.aipi.lab06.reservation.ReservationData;
import ro.pub.cs.aipi.lab06.reservation.Reservation_Service;

public class Client {
	
	private static Reservation_Service serviciu;
    
    public static void main(String[] args) {
        
        serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        
        List<Interval> timetable = port.getTimeTable();
        for (Interval timetableEntry:timetable) {
            System.out.println ("interval in timetable: "+timetableEntry.getStartingTime().toXMLFormat()+"->"+timetableEntry.getEndingTime().toXMLFormat());
        }
        Interval interval = new Interval();
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,15)));
            port.makeReservation(1,interval,3);
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,30)));
            port.makeReservation(2,interval,4);
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,20,0)));
            port.makeReservation(3,interval,4);     
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,20,0)));
            port.makeReservation(4,interval,2);      
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,19,15)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,20,0)));
            port.makeReservation(5,interval,2); 
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014,11,17,18,30)));
            port.cancelReservation(2,interval);
        }
        catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();
        }
        System.out.println(port.getAvailableSeats(interval));
        List<ReservationData> reservations = port.getReservations(2);
        for (ReservationData reservationsEntry:reservations) {
            System.out.println ("reservation:\n  interval: "+reservationsEntry.getInterval().getStartingTime().toXMLFormat()+"->"+reservationsEntry.getInterval().getEndingTime().toXMLFormat()+"\n  number of seats: "+reservationsEntry.getNumberOfSeats());
        }
    }
}
