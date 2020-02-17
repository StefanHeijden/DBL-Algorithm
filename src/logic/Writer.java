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
        // Write the input Data
        writeGlobalData();
        
        // Then write the placement of the rectangles
        int[][] placement = grid.getPlacement();
        System.out.println("placement of rectangles");
        // If it is without rotation
        if (!data.getRA()) {
            // Then place it simple x and y coordinates without yes / no
            for (int[] i: placement) {
                System.out.println(i[0] + " " + i[1]);
            }
        } else {  // If it is with rotation
            for (int i = 0; i < placement.length; i++) {
                // Then place it simple x and y coordinates with yes / no
                if (grid.getRotations()[i]) {
                    System.out.println("yes " + placement[i][0] + " " + 
                            placement[i][1]);
                } else {
                    System.out.println("no " + placement[i][0] + " " + 
                            placement[i][1]);
                }
            }
        }
    }
    
    public void writeGlobalData(){
        // Print First line
        System.out.print("container height: ");
        System.out.print(data.getType());
        if(data.getType() == null ? data.FIXED == null : data.getType().equals(data.FIXED)){
            System.out.print(" " + data.getHeight());
        }
        System.out.println();
        
        // Print Second Line
        System.out.print("rotations allowed: ");
        if(data.getRA()){
            System.out.println("yes");
        }else{
            System.out.println("no");
        }
        
        // Print Third line
        System.out.print("number of rectangles: ");
        System.out.println(data.getNumRectangles());
        
        // Print Rectangles Input
        int[][] rectangles = data.getRectangles();
        for (int[] i: rectangles) {
            System.out.println(i[0] + " " + i[1]);
        }
    }
}
