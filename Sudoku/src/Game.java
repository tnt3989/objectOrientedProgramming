import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


/**
 * @author Daniel Orsa, Travis Tibbetts
 * @version 1.0
 * 14 April 2017
 *
 * This sudoku solver is designed to take input from a user and help solve any sudoku puzzle. The user should 
 * enter the predetermined numbers into the correct cell, and the solver will provide available numbers
 * to place into the other cells. It follows the rules of sudoku and will not allow the user to enter 
 * numbers into conflicting cells.
 * 
 * Game extends JFrame and creates a new frame which holds an array of JTextFields. Game defines each 3x3 grid
 * for a sudoku game. It builds and provides functionality to a game menu with options to start new, save a
 * game, open a saved game, and exit the program. 
 */
public class Game extends JFrame{
	private JPanel board, notificationArea, cellArea;
	private JLabel notificationLabel;
	private EventHandler eh;
	private Container c;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private Cell[][] cellList;
	private int blockNum;
	private ArrayList<Integer> notAvailable;

	
	/**
	 * Method executes a new game.
	 * 
	 */
	public static void main(String[] args) {
		new Game();
	}

	/**
	 * Constructor defines the size of the JFrame and displays it to the user. Creates a new array of cells.
	 * 
	 */
	public Game() {
		setTitle("Sudoku");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c = this.getContentPane();
		c.setPreferredSize(new Dimension(600, 625));

		cellList = new Cell[9][9];

		setUpBoard();
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Method defines the layout of the game board. It defines the location of the 3x3 block location,
	 * and adds the block value to the correct cells. The block value defines which cells are to be painted.
	 * 
	 */
	public void setUpBoard() {
		eh = new EventHandler();
		
		buildMenu();

		board = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
		board.setBackground(Color.DARK_GRAY);

		// notification area //
		notificationArea = new JPanel();
		notificationArea.setPreferredSize(new Dimension(600, 25));
		notificationArea.setBackground(null);
		// show user notifications
		notificationLabel = new JLabel();
		notificationLabel.setText("Welcome to Sudoku!");
		notificationLabel.setForeground(Color.WHITE);

		notificationArea.add(notificationLabel);
		board.add(notificationArea);

		// cell area //
		cellArea = new JPanel(new GridLayout(9, 9, 3, 3));
		cellArea.setPreferredSize(new Dimension(585, 585));
		cellArea.setBackground(Color.DARK_GRAY);
		
		int[] blockNum0_2 = {0, 0, 0, 1, 1, 1, 2, 2, 2};
		int[] blockNum3_5 = {3, 3, 3, 4, 4, 4, 5, 5, 5};
		int[] blockNum6_8 = {6, 6, 6, 7, 7, 7, 8, 8, 8};
		
		for(int i=0; i < 9; i++) {
			for(int j=0; j<9; j++) {
				if(i <= 2) {
					blockNum = blockNum0_2[j];
				} else if(i >=3 && i <=5) {
					blockNum = blockNum3_5[j];
				} else if(i >= 6) {
					blockNum = blockNum6_8[j];
				}
				Cell cell = new Cell(blockNum, i, j);
				cell.addMouseListener(eh);
				cell.addKeyListener(eh);
				if((i <= 2 | i >= 6) && (j <= 2 | j >= 6)) {
					cell.paintCell(Color.CYAN);
				} else if((i >=3 && i <= 5) && (j >=3 && j <= 5)) {
					cell.paintCell(Color.CYAN);
				} else {
					cell.paintCell(Color.LIGHT_GRAY);
				}
				cellArea.add(cell);
				cellList[i][j] = cell;
			}
		}
		
		board.add(cellArea);

		c.add(board);
	}

	/**
	 * Method builds the game menu, allowing options for a new game, save game, open game, and exit game.
	 * 
	 */
	public void buildMenu() {	
		menuBar = new JMenuBar();
		fileMenu = new JMenu("Game");

		JMenuItem menuItem = new JMenuItem("New");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Open");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Save");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuItem = new JMenuItem("Exit");
		menuItem.addActionListener(eh);
		fileMenu.add(menuItem);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
	}
	/**
	 * Method displays which numbers are available for input into a cell.
	 * 
	 * @param currentCell The cell that is being edited. 
	 */
	public void displayAvailable(Cell currentCell) {
		notAvailable = new ArrayList<Integer>();
		currentCell.dumpAvailable();
		int[] choices = {1, 2, 3, 4, 5, 6, 7, 8, 9}; 
		
		for(Cell[] cellRow: cellList) {
			for(Cell c: cellRow) {
				if(c.getRow() == currentCell.getRow() | c.getCol() == currentCell.getCol() | c.getBlock() == currentCell.getBlock()) {
					if(c.getValue() != null) {
						notAvailable.add((Integer) c.getValue());
					}
				}
			}
    	}
		for(Integer a: choices) {
			boolean add = true;
			for(Integer na: notAvailable) {
				if(a == na) {
					add = false;
				}
			}
			if(add == true) {
				currentCell.addAvailable(a);
			}
		}
		currentCell.setToolTipText(currentCell.toolTipText());
	}
	
	/**
	 * Method checks whether the input into the cell is legal or not.
	 * 
	 * @param currentCell The cell that is being edited.
	 * @return false if the input is not a legal integer, otherwise return true.
	 */
	public boolean checkAvailable(Cell currentCell) {
		for(Integer i: notAvailable) {
			if(Integer.valueOf(currentCell.getText()) == i) {
				return false;
			}
		}
		return true;
	}

	/**
	 * EventHandler implements ActionListener, MouseListener, and KeyListener. Adds functionality to 
	 * the game menu and allows the user to start a new game, save a game, open a game, and exit the game.
	 * Gathers information from the MouseListener to know which cell has been selected, show the tool 
	 * tip box of legal inputs. KeyListener gathers information on which key has been pressed. If the backspace
	 * has been pressed, the available input list gets updated. The KeyListener also tests for a legal
	 * input and displays an error dialog message if it detects one, and resets the cell. 
	 * 
	 */
	private class EventHandler implements ActionListener, MouseListener, KeyListener{
		private Cell enteredCell;
		private Cell clickedCell;
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getActionCommand().equals("Exit")) {//If exit option is selected
				System.exit(0);
			}
			if (arg0.getActionCommand().equals("New")) {
				setVisible(false);
				Game newGame = new Game();
				newGame.setLocationRelativeTo(null); // sets location to middle of screen
				newGame.setVisible(true);
			}
			
			if (arg0.getActionCommand().equals("Save")) { //If save option is selected
				JFileChooser saveGame = new JFileChooser();//New file browser
				int saveValue = saveGame.showSaveDialog(null);//Open file browser
				
				if(saveValue == JFileChooser.APPROVE_OPTION) {
			    	
	            try {
	            	FileWriter fw = new FileWriter(saveGame.getSelectedFile()+".txt");
	            	BufferedWriter bw = new BufferedWriter(fw);
	            	for(Cell[] cellRow: cellList) {
	    				for(Cell c: cellRow) {
	    					bw.write(String.valueOf(c.getValue()) + " ");
	    				}
	    				bw.newLine();
	            	}
	            	bw.close();
				} catch (IOException e) {
					e.printStackTrace();
					}
	            }
			}
			    
			if (arg0.getActionCommand().equals("Open")) { //If the open option is chosen
				for(Cell[] cellRow: cellList) {
					for(Cell c: cellRow) {
						c.dumpAvailable();
						c.dumpValue();
					}
				}
				JFileChooser openGame = new JFileChooser();//New file browser
				int openValue = openGame.showOpenDialog(null);//Open file browser
				if(openValue == JFileChooser.APPROVE_OPTION) {
					String[] lines = new String[9];
			        Scanner scan = null;
					File selectedFile = openGame.getSelectedFile();
			        try {
						scan = new Scanner(selectedFile);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} 
			        for(int i=0; i<9; i ++) {
			        	lines[i] = scan.nextLine();
			        }
			        
			        for(int i=0; i<9; i ++) {
			        	String[] lineStr = lines[i].split(" ");
			        	for(int j=0; j<9; j ++) {
			        		if(lineStr[j].equals("0")) {
			        			cellList[i][j].setSudoVal("0");
			        		} else {
			        			cellList[i][j].setSudoVal(lineStr[j]);
			        			cellList[i][j].setText(lineStr[j]);
			        		}
			        	}
			        }
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			Object o = arg0.getSource();
			if(o instanceof Cell) {
				clickedCell = (Cell) o;
			}
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			Object o = arg0.getSource();
			if(o instanceof Cell) {
				enteredCell = (Cell) o;
			}
			displayAvailable(enteredCell);
			
		}
		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent arg0) {

			
		}
		@Override
		public void keyReleased(KeyEvent arg0) {
			Object o = arg0.getSource();
			if(o instanceof Cell) {
				clickedCell = (Cell) o;
			}
			
			if(arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				clickedCell.dumpValue();
			} else if(checkAvailable(clickedCell) && clickedCell.getText().length() == 1) {
				clickedCell.setSudoVal();
			} else {
				JOptionPane.showMessageDialog(null, "That number is not a legal choice for that cell.", "Whoops!", JOptionPane.WARNING_MESSAGE);
				clickedCell.dumpValue();
			}
			
		}
		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	}	
}