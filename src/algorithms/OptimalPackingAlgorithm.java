
package algorithms;

import java.util.Arrays;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author Ezra
 * 
 * 
 */
public class OptimalPackingAlgorithm extends AbstractAlgorithm {
    
    // get the set of rectangles from global data
    int[][] rectangles = global.getRectangles();
    
    public OptimalPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    // generates set of bounding boxes 
    public int[][] generateBoundingBoxes(int[] data, int[] bounds) {
        // data to set width of bounding boxes
        // branching factor determines how many boxes there will in the set
        int branchFactor = 4;
        int range = data[1] - data[0];
        int increase = range / branchFactor;
        
        // array to store packings in
        int[][] boxes = new int[branchFactor][]; 
        
        //set widths and heights of bounding boxes
        int i = 0; 
        for (int[] b: boxes) {
            b[0] = data[0] + i*increase;
            
            // compute third integer for determining box height
            int stackedHeight = computeStackedHeight(b[0]);
            
            // compute fourth integer for determining box height
            int halfStackedHeight = computeHalfStackedHeight((b[0] / 2));
            
            // compute the height of boxes by taking maximum...
            // of 4 computed integers
            int boxHeight = Math.max(data[2], bounds[1]); 
            boxHeight = Math.max(boxHeight, stackedHeight);
            boxHeight = Math.max(boxHeight, halfStackedHeight);
            
            // set the current box' height
            b[1] = boxHeight;
            
            // In case rotations are allowed, make height at least 1 greater...
            // than width
            if (global.getRA()) {
                if (b[1] < b[0]) {
                    b[1] = b[0]++;
                }
            }
            
            i++;
        }
        
        return boxes;
    }
    
    // compute useful data about set of rectangles
    public int[] computeDataRectangles() {
        //create array to store data in
        int[] rectData = new int[5];
        
        // compute the total width/ height of all rectangles
        // compute the largest width/ height of all rectangles
        // compute the total area of all rectangles
        // compute second largest width, used for 3rd box height integer
        int largestWidth = 0;
        int totalWidth = 0;
        int largestHeight = 0;
        int totalHeight = 0;
        int totalRectArea = 0;
        for (int[] r: rectangles){
            if (r[0] > largestWidth){
                largestWidth = r[0]; 
            }
            if (r[1] > largestHeight){
                largestHeight = r[1]; 
            }
            totalWidth += r[0];
            totalHeight += r[1]; 
            totalRectArea += r[0] * r[1];
        }
        
        // store data in output array
        rectData[0] = largestWidth;
        rectData[1] = totalWidth;
        rectData[2] = largestHeight;
        rectData[3] = totalHeight;
        rectData[4] = totalRectArea;
        
        return rectData;
    }
    
    // computing the area related bounds to the packing boxes
    public int[] computeAreaBounds(int[] data) {
        //create array to store bounds in
        int[] areaBounds = new int[2];
        
        // getting useful data from array for rectangle data
        int totalWidth = data[1];
        int largestHeight = data[2];
        int totalRectArea = data[4];
                
        // store bounds in array
        areaBounds[0] = largestHeight * totalWidth;
        areaBounds[1] = totalRectArea;

        return areaBounds;
    }
    
    // sum of two largest rectangle widths compared to box width
    // if sum is larger the sum of respective rectangle heights returned
    public int computeStackedHeight(int boxWidth) {
        // remains zero unless said sum is bigger than box width
        int stackedHeight = 0;
        
        // compute the largest/ second largest widths with corresponding heights
        int largestWidth = 0;
        int secondLargestWidth = 0;
        int firstHeight = 0;
        int secondHeight = 0; 
        for (int i = 0; i < rectangles.length; i++) {
            if (rectangles[i][0] > largestWidth) {
                largestWidth = rectangles[i][0];
                firstHeight = rectangles[i][1];
            }
            if (rectangles[i][0] > secondLargestWidth && 
                    rectangles[i][0] <= largestWidth) {
                secondLargestWidth = rectangles[i][0];
                secondHeight = rectangles[i][1];
            }
        }
        if (largestWidth + secondLargestWidth > boxWidth) {
            stackedHeight = firstHeight + secondHeight; 
        }
            
        return stackedHeight;
    }
    
    // computes combined height of set of rectangles that are in width...
    // greater or equal to half of box width 
    public int computeHalfStackedHeight(int halfBoxWidth) {
        int halfStackedHeight = 0;
            
        return halfStackedHeight;
    }
    
    @Override
    public void run() {
        // compute area bounds
        int[] data = computeDataRectangles();
        int[] bounds = computeAreaBounds(data);
        
        // generate set of packing boxes
        int[][] packings = generateBoundingBoxes(data, bounds);
        
        // preliminary test to see if algorithm works correcly
        System.out.println(Arrays.toString(packings));
    }
}


