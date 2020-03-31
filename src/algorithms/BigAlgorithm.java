package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import logic.GlobalData;
import logic.Grid;
/**
 *
 * @author yana
 * This class places rectangles in a given layout
 */
public class BigAlgorithm extends AbstractAlgorithm {

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
    
    //upper bound
    int fixedBound = 0;
    
    //rotations allowed
    boolean rotate = false;
    
    //has been rotated
    int rotated = 0;
    
    //rotations array with reference

    public BigAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    //method to call, given an array of rectagnles 
    //will sort them using the bottom left heurstic
    //returns a grid
    public int[][][] bottomLeft(int[][] passedRectangle, boolean rotationAllowed, int size, boolean local) {
        
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
        placementFinal = new int[size][];
        
        placementReturn = new int[size][];
    
        //upper bound
        fixedBound = 0;
        
        //rotationsF = new boolean[size];
        
    
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        

        // assign placement of rectangle
        for (int i = 0; i < passedRectangle.length; i++) { 
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
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
            int[] coord = new int[]{placementFinal[i][0], placementFinal[i][1], passedRectangle[i][2], rotated};
            placementReturn[i] = coord;
            if(local) {
                if(placementReturn[i][3] == 1) {
                    int rectW = passedRectangle[i][0];
                    int rectL = passedRectangle[i][1];
                    passedRectangle[i] = new int[]{rectL, rectW, passedRectangle[i][2]};
                }
            }   
        }
        int[][][] coord = new int[2][][];
        coord[0] = placementReturn;
        if(local) {
            coord[1] = passedRectangle;
        }
        return coord;
    }

    //looks at all y values and forwards the lowest y value at a given x position
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
        int[][] lowestPoints = new int[(maxWidth + 1) * 2][];

        for(int i = 0; i < maxWidth + 1; i++) {
                y2 = computeLowestPoint2(width, height, passedRectangle, x2);
            if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                int[] coord = new int[]{x2, y2, 0};
                lowestPoints[count] = coord;
                count ++;
            }
            x2 ++;
        }
        if(rotate) {
            x2 = 0;
            for(int i = 0; i < maxWidth + 1; i++) {
                    y2 = computeLowestPoint2(height, width, passedRectangle, x2);
                    if(overlapsRectangle(passedRectangle, height, width, y2, x2)) {
                        int[] coord = new int[]{x2, y2, 1};
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
                    fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                }
                else {
                    if((yInitial <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height))) {
                        fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                    }
                    else { 
                        if(((yInitial >= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) <= (yInitial + height)))  && (yInitial < (passedRectangle[i][1] + placementFinal[i][1]))) {
                            fits[count] = !overlapsXRectanlge(passedRectangle[i][0], width, xInitial, i);
                        }
                        else {
                            if(((yInitial <= placementFinal[i][1])&& ((passedRectangle[i][1] + placementFinal[i][1]) >= (yInitial + height))) && (placementFinal[i][1] < (yInitial + height))) {
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
            return false;
        }
        if((xInitial <= placementFinal[i][0]) && ((passedRectangle + placementFinal[i][0]) <= (xInitial + width))) {
            return false;
        }
        if(((xInitial >= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) <= (xInitial + width)) && ((xInitial) < (passedRectangle + placementFinal[i][0])))) {
            return false;
        }
        if (((xInitial <= placementFinal[i][0])&& ((passedRectangle + placementFinal[i][0]) >= (xInitial + width))) && ((xInitial + width) > (placementFinal[i][0]))) {
            return false;
        }
        return true;
    }

    
    // computation of bottomleft corner
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
    
    //inspired by https://stackoverflow.com/questions/27857011/how-to-split-a-string-array-into-small-chunk-arrays-in-java
    public int[][][] splitArray(int[][] rectangles, int perEach){
        int arrayNumber = rectangles.length / perEach;
        int[][][] arraysRect = new int[arrayNumber][][];
        for(int i = 0; i < arrayNumber ; i++){
            arraysRect[i] = Arrays.copyOfRange(rectangles, (i * perEach), (i * perEach) + perEach);
        }
        return arraysRect;
    }
    
    @Override
    public void run() {
        // getting the data from the logic package
        int[][][] rectangleStorage = new int[1000][][];
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
               
        int[][][] rectangleSplit = splitArray(rectangleOriginalRef, 10);
        int[][] rectangleTotal = new int[rectangleSplit.length][]; 
        
        int [][][] placementTemp = new int[rectangleSplit.length][][];
        int[][][] temp = new int[2][][];
        for( int i = 0; i < rectangleSplit.length; i ++) {
            temp = bottomLeft(rectangleSplit[i], global.getRA(), 10, true);
            placementTemp[i] = temp[0];
            rectangleStorage[i] = temp[1];
            rectangleTotal[i] = new int[]{maxWidth, maxHeight,0}; //1000
        }
        
        int[][] placement = new int[rectangleOriginalRef.length][]; //10000
        int count = 0;
        for(int i = 0; i < placementTemp.length; i ++) {
            for( int j = 0; j < placementTemp[0].length; j ++) {            
                placement[count] = placementTemp[i][j];
                count ++;
            }
        }
        
        int[][][] rectangleTotalSplit = splitArray(rectangleTotal, 10); //100
        int[][] rectangleTotal2 = new int[rectangleTotalSplit.length][]; //100
        
        int [][][] placementTempTotal = new int[rectangleTotalSplit.length][][]; //100
        for( int i = 0; i < rectangleTotalSplit.length; i ++) {
            temp = bottomLeft(rectangleTotalSplit[i], false, 10, false);
            placementTempTotal[i] = temp[0];
            rectangleTotal2[i] = new int[]{maxWidth, maxHeight, 0}; //16
        }
        
        int[][] placementTotal = new int[rectangleTotal.length][];
        count = 0;
        for(int i = 0; i < placementTempTotal.length; i ++) {
            for( int j = 0; j < placementTempTotal[0].length; j ++) {              
                placementTotal[count] = placementTempTotal[i][j];
                count ++;
            }
        }
        
        int[][][] rectangleTotalSplit2 = splitArray(rectangleTotal2, 10); //10
        int[][] rectangleTotal3 = new int[rectangleTotalSplit2.length][]; //10
        
        int [][][] placementTempTotal2 = new int[rectangleTotalSplit2.length][][]; //10
        for( int i = 0; i < rectangleTotalSplit2.length; i ++) {
            temp = bottomLeft(rectangleTotalSplit2[i], false, 10, false);
            placementTempTotal2[i] = temp[0];
            rectangleTotal3[i] = new int[]{maxWidth, maxHeight, 0}; //10
        }
        
        int[][] placementTotal2 = new int[rectangleTotal2.length][];
        count = 0;
        for(int i = 0; i < placementTempTotal2.length; i ++) {
            for( int j = 0; j < placementTempTotal2[0].length; j ++) {              
                placementTotal2[count] = placementTempTotal2[i][j];
                count ++;
            }
        }
        
//        int[][][] rectangleTotalSplit3 = splitArray(rectangleTotal3, 10); //10
//        int[][] rectangleTotal4 = new int[rectangleTotalSplit3.length][]; //10
//        
//        int [][][] placementTempTotal3 = new int[rectangleTotalSplit3.length][][]; //10
//        for( int i = 0; i < rectangleTotalSplit3.length; i ++) {
//            System.out.println(i);
//            placementTempTotal3[i] = bottomLeft(rectangleTotalSplit3[i], false, 10, false);
//            rectangleTotal4[i] = new int[]{maxWidth, maxHeight}; //10
//        }
//        
//        int[][] placementTotal3 = new int[rectangleTotal3.length][];
//        count = 0;
//        for(int i = 0; i < placementTempTotal3.length; i ++) {
//            for( int j = 0; j < placementTempTotal3[0].length; j ++) {              
//                placementTotal3[count] = placementTempTotal3[i][j];
//                count ++;
//            }
//        }
        temp = bottomLeft(rectangleTotal3, false, rectangleTotal2.length, false);
        int [][] rectangleTotal5 = temp[0]; //16
                
//        System.out.println("Done with last");
//        count = 0;
//        for(int i = 0; i < 10; i++) {
//            for(int j=0; j < 10; j++) {
//                placementTotal3[count][0] += rectangleTotal5[i][0];
//                placementTotal3[count][1] += rectangleTotal5[i][1];
//                count++;
//            }
//            
//        }
//        System.out.println("Accounted 1");
        
        count = 0;
        for(int i = 0; i < 10; i++) {
            for(int j=0; j < 10; j++) {
                placementTotal2[count][0] += rectangleTotal5[i][0];
                placementTotal2[count][1] += rectangleTotal5[i][1];
                count++;
            }
            
        }
        
        count = 0;
        for(int i = 0; i < 100; i++) {
            for(int j=0; j < 10; j++) {
                placementTotal[count][0] += placementTotal2[i][0];
                placementTotal[count][1] += placementTotal2[i][1];
                count++;
            }   
        }
        
        count = 0;
        for(int i = 0; i < 1000; i++) {
            for(int j=0; j < 10; j++) {
                placement[count][0] += placementTotal[i][0];
                placement[count][1] += placementTotal[i][1];
                count++;
            }
            
        }
            
        java.util.Arrays.sort(placement, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[2], b[2]);
            }
        });
        
        count = 0;
        int[][] rectanglesFinal = new int[global.getNumRectangles()][];
        for(int i = 0; i < rectangleStorage.length; i ++) {
            for( int j = 0; j < rectangleStorage[0].length; j ++) {   
                if(rectangleStorage[i] != null) {
                    rectanglesFinal[count] = rectangleStorage[i][j];
                    count ++;
                }
            }
        }
        
        java.util.Arrays.sort(rectanglesFinal, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(a[2], b[2]);
            }
        });
        
        
        boolean[] rotationsF = new boolean[global.getNumRectangles()];
        for(int i = 0; i < placement.length; i ++) {
            if(placement[i][3] == 1) {
                rotationsF[i] = true;
            }
        }
        grid.storeRotations(rotationsF);
        global.setRectangles(rectanglesFinal);
        grid.storePlacement(placement);
    }  
}
