package algorithms;

import java.util.ArrayList;
import java.util.List;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author stefa
 */
public class BruteForceLeftBottomAlgorithm extends AbstractAlgorithm{
    int[] positions;
    List<Integer> result;
    double bestValue = 0;
    int[][] bestResult;
    public BruteForceLeftBottomAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }

    @Override
    public void run() {
        result = new ArrayList();
        calcLB(new ArrayList(), 0);
        
        // Set best placement in Grid
        grid.storePlacement(bestResult);
    }
    
    public void calcLB(ArrayList<Integer> remaining, int depth){
        // If we have a full list of placements
        // then use LB and return the value of that placement
        if(depth >= positions.length){
            // Use LB -------------------------TO DO
            System.out.println(result);
             // Calc score of the solution
            grid.computeFinalDensity(global.getType(), global.getHeight(), global);
            if(grid.getDensity() > bestValue){
                bestValue = grid.getDensity();
                bestResult = toArray(grid.getPlacement());
            }
        }
        // If not then recursivly add remaining placement to the old one
        int counter = 0;
        for(int i: remaining){
            if(i < 0){
                continue;
            }
            result.add(i);
            remaining.set(counter, -1);
            calcLB(remaining, depth + 1);
            remaining.set(counter, i);
            result.remove(result.size() - 1);
            counter++;
        }
    }

    private int[][] toArray(int[][] result) {
        int[][] array = new int[result.length][2];
        int counter = 0;
        for(int[] i: result){
            array[counter][0] = i[0];
            array[counter][1] = i[1];
            counter++;
        }
        return array;
    }
    
}
