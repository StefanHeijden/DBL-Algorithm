/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *
 * Lets try to make a program that continates all required java files
 * removes the packages
 * and imports to those packages
 */
public class ContinateJaveFiles {
    // Path variables for Stefan:
    static final String PSTEFAN = "C:/Users/stefa/Documents/";
    static final String DSTEFAN = "C:/Users/stefa/Downloads/";
    // ADD YOURS HERE:
    // Path variables for Leighton
    
    // Path variables for Ezra:
    
    // Path variables for Jodi
    
    // Path variables for Yana
    
    
    
    // Specify which path and destination to use
    static final String PATH1 = PSTEFAN;
    static final String DESTINATION = DSTEFAN;
    // Standard variables
    static final String PATH2 = "DBL-Algorithm/src/";
    static final String PATH = PATH1 + PATH2;
    static final String[] NAMES = {"PackingSolver.java",
        //algorithms
        "AbstractAlgorithm.java",
        "SimpleAlgorithm.java",
        "TestingAlgorithm.java",
        //logic
        "AbstractReader.java",
        "GlobalData.java",
        "Grid.java",
        "MomotorReader.java",
        "TestReader.java",
        "Writer.java"
    };
    static final String[] PACKAGES = {"main/",
        //algorithms
        "algorithms/",
        "algorithms/",
        "algorithms/",
        //logic
        "logic/",
        "logic/",
        "logic/",
        "logic/",
        "logic/",
        "logic/"
        
    };
        
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // for each file
        for(int i = 0; i < NAMES.length; i++){
            // Create a copy of the file in destinationPath
            boolean succes = copyFile(PATH + PACKAGES[i], NAMES[i], DESTINATION);
            if(succes) {
                // Then replace the package on import lines with comments
                replaceLines(DESTINATION + NAMES[i]);
            }
        }
        appendFiles();
    }
    
    public static void appendFiles(){
            StringBuffer inputBuffer = new StringBuffer();
            String line;
            for(int i = 1; i < NAMES.length; i++){
            try {
                BufferedReader file = new BufferedReader(new FileReader(DESTINATION + NAMES[i]));
                while ((line = file.readLine()) != null) {
                    inputBuffer.append(line);
                    inputBuffer.append('\n');
                }
            // Done reading file 
            file.close();
            } catch (IOException e) {
                    System.out.println("Problem reading file for appending");
            }
        }
        try {
            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(DESTINATION + "Combined.java");
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();
        }catch(IOException e){
            System.out.println("Problem writing file for appending");
        }
    }
    
    // This method replaces certain package lines and import lines with comments
    public static void replaceLines(String source) {
        try {
            // input the (modified) file content to the StringBuffer "input"
            BufferedReader file = new BufferedReader(new FileReader(source));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            // Loop over each line in the file
            while ((line = file.readLine()) != null) {
                // If line has the be outcommented
                if(line.contains("package") || line.contains("import logic")
                        || line.contains("import algorithms")
                                || line.contains("import algorithms")){
                    // Then add comment lines 
                    line = "//" + line;
                }
                // Add the lines to the buffer
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            // Done reading file 
            file.close();

            // write the new string with the replaced line OVER the same file
            FileOutputStream fileOut = new FileOutputStream(source);
            fileOut.write(inputBuffer.toString().getBytes());
            fileOut.close();

        } catch (IOException e) {
            System.out.println("Problem reading file for line augmentation");
        }
    }

    // Create a copy of a certain file
    public static boolean copyFile(String path, String file, String targetPath)
    {	
    	
    	InputStream inStream = null;
	OutputStream outStream = null;
        
    	try{
            // Get files 
    	    File afile = new File(path + file);
    	    File bfile = new File(targetPath + file);
            
            // Create stream for the files
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
            
            // Create variables needed to copy file
    	    byte[] buffer = new byte[1024];
    	    int length;
            
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	    	outStream.write(buffer, 0, length);
    	    }
    	 
            // Close both streams
    	    inStream.close();
    	    outStream.close();
    	    
            // File is coppied succesfull
    	    return true;
    	}catch(IOException e){
            return false;
        }
    }
}
