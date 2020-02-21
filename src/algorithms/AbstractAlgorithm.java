package algorithms;

import logic.GlobalData;
import logic.Grid;


/**
 *
 * Update this class to have all general methods for each algorithm
 */
public abstract class AbstractAlgorithm {
    Grid grid;
    GlobalData global;
    
    public AbstractAlgorithm(Grid grid, GlobalData data){
        this.grid = grid;
        this.global = data;
    };
    
    public abstract void run();
}
