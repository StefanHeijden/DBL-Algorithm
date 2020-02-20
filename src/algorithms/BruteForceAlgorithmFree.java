
package algorithms;

import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author Leighton
 * 
 * The purpose of this algorithm is to compute good and if possible perfect
 * packings for small instances. It tries out all different orderings of all 
 * rectangles in trying to fill each corner made by a combination of either 
 * rectangles or the boundaries. It computes the density for each packing
 * if the density is bigger than previous packing then it stores that packing
 * if the density is 100% (so a perfect packing) it immediately returns this as
 * placement as a result.
 */
public class BruteForceAlgorithmFree extends AbstractAlgorithm{
    
    BruteForceAlgorithmFree(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    @Override
    public void run() {
        
    }
}
