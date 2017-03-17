import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;


/**
 * @author Travis Tibbetts
 * @version 1.1
 *
 */
public class Game {

	public static void main(String[] args) {
		GameBoard b = new GameBoard();
		b.setPreferredSize(new Dimension(650,650));
		b.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		b.pack();
		b.setLocationRelativeTo(null); // sets location to middle of screen
		b.setVisible(true);
	}

}
