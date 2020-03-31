package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * Panel used in GUI to draw the rectangles
 */
public class DrawPanel extends JPanel {
    private List<BetterRectangle> rectangles; // Rectangles to be drawn
    double scale = 1.0; // Increase to increase size of the rectangles
    // For some reason the panel height is warped and is about
    // 70 units smaller
    int heightFix = 70;
    BetterRectangle boundingBox = new BetterRectangle(1, 1, 1, 1);
    Color boundingBoxColor = new Color(255,255,0);
    BetterRectangle selected;
    
    public DrawPanel(){
        super();
    }
    
    @Override
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        
        // First draw the boundingBox
        g.setColor(boundingBoxColor);
        g.fillRect(boundingBox.scaledX, this.getHeight() - heightFix - boundingBox.scaledHeight, 
                boundingBox.scaledWidth, 
                boundingBox.scaledHeight);
        
        // Loop to draw each rectangle
        rectangles.forEach((r) -> {
            // Set drawing color to red/green for rectangle
            g.setColor(r.getColor());
            // Then make filled rectangle with that color
            g.fillRect(r.scaledX, this.getHeight() - heightFix - r.scaledY - 
                    r.scaledHeight, r.scaledWidth , r.scaledHeight);
            // Set to color black for edges around rectangles
            g.setColor(Color.BLACK);
            // Draw a rectanles , basicly just 4 black lines
            g.drawRect(r.scaledX, this.getHeight() - heightFix - r.scaledY - 
                    r.scaledHeight, r.scaledWidth, r.scaledHeight);
        });
    }
    
    public void setRectangles(List<BetterRectangle> rectangles, int height){
        this.rectangles = rectangles;
        scale = 1; // also reset scaling
        int width = getMaxWidth();
        if(height <= 0){
            height = getMaxHeight();
        }
        boundingBox = new BetterRectangle(width, height, 1, 1);
        if(!isLegal()){
            System.out.println("Some rectangles overlap!");
        }
    }
    
    
    public List<BetterRectangle> getRectangles(){
        return rectangles;
    }
   
    // Determines wether the rectangle can be painted on the screen
    public boolean canPaint(){
        // Check each rectangle
        for(BetterRectangle r: rectangles){
            // Scale the rectangles
            r.scaledX = (int) (r.x * scale);
            r.scaledY = (int) (r.y * scale);
            r.scaledWidth = (int) (r.width * scale);
            r.scaledHeight = (int) (r.height * scale);
            // Check whether the rectangles fits on the screen
            if(!(r.scaledX + r.scaledWidth < this.getWidth() &&
                    r.scaledY + r.scaledHeight < this.getHeight() - heightFix)){
                return false;
            }
        }
        boundingBox.scaledX = (int) (boundingBox.x * scale);
        boundingBox.scaledY = (int) (boundingBox.y * scale);
        boundingBox.scaledWidth = (int) (boundingBox.width * scale);
        boundingBox.scaledHeight = (int) (boundingBox.height * scale);
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

    public double getScaling() {
        return scale;
    }
    
    public boolean isLegal(){
        for(BetterRectangle r1: rectangles){
            for(BetterRectangle r2: rectangles){
                if(r1 != r2){
                    if(r1.intersects(r2)){
                        System.out.println(r1.index+ " " + r1.toString() + " " + r2.index + " " + r2.toString());
                        r1.setColor(new Color(255, 0, 0));
                        r2.setColor(new Color(255, 0, 0));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    String[] getRectangleAt(int x, int y) {
        y = this.getHeight() - y;
        String[] info = new String[5];
        info[0] = "Rectangle: ";
        info[1] = "x: ";
        info[2] = "y: ";
        info[3] = "width: " ;
        info[4] = "height: ";
        if(canRepaint()){ 
            for(BetterRectangle r1: rectangles){
               if(r1.scaledInside(x, y)){
                    info[0] = "Rectangle: " + r1.index;
                    info[1] = "x: " + r1.x;
                    info[2] = "y: " + r1.y;
                    info[3] = "width: " + r1.width;
                    info[4] = "height: " + r1.height;
               }
            }
        }
        return info;
    }
    
    int getRectangleIndexAt(int x, int y) {
        y = this.getHeight() - y;
        if(canRepaint()){ 
            for(BetterRectangle r1: rectangles){
                if(r1.scaledInside(x, y)){
                    return r1.index;
                }
            }
        }
        return -1;
    }
    
    public void selectRectangleAt(int x, int y){
        y = this.getHeight() - y;
        if(selected != null){
            selected.changeColor();
        }
        selected = null;
        if(canRepaint()){ 
            for(BetterRectangle r1: rectangles){
               if(r1.scaledInside(x, y)){
                   r1.changeColor();
                   selected = r1;
               }
            }
        }
    }
    
    public int getMaxWidth(){
        int maxWidth = 0;
        for(BetterRectangle r1: rectangles){
            if(r1.width + r1.x > maxWidth){
                maxWidth = r1.width + r1.x;
            }
        }
        return maxWidth;
    }
    
    public int getMaxHeight(){
        int maxHeight = 0;
        for(BetterRectangle r1: rectangles){
            if(r1.height + r1.y > maxHeight){
                maxHeight = r1.height + r1.y;
            }
        }
        return maxHeight;
    }
}
