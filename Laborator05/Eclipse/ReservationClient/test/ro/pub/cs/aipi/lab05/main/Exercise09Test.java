package ro.pub.cs.aipi.lab05.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import ro.pub.cs.aipi.lab05.general.Constants;
import ro.pub.cs.aipi.lab05.general.Utilities;
import ro.pub.cs.aipi.lab05.reservation.Date;
import ro.pub.cs.aipi.lab05.reservation.Moment;
import ro.pub.cs.aipi.lab05.reservation.ReservationInformation;
import ro.pub.cs.aipi.lab05.reservation.ReservationService;
import ro.pub.cs.aipi.lab05.reservation.ReservationServiceHelper;
import ro.pub.cs.aipi.lab05.reservation.Time;

public class Exercise09Test {

	@Test
	public void check01ExistingReservation() {
        try {
            // creare si initializare ORB
        	String[] args = new String[4];
        	args[0] = "-ORBInitialHost";
        	args[1] =  "localhost";
        	args[2] = "-ORBInitialPort";
        	args[3] = "1100";
            ORB orb = ORB.init(args, null);
            
            // obtine contextul de nume radacina
            org.omg.CORBA.Object nameServiceRef = orb.resolve_initial_references(Constants.SERVICE_NAME);
            NamingContextExt namingContextRef = NamingContextExtHelper.narrow(nameServiceRef);
            
            // rezolvare referinta obiect distribuit prin serviciu de nume
            ReservationService reservationServiceRef = ReservationServiceHelper.narrow(namingContextRef.resolve_str(Constants.ORB_IDENTIFIER));
            
            ReservationInformation[] user1Reservations = reservationServiceRef.getReservations(1);
            
            assertEquals("Existing reservation should return one entry", 
            		user1Reservations.length, 1);
            assertTrue("Existing reservation information should return be accurate", 
            		(user1Reservations[0].customerId==1) &&
            		Utilities.timeCompare(new Time(new Date(user1Reservations[0].interval.start.date.day, user1Reservations[0].interval.start.date.month, user1Reservations[0].interval.start.date.year), new Moment(user1Reservations[0].interval.start.moment.hour, user1Reservations[0].interval.start.moment.minute)), new Time(new Date(10, 11, 2014), new Moment(14, 0)))==0 &&
            		Utilities.timeCompare(new Time(new Date(user1Reservations[0].interval.end.date.day, user1Reservations[0].interval.end.date.month, user1Reservations[0].interval.end.date.year), new Moment(user1Reservations[0].interval.end.moment.hour, user1Reservations[0].interval.end.moment.minute)), new Time(new Date(10, 11, 2014), new Moment(18, 0)))==0 &&
            		user1Reservations[0].numberOfSeats==10);
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check02NonExistingReservation() {
        try {
            // creare si initializare ORB
        	String[] args = new String[4];
        	args[0] = "-ORBInitialHost";
        	args[1] =  "localhost";
        	args[2] = "-ORBInitialPort";
        	args[3] = "1100";
            ORB orb = ORB.init(args, null);
            
            // obtine contextul de nume radacina
            org.omg.CORBA.Object nameServiceRef = orb.resolve_initial_references(Constants.SERVICE_NAME);
            NamingContextExt namingContextRef = NamingContextExtHelper.narrow(nameServiceRef);
            
            // rezolvare referinta obiect distribuit prin serviciu de nume
            ReservationService reservationServiceRef = ReservationServiceHelper.narrow(namingContextRef.resolve_str(Constants.ORB_IDENTIFIER));
            
            ReservationInformation[] user2Reservations = reservationServiceRef.getReservations(2);
            
            assertEquals("Non-existing reservation should return no entries", 
            		0, user2Reservations.length);
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}

}
