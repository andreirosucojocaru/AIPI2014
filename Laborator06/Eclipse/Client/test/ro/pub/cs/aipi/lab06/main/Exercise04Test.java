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

public class Exercise04Test {

    @Test
    public void check01NoReservationNumberOfSeats() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
        
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 10, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 12, 0)));
            assertEquals("Entry 1 number of available seats is not correct", 
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 10, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 11, 0)));
            assertEquals("Entry 1 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 11, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 12, 0)));
            assertEquals("Entry 1 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 10, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 11, 30)));
            assertEquals("Entry 1 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }
	
    @Test
    public void check02NoReservationNumberOfSeats() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 20, 0)));
            assertEquals("Entry 2 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)));
            assertEquals("Entry 2 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 19, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 20, 0)));
            assertEquals("Entry 2 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));

            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 19, 30)));
            assertEquals("Entry 2 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }
	
    @Test
    public void check03NoReservationNumberOfSeats() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 16, 0)));
            assertEquals("Entry 3 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 15, 0)));
            assertEquals("Entry 3 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 15, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 16, 0)));
            assertEquals("Entry 3 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 14, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 15, 30)));            
            assertEquals("Entry 3 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }
	
    @Test
    public void check04NoReservationNumberOfSeats() {

        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 18, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 20, 0)));
            assertEquals("Entry 4 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 18, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 19, 0)));            
            assertEquals("Entry 4 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 19, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 20, 0)));            
            assertEquals("Entry 4 number of available seats is not correct", 
                    20,
                    port.getAvailableSeats(interval));
            
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 18, 30)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 20, 19, 30)));            
            assertEquals("Entry 4 number of available seats is not correct",
                    20,
                    port.getAvailableSeats(interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
		
    }

}
