package ro.pub.cs.aipi.lab06.entities;

import java.util.GregorianCalendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name="Interval")
public class Interval {

    GregorianCalendar startingTime;
    GregorianCalendar endingTime;

    public Interval() {
        startingTime = new GregorianCalendar();
	endingTime   = new GregorianCalendar();
    }

    public Interval(GregorianCalendar startingTime, GregorianCalendar endingTime) {
	this.startingTime = startingTime;
	this.endingTime   = endingTime;
    }

    public void setStartingTime(GregorianCalendar startingTime) {
    	this.startingTime = startingTime;
    }
    
    public GregorianCalendar getStartingTime() {
	return startingTime;
    }

    public void setEndingTime(GregorianCalendar endingTime) {
	this.endingTime = endingTime;
    }

    public GregorianCalendar getEndingTime() {
    	return endingTime;
    }

}
