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
    int[] heights  = new int[global.getRectangles().length];
    
    //array of widths of all the placed rectangles
    int[] widths = new int[global.getRectangles().length];
    
    //temporary placement
    int[][] placementFinal = new int[global.getNumRectangles()][];
    
    //rectagnle number
    int rectangle = 0;
    
    //upper bound
    int fixedBound = 0;
    
    //rotations allowed
    boolean rotate = false;
    
    //has been rotated
    boolean rotated = false;
    
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    //method to call, given an array of rectagnles 
    //will sort them using the bottom left heurstic
    //returns a grid
    public Grid bottomLeft(int[][] passedRectangle, boolean rotationAllowed) {
        counter = 0;
        int x = 0; 
        int y = 0; 
    
        // counts the number of rectangles placed
        counter = 0;
    
        // maximum height of already placed rectagnles
        maxHeight = 0;
    
        //maximum widht of already placed rectangles
        maxWidth = 0;
    
        //total area of the rectangles
        rectArea = 0;
    
        //array of heights of all the placed rectangles
        heights  = new int[global.getRectangles().length];
    
        //array of widths of all the placed rectangles
        widths = new int[global.getRectangles().length];
    
        //temporary placement
        placementFinal = new int[global.getNumRectangles()][];
    
        //rectagnle number
        rectangle = 0;
    
        //upper bound
        fixedBound = 0;
    
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        

        // assign placement of rectangle
        
        for (int i = 0; i < passedRectangle.length; i++) { 
            //System.out.println("rectagnle number: " + rectangle);
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
            System.out.println(rectangle);
            if (global.getType().equals("free") && !global.getRA() ) {
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            } 
            else if (global.getType().equals("free") && global.getRA() ) {
                    if (rotationAllowed){
                        rotate = true;
                    }
                    placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
                    //grid.setRotationsIndexI(false, i);
            } 
            else if (global.getType().equals("fixed") && !global.getRA() ) {
                fixedBound = global.getHeight();
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            }
            else if (global.getType().equals("fixed") && global.getRA() ) {
                fixedBound = global.getHeight();
                if (rotationAllowed){
                    rotate = true;
                }
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            }
            rectangle ++;
        }
        global.setRectangles(passedRectangle);
        grid.storePlacement(placementFinal);
        return grid;
    }

    public int computeLowestPoint2(int width, int height, int[][] passedRectangle, int x){
        int y2 = 0;
        int heightUsed = maxHeight;
        if(fixedBound != 0) {
            heightUsed = fixedBound - height;
        }
        for(int i = 0; i < heightUsed; i++) {
                //System.out.println("MAYBE FITS INTO 0 " + overlapsRectangle(passedRectangle, width, height, 0, x));
                if(overlapsRectangle(passedRectangle, width, height, 0, x)) {
                    //System.out.println("FITS INTO 0");
                    return 0;
                }
                if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                    //System.out.println("FITS INTO " + y2 + " " + x);
                    return y2;
                }
                //System.out.println("y count " + y2);
                y2 += 1;
        }
        return y2;
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
        boolean[] rotations = new boolean[(maxWidth + 1) * 2];
        int[][] lowestPoints = new int[(maxWidth + 1) * 2][];

        for(int i = 0; i < maxWidth + 1; i++) {
            if(overlapsRectangle(passedRectangle, width, height, 0, x)) {
                //System.out.println("FITS INTO 0 two");
                return new int[]{x2,y2};
            }
            else {
                y2 = computeLowestPoint2(width, height, passedRectangle, x2);

                //System.out.println("1 " + rectangle + " x " + x2 + " y " + y2 + " overlaps " + overlapsRectangle(passedRectangle, width, height, y2, x2));
                if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                    //System.out.println("1.1 " + rectangle + " x " + x2 + " y " + y2 + " overlaps " + overlapsRectangle(passedRectangle, width, height, y2, x2));
                    int[] coord = new int[]{x2, y2};
                    lowestPoints[count] = coord;
                    rotations[count] = false;
                    count ++;
                }
                x2 ++;
            }
        }
        if (rotate) {
            x2 = 0;
            for(int i = 0; i < maxWidth + 1; i++) {
                if(overlapsRectangle(passedRectangle, height, width, 0, x)) {
                //System.out.println("FITS INTO 0 two");
                    return new int[]{x2,y2};
                }
                else {
                    if(fixedBound != 0) {
                    y2 = computeLowestPoint2(height, width, passedRectangle, x2);
                    //System.out.println("2 " + rectangle + " x " + x2 + " y " + y2 + " overlaps " + overlapsRectangle(passedRectangle, height, width, y2, x2));
                    if(overlapsRectangle(passedRectangle, height, width, y2, x2)) {
                        //System.out.println("2.1 " + rectangle + " x " + x2 + " y " + y2 + " overlaps " + overlapsRectangle(passedRectangle, height, width, y2, x2));
                        int[] coord = new int[]{x2, y2};
                        lowestPoints[count] = coord;
                        rotations[count] = true;
                        count ++;
                    }
                    x2 ++;
                }
            }
        }
        }
        float ration = 200000.0f;
        float rationTemp = 0;
        float rectAreaTemp = rectArea;
        int xMaximized = 100000000;
        rectAreaTemp += (width * height);
        for(int i = 0; i < lowestPoints.length; i++) {
            if (lowestPoints[i] != null && overlapsRectangle(passedRectangle, height, width, lowestPoints[i][1], lowestPoints[i][0])) {
                float maxWidth_1 = maxWidth;
                float maxHeight_1 = maxHeight;
                //System.out.println(rectangle + " x " + lowestPoints[i][0] + " y " + lowestPoints[i][1] + " overlaps " + overlapsRectangle(passedRectangle, height, width, lowestPoints[i][1], lowestPoints[i][0]));
                if(rotations[i]) {
                    if((lowestPoints[i][0] + height) > maxWidth_1) {
                        maxWidth_1  = lowestPoints[i][0] + height;
                    }
                    if((lowestPoints[i][1] + width) > maxHeight_1) {
                        maxHeight_1 = lowestPoints[i][1] + width;
                    } 
                }
                else {
                    if((lowestPoints[i][0] + width) > maxWidth_1) {
                        maxWidth_1  = lowestPoints[i][0] + width;
                    }
                    if((lowestPoints[i][1] + height) > maxHeight_1) {
                        maxHeight_1 = lowestPoints[i][1] + height;
                    } 
                }
                rationTemp = ((maxHeight_1 * maxWidth_1)/rectAreaTemp);
                if(fixedBound != 0) {
                    if(xMaximized > maxWidth_1) {
                        xMaximized = (int)maxWidth_1;
                        y2 = lowestPoints[i][1];
                        x2 = lowestPoints[i][0];
                        if(rotate) {
                            grid.setRotationsIndexI(rotations[i], rectangle);
                            passedRectangle[rectangle][0] = height;
                            passedRectangle[rectangle][1] = width;
                            rotated = true;
                        }
                    }
                }
                else{
                    if(rationTemp < ration) {
                        ration = rationTemp;
                        y2 = lowestPoints[i][1];
                        x2 = lowestPoints[i][0];
                        if(rotate) {
                            grid.setRotationsIndexI(rotations[i], rectangle);
                            passedRectangle[rectangle][0] = height;
                            passedRectangle[rectangle][1] = width;
                            rotated = true;
                        }
                    }
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
        //System.out.println("Rectangle safe " + rectang;);
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
        if(rotated) {
            heights[counter] = width + lowestY;
            widths[counter] = height + closestX;
        }
        else {
            heights[counter] = height + lowestY;
            widths[counter] = width + closestX;
        }
        rotated = false;
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
        grid.setRotationsLength(global.getNumRectangles());
        
        bottomLeft(rectangle, global.getRA());
    }  
}
