/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * Lets try to make a program that continates all required java files
 * removes the packages
 * and imports to those packages
 */
public class ContinateJaveFiles {
    // Path variables for Stefan:
    static String pathStefan = "C:/Users/stefa/Documents/";
    static String destinationStefan = "C:/Users/stefa/Downloads/";
    // Standard variables
    static String path2 = "DBL-Algorithm/src/";
    static String[] names = {"PackingSolver.java",
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
    static String[] packages = {"main/",
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
        String path = pathStefan + path2;
        // for each file
        for(int i = 0; i < names.length; i++){
            // Create a copy of the file in destinationPath
            CopyFile copyFile = new CopyFile(path + packages[i], names[i], destinationStefan);
            if(copyFile.succes) {
                // Then replace the package on import lines with comments
                replaceLines(destinationStefan + names[i]);
            }
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

        } catch (Exception e) {
            System.out.println("Problem reading file.");
        }
    }

}
