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
public class Reader {
    
    private String containerType;
    private int containerHeight;
    private boolean rotationsAllowed;
    private int[][] rectangles;
    
    public Reader() {
        
    }
    
    public GlobalData read(){
        GlobalData data;
        // read input
        // and store it in data
        data = new GlobalData(containerType, containerHeight, rotationsAllowed
                                , rectangles);
        return data;
    }
}
