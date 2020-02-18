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
public class PackingSolver {
    // You can add you file path here 
    private static final String pathLeigthon = "E:/TUe/PT/Courses/Y3/"
                + "DBL algorithms/testcases/";
    private static final String pathStefan = "./../testfiles";
    private static final String pathEzra = "";
    private static final String pathYana = "";
    private static final String pathJodi = "";
    
    // Name of the file you want to test
    private static final String fileName = "0000_r4-h20-rn.in";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // read inputs from file
        AbstractReader input = new MomotorReader(pathLeigthon + fileName);
        // read inputs from reader
        GlobalData data = input.read();
        // Start GUI?
        
        // Use inputs to determine what algorithm to run
        Grid grid = new Grid();
        AbstractAlgorithm algorithm = new TestingAlgorithm(grid, data);
        algorithm.run();
        
        //Use the results of the algorithm to write the output
        Writer output = new Writer(data, grid);
        output.writeOutput();
    }
    
}
