import java.awt.Dimension;

import javax.swing.JButton;

public class GameTileTest extends JButton {
	private int row;
	private int col;
	private static int width = 100, height = 100;
	
	public GameTileTest(String label, int row, int col) {
		setPreferredSize(new Dimension(width, height));
		setText(label);
		this.row = row;
		this.col = col;
		
	}
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
}
