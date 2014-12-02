package ro.pub.cs.aipi.lab06.reservation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import ro.pub.cs.aipi.lab06.entities.Interval;
import ro.pub.cs.aipi.lab06.entities.PersistentInterval;
import ro.pub.cs.aipi.lab06.entities.PersistentReservationData;
import ro.pub.cs.aipi.lab06.entities.ReservationData;
import ro.pub.cs.aipi.lab06.general.Constants;
import ro.pub.cs.aipi.lab06.general.Utilities;

@WebService(serviceName = "Reservation")
public class Reservation {

    public int                       numberOfSeats;
    public PersistentInterval        timetable;
    public PersistentReservationData reservationData;
    
    JAXBContext contextPersistentInterval;
    JAXBContext contextPersistentReservationData;
    
    public Reservation() {
        try {
            contextPersistentInterval = JAXBContext.newInstance(PersistentInterval.class);
            contextPersistentReservationData = JAXBContext.newInstance(PersistentReservationData.class);
        }
        catch (JAXBException jaxbException) { 
            System.out.println ("An exception has occurred: "+jaxbException.getMessage());
            if (Constants.DEBUG)
                jaxbException.printStackTrace();
        }
        if (new File(Constants.PERSISTENT_PATH + Constants.PERSISTENT_INTERVAL_FILE).exists()) {
            timetable = unpackPersistentInterval(Constants.PERSISTENT_PATH + Constants.PERSISTENT_INTERVAL_FILE);
            numberOfSeats = timetable.getNumberOfSeats();
        } else {
            timetable = new PersistentInterval();
            try (RandomAccessFile file = new RandomAccessFile(Constants.TIMETABLE_PATH + Constants.TIMETABLE_FILE, "r")) {
                numberOfSeats = Integer.parseInt(file.readLine());
                timetable.setNumberOfSeats(numberOfSeats);
                String currentLine;
                while((currentLine = file.readLine())!=null) {
                    StringTokenizer currentAnalyzer = new StringTokenizer(currentLine," ");
                    int day = Integer.parseInt(currentAnalyzer.nextToken());
                    int month = Integer.parseInt(currentAnalyzer.nextToken());
                    int year = Integer.parseInt(currentAnalyzer.nextToken());
                    ArrayList<GregorianCalendar> startingIntervals = timetable.getStartingIntervals();
                    startingIntervals.add(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                    timetable.setStartingIntervals(startingIntervals);
                    ArrayList<GregorianCalendar> endingIntervals = timetable.getEndingIntervals();
                    endingIntervals.add(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                    timetable.setEndingIntervals(endingIntervals);
                }
            } catch (IOException ioException) {
                System.out.println ("An exception has occurred: "+ioException.getMessage());
                if (Constants.DEBUG)
                    ioException.printStackTrace();                
            }
            packPersistentInterval(timetable, Constants.PERSISTENT_PATH + Constants.PERSISTENT_INTERVAL_FILE);
        }
            
        if (new File(Constants.PERSISTENT_PATH + Constants.PERSISTENT_RESERVATION_DATA_FILE).exists()) {
            reservationData = unpackPersistentReservationData(Constants.PERSISTENT_PATH + Constants.PERSISTENT_RESERVATION_DATA_FILE);
        } else {
            reservationData = new PersistentReservationData();
            packPersistentReservationData(reservationData, Constants.PERSISTENT_PATH + Constants.PERSISTENT_RESERVATION_DATA_FILE);
        }
    }
    
    public final void packPersistentInterval(PersistentInterval object, String file) {
        try {
            Marshaller conversion = contextPersistentInterval.createMarshaller();
            conversion.marshal(object, new FileOutputStream(file));              
        }
        catch (JAXBException | FileNotFoundException exception) { 
            System.out.println ("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();
        }
    }

    public final void packPersistentReservationData(PersistentReservationData object, String file) {
        try {
            Marshaller conversion = contextPersistentReservationData.createMarshaller();
            conversion.marshal(object, new FileOutputStream(file));              
        }
        catch (JAXBException | FileNotFoundException exception) { 
            System.out.println ("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();
        }
    }    
    
    public final PersistentInterval unpackPersistentInterval(String file) {
        try {
            Unmarshaller conversion = contextPersistentInterval.createUnmarshaller();
            return (PersistentInterval)conversion.unmarshal(new FileInputStream(file));  
        }
        catch (JAXBException | FileNotFoundException exception) {
            System.out.println ("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();
        }
        return null;
    }    

    public final PersistentReservationData unpackPersistentReservationData(String file) {
        try {
            Unmarshaller conversion = contextPersistentReservationData.createUnmarshaller();
            return (PersistentReservationData)conversion.unmarshal(new FileInputStream(file));  
        }
        catch (JAXBException | FileNotFoundException exception) {
            System.out.println ("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
                exception.printStackTrace();
        }
        return null;
    }    
    
    private Interval getIntervalIntersection(Interval interval1, Interval interval2) {
        Interval result = new Interval();
        if (interval1.getStartingTime().before(interval2.getStartingTime()) ||
            Utilities.convertGregorianCalendarToString(interval1.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(interval2.getStartingTime()))) {
            if (interval1.getEndingTime().before(interval2.getStartingTime())) {
                return null;
            }
            if (Utilities.convertGregorianCalendarToString(interval1.getEndingTime()).equals(Utilities.convertGregorianCalendarToString(interval2.getStartingTime()))) {
                result.setStartingTime(interval1.getEndingTime());
                result.setEndingTime(interval2.getStartingTime());
            }
            if (interval1.getEndingTime().after(interval2.getStartingTime()) ||
                Utilities.convertGregorianCalendarToString(interval1.getEndingTime()).equals(Utilities.convertGregorianCalendarToString(interval2.getEndingTime()))) {
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
            if (Utilities.convertGregorianCalendarToString(interval1.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(interval2.getEndingTime()))) {
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
        if (Utilities.convertGregorianCalendarToString(result.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(result.getEndingTime())))
            return null;
        return result;
    }     
    
    private ArrayList<Interval> persistentInterval2IntervalArrayList(PersistentInterval persistentInterval) {
        ArrayList<Interval> intervals = new ArrayList<>();
        ArrayList<GregorianCalendar> startingIntervals = persistentInterval.getStartingIntervals();
        ArrayList<GregorianCalendar> endingIntervals = persistentInterval.getEndingIntervals();
        for (int currentIndex = 0; currentIndex < persistentInterval.getDimension(); currentIndex++) {
            intervals.add(new Interval(startingIntervals.get(currentIndex),endingIntervals.get(currentIndex)));
        }        
        return intervals;
    }

    private ArrayList<ReservationData> persistentReservationData2ReservationDataArrayList(PersistentReservationData persistentReservationData) {
        ArrayList<ReservationData> reservations = new ArrayList<>();
        ArrayList<Integer> customerIds = persistentReservationData.getCustomerIds();
        ArrayList<GregorianCalendar> startingIntervals = persistentReservationData.getStartingIntervals();
        ArrayList<GregorianCalendar> endingIntervals = persistentReservationData.getEndingIntervals();
        ArrayList<Integer> numberOfSeatsRequired = persistentReservationData.getNumberOfSeats();
        for (int currentIndex = 0; currentIndex < persistentReservationData.getDimension(); currentIndex++) {
            reservations.add(new ReservationData(customerIds.get(currentIndex),new Interval(startingIntervals.get(currentIndex),endingIntervals.get(currentIndex)),numberOfSeatsRequired.get(currentIndex)));
        }        
        return reservations;
    }  
    
    @WebMethod(operationName = "getTimeTable")
    public ArrayList<Interval> getTimeTable() {
        return persistentInterval2IntervalArrayList(timetable);                
    }
    
    private int getAvailableSeats1(Interval interval) {
        int result = numberOfSeats;
        System.out.println("Interval "+interval.getStartingTime().getTime()+"->"+interval.getEndingTime().getTime());
        for (ReservationData reservation:persistentReservationData2ReservationDataArrayList(reservationData)) {
            if (getIntervalIntersection(interval,reservation.getInterval())!= null) {
                System.out.println("- substract "+reservation.getNumberOfSeats()+" seats from interval "+reservation.getInterval().getStartingTime().getTime()+"->"+reservation.getInterval().getEndingTime().getTime());
                result -= reservation.getNumberOfSeats();
            }
        }
        System.out.println("=>the interval "+interval.getStartingTime().getTime().toString()+"->"+interval.getEndingTime().getTime().toString()+" has "+result+" available seats");        
        return result;
    }    
    
    @WebMethod(operationName = "getAvailableSeats")
    public int getAvailableSeats(@WebParam(name = "interval") Interval interval) {  
        int result = numberOfSeats;
        boolean isInTimetable = false;
        for (Interval currentInterval:persistentInterval2IntervalArrayList(timetable)) {
            if ((interval.getStartingTime().after(currentInterval.getStartingTime()) ||
                 Utilities.convertGregorianCalendarToString(interval.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(currentInterval.getStartingTime()))) &&
                (interval.getEndingTime().before(currentInterval.getEndingTime()) ||
                 Utilities.convertGregorianCalendarToString(interval.getEndingTime()).equals(Utilities.convertGregorianCalendarToString(currentInterval.getEndingTime())))) {
                isInTimetable = true;
            }
        }
        if (!isInTimetable) {
            System.out.println("The interval is outside the timetable.");
            return -1;
        }
        ArrayList<GregorianCalendar> intervalIntersections = new ArrayList<>();
        for (ReservationData reservation:persistentReservationData2ReservationDataArrayList(reservationData)) {
            Interval intersection = getIntervalIntersection(interval,reservation.getInterval());
            if (intersection != null) {
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
        
    @WebMethod(operationName = "makeReservation")
    public boolean makeReservation(@WebParam(name = "customerId") int customerId,@WebParam(name = "interval") Interval interval,@WebParam(name = "noOfSeatsRequired") int noOfSeatsRequired) {
        boolean result = false;
        System.out.println("available seats = "+getAvailableSeats(interval)+" seats required = "+noOfSeatsRequired);
        if (getAvailableSeats(interval) >= noOfSeatsRequired) {
            ArrayList<Integer> customerIds = reservationData.getCustomerIds();
            customerIds.add(customerId);
            ArrayList<GregorianCalendar> startingIntervals = reservationData.getStartingIntervals();
            startingIntervals.add(interval.getStartingTime());
            ArrayList<GregorianCalendar> endingIntervals = reservationData.getEndingIntervals();
            endingIntervals.add(interval.getEndingTime());
            ArrayList<Integer> numberOfSeatsRequired = reservationData.getNumberOfSeats();
            numberOfSeatsRequired.add(noOfSeatsRequired);
            reservationData.setCustomerIds(customerIds);
            reservationData.setStartingIntervals(startingIntervals);
            reservationData.setEndingIntervals(endingIntervals);
            reservationData.setNumberOfSeats(numberOfSeatsRequired);
            packPersistentReservationData(reservationData, Constants.PERSISTENT_PATH + Constants.PERSISTENT_RESERVATION_DATA_FILE);
            result = true;
        }
        return result;
    }

    @WebMethod(operationName = "cancelReservation")
    public boolean cancelReservation(@WebParam(name = "customerId") int customerId,@WebParam(name = "interval") Interval interval) {
        boolean result = false;
        int currentIndex;
        ArrayList<Integer> customerIds = reservationData.getCustomerIds();
        ArrayList<GregorianCalendar> startingIntervals = reservationData.getStartingIntervals();
        ArrayList<GregorianCalendar> endingIntervals = reservationData.getEndingIntervals();
        ArrayList<Integer> numberOfSeatsRequired = reservationData.getNumberOfSeats();
        for (currentIndex = 0; currentIndex < reservationData.getDimension() && !result; currentIndex++) {
            if (customerIds.get(currentIndex).equals(customerId) &&
                    Utilities.convertGregorianCalendarToString(interval.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(startingIntervals.get(currentIndex))) &&
                    Utilities.convertGregorianCalendarToString(interval.getEndingTime()).equals(Utilities.convertGregorianCalendarToString(endingIntervals.get(currentIndex)))) {
                result = true;
            }
        }
        if (result) {            
            customerIds.remove(currentIndex-1);            
            startingIntervals.remove(currentIndex-1);            
            endingIntervals.remove(currentIndex-1);         
            numberOfSeatsRequired.remove(currentIndex-1);
            reservationData.setCustomerIds(customerIds);
            reservationData.setStartingIntervals(startingIntervals);
            reservationData.setEndingIntervals(endingIntervals);
            reservationData.setNumberOfSeats(numberOfSeatsRequired);
            packPersistentReservationData(reservationData, Constants.PERSISTENT_PATH + Constants.PERSISTENT_RESERVATION_DATA_FILE);              
        }       
        return result;
    }    
    
    @WebMethod(operationName = "getReservations")
    public ArrayList<ReservationData> getReservations(@WebParam(name = "customerId") int customerId) {
        ArrayList<ReservationData> result = new ArrayList<>();
        ArrayList<Integer> customerIds = reservationData.getCustomerIds();
        ArrayList<GregorianCalendar> startingIntervals = reservationData.getStartingIntervals();
        ArrayList<GregorianCalendar> endingIntervals = reservationData.getEndingIntervals();
        ArrayList<Integer> numberOfSeatsRequired = reservationData.getNumberOfSeats();
        for (int currentIndex = 0; currentIndex < reservationData.getDimension(); currentIndex++) {
            if (customerIds.get(currentIndex).equals(customerId)) {
                ReservationData currentReservation = new ReservationData(customerIds.get(currentIndex),new Interval(startingIntervals.get(currentIndex),endingIntervals.get(currentIndex)),numberOfSeatsRequired.get(currentIndex));
                result.add(currentReservation);
            }
        }      
        return result;
    }
}
