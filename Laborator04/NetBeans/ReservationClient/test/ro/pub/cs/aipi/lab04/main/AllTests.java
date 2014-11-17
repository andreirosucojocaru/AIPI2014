package ro.pub.cs.aipi.lab04.main;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Exercise03Test.class,
	Exercise04Test.class,
	Exercise05Test.class,
	Exercise06Test.class,
	Exercise09Test.class
})
public class AllTests {
	
	@BeforeClass
	public static void setup() {
		System.setProperty("java.rmi.server.codebase", "file:///D:/Dropbox/cursuri/automatica_si_calculatoare/aplicatii_integrate_pentru_intreprinderi/2014_2015/laboratoare/Laborator04/cod_sursa/rezolvate/NetBeans/ReservationServer/libs/reservation.jar");
		System.setProperty("java.security.policy", "configuration/policy");
	}
}