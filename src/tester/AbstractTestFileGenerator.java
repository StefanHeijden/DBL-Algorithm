package tester;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import logic.GlobalData;

/**
 *
 * This is a simple runnable class that will create a file with inputs
 * of choice and random generated rectangles
 */
public class AbstractTestFileGenerator {
    public GlobalData data;
    public int MAXIMUMSIZE = 10000;
    private final String PATH;
    StringBuffer inputBuffer;
    private String fileName;
    int numberOfFiles;
    
    // Standard, mostly used in GUI to create random input files
    public AbstractTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        int[][] simple = {{1, 1}};
        data = new GlobalData(containerType, containerHeight, rotationsAllowed, 
                simple, numRectangles);
        PATH = path;
        this.numberOfFiles = numberOfFiles;
    }
    
    // Can also be init from global data object
    public AbstractTestFileGenerator(GlobalData data, String path){
        int[][] simple = {{1, 1}};
        this.data = new GlobalData(data.getType(), data.getHeight(), 
                data.getRA(), simple, data.getNumRectangles());
        PATH = path;
    }
    
    // Generate a new file 
    public void generateFile(){
        // Create new filename
        fileName = "";
        if(data.getType().equalsIgnoreCase(data.FREE)){
            fileName = fileName + "r" + data.getNumRectangles() + "-hf-r";
        }else{
            fileName = fileName + "r" + data.getNumRectangles() + "-h" + data.getHeight() + "-r";
        }
        if(data.getRA()){
            fileName = fileName + "y.in";
        }else{
            fileName = fileName + "n.in";
        }
        
        // Generate rectangles
        int[][] rectangles = generateRectangles();
        
        // Replace rectanges in Global data for easy printing
        data.setRectangles(rectangles);
        
        // Write global data into inputbuffer
        writeGlobalData();
        
        // Wite input buffer into the file and then close file
        String zeros = "";
        if( numberOfFiles < 1000){
            zeros = zeros + "0";
            if( numberOfFiles < 100){
                zeros = zeros + "0";
                if(numberOfFiles < 10){
                    zeros = zeros + "0";
                }
                
            }
        }
        fileName = zeros + numberOfFiles +  "_" + fileName;
        try{
            FileOutputStream fileOut = new FileOutputStream(PATH + fileName);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        }catch(IOException e){
            System.out.println("Writing buffer to test file went wrong");
        }
    }
    
    // Generate the rectangles
    public int[][] generateRectangles(){
        System.out.print("Extend this method for implementation");
        int[][] rectangles = new int[data.getHeight()][2];
        for (int[] rectangle : rectangles) {
            rectangle[0] = 1;
            rectangle[1] = 1;
        }
        return rectangles;
    }
    
    
    // This method writes a string to the file and places a next line at the end
    public void writeToFileWithNewline( String textLine ) throws IOException {
        FileWriter write = new FileWriter("" , false);
        PrintWriter print_line = new PrintWriter(write);
        
        print_line.printf( "%s" + "%n" , textLine);
    }
    
    // This method writes a string to the file and places a space at the end
    public void writeToFileWithoutNewlineAndWithSpace( String textLine ) 
            throws IOException {
        FileWriter write = new FileWriter("" , false);
        PrintWriter print_line = new PrintWriter(write);
        
        print_line.printf( "%s" , textLine + " ");
    }
    
    public void writeGlobalData(){
        inputBuffer = new StringBuffer();
        String[] lines = data.dataToString();
        for(String line : lines){
            inputBuffer.append(line);
        }
    }
    
    public void addToFileName(String ext){
        fileName = ext + fileName;
    }

    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String name) {
        fileName = name;
    }
}
