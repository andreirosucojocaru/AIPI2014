package ro.pub.cs.aipi.lab06.main;

import static org.junit.Assert.assertEquals;

import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;

import ro.pub.cs.aipi.lab06.general.Constants;
import ro.pub.cs.aipi.lab06.reservation.Interval;
import ro.pub.cs.aipi.lab06.reservation.Reservation;
import ro.pub.cs.aipi.lab06.reservation.Reservation_Service;

public class Exercise05Test {

    @Test
    public void check01IntervalOutsideSchedule() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 18, 12, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 18, 14, 0)));
            assertEquals("Interval outside schedule should fail reservation",
                    false,
                    port.makeReservation(1, interval, 1));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }
	
    @Test
    public void check02IntervalIntersectingSchedule() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 9, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 11, 30)));
            assertEquals("Interval intersecting schedule should fail reservation",
                    false,
                    port.makeReservation(1, interval, 1));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }
	
    @Test
    public void check03IntervalIntersectingSchedule() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 14, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 16, 30)));
            assertEquals("Interval intersecting schedule should fail reservation",
                    false,
                    port.makeReservation(1, interval, 1));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }
	
    @Test
    public void check04IntervalInsideScheduleAvailableNumberOfSeats() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 18, 0)));
            assertEquals("Interval inside schedule with available number of seats should pass reservation",
                    true,
                    port.makeReservation(1, interval, 10));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }
	
    @Test
    public void check05IntervalInsideScheduleIntersectingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 17, 0)));
            assertEquals("Interval inside schedule intersecting another reservation should pass reservation",
                    true,
                    port.makeReservation(2, interval, 10));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }
	
    @Test
    public void check06IntervalInsideScheduleNonAvailableNumberOfSeats() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 15, 0)));
            assertEquals("Interval inside schedule with non-available number of seats should fail reservation",
                    false,
                    port.makeReservation(1, interval, 40));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }

}
