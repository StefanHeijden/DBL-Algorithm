/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 *
 * Simple class that stores general data needed throughout
 */
public class GlobalData {
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
        System.out.println("length: " + result.length);
        for(int i = 3; i < result.length; i++){
            System.out.println("i: " + i);
            result[i] = rectangles[i-3][0] + " " + rectangles[i-3][1] + '\n';
        }
        
        return result;
    }
    
}
