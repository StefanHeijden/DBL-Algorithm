package algorithms;

import java.util.ArrayList;
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
        // TODO -----------------------------------------------------------------
        // Count the number of calculation that are made
        numberOfCalculations++;
        // Calc score of the solution
//        grid.computeFinalDensity(global);
//        if(grid.getDensity() > bestValue){
//            bestValue = grid.getDensity();
//            bestResult = toArray(grid.getPlacement());
//            bestRotations = toArray(rotations);
//        }
    }
    
    // Returns a copy of the array given
    public boolean[] toArray(ArrayList<Boolean> result) {
        boolean[] array = new boolean[result.size()];
        int counter = 0;
        for(boolean i: result){
            array[counter] = i;
            counter++;
        }
        return array;
    }
}
