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
    
    // counts the number of rectangles placed
    int counter = 0;
    
    // maximum height of already placed rectagnles
    int maxHeight = 0;
    
    //maximum widht of already placed rectangles
    int maxWidth = 0;
    
    //total area of the rectangles
    int rectArea = 0;
    
    //array of heights of all the placed rectangles
    int[] heights = new int[global.getRectangles().length];
    
    //array of widths of all the placed rectangles
    int[] widths = new int[global.getRectangles().length];
    
    //temporary placement
    int[][] placementFinal = new int[global.getNumRectangles()][];
    
    //rectagnle number
    int rectangle = 0;
    
    //upper bound
    int fixedBound = 0;
    
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    //method to call, given an array of rectagnles 
    //will sort them using the bottom left heurstic
    //returns a grid
    public Grid bottomLeft(int[][] passedRectangle, boolean rotationAllowed) {
        
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        

        // assign placement of rectangle
        
        for (int i = 0; i < passedRectangle.length; i++) { 
            //System.out.println("rectagnle number: " + rectangle);
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
               if (global.getType().equals("free") && !global.getRA() ) {
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            } 
            else if (global.getType().equals("free") && global.getRA() ) {
                    placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
                    //grid.setRotationsIndexI(false, i);
            } 
            else if (global.getType().equals("fixed") && !global.getRA() ) {
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            }
            else if (global.getType().equals("fixed") && global.getRA() ) {
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            }
            rectangle ++;
        }
        grid.storePlacement(placementFinal);
        return grid;
    }

    //computes the lowest y based on the given x coordinate
    //looks through all rectagnles and depending on if the rectagnle is 
    //smaller/bigger or between the current one, based on the x axis
    //the y value becomes the end point of the checked rectangle.
    //If the rectangle fits into the spot, y value is returned
    public int computeLowestPoint(int width, int height, int[][] passedRectangle, int x){
        for(int i = 0; i < placementFinal.length; i++) {
            int y2 = y;
            if(placementFinal[i] != null) {
                //System.out.println("MAYBE FITS INTO 0 " + overlapsRectangle(passedRectangle, width, height, 0, x));
                if(overlapsRectangle(passedRectangle, width, height, 0, x)) {
                    //System.out.println("FITS INTO 0");
                    return 0;
                }
                if((x >= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) >= (x + width))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    //System.out.println("y Rectangle larger or same size as current, with y2 " + y2 + " x " + x);
                    return y2;
                }
                if((x <= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) >= (x + width)) && ((x + width) > (placementFinal[i][0]))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    //System.out.println("y Rectangle starts in middle and end after current, with y2 " + y2 + " x " + x);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                        return y2;
                    }
                }
                if((x <= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) <= (x + width))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);  
                    //System.out.println("y Rectangle in middle of current, with y2 " + y2 + " x " + x);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                        return y2;
                    }
                }
                if((x >= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) <= (x + width)) && (x < passedRectangle[i][0] + placementFinal[i][0])) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    //System.out.println("y Rectangle starts before and end in middle of current, with y2 " + y2 + " x " + x);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                        return y2;
                    }
                }
            }
        }
        if(overlapsRectangle(passedRectangle, width, height, 0, x)){
            return 0;
        }
        return y;
    }
    
    //for every x in the width of the placed rectangles, the algorithm finds the lowest y
    //where the rectagnle can be placed.
    //if it can be placed at coordinate 0, then that is returned.
    //else all possible coordinates are stored
    //the best coordinate is chosen based that minimizes the wasted space.
    //this coordinate is returned
    public int[] computeBottomLeftCoordinate(int[][] passedRectangle, int width, int height) {
        int y2 = 0;
        int x2 = 0;
        int count = 0;
        int[][] lowestPoints = new int[maxWidth + 1][];

        for(int i = 0; i < maxWidth + 1; i++) {
            if(overlapsRectangle(passedRectangle, width, height, 0, x)) {
                return new int[]{x2,y2};
            }
            else {
                if(fixedBound != 0) {
                    if((computeLowestPoint(width, height, passedRectangle, x2) + height) <= fixedBound) {
                         y2 = computeLowestPoint(width, height, passedRectangle, x2);
                    }
                }
                else {
                    y2 = computeLowestPoint(width, height, passedRectangle, x2);
                }
                if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                    //System.out.println("YYYYYYYYYYYYY " + y2 + " XXXXXXXXXXX " + x2);
                    int[] coord = new int[]{x2, y2};
                    lowestPoints[count] = coord;
                    count ++;
                }
                x2 ++;
            }
        }
        float ration = 200000.0f;
        float rationTemp = 0;
        float rectAreaTemp = rectArea;
        rectAreaTemp += (width * height);
        for(int[] coordinate: lowestPoints) {
            if (coordinate != null) {
                float maxWidth_1 = maxWidth;
                float maxHeight_1 = maxHeight;
                
                if((coordinate[0] + width) > maxWidth_1) {
                    maxWidth_1  = coordinate[0] + width;
                }
                if((coordinate[1] + height) > maxHeight_1) {
                    maxHeight_1 = coordinate[1] + height;
                } 
                rationTemp = ((maxHeight_1 * maxWidth_1)/rectAreaTemp);
                if(rationTemp < ration) {
                    ration = rationTemp;
                    y2 = coordinate[1];
                    x2 = coordinate[0];
                }
            }
        }
        return new int[]{x2,y2};
    }
    
    //this method looks at the given y coordinate and find if there is any rectagnle 
    //that is overlapping with it on the y axis.
    //if yes, then the x coordinate is checked. 
    //the overlapping for a given rectangle to each other rectangle is stored in 
    //a boolean array.
    //if all values are false, so no overlapping, then true is returned (no overlaps).
    public boolean overlapsRectangle(int[][] passedRectangle, int width, int height, int yInitial, int xInitial) {
        boolean[] fits = new boolean[passedRectangle.length];
        int count = 0;
        for(int i = 0; i < passedRectangle.length; i ++) {
            if(placementFinal[i] != null) { 
                if((yInitial >= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) >= (yInitial + height))) {
                    //System.out.println("There is a rectangle larger than current. height " + passedRectangle[i][1]);
                    if (overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i)) {
                        fits[count] = false;
                    }
                    else{
                        fits[count] = true;
                    }
                }
                else {
                    if((yInitial <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height))) {
                        //System.out.println("There is a rectangle in middle of current. height " + passedRectangle[i][1]);
                        if (overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i)) {
                            fits[count] = false;
                        }
                        else{
                            fits[count] = true;
                        }
                    }
                    else { 
                        if(((yInitial >= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height)))  && (yInitial < (passedRectangle[i][1] + placementFinal[i][1]))) {
                            //System.out.println("There is a rectangle starts before and ends in middle of current. height " + passedRectangle[i][1]);
                            if (overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i)) {
                                fits[count] = false;
                            }
                            else{
                                fits[count] = true;
                            }
                        }
                        else {
                            if(((yInitial <= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) >= (yInitial + height))) && (placementFinal[i][1] < (yInitial + height))) {
                                //System.out.println("There is a rectangle starts in middle and ends after current. height " + passedRectangle[i][1]);
                                if (overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i)) {
                                    fits[count] = false;
                                }
                                else{
                                    fits[count] = true;
                                }
                            }
                        }
                    }
                }
                count ++;
            }
        }
        for(boolean i: fits) {
            //System.out.println(i);
            if(i == true) {
                return false;
            }
        }
        return true;
    }
    
    //this method checks if the given rectangle overlaps with an already placed rectangle
    //on the a axis.
    //if yes, false is returned.
    //otherwise true is returned.
    public boolean overlapsXRectanlge(int passedRectangle, int width, int xInitial, int i) {
        if((xInitial >= placementFinal[i][0]) && ((passedRectangle + placementFinal[i][0]) >= (xInitial + width))) {
            //System.out.println("There is a rectangle larger than current. width " + passedRectangle);
            return false;
        }
        if((xInitial <= placementFinal[i][0]) && ((passedRectangle + placementFinal[i][0]) <= (xInitial + width))) {
            //System.out.println("There is a rectangle in middle of current. width " + passedRectangle);
            return false;
        }
        if(((xInitial >= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) <= (xInitial + width)) && (xInitial < (passedRectangle + placementFinal[i][0])))) {
            //System.out.println("There is a rectangle starts before and ends in middle of current. width " + passedRectangle);
            return false;
        }
        if(((xInitial <= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) >= (xInitial + width))) && ((xInitial + width) > (placementFinal[i][0]))) {
            //System.out.println("There is a rectangle starts in middle and ends after current. width " + passedRectangle);
            return false;
        }
        //System.out.println("Rectangle safe");
        return true;
    }
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height, int[][] passedRectangle) {

        int[] z = computeBottomLeftCoordinate(passedRectangle, width, height);
        int lowestY = z[1];
        int closestX = z[0];
        
        //creates return coordinates
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = closestX;
        returnCoordinates[1] = lowestY;
        
        //adds current object
        heights[counter] = height + lowestY;
        widths[counter] = width + closestX;
        
        //if current height is larger than existing, replace it
        if(heights[counter] > maxHeight) {
            maxHeight = heights[counter];
        }
        
        if (widths[counter] > maxWidth) {
            maxWidth = widths[counter];
        }
        rectArea = rectArea + width*height;
        counter ++;
        return returnCoordinates;
    }
    
    // computation of bottomleft corner in case (containerType == "fixed")
    public int[] computeBottomleftFixed(int width, int height, int[][] passedRectangle, int maxHeight) {
        fixedBound = maxHeight;
        int[] z = computeBottomLeftCoordinate(passedRectangle, width, height);
        int lowestY = z[1];
        int closestX = z[0];
        
        //creates return coordinates
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = closestX;
        returnCoordinates[1] = lowestY;
        
        //adds current object
        heights[counter] = height + lowestY;
        widths[counter] = width + closestX;
        
        //if current height is larger than existing, replace it
        if(heights[counter] > maxHeight) {
            maxHeight = heights[counter];
        }
        
        if (widths[counter] > maxWidth) {
            maxWidth = widths[counter];
        }
        rectArea = rectArea + width*height;
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
    }  
}
