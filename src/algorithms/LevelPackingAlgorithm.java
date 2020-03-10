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
    
    int maxWidth = 0;
    
    int rectArea = 0;
    
    int[] heights = new int[global.getRectangles().length];
    
    int[] widths = new int[global.getRectangles().length];
    
    //temporary placement
    int[][] placementFinal = new int[global.getNumRectangles()][];
    
    int rectangle = 0;
    
    
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
            //System.out.println("rectagnle number: " + rectangle);
            int rectWidth = passedRectangle[i][0];
            int rectLength = passedRectangle[i][1];
           
            placementFinal[i] = computeBottomleftFree(rectWidth, rectLength, passedRectangle);
            rectangle ++;
        }
        grid.storePlacement(placementFinal);
        return grid;
    }
    public void lowestPoint(int width, int height, int[][] passedRectangle) {
        for(int i = 0; i < placementFinal.length - 1; i++) {
            if(placementFinal[i] != null) {
            }
        }
    }
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
        //System.out.println("HDUHISFSBFHSJHGDBHSG " + y);
        //System.out.println("HEREEEEEEE " + overlapsRectangle(passedRectangle, width, height, 0, x));
        if(overlapsRectangle(passedRectangle, width, height, 0, x)){
            //System.out.println("returns 0");
            return 0;
        }
        return y;
    }
    
    public int computeClosestXPoint(int width, int height, int[][] passedRectangle, int lowestY){
        int[] xOptions = new int[placementFinal.length];
        for(int i = 0; i < xOptions.length; i++) {
            xOptions[i] = -1;
        }
        int count = 0;
        float[] ratio = new float[2];
        ratio[0] = x;
        ratio[1] = 10000000;
        
        for(int i = 0; i < placementFinal.length; i ++) {
            int x2 = x;
            if(placementFinal[i] != null) {
                if((lowestY >= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) >= (lowestY + height))) {
                    x2 = (passedRectangle[i][0] + placementFinal[i][0]);
                    //System.out.println("x Rectangle larger or same size as current, with y2 " + lowestY + " x " + x2);
                    if(overlapsRectangle(passedRectangle, width, height, lowestY, x2)) {
                        xOptions[count] = x2;
                    }
                }
                if((lowestY <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) >= (lowestY + height)) && ((lowestY + height) > (placementFinal[i][1]))) {
                    x2 = (passedRectangle[i][0] + placementFinal[i][0]);
                    //System.out.println("x Rectangle starts in middle and end after current, with y2 " + lowestY + " x " + x2);
                    if(overlapsRectangle(passedRectangle, width, height, lowestY, x2)) {
                        xOptions[count] = x2;
                    }
                }
                if((lowestY <= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (lowestY + height))) {
                    x2 = (passedRectangle[i][0] + placementFinal[i][0]);  
                    //System.out.println("x Rectangle in middle of current, with y2 " + lowestY + " x " + x2);
                    if(overlapsRectangle(passedRectangle, width, height, lowestY, x2)) {
                        xOptions[count] = x2;
                    }
                }
                if((lowestY >= placementFinal[i][1]) && ((passedRectangle[i][1] + placementFinal[i][1]) <= (lowestY + height)) && (lowestY < passedRectangle[i][1] + placementFinal[i][1])) {
                    x2 = (passedRectangle[i][0] + placementFinal[i][0]);
                    //System.out.println("x Rectangle starts before and end in middle of current, with y2 " + lowestY + " x " + x2);
                    if(overlapsRectangle(passedRectangle, width, height, lowestY, x2)) {
                        xOptions[count] = x2;
                    }
                }
            }
            count ++;
        
        }
        for(int xValue: xOptions) {
            //System.out.println("xValue 2 " + xValue);
            if(xValue > -1) {
                //System.out.println("xValue 2.1 " + xValue);
                int rectAreaTemp = rectArea;
                rectAreaTemp += width * height;
                if((xValue + width) > maxWidth) {
                    maxWidth  = xValue + width;
                }
                if((lowestY + height) > maxHeight) {
                    maxHeight = lowestY + height;
                }
                if((maxHeight + maxWidth)/rectAreaTemp < ratio[1]) {
                    ratio[0] = xValue;
                    //System.out.println("xValue 2.2 " + xValue);
                    ratio[1] = rectAreaTemp/(maxHeight * maxWidth);
                }
            }
        }
        //System.out.println("PPPPPPPPPPPPPPPPPPPPPP ");
        if(overlapsRectangle(passedRectangle, width, height, lowestY, 0)) {
            //System.out.println("returns 0 x");
            return 0;
        }
        //System.out.println("alternative " + ratio[0] + " " + Math.round(ratio[0]));
        return Math.round(ratio[0]);
    }
    
    public int[] something(int[][] passedRectangle, int width, int height) {
        int y2 = 0;//maybe max height
        int x2 = 0;
        int count = 0;
        double[] ratio = new double[3];
        ratio[0] = x;
        ratio[1] = y;
        ratio[2] = 10000000.0;
        int[][] lowestPoints = new int[maxWidth + 1][];
        for(int i = 0; i < maxWidth + 1; i++) {
            //if(placementFinal[i] != null) {
                //System.out.println("Y " + y2 + " X " + x2 + " fits " + overlapsRectangle(passedRectangle, width, height, y2, x2));
                if(overlapsRectangle(passedRectangle, width, height, 0, x)) {
                        //System.out.println("MUST RETURN COORDINATES");
                        //int[] coord = new int[]{x2, y2};
                        //lowestPoints[i] = coord;
                        //x2 ++;
                        return new int[]{x2,y2};
                    }
                else {
                    y2 = computeLowestPoint(width, height, passedRectangle, x2);
                    if(overlapsRectangle(passedRectangle, width, height, y2, x2)) {
                        //System.out.println("YYYYYYYYYYYYY " + y2 + " XXXXXXXXXXX " + x2);
                        int[] coord = new int[]{x2, y2};
                        lowestPoints[count] = coord;
                        count ++;
                     
                    }
                    x2 ++;
                }
            //}
        }
        float ration = 200000.0f;
        float rationTemp = 0;
        float rectAreaTemp = rectArea;
        rectAreaTemp += (width * height);
        for(int[] coordinate: lowestPoints) {
            //System.out.println("COORDINATESSSS " + coordinate);
            if (coordinate != null) {
                //System.out.println("x coordinate " + coordinate[0] + " y " + coordinate[1]);
                //System.out.println("xValue 2.1 ");
                float maxWidth_1 = maxWidth;
                float maxHeight_1 = maxHeight;
                
                if((coordinate[0] + width) > maxWidth_1) {
                    maxWidth_1  = coordinate[0] + width;
                }
                if((coordinate[1] + height) > maxHeight_1) {
                    maxHeight_1 = coordinate[1] + height;
                } 
                rationTemp = ((maxHeight_1 * maxWidth_1)/rectAreaTemp);
                //System.out.println(rectAreaTemp + " " +  (maxHeight_1 * maxWidth_1) + " " + rationTemp + " " + coordinate[0] + " " + coordinate[1]);
                if(rationTemp < ration) {
                    ration = rationTemp;
                    //System.out.println(ration);
                    y2 = coordinate[1];
                    x2 = coordinate[0];
                    //System.out.println(x2 + " " + y2);
                    //ratio[0] = coordinate[0];
                    //ratio[1] = coordinate[1];
                    //ratio[2] = (maxHeight_1 * maxWidth_1)/rectAreaTemp;
                    //System.out.println(ratio[2]);
                }
            }
        } 
        
        //System.out.println("BEIFBSFSJHSJHSFSJHJHFFSJHFSEHGSRHSG " + ration + " " + x2 + " " + y2);
        //x2 = (int)ratio[0];
        //y2 = (int)ratio[1];
        return new int[]{x2,y2};
    }
    
    int issue = 0;
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
                issue ++;
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
        //System.out.println("Original y : " + y + " x " + x);
        //y = y + height;
        //x = x + width;
        int[] z = something(passedRectangle, width, height);
        int lowestY = z[1];
        int closestX = z[0];
        //int lowestY_1 = y;
        //int closestX_1 = x;
        //lowestY = computeLowestPoint(width, height, passedRectangle, x);
        //closestX = computeClosestXPoint(width, height, passedRectangle, lowestY);
        //closestX_1 = computeClosestXPoint(width, height, passedRectangle, y);
        //lowestY_1 = computeLowestPoint(width, height, passedRectangle, closestX_1);

        //System.out.println("Resulting y : " + lowestY + " Resulting x " + closestX);

        //int rectAreaTemp = rectArea;
        //rectAreaTemp += width * height;
        //int maxHeight_1 = maxHeight;
        //int maxWidth_1 = maxWidth;
        //int maxHeight_2 = maxHeight;
        //int maxWidth_2 = maxWidth;
        
        //if(height == 0) {
        //    height = 1;
        //}
        //if(width == 0){
        //    width = 1;
        //}
        //if(rectAreaTemp == 0) {
        //    rectAreaTemp = 1;
        //}
        //if((closestX + width) > maxWidth_1) {
        //    maxWidth_1  = closestX + width;
        //}
        //if((closestX_1 + width) > maxWidth_2) {
        //    maxWidth_2  = closestX_1 + width;
        //}
        //if((lowestY + height) > maxHeight_1) {
        //    maxHeight_1 = lowestY + height;
        //}
        //if((lowestY_1 + height) > maxHeight_2) {
        //    maxHeight_2 = lowestY_1 + height;
        //}
        //System.out.println("max height 1 " + maxHeight_1 + " width " + maxWidth_1);
        //System.out.println("max height 2 " + maxHeight_2 + " width " + maxWidth_2 + " area " + rectAreaTemp);
        //float ratio_1 = (maxHeight_1 * maxWidth_1)/rectAreaTemp;
        //float ratio_2 = (maxHeight_2 * maxWidth_2)/rectAreaTemp;
        //System.out.println("ratio 1 " + ratio_1 + " area " + rectAreaTemp + " total " + (maxHeight_1 * maxWidth_1));
        //System.out.println("ratio 2 " + ratio_2 + " area " + rectAreaTemp + " total " + (maxHeight_2 * maxWidth_2));
        //if(ratio_2 > ratio_1) {
        //    lowestY = lowestY_1;
        //    closestX = closestX_1;
        //}
        
        //creates return coordinates
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = closestX;
        returnCoordinates[1] = lowestY;
        
        //adds current object
        heights[counter] = height + lowestY;
        widths[counter] = width + closestX;
        //System.out.println("height of rectangle " + counter + " " + heights[counter]);
        
        //if current height is larger than existing, replace it
        if(heights[counter] > maxHeight) {
            maxHeight = heights[counter];
        }
        //System.out.println("Max height " + maxHeight);
        
        if (widths[counter] > maxWidth) {
            maxWidth = widths[counter];
        }
        //System.out.println("Max width " + maxWidth);
        rectArea = rectArea + width*height;

        //if (x >= sumRectWidth/2)      { // x coordinate replaced if greater than limit
        //    x = 0;
        //    y = maxHeight;
        //}
        //else {
        //x = maxWidth; 
        //y = maxHeight;
        //}
        counter ++;
        return returnCoordinates;
    }
    
    @Override
    public void run() {
        
        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        //int[][] placementFinal = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
                
        //comutes the x limit
        for (int i = 0; i < rectangle.length; i++) { 
            int rectWidth = rectangle[i][0];
            sumRectWidth += rectWidth;    
        }
        
        bottomLeft(rectangle, global.getRA());
    }  
}
