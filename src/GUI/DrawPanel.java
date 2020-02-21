package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author stefa
 */
public class DrawPanel extends JPanel{
    private List<BetterRectangle> rectangles;
    double scale = 1.0; // Increase to increase size of the rectangles
    int heightFix = 70;
    
    public DrawPanel(){
        super();
        
    }
    
    @Override
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        rectangles.forEach((r) -> {
            //System.out.println("DRAW RECTANGLE: \nx: " + r.x + "\ny: " + r.y + 
                    //"\nwidth: " +  r.width + "\nheight: " +  r.height);
            //System.out.println("Color(" + r.getColor().getRed() + ", " 
                    //+ r.getColor().getGreen() + ", " 
                    //+ r.getColor().getBlue() + ")");
            int x = (int) (r.x * scale);
            int y = (int) (r.y * scale);
            int width = (int) (r.width * scale);
            int height = (int) (r.height * scale);
            if(canPaint(x, y, width, height)){
                g.setColor(r.getColor());
                System.out.println(this.getHeight());
                g.fillRect(x, this.getHeight() - heightFix - y - height, width , height);
                g.setColor(Color.BLACK);
                g.drawRect(x, this.getHeight() - heightFix - y - height, width, height);
            }else{
                System.out.println("DIDNT DRAW RECTANGLE");
            }
        });
    }
    
    public void setRectangles(List<BetterRectangle> rectangles){
        this.rectangles = rectangles;
    }
    
    
    public List<BetterRectangle> getRectangles(){
        return rectangles;
    }
   
    // Determines wether the rectangle can be painted on the screen
    public boolean canPaint(int x, int y, int width, int height){
        return (x + width < this.getWidth() &&
                    y + height < this.getHeight() - heightFix);
    }
       
    public boolean canRepaint(){
        return (getRectangles() != null && getRectangles().size() > 0);
    }
   
    public void setScale(double scale){
        this.scale = scale;
    }
    
    public void specialRepaint(){
        paintComponents(this.getGraphics());
    }
    
    // Increment the scaling with one step based on what the scaling is
    public void incrementScale() throws ArithmeticException{
        if (scale <= 10){ // Scaling cannot be to big
            if(scale >= 1){
                scale = scale + 1;

            }else{ if(scale >= 0.1){
                scale = scale + 0.1;

            }else{ if(scale >= 0.01){
                scale = scale + 0.01;
            }else{ throw new ArithmeticException();}}} // Possibly unreachable
        }
    }
    
    // Increment the scaling with one step based on what the scaling is
    public void decrementScale() throws ArithmeticException{
        if(scale >= 2){
            scale = scale - 1;
            
        }else{ if(scale >= 0.2){
            scale = scale - 0.1;
            
        }else{ if(scale >= 0.02){
            scale = scale - 0.01;
        }else{ throw new ArithmeticException();}}} // Scaling cannot be to small
    }
 
}
