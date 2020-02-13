/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author Ezra
 */
public class TestingAlgorithm extends AbstractAlgorithm{
    
    public TestingAlgorithm(Grid grid) {
        super(grid); 
    }
    
    GlobalData global;
    
    private int x = 0; 
    private int y = 0; 
    public int[] bottomleft = {x, y};
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height) {
        x += width; 
        y += height; 
        return bottomleft;
    }
    
    // computation of bottomleft corner in case (containerType == "fixed")
    public int[] computeBottomleftFixed(int width) {
        x += width; 
        return bottomleft;
    }
    
    @Override
    public void run() {
        
        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        int[][] placement = grid.getPlacement();
        
        for (int i= 0; i < rectangle.length; i++) {
            placement[i] = bottomleft;
            grid.storePlacement(placement);
            int rectWidth = rectangle[i][1]; 
            int rectHeight = rectangle[i][2];
            
            // Series of if-statements that compute bottomleft differently
            // Computation based on vars containerType and rotationsAllowed
            // Very ugly implementation, purely meant for simple testing
            if (global.getType().equals("free") && 
                    !global.getRA() ) {
               computeBottomleftFree(rectWidth, rectHeight); 
            } else if (global.getType().equals("free") && 
                    global.getRA() ) {
                computeBottomleftFree(rectHeight, rectWidth);
            } else if (global.getType().equals("fixed") && 
                    !global.getRA() ) {
                computeBottomleftFixed(rectWidth);
            } else if (global.getType().equals("fixed") && 
                    global.getRA() ) {
                computeBottomleftFixed(rectHeight);
            }
        }
    }
    
}