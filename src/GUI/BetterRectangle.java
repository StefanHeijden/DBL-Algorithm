package GUI;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * Used to draw the rectangles in the GUI
 */
public class BetterRectangle extends Rectangle{
    Color color;// Color of the rectangle
    private int weight;// Is larger when rectangle is larger
    private final int MAXWEIGHT;// Is determined by largest rectangle
    
    public BetterRectangle(int width, int height, int index, int maxWeight){
        super(width, height);
        this.MAXWEIGHT = maxWeight;
        setWeight(width, height);
        setColorBasedOnWeight();
    }
    
    // Color the rectangle
    public void setColor(Color color){
        this.color = color;
    }

    // Give the rectangle a color based on how big it is
    private void setColorBasedOnWeight() {
        // The larger the rectangle, the redder is will become
        int red = (int) Math.round(getWeight() / MAXWEIGHT);
        // The smaller the rectangle, the greener is will become
        int green = 255 - (int) Math.round(getWeight() / MAXWEIGHT);
        // Change the color
        setColor(new Color(red, green, 0));
    }
    
    // Change the weight which can be used for coloring and such
    private void setWeight(int width, int height){
        if(width * height > MAXWEIGHT){
            weight = MAXWEIGHT;
        }else{
            weight = width * height;
        }
    }
    
    // Returns weight which can be used for coloring and such
    public int getWeight(){
        // Make sure the weight is not too large
        if(weight > MAXWEIGHT){
            return MAXWEIGHT;
        }
        return width * height;
    }
    
}
