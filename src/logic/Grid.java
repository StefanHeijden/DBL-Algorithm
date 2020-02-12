/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.List;

/**
 *
 * @author stefa
 */
public class Grid {
    
    private int[][] placement;
    private List rotations; 
    
    public int[][] getPlacement() {
        return placement;
    }
    
    public void storePlacement(int[][] placement) {
        this.placement = placement;
    }

    public List getRotations() {
        return rotations;
    }
    
    public void storeRotations(List rotations) {
        this.rotations = rotations;
    }
}
