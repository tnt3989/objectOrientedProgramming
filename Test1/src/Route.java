import java.util.ArrayList;

public class Route {
	private Station station;
	private static int counter = 0;
	private int routeID;
	private ArrayList<Station> routeList = new ArrayList<Station>();

	public Route(){
		counter++;
		routeID = counter;
		
	}
	public void add(Station nextStation){
		routeList.add(nextStation);
		
	}
	public Route add(Route route){
		Route r = new Route();
		ArrayList<Station> array = new ArrayList<Station>();
		for (Station s: routeList){
			r.add(s);
		}
		array = route.getArray();
		for(Station nextStation: array){
			r.add(nextStation);
		}
		return r;
	}
		
	public ArrayList<Station>getArray() {
		return routeList;
	}
	
	public double length() {
		double length = 0;
		for (int i = 0; i < routeList.size()-1; i++) {
			Station current = routeList.get(i);
			Station next = routeList.get(i+1);
			length = length + current.distanceTo(next);
			}
		return length;
		
	}
	

	public String toString(){
		String printOut = "";
		for (int i = 0; i < routeList.size(); i++){
			if (i == routeList.size()-1) {
				printOut = printOut + " " +  routeList.get(i).getName();
			}
			else {
				printOut = printOut+ " " + routeList.get(i).getName() + " -->";
			}
		}
		return "[" + routeID + "." + printOut + "]";
	}
}
