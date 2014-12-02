package ro.pub.cs.aipi.lab05.main;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import ro.pub.cs.aipi.lab05.general.Constants;
import ro.pub.cs.aipi.lab05.reservation.ReservationService;
import ro.pub.cs.aipi.lab05.reservation.ReservationServiceHelper;

public class Server {
    
    public static void main (String[] args) {
        
        try {
            
            // creare si initializare ORB
            ORB orb = ORB.init(args, null);
            
            // obtine referinta catre obiectul radacina POA si activeaza POAManager
            POA POARoot = POAHelper.narrow(orb.resolve_initial_references(Constants.POA_ROOT));
            POARoot.the_POAManager().activate();
            
            // creare instanta servant
            ReservationServiceImplementation reservationServiceImpl = new ReservationServiceImplementation();
            
            // obtine referinta catre obiectul distribuit de la instanta servant
            org.omg.CORBA.Object reservationServiceImplRef = POARoot.servant_to_reference(reservationServiceImpl);
            ReservationService reservationServiceRef = ReservationServiceHelper.narrow(reservationServiceImplRef);
            
            // obtine contextul de nume radacina
            org.omg.CORBA.Object nameServiceRef = orb.resolve_initial_references(Constants.SERVICE_NAME);
            NamingContextExt nameContextRef = NamingContextExtHelper.narrow(nameServiceRef);
            
            // legare referinta obiect distribuit la context de nume
            NameComponent nameComponent = new NameComponent(Constants.ORB_IDENTIFIER,"");
            NameComponent path[] = {nameComponent};
            nameContextRef.rebind (path, reservationServiceRef);
            
            System.out.println ("server started successfully");
            
            // asteptare de invocari de la clienti
            orb.run();
            
        }
        catch (Exception exception) {
            System.out.println("An exception has occurred :"+exception.getMessage());
            exception.printStackTrace();
        }
        
        System.out.println ("server stopped successfully");
    }
}
