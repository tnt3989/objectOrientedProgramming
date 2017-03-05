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
import java.util.Collections;

/**
 * @author Travis Tibbetts
 * @version 1.0
 */
public class GameBoard extends JFrame{
    private List<Card> cards;
    private Card selectedCard;
    private Card c1;
    private Card c2;
    private Timer timer;
    private static int counter;
    

    /**
     * Constructor to build ArrayLists of cards and colors, add colors to ArrayList of colors,
     * 
     */
    public GameBoard(){
    	// create arrayLists of cards and colors
        ArrayList<Card> cardsList = new ArrayList<Card>();
        ArrayList<Color> cardColors = new ArrayList<Color>();
        
        // add all colors to ArrayList of colors
        cardColors.add(Color.BLUE);
        cardColors.add(Color.GREEN);
        cardColors.add(Color.MAGENTA);
        cardColors.add(Color.ORANGE);
        cardColors.add(Color.RED);
        cardColors.add(Color.YELLOW);
        
        for (Color colors : cardColors){ //for each color in cardColors
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
            cardsList.add(c1);
            cardsList.add(c2);
        }
        Collections.shuffle(cardsList); // shuffles the cards
        this.cards = cardsList;
        timer = new Timer(800, new ActionListener(){ // initiate timer and new actionListener
            public void actionPerformed(ActionEvent ae){
                checkCards();
            }
        });

        timer.setRepeats(false);

        Container pane = getContentPane(); // create the GameBoard window pane
        pane.setLayout(new GridLayout(3, 4, 10, 10)); // set layout as grid with 3 rows 4 columns, 10 height difference, 10 vertical difference
        
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
            counter++; // +1 to click counter
        }

        if (c1 != null && c1 != selectedCard && c2 == null) {
            c2 = selectedCard;
            c2.setBackground(c2.getColor());
            timer.start(); // creates the pause to memorize colors shown
            counter++; // +1 to click counter

        }
    }

    /**
     *   Method checks whether the cards are matches, if they match turns off the buttons.
     *   Tests if game is won, and then produces dialog box with exit button and counter
     */
    public void checkCards(){
        if (c1.getColor() == c2.getColor()){ // checks match
            c1.setEnabled(false); //disables the button
            c2.setEnabled(false);
            c1.setMatched(true); //flags the button as having been matched
            c2.setMatched(true);
            if (this.isGameWon()){
                JOptionPane.showMessageDialog(this, "You win!\n Number of click required: " + counter);
                System.exit(0);
            }
        }

        else{
            c1.setBackground(Color.GRAY); // Sets color back to Gray
            c2.setBackground(Color.GRAY); // When cards do not match.
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