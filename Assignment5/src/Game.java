import java.awt.Dimension;
import javax.swing.JFrame;


/**
 * @author Travis Tibbetts
 * @version 1.0
 *
 */
public class Game {

	public static void main(String[] args) {
		GameBoard b = new GameBoard();
		b.setPreferredSize(new Dimension(450,450));
		b.setLocation(435, 200);
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b.pack();
		b.setVisible(true);
	}

}
