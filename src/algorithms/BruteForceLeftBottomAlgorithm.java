package algorithms;

import java.util.ArrayList;
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
    int[][] rectangles;
    double bestValue = 0;
    int[][] bestResult;
    int numberOfCalculations = 0;
    LevelPackingAlgorithm bottemLeftAgorithm;
    int[][] finalResultingPlacement;
    boolean rotationsAllowed = false;
    
    public BruteForceLeftBottomAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        bottemLeftAgorithm = new LevelPackingAlgorithm(grid, data);
        // Initialize lists
        rectangles = new int[data.getNumRectangles()][3];
        positions = new ArrayList();
        
        // Initialize the position list with integers
        for(int i = 0; i < global.getRectangles().length; i++){
            positions.add(i);
        }
    }

    @Override
    public void run() {
        // Make sure LB runs no rotations, even if it is allowed
        if(global.getRA()){
            boolean[] rotations = new boolean[global.getNumRectangles()];
            for(int i = 0; i < global.getNumRectangles(); i++){
                rotations[i] = false;
            }
            grid.storeRotations(rotations);
            global.setRA(false);
            rotationsAllowed = true;
        }
        // Calculate the lists
        calcLB(0);
        
        // Set best placement in Grid
        setBestResult();
        grid.computeFinalDensity(global);
        
        // Reset rotationsAllowed for correct output
        if(rotationsAllowed){
            global.setRA(true);
        }
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
            rectangles[depth][0] = global.getRectangles()[i][0];
            rectangles[depth][1] = global.getRectangles()[i][1];
            rectangles[depth][2] = i;
            positions.set(counter, -1);
            doLoop(depth + 1);
            positions.set(counter, i);
            counter++;
        }
    }

    // Returns a copy of the array given
    public int[][] toArray(int[][] result) {
        int[][] array = new int[result.length][3];
        int counter = 0;
        for(int[] i: result){
            // Store placement
            array[counter][0] = i[0];
            array[counter][1] = i[1];
            // Store position
            array[counter][2] = rectangles[counter][2];
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
        grid = bottemLeftAgorithm.bottomLeft(rectangles, false, false);
        
        // Calc score of the solution
        grid.storePlacement(orden());
        grid.computeFinalDensity(global);
        if(grid.getDensity() > bestValue){
            bestValue = grid.getDensity();
            bestResult = toArray(grid.getPlacement());
        }
    }
 
    public int[][] orden(){
       int[][] ordened = new int[global.getNumRectangles()][2];
       
        for(int i = 0; i < ordened.length; i++){
            int counter = 0;
            for(int[] placement: grid.getPlacement() ){
                if(i == rectangles[counter][2]){
                    ordened[i][0] = placement[0];
                    ordened[i][1] = placement[1];
                    break;
                }
                counter++;
            }
        }
        return ordened;
    }

    public void setBestResult() {
        grid.storePlacement(bestResult);
    }
    
}
