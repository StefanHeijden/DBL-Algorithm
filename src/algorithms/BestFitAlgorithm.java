/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//TO DO:
//ADDING BETTER WAY OF UPDATING SLOTS?
//ADDING RETURNING BEST SOLUTION
//ADDING POLICIES
//ADDING ROTATING OF RECTANGLES
//ADDING THAT FIRST RECTANGLE IS PLACED IN UPPER LEFT CORNER
//ADDING WHAT TO DO IF IF STATEMENT IS NOT MET
//FIXING INITIALIZING ARRAYS/REPLACE ARRAYS WITH ARRAYLISTS

package algorithms;

import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author Jodi
 */
public class BestFitAlgorithm extends AbstractAlgorithm {
    int [][] rectangles; //with width and height
    int gridHeight;
    int gridWidth; 
    
    public BestFitAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        //gets the data and gives every rectangle a number
        rectangles = new int[data.getRectangles().length][3];
        int i = 0;
        for(int[] r: data.getRectangles()){
            rectangles[i][0] = r[0];
            rectangles[i][1] = r[1];
            rectangles[i][2] = i;
            System.out.println(rectangles);
            i++;
        }
    }
    
    //takes the lower left corner of a slot
    public int[] UpperLeftCorner (int[] slot) {
        int xSlot = slot[0];
        int ySlot = slot[1] + slot[2];
        int[] coordinate = {xSlot, ySlot};
        return coordinate;
    }
    
    //adds an item to an array
    public int[][] AddingToArray (int[][] array, int width,  int[] item){
        int length = array.length;
        
        int[][] newArray = new int[length + 1][];
        
        for (int i = 0; i < length; i++){
            for (int j = 0; j < width; j++){
                newArray[i][j] = array[i][j];
                
            }
        }   
  
        newArray[length] = item; 
        
        return newArray;
    } 
    
    @Override
    public void run() {
        
        gridHeight = global.getHeight();
               
        //determining the optimal height of the sheet
        gridWidth = 0;
        for (int i=0; i<rectangles.length; i++){
            gridWidth = gridWidth + notPlacedRectangles[i][1];
        }
        
        //array with the placement policies
        String[] policies = new String[3];
        policies[0] = "lowerLeftCorner";
        policies[1] = "tallestNeighboringPiece";
        policies[2] = "shortestNeighboringPiece";
        
        /*
        //arrays with the solutions
        //solutions first policy
        int[][] placedRectangles1 = new int[rectangles.length][2];
        int[][] places1 = new int[rectangles.length][2];
        //solutions second policy
        int[][] placedRectangles2 = new int[rectangles.length][2];
        int[][] places2 = new int[rectangles.length][2];
        //solutions third policy
        int[][] placedRectangles3 = new int[rectangles.length][2];
        int[][] places3 = new int[rectangles.length][2];
        */
        
        for(int p=0; p<policies.length; p++){
            String policie = policies[p];
            //identify the slot at the beginning
            int[][] slots = {{0, 0, gridHeight}}; //with x, y of lower left corner and heigth
        
            int[][] notPlacedRectangles = rectangles; //with width and height
            int[][] placedRectangles; //with width and height
            int[][] places; //with x and y of lower left corner
            while (notPlacedRectangles.length > 0) {
                //checks if there is a rectangle which can fit in a slot
                boolean canFit = false;
                for(int i=0; i<notPlacedRectangles.length; i++){
                    for(int j=0; j<slots.length; j++){
                        if(rectangles[i][1] < slots[j][3]){
                            canFit = true;
                        } 
                        else {
                        
                        }
                    }
                }
                if (canFit){
                    //safe the highest score and the rectangle and place of the highest score
                    double highestScore = -1000000000;
                    int[] allocationSlot = new int[3]; //with x, y and height 
                    int[] allocationRectangle = new int[2]; //with width and height
                    int[] allocationPlace = new int[2]; //with width and height;
                    int[][] boldBlackVertcalLines; //with x, lowest y and highest y
                    //check every allocation
                    for(int i=0; i<notPlacedRectangles.length; i++){
                        for(int j=0; j<slots.length; j++){
                            //if rectangle fits in the slot;
                            if(rectangles[i][1] < slots[j][3]){
                                //possible values to compute the score with
                                double W = notPlacedRectangles[i][0]; //width of the piece
                                double H = notPlacedRectangles[i][1]; //height of the piece
                                double A = W * H; //area of piece
                                double SW = slots[j][0]; //sloth width, relative to base of sheet
                                double SHL = slots[j][2] - notPlacedRectangles[i][1]; //difference between slot and piece heights
                                double GH = gridHeight; //height of grid;
                                double GW = gridWidth * 1.5; //width of optimum solution multiplied by 1.5
                                //double ERC = ; //ephemeral random constant (to be determined if necessarily);

                                double score = (SHL / (GW - W)) - (SW + H);
                                //if the score is better, safe the score, rectangle and place
                                if (score > highestScore){
                                    highestScore = score;
                                    allocationSlot = notPlacedRectangles[i];
                                    allocationRectangle = slots[j];
                                }
                            }
                        }
                    }
                    //computing the exact place of the allocation
                    if(policie == "lowerLeftCorner"){
                        allocationPlace = UpperLeftCorner(allocationSlot);
                    }
                    //else if(policie == "tallestNeighboringPiece"){
                    // ;   
                    //}
                    //else if(policie == "smallestNeightboringPiece"){
                    // ;   
                    //}
                            
                    //storing the location and rectangle
                    AddingToArray (places, 2,  allocationPlace);
                    AddingToArray (placedRectangles, 2,  allocationRectangle);
                    //deleting rectangle from notPlacedRectangles
                    for(int k = 0; k < notPlacedRectangles.length; k++){
                        if(notPlacedRectangles[k] == allocationRectangle){
                            // shifting elements
                            for(int j = k; j < notPlacedRectangles.length - 1; j++){
                                notPlacedRectangles[j] = notPlacedRectangles[j+1];
                            }
                        }
                    }
                        
                    //updating boldBlackVertcalLines
                    int[] rightSideOfAllocationRectangle = {allocationPlace[0]+allocationRectangle[0], allocationPlace[0], allocationPlace[0]+allocationRectangle[1]}; //with x, lowest y and highest y
                    for(int i=0; i<boldBlackVertcalLines.length; i++){
                        int lowestYBoldBlackVertcalLine = boldBlackVertcalLines[i][1];
                        int highestYBoldBlackVertcalLine = boldBlackVertcalLines[i][2];
                    
                        //if boldBlackVerticalLines element is completely under rightSideOfAllocationRectangle
                        if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1] 
                                && rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //delete line from array
                            for(int k = 0; k < boldBlackVertcalLines.length; k++){
                                if(boldBlackVertcalLines[k] == boldBlackVertcalLines[i]){
                                    // shifting elements
                                    for(int j = k; j < boldBlackVertcalLines.length - 1; j++){
                                        boldBlackVertcalLines[j] = boldBlackVertcalLines[j+1];
                                    }
                                }
                            }
                        }
                        //if upper part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //only hold bottom part of line
                            boldBlackVertcalLines[i][2] = rightSideOfAllocationRectangle[1];
                        }
                        //if bottom part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //only hold upper part of line
                            boldBlackVertcalLines[i][1] = rightSideOfAllocationRectangle[2];
                        }
                        //if middle part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1] 
                                && rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //add under part of line to array
                            AddingToArray (boldBlackVertcalLines, 3,  boldBlackVertcalLines[i]);
                            boldBlackVertcalLines[i][2] = rightSideOfAllocationRectangle[1];
                            //add upper part of line to array
                            AddingToArray (boldBlackVertcalLines, 3,  boldBlackVertcalLines[i]);
                            boldBlackVertcalLines[i][1] = rightSideOfAllocationRectangle[2];
                            //delete line from array
                            for(int k = 0; k < boldBlackVertcalLines.length; k++){
                                if(boldBlackVertcalLines[k] == boldBlackVertcalLines[i]){
                                    // shifting elements
                                    for(int j = k; j < boldBlackVertcalLines.length - 1; j++){
                                        boldBlackVertcalLines[j] = boldBlackVertcalLines[j+1];
                                    }
                                }
                            }
                        }
                    }
                    //adding right side of allocation rectangle to boldBlackVertcalLines
                    AddingToArray (boldBlackVertcalLines, 3,  rightSideOfAllocationRectangle);
                    
                    //sorting boldBlackVertcalLines on first column in descending order
                    boolean needToBeSwapped;
                    do {
                        needToBeSwapped = false;
                        for (int i = 0; i < boldBlackVertcalLines.length - 1; i++) {
                            //check if needed to swap
                            if (boldBlackVertcalLines[i][0] < boldBlackVertcalLines[i + 1][0]) {
                                 
                                //swap
                                int temp = boldBlackVertcalLines[i][0];
                                boldBlackVertcalLines[i][0] = boldBlackVertcalLines[i + 1][0];
                                boldBlackVertcalLines[i + 1][0] = temp;
                       
                                needToBeSwapped = true;
                            }
                        }
                    } while (needToBeSwapped);
                    
                    /* maybe it can be done simpler?
                    //updating slots
                    for(int i = 0; i < boldBlackVertcalLines.length; i++){
                        int[] slot = new int[3];
                        slot[0] = boldBlackVertcalLines[i][0];
                        for(int j = 0; j < placedRectangles.length; j++){

                        }
                        AddingToArray (slots, 3, slot);
                    }
                    */
                      
                    //initializing a array with the dashed lines
                    int[][] dashedLines = {{0, gridHeight}}; //with lower y and higher y
                    
                    //updating slots
                    for(int i=0; i < boldBlackVertcalLines.length; i++){
                        for(int j=0; j < dashedLines.length; i++){
                            //if bold black vertical line is (partly) in dashed line
                            if((dashedLines[i][1] >= boldBlackVertcalLines[i][1] && boldBlackVertcalLines[i][1] >= dashedLines[i][0])
                                || (dashedLines[i][1] >= boldBlackVertcalLines[i][2] && boldBlackVertcalLines[i][2] >= dashedLines[i][0])){
                                //same x and next to each other in y restults in same slot
                                if((boldBlackVertcalLines[i][0] != boldBlackVertcalLines[i-1][0]) && (boldBlackVertcalLines[i][1] != boldBlackVertcalLines[i-1][2]) && (boldBlackVertcalLines[i][2] != boldBlackVertcalLines[i-1][1])){
                                    //add slot
                                    int [] element = {boldBlackVertcalLines[i][0], dashedLines[j][0], dashedLines[j][1],  dashedLines[j][0]};
                                    AddingToArray (slots, 3, element);
                                    
                                    //updating dashedLines
                                    // right side of the bold black vertical line is completely in the y of the dashed line
                                    if ((dashedLines[j][1] > boldBlackVertcalLines[i][1]) && (boldBlackVertcalLines[i][1] > dashedLines[j][0])
                                        && (dashedLines[j][1] > boldBlackVertcalLines[i][2]) && (boldBlackVertcalLines[i][2] > dashedLines[j][0])){
                                        
                                        //add line under the bold black vertical line
                                        int [] underLine = dashedLines[j];
                                        underLine[1] = boldBlackVertcalLines[i][1];
                                        AddingToArray (dashedLines, 3, underLine);
                                        
                                        //add line above the bold black vertical line
                                        int [] upperLine = dashedLines[j];
                                        upperLine[0] = boldBlackVertcalLines[i][2];
                                        AddingToArray (dashedLines, 3, upperLine);
                                        
                                        //delete line from array with dashed lines
                                        for(int k = 0; k < dashedLines.length; k++){
                                            if(dashedLines[k] == dashedLines[j]){
                                                // shifting elements
                                                for(int l = k; l < dashedLines.length - 1; l++){
                                                    dashedLines[j] = dashedLines[l+1];
                                                }
                                            }
                                        }
                                    }
                                    // right side of the bold black vertical line is completely in the y of the dashed line
                                    else if ((dashedLines[j][1] > boldBlackVertcalLines[i][1]) && (boldBlackVertcalLines[i][1] > dashedLines[j][0])){
                                        //add line under the bold black vertical line
                                        int [] underLine = dashedLines[j];
                                        underLine[1] = boldBlackVertcalLines[i][1];
                                        AddingToArray (dashedLines, 3, underLine);
                                        
                                        //delete line from array with dashed lines
                                        for(int k = 0; k < dashedLines.length; k++){
                                            if(dashedLines[k] == dashedLines[j]){
                                                // shifting elements
                                                for(int l = k; l < dashedLines.length - 1; l++){
                                                    dashedLines[j] = dashedLines[l+1];
                                                }
                                            }
                                        }
                                    }
                                    else if ((dashedLines[j][1] > boldBlackVertcalLines[i][2]) && (boldBlackVertcalLines[i][2] > dashedLines[j][0])){
                                        //add line above the bold black vertical line
                                        int [] upperLine = dashedLines[j];
                                        upperLine[0] = boldBlackVertcalLines[i][2];
                                        AddingToArray (dashedLines, 3, upperLine);
                                        
                                        //delete line from array with dashed lines
                                        for(int k = 0; k < dashedLines.length; k++){
                                            if(dashedLines[k] == dashedLines[j]){
                                                // shifting elements
                                                for(int l = k; l < dashedLines.length - 1; l++){
                                                    dashedLines[j] = dashedLines[l+1];
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    
                    
                    
                    
                    
                    
                }
            }    
        } 
        
        
        
    }
    
    
    
    
}
