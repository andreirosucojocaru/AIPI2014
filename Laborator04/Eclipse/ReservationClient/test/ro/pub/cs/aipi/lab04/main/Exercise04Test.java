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

public class Exercise04Test {

	@Test
	public void check01NoReservationNumberOfSeats() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Entry 1 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 10, 0), new GregorianCalendar(2014, 11, 3, 12, 0))), 20);
            assertEquals("Entry 1 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 10, 0), new GregorianCalendar(2014, 11, 3, 11, 0))), 20);
            assertEquals("Entry 1 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 11, 0), new GregorianCalendar(2014, 11, 3, 12, 0))), 20);
            assertEquals("Entry 1 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 10, 30), new GregorianCalendar(2014, 11, 3, 11, 30))), 20);

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
	public void check02NoReservationNumberOfSeats() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Entry 2 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 14, 0), new GregorianCalendar(2014, 11, 3, 20, 0))), 20);
            assertEquals("Entry 2 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 14, 0), new GregorianCalendar(2014, 11, 3, 15, 0))), 20);
            assertEquals("Entry 2 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 19, 0), new GregorianCalendar(2014, 11, 3, 20, 0))), 20);
            assertEquals("Entry 2 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 3, 15, 30), new GregorianCalendar(2014, 11, 3, 19, 30))), 20);

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
	public void check03NoReservationNumberOfSeats() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Entry 3 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 14, 0), new GregorianCalendar(2014, 11, 6, 16, 0))), 20);
            assertEquals("Entry 3 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 14, 0), new GregorianCalendar(2014, 11, 6, 15, 0))), 20);
            assertEquals("Entry 3 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 15, 0), new GregorianCalendar(2014, 11, 6, 16, 0))), 20);
            assertEquals("Entry 3 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 14, 30), new GregorianCalendar(2014, 11, 6, 15, 30))), 20);

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
	public void check04NoReservationNumberOfSeats() {

        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Entry 4 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 18, 0), new GregorianCalendar(2014, 11, 6, 20, 0))), 20);
            assertEquals("Entry 4 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 18, 0), new GregorianCalendar(2014, 11, 6, 19, 0))), 20);
            assertEquals("Entry 4 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 19, 0), new GregorianCalendar(2014, 11, 6, 20, 0))), 20);
            assertEquals("Entry 4 number of available seats is not correct", 
            		reservation.getAvailableSeats(new Interval(new GregorianCalendar(2014, 11, 6, 18, 30), new GregorianCalendar(2014, 11, 6, 19, 30))), 20);

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
