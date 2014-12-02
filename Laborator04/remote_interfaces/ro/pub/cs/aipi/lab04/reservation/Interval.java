package ro.pub.cs.aipi.lab04.reservation;

import java.io.Serializable;
import java.util.GregorianCalendar;

public class Interval implements Serializable {
	
	private static final long serialVersionUID = 1024L;
	
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
}