package ro.pub.cs.aipi.lab05.main;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.StringTokenizer;

import ro.pub.cs.aipi.lab05.entities.Interval;
import ro.pub.cs.aipi.lab05.entities.ReservationData;
import ro.pub.cs.aipi.lab05.general.Constants;
import ro.pub.cs.aipi.lab05.general.Utilities;

public class ReservationServiceImplementation extends ro.pub.cs.aipi.lab05.reservation.ReservationServicePOA { 
    
    private int                                                         numberOfSeats;
    private ArrayList<ro.pub.cs.aipi.lab05.entities.Interval>           timetable;
    private ArrayList<ro.pub.cs.aipi.lab05.entities.ReservationData>    reservations;
    
    public ReservationServiceImplementation() {
        numberOfSeats   = -1;
        timetable       = new ArrayList<>();
        reservations    = new ArrayList<>();
        try {
            RandomAccessFile file = new RandomAccessFile(Constants.INPUT_PATH + Constants.INPUT_FILE,"r");
            numberOfSeats = Integer.parseInt(file.readLine());
            String currentLine;
            while((currentLine=file.readLine())!=null) {
                StringTokenizer currentAnalyzer = new StringTokenizer(currentLine," ");
                ro.pub.cs.aipi.lab05.entities.Interval currentInterval = new ro.pub.cs.aipi.lab05.entities.Interval();
                int day = Integer.parseInt(currentAnalyzer.nextToken());
                int month = Integer.parseInt(currentAnalyzer.nextToken());
                int year = Integer.parseInt(currentAnalyzer.nextToken());
                currentInterval.setStartingTime(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                currentInterval.setEndingTime(new GregorianCalendar(year,month,day,Integer.parseInt(currentAnalyzer.nextToken()),Integer.parseInt(currentAnalyzer.nextToken())));
                timetable.add(currentInterval);
            }
            file.close();
        } catch (Exception exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            if (Constants.DEBUG)
            	exception.printStackTrace();
        }
    }
    
    public ro.pub.cs.aipi.lab05.reservation.Interval entitiesIntervalToReservationInterval(ro.pub.cs.aipi.lab05.entities.Interval interval) {
    	ro.pub.cs.aipi.lab05.reservation.Interval result = new ro.pub.cs.aipi.lab05.reservation.Interval();
        result.start = new ro.pub.cs.aipi.lab05.reservation.Time();
        result.start.date = new ro.pub.cs.aipi.lab05.reservation.Date();
        result.start.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();
        result.end = new ro.pub.cs.aipi.lab05.reservation.Time();
        result.end.date = new ro.pub.cs.aipi.lab05.reservation.Date();
        result.end.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();
        result.start.date.day = interval.getStartingTime().get(Calendar.DAY_OF_MONTH);
        result.start.date.month = interval.getStartingTime().get(Calendar.MONDAY);
        result.start.date.year = interval.getStartingTime().get(Calendar.YEAR);
        result.start.moment.hour = interval.getStartingTime().get(Calendar.HOUR_OF_DAY);
        result.start.moment.minute = interval.getStartingTime().get(Calendar.MINUTE);
        result.end.date.day = interval.getEndingTime().get(Calendar.DAY_OF_MONTH);
        result.end.date.month = interval.getEndingTime().get(Calendar.MONDAY);
        result.end.date.year = interval.getEndingTime().get(Calendar.YEAR);
        result.end.moment.hour = interval.getEndingTime().get(Calendar.HOUR_OF_DAY);
        result.end.moment.minute = interval.getEndingTime().get(Calendar.MINUTE);        
        return result;
    }
    
    public ro.pub.cs.aipi.lab05.entities.Interval reservationIntervalToEntitiesInterval(ro.pub.cs.aipi.lab05.reservation.Interval interval) {
        ro.pub.cs.aipi.lab05.entities.Interval result = new ro.pub.cs.aipi.lab05.entities.Interval();
        result.setStartingTime(new GregorianCalendar(interval.start.date.year,interval.start.date.month,interval.start.date.day,interval.start.moment.hour,interval.start.moment.minute));
        result.setEndingTime(new GregorianCalendar(interval.end.date.year,interval.end.date.month,interval.end.date.day,interval.end.moment.hour,interval.end.moment.minute));
        return result;
    }
    
    public ro.pub.cs.aipi.lab05.reservation.ReservationInformation entitiesReservationDataToReservationReservationInformation(ro.pub.cs.aipi.lab05.entities.ReservationData reservationData) {
    	ro.pub.cs.aipi.lab05.reservation.ReservationInformation result = new ro.pub.cs.aipi.lab05.reservation.ReservationInformation();
        result.customerId = reservationData.getClientId();
        result.interval = new ro.pub.cs.aipi.lab05.reservation.Interval();
        result.interval.start = new ro.pub.cs.aipi.lab05.reservation.Time();
        result.interval.start.date = new ro.pub.cs.aipi.lab05.reservation.Date();
        result.interval.start.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();
        result.interval.end = new ro.pub.cs.aipi.lab05.reservation.Time();
        result.interval.end.date = new ro.pub.cs.aipi.lab05.reservation.Date();
        result.interval.end.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();
        result.interval = entitiesIntervalToReservationInterval(reservationData.getInterval());
        result.numberOfSeats = reservationData.getNumberOfSeats();
        return result;
    }
    
    public ro.pub.cs.aipi.lab05.entities.ReservationData reservationReservationDataToEntitiesReservationData(ro.pub.cs.aipi.lab05.reservation.ReservationInformation reservationInformation) {
        ro.pub.cs.aipi.lab05.entities.ReservationData result = new ro.pub.cs.aipi.lab05.entities.ReservationData();
        result.setClientId(reservationInformation.customerId);
        result.setInterval(reservationIntervalToEntitiesInterval(reservationInformation.interval));
        result.setNumberOfSeats(reservationInformation.numberOfSeats);
        return result;
    }
    
    @Override
    public ro.pub.cs.aipi.lab05.reservation.Interval[] getTimeTable() throws ro.pub.cs.aipi.lab05.reservation.UnspecifiedTimeTable {
    	ro.pub.cs.aipi.lab05.reservation.Interval[] result = new ro.pub.cs.aipi.lab05.reservation.Interval[timetable.size()];
        int count = 0;
        for (ro.pub.cs.aipi.lab05.entities.Interval interval:timetable)
            result[count++] = entitiesIntervalToReservationInterval(interval);
        return result;
    } // getTimeTable

    private Interval getIntervalIntersection(ro.pub.cs.aipi.lab05.entities.Interval interval1, ro.pub.cs.aipi.lab05.entities.Interval interval2) {
        ro.pub.cs.aipi.lab05.entities.Interval result = new ro.pub.cs.aipi.lab05.entities.Interval();
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
    
    private int getAvailableSeats1(ro.pub.cs.aipi.lab05.entities.Interval interval) {
        int result = numberOfSeats;
        System.out.println("Intervalul "+interval.getStartingTime().getTime()+"->"+interval.getEndingTime().getTime());
        for (ro.pub.cs.aipi.lab05.entities.ReservationData reservation:reservations) {
            if (getIntervalIntersection(interval,reservation.getInterval())!=null) {
                System.out.println("- se scad "+reservation.getNumberOfSeats()+" locuri aferente intervalului "+reservation.getInterval().getStartingTime().getTime()+"->"+reservation.getInterval().getEndingTime().getTime());
                result -= reservation.getNumberOfSeats();
            }
        }
        System.out.println("=>in intervalul "+interval.getStartingTime().getTime().toString()+"->"+interval.getEndingTime().getTime().toString()+" sunt "+result+" locuri disponibile");        
        return result;
    }
    
    public int getAvailableSeats(ro.pub.cs.aipi.lab05.entities.Interval interval) {
        int result = numberOfSeats;
        boolean isInTimetable = false;
        for (ro.pub.cs.aipi.lab05.entities.Interval currentInterval:timetable) {
            if ((interval.getStartingTime().after(currentInterval.getStartingTime()) ||
                 interval.getStartingTime().equals(currentInterval.getStartingTime())) &&
                (interval.getEndingTime().before(currentInterval.getEndingTime()) ||
                 interval.getEndingTime().equals(currentInterval.getEndingTime()))) {
                isInTimetable = true;
            }
        }
        if (!isInTimetable) {
            System.out.println("Intervalul nu face parte din programul de functionare.");
            return -1;
        }
        ArrayList<GregorianCalendar> intervalIntersections = new ArrayList<>();
        for (ro.pub.cs.aipi.lab05.entities.ReservationData reservation:reservations) {
            ro.pub.cs.aipi.lab05.entities.Interval intersection = getIntervalIntersection(interval,reservation.getInterval());
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
            for (GregorianCalendar calendar:intervalIntersections) {
                System.out.println(Utilities.convertGregorianCalendarToString(calendar));
            }
            ro.pub.cs.aipi.lab05.entities.Interval currentInterval = new Interval();
            currentInterval.setStartingTime(interval.getStartingTime());
            for (GregorianCalendar nextInterval:intervalIntersections) {
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
    public int getAvailableSeats(ro.pub.cs.aipi.lab05.reservation.Interval interval) throws ro.pub.cs.aipi.lab05.reservation.UnspecifiedTimeTable {
        return getAvailableSeats(reservationIntervalToEntitiesInterval(interval));
    } // getAvailableSeats

    public boolean makeReservation(int customerId, Interval interval, int numberOfSeats) {
        if (getAvailableSeats(interval)>=numberOfSeats) {
            reservations.add(new ReservationData(customerId,interval,numberOfSeats));
            return true;
        }
        return false;
    }    
    
    @Override
    public boolean makeReservation(int customerId, ro.pub.cs.aipi.lab05.reservation.Interval interval, int numberOfSeats) throws ro.pub.cs.aipi.lab05.reservation.UnspecifiedTimeTable {
        return makeReservation(customerId, reservationIntervalToEntitiesInterval(interval), numberOfSeats);
    } // makeReservation

    public boolean cancelReservation(int clientId, Interval interval) {
        int position=-1;
        int currentIndex=0;
        for (ReservationData reservation:reservations) {
            if (reservation.getClientId()==clientId && reservation.getInterval().equals(interval)) {
                position=currentIndex;
            }
            currentIndex++;
        }
        if (position!=-1) {
            reservations.remove(position);
        }
        return (position!=-1);
    }       
    
    @Override
    public boolean cancelReservation(int customerId, ro.pub.cs.aipi.lab05.reservation.Interval interval) throws ro.pub.cs.aipi.lab05.reservation.UnspecifiedTimeTable {
        return cancelReservation(customerId, reservationIntervalToEntitiesInterval(interval));
    } // cancelReservation
    
    @Override
    public ro.pub.cs.aipi.lab05.reservation.ReservationInformation[] getReservations (int customerId) throws ro.pub.cs.aipi.lab05.reservation.UnspecifiedTimeTable {
        int noOfReservations = 0;
    	for (ReservationData reservationData:reservations)
            if (reservationData.getClientId()==customerId)
            	noOfReservations ++;
    	ro.pub.cs.aipi.lab05.reservation.ReservationInformation[] result = new ro.pub.cs.aipi.lab05.reservation.ReservationInformation[noOfReservations];
    	if (noOfReservations == 0)
    		return result;
        int count=0;
        for (ReservationData reservationData:reservations)
            if (reservationData.getClientId()==customerId) {
            	result[count] = new ro.pub.cs.aipi.lab05.reservation.ReservationInformation();
            	result[count].interval = new ro.pub.cs.aipi.lab05.reservation.Interval();
            	result[count].interval.start = new ro.pub.cs.aipi.lab05.reservation.Time();
            	result[count].interval.start.date = new ro.pub.cs.aipi.lab05.reservation.Date();
            	result[count].interval.start.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();
            	result[count].interval.end = new ro.pub.cs.aipi.lab05.reservation.Time();
            	result[count].interval.end.date = new ro.pub.cs.aipi.lab05.reservation.Date();
            	result[count].interval.end.moment = new ro.pub.cs.aipi.lab05.reservation.Moment();            	
                result[count++]=entitiesReservationDataToReservationReservationInformation(reservationData);   
            }
        return result;
    } // getReservations
}
