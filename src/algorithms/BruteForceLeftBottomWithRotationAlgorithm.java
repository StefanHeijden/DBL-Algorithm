package algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * This algorithm calculates all possible position and rotation the rectangles 
 * can be in and on each list of positions it then uses the LB algorithm and 
 * remembers the best solution for the positions and whether they were rotated.
 */
public class BruteForceLeftBottomWithRotationAlgorithm extends BruteForceLeftBottomAlgorithm {
    ArrayList<Boolean> rotations;
    boolean[]  bestRotations;
    
    boolean[] tempRot;
    int[][] tempRect;
    
    public BruteForceLeftBottomWithRotationAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        rotations = new ArrayList();
    }
    
    @Override
    public void doLoop(int depth){
        // Loop with rotating the current rectangle at position i
        rotations.add(true);
        calcLB(depth);
        rotations.remove(rotations.size() - 1);
        
        // Loop without rotating the current rectangle at position i
        rotations.add(false);
        calcLB(depth);
        rotations.remove(rotations.size() - 1);
    }
    
    // Run the Left Bottom heuristic with rotation
    @Override
    public void useLB(){
        // Run lb
        // Count the number of calculation that are made
        numberOfCalculations++;
        grid = bottemLeftAgorithm.bottomLeft(rectangles, false);
        
        // Calc score of the solution
        // First make sure the rectangles and rotations array ordened such
        // that they repesent the rectangles of the input in order
        orderWithRotations();
        // Update the grid
        grid.storePlacement(tempRect);
        grid.storeRotations(tempRot);
        // Then compute the density
        grid.computeFinalDensity(global);
        // And check whether the new density is better then the old one
        if(grid.getDensity() > bestValue){
            bestValue = grid.getDensity();
            bestResult = toArray(grid.getPlacement());
            bestRotations = toArray(tempRot);
        }
    }
    
    // Returns a copy of the array given
    public boolean[] toArray(boolean[] result) {
        boolean[] array = new boolean[result.length];
        int counter = 0;
        for(boolean i: result){
            array[counter] = i;
            counter++;
        }
        return array;
    }
    
    public void orderWithRotations(){
        tempRot = new boolean[global.getNumRectangles()];
        tempRect = new int[global.getNumRectangles()][2];
       
        for(int i = 0; i < tempRect.length; i++){
            int counter = 0;
            for(int[] placement: grid.getPlacement() ){
                if(i == rectangles[counter][2]){
                    tempRect[i][0] = placement[0];
                    tempRect[i][1] = placement[1];
                    tempRot[i] = rotations.get(i);
                    break;
                }
                counter++;
            }
        }
    }
    
    @Override
    public void setBestResult() {
        grid.storePlacement(bestResult);
        grid.storeRotations(bestRotations);
        System.out.println(Arrays.toString(bestRotations));
    }
}
