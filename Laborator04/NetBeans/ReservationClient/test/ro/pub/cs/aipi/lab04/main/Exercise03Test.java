package ro.pub.cs.aipi.lab04.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.junit.Test;

import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.reservation.Interval;
import ro.pub.cs.aipi.lab04.reservation.Reservation;

public class Exercise03Test {

	@Test
	public void checkTimetable() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            ArrayList<Interval> timetable = reservation.getTimetable();
            
            assertEquals("Entry 1 starting time in timetable does not match", timetable.get(0).getStartingTime(), new GregorianCalendar(2014, 11, 3, 10, 0));
            assertEquals("Entry 1 ending time in timetable does not match", timetable.get(0).getEndingTime(), new GregorianCalendar(2014, 11, 3, 12, 0));
            assertEquals("Entry 2 starting time in timetable does not match", timetable.get(1).getStartingTime(), new GregorianCalendar(2014, 11, 3, 14, 0));
            assertEquals("Entry 2 ending time in timetable does not match", timetable.get(1).getEndingTime(), new GregorianCalendar(2014, 11, 3, 20, 0));
            assertEquals("Entry 3 starting time in timetable does not match", timetable.get(2).getStartingTime(), new GregorianCalendar(2014, 11, 6, 14, 0));
            assertEquals("Entry 3 ending time in timetable does not match", timetable.get(2).getEndingTime(), new GregorianCalendar(2014, 11, 6, 16, 0));
            assertEquals("Entry 4 starting time in timetable does not match", timetable.get(3).getStartingTime(), new GregorianCalendar(2014, 11, 6, 18, 0));
            assertEquals("Entry 4 ending time in timetable does not match", timetable.get(3).getEndingTime(), new GregorianCalendar(2014, 11, 6, 20, 0));
        } catch (RemoteException remoteException) {
            fail("An exception has occurred: "+remoteException.getMessage());
            if (Constants.DEBUG)
            	remoteException.printStackTrace();
        } catch (NotBoundException notBoundException) {
            fail("An exception has occurred: "+notBoundException.getMessage());
            if (Constants.DEBUG)
            	notBoundException.printStackTrace();
		}

	}

}
