/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// Create a copy of a certain file
public class CopyFile 
{
    public boolean succes = false;
    
    // Create a copy of a certain file
    public CopyFile(String path, String file, String targetPath)
    {	
    	
    	InputStream inStream = null;
	OutputStream outStream = null;
        
    	try{
            // Get files 
    	    File afile = new File(path + file);
    	    File bfile = new File(targetPath + file);
            
            // Create stream for the files
    	    inStream = new FileInputStream(afile);
    	    outStream = new FileOutputStream(bfile);
            
            // Create variables needed to copy file
    	    byte[] buffer = new byte[1024];
    	    int length;
            
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
    	    	outStream.write(buffer, 0, length);
    	    }
    	 
            // Close both streams
    	    inStream.close();
    	    outStream.close();
    	    
            // File is coppied succesfull
    	    succes = true;
    	}catch(IOException e){
    		e.printStackTrace();
    	}
    }
}
