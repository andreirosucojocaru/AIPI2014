package ro.pub.cs.aipi.lab04.main;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.reservation.Reservation;

public class Server {

    public static void main (String[] args) {
        
        if (System.getSecurityManager() == null)
            System.setSecurityManager(new SecurityManager());

        try {
            Reservation service = new ReservationImplementation();
            Reservation proxy = (Reservation)UnicastRemoteObject.exportObject(service, 0);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(Constants.SERVICE_NAME, proxy);
            System.out.println("The service was registered successfully");
        } catch (RemoteException remoteException) {
        	System.out.println("The service was not registered successfully");
            System.out.println("An exception has occurred: "+remoteException.getMessage());
            if (Constants.DEBUG)
            	remoteException.printStackTrace();
        }

    }
}