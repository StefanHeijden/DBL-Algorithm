//import logic.*;
//import algorithms.*;
//import logic.GlobalData;
//import logic.Grid;
//import logic.GlobalData;
//import logic.Grid;
//import logic.GlobalData;
//import logic.Grid;
import java.util.Arrays;
//import logic.GlobalData;
//import logic.Grid;
import java.util.List;
import java.util.Scanner;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//package main;


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
    private static final String pathYana = "C:/Users/yana/Documents/"
            +"DBL-Algorithm/testcases/";
    private static final String pathJodi = "C:/Users/s165698/Documents/DBL Algorithms/";
    
    // Name of the file you want to test
    private static final String fileName = "0003_r6-h80-ry.in";
    //choose you path
    private static final String path = pathLeigthon;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // read inputs from file
        AbstractReader input = new MomotorReader(path + fileName);
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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package algorithms;



/**
 *
 * Update this class to have all general methods for each algorithm
 */
 abstract class AbstractAlgorithm {
    Grid grid;
    GlobalData global;
    
    public AbstractAlgorithm(Grid grid, GlobalData data){
        this.grid = grid;
        this.global = data;
    };
    
    public abstract void run();
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package algorithms;


/**
 *
 * @author stefa
 */
 class SimpleAlgorithm extends AbstractAlgorithm{

    public SimpleAlgorithm(Grid grid, GlobalData data) {
        super(grid, data); 
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package algorithms;

/**
 *
 * @author Ezra, and fixes leighton
 */
 class TestingAlgorithm extends AbstractAlgorithm{
    
    public TestingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data); 
    }
    
    
    private int x = 0; 
//    private int y = 0; //not used anymore
//    public int[] bottomleft = new int[2];
    
    //THIS FUNCTION IS NOT USED ANYMORE
    // computation of bottomleft corner in case (containerType == "free")
//    public int[] computeBottomleftFree(int width, int height) {
//        x += width; 
//        y += height;
//        int[] returnCoordinates = new int[2];
//        returnCoordinates[0] = x;
//        returnCoordinates[1] = y;
//        return returnCoordinates;
//    }
    
    // computation of bottomleft corner in case (containerType == "fixed")
    public int[] computeBottomleftFixed(int width) {
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = 0;
        x += width;
        return returnCoordinates;
    }
    
    @Override
    public void run() {
        
//        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        int[][] placement = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
        for (int i = 0; i < rectangle.length; i++) { 
            int rectWidth = rectangle[i][0]; 
            //int rectHeight = rectangle[i][1]; //not used anymore
            
            // Series of if-statements that compute bottomleft differently
            // Computation based on vars containerType and rotationsAllowed
            // Very ugly implementation, purely meant for simple testing
            if (global.getType().equals("free") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectWidth); 
            } else if (global.getType().equals("free") && 
                    global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectWidth);
                grid.setRotationsIndexI(false, i);
            } else if (global.getType().equals("fixed") && 
                    !global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectWidth);
            } else if (global.getType().equals("fixed") && 
                    global.getRA() ) {
                placement[i] = computeBottomleftFixed(rectWidth);
                grid.setRotationsIndexI(false, i);
            }
        }
        
        grid.storePlacement(placement);
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package algorithms;

/**
 *
 * @author yana
 * This class places rectangles in a given layout
 */
 class LevelPackingAlgorithm extends AbstractAlgorithm {

        
    private int x = 0; 
    private int y = 0; 
    
    // the total width of all rectangles divided by 2 or the Width of the 
    // longest rectangle.
    int sumRectWidth = 0;
    
    // counts the number of rectangles placed
    int counter = 0;
    
    // maximum height of already placed rectagnles
    int maxHeight = 0;
    
    public LevelPackingAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    // computation of bottomleft corner in case (containerType == "free")
    public int[] computeBottomleftFree(int width, int height) {
        if (height > maxHeight) { // computes maximum height
            maxHeight = height;
        }
        System.out.println("height " + width); 
        System.out.println("t " + sumRectWidth); 
        if (x + width >= sumRectWidth) { // if the width + free x is larger than
            x = 0;                       // limit, resets x and sets y
            y = maxHeight;
        }
        int[] returnCoordinates = new int[2];
        returnCoordinates[0] = x;
        returnCoordinates[1] = y;
        if (x >= sumRectWidth) { // x coordinate replaced if greater than limit
            x = 0;
            y = maxHeight;
        }
        else {
           x += width; 
        }
        return returnCoordinates;
    }
    
    @Override
    public void run() {
        
//        // getting the data from the logic package
        int[][] rectangle = global.getRectangles();
        int[][] placement = new int[global.getNumRectangles()][];
        grid.setRotationsLength(global.getNumRectangles());
        
        // sorted arrays of rectangles based on height and length
        int[][] sortedHRect = new int[global.getNumRectangles()][];
        int[][] sortedLRect = new int[global.getNumRectangles()][];
        
        int counter = 0;
        
        // copies the rectagnle into a new array 
        System.arraycopy(rectangle, 0, sortedHRect, 0, global.getNumRectangles());
        System.arraycopy(rectangle, 0, sortedLRect, 0, global.getNumRectangles());
        
        // sorts the array based on x (width)
        java.util.Arrays.sort(sortedHRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[0], a[0]);
            }
        });
        
        java.util.Arrays.sort(sortedLRect, new java.util.Comparator<int[]>() {
            public int compare(int[] a, int[] b) {
                return Integer.compare(b[1], a[1]);
            }
        });
        
        // print statements to ensure correctly sorts
        for (int i = 0; i < rectangle.length; i++){
            System.out.println("width H x " + sortedHRect[i][0]);
            System.out.println("width L x " + sortedLRect[i][0]);
            System.out.println("width   x " + rectangle[i][0]);
            System.out.println("width H y " + sortedHRect[i][1]);
            System.out.println("width L y " + sortedLRect[i][1]);
            System.out.println("width   y " + rectangle[i][1]);
        }
        
        // comutes the x limit
        for (int i = 0; i < rectangle.length; i++) { 
            int rectWidth = rectangle[i][0];
            sumRectWidth += rectWidth;    
        }
        sumRectWidth = sumRectWidth/2;
        
        // if there is a rectangle larger than the limit, then set it to be limit
        if (sortedHRect[0][0] > sumRectWidth) {
            sumRectWidth = sortedHRect[0][0];
            System.out.println(true);
        }

        // assign placement of rectangle
        for (int i = 0; i < sortedHRect.length; i++) { 
            int rectWidth = sortedHRect[i][0];
            int rectLength = sortedHRect[i][1];
            placement[i] = computeBottomleftFree(rectWidth, rectLength);
         }
        grid.storePlacement(placement);
    }
    
    
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package logic;

/**
 *
 *  This class turns the current solution into output that is accepted by
 *  momoter
 * @author Stefan setup of class, implementation reader Leighton
 */
 class AbstractReader {
    
    private String containerType;
    private int containerHeight = -1; //default of free variant
    private boolean rotationsAllowed;
    private int[][] rectangles;
    private int numRectangles;
    String path;
    
    public AbstractReader(String path) {
        this.path = path;
    }
    
    /** reads the number of lines the input file has and returns it
     * need to read the lines because array is faster implementation.
     */
    public int readLines() {
        System.out.println("DO NOT USE ABSTRACTREADER!!!!");
        
        return -1;
    }
    
    public String[] createArrayOfInput() {
        System.out.println("DO NOT USE ABSTRACTREADER!!!!");
        return new String[1];
    }
    
    
    /** reads input and returns it as GlobalData object */
    public GlobalData read() {
        GlobalData data;
        // read input
        String[] textData = createArrayOfInput();
        
        // store the input in data
        String containerHeightTypeString = textData[0].substring(18);
        if (containerHeightTypeString.equals("free")) {
            containerType = "free";
        } else {
            containerType = containerHeightTypeString.substring(0,5);
            try {
                containerHeight = Integer.parseInt(
                        containerHeightTypeString.substring(6));
            }
            catch (NumberFormatException e) {
                System.out.println("NumberFormatException: " + e.getMessage());
            }
        }
        
        rotationsAllowed = textData[1].substring(19).equals("yes");
        
        numRectangles = Integer.parseInt(textData[2].substring(22));
        
        rectangles = new int[numRectangles][2];
        
        for (int i  = 3; i < textData.length; i++) { //loop through all rectan.
            String currentRectangleString = textData[i];
            int j = 0;
            String xCoordinate = "";
            while (! Character.toString(currentRectangleString.charAt(j)).equals(" ")) {
                xCoordinate += Character.toString(currentRectangleString.charAt(j));
                j++;
            }
            String yCoordinate = currentRectangleString.substring(j+1);
            
//            if (! (yCoordinate instanceof String)) {
//                throw new NotAStringException("This is not a string: " + 
//                        yCoordinate);
//            }
            
            int[] currentRectangle = {
                    Integer.parseInt(xCoordinate),
                    Integer.parseInt(yCoordinate)
            };
            rectangles[i-3] = currentRectangle;
        }
//the following lines are for testing purposes:        
//        System.out.println("Reader tests: ");
//        System.out.println("containerType: " + containerType);
//        System.out.println("containerHeight: " + containerHeight);
//        System.out.println("rotationsAllowed: " + rotationsAllowed);
//        System.out.println("numRectangles: " + numRectangles);
//        System.out.println("rectangles: ");        
//        for (int i = 0; i < numRectangles; i++) {
//            System.out.println(Arrays.toString(rectangles[i]));
//        }
        
        data = new GlobalData(containerType, containerHeight, rotationsAllowed,
                rectangles, numRectangles);
        return data;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package logic;

/**
 *
 * Simple class that stores general data needed throughout
 */
 class GlobalData {
    private final String containerType;
    private final int containerHeight;
    private final boolean rotationsAllowed;
    private int[][] rectangles;
    private final int numRectangles;
    public final String FREE = "free";
    public final String FIXED = "fixed";

    public GlobalData(String containerType, int containerHeight, boolean rotationsAllowed, int[][] rectangles, int numRectangles) {
        this.containerType = containerType;
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
        this.numRectangles = numRectangles;
    }
    
    public String getType() {
        return containerType;
    }
    
    public int getHeight() {
        return containerHeight;
    }
    
    public boolean getRA() {
        return rotationsAllowed;
    }

    public int[][] getRectangles() {
        return rectangles;
    }

    public int getNumRectangles() {
        return numRectangles;
    }

    public void setRectangles(int[][] rectangles) {
        this.rectangles = rectangles;
    }
    
    public String[] dataToString(){
        //String containerType, int containerHeight, boolean rotationsAllowed, int[][] rectangles, int numRectangles
        // Add first Line
        String[] result = new String[3 + numRectangles];
        result[0] = "container height: " + containerType;
        if(containerType.equals(FIXED)){
            result[0] = result[0] + containerHeight;
        }
        result[0] = result[0] + '\n';
        
        // Add Second Line
        result[1] = "rotations allowed: ";
        if(rotationsAllowed){
            result[1] = result[1] + "yes";
        }else{
            result[1] = result[1] + "no";
        }
        result[1] = result[1] + '\n';
        
        // Add Third line
        result[2] = "number of rectangles: " + numRectangles + '\n';
        
        // Add Rectangles Input
        for(int i = 3; i < result.length; i++){
            result[i] = rectangles[i-3][0] + " " + rectangles[i-3][1] + '\n';
        }
        
        return result;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package logic;


/**
 *
 * @author stefa
 */
 class Grid {
    
    private int[][] placement;
    private boolean[] rotations; 
    
    public int[][] getPlacement() {
        return placement;
    }
    
    public void storePlacement(int[][] placement) {
        this.placement = placement;
    }

    public boolean[] getRotations() {
        return rotations;
    }
    
    public void setRotationsLength(int length) {
        this.rotations = new boolean[length];
    }
    
    public void setRotationsIndexI(boolean rotation, int i) {
        this.rotations[i] = rotation;
    }
    
    public void storeRotations(boolean[] rotations) {
        this.rotations = rotations;
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package logic;


/**
 *
 * @author leighton
 */
 class MomotorReader extends AbstractReader{
    
    public MomotorReader(String path) {
        super(path);
    }
    
    @Override
    public String[] createArrayOfInput() {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        String[] firstThreeLines = new String[3];
        while (count < 3) { //reading the first three lines, to create other part
            String line = "";
            if (sc.hasNextLine()) {
                line = sc.nextLine();
                count++;
            }
            firstThreeLines[count - 1] = line;
        }
        //counts number of rectangles
        int numRectangles = Integer.parseInt(firstThreeLines[2].substring(22));
        int ArraySize = numRectangles + 3; //because there are three lines in front of rectangles
        String[] textData = new String[ArraySize];//array to store input in
        for (int i = 0; i < 3; i++) {
            textData[i] = firstThreeLines[i]; //copy first three lines
        }
        
        int counter2 = 0;
        while (counter2 < numRectangles) { //read all lines with rectangles
            String line = "";
            if (sc.hasNextLine()) {
                line = sc.nextLine();

            }
            textData[counter2 + 3] = line; //store these lines in textData
            counter2++;
        }
        
        return textData;
    }
    
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//package logic;

/**
 * 
 *  This class turns the current solution into output that is accepted by
 *  momoter
 */
 class Writer {
    GlobalData data;
    Grid grid;
    
    public Writer(GlobalData data, Grid grid){
        this.data = data;
        this.grid = grid;
    }
    
    // output data
    public void writeOutput() {
        // Write the input Data
        writeGlobalData();
        
        // Then write the placement of the rectangles
        int[][] placement = grid.getPlacement();
        System.out.println("placement of rectangles");        
        
        // If it is without rotation
        if (!data.getRA()) {
            // Then place it simple x and y coordinates without yes / no
            for (int[] i: placement) {
                System.out.println(i[0] + " " + i[1]);
            }
        } else {  // If it is with rotation
            for (int i = 0; i < placement.length; i++) {
                // Then place it simple x and y coordinates with yes / no
                if (grid.getRotations()[i]) {
                    System.out.println("yes " + placement[i][0] + " " + 
                            placement[i][1]);
                } else {
                    System.out.println("no " + placement[i][0] + " " + 
                            placement[i][1]);
                }
            }
        }
    }
    
    public void writeGlobalData(){
        // Print First line
        System.out.print("container height: ");
        System.out.print(data.getType());
        if(data.getType() == null ? data.FIXED == null : data.getType().equals(data.FIXED)){
            System.out.print(" " + data.getHeight());
        }
        System.out.println();
        
        // Print Second Line
        System.out.print("rotations allowed: ");
        if(data.getRA()){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        
        // Print Third line
        System.out.print("number of rectangles: ");
        System.out.println(data.getNumRectangles());
        
        // Print Rectangles Input
        int[][] rectangles = data.getRectangles();
        for (int[] i: rectangles) {
            System.out.println(i[0] + " " + i[1]);
        }
    }
}
