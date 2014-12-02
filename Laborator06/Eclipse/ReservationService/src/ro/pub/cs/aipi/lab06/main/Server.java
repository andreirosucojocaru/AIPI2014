package ro.pub.cs.aipi.lab06.main;

import javax.xml.ws.Endpoint;

import ro.pub.cs.aipi.lab06.reservation.Reservation;

public class Server {
	
    public static void main (String[] args) {
    	Endpoint.publish("http://localhost:8080/reservation/Reservation",new Reservation());
    }

}
