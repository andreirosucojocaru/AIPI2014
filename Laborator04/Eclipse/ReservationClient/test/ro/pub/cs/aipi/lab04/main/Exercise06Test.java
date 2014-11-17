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

public class Exercise06Test {

	@Test
	public void check01ExistingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Existing reservation should pass cancelation", 
            		reservation.cancelReservation(2, new Interval(new GregorianCalendar(2014, 11, 3, 15, 0), new GregorianCalendar(2014, 11, 3, 17, 0))), true);
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
	public void check02IntersectingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Intersecting reservation should fail cancelation", 
            		reservation.cancelReservation(1, new Interval(new GregorianCalendar(2014, 11, 3, 14, 0), new GregorianCalendar(2014, 11, 3, 16, 0))), false);
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
	public void check03NonExistingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            assertEquals("Non-existing reservation should fail cancelation", 
            		reservation.cancelReservation(1, new Interval(new GregorianCalendar(2014, 11, 4, 14, 0), new GregorianCalendar(2014, 11, 4, 18, 0))), false);
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
