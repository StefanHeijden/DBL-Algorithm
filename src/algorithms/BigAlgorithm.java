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

        
    int x = 0; 
    int y = 0; 
    
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
    int[][] placementFinal = new int[25][];

    //return placement
    int[][] placementReturn = new int[25][];
    
    
    //rectagnle number
    int rectangle = 0;
    
    //upper bound
    int fixedBound = 0;
    
    //rotations allowed
    boolean rotate = false;
    
    //has been rotated
    boolean rotated = false;
    
    //total width of all rectangles (not yet added too)
    int totalWidth = 0;
    
    //big rectangle rotated
    boolean bigRotate = false;
    
    //number of rectangle actual
    int rectNum = 0;

    public BigAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    //method to call, given an array of rectagnles 
    //will sort them using the bottom left heurstic
    //returns a grid
    public int[][] bottomLeft(int[][] passedRectangle, boolean rotationAllowed, int size) {
        
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
        
        //treturn placement
        placementReturn = new int[size][];
    
        //rectagnle number
        rectangle = 0;
    
        //upper bound
        fixedBound = 0;
        
        // Series of if-statements that compute bottomleft differently
        // Computation based on vars containerType and rotationsAllowed
        // Very ugly implementation, purely meant for simple testing
        

        // assign placement of rectangle
        
        for (int i = 0; i < passedRectangle.length; i++) { 
            rectNum = passedRectangle[i][2];
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
            rectangle ++;
            int[] coord = new int[]{placementFinal[i][0], placementFinal[i][1], passedRectangle[i][2]};
            placementReturn[i] = coord;
        }
        //grid.storePlacement(placementFinal);
        return placementReturn;
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
                if((x >= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) >= (x + width))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    //System.out.println("y Rectangle larger or same size as current, with y2 " + y2 + " x " + x);
                    return y2;
                }
                if((x <= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) >= (x + width)) && ((x + width) > (placementFinal[i][0]))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                    
                    //System.out.println("y Rectangle starts in middle and end after current, with y2 " + y2 + " x " + x);
                        return y2;
                    }
                }
                if((x <= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) <= (x + width))) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);  
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                    
                    //System.out.println("y Rectangle starts in middle and end after current, with y2 " + y2 + " x " + x);
                        return y2;
                    }
                }
                if((x >= placementFinal[i][0]) && ((passedRectangle[i][0] + placementFinal[i][0]) <= (x + width)) && (x < passedRectangle[i][0] + placementFinal[i][0])) {
                    y2 = (passedRectangle[i][1] + placementFinal[i][1]);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x)) {
                    
                    //System.out.println("y Rectangle starts in middle and end after current, with y2 " + y2 + " x " + x);
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
        int x2 = x;
        int count = 0;
        boolean[] rotations = new boolean[(maxWidth + 1) * 2];
        int[][] lowestPoints = new int[(maxWidth + 1) * 2][];

        for(int i = 0; i < maxWidth + 1; i++) {
            y2 = computeLowestPoint2(width, height, passedRectangle, x2);
            if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                int[] coord = new int[]{x2, y2};
                lowestPoints[count] = coord;
                rotations[count] = false;
                count ++;
            }
            x2 ++;
        }
        if (rotate) {
            x2 = x;
            for(int i = 0; i < maxWidth + 1; i++) {
                    y2 = computeLowestPoint2(height, width, passedRectangle, x2);
                    if(overlapsRectangle(passedRectangle, height, width, y2, x2)) {
                        int[] coord = new int[]{x2, y2};
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
        int xMaximized = 100000000;
        rectAreaTemp += (width * height);
        for(int i = 0; i < lowestPoints.length; i++) {
            if (lowestPoints[i] != null && overlapsRectangle(passedRectangle, height, width, lowestPoints[i][1], lowestPoints[i][0])) {
                float maxWidth_1 = maxWidth;
                float maxHeight_1 = maxHeight;
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
                    if(rationTemp < ration) {
                        ration = rationTemp;
                        y2 = lowestPoints[i][1];
                        x2 = lowestPoints[i][0];
                        if(rotate) {
                            grid.setRotationsIndexI(rotations[i], rectNum);
                            passedRectangle[rectangle][0] = height;
                            passedRectangle[rectangle][1] = width;
                            rotated = true;
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
            //System.out.println(i);
            if(i == true) {
                return false;
            }
        }
        
        //System.out.println("rectangle fits ");
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
        //System.out.println("Rectangle safe " + rectangle);
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
    
    public int[][][] splitArray(int[][] arrayToSplit, int chunkSize){
        int chunks = arrayToSplit.length / chunkSize;
        int[][][] arrays = new int[chunks][][];
        for(int i = 0; i < chunks ; i++){
            arrays[i] = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize);
        }
        return arrays;
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
               
        int[][][] rectangleSplit = splitArray(rectangleOriginalRef, 10);
        int[][] rectangleTotal = new int[rectangleSplit.length][]; //1000
        
        int [][][] placementTemp = new int[rectangleSplit.length][][];
        for( int i = 0; i < rectangleSplit.length; i ++) {
            placementTemp[i] = bottomLeft(rectangleSplit[i], global.getRA(), 10);
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
            placementTempTotal[i] = bottomLeft(rectangleTotalSplit[i], false, 10);
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
            placementTempTotal2[i] = bottomLeft(rectangleTotalSplit2[i], false, 10);
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
        
        int [][] rectangleTotal5 = bottomLeft(rectangleTotal3, false, rectangleTotal2.length); //16
                
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
        
        grid.storePlacement(placement);
    }  
}
