/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//TO DO:
//ADDING WHAT IT NEEDS TO DO IF THERE IS NO SOLUTION
//TESTING
//MAYBE ADDING EASIER WAY OR SORTING BOLDBLACKVERTICALLINES
//MAYBE ADDING EASIER WAY OF UPDATING SLOTS?
//ADDING ROTATING OF RECTANGLES

package algorithms;

import logic.GlobalData;
import logic.Grid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
//import java.util.Collections;

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
    
    //takes the upper left corner of a slot
    public int[] UpperLeftCorner (int[] slot){
        int xSlot = slot[0];
        int ySlot = slot[1] + slot[2];
        int[] coordinates = {xSlot, ySlot};
        return coordinates;
    }
    
    //takes the upper left corner of a rectangle when it is placed to the side
    //of the slot with the neighboring rectangle with the highest width
    public int[] FattestNeighboringPiece (int[] slot, ArrayList<ArrayList<Integer>> placedRectangles, ArrayList<ArrayList<Integer>> places){
        int xSlot = slot[0];
        int yLowerSlot = slot[1];
        int yUpperSlot = slot[1] + slot[2];
        int[] coordinates = new int[2];
        
        //index for the upper neighboring rectangle
        int u = 0;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the upper left corner of the slot
            if(places.get(i).get(0) == yUpperSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                u = i;
            }
        }
        
        //index for the lower neighboring rectangle
        int l = 0;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the lower left corner of the slot
            if((places.get(i).get(0) + placedRectangles.get(i).get(0)) == yLowerSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                l = i;
            }
        }
        
        //if the width of the lower neighbor is bigger than the width of the upper neighbor
        if(placedRectangles.get(1).get(0) > placedRectangles.get(u).get(0)){
            //the rectangle has to be placed in the lower left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yLowerSlot + placedRectangles.get(1).get(1);
        }
        //if the width of the lower neighbor is not bigger than the width of the upper neighbor
        else{
            //the rectangle has to be placed in the upper left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yUpperSlot;
        }
        
        return coordinates;
    }
    
     //takes the upper left corner of a rectangle when it is placed to the side
    //of the slot with the neighboring rectangle with the highest width
    public int[] ThinnestNeighboringPiece (int[] slot, ArrayList<ArrayList<Integer>> placedRectangles, ArrayList<ArrayList<Integer>> places){
        int xSlot = slot[0];
        int yLowerSlot = slot[1];
        int yUpperSlot = slot[1] + slot[2];
        int[] coordinates = new int[2];
        
        //index for the upper neighboring rectangle
        int u = 0;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the upper left corner of the slot
            if(places.get(i).get(1) == yUpperSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                u = i;
            }
        }
        
        //index for the lower neighboring rectangle
        int l = 0;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the lower left corner of the slot
            if((places.get(i).get(1) + placedRectangles.get(i).get(1)) == yLowerSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                l = i;
            }
        }
        
        //if the width of the lower neighbor is smaller than the width of the upper neighbor
        if(placedRectangles.get(1).get(0) < placedRectangles.get(u).get(0)){
            //the rectangle has to be placed in the lower left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yLowerSlot + placedRectangles.get(1).get(1);
        }
        //if the width of the lower neighbor is not smaller than the width of the upper neighbor
        else{
            //the rectangle has to be placed in the upper left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yUpperSlot;
        }
        
        return coordinates;
    }
    
    //adds an item to an two dimentional array
    public int[][] AddingToArray (int[][] array, int variables,  int[] item){
        int length = array.length;
        
        int[][] newArray = new int[length + 1][];
        
        for (int i = 0; i < length; i++){
            for (int j = 0; j < variables; j++){
                newArray[i][j] = array[i][j];
            }
        }   
  
        newArray[length] = item; 
        
        return newArray;
    } 
    
    //adds an item to an three array
    public int[][][] AddingToArray (int[][][] array, int rectangles, int variables, int[][] item){
        int length = array.length;
        
        int[][][] newArray = new int[length + 1][][];
        
        for (int i = 0; i < length; i++){
            for (int j = 0; j < rectangles; j++){
                for (int k = 0; k < variables; k++){
                newArray[i][j][k] = array[i][j][k];
                }
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
            gridWidth = gridWidth + rectangles[i][1];
        }
        
        //array with the placement policies
        String[] policies = new String[3];
        policies[0] = "upperLeftCorner";
        policies[1] = "fattestNeighboringPiece";
        policies[2] = "thinnestNeighboringPiece";
        
        
        //arrays with the solutions
        ArrayList<ArrayList<ArrayList<Integer>>> placedRectanglesSolutions = new ArrayList<ArrayList<ArrayList<Integer>>>();
        ArrayList<ArrayList<ArrayList<Integer>>> placesSolutions = new ArrayList<ArrayList<ArrayList<Integer>>>();
        int widthSolution = 1000000000;
        int indexSolution = 1000000000;
        int [][] finalPlaces = new int [rectangles.length][2];

        
        for(int p=0; p<policies.length; p++){
            String policie = policies[p];
            //identify the slot at the beginning
            int[][] slots = {{0, 0, gridHeight}}; //with x, y of lower left corner and heigth
        
            int[][] notPlacedRectangles = rectangles; //with width and height
            ArrayList<ArrayList<Integer>> placedRectangles = new ArrayList<ArrayList<Integer>>(); //with width and height
            ArrayList<ArrayList<Integer>> places = new ArrayList<ArrayList<Integer>>(); //with x and y of lower left corner
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
                    ArrayList<ArrayList<Integer>> boldBlackVertcalLines = new ArrayList<ArrayList<Integer>>(); //with x, lowest y and highest y
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
                    if(policie == "upperLeftCorner"){
                        allocationPlace = UpperLeftCorner(allocationSlot);
                    }
                    else if(policie == "fattestNeighboringPiece"){
                        FattestNeighboringPiece(allocationSlot, placedRectangles, places);   
                    }
                    else if(policie == "thinnestNeightboringPiece"){
                        ThinnestNeighboringPiece(allocationSlot, placedRectangles, places);   
                    }
                            
                    //storing the location
                    places.add(new ArrayList<Integer>());
                    places.get(places.size()-1).addAll(Arrays.asList(Arrays.stream(allocationPlace).boxed().toArray(Integer[]::new)));
                    //storing the rectangle
                    placedRectangles.add(new ArrayList<Integer>());
                    placedRectangles.get(placedRectangles.size()-1).addAll(Arrays.asList(Arrays.stream(allocationRectangle).boxed().toArray(Integer[]::new)));
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
                    for(int i=0; i<boldBlackVertcalLines.size(); i++){
                        int lowestYBoldBlackVertcalLine = boldBlackVertcalLines.get(i).get(1);
                        int highestYBoldBlackVertcalLine = boldBlackVertcalLines.get(i).get(2);
                    
                        //if boldBlackVerticalLines element is completely under rightSideOfAllocationRectangle
                        if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1] 
                                && rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //delete line from array
                            for(int k = 0; k < boldBlackVertcalLines.size(); k++){
                                if(boldBlackVertcalLines.get(k) == boldBlackVertcalLines.get(i)){
                                    boldBlackVertcalLines.remove(k);
                                }
                            }
                        }
                        //if upper part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //only hold bottom part of line
                            boldBlackVertcalLines.get(i).set(2, rightSideOfAllocationRectangle[1]);
                        }
                        //if bottom part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //only hold upper part of line
                            boldBlackVertcalLines.get(i).set(1, rightSideOfAllocationRectangle[2]);
                        }
                        //if middle part of boldBlackVerticalLines element is under rightSideOfAllocationRectangle
                        else if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1] 
                                && rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                            //add under part of line to array
                            boldBlackVertcalLines.add(new ArrayList<Integer>());
                            boldBlackVertcalLines.get(boldBlackVertcalLines.size()-1).addAll(boldBlackVertcalLines.get(i));
                            boldBlackVertcalLines.get(i).set(2, rightSideOfAllocationRectangle[1]);
                            //add upper part of line to array
                            boldBlackVertcalLines.add(new ArrayList<Integer>());
                            boldBlackVertcalLines.get(boldBlackVertcalLines.size()-1).addAll(boldBlackVertcalLines.get(i));
                            boldBlackVertcalLines.get(i).set(1, rightSideOfAllocationRectangle[2]);
                            //delete line from array
                            for(int k = 0; k < boldBlackVertcalLines.size(); k++){
                                if(boldBlackVertcalLines.get(k) == boldBlackVertcalLines.get(i)){
                                    boldBlackVertcalLines.remove(k);
                                }
                            }
                        }
                    }
                    //adding right side of allocation rectangle to boldBlackVertcalLines
                    boldBlackVertcalLines.add(new ArrayList<Integer>());
                    boldBlackVertcalLines.get(boldBlackVertcalLines.size()-1).addAll(Arrays.asList(Arrays.stream(rightSideOfAllocationRectangle).boxed().toArray(Integer[]::new)));
                    //AddingToArray (boldBlackVertcalLines, 3,  rightSideOfAllocationRectangle);
                    
                    //sorting boldBlackVertcalLines on first column in descending order
                    
                    //Maybe it can be done simpler?
                    //Collections.sort(boldBlackVertcalLines, Collections.reverseOrder());
                    
                    boolean needToBeSwapped;
                    do {
                        needToBeSwapped = false;
                        for (int i = 0; i < boldBlackVertcalLines.size() - 1; i++) {
                            //check if needed to swap
                            if (boldBlackVertcalLines.get(i).get(0) < boldBlackVertcalLines.get(i + 1).get(0)) {
                                 
                                //swap
                                int temp = boldBlackVertcalLines.get(i).get(0);
                                boldBlackVertcalLines.get(i).set(0, boldBlackVertcalLines.get(i + 1).get(0));
                                boldBlackVertcalLines.get(i + 1).set(0, temp);
                       
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
                    for(int i=0; i < boldBlackVertcalLines.size(); i++){
                        for(int j=0; j < dashedLines.length; i++){
                            //if bold black vertical line is (partly) in dashed line
                            if((dashedLines[i][1] >= boldBlackVertcalLines.get(i).get(1) && boldBlackVertcalLines.get(i).get(1) >= dashedLines[i][0])
                                || (dashedLines[i][1] >= boldBlackVertcalLines.get(i).get(2) && boldBlackVertcalLines.get(i).get(2) >= dashedLines[i][0])){
                                //same x and next to each other in y restults in same slot
                                if((boldBlackVertcalLines.get(i).get(0) != boldBlackVertcalLines.get(i-1).get(0)) && (boldBlackVertcalLines.get(i).get(2) != boldBlackVertcalLines.get(i-1).get(2)) 
                                        && (boldBlackVertcalLines.get(i).get(2) != boldBlackVertcalLines.get(i-1).get(1))){
                                    //add slot
                                    int [] element = {boldBlackVertcalLines.get(i).get(0), dashedLines[j][0], dashedLines[j][1],  dashedLines[j][0]};
                                    AddingToArray (slots, 3, element);
                                    
                                    //updating dashedLines
                                    // right side of the bold black vertical line is completely in the y of the dashed line
                                    if ((dashedLines[j][1] > boldBlackVertcalLines.get(i).get(1)) && (boldBlackVertcalLines.get(i).get(1) > dashedLines[j][0])
                                        && (dashedLines[j][1] > boldBlackVertcalLines.get(i).get(2)) && (boldBlackVertcalLines.get(i).get(2) > dashedLines[j][0])){
                                        
                                        //add line under the bold black vertical line
                                        int [] underLine = dashedLines[j];
                                        underLine[1] = boldBlackVertcalLines.get(i).get(1);
                                        AddingToArray (dashedLines, 3, underLine);
                                        
                                        //add line above the bold black vertical line
                                        int [] upperLine = dashedLines[j];
                                        upperLine[0] = boldBlackVertcalLines.get(i).get(2);
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
                                    else if ((dashedLines[j][1] > boldBlackVertcalLines.get(i).get(1)) && (boldBlackVertcalLines.get(i).get(1) > dashedLines[j][0])){
                                        //add line under the bold black vertical line
                                        int [] underLine = dashedLines[j];
                                        underLine[1] = boldBlackVertcalLines.get(i).get(1);
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
                                    else if ((dashedLines[j][1] > boldBlackVertcalLines.get(i).get(2)) && (boldBlackVertcalLines.get(i).get(2) > dashedLines[j][0])){
                                        //add line above the bold black vertical line
                                        int [] upperLine = dashedLines[j];
                                        upperLine[0] = boldBlackVertcalLines.get(i).get(2);
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
                else{
                    //go further with the next policy
                    break;
                }
            }
            //add solution to solutions
            
            placedRectanglesSolutions.add(new ArrayList<ArrayList<Integer>>());
            placedRectanglesSolutions.get(placedRectanglesSolutions.size()-1).addAll(placedRectangles);
            
            placesSolutions.add(new ArrayList<ArrayList<Integer>>());
            placesSolutions.get(placesSolutions.size()-1).addAll(places);
        } 
        
        
        for(int i = 0; i < placedRectanglesSolutions.size(); i++){
            //keeps the width of the solution of the placing policy
            int widthPolicySolution = 0; 
            for(int j = 0; j < rectangles.length; j++){
            
                //the x value of the right side of the rectangle
                int highestXofRectangle = placedRectanglesSolutions.get(i).get(j).get(0) + placesSolutions.get(i).get(j).get(0);
                if(highestXofRectangle > widthPolicySolution){
                    widthPolicySolution = highestXofRectangle;
                }
            }
            if(widthPolicySolution < widthSolution){
                indexSolution = i;
            }
        }
        
        //the places of the solution converted to an array
        int[][] placesSolution = new int[placesSolutions.get(indexSolution).size()][2];
        for(int i=0; i<placesSolutions.get(indexSolution).size();i++){
            ArrayList<Integer> locationOutput = placesSolutions.get(indexSolution).get(i);
            for(int j=0; j<locationOutput.size();j++){
                placesSolution[i][j] = locationOutput.get(j).intValue(); 
            }
        }
        
        //the rectangles of the solution converted to an array
        int[][] placedRectanglesSolution = new int[placedRectanglesSolutions.get(indexSolution).size()][2];
        for(int i=0; i<placedRectanglesSolutions.get(indexSolution).size();i++){
            ArrayList<Integer> rectangleOutput = placedRectanglesSolutions.get(indexSolution).get(i);
            for(int j=0; j<rectangleOutput.size();j++){
                placedRectanglesSolution[i][j] = rectangleOutput.get(j).intValue(); 
            }
        } 
        
        //sort the places so it matches the rectangles
        for(int i = 0; i < placedRectanglesSolution.length; i++){
            finalPlaces[placedRectanglesSolution[i][2]] = placesSolution[i];
        }
        
        //puts the values in the gui
        grid.storePlacement(finalPlaces);
    } 
}
