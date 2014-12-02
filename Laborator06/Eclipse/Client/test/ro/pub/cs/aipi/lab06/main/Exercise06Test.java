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

public class Exercise06Test {

	@Test
	public void check01ExistingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 15, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 17, 0)));
            assertEquals("Existing reservation should pass cancelation", 
                    true,
                    port.cancelReservation(2, interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }
	
    @Test
    public void check02IntersectingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 17, 16, 0)));
            assertEquals("Intersecting reservation should fail cancelation", 
                    false,
                    port.cancelReservation(1, interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }
	
    @Test
    public void check03NonExistingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        Interval interval = new Interval();
		
        try {
            interval.setStartingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 18, 14, 0)));
            interval.setEndingTime(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar(2014, 11, 18, 18, 0)));
            assertEquals("Non-existing reservation should fail cancelation",
                    false,
                    port.cancelReservation(1, interval));
        } catch (DatatypeConfigurationException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();            
        }
        
    }

}
