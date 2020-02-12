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
        // output data
    }
}
