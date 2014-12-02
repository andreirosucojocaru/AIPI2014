package ro.pub.cs.aipi.lab05.general;

import java.util.Calendar;
import java.util.GregorianCalendar;

import ro.pub.cs.aipi.lab05.reservation.Time;

public class Utilities {
	
	public static String convertGregorianCalendarToString(GregorianCalendar date) {
		return date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+" "+date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE);
	}
	
	public static int timeCompare(Time time1, Time time2) {
		if (time1.date.year < time2.date.year)
			return -1;
		if (time1.date.year > time2.date.year)
			return 1;
		if (time1.date.year == time2.date.year) {
			if (time1.date.month < time2.date.month)
				return -1;
			if (time1.date.month > time2.date.month)
				return 1;
			if (time1.date.month == time2.date.month) {
				if (time1.date.day < time2.date.day)
					return -1;
				if (time1.date.day > time2.date.day)
					return 1;
				if (time1.date.day == time2.date.day) {
					if (time1.moment.hour < time2.moment.hour)
						return -1;
					if (time1.moment.hour > time2.moment.hour)
						return 1;
					if (time1.moment.hour == time2.moment.hour) {
						if (time1.moment.minute < time2.moment.minute)
							return -1;
						if (time1.moment.minute > time2.moment.minute)
							return 1;
						if (time1.moment.minute == time2.moment.minute)
							return 0;
					}
				}
			}
		}
		return -2;
	}


}
