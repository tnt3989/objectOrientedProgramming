import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

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
 * Cell extends JFormattedTextField and defines all of the properties of a cell. Each cell knows its position
 * in the array, and its value input by the user. The cell knows which numbers are available for input,
 * can reset the available input numbers, and can reset the value of an individual cell.
 *
 */
public class Cell extends JFormattedTextField{
	private Integer sudoValue;
	private Font cellFont = new Font("SansSerif", Font.BOLD, 30);
	private int _block;
	private int _row;
	private int _col;
	private ArrayList<Integer> available;
	
	/**
	 * Cell constructor defines the size and overall visual aspect of each JFormattedTextField. It defines
	 * which block, row, and column the cell is in. 
	 * 
	 * @param block Defines which 3x3 grid position the cell is in.
	 * @param row Defines which row the cell is in.
	 * @param col Defines which column the cell is in.
	 */
	public Cell(int block, int row, int col) {
		this.setSize(new Dimension(65, 65));
		this.setHorizontalAlignment(JTextField.CENTER);
		this.setForeground(Color.BLACK);
		this.setFont(cellFont);
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setEditable(true);
		_block = block;
		_row = row;
		_col = col;
		available = new ArrayList<Integer>();
		sudoValue = 0;
		
	}
	
	/**
	 * Method paints the background of a cell with the selected color.
	 * 
	 * @param c Color to re-color a cell.
	 */
	public void paintCell(Color c) {
		this.setBackground(c);
		this.paintImmediately(0, 0, 65, 65);
	}
	
	/**
	 * Method returns the value of which 3x3 block a cell is located in.
	 * 
	 * @return _block The value of the cell's block.
	 */
	public int getBlock() {
		return _block;
	}
	
	/**
	 * Method returns the value of which row the cell is located in.
	 * 
	 * @return _row The value of the cell's row.
	 */
	public int getRow() {
		return _row;
	}
	
	/**
	 * Method returns the value of which column the cell is located in.
	 * 
	 * @return _col The value of the cell's column.
	 */
	public int getCol() {
		return _col;
	}
	
	/** 
	 * Method returns the value of an individual cell
	 * 
	 * @return sudoValue The value that has been input to the cell.
	 */
	public Object getValue() {
		return this.sudoValue;
	}
	
	/**
	 * Method returns a string with the values of a cell's block, row, and column.
	 * 
	 */
	public String toString() {
		return "Block: " + String.valueOf(this._block) + " Row: " + String.valueOf(this._row) + " Col: " + String.valueOf(this._col);
	}
	
	/**
	 * Method sets the value of a cell by gathering the input from the user.
	 * 
	 */
	public void setSudoVal() {
		this.sudoValue = Integer.valueOf(this.getText());
	}
	
	/**
	 * Method sets the value of a cell by gathering the input from the user.
	 * 
	 * @param s String that will be tested to set the value of a cell.
	 */
	public void setSudoVal(String s) {
		if(s.equals("0")) {
			this.sudoValue = 0;
		} else {
			this.sudoValue = Integer.valueOf(s);
		}
	}
	
	/**
	 * Method that resets the value of a cell.
	 * 
	 */
	public void dumpValue() {
		this.sudoValue = 0;
		this.setText(null);
	}
	
	/**
	 * Method that resets the legal input list displayed to the user.
	 * 
	 */
	public void dumpAvailable() {
		available.clear();
	}
	
	/**
	 * Method that adds an integer to the legal input list.
	 * 
	 * @param a Integer value that is being added to the legal input list.
	 */
	public void addAvailable(Integer a) {
		available.add(a);
	}
	
	/**
	 * Method that creates the string of legal inputs to be displayed to the user.
	 * 
	 * @return finalAvailable The string with all choices available to be displayed to the user.
	 */
	public String toolTipText() {
		String finalAvailable = "Legal choices: ";
		for(Integer i: available) {
			finalAvailable += (i + " ");
		}
		return finalAvailable;
	}
}
