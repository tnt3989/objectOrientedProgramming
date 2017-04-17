import java.awt.Graphics;

/**
 * @author Travis
 * @version 1.1
 * 
 * Rectangle class inherits all of Shape. Rectangle draws and returns the vertices of a rectangle.
 */
public class Rectangle extends Shape{
	
	private int startX, startY, lastX, lastY, width, height;
	
	/**
	 * Constructor defines the vertices of a rectangle, and the width and height.
	 * 
	 * @param startX The starting x coordinate of a rectangle
	 * @param startY The starting y coordinate of a rectangle
	 * @param lastX The ending x coordinate of a rectangle
	 * @param lastY The ending y coordinate of a rectangle
	 */
	public Rectangle(int startX, int startY, int lastX, int lastY) {
		this.startX = startX;
		this.startY = startY;
		this.lastX = lastX;
		this.lastY = lastY;
		this.width = Math.abs(startX - lastX);
		this.height = Math.abs(startY - lastY);
	}
	
	/**
	 * Method draw the rectangle defined by the vertices.
	 * 
	 * @param g1 Reference to which rectangle to draw.
	 */
	public void draw(Graphics g1) {
		g1.drawRect(startX, startY, width, height);
	}
	/**
	 * Method overrides toString method to produce the x,y coordinates for the vertices of a rectangle.
	 */
	public String toString() {
		return "R " + startX + " " + startY + " " + lastX + " " + lastY;
	}

	

}
