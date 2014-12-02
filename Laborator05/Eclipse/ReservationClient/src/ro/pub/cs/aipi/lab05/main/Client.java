package ro.pub.cs.aipi.lab05.main;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;

import ro.pub.cs.aipi.lab05.reservation.Date;
import ro.pub.cs.aipi.lab05.reservation.Interval;
import ro.pub.cs.aipi.lab05.reservation.Moment;
import ro.pub.cs.aipi.lab05.reservation.ReservationService;
import ro.pub.cs.aipi.lab05.reservation.ReservationServiceHelper;
import ro.pub.cs.aipi.lab05.reservation.Time;

public class Client {
    
    public static void main (String[] args) {
        
        try {
            // creare si initializare ORB
            ORB orb = ORB.init(args,null);
            
            // obtine contextul de nume radacina
            org.omg.CORBA.Object nameServiceRef = orb.resolve_initial_references("NameService");
            NamingContextExt namingContextRef = NamingContextExtHelper.narrow(nameServiceRef);
            
            // rezolvare referinta obiect distribuit prin serviciu de nume
            String serviceName = "ReservationService";
            ReservationService reservationServiceRef = ReservationServiceHelper.narrow(namingContextRef.resolve_str(serviceName));

            // TO DO: apeleaza metode obiect reservare
            System.out.println(reservationServiceRef.makeReservation(1, new Interval(new Time(new Date(10,11,2014),new Moment(10,45)),new Time(new Date(10,11,2014),new Moment(11,15))), 10));
            System.out.println(reservationServiceRef.makeReservation(2, new Interval(new Time(new Date(10,11,2014),new Moment(11,20)),new Time(new Date(10,11,2014),new Moment(11,40))), 10));
            System.out.println(reservationServiceRef.makeReservation(3, new Interval(new Time(new Date(10,11,2014),new Moment(10,00)),new Time(new Date(10,11,2014),new Moment(12,00))), 10));
            System.out.println(reservationServiceRef.cancelReservation(2, new Interval(new Time(new Date(10,11,2014),new Moment(11,20)),new Time(new Date(10,11,2014),new Moment(11,40)))));
            reservationServiceRef.getReservations(1);
        }
        catch (Exception exception) {
            System.out.println("An exception has occurred: "+exception.getMessage());
            exception.printStackTrace();
        }
    }
}
