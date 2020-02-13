/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import logic.*;
import algorithms.*;

/**
 *
 * @author stefa
 */
public class DBLAlgorithm {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // read inputs from file
        Reader input = new Reader("");
        // read inputs from reader
        GlobalData data = input.read();
        // Start GUI?
        
        // Use inputs to determine what algorithm to run
        Grid grid = new Grid();
        AbstractAlgorithm algorithm;
        //Use the results of the algorithm to write the output
        Writer output = new Writer(data, grid);
    }
    
}
