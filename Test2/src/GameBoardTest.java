import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameBoardTest extends JFrame {
	GameTileTest q;
	GameTileTest selected;
	private int num = 0;
	public GameBoardTest() {
		setSize(600, 600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("99 Puzzle");

		Container c = getContentPane();
		c.add(createGamePanel());

		setVisible(true);

	}

	private JPanel createGamePanel() {
		JPanel p = new JPanel();

		p.setLayout(new GridLayout(10, 10));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				num++;
				final int value = num;
				if (num < 100) {
					String enumeration = String.valueOf(num);
					selected = new GameTileTest(enumeration, i, j);
					p.add(selected);
					selected.addActionListener(new ActionListener(){
		                public void actionPerformed(ActionEvent ae){
		                	swapLabels((GameTileTest) ae.getSource());
		                }
					});
				}
				else {
					q = new GameTileTest("?", i , j);
					p.add(q);
					q.addActionListener(new ActionListener(){
		                public void actionPerformed(ActionEvent ae){
		                	swapLabels((GameTileTest) ae.getSource());
		                }
					});
				}
			}
		}

		return p;
	}
	
	public void swapLabels(GameTileTest selected) {
		if (((q.getRow() + 1) == selected.getRow() && q.getCol() == selected.getCol()) || ((q.getRow() - 1) == selected.getRow() && q.getCol() == selected.getCol())) {
			String temp = selected.getText();
			selected.setText("?");
			q.setText(temp);
			q = selected;
		}
		else  if (((q.getCol() + 1) == selected.getCol() && q.getRow() == selected.getRow()) || ((q.getCol() - 1) == selected.getCol() && q.getRow() == selected.getRow())) {
			String temp = selected.getText();
			selected.setText("?");
			q.setText(temp);
			q = selected;
		}
	}

	public static void main(String[] args) {
		new GameBoardTest();
	}

}
