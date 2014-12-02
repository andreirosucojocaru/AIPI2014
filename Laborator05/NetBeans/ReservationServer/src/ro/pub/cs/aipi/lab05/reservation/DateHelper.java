package ro.pub.cs.aipi.lab05.reservation;


/**
* reservation/DateHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from Reservation.idl
* Monday, November 10, 2014 3:20:14 AM EET
*/

abstract public class DateHelper
{
  private static String  _id = "IDL:reservation/Date:1.0";

  public static void insert (org.omg.CORBA.Any a, ro.pub.cs.aipi.lab05.reservation.Date that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static ro.pub.cs.aipi.lab05.reservation.Date extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  private static boolean __active = false;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      synchronized (org.omg.CORBA.TypeCode.class)
      {
        if (__typeCode == null)
        {
          if (__active)
          {
            return org.omg.CORBA.ORB.init().create_recursive_tc ( _id );
          }
          __active = true;
          org.omg.CORBA.StructMember[] _members0 = new org.omg.CORBA.StructMember [3];
          org.omg.CORBA.TypeCode _tcOf_members0 = null;
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[0] = new org.omg.CORBA.StructMember (
            "day",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[1] = new org.omg.CORBA.StructMember (
            "month",
            _tcOf_members0,
            null);
          _tcOf_members0 = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
          _members0[2] = new org.omg.CORBA.StructMember (
            "year",
            _tcOf_members0,
            null);
          __typeCode = org.omg.CORBA.ORB.init ().create_struct_tc (ro.pub.cs.aipi.lab05.reservation.DateHelper.id (), "Date", _members0);
          __active = false;
        }
      }
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static ro.pub.cs.aipi.lab05.reservation.Date read (org.omg.CORBA.portable.InputStream istream)
  {
    ro.pub.cs.aipi.lab05.reservation.Date value = new ro.pub.cs.aipi.lab05.reservation.Date ();
    value.day = istream.read_long ();
    value.month = istream.read_long ();
    value.year = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, ro.pub.cs.aipi.lab05.reservation.Date value)
  {
    ostream.write_long (value.day);
    ostream.write_long (value.month);
    ostream.write_long (value.year);
  }

}
