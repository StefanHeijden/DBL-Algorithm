package tester;

import java.io.FileOutputStream;
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
    public GlobalData data;
    private final String PATH = "C:/Users/stefa/Downloads/";
    StringBuffer inputBuffer;
    
    // Standard, mostly used in GUI to create random input files
    public AbstractTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles) {
        int[][] simple = {{1, 1}};
        data = new GlobalData(containerType, containerHeight, rotationsAllowed, 
                simple, numRectangles);
        generateFile();
    }
    
    // Can also be init from global data object
    public AbstractTestFileGenerator(GlobalData data){
        int[][] simple = {{1, 1}};
        this.data = new GlobalData(data.getType(), data.getHeight(), 
                data.getRA(), simple, data.getNumRectangles());
        generateFile();
    }
    
    // Generate a new file 
    public void generateFile(){
        // Create new filename
        String filename = data.getType() + data.getHeight() + data.getRA() + 
                data.getNumRectangles() + ".java";
        System.out.print(filename);
        
        // Generate rectangles
        int[][] rectangles = generateRectangles();
        
        // Replace rectanges in Global data for easy printing
        data.setRectangles(rectangles);
        
        // Write global data into inputbuffer
        writeGlobalData();
        
        // Wite input buffer into the file and then close file
        try{
            FileOutputStream fileOut = new FileOutputStream(PATH + filename);
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
    public void writeToFileWithoutNewlineAndWithSpace( String textLine ) throws IOException {
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
}
