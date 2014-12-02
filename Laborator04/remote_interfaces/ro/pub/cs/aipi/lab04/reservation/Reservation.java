package ro.pub.cs.aipi.lab04.reservation;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import ro.pub.cs.aipi.lab04.reservation.ReservationInformation;

public interface Reservation extends Remote {
	public ArrayList<Interval> getTimetable() throws RemoteException;
	public int getAvailableSeats(Interval interval) throws RemoteException;
	public boolean makeReservation(int clientId, Interval interval, int numberOfSeats) throws RemoteException;
	public boolean cancelReservation(int clientId, Interval interval) throws RemoteException;
        public ArrayList<ReservationInformation> getReservations(int clientId) throws RemoteException;
}