package ro.pub.cs.aipi.lab04.main;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import ro.pub.cs.aipi.lab04.entities.ReservationData;
import ro.pub.cs.aipi.lab04.general.Constants;
import ro.pub.cs.aipi.lab04.general.Utilities;
import ro.pub.cs.aipi.lab04.reservation.Interval;
import ro.pub.cs.aipi.lab04.reservation.Reservation;
import ro.pub.cs.aipi.lab04.reservation.ReservationInformation;

public class ReservationImplementation implements Reservation {
    private int                        numberOfSeats;
    private ArrayList<Interval>        timetable;
    private ArrayList<ReservationData> reservations; 
    
    public ReservationImplementation() {
        super();
        numberOfSeats   = -1;
        timetable       = new ArrayList<>();
        reservations    = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile(Constants.INPUT_PATH+Constants.INPUT_FILE,"r");
            numberOfSeats = Integer.parseInt(file.readLine());
            String currentLine;
            while((currentLine = file.readLine()) != null) {
                StringTokenizer currentAnalyzer = new StringTokenizer(currentLine," ");
                Interval currentInterval = new Interval();
                int day = Integer.parseInt(currentAnalyzer.nextToken());
                int month = Integer.parseInt(currentAnalyzer.nextToken());
                int year = Integer.parseInt(currentAnalyzer.nextToken());
                currentInterval.setStartingTime(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                currentInterval.setEndingTime(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                timetable.add(currentInterval);
            }
            file.close();
        } catch (IOException ioException) {
            System.out.println("An exception has occurred: "+ioException.getMessage());
            if (Constants.DEBUG)
            	ioException.printStackTrace();
        }
    }

    private Interval getIntervalIntersection(Interval interval1, Interval interval2) {
        Interval result = new Interval();
        if (interval1.getStartingTime().before(interval2.getStartingTime()) ||
            interval1.getStartingTime().equals(interval2.getStartingTime())) {
            if (interval1.getEndingTime().before(interval2.getStartingTime())) {
                return null;
            }
            if (interval1.getEndingTime().equals(interval2.getStartingTime())) {
                result.setStartingTime(interval1.getEndingTime());
                result.setEndingTime(interval2.getStartingTime());
            }
            if (interval1.getEndingTime().after(interval2.getStartingTime()) ||
                interval1.getEndingTime().equals(interval2.getEndingTime())) {
                result.setStartingTime(interval2.getStartingTime());
                result.setEndingTime(interval1.getEndingTime());
            }
            if (interval1.getEndingTime().after(interval2.getEndingTime())) {
                return interval2;
            }
        }
        if (interval1.getStartingTime().after(interval2.getStartingTime())) {
            if (interval2.getEndingTime().before(interval1.getStartingTime())) {
                return null;
            }
            if (interval1.getStartingTime().equals(interval2.getEndingTime())) {
                result.setStartingTime(interval1.getStartingTime());
                result.setEndingTime(interval2.getEndingTime());
            }
            if (interval1.getStartingTime().before(interval2.getEndingTime()) ||
                interval1.getEndingTime().equals(interval2.getEndingTime())) {
                result.setStartingTime(interval1.getStartingTime());
                result.setEndingTime(interval2.getEndingTime());
            }
            if (interval1.getEndingTime().before(interval2.getEndingTime())) {
                return interval1;
            }
        }
        return result;
    }     
    
    @Override
    public ArrayList<Interval> getTimetable() throws RemoteException {
        return timetable;
    }
    
    private int getAvailableSeats1(Interval interval) {
        int result = numberOfSeats;
        System.out.println("Interval "+interval.getStartingTime().getTime()+"->"+interval.getEndingTime().getTime());
        for (ReservationData reservation:reservations) {
            if (getIntervalIntersection(interval,reservation.getInterval())!=null) {
                System.out.println("- substract "+reservation.getNumberOfSeats()+" seats from interval "+reservation.getInterval().getStartingTime().getTime()+"->"+reservation.getInterval().getEndingTime().getTime());
                result -= reservation.getNumberOfSeats();
            }
        }
        System.out.println("=>The interval "+interval.getStartingTime().getTime().toString()+"->"+interval.getEndingTime().getTime().toString()+" has "+result+" available seats");        
        return result;
    }
    
    @Override
    public int getAvailableSeats(Interval interval) throws RemoteException {
        int result = numberOfSeats;
        boolean isInTimetable = false;
        for (Interval currentInterval: timetable) {
            if ((interval.getStartingTime().after(currentInterval.getStartingTime()) ||
                 interval.getStartingTime().equals(currentInterval.getStartingTime())) &&
                (interval.getEndingTime().before(currentInterval.getEndingTime()) ||
                 interval.getEndingTime().equals(currentInterval.getEndingTime()))) {
                isInTimetable = true;
            }
        }
        if (!isInTimetable) {
            System.out.println("The interval is outside the timetable.");
            return -1;
        }
        ArrayList<GregorianCalendar> intervalIntersections = new ArrayList<>();
        for (ReservationData reservation: reservations) {
            Interval intersection = getIntervalIntersection(interval, reservation.getInterval());
            if (intersection!=null) {
                if (!intervalIntersections.contains(intersection.getStartingTime())) {
                    intervalIntersections.add(intersection.getStartingTime());
                }
                if (!intervalIntersections.contains(intersection.getEndingTime())) {
                    intervalIntersections.add(intersection.getEndingTime());
                }
            }
        }
        if (!intervalIntersections.isEmpty()) {
            Collections.sort(intervalIntersections);
            for (GregorianCalendar calendar: intervalIntersections) {
                System.out.println(calendar.getTime());
            }
            Interval currentInterval = new Interval();
            currentInterval.setStartingTime(interval.getStartingTime());
            for (GregorianCalendar nextInterval: intervalIntersections) {
                currentInterval.setEndingTime(nextInterval);
                int currentNumberOfSeats = getAvailableSeats1(currentInterval);
                if (result > currentNumberOfSeats) {
                    result = currentNumberOfSeats;
                }
                currentInterval.setStartingTime(nextInterval);
            }
            currentInterval.setEndingTime(interval.getEndingTime());
            int currentNumberOfSeats = getAvailableSeats1(currentInterval);
            if (result > currentNumberOfSeats) {
                result = currentNumberOfSeats;
            }            
        }
        return result;
    }    
    
    @Override
    public boolean makeReservation(int customerId, Interval interval, int numberOfSeats) throws RemoteException {
        if (getAvailableSeats(interval) >= numberOfSeats) {
            reservations.add(new ReservationData(customerId,interval,numberOfSeats));
            return true;
        }
        return false;
    }    
    
    @Override
    public boolean cancelReservation(int clientId, Interval interval) throws RemoteException {
    	int position = -1;
        int currentIndex = 0;
        for (ReservationData reservation: reservations) {
            if (reservation.getClientId() == clientId && 
            		Utilities.convertGregorianCalendarToString(reservation.getInterval().getStartingTime()).equals(Utilities.convertGregorianCalendarToString((interval.getStartingTime()))) && 
            		Utilities.convertGregorianCalendarToString(reservation.getInterval().getEndingTime()).equals(Utilities.convertGregorianCalendarToString((interval.getEndingTime())))) {
                position=currentIndex;
            }
            currentIndex++;
        }
        if (position != -1)
            reservations.remove(position);
        return (position != -1);
    }
    
    @Override
    public ArrayList<ReservationInformation> getReservations(int clientId) throws RemoteException {
    	ArrayList<ReservationInformation> result = new ArrayList<>();
    	for (ReservationData reservation: reservations)
            if (reservation.getClientId() == clientId)
            	result.add(new ReservationInformation(reservation.getClientId(),reservation.getInterval(),reservation.getNumberOfSeats()));
    	return result;
    }

}
