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
    int [][] rectangles;
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
        int[][] slots = {{0,0,gridHeight}};
        
        
        int[][] notPlacedRectangles = rectangles;
        int[][] placedRectangles = new int[rectangles.length][3];
        int[][] places = new int[rectangles.length][2];
                
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
                    int[] allocationSlot = new int[3];
                    int[] allocationRectangle = new int[2];
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
                            //computing the exact place of the rectangle and store it
                            if(policie == "lowerLeftCorner"){
                                places[placedRectangles.length] = LowerLeftCorner(allocationSlot);
                                placedRectangles[placedRectangles.length] = allocationRectangle;
                            }
                            //else if(policie == "tallestNeighboringPiece"){
                            // ;   
                            //}
                            //else if(policie == "smallestNeightboringPiece"){
                            // ;   
                            //}
                        }
                    }
                }
            }    
        }       
    }
}
