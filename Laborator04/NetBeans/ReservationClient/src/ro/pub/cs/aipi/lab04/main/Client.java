package ro.pub.cs.aipi.lab04.main;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.GregorianCalendar;

import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.reservation.Interval;
import ro.pub.cs.aipi.lab04.reservation.Reservation;

public class Client {
 
    public static void main(String[] args) {
    	
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());
		
        try {
            Registry registry = LocateRegistry.getRegistry(Constants.RMIREGISTRY_LOCATION);
            Reservation reservation = (Reservation)registry.lookup(Constants.SERVICE_NAME);
            
            int customerId = 1;
            Interval interval = new Interval();
            interval.setStartingTime(new GregorianCalendar(2014, 11, 3, 10, 20));
            interval.setEndingTime(new GregorianCalendar(2014, 11, 3, 11, 40));
            int numberOfSeats = 10;
			
            if (reservation.makeReservation(customerId,interval,numberOfSeats)) {
                System.out.println("The reservation has been made successfully.");
            } else {
                System.out.println("The reservation has not been made successfully.");
            }
            
            customerId = 2;
            interval = new Interval();
            interval.setStartingTime(new GregorianCalendar(2014, 11, 3, 11, 20));
            interval.setEndingTime(new GregorianCalendar(2014, 11, 3, 12, 00));
            numberOfSeats = 15;
			
            if (reservation.makeReservation(customerId,interval,numberOfSeats)) {
                System.out.println("The reservation has been made successfully.");
            } else {
                System.out.println("The reservation has not been made successfully.");
            }            
        } catch (RemoteException | NotBoundException exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }

    }

}