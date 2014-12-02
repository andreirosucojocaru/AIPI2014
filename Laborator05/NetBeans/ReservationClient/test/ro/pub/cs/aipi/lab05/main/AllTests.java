package ro.pub.cs.aipi.lab05.main;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import ro.pub.cs.aipi.lab05.main.Exercise03Test;
import ro.pub.cs.aipi.lab05.main.Exercise04Test;
import ro.pub.cs.aipi.lab05.main.Exercise05Test;
import ro.pub.cs.aipi.lab05.main.Exercise06Test;
import ro.pub.cs.aipi.lab05.main.Exercise09Test;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	Exercise03Test.class,
	Exercise04Test.class,
	Exercise05Test.class,
	Exercise06Test.class,
	Exercise09Test.class
})
public class AllTests {
}