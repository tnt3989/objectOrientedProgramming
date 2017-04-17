
public class Station {
	private String name;
	private double xCoord;
	private double yCoord;
	private static int counter = 0;
	private int stationID;

	public Station(String n, double x, double y){
		name = n;
		xCoord = x;
		yCoord = y;
		counter++;
		stationID = counter;
	}
	
	public String getName(){
		return name;
	}
	
	public double distanceTo(Station second){
		Station first = this;
		double xDistance = (second.xCoord - first.xCoord);
		double yDistance = (second.yCoord - first.yCoord);
		xDistance = (xDistance * xDistance);
		yDistance = (yDistance * yDistance);
		double xPlusY = xDistance + yDistance;
		double distance = Math.sqrt(xPlusY);
		return distance;
	}
	
	public String toString(){
		return "[" + stationID + "." + " " + "(" + xCoord +"," + yCoord + ")" + " "+ "-->" + " " + name + "]";
	}
	
}
