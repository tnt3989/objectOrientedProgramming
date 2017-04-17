import java.awt.Graphics;

/**
 * @author Travis Tibbetts
 * @version 1.1
 * 
 * Line class inherits all of Shape. Line draws and returns the coordinates of a line.
 */
public class Line extends Shape{
	
	private int startX, startY, lastX, lastY;
	
	/**
	 * Constructor defines the coordinates of the beginning and end of a line.
	 * 
	 * @param startX Beginning x coordinate of line drawn
	 * @param startY Beginning y coordinate of line drawn
	 * @param lastX Ending x coordinate of line drawn
	 * @param lastY Ending y coordinate of line drawn
	 */
	public Line (int startX, int startY, int lastX, int lastY) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = lastX;
		this.lastY = lastY;
	}

	/**
	 * Method draws the line defined by the coordinates.
	 * 
	 * @param g1 Reference to which line to draw.
	 */
	public void draw(Graphics g1) {
		g1.drawLine(startX, startY, lastX, lastY);
	}
	
	/** 
	 * Method overrides toString method to produce the x,y coordinates for beginning and end of a line.
	 */
	public String toString() {
		return "L " + startX + " " + startY + " " + lastX + " " + lastY;
	}

}
