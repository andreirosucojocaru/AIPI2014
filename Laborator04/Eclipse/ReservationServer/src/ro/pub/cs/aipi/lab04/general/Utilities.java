package ro.pub.cs.aipi.lab04.general;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Utilities {
	
	public static String convertGregorianCalendarToString(GregorianCalendar date) {
		return date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+" "+date.get(Calendar.HOUR)+":"+date.get(Calendar.MINUTE);
	}

}
