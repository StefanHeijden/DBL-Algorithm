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
    int[][] placementTemp = new int[global.getNumRectangles()][];
    
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    public int computeLowestPoint(int width){
        for(int i = 0; i < placementTemp.length; i++) {
            int y2 = y;
            if(placementTemp[i] != null) {
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
                if((y != 0) &&((width <= placementTemp[i][1]) || (0 == placementTemp[i][1]))) {
                    return y2;
                }
            }
        }
        return y;
    }
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height) {
        int lowestY = computeLowestPoint(width);

        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = lowestY;
        
        
        heights[counter] = height + lowestY;
        
        
        for (int l = 0; l < heights.length; l ++) {
            System.out.println("h " + heights[l]);
            if(heights[l] > maxHeight) {
                maxHeight = heights[l];
                
            }
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
        int[][] placementFinal = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
        //numbered rectanlge array
        int[][] rectNumb = new int[rectangle.length][];
        
        // sorted arrays of rectangles based on height and length
        int[][] sortedHRect = new int[global.getNumRectangles()][];
        int[][] sortedLRect = new int[global.getNumRectangles()][];
        
        int counter = 0;
        
        //rectangle number
        int rectId = 1;
        
        // addsidentifier to each rectangle
        for(int i = 0; i < rectangle.length; i ++) {
            int[] q = new int[] {rectId, rectangle[i][0], rectangle[i][1]};
            rectNumb[i] = q;
            System.out.println("r " + rectId + " " + rectangle[i][0] + " " + rectangle[i][1]);
            rectId ++;
        }
            
        // copies the rectagnle into a new array 
        System.arraycopy(rectNumb, 0, sortedHRect, 0, global.getNumRectangles());
        System.arraycopy(rectNumb, 0, sortedLRect, 0, global.getNumRectangles());
        
        // sorts the array based on x (width)
        java.util.Arrays.sort(sortedHRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[1], a[1]);
            }
        });
        
        // sorts the array based on y (height)
        java.util.Arrays.sort(sortedLRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[2], a[2]);
            }
        });
        
        // comutes the x limit
        for (int i = 0; i < rectNumb.length; i++) { 
            int rectWidth = rectNumb[i][1];
            sumRectWidth += rectWidth;    
        }
        sumRectWidth = sumRectWidth/2;
        
        
        // if there is a rectangle larger than the limit, then set it to be limit
        for (int i = 0; i < rectNumb.length; i++) { 
            if (sortedHRect[i][1] > sumRectWidth) {
                sumRectWidth = sortedHRect[i][1];
            }
        }

        // assign placement of rectangle
        for (int i = 0; i < sortedHRect.length; i++) { 
            int rectWidth = sortedHRect[i][1];
            int rectLength = sortedHRect[i][2];
           
            //adds identifier to placement array
            int[] z = computeBottomleftFree(rectWidth, rectLength);
            int[] p = new int[3];
            p[0] = sortedHRect[i][0];
            p[1] = z[0];
            p[2] = z[1];
            
            placementTemp[i] = p;
        }
        //sorts placement array to have rectangles in input order
        java.util.Arrays.sort(placementTemp, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(a[0], b[0]);
                }
        });
        //places rectangles into placement without identifcation in correct order
        for (int i = 0; i < placementTemp.length; i ++) {
            int[] w = new int[2];
            w[0] = placementTemp[i][1];
            w[1] = placementTemp[i][2];
            placementFinal[i] = w;
        }
        grid.storePlacement(placementFinal);
    }  
}
