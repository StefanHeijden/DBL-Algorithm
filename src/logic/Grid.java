/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

//import java.util.List;

/**
 *
 * @author stefa
 */
public class Grid {
    
    private int[][] placement;
    private boolean[] rotations; 
    
    public int[][] getPlacement() {
        return placement;
    }
    
    public void storePlacement(int[][] placement) {
        this.placement = placement;
    }

    public boolean[] getRotations() {
        return rotations;
    }
    
    public void setRotationsIndexI(boolean rotation, int i) {
        this.rotations[i] = rotation;
    }
    
    public void storeRotations(boolean[] rotations) {
        this.rotations = rotations;
    }
}
