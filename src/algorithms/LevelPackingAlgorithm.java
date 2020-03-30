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
    
    //return  placement if local
    int[][] placementReturn = new int[global.getNumRectangles()][];
    
    //rectagnle number
    int rectangle = 0;
    
    //upper bound
    int fixedBound = 0;
    
    //rotations allowed
    boolean rotate = false;
    
    //has been rotated
    int rotated = 0;
    
    int rectNum;
    
    //rotations array with reference
    boolean[] rotationsF = new boolean[global.getNumRectangles()];

    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    //method to call, given an array of rectagnles 
    //will sort them using the bottom left heurstic
    //returns a grid
    public Grid bottomLeft(int[][] passedRectangle, boolean rotationAllowed, boolean local) {
        
        x = 0; 
        y = 0; 
        
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
        
        placementReturn = new int[global.getNumRectangles()][];
    
        //rectagnle number
        rectangle = 0;
    
        //upper bound
        fixedBound = 0;
        
        
    
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        
        // assign placement of rectangle
        for (int i = 0; i < passedRectangle.length; i++) { 
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
            rectNum = passedRectangle[i][2];
            if (global.getType().equals("free") && !global.getRA() ) {
                placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            } 
            else if (global.getType().equals("free") && global.getRA() ) {
                    if (rotationAllowed){
                        rotate = true;
                    }
                    placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
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
            if(local) {
                int[] coord = new int[]{placementFinal[i][0], placementFinal[i][1], passedRectangle[i][2], rotated};
                placementReturn[i] = coord;
                if(placementReturn[i][3] == 1) {
                    rotationsF[i] = true;
                    int rectW = passedRectangle[i][0];
                    int rectL = passedRectangle[i][1];
                    passedRectangle[i] = new int[]{rectL, rectW, passedRectangle[i][2]};
                }
            }
        }
        if(local) {
            java.util.Arrays.sort(placementReturn, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(a[2], b[2]);
                }
            });
            java.util.Arrays.sort(passedRectangle, new java.util.Comparator<int[]>() {
                public int compare(int[] a, int[] b) {
                    return Integer.compare(a[2], b[2]);
                }
            });
            int [][] rectanglesFinal = Arrays.copyOf(passedRectangle, passedRectangle.length);

            grid.storeRotations(rotationsF);
            global.setRectangles(rectanglesFinal);
            grid.storePlacement(placementReturn);
        }
        else {
            grid.storePlacement(placementFinal);
        }
        return grid;
    }

    public int computeLowestPoint2(int width, int height, int[][] passedRectangle, int x2){
        int y2 = 0;
        int heightUsed = maxHeight;
        if(fixedBound != 0) {
            heightUsed = fixedBound - height;
        }
        for(int i = 0; i < heightUsed; i++) {
            if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                return y2;
            }
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
                y2 = computeLowestPoint2(width, height, passedRectangle, x2);
            if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                int[] coord = new int[]{x2, y2, 0};
                lowestPoints[count] = coord;
                rotations[count] = false;
                count ++;
            }
            x2 ++;
        }
        if (rotate) {
            x2 = 0;
            for(int i = 0; i < maxWidth + 1; i++) {
                    y2 = computeLowestPoint2(height, width, passedRectangle, x2);
                    if(overlapsRectangle(passedRectangle, height, width, y2, x2)) {
                        int[] coord = new int[]{x2, y2, 1};
                        lowestPoints[count] = coord;
                        rotations[count] = true;
                        count ++;
                    }
                x2 ++;
            }
        }
        float ration = 200000.0f;
        float rationTemp = 0;
        float rectAreaTemp = rectArea;
        rectAreaTemp += (width * height);
        for(int i = 0; i < lowestPoints.length; i++) {
            boolean pass = false;
            if(lowestPoints[i] != null) {
            if((lowestPoints[i][2] == 0)) {
                pass = overlapsRectangle(passedRectangle, height, width, lowestPoints[i][1],lowestPoints[i][0]);
            }
            if((lowestPoints[i][2] == 1)) {
                pass = overlapsRectangle(passedRectangle, width, height, lowestPoints[i][1],lowestPoints[i][0]);
            }
            if (pass) {
                float maxWidth_1 = maxWidth;
                float maxHeight_1 = maxHeight;
                if(lowestPoints[i][2] == 1) {
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
                if(rationTemp < ration) {
                    ration = rationTemp;
                    y2 = lowestPoints[i][1];
                    x2 = lowestPoints[i][0];
                    if(lowestPoints[i][2] == 1) {
                        rotated = 1;
                    }
                    else {
                        rotated = 0;
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
                    fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                }
                else {
                    if((yInitial <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height))) {
                        //System.out.println("There is a rectangle in middle of current. height " + passedRectangle[i][1]);
                        fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                    }
                    else { 
                        if(((yInitial >= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height)))  && (yInitial < (passedRectangle[i][1] + placementFinal[i][1]))) {
                            //System.out.println("There is a rectangle starts before and ends in middle of current. height " + passedRectangle[i][1]);
                            fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                        }
                        else {
                            if(((yInitial <= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) >= (yInitial + height))) && (placementFinal[i][1] < (yInitial + height))) {
                                //System.out.println("There is a rectangle starts in middle and ends after current. height " + passedRectangle[i][1]);
                                fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                            }
                        }
                    }
                }
                count ++;
            }
        }
        for(boolean i: fits) {
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
        if(((xInitial >= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) <= (xInitial + width)) && ((xInitial) < (passedRectangle + placementFinal[i][0])))) {
            //System.out.println("There is a rectangle starts before and ends in middle of current. width " + passedRectangle);
            return false;
        }
        if (((xInitial <= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) >= (xInitial + width))) && ((xInitial + width) > (placementFinal[i][0]))) {
            return false;
        }
        return true;
    }

    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height, int[][] passedRectangle) {
        int[] z;
        int lowestY;
        int closestX;
            z = computeBottomLeftCoordinate(passedRectangle, width, height);
            lowestY = z[1];
            closestX = z[0];
        
        //creates return coordinates
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = closestX;
        returnCoordinates[1] = lowestY;
        
        //adds current object
        if(rotated == 1) {
            heights[counter] = width + lowestY;
            widths[counter] = height + closestX;
        }
        else {
            heights[counter] = height + lowestY;
            widths[counter] = width + closestX;
        }
        //rotated = 0;
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
        int[][] rectangleOriginal = global.getRectangles();
        grid.setRotationsLength(global.getNumRectangles());
        int[][] rectangleOriginalRef = new int[rectangleOriginal.length][];
        for(int i= 0; i < rectangleOriginal.length; i++) {
            int[] rect = new int[]{rectangleOriginal[i][0],rectangleOriginal[i][1],i};
            rectangleOriginalRef[i] = rect;
        }
        
        java.util.Arrays.sort(rectangleOriginalRef, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[0], a[0]);
            }
        });
        bottomLeft(rectangleOriginalRef, global.getRA(), true);
        
    }  
}
