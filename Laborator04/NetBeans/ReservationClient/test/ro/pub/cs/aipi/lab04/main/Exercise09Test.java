package ro.pub.cs.aipi.lab04.main;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

import org.junit.Test;

import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.general.Utilities;
import ro.pub.cs.aipi.lab04.reservation.Reservation;
import ro.pub.cs.aipi.lab04.reservation.ReservationInformation;

public class Exercise09Test {

	@Test
	public void check01ExistingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            ArrayList<ReservationInformation> user1Reservations = reservation.getReservations(1);
            
            assertEquals("Existing reservation should return one entry", 
            		user1Reservations.size(), 1);
            assertTrue("Existing reservation information should return be accurate", 
            		user1Reservations.get(0).getClientId()==1 &&
            		Utilities.convertGregorianCalendarToString(user1Reservations.get(0).getInterval().getStartingTime()).equals("3/11/2014 2:0") &&
            		Utilities.convertGregorianCalendarToString(user1Reservations.get(0).getInterval().getEndingTime()).equals("3/11/2014 6:0") &&
            		user1Reservations.get(0).getNumberOfSeats()==10);
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
	public void check02NonExistingReservation() {
		
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            ArrayList<ReservationInformation> user2Reservations = reservation.getReservations(2);
            
            assertEquals("Non-existing reservation should return no entries", 
            		user2Reservations.size(), 0);
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
