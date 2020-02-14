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
import java.util.Arrays;


/**
 *
 *  This class turns the current solution into output that is accepted by
 *  momoter
 * @author Stefan setup of class, implementation reader Leighton
 */
public class AbstractReader {
    
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
            
            int[] currentRectangle = {
                    Integer.parseInt(xCoordinate),
                    Integer.parseInt(yCoordinate)
            };
            rectangles[i-3] = currentRectangle;
        }
        
        System.out.println("Reader tests: ");
        System.out.println("containerType: " + containerType);
        System.out.println("containerHeight: " + containerHeight);
        System.out.println("rotationsAllowed: " + rotationsAllowed);
        System.out.println("numRectangles: " + numRectangles);
        System.out.println("rectangles: ");        
        for (int i = 0; i < numRectangles; i++) {
            System.out.println(Arrays.toString(rectangles[i]));
        }
        
        data = new GlobalData(containerType, containerHeight, rotationsAllowed,
                rectangles, numRectangles);
        return data;
    }
}
