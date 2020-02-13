/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

/**
 * 
 *  This class turns the current solution into output that is accepted by
 *  momoter
 */
public class Writer {
    GlobalData data;
    Grid grid;
    
    public Writer(GlobalData data, Grid grid){
        this.data = data;
        this.grid = grid;
    }
    
    // output data
    public void writeOutput() {
        
        int[][] placement = grid.getPlacement();
        System.out.println("placement of rectangles: ");
        if (!data.getRA()) {
            for (int[] i: placement) {
                System.out.println(i[0] + " " + i[1]);
            }
        } else {
            for (int i = 0; i < placement.length; i++) {
                if (grid.getRotations()[i]) {
                    System.out.println("yes" + placement[i][0] + " " + 
                            placement[i][1]);
                } else {
                    System.out.println("no" + placement[i][0] + " " + 
                            placement[i][1]);
                }
            }
        }
    }
}
