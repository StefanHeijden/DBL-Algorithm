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
    int scale = 1; // Increase to increase size of the rectangles
              
    public DrawPanel(){
        super();
        //Graphics gb = new;
        
    }
    
    @Override
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        System.out.println("DRAW");
        rectangles.forEach((r) -> {
            System.out.println("DRAW RECTANGLE: \nx: " + r.x + "\ny: " + r.y + 
                    "\nwidth: " +  r.width + "\nheight: " +  r.height);
            System.out.println("Color(" + r.getColor().getRed() + ", " 
                    + r.getColor().getGreen() + ", " 
                    + r.getColor().getBlue() + ")");
            if(r.x * scale + r.width * scale < this.getWidth() &&
                    r.y * scale + r.height * scale < this.getHeight()){
                g.setColor(r.getColor());
                g.fillRect(r.x * scale, r.y * scale, r.width * scale, r.height * scale);
                g.setColor(Color.BLACK);
                g.drawRect(r.x * scale, r.y * scale, r.width * scale, r.height * scale);
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
   
    public void setScale(int scale){
        this.scale = scale;
    }
    
    public void specialRepaint(){
        paintComponents(this.getGraphics());
    }
}
