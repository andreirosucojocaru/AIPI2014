package ro.pub.cs.aipi.lab05.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import ro.pub.cs.aipi.lab05.general.Constants;
import ro.pub.cs.aipi.lab05.general.Utilities;
import ro.pub.cs.aipi.lab05.reservation.Date;
import ro.pub.cs.aipi.lab05.reservation.Interval;
import ro.pub.cs.aipi.lab05.reservation.Moment;
import ro.pub.cs.aipi.lab05.reservation.ReservationService;
import ro.pub.cs.aipi.lab05.reservation.ReservationServiceHelper;
import ro.pub.cs.aipi.lab05.reservation.Time;

public class Exercise03Test {

	@Test
	public void checkTimetable() {
		
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

           Interval[] timetable = reservationServiceRef.getTimeTable();
            
            assertEquals("Entry 1 starting time in timetable does not match", 0, Utilities.timeCompare(timetable[0].start, new Time(new Date(10, 11, 2014), new Moment(10, 0))));
            assertEquals("Entry 1 ending time in timetable does not match", 0, Utilities.timeCompare(timetable[0].end, new Time(new Date(10, 11, 2014), new Moment(12, 0))));
            assertEquals("Entry 2 starting time in timetable does not match", 0, Utilities.timeCompare(timetable[1].start, new Time(new Date(10, 11, 2014), new Moment(14, 0))));
            assertEquals("Entry 2 ending time in timetable does not match", 0, Utilities.timeCompare(timetable[1].end, new Time(new Date(10, 11, 2014), new Moment(20, 0))));
            assertEquals("Entry 3 starting time in timetable does not match", 0, Utilities.timeCompare(timetable[2].start, new Time(new Date(13, 11, 2014), new Moment(14, 0))));
            assertEquals("Entry 3 ending time in timetable does not match", 0, Utilities.timeCompare(timetable[2].end, new Time(new Date(13, 11, 2014), new Moment(16, 0))));
            assertEquals("Entry 4 starting time in timetable does not match", 0, Utilities.timeCompare(timetable[3].start, new Time(new Date(13, 11, 2014), new Moment(18, 0))));
            assertEquals("Entry 4 ending time in timetable does not match", 0, Utilities.timeCompare(timetable[3].end, new Time(new Date(13, 11, 2014), new Moment(20, 0))));
        } catch (Exception exception) {
            fail("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }

	}

}
