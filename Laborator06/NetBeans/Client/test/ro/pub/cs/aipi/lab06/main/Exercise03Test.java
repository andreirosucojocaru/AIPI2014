package ro.pub.cs.aipi.lab06.main;

import static org.junit.Assert.assertEquals;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Test;
import ro.pub.cs.aipi.lab06.general.Utilities;

import ro.pub.cs.aipi.lab06.reservation.Interval;
import ro.pub.cs.aipi.lab06.reservation.Reservation;
import ro.pub.cs.aipi.lab06.reservation.Reservation_Service;

public class Exercise03Test {

    @Test
    public void checkTimetable() {
	
        Reservation_Service serviciu = new Reservation_Service();
        Reservation port = serviciu.getReservationPort();
        List<Interval> timetable = port.getTimeTable();
        
        assertEquals("Entry 1 starting time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 17, 10, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(0).getStartingTime()));
        assertEquals("Entry 1 ending time in timetable does not match",
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 17, 12, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(0).getEndingTime()));
        assertEquals("Entry 2 starting time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 17, 14, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(1).getStartingTime()));
        assertEquals("Entry 2 ending time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 17, 20, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(1).getEndingTime()));
        assertEquals("Entry 3 starting time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 20, 14, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(2).getStartingTime()));
        assertEquals("Entry 3 ending time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 20, 16, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(2).getEndingTime()));
        assertEquals("Entry 4 starting time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 20, 18, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(3).getStartingTime()));
        assertEquals("Entry 4 ending time in timetable does not match", 
                Utilities.convertGregorianCalendarToString(new GregorianCalendar(2014, 11, 20, 20, 0)),
                Utilities.convertGregorianCalendarToString(timetable.get(3).getEndingTime()));
    }

}
