/**
 * YOU MAY NOT DISCUSS THIS TEST OR SHARE THIS CODE WITH ANYONE, FOR ANY REASON, TILL THE TEST
 * IS RETURNED
 * YOU MAY NOT DISCUSS THIS TEST OR SHARE THIS CODE WITH ANYONE, FOR ANY REASON, TILL THE TEST
 * IS RETURNED
 * 
 * MAKE NO CHANGES TO THIS FILE (OTHER THAN COMMENTING OUT LINES FOR TESTING)
 * MAKE NO CHANGES TO THIS FILE (OTHER THAN COMMENTING OUT LINES FOR TESTING)
 * 
 * Imagine a large manufacturing facility that has many Stations. 
 * 
 * Each station has a name
 * name (which describes what happens there), an x coordinate, and a y
 * coordinate. Each Station is also automatically assigned a unique id, starting with 1, when the 
 * Station is created. The distance between two stations is the ordinary, straight-line distance
 * between the stations, calculated using the stations coordinates.
 * 
 * A Route is an ordered collection of stations. Each 
 * Route is also automatically assigned a unique id, starting with 1, when the 
 * Route is created. The
 * length of a Route is the sum of the distances between adjacent Stations on
 * the route, starting with the first station on the Route.
 * 
 * 
 * To get credit for a method tested, it must pass all associated tests. Otherwise, 
 * you do not get any credit for that method.
 * @author narayans
 *
 */
public class TestStop {
	private static int testCount = 0;

	public static void main(String[] args) {
		Station s1 = new Station("Heat", 0.0, 0.0);
		Station s2 = new Station("Cool", 12.5, 10.5);
		Station s3 = new Station("Dip", 3.5, 1.25);
		Station s4 = new Station("Wash", 24.25, 12.5);
		Station s5 = new Station("Soak", 23.5, 45.5);
		Station s6 = new Station("Rinse", 20.25, 12.5);
		Station s7 = new Station("Paint", 34.5, 87.2);
		Station s8 = new Station("Peel", 56.5, 23.3);
		Station s9 = new Station("Scrub", 45.5, 12.5);
		Station s10 = new Station("Etch", 123.4, 67.8);
		Station s11 = new Station("Coat", 45.5, 125.5);
		Station s12 = new Station("Wax", 100, 250.5);

		printTest("s4.toString", s4.toString(), "[4. (24.25,12.5) --> Wash]");
		printTest("s1.toString", s1.toString(), "[1. (0.0,0.0) --> Heat]");
		printTest("s9.toString", s9.toString(), "[9. (45.5,12.5) --> Scrub]");
		
		
		printTest("s2.distanceTo(s4)", s2.distanceTo(s4), 11.918);
		
		printTest("s3.distanceTo(s8)", s3.distanceTo(s8), 57.403);
		
		printTest("s11.distanceTo(s7)", s11.distanceTo(s7), 39.848);

		Route r1 = new Route();
		r1.add(s1);
		r1.add(s3);
		r1.add(s6);

		printTest("r1.toString", r1.toString(), "[1. Heat --> Dip --> Rinse]");
		
		printTest("r1.length", r1.length(), 23.893);
		
		Route r2 = new Route();
		r2.add(s12);
		r2.add(s3);
		
		printTest("r2.toString", r2.toString(), "[2. Wax --> Dip]");
		
		printTest("r2.length", r2.length(), 267.278);
		
		Route r3 = r1.add(r2); 
		//r3 contains all stations in r1 followed by stations in r2
		
		printTest("r3.toString", r3.toString(), "[3. Heat --> Dip --> Rinse --> Wax --> Dip]");
		
		printTest("r3.length", r3.length(), 542.178);
		
		r1.add(s12);
		
		printTest("r1.toString", r1.toString(), "[1. Heat --> Dip --> Rinse --> Wax]");
		
		printTest("r1.length", r1.length(), 23.893+s6.distanceTo(s12));
	}

	private static void printTest(String s, double actual, double expected) {
		// TODO Auto-generated method stub
		printTestHeader(s, Double.toString(actual), Double.toString(expected));
		
		if (Math.abs(actual-expected) <= 0.001)
			System.out.println("Pass");
		else
			System.out.println("Fail");

		System.out.println();
		
	}

	private static void printTest(String s, String actual, String expected) {
		printTestHeader(s, actual, expected);
		if (actual.equals(expected))
			System.out.println("Pass");
		else
			System.out.println("Fail");

		System.out.println();
	}

	private static void printTestHeader(String s, String actual, String expected) {
		testCount++;
		System.out.println("\tTesting " + s);
		System.out.println("\tActual = "+actual+" , Expected = "+expected);
		System.out.print("\tTest " + testCount + ". ");
	}

}
