/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import java.util.Arrays;
import logic.GlobalData;
import logic.Grid;
/**
 *
 * @author yana
 * This class places rectangles in a given layout
 */
public class LevelPackingAlgorithm extends AbstractAlgorithm {

        
    private int x = 0; 
    private int y = 0; 
    
    // the total width of all rectangles divided by 2 or the Width of the 
    // longest rectangle.
    int sumRectWidth = 0;
    
    // counts the number of rectangles placed
    int counter = 0;
    
    // maximum height of already placed rectagnles
    int maxHeight = 0;
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height) {
        if (height > maxHeight) { // computes maximum height
            maxHeight = height;
        }
        System.out.println("height " + width); 
        System.out.println("t " + sumRectWidth); 
        if (x + width >= sumRectWidth) { // if the width + free x is larger than
            x = 0;                       // limit, resets x and sets y
            y = maxHeight;
        }
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = y;
        if (x >= sumRectWidth) { // x coordinate replaced if greater than limit
            x = 0;
            y = maxHeight;
        }
        else {
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
        
        // sorted arrays of rectangles based on height and length
        int[][] sortedHRect = new int[global.getNumRectangles()][];
        int[][] sortedLRect = new int[global.getNumRectangles()][];
        
        int counter = 0;
        
        // copies the rectagnle into a new array 
        System.arraycopy(rectangle, 0, sortedHRect, 0, global.getNumRectangles());
        System.arraycopy(rectangle, 0, sortedLRect, 0, global.getNumRectangles());
        
        // sorts the array based on x (width)
        java.util.Arrays.sort(sortedHRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[0], a[0]);
            }
        });
        
        java.util.Arrays.sort(sortedLRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[1], a[1]);
            }
        });
        
        // print statements to ensure correctly sorts
        for (int i = 0; i < rectangle.length; i++){
            System.out.println("width H x " + sortedHRect[i][0]);
            System.out.println("width L x " + sortedLRect[i][0]);
            System.out.println("width   x " + rectangle[i][0]);
            System.out.println("width H y " + sortedHRect[i][1]);
            System.out.println("width L y " + sortedLRect[i][1]);
            System.out.println("width   y " + rectangle[i][1]);
        }
        
        // comutes the x limit
        for (int i = 0; i < rectangle.length; i++) { 
            int rectWidth = rectangle[i][0];
            sumRectWidth += rectWidth;    
        }
        sumRectWidth = sumRectWidth/2;
        
        // if there is a rectangle larger than the limit, then set it to be limit
        if (sortedHRect[0][0] > sumRectWidth) {
            sumRectWidth = sortedHRect[0][0];
            System.out.println(true);
        }

        // assign placement of rectangle
        for (int i = 0; i < sortedHRect.length; i++) { 
            int rectWidth = sortedHRect[i][0];
            int rectLength = sortedHRect[i][1];
            placement[i] = computeBottomleftFree(rectWidth, rectLength);
         }
        grid.storePlacement(placement);
    }
    
    
    
}
