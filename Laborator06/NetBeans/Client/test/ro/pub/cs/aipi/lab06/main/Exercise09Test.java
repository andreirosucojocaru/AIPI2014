package ro.pub.cs.aipi.lab06.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.List;

import org.junit.Test;

import ro.pub.cs.aipi.lab06.general.Utilities;
import ro.pub.cs.aipi.lab06.reservation.Reservation;
import ro.pub.cs.aipi.lab06.reservation.ReservationData;
import ro.pub.cs.aipi.lab06.reservation.Reservation_Service;

public class Exercise09Test {

    @Test
    public void check01ExistingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
            
        List<ReservationData> user1Reservations = port.getReservations(1);
            
        assertEquals("Existing reservation should return one entry",
                1,
            	user1Reservations.size());
        assertTrue("Existing reservation information should return be accurate", 
                user1Reservations.get(0).getClientId()==1 &&
                Utilities.convertGregorianCalendarToString(user1Reservations.get(0).getInterval().getStartingTime()).equals("17/11/2014 14:0") &&
                Utilities.convertGregorianCalendarToString(user1Reservations.get(0).getInterval().getEndingTime()).equals("17/11/2014 18:0") &&
            	user1Reservations.get(0).getNumberOfSeats()==10);
		
    }
	
    @Test
    public void check02NonExistingReservation() {
		
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        
        List<ReservationData> user2Reservations = port.getReservations(2);
            
        assertEquals("Non-existing reservation should return no entries", 
                0,
                user2Reservations.size());
    }

}
