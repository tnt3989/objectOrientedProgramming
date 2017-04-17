import java.awt.Color;
import java.awt.Graphics;

/**
 * @author Travis
 * @version 1.1
 * 
 * Abstract class to define a shape. All children are types of shapes.
 */
public abstract class Shape { //Cannot instantiate a new shape object since class is abstract
	//							Simply meant to be built upon. They are meant to be extended.
	
	/**
	 * Constructor that still allows common characteristics to be defined.
	*/
	public Shape() {
		
	}

	/**
	 * Abstract method requires each child to define their own draw method for their specific shape.
	 * 
	 * @param g1 Draws the shape defined by each child.
	 */
	public abstract void draw(Graphics g1);

}
