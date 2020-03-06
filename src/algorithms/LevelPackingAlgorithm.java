package algorithms;

import logic.GlobalData;
import logic.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    
    int[] heights = new int[global.getRectangles().length];
    
    //temporary placement
    int[][] placementFinal = new int[global.getNumRectangles()][];
    
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    public Grid bottomLeft(int[][] passedRectangle, boolean rotationAllowed) {
        int[][] placementTemp = new int[global.getNumRectangles()][];
        
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        //if (global.getType().equals("free") && 
        //    !global.getRA() ) {
        //        placement[i] = computeBottomleftStandard(rectWidth); 
        //} else if (global.getType().equals("free") && 
        //        global.getRA() ) {
        //    placement[i] = computeBottomleftStandard(rectWidth);
        //    grid.setRotationsIndexI(false, i);
        //} else if (global.getType().equals("fixed") && 
        //        !global.getRA() ) {
        //    placement[i] = computeBottomleftStandard(rectWidth);
        //} else if (global.getType().equals("fixed") && 
        //        global.getRA() ) {
        //    placement[i] = computeBottomLeftSpecial(rectWidth, rectHeight, i);
        //}

        // assign placement of rectangle
        for (int i = 0; i < passedRectangle.length; i++) { 
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
           
            placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
        }
        grid.storePlacement(placementFinal);
        return grid;
    }
    
    public int computeLowestPoint(int width, int height, int[][] passedRectangle){
        for(int i = 0; i < placementFinal.length; i++) {
            int y2 = y;
            if(placementFinal[i] != null) {
                //if the y is not yet the lowst possible height then decrease y
                while((heights[i] != y2)) {
                    if(y2 ==  0){
                        break;
                    } 
                    y2 --;
                    //System.out.println("y " + heights[i]);
                    //System.out.println("y2 " + y2);
                }
                //if find such a y is found, check if width fits
                if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                    //System.out.println("width and height " + width + " " + height + ' ' + y2);
                    return y2;
                }
                //if((y != 0) &&((width <= placementFinal[i][0])  || (0 == placementFinal[i][0]))) {
                //}
            }
        }
        return y;
    }
    
    public int computeClosestXPoint(int width, int height, int[][] passedRectangle){
        System.out.println();
    }
    
    public boolean overlapsRectangle(int[][] passedRectangle, int width, int height, int yInitial, int xInitial) {
        for(int i = 0; i < passedRectangle.length; i ++) {
            if(passedRectangle[i] == null) { 
                if((yInitial <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height)) || ((yInitial >= passedRectangle[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height)))){
                    if((xInitial <= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) <= (xInitial + width)) || ((xInitial >= passedRectangle[i][0])&& ((passedRectangle[i][0] + placementFinal[i][0]) <= (xInitial + width)))) {
                        return false;
                    }
                }
            }
        }
        return false;
    }
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height, int[][] passedRectangle) {
        int lowestY = computeLowestPoint(width, height, passedRectangle);
        //System.out.println("lowestY " + lowestY);
        
        //creates return coordinates
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = lowestY;
        
        //adds current object
        heights[counter] = height + lowestY;
        
        //System.out.println("height of rectangle " + counter + " " + heights[counter]);
        
        //if current height is larger than existing, replace it
        if(heights[counter] > maxHeight) {
            maxHeight = heights[counter];
            System.out.println("max height of rectangles " + maxHeight);
        }
        
        
        if (x >= sumRectWidth)      { // x coordinate replaced if greater than limit
            x = 0;
            y = maxHeight;
        }
        else {
           x += width; 
        }
        counter ++;
        return returnCoordinates;
    }
    
    @Override
    public void run() {
        
        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        //int[][] placementFinal = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
        bottomLeft(rectangle, global.getRA());
        
        //numbered rectanlge array
        //int[][] rectNumb = new int[rectangle.length][];
        
        // sorted arrays of rectangles based on height and length
        //int[][] sortedHRect = new int[global.getNumRectangles()][];
        //int[][] sortedLRect = new int[global.getNumRectangles()][];
        
        //int counter = 0;
        
        //rectangle number
        //int rectId = 1;
        
        // addsidentifier to each rectangle
        //for(int i = 0; i < rectangle.length; i ++) {
        //    int[] q = new int[] {rectId, rectangle[i][0], rectangle[i][1]};
        //    rectNumb[i] = q;
        //    System.err.println("r " + rectId + " " + rectangle[i][0] + " " + rectangle[i][1]);
        //    rectId ++;
        //}
            
        // copies the rectagnle into a new array 
        //System.arraycopy(rectNumb, 0, sortedHRect, 0, global.getNumRectangles());
        //System.arraycopy(rectNumb, 0, sortedLRect, 0, global.getNumRectangles());
        
        // sorts the array based on x (width)
        //java.util.Arrays.sort(sortedHRect, new java.util.Comparator<int[]>() {
        //    public int compare(int[] a, int[] b) {
        //        return Integer.compare(b[1], a[1]);
        //    }
        //});
        
        // sorts the array based on y (height)
        //java.util.Arrays.sort(sortedLRect, new java.util.Comparator<int[]>() {
        //    public int compare(int[] a, int[] b) {
        //        return Integer.compare(b[2], a[2]);
        //    }
        //});
        
        // comutes the x limit
        //for (int i = 0; i < rectangle.length; i++) { 
        //    int rectWidth = rectangle[i][0];
        //    sumRectWidth += rectWidth;    
        //}
        //sumRectWidth = sumRectWidth/2;
        
        
        // if there is a rectangle larger than the limit, then set it to be limit
        //for (int i = 0; i < rectangle.length; i++) { 
        //    if (rectangle[i][0] > sumRectWidth) {
        //        sumRectWidth = rectangle[i][0];
        //    }
        //}

        // assign placement of rectangle
        //for (int i = 0; i < rectangle.length; i++) { 
        //    int rectWidth = rectangle[i][0];
        //    int rectLength = rectangle[i][1];
           
            //adds identifier to placement array
        //    int[] z = computeBottomleftFree(rectWidth, rectLength);
            //int[] p = new int[3];
            //p[0] = sortedHRect[i][0];
            //p[1] = z[0];
            //p[2] = z[1];
            
            //placementTemp[i] = p;
        //    placementFinal[i] = z;
        //}
        //sorts placement array to have rectangles in input order
        //java.util.Arrays.sort(placementTemp, new java.util.Comparator<int[]>() {
        //        public int compare(int[] a, int[] b) {
        //            return Integer.compare(a[0], b[0]);
        //        }
        //});
        //places rectangles into placement without identifcation in correct order
        //for (int i = 0; i < placementTemp.length; i ++) {
        //    int[] w = new int[2];
        //    w[0] = placementTemp[i][1];
        //    w[1] = placementTemp[i][2];
        //    placementFinal[i] = w;
        //}
        //grid.storePlacement(placementFinal);
    }  
}
