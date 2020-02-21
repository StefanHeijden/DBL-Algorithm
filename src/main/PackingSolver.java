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
    private static final String pathStefan = "C:/Users/stefa/Documents/DBL-Algorithm/testfiles/";
    private static final String pathEzra = "";
    private static final String pathYana = "C:/Users/yana/Documents/"
            +"DBL-Algorithm/testcases/";
    private static final String pathJodi = "C:/Users/s165698/Documents/DBL Algorithms/";
    
    //choose you path
    private static final String path = pathStefan;
    private static GlobalData data;
    public static Grid grid;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Name of the file you want to test
        String fileName = "0000_r4-h20-rn.in";
        run(fileName);
    }
    
    public static void run(String fileName) {
        // read inputs from file
        AbstractReader input = new MomotorReader(path + fileName);
        // read inputs from reader
        data = input.read();
        // Start GUI?
        
        // Use inputs to determine what algorithm to run
        grid = new Grid();
        AbstractAlgorithm algorithm = new TestingAlgorithm(grid, data);
        algorithm.run();
        
        //Use the results of the algorithm to write the output
        Writer output = new Writer(data, grid);
        output.writeOutput();
    }
    
    public int[][] getRectangles(){
        return data.getRectangles();
    }
    
    public int[][] getPlacement(){
        return data.getRectangles();
    }
}
