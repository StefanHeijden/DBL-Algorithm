/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import logic.GlobalData;

/**
 *
 * This is a simple runnable class that will create a file with inputs
 * of choise and random generated rectangles
 */
public class AbstractTestFileGenerator {
    private final String containerType;
    private final int containerHeight;
    private final boolean rotationsAllowed;
    private final int numRectangles;
    
    // Standard, mostly used in GUI to create random input files
    public AbstractTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles) {
        this.containerType = containerType;
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.numRectangles = numRectangles;
        generateFile();
    }
    
    // Can also be init from global data object
    public AbstractTestFileGenerator(GlobalData data){
        containerType = data.getType();
        containerHeight = data.getHeight();
        rotationsAllowed = data.getRA();
        numRectangles = data.getNumRectangles();
        generateFile();
    }
    
    // Generate a new file 
    public void generateFile(){
        String filename = containerType + containerHeight + rotationsAllowed + numRectangles;
        createFile(filename);
        findAndOpenFile(filename);
        System.out.print("TO DO: finish generating");
        // Write basic stuff
        // Generate rectangles
        // Write rectangles
    }
    
    // Generate the rectangles
    public void generateRectangles(){
        System.out.print("Extend this method for implementation");
    }
    
    
    // Create a new file with a certain name such that it can be writen in
    public void createFile(String filename){
        System.out.print("TO DO: create File");
    }
    
    // Find and open the file such that it can be writen in
    public void findAndOpenFile(String filename){
        System.out.print("TO DO: find and open File");
    }
    
    // This method writes a string to the file and places a next line at the end
    public void writeToFileWithNewline( String textLine ) throws IOException {
        FileWriter write = new FileWriter("" , false);
        PrintWriter print_line = new PrintWriter(write);
        
        print_line.printf( "%s" + "%n" , textLine);
    }
    
    // This method writes a string to the file and places a space at the end
    public void writeToFileWithoutNewlineAndWithSpace( String textLine ) throws IOException {
        FileWriter write = new FileWriter("" , false);
        PrintWriter print_line = new PrintWriter(write);
        
        print_line.printf( "%s" , textLine + " ");
    }
}
