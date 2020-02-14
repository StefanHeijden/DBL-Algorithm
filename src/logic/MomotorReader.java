/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author stefa
 */
public class MomotorReader extends AbstractReader{
    
    public MomotorReader(String path) {
        super(path);
    }
    
    /** reads the number of lines the input file has and returns it
     * need to read the lines because array is faster implementation.
     */
    @Override
    public int readLines() {
        
    }
    
    @Override
    public String[] createArrayOfInput() {
        
    }
    
}
