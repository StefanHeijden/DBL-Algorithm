package main;

import logic.*;
import algorithms.*;

/**
 *
 * @author stefa
 */
public class PackingSolver {
    public static AbstractReader input;
    private static GlobalData data;
    public static Grid grid;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Read from System.in
        input = new MomotorReader("Doesn't matter, reads from System.in");
        run();
    }
    
    public static void runFromGUI(String file) {
        // read inputs from file
        input = new TestReader(file);
        run();
        
    }
    
    public static void run() {
        // read inputs from reader
        data = input.read();
        
        // Use inputs to determine what algorithm to run
        grid = new Grid();
        AbstractAlgorithm algorithm = new TestingAlgorithm(grid, data);
        // The execute algorithm
        algorithm.run();
        
        //Use the results of the algorithm to write the output
        Writer output = new Writer(data, grid);
        output.writeOutput();
    }
    
    public int[][] getRectangles(){
        return data.getRectangles();
    }
    
    public String[] getGlobalData(){
        return data.dataToString();
    }
    
    public int[][] getPlacement(){
        return grid.getPlacement();
    }
}
