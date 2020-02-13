/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;


/**
 *
 *  This class turns the current solution into output that is accepted by
 *  momoter
 * @author Stefan setup of class, implementation reader Leighton
 */
public class Reader {
    
    private String containerType;
    private int containerHeight;
    private boolean rotationsAllowed;
    private int[][] rectangles;
    private String path;
    
    public Reader(String path) {
        this.path = path;
    }
    
    /** reads the number of lines the input file has and returns it
     * need to read the lines because array is faster implementation.
     */
    public int readLines() {
        FileReader fileToRead = null;
        BufferedReader bf = null;
        try {
        fileToRead = new FileReader(path);
        bf = new BufferedReader(fileToRead);
        }  
        catch (FileNotFoundException e) {
            System.out.println("Reader.readLines - File not found: " + path);
        }
        
        String aLine;
        int numberOfLines = 0;
        try {
            while (( aLine = bf.readLine()) != null ) {
                numberOfLines++;
            }
            bf.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
 
        return numberOfLines;
    }
    
    private String[] createArrayOfInput() {
        FileReader fr = null;
        BufferedReader textReader = null;
        try{
            fr = new FileReader(path);
            textReader = new BufferedReader(fr);
        }
        catch (FileNotFoundException e) {
            System.out.println("Reader.read - File not found: " + path);
        }
        
        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];
        
        try {
            for (int i = 0; i < numberOfLines; i++) {
                textData[i] = textReader.readLine();
            }
            textReader.close();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        
        System.out.println("testing reader");
        for (int i = 0; i < textData.length; i++){
            System.out.println(textData[i]);
        }
        
        return textData;
    }
    
    /** reads input and returns it as GlobalData object */
    public GlobalData read() {
        GlobalData data;
        // read input
        String[] textData = createArrayOfInput();
        // store the input in data
        
        
        data = new GlobalData(containerType, containerHeight, rotationsAllowed
                                , rectangles);
        return data;
    }
}
