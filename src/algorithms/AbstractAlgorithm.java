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
