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
    public int[] LeftLowerCorner (int[][] slot) {
        int[] coordinates = slot[0];
        int xCoordinate = coordinates[0];
        int yCoordinate = coordinates[1];
        int[] coordinate = {xCoordinate, yCoordinate};
        return coordinate;
    }
    
    @Override
    public void run() {
        
        gridHeight = global.getHeight();
        //identify the slot at the beginning
        int[][] slots = {{0,0,gridHeight}};
        
        
        int[][] notPlacedRectangles = rectangles;
        int[][] placedRectangles = new int[rectangles.length][3];
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
                int highestScore;
                int[] allocationSlot;
                int[] alloctationReclangle;
                //check every allocation
                for(int i=0; i<notPlacedRectangles.length; i++){
                    for(int j=0; j<slots.length; j++){
                    //score = ;
                    //if the score is better, safe the score, rectangle and place
                    //if (score > highestScore){
                        //higestScore = score;
                        //allocationSlot = notPlacedRectangles[i];
                        //allocatationRectangle = slots[j];
                    }
                }
            }    
        }       
    }
}
