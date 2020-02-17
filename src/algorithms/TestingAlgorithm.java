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
 * @author Ezra, and fixes leighton
 */
public class TestingAlgorithm extends AbstractAlgorithm{
    
    public TestingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data); 
    }
    
    
    private int x = 0; 
    private int y = 0; 
    public int[] bottomleft = new int[2];
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height) {
        x += width; 
        y += height;
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = y;
        return returnCoordinates;
    }
    
    // computation of bottomleft corner in case (containerType == "fixed")
    public int[] computeBottomleftFixed(int width) {
        x += width; 
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = y;
        return returnCoordinates;
    }
    
    @Override
    public void run() {
        
        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        int[][] placement = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
        for (int i = 0; i < rectangle.length; i++) { 
            int rectWidth = rectangle[i][0]; 
            int rectHeight = rectangle[i][1];
            
            // Series of if-statements that compute bottomleft differently
            // Computation based on vars containerType and rotationsAllowed
            // Very ugly implementation, purely meant for simple testing
            if (global.getType().equals("free") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftFree(rectWidth, rectHeight); 
            } else if (global.getType().equals("free") && 
                    global.getRA() ) {
                placement[i] = computeBottomleftFree(rectHeight, rectWidth);
                grid.setRotationsIndexI(true, i);
            } else if (global.getType().equals("fixed") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectWidth);
            } else if (global.getType().equals("fixed") && 
                    global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectHeight);
                grid.setRotationsIndexI(true, i);
            }
        }
        grid.storePlacement(placement);
    }
    
}