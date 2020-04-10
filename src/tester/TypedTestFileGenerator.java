package tester;

import java.util.ArrayList;

/**
 *
 * @author stefa
 */
public class TypedTestFileGenerator extends AbstractTestFileGenerator{
    int[][] rectangles;
    ArrayList<String> types;
    
    public TypedTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, path, numberOfFiles);
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        rectangles = new int[data.getNumRectangles()][2];
        
        // Get types from input screen
        // TODO
        
        // Then create rectangles for each type chosen at random
        for(int i = 0; i < data.getNumRectangles(); i++){
            rectangles[i] = createRectangle();
        }
        return rectangles;
    }

    private int[] createRectangle() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
