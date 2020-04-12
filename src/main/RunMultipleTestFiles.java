package main;

import algorithms.AbstractAlgorithm;
import algorithms.BestFitAlgorithm;
import algorithms.BigAlgorithm;
import algorithms.BruteForceAlgorithm;
import algorithms.BruteForceLeftBottomAlgorithm;
import algorithms.LevelPackingAlgorithm;
import algorithms.TestingAlgorithm;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import logic.AbstractReader;
import logic.GlobalData;
import logic.Grid;
import logic.TestReader;

/**
 *
 * @author stefa
 * This class is used to run multiple test Files at once
 * it creates a file with the result in the TestFiles folder
 */
public class RunMultipleTestFiles {
    // ---------------------------IMPORTANT------------------------------------
    // You could change the path to somthing like:
    // "./../DBL-Algorithm/testfiles/Jodi/" for example if you want to test
    // the test in Jodi's folder
    private static final String PATH = "./../DBL-Algorithm/testfiles/";
    // Then change this to the algorithm you want to test
    private static final String testingAlgorithm = "BestFit";
    // ---------------------------IMPORTANT------------------------------------
 
    
    // Some variables to be able to do stuff
    private static AbstractReader input;
    private static GlobalData data;
    private static Grid grid;
    private static int numberOfFiles = 0;
    static StringBuffer inputBuffer;
    static String[] testFiles;
    static String[] density;
    static long[] runtime;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        testFiles = getFiles();
        //System.out.println(Arrays.toString(testFiles));
        density = new String[numberOfFiles];
        runtime = new long[numberOfFiles];
        
        for(int i = 0; i < numberOfFiles; i++){
            // Skip the result file if its in the testFiles
            if(testFiles[i].equals("result.txt")){
                continue;
            }
            // Print the current file being checked
            System.err.println("start " + testFiles[i]);
            // Get the global data from the files
            input = new TestReader("./../DBL-Algorithm/testfiles/" + testFiles[i]);
            data = input.read();
            // Use inputs to determine what algorithm to run
            grid = new Grid();
            AbstractAlgorithm algorithm = getAlgorithm();
            // The execute algorithm
            try{
                // Get current time before running Packing Solver
                Date d1 = new Date();
                // Run the algorithm
                algorithm.run();
                // Get current time after running Packing Solver
                Date d2 = new Date();
                // Compare the two so that we know runtime
                runtime[i] = (d2.getTime()-d1.getTime());
                
                // Then calculate the density
                grid.computeFinalDensity(data);
                double percentage = grid.getDensity();
                DecimalFormat df = new DecimalFormat("#.####");
                df.setRoundingMode(RoundingMode.CEILING);
                density[i] = df.format(percentage);
            }catch(Exception E){
                   System.out.println("File: " + testFiles[i] + " "
                           + "gave an error: " + E.toString());
            }
        }
        
        writeToFile();
    }
    
    // Get all the files from the testFiles
    public static String[] getFiles(){
        // Obtain all the file paths from the path folder
        List<String> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(PATH))) {
            paths.forEach((p) -> {
                files.add(p.toString());
            });
        }catch(IOException e){
            System.out.println("Getting files failed");
        }
        
        // Remove the empty path(s) from the arrayList
        boolean done = false;
        while(!done){
            done = true;
            String remove = "";
            for(String s: files){
                if(s.length() <= PATH.length()){
                    remove = s;
                    done = false;
                }
            }
            files.remove(remove);
        }
        
        // Obtain the file names from the Arraylist and add them into a String[]
        numberOfFiles = 0;
        String[] file = new String[files.size()];
        for(int i = 0; i < file.length; i++){
            numberOfFiles++;
            file[i] = (files.get(i).substring(PATH.length()));
        }
        return file;
    }
    
    // Returns a new algorithm based on what algorithm must be used
    public static AbstractAlgorithm getAlgorithm(){
        // Return the algorithm based on the string used
        switch(testingAlgorithm) {
        case "BestFit":
            return new BestFitAlgorithm(grid, data);
        case "BruteForce":
            return new BruteForceAlgorithm(grid, data);
        case "BruteForceLeftBottom":
            return new BruteForceLeftBottomAlgorithm(grid, data);
        case "LevelPacking":    
            return new LevelPackingAlgorithm(grid, data);
        case "Testing":
            return new TestingAlgorithm(grid, data);
        case "BigAlgorithm":
            return new BigAlgorithm(grid, data);
        }
        // If nothing is found
        System.err.println("Algorithm not found: " + testingAlgorithm);
        return new TestingAlgorithm(grid, data);
    }
    
    public static void writeToFile(){
        inputBuffer = new StringBuffer();
        System.out.println("write to: ");
        for(int i = 0; i < numberOfFiles; i++){
            String line = "Filename: " + testFiles[i] + "\n";
            inputBuffer.append(line);
            line = "Density: " + density[i] + "\n";
            inputBuffer.append(line);
            line = "Runtime: " + runtime[i] + "\n";
            inputBuffer.append(line);
        }
        System.err.println("You can find the resuls in: " + PATH + "result.txt");
        try{
            FileOutputStream fileOut = new FileOutputStream(PATH + "result.txt");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        }catch(IOException e){
            System.out.println("Writing buffer to test file went wrong");
        }
    }
}
