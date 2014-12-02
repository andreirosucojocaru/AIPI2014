package ro.pub.cs.aipi.lab05.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import ro.pub.cs.aipi.lab05.general.Constants;
import ro.pub.cs.aipi.lab05.reservation.Date;
import ro.pub.cs.aipi.lab05.reservation.Interval;
import ro.pub.cs.aipi.lab05.reservation.Moment;
import ro.pub.cs.aipi.lab05.reservation.ReservationService;
import ro.pub.cs.aipi.lab05.reservation.ReservationServiceHelper;
import ro.pub.cs.aipi.lab05.reservation.Time;

public class Exercise04Test {

	@Test
	public void check01NoReservationNumberOfSeats() {		
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
            
            assertEquals("Entry 1 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (10, 0)), new Time(new Date (10, 11, 2014), new Moment (12, 0)))));
            assertEquals("Entry 1 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (10, 0)), new Time(new Date (10, 11, 2014), new Moment (11, 0)))));
            assertEquals("Entry 1 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (11, 0)), new Time(new Date (10, 11, 2014), new Moment (12, 0)))));
            assertEquals("Entry 1 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (10, 30)), new Time(new Date (10, 11, 2014), new Moment (11, 30)))));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check02NoReservationNumberOfSeats() {
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
            
            assertEquals("Entry 2 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (14, 0)), new Time(new Date (10, 11, 2014), new Moment (20, 0)))));
            assertEquals("Entry 2 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (14, 0)), new Time(new Date (10, 11, 2014), new Moment (15, 0)))));
            assertEquals("Entry 2 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (19, 0)), new Time(new Date (10, 11, 2014), new Moment (20, 0)))));
            assertEquals("Entry 21 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(10, 11, 2014), new Moment (14, 30)), new Time(new Date (10, 11, 2014), new Moment (19, 30)))));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check03NoReservationNumberOfSeats() {
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
            
            assertEquals("Entry 3 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (14, 0)), new Time(new Date (13, 11, 2014), new Moment (16, 0)))));
            assertEquals("Entry 3 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (14, 0)), new Time(new Date (13, 11, 2014), new Moment (15, 0)))));
            assertEquals("Entry 3 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (15, 0)), new Time(new Date (13, 11, 2014), new Moment (16, 0)))));
            assertEquals("Entry 3 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (14, 30)), new Time(new Date (13, 11, 2014), new Moment (15, 30)))));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check04NoReservationNumberOfSeats() {
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
            
            assertEquals("Entry 4 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (18, 0)), new Time(new Date (13, 11, 2014), new Moment (20, 0)))));
            assertEquals("Entry 4 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (18, 0)), new Time(new Date (13, 11, 2014), new Moment (19, 0)))));
            assertEquals("Entry 4 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (19, 0)), new Time(new Date (13, 11, 2014), new Moment (20, 0)))));
            assertEquals("Entry 4 number of available seats is not correct", 
            		20, reservationServiceRef.getAvailableSeats(new Interval(new Time(new Date(13, 11, 2014), new Moment (18, 30)), new Time(new Date (13, 11, 2014), new Moment (19, 30)))));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}

}
