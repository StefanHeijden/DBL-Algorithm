package GUI;

import java.awt.Color;
import java.awt.Rectangle;

/**
 *
 * Used to draw the rectangles in the GUI
 */
public class BetterRectangle extends Rectangle{
    public int index;
    private Color color;// Color of the rectangle
    private Color selectedColor = new Color(255, 100, 255);
    private boolean selected = false;
    private int weight;// Is larger when rectangle is larger
    private final int MAXWEIGHT;// Is determined by largest rectangle
    public int scaledX;
    public int scaledY;
    public int scaledWidth;
    public int scaledHeight;
    
    public BetterRectangle(int width, int height, int index, int maxWeight){
        super(width, height);
        scaledX = x;
        scaledY = y;
        scaledWidth = width;
        scaledHeight = height;
        this.MAXWEIGHT = maxWeight;
        setWeight(width, height);
        setColorBasedOnWeight();
        this.index = index;
    }
    
    // Color the rectangle
    public void setColor(Color color){
        this.color = color;
    }
    
    public void changeColor(){
        selected = !selected;
    }

    // Give the rectangle a color based on how big it is
    private void setColorBasedOnWeight() {
        // The larger the rectangle, the redder is will become
        int blue = (int) Math.round( 250.0 * ((double) getWeight() 
                / (double) MAXWEIGHT));
        // The smaller the rectangle, the greener is will become
        int green = 250 - (int) Math.round( 250.0 * ((double) getWeight() 
                / (double) MAXWEIGHT));
        // Change the color
        setColor(new Color(0, green, blue));
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
        return weight;
    }

    Color getColor() {
        if(selected){
            return selectedColor;
        }
        return color;
    }

    public boolean scaledInside(int X, int Y) {
        int w = scaledWidth;
        int h = scaledHeight;
        if ((w | h) < 0) {
            // At least one of the dimensions is negative...
            return false;
        }
        // Note: if either dimension is zero, tests below must return false...
        if (X < scaledX || Y < scaledY) {
            return false;
        }
        w += scaledX;
        h += scaledY;
        //    overflow || intersect
        return ((w < scaledX || w > X) &&
                (h < scaledY || h > Y));
    }
}
