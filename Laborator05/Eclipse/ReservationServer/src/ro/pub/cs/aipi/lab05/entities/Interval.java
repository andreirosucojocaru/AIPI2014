package ro.pub.cs.aipi.lab05.entities;

import java.util.GregorianCalendar;

import ro.pub.cs.aipi.lab05.general.Utilities;

public class Interval {
	
	GregorianCalendar startingTime;
	GregorianCalendar endingTime;

	public Interval() {
		startingTime = new GregorianCalendar();
		endingTime = new GregorianCalendar();
	}

	public Interval(GregorianCalendar startingTime, GregorianCalendar endingTime) {
		this.startingTime = startingTime;
		this.endingTime = endingTime;
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
	
	public boolean equals(Interval interval) {
		return Utilities.convertGregorianCalendarToString(this.getStartingTime()).equals(Utilities.convertGregorianCalendarToString(interval.getStartingTime())) 
				&& Utilities.convertGregorianCalendarToString(this.getEndingTime()).equals(Utilities.convertGregorianCalendarToString(interval.getEndingTime()));
	}
}