/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 *
 * Lets try to make a program that continates all required java files
 * removes the packages
 * and imports to those packages
 */
public class ContinateJaveFiles {
    static String path1 = "C:/Users/stefa/Documents/";
    static String path2 = "DBL-Algorithm/src/";
    static String destination = "C:/Users/stefa/Downloads";
    static String[] names = {"main/PackingSolver",
        //algorithms
        "algorithms/AbstractAlgorithm",
        "algorithms/SimpleAlgorithm",
        "algorithms/TestingAlgorithm",
        //logic
        "logic/AbstractReader",
        "logic/GlobalData",
        "logic/Grid",
        "logic/MomotorReader",
        "logic/TestReader",
        "logic/Writer"
    };
        
    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        String path = path1 + path2;
        File source = new File("src/resources/bugs.txt");
        File dest = new File(destination + "PackingSolver.java");

        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(dest)) {

            byte[] buffer = new byte[1024];
            int length;

            while ((length = fis.read(buffer)) > 0) {

                fos.write(buffer, 0, length);
            }
        }
        // for each file
        // find file
        // Remove package statement
        // Remove imports to package: logic, algorithms, main
        // if first then result = file
        // if not then result = result + file
    }
    
}
