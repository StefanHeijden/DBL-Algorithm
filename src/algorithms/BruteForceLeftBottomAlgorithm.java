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
    ArrayList<Integer> positions;
    List<Integer> result;
    double bestValue = 0;
    int[][] bestResult;
    public BruteForceLeftBottomAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }

    @Override
    public void run() {
        System.err.println("Start algorithm");
        result = new ArrayList();
        positions = new ArrayList();
        for(int i = 0; i < global.getRectangles().length; i++){
            System.err.println(i);
            positions.add(i);
        }
        
        calcLB(0);
        
        // Set best placement in Grid
        grid.storePlacement(bestResult);
        System.err.println("Done with algorithm");
    }
    
    public void calcLB(int depth){
        // If we have a full list of placements
        // then use LB and return the value of that placement
        if(depth >= positions.size()){
            // Use LB -------------------------TO DO
             // Calc score of the solution
            grid.computeFinalDensity(global);
            if(grid.getDensity() > bestValue){
                bestValue = grid.getDensity();
                bestResult = toArray(grid.getPlacement());
            }
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
            calcLB(depth + 1);
            positions.set(counter, i);
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
