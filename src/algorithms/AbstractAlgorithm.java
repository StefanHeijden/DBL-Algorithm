/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithms;

import logic.Grid;


/**
 *
 * Update this class to have all general methods for each algorithm
 */
public abstract class AbstractAlgorithm {
    Grid grid;
    
    public AbstractAlgorithm(Grid grid){
        this.grid = grid;
    };
    
    public abstract void run();
}
