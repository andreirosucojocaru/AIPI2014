package ro.pub.cs.aipi.lab06.general;

import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.xml.datatype.XMLGregorianCalendar;

public class Utilities {
	
    public static String convertGregorianCalendarToString(GregorianCalendar date) {
        return date.get(Calendar.DAY_OF_MONTH)+"/"+date.get(Calendar.MONTH)+"/"+date.get(Calendar.YEAR)+" "+date.get(Calendar.HOUR_OF_DAY)+":"+date.get(Calendar.MINUTE);
    }

    public static String convertGregorianCalendarToString(XMLGregorianCalendar date) {
        return date.getDay()+"/"+(date.getMonth()-1)+"/"+date.getYear()+" "+date.getHour()+":"+date.getMinute();
    }

}
