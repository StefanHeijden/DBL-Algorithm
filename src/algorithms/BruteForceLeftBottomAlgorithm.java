package algorithms;

import java.util.ArrayList;
import java.util.List;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * This algorithm calculates all possible position the rectangles can be in
 * and on each list of positions it then uses the LB algorithm and remembers
 * the best solution
 */
public class BruteForceLeftBottomAlgorithm extends AbstractAlgorithm{
    ArrayList<Integer> positions;
    List<Integer> result;
    double bestValue = 0;
    int[][] bestResult;
    int numberOfCalculations = 0;
    
    public BruteForceLeftBottomAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        // Initialize lists
        result = new ArrayList();
        positions = new ArrayList();
        
        // Initialize the position list with integers
        for(int i = 0; i < global.getRectangles().length; i++){
            positions.add(i);
        }
    }

    @Override
    public void run() {
        // Calculate the lists
        calcLB(0);
        
        // Set best placement in Grid
        grid.storePlacement(bestResult);
        
        // Show the number of calculation that are made
        System.err.println("Number Of Calculations: " + numberOfCalculations);
    }
    
    public void calcLB(int depth){
        // If we have a full list of placements
        // then use LB and return the value of that placement
        if(depth >= positions.size()){
            // Run the Left Bottom heuristic
            useLB();
        }
        // If not then recursivly add remaining placement to the old one
        int counter = 0;
        for(int i: positions){
            if(i < 0){
                counter++;
                continue;
            }
            result.add(i);
            positions.set(counter, -1);
            doLoop(depth + 1);
            positions.set(counter, i);
            result.remove(result.size() - 1);
            counter++;
        }
    }

    // Returns a copy of the array given
    public int[][] toArray(int[][] result) {
        int[][] array = new int[result.length][2];
        int counter = 0;
        for(int[] i: result){
            array[counter][0] = i[0];
            array[counter][1] = i[1];
            counter++;
        }
        return array;
    }
    
    // Recurse over the list of positions
    public void doLoop(int depth){
        calcLB(depth);
    }
    
    // Run the Left Bottom heuristic
    public void useLB(){
        // Run lb
        // Count the number of calculation that are made
        numberOfCalculations++;
        // TODO -----------------------------------------------------------------
        // Calc score of the solution
//        grid.computeFinalDensity(global);
//        if(grid.getDensity() > bestValue){
//            bestValue = grid.getDensity();
//            bestResult = toArray(grid.getPlacement());
//        }
    }
    
}
