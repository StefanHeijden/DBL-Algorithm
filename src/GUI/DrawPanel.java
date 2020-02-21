package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * Panel used in GUI to draw the rectangles
 */
public class DrawPanel extends JPanel{
    private List<BetterRectangle> rectangles; // Rectangles to be drawn
    double scale = 1.0; // Increase to increase size of the rectangles
    // For some reason the panel height is warped and is about
    // 70 units smaller
    int heightFix = 70;
    
    public DrawPanel(){
        super();
        
    }
    
    @Override
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        // Loop to draw each rectangle
        rectangles.forEach((r) -> {
            // Scale the rectangles
            int x = (int) (r.x * scale);
            int y = (int) (r.y * scale);
            int width = (int) (r.width * scale);
            int height = (int) (r.height * scale);
            // Set drawing color to red/green for rectangle
            g.setColor(r.getColor());
            // Then make filled rectangle with that color
            g.fillRect(x, this.getHeight() - heightFix - y - height, 
                    width , height);
            // Set to color black for edges around rectangles
            g.setColor(Color.BLACK);
            // Draw a rectanles , basicly just 4 black lines
            g.drawRect(x, this.getHeight() - heightFix - y - height, 
                    width, height);
        });
    }
    
    public void setRectangles(List<BetterRectangle> rectangles){
        this.rectangles = rectangles;
        scale = 1; // also reset scaling
    }
    
    
    public List<BetterRectangle> getRectangles(){
        return rectangles;
    }
   
    // Determines wether the rectangle can be painted on the screen
    public boolean canPaint(){
        // Check each rectangle
        for(BetterRectangle r: rectangles){
            // Scale the rectangles
            int x = (int) (r.x * scale);
            int y = (int) (r.y * scale);
            int width = (int) (r.width * scale);
            int height = (int) (r.height * scale);
            // Check whether the rectangles fits on the screen
            if(!(x + width < this.getWidth() &&
                    y + height < this.getHeight() - heightFix)){
                return false;
            }
        }
        return true;
    }
       
    // Used in GUI to make sure repaint isn't used when there are no rectangles
    public boolean canRepaint(){
        return (getRectangles() != null && getRectangles().size() > 0);
    }
   
    // Change the scale of the rectangles
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public void specialRepaint(){
        paintComponents(this.getGraphics());
    }
    
    // Increment the scaling with one step based on what the scaling is
    public void incrementScale() throws ArithmeticException{
        if (scale <= 20){ // Scaling cannot be to big
            if(scale >= 0.1){
                scale = scale + 0.1;

            }else{ if(scale >= 0.005){
                scale = scale + 0.005;
            }else{ throw new ArithmeticException();}} // Possibly unreachable
        }else{
             throw new ArithmeticException();
        }
    }
    
    // Increment the scaling with one step based on what the scaling is
    public void decrementScale() throws ArithmeticException{
        if(scale >= 0.2){
            scale = scale - 0.1;
            
        }else{ if(scale >= 0.01){
            scale = scale - 0.005;
        }else{ throw new ArithmeticException();}} // Scaling cannot be to small
    }
    
    // Scale the rectangles such that they fit on screen
    public void scale(){
        // Make scaling larger if the rectangles fit
        if(canPaint()){
            makeLarger();
        }else{ // else make them smaller
            makeSmaller();
        }
    }

    // Keep making the scaling larger until we find a one that is too large
    private void makeLarger() {
        boolean done = false;
        // Try in case of ArithmeticException
        try{
            // Keep incrementing the scale untill one doesn't fit
            while(!done){
                // Incrementing the scale
                incrementScale();
                done = !canPaint();
            }
            // Then make one smaller, this one should just fit
            decrementScale();
            // but make sure it does
            if(!canPaint()){
                System.out.println("CHECK IN SCALING makeLarger failed");
            }
        }catch(ArithmeticException e){
            System.out.println("ERROR IN SCALING makeLarger");
        }
    }

     // Keep making the scaling smaller until we find one that fits
    private void makeSmaller() {
        boolean done = false;
         // Try in case of ArithmeticException
        try{
            // Keep decrementing the scale untill one fits
            while(!done){
                decrementScale();
                done = canPaint();
            }
        }catch(ArithmeticException e){
            System.out.println("ERROR IN SCALING makeSmaller");
        }
        // Make sure it fits
        if(!canPaint()){
            System.out.println("CHECK IN SCALING makeSmaller failed");
        }
    }
    
    
 
}
