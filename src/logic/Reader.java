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
    private int containerHeight = -1; //default of free variant
    private boolean rotationsAllowed;
    private int[][] rectangles;
    private int numRectangles;
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
            System.out.println("IOException" + e.getMessage());
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
            System.out.println("IOException: " + e.getMessage());
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
        
        if (textData[1].substring(18).equals("yes")) {
            rotationsAllowed = true;
        } else {
            rotationsAllowed = false;
        }
        
        numRectangles = Integer.parseInt(textData[2].substring(22));
        
        for (int i  = 3; i < textData.length; i++) { //loop through all rectan.
            String currentRectangleString = textData[i];
            int j = 0;
            String xCoordinate = "";
            while (! Character.toString(currentRectangleString.charAt(j)).equals(" ")) {
                xCoordinate += Character.toString(currentRectangleString.charAt(j));
                j++;
            }
            
            int[] currentRectangle = {
                    //Integer.parseInt(textData[i].substring(0, 2)),
                    //Integer.parseInt(textData[i].substring())
            };
            //rectangles[i] = 
        }
        
        System.out.println("Reader tests: ");
        System.out.println("containerType: " + containerType);
        System.out.println("containerHeight: " + containerHeight);
        System.out.println("rotationsAllowed: " + rotationsAllowed);
        System.out.println("numRectagnles: " + numRectangles);
        
        
        data = new GlobalData(containerType, containerHeight, rotationsAllowed,
                rectangles, numRectangles);
        return data;
    }
}
