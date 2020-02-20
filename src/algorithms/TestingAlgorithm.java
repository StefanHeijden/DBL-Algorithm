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
    
    // computation of bottomleft corner in all cases except if rotations allowed
    // and height is fixed
    public int[] computeBottomleftStandard(int width) {
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = 0;
        x += width; //update coordinates for next rectangle
        return returnCoordinates;
    }
    
    public int[] computeBottomLeftSpecial(int width, int height, int index) {
        int[] returnCoordinates = new int[2];
        if (height > global.getHeight()) { // if height exceeds the limit rotate rectangle
            grid.setRotationsIndexI(true, index); //rotate placed rectangle
            returnCoordinates[0] = x;
            returnCoordinates[1] = 0;
            x += height; //update coordinates of next rectangle with rotated rect.
        } else { //in case it does not have to be rotated
            returnCoordinates[0] = x;
            returnCoordinates[1] = 0;
            grid.setRotationsIndexI(false, index); //set rotation to false
            x += width;
        }
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
            int rectHeight = rectangle[i][1]; //not used anymore
            
            // Series of if-statements that compute bottomleft differently
            // Computation based on vars containerType and rotationsAllowed
            // Very ugly implementation, purely meant for simple testing
            if (global.getType().equals("free") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftStandard(rectWidth); 
            } else if (global.getType().equals("free") && 
                    global.getRA() ) {
                placement[i] = computeBottomleftStandard(rectWidth);
                grid.setRotationsIndexI(false, i);
            } else if (global.getType().equals("fixed") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftStandard(rectWidth);
            } else if (global.getType().equals("fixed") && 
                    global.getRA() ) {
                placement[i] = computeBottomLeftSpecial(rectWidth, rectHeight, i);
            }
        }
        
        grid.storePlacement(placement);
    }
    
}