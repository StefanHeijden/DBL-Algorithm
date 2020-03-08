/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    public int[] LowerLeftCorner (int[] slot) {
        int xSlot = slot[0];
        int ySlot = slot[1];
        int[] coordinate = {xSlot, ySlot};
        return coordinate;
    }
    
    @Override
    public void run() {
        
        gridHeight = global.getHeight();
        //identify the slot at the beginning
        int[][] slots = {{0,0,gridHeight}}; //with x, y of lower left corner and heigth
        
        
        int[][] notPlacedRectangles = rectangles; //with width and height
        int[][] placedRectangles = new int[rectangles.length][3]; //with width and height
        int[][] places = new int[rectangles.length][2]; //with x and y of lower left corner
                
        //determining the optimal height of the sheet
        gridWidth = 0;
        for (int i=0; i<rectangles.length; i++){
            gridWidth = gridWidth + notPlacedRectangles[i][1];
        }
        
        //list with the placement policies
        String[] policies = new String[3];
        policies[0] = "lowerLeftCorner";
        policies[1] = "tallestNeighboringPiece";
        policies[2] = "shortestNeighboringPiece";
        
        for(int p=0; p<policies.length; p++){
            String policie = policies[p];
            while (notPlacedRectangles.length > 1) {
                //checks if every rectangle can fit in a slot
                boolean canFit = true;
                for(int i=0; i<notPlacedRectangles.length; i++){
                    for(int j=0; j<slots.length; j++){
                        if(rectangles[i][1] < slots[j][3]){

                        } 
                        else {
                            canFit = false;
                            break;
                        }
                    }
                }
                if (canFit){
                    //safe the highest score and the rectangle and place of the highest score
                    double highestScore = -1000000000;
                    int[] allocationSlot = new int[3]; //with x, y and height 
                    int[] allocationRectangle = new int[2]; //with width and height
                    int[] allocationPlace = new int[2]; //with width and height;
                    int[][] boldBlackVertcalLines = new int[rectangles.length][3]; //with x, lowest y and highest y
                    //check every allocation
                    for(int i=0; i<notPlacedRectangles.length; i++){
                        for(int j=0; j<slots.length; j++){
                            //if rectangle fits in the slot;
                            if(rectangles[i][1] < slots[j][3]){
                                //possible values to compute the score with
                                double W = notPlacedRectangles[i][0]; //width of the piece
                                double H = notPlacedRectangles[i][1]; //height of the piece
                                double A = W * H; //area of piece
                                double SH = slots[j][0]; //sloth width, relative to base of sheet
                                double SWL = slots[j][2] - notPlacedRectangles[i][1]; //difference between slot and piece heights
                                double SHW = gridHeight; //height of sheet;
                                double SHH = gridWidth * 1.5; //height of optimum solution multiplied by 1.5
                                //double ERC = ; //ephemeral random constant (to be determined if necessarily);

                                double score = (SWL / (SHW - W)) - (SH + H);
                                //if the score is better, safe the score, rectangle and place
                                if (score > highestScore){
                                    highestScore = score;
                                    allocationSlot = notPlacedRectangles[i];
                                    allocationRectangle = slots[j];
                                }
                            }
                            //computing the exact place of the allocation
                            if(policie == "lowerLeftCorner"){
                                allocationPlace = LowerLeftCorner(allocationSlot);
                            }
                            //else if(policie == "tallestNeighboringPiece"){
                            // ;   
                            //}
                            //else if(policie == "smallestNeightboringPiece"){
                            // ;   
                            //}
                            
                            //storing the location and rectangle
                            places[placedRectangles.length] = allocationPlace;
                            placedRectangles[placedRectangles.length] = allocationRectangle;
                        }
                    }
                //updating boldBlackVertcalLines
                int[] rightSideOfAllocationRectangle = {allocationPlace[0]+allocationRectangle[0], allocationPlace[0], allocationPlace[0]+allocationRectangle[1]}; //with x, lowest y and highest y
                for(int i=0; i<boldBlackVertcalLines.length; i++){
                    int lowestYBoldBlackVertcalLine = boldBlackVertcalLines[i][1];
                    int highestYBoldBlackVertcalLine = boldBlackVertcalLines[i][2];
                    
                    if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1] && rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                        //boldBlackVertcalLines.remove(boldBlackVertcalLines[i]);
                    }
                    else if(rightSideOfAllocationRectangle[2]>highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                        
                    }
                    else if(rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]){
                        
                    }
                }
                
                //updating slots
                    
                    
                    
                }
                
                
            }    
        }       
    }
}
