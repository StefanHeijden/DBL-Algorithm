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
    public BestFitAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        rectangles = new int[data.getRectangles().length][3];
        int i = 0;
        for(int[] r: data.getRectangles()){
            rectangles[i][0] = r[0];
            rectangles[i][1] = r[1];
            rectangles[i][2] = i;
            i++;
        }
    }
    
    public int[] LeftLowerCorner (int[][] slot) {
        int[] coordinates = slot[0];
        int xCoordinate = coordinates[0];
        int yCoordinate = coordinates[1];
        int[] coordinate = {xCoordinate, yCoordinate};
        return coordinate;
    }
    
    public int[] LeftUpperCorner (int[][] slot, int gridHeight) {
        int[] coordinates = slot[0];
        int xCoordinate = coordinates[0];
        int yCoordinate = coordinates[1];
        int slotHeight = coordinates[3];
        int[] coordinate = {xCoordinate, yCoordinate + slotHeight};
        return coordinate;
    }
    
    @Override
    public void run() {
        int[][] rectangle = global.getRectangles();
        int gridHeight = global.getHeight();
        int[][] slots = {{0,0,100000000,gridHeight}};
        
    for (int i = 0; i < rectangle.length; i++){
        for (int j = 0; i < 2; j++){
        
        }
    }
        
        
        
        
    }
}
