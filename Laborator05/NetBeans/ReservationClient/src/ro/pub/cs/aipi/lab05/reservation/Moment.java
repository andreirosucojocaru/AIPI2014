package ro.pub.cs.aipi.lab05.reservation;


/**
* reservation/Moment.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Reservation.idl
* Monday, November 10, 2014 3:19:56 AM EET
*/

public final class Moment implements org.omg.CORBA.portable.IDLEntity
{
  public int hour = (int)0;
  public int minute = (int)0;

  public Moment ()
  {
  } // ctor

  public Moment (int _hour, int _minute)
  {
    hour = _hour;
    minute = _minute;
  } // ctor

} // class Moment
