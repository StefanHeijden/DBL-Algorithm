package GUI;

import java.awt.Graphics;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author stefa
 */
public class DrawPanel extends JPanel{
    List<BetterRectangle> rectangles;
    private boolean specialUpdate = false;
    
    public DrawPanel(){
        super();
        //Graphics gb = new;
        
    }
    
    @Override
    public void paintComponents(Graphics g){
        super.paintComponent(g);
        System.out.println("DRAW");
        rectangles.forEach((r) -> {
            System.out.println("DRAW RECTANGLE: \nx:" + r.x + "\ny" + r.y + 
                    "\nwidth" +  r.width + "\nheight" +  r.height);
            if(r.x + r.width < this.getWidth() &&
                    r.y + r.height < this.getHeight()){
                g.drawRect(r.x, r.y, r.width, r.height);
            }else{
                System.out.println("DIDNT DRAW RECTANGLE");
            }
        });
    }
    
    public void setRectangles(List<BetterRectangle> rectangles){
        this.rectangles = rectangles;
    }
    
    public void specialRepaint(){
        paintComponents(this.getGraphics());
    }
}
