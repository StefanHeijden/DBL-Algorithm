package main;

import logic.*;
import algorithms.*;

/**
 *
 * @author stefa
 */
public class PackingSolver {
    // Some variables to be able to pas them to the GUI
    public static AbstractReader input;
    private static GlobalData data;
    public static Grid grid;
    
    // Use the next variables to determine what algorithm to run
    private final static int BRUTEFORCETHRESHOLD = 6;
    
    // Use the next variables to determine what algorithm to run FOR TESTING!
    private static String testingAlgorithm = "standard";
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // Read from System.in
        input = new MomotorReader("Doesn't matter, reads from System.in");
        run();
    }
    
    public static void runFromGUI(String file, String algorithm) {
        // read inputs from file
        input = new TestReader(file);
        testingAlgorithm = algorithm;
        run();
        
    }
    
    public static void run() {
        // read inputs from reader
        data = input.read();
        
        // Use inputs to determine what algorithm to run
        grid = new Grid();
        AbstractAlgorithm algorithm = getAlgorithm();
        // The execute algorithm
        algorithm.run();
        
        //Use the results of the algorithm to write the output
        Writer output = new Writer(data, grid);
        output.writeOutput();
    }
    
    // Used in GUI for creaing rectangles to be drawn
    public int[][] getRectangles(){
        return data.getRectangles();
    }
    
    // Used in GUI for creaing rectangles to be drawn
    public int[][] getPlacement(){
        return grid.getPlacement();
    }
    
    public Grid getGrid(){
        return grid;
    }
    
    public GlobalData getData(){
        return data;
    }
    
    // Used in GUI for setting textArea, returns string of input data
    public String[] getGlobalData(){
        return data.dataToString();
    }
    
    // Determines what algorithm to use based on the input data
    // Used certain variables which we can change in order to get best results
    public static String getAlgorithmName(){
        // When testing we can use algorithm directly, or if we choose standard
        // way of choosing algorithm
        if(!testingAlgorithm.equals("standard")){
            return testingAlgorithm;
        }
        // Else determine the algorithm based on some variables
        // If number of rectangles is small, use Brute Force
        if(data.getNumRectangles() < BRUTEFORCETHRESHOLD){
            if(data.getType().equalsIgnoreCase(data.FREE)){
                return "BruteForceFree";
            }
        }
        return "Testing";
    }
    
    // Returns a new algorithm based on what algorithm must be used
    public static AbstractAlgorithm getAlgorithm(){
        String algorithmName = getAlgorithmName();
        // Return the algorithm based on the string used
        switch(algorithmName) {
        case "BruteForcFree":
            return new BruteForceAlgorithmFree(grid, data);
        case "BruteForceLeftBottom":
            return new BruteForceLeftBottomAlgorithm(grid, data);
        case "LevelPacking":    
            return new LevelPackingAlgorithm(grid, data);
        case "Testing":
            return new TestingAlgorithm(grid, data);
        }
        // If nothing is found
        System.out.println("Algorithm not found: " + testingAlgorithm);
        return new TestingAlgorithm(grid, data);
    }
    
}
