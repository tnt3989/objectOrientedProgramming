import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Collections;

/**
 * @author Travis Tibbetts
 * @version 1.1
 */
public class GameBoard extends JFrame{
    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer timer;
    private static int counter;
    private String pairs;
    private double col1 = 0;
    private int rows, columns = 0;
    
    /**
     * Constructor to build ArrayLists of cards and colors, add colors to ArrayList of colors,
     * 
     */
    public GameBoard(){

    	// create arrayLists of cards and colors
        ArrayList<Card> cardsList = new ArrayList<Card>();
        ArrayList<Color> cardColors = new ArrayList<Color>();
        ArrayList<String> cardStrings = new ArrayList<String>();
        ArrayList<Color> listColors = new ArrayList<Color>();
        ArrayList<String> listStrings = new ArrayList<String>();
        
        // add all colors to ArrayList of colors
        cardColors.add(Color.BLUE);
        cardColors.add(Color.GREEN);
        cardColors.add(Color.MAGENTA);
        cardColors.add(Color.ORANGE);
        cardColors.add(Color.RED);
        cardColors.add(Color.YELLOW);
        
        // add all color strings to ArrayList of Strings
        cardStrings.add("Blue");
        cardStrings.add("Green");
        cardStrings.add("Magenta");
        cardStrings.add("Orange");
        cardStrings.add("Red");
        cardStrings.add("Yellow");
        
        //prompt user for the number of card pairs
        pairs = JOptionPane.showInputDialog("How many cards would you like to play with?");
    	int numberPairs = Integer.valueOf(pairs); //get value of user input
    	Random r = new Random(); // create new Random
    	int textPairs = r.nextInt(numberPairs-1) + 1; // number of text pairs 
    	int colorPairs = numberPairs - textPairs; // number of color pairs
    	for (int i = 0; i < colorPairs; i++) {
    		Random r2 = new Random();
    		int index = r2.nextInt(6); // 6 available colors
    		listColors.add(cardColors.get(index));
    	}
    	for (int i = 0; i < textPairs; i++) {
    		Random r3 = new Random();
    		int index = r3.nextInt(6); // 6 available color strings
    		listStrings.add(cardStrings.get(index));
    	}
    	
    	int totalCards = numberPairs * 2;
    	col1 = Math.sqrt(totalCards);
    	int testSq = (int) col1;
    	
    	if (Math.pow(col1, 2) == Math.pow(testSq, 2)) {
    		rows = (int) col1;
    		columns = (int) col1;
    	}
    	else {
    		double columns2 = col1 + 1;
    		rows = (int) columns2;
    		columns = (int) col1;
    		if (columns * rows < totalCards) {
    			rows = rows + 1;
    		}
    	}
        
        
        for (Color colors : listColors){ //for each color in cardColors
            Card c1 = new Card(); // initiate new cards
            Card c2 = new Card();
            c1.setColor(colors); // set the color of each card at index colors
            c2.setColor(colors);
            c1.addActionListener(new ActionListener(){ // add actionListener for click on c1
                public void actionPerformed(ActionEvent ae){
                    selectedCard = c1;
                    doTurn();
                }
            });
            c2.addActionListener(new ActionListener() { // add actionListener for click on c2
            	public void actionPerformed(ActionEvent ae){
            		selectedCard = c2;
            		doTurn();
            	}
            
            });
            cardsList.add(c1); //add the cards to the list of cards available to be played
            cardsList.add(c2);
        }
        
        for (String colorName : listStrings){ //for each color name in cardStrings
            Card c1 = new Card(); // initiate new cards
            Card c2 = new Card();
            c1.setName(colorName);// set the color name of each card at index colorName
            c2.setName(colorName);
            c1.addActionListener(new ActionListener(){ // add actionListener for click on c1
                public void actionPerformed(ActionEvent ae){
                    selectedCard = c1;
                    doTurn();
                }
            });
            c2.addActionListener(new ActionListener() { // add actionListener for click on c2
            	public void actionPerformed(ActionEvent ae){
            		selectedCard = c2;
            		doTurn();
            	}
            
            });
            cardsList.add(c1); //add the cards to the list of cards available to be played
            cardsList.add(c2);
        }
        
        
        Collections.shuffle(cardsList); // shuffles the cards
        this.cards = cardsList;
        timer = new Timer(800, new ActionListener(){ // initiate timer and new actionListener
            public void actionPerformed(ActionEvent ae){
            	if (c1.getColor() != null) {
            		checkColors();
            	}
            	else if (c1.getColor() == null) {
                	checkNames();
                }
            }
        });

        timer.setRepeats(false);

        Container pane = getContentPane(); // create the GameBoard window pane
        pane.setLayout(new GridLayout ( rows, columns, 10, 10)); // set layout as grid with 3 rows 4 columns, 10 height difference, 10 vertical difference
        
        for (Card c : cards){ // for each card in cards list
        	c.setBackground(Color.GRAY); // set the back face to gray
            pane.add(c); // add each card to the pane
        }
        setTitle("Concentration Game");
    }

    /**
     * Method checks if cards are reset, flips the cards, and adds to the counter.
     */
    public void doTurn(){
    	if (c1 == null && c2 == null){
    		c1 = selectedCard;
    		c1.setBackground(c1.getColor()); // flips the selected card
    		c1.setText(c1.getName());
    		counter++; // +1 to click counter
    	}

    	if (c1 != null && c1 != selectedCard && c2 == null) {
    		c2 = selectedCard;
   			c2.setBackground(c2.getColor());
   			c2.setText(c2.getName());
    		timer.start(); // creates the pause to memorize colors shown
    		counter++; // +1 to click counter
    	}
    }
 
    /**
     *   Method checks whether the cards are matches, if they match turns off the buttons.
     *   Tests if game is won, and then produces dialog box with exit button and counter
     */
    public void checkColors(){
    	if (c1.getColor() == c2.getColor()){ // checks color match
    		c1.setEnabled(false); //disables the button
    		c2.setEnabled(false);
    		c1.setMatched(true); //flags the button as having been matched
    		c2.setMatched(true);
    		if (this.isGameWon()){
    			JOptionPane.showMessageDialog(this, "You win!\n Number of click required: " + counter);
    			System.exit(0);
    		}
    	}
    	else {
    		c1.setBackground(Color.GRAY); // Sets color back to Gray
    		c1.setText("");//reset text to empty string
    		c2.setBackground(Color.GRAY); // When cards do not match.
    		c2.setText("");//reset text to empty string
    	}
    	c1 = null; //reset c1 and c2
    	c2 = null;
    }
    
    /**
     * 	Method checks whether the cards are matches, if they match it turns off the buttons.
     *  Tests if game is won, and then produces dialog box with exit button and counter
     */
    public void checkNames() {
    	if (c2.getName() != null && c1.getName().equals(c2.getName())) { // checks word match
    		c1.setEnabled(false); //disables the button
    		c2.setEnabled(false);
    		c1.setMatched(true); //flags the button as having been matched
    		c2.setMatched(true);
    		if (this.isGameWon()){
    			JOptionPane.showMessageDialog(this, "You win!\n Number of click required: " + counter);
    			System.exit(0);
    		}
    	}
    	else {
    		c1.setBackground(Color.GRAY); // Sets color back to Gray
    		c1.setText("");//reset text to empty string
    		c2.setBackground(Color.GRAY); // When cards do not match.
    		c2.setText("");//reset text to empty string
    	}
    	c1 = null; //reset c1 and c2
    	c2 = null;
    }
    
    /**
     * @return false if cards do not match, true if all cards match.
     */
    public boolean isGameWon(){
        for(Card c: this.cards){
            if (c.getMatched() == false){
                return false;
            }
        }
        return true;
    }
    
}