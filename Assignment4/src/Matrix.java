import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class defines methods to multiply, transpose, and add matrices.
 * 
 * @author Travis Tibbetts
 * 
 */
public class Matrix {
	private int rows;
	private int columns;
	private double[][] data;

	
	
	/**
	 * Constructor to initialize the values of the numbers of rows and columns.
	 * 
	 * @param rows the number of rows
	 * @param columns the number of columns
	 */
	public Matrix(int rows, int columns){
		this.rows = rows;
		this.columns = columns;
		data = new double[rows][columns];
	}
	
	/**
	 * Constructor to initialize a two-dimensional array
	 * 
	 * @param data initializes the number of rows and columns to be made for the matrix.
	 */
	public Matrix(double[][] data) {
		rows = data.length;
		columns = data[0].length;
		this.data = new double[rows][columns];
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < columns; j++){
				this.data[i][j] = data[i][j];
			}
		}
	}
	
	/**
	 * Constructor to read a text file and format it into a two-dimensional array.
	 * 
	 * @param filename the file path for the text file
	 */
	public Matrix(String filename) {
		ArrayList<String> lines;
		lines = readData(filename);
		rows = lines.size();
		String[] columnSize = lines.get(0).split(" ");
		columns = columnSize.length;
		data = new double[rows][columns];
			for(int i = 0; i < rows; i++){
				String[] iData = lines.get(i).split(" ");
				for (int j = 0; j < columns; j++){
					data[i][j] = Double.parseDouble(iData[j]);
				}
			}
	}
		
	/**
	 * Helper method to read all lines of a file into an ArrayList of strings
	 * 
	 * @param filename the file path to be read
	 * @return the ArrayList of strings. The information to be read.
	 */
	private static ArrayList<String> readData(String filename) {
		ArrayList<String> lines = new ArrayList<>();
		Scanner s = null;
		File infile = new File(filename);
		try {
			s = new Scanner(infile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (s.hasNext()) 
			lines.add(s.nextLine());

		return lines;
	}
	
	/**
	 * Method that checks whether a matrix's data is equivalent to another matrix's data.
	 * 
	 * @param second Matrix to be tested
	 * @return true or false whether the matrices are the same.
	 */
	public boolean equals(Matrix second){
		Matrix first = this;
		if (second.rows != first.rows || second.columns != first.columns){
			return false;
		}
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < columns; j++){
				if (first.data[i][j] != second.data[i][j]){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Method to transpose a matrix.
	 * 
	 * @return the transposed matrix
	 */
	public Matrix transpose() {
		double[][] transposed = new double[columns][rows];
		for (int i = 0; i < rows; i++){
			for (int j = 0; j < columns; j++){
				transposed[j][i] = this.data[i][j];
			}
		}
		return new Matrix(transposed);
	}

	/**
	 * Method to add the elements of one matrix to the elements of another matrix.
	 * 
	 * @param second Matrix that you are adding elements of.
	 * @return the added matrices
	 */
	public Matrix add(Matrix second) {
		double[][] added = new double[rows][columns];
		if (second.rows == this.rows || second.columns == this.columns){
				for (int i = 0; i < rows; i++){
					for (int j = 0; j < columns; j++){
						added[i][j] = this.data[i][j] + second.data[i][j];
					}
				}
				
			}
		return new Matrix(added);
	}
	
	/**
	 * Method to multiply a matrix by a scalar operation.
	 * 
	 * @param scalar the value which you want to multiply the matrix by.
	 * @return the matrix multiplied by the scalar value.
	 */
	public Matrix mult(double scalar) {
		Matrix array = this;
		Matrix scalarMult = new Matrix(rows, columns);
		for (int i = 0; i < array.rows; i++) {
			for (int j = 0; j < array.columns; j++){
				scalarMult.data[i][j] = (array.data[i][j] * scalar);
			}
		}
		return scalarMult;
	}

	
	/**
	 * Method to multiply a matrix by another matrix.
	 * 
	 * @param second matrix with which you will multiply 
	 * @return the matrices multiplied by each other.
	 */
	public Matrix mult(Matrix second) {
		Matrix first = this;
		Matrix multiplied = new Matrix(first.rows, second.columns);
		for (int i = 0; i < multiplied.rows; i++){
			for (int j = 0; j < multiplied.columns; j++){
				for (int n = 0; n < first.columns; n++){
					multiplied.data[i][j] += (first.data[i][n] * second.data[n][j]);
				}
			}
		}
		return multiplied;	
	}
	
	
	/* 
	 * Override toString method to produce a proper matrix format.
	 * 
	 * @return the string that will produce the matrix.
	 */
	public String toString() {
		String s = "";
		s += rows + "x" + columns + " matrix\n";
		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				s += data[i][j]+" ";
			}
			s += "\n";
		}
		return s;
	}

	
}
