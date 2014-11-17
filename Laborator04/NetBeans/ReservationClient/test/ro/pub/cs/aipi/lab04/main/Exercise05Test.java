package ro.pub.cs.aipi.lab04.main;

import static org.junit.Assert.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.GregorianCalendar;

import org.junit.Test;

import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.reservation.Interval;
import ro.pub.cs.aipi.lab04.reservation.Reservation;

public class Exercise05Test {

	@Test
	public void check01IntervalOutsideSchedule() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval outside schedule should fail reservation", 
            		reservation.makeReservation(1, new Interval(new GregorianCalendar(2014, 11, 4, 12, 0), new GregorianCalendar(2014, 11, 4, 14, 0)), 1), false);
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
	
	@Test
	public void check02IntervalIntersectingSchedule() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval intersecting schedule should fail reservation", 
            		reservation.makeReservation(1, new Interval(new GregorianCalendar(2014, 11, 3, 9, 30), new GregorianCalendar(2014, 11, 3, 11, 30)), 1), false);
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
	
	@Test
	public void check03IntervalIntersectingSchedule() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval intersecting schedule should fail reservation", 
            		reservation.makeReservation(1, new Interval(new GregorianCalendar(2014, 11, 6, 14, 30), new GregorianCalendar(2014, 11, 6, 16, 30)), 1), false);
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
	
	@Test
	public void check04IntervalInsideScheduleAvailableNumberOfSeats() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval inside schedule with available number of seats should pass reservation", 
            		reservation.makeReservation(1, new Interval(new GregorianCalendar(2014, 11, 3, 14, 0), new GregorianCalendar(2014, 11, 3, 18, 0)), 10), true);
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
	
	@Test
	public void check05IntervalInsideScheduleIntersectingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval inside schedule intersecting another reservation should pass reservation", 
            		reservation.makeReservation(2, new Interval(new GregorianCalendar(2014, 11, 3, 15, 0), new GregorianCalendar(2014, 11, 3, 17, 0)), 10), true);
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
	
	@Test
	public void check06IntervalInsideScheduleNonAvailableNumberOfSeats() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Interval inside schedule with non-available number of seats should fail reservation", 
            		reservation.makeReservation(1, new Interval(new GregorianCalendar(2014, 11, 6, 14, 0), new GregorianCalendar(2014, 11, 6, 15, 0)), 40), false);
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
