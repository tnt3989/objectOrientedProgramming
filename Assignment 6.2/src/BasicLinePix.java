import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.border.BevelBorder;

/**
 * @author Travis Tibbetts
 * @version 1.1
 *
 */
@SuppressWarnings("serial")
public class BasicLinePix extends JFrame {
	private JPanel drawingPanel; // user draws here
	private Container cp;
	private JPanel statusBar;
	private JLabel statusLabel;// used to show informational messages
	private JMenuBar menuBar;
	private JMenu fileMenu, fileMenu2;
	private EventHandler eh;
	private String mode = "Line"; //set default mode to line
	
	ArrayList<Shape> shapeList = new ArrayList<>();
	
	public static void main(String[] args) {
		BasicLinePix lp = new BasicLinePix();
		lp.setLocationRelativeTo(null); // sets location to middle of screen
		lp.setVisible(true);
	}

	/**
	 * Constructor builds content pane with all panels.
	 */
	public BasicLinePix() {
		setTitle("Basic Line Pix 1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		eh = new EventHandler(this);

		drawingPanel = makeDrawingPanel();
		drawingPanel.addMouseListener(eh);
		drawingPanel.addMouseMotionListener(eh);

		
		statusBar = createStatusBar();

		cp.add(drawingPanel, BorderLayout.CENTER);
		cp.add(statusBar, BorderLayout.SOUTH);

		
		buildMenu();
		pack();
		
	}

	/**
	 * Constructor builds the menu options.
	 */
	public void buildMenu() {	
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		fileMenu2 = new JMenu("Draw");
		
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
		
		menuItem = new JMenuItem("Line");
		menuItem.addActionListener(eh);
		fileMenu2.add(menuItem);
		
		menuItem = new JMenuItem("Rectangle");
		menuItem.addActionListener(eh);
		fileMenu2.add(menuItem);
		
		menuBar.add(fileMenu);
		menuBar.add(fileMenu2);
		
		
		setJMenuBar(menuBar);

	}

	/**
	 * Method creates a new JPanel with a background color and dimensions.
	 * 
	 * @return The JPanel
	 */
	private JPanel makeDrawingPanel() {
		JPanel p = new JPanel();
		p.setPreferredSize(new Dimension(500, 400));
		p.setBackground(Color.YELLOW);
		return p;
	}

	/**
	 * Method creates a new JPanel as a status bar at the bottom of the drawing panel.
	 * 
	 * @return The JPanel
	 */
	private JPanel createStatusBar() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		statusLabel = new JLabel("Draw a pretty picture.");
		panel.add(statusLabel, BorderLayout.CENTER);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED));
		return panel;
	}

	//this method overrides the paint method defined in JFrame
	//add code here for drawing the lines on the drawingPanel
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics g1 = drawingPanel.getGraphics();

		for (Shape s: shapeList) {
			s.draw(g1);
		}
		// Send a message to each line in the drawing to
		// draw itself on g1
	}

	/**
	 * Inner class - instances of this class handle action events
	 *
	 */
	private class EventHandler implements ActionListener, MouseListener, MouseMotionListener {
		BasicLinePix blp;
		public EventHandler (BasicLinePix blp) { //create a reference to BasicLinePix to dispose of old frame
			this.blp = blp;
		}

		private int startX, startY; // line's start coordinates
		private int lastX, lastY; // line's most recent end point
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			Graphics g1 = drawingPanel.getGraphics();
			
			if (arg0.getActionCommand().equals("Exit")) {//If exit option is selected
				statusLabel.setText("Exiting program...");
				System.exit(0);
			}
			if (arg0.getActionCommand().equals("New")) {
				BasicLinePix linePix = new BasicLinePix();
				linePix.setLocationRelativeTo(null); // sets location to middle of screen
				linePix.setVisible(true);
				shapeList = new ArrayList<>(); //Create new ArrayList
				blp.dispose(); //Dispose of old drawing frame 
			}
			if (arg0.getActionCommand().equals("Save")) { //If save option is selected
				JFileChooser fileChooser = new JFileChooser();//New file browser
				int returnValue = fileChooser.showSaveDialog(BasicLinePix.this);//Open file browser
				
				FileWriter fr;
				BufferedWriter br;
				try {
					fr = new FileWriter(fileChooser.getSelectedFile());
					br = new BufferedWriter(fr);
					for (Shape s: shapeList) {//For each Shape s in shapeList
						br.write(s.toString());//Write the string s to the file
						br.newLine();//Push new text to a new line
						}
					br.close();//Close file after writing
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
			if (arg0.getActionCommand().equals("Open")) { //If the open option is chosen
				JFileChooser fileChooser = new JFileChooser();//New file browser
				int returnValue = fileChooser.showOpenDialog(BasicLinePix.this);//Open file browser
				ArrayList<String> newStrings = new ArrayList<>();//Create new array list for each shape
				shapeList.clear();
				
				try {
					Scanner scanner = new Scanner(fileChooser.getSelectedFile());//New scanner to find file to open
					while (scanner.hasNext()) {//While file has text
						newStrings.add(scanner.nextLine());//add text to string list
					}
					for (String s: newStrings){
						String[] line = s.split(" ");
						String shapeLetter = line[0];
						String scannedStartX = line[1];
						String scannedStartY = line[2];
						String scannedLastX = line[3];
						String scannedLastY = line[4];
						
						if (s.contains("L")) {
							Line l2 = new Line(Integer.valueOf(scannedStartX), Integer.valueOf(scannedStartY), Integer.valueOf(scannedLastX), Integer.valueOf(scannedLastY));
							shapeList.add(l2);
						}
						else if (s.contains("R")) {
							Rectangle r2 = new Rectangle(Integer.valueOf(scannedStartX), Integer.valueOf(scannedStartY), Integer.valueOf(scannedLastX), Integer.valueOf(scannedLastY));
							shapeList.add(r2);
						}
						
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (Shape s: shapeList) {
					s.draw(g1);       
				}
				
			}
			if (arg0.getActionCommand().equals("Line")) {
				mode = "Line"; //set mode to line when clicked
				
			}
			if (arg0.getActionCommand().equals("Rectangle")) {
				mode = "Rectangle"; //set mode to rectangle when clicked
			}

		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			
			startX = e.getX();
			startY = e.getY();

			// initialize lastX, lastY
			lastX = startX;
			lastY = startY;
			
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// Implement rubber-band cursor
			Graphics g = drawingPanel.getGraphics();
			g.setColor(Color.black);
		
			g.setXORMode(drawingPanel.getBackground());
		
			// REDRAW the line that was drawn 
			// most recently during this drag
			// XOR mode means that yellow pixels turn black
			// essentially erasing the existing line
			if (mode == "Line") {
				g.drawLine(startX, startY, lastX, lastY);
				g.drawLine(startX, startY, e.getX(), e.getY());
			}
			else {
				g.drawRect(startX, startY, Math.abs(e.getX() - startX), Math.abs(e.getY() - startY));
				g.drawRect(startX, startY, Math.abs(lastX - startX), Math.abs(lastY - startY));
			}
			
			// draw line to current mouse position
			// XOR mode: yellow pixels become black
			// black pixels, like those from existing lines, temporarily become
			// yellow
			
			
			
			lastX = e.getX();
			lastY = e.getY();
			
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			if (mode == "Line") {
				Line releasedLine = new Line(startX, startY, lastX, lastY);
				shapeList.add(releasedLine);
			}
			else if (mode == "Rectangle") {
				Rectangle releasedRectangle = new Rectangle(startX, startY, lastX, lastY);
				shapeList.add(releasedRectangle);
			}
			
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}

}

