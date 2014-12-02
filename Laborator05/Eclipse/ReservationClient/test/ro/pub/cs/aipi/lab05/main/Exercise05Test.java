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

public class Exercise05Test {

	@Test
	public void check01IntervalOutsideSchedule() {
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
            
            assertEquals("Interval outside schedule should fail reservation", 
            		false, reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(11, 11, 2014), new Moment(14, 0)), new Time(new Date(11, 11, 2014), new Moment(16, 0))), 1));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check02IntervalIntersectingSchedule() {
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
            
            assertEquals("Interval intersecting schedule should fail reservation", 
            		false, reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(10, 11, 2014), new Moment(9, 30)), new Time(new Date(10, 11, 2014), new Moment(11, 30))), 1));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check03IntervalIntersectingSchedule() {
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
            
            assertEquals("Interval intersecting schedule should fail reservation", 
            		false, reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(13, 11, 2014), new Moment(14, 30)), new Time(new Date(13, 11, 2014), new Moment(16, 30))), 1));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
	
	@Test
	public void check04IntervalInsideScheduleAvailableNumberOfSeats() {
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
            
            assertEquals("Interval inside schedule with available number of seats should pass reservation", 
            		true, reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(10, 11, 2014), new Moment(14, 0)), new Time(new Date(10, 11, 2014), new Moment(18, 0))), 10));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        } 
	}
	
	@Test
	public void check05IntervalInsideScheduleIntersectingReservation() {
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
            
            assertEquals("Interval inside schedule intersecting another reservation should pass reservation", 
            		true, reservationServiceRef.makeReservation(2, new Interval(new Time(new Date(10, 11, 2014), new Moment(15, 0)), new Time(new Date(10, 11, 2014), new Moment(17, 0))), 10));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        } 
	}
	
	@Test
	public void check06IntervalInsideScheduleNonAvailableNumberOfSeats() {
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
            
            assertEquals("Interval inside schedule with non-available number of seats should fail reservation", 
            		false, reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(13, 11, 2014), new Moment(14, 0)), new Time(new Date(13, 11, 2014), new Moment(15, 0))), 40));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
	}
}
