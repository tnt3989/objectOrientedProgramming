import java.awt.Color;
import javax.swing.JButton;


/**
 * @author Travis Tibbetts
 * @version 1.1
 */
public class Card extends JButton{
    private Color c;
    private String cName;
    private boolean matched = false;

    /**
     * @param c color object to set the color of a card
     */
    public void setColor(Color c){
        this.c = c;
    }

    /**
     * @return return the color of a card
     */
    public Color getColor(){
        return this.c;
    }
    
    /**
     * 
     */
    public void setName(String cName){
    	this.cName = cName;
    }
    
    /**
     * @return return the string within the card
     */
    public String getName(){
    	return this.cName;
    }
  
    /**
     * @param matched returns true or false 
     */
    public void setMatched(boolean matched){
        this.matched = matched;
    }

    /**
     * @return true if cards are matched, false otherwise
     */
    public boolean getMatched(){
        return this.matched;
    }

}