package logic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author stefa & leighton
 */
public class TestReader extends AbstractReader{
    
    public TestReader(String path) {
        super(path);
    }
    
    /** reads the number of lines the input file has and returns it
     * need to read the lines because array is faster implementation.
     */
    @Override
    public int readLines() {
        FileReader fileToRead = null;
        BufferedReader bf = null;
        try {
        fileToRead = new FileReader(path);
        bf = new BufferedReader(fileToRead);
        }  
        catch (FileNotFoundException e) {
            System.out.println("Reader.readLines - File not found: " + path);
        }
        
        String aLine;
        int numberOfLines = 0;
        try {
            while (( aLine = bf.readLine()) != null ) {
                numberOfLines++;
            }
            bf.close();
        }
        catch (IOException e) {
            System.out.println("IOException" + e.getMessage());
        }
        
        return numberOfLines;
    }
    
    @Override
    public String[] createArrayOfInput() {
        FileReader fr = null;
        BufferedReader textReader = null;
        try{
            fr = new FileReader(path);
            textReader = new BufferedReader(fr);
        }
        catch (FileNotFoundException e) {
            System.out.println("Reader.read - File not found: " + path);
        }
        
        int numberOfLines = readLines();
        String[] textData = new String[numberOfLines];
        
        try {
            for (int i = 0; i < numberOfLines; i++) {
                textData[i] = textReader.readLine();
            }
            textReader.close();
        }
        catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
        
        return textData;
    }
    
}
