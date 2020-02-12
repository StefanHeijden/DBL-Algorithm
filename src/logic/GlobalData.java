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
    private final int[][] rectangles;

    GlobalData(String containerType, int containerHeight, boolean rotationsAllowed, int[][] rectangles) {
        this.containerType = containerType;
        this.containerHeight = containerHeight;
        this.rotationsAllowed = rotationsAllowed;
        this.rectangles = rectangles;
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
    
}
