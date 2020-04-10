
package tester;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author stefan
 * Creates a perfect packing file
 */
public class PerfectTestFileGenerator extends AbstractTestFileGenerator{
    int width;
    int height;
    int[][] rectangles;
    int splitsX;
    int splitsY;
    ArrayList<int[]> xCoord;
    ArrayList<int[]> yCoord;
    int SIZE = 100; // Gives an estimate of how big the rectangle will be
    
    public PerfectTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, 
                path, numberOfFiles);
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        // Add string Perfect to testfile name, so that we know what kind of 
        // Test file it is
        addToFileName("Perfect");
        // Determine the height and the width for the perfect packing
        initRectangle();
        // Determine how many splits we can "split up" the rectangle, based 
        // On how many rectangles there are.
        determineSplits();
        // Split the xCoordinates
        int currentNumberOfSplitsX = 1;
        while(currentNumberOfSplitsX < splitsX ){
            int[] toSplit = getLargest(xCoord, "x");
            int[][] newSplits = split(toSplit[0], toSplit[1]);
            xCoord.add(newSplits[0]);
            xCoord.add(newSplits[1]);
            currentNumberOfSplitsX++;
        }
        // Split the yCoordinates
        int currentNumberOfSplitsY = 1;
        while(currentNumberOfSplitsY < splitsY ){
            int[] toSplit = getLargest(yCoord, "y");
            int[][] newSplits = split(toSplit[0], toSplit[1]);
            yCoord.add(newSplits[0]);
            yCoord.add(newSplits[1]);
            currentNumberOfSplitsY++;
        }
        // Create the rectangles
        // First add them to an Arraylist such that we can shuffle it
        // Easier at the end
        ArrayList<ArrayList<Integer>> rectanglesTemp = new ArrayList();
        for(int[] y: yCoord){
            for(int[] x: xCoord){
                ArrayList<Integer> rectanglesTemp2 = new ArrayList();
                rectanglesTemp2.add(x[1] - x[0]);
                rectanglesTemp2.add(y[1] - y[0]);
                // If rotation is alowed, shuffle the x and y
                if(data.getRA()){
                    Collections.shuffle(rectanglesTemp2);
                }
                rectanglesTemp.add(rectanglesTemp2);
            }
        }
        Collections.shuffle(rectanglesTemp); 
        // Add the temp list to the rectangles, such that we can create the file
        int counter = 0;
        for(ArrayList<Integer> rectangle: rectanglesTemp){
            rectangles[counter][0] = rectangle.get(0);
            rectangles[counter][1] = rectangle.get(1);
            counter++;
        }
        return rectangles;
    }
    
    // Split a line in two lines
    public int[][] split(int p1, int p2){
        // Make sure the integers can be split
        if(Math.abs(p1-p2) < 2){
            return null;
        }
        int[][] result = new int[2][2];
        result[0][0] = p1;
        result[0][1] = p1 + (int) ((Math.abs(p1-p2) - 1) * Math.random()) + 1;
        result[1][0] = result[0][1];
        result[1][1] = p2;
        return result;
    }
    
    // Determine the height and the width for the perfect packing
    // Also init rectangles table and splits table
    public void initRectangle(){
        // Determine the height and the width for the perfect packing
        if(data.getType().equalsIgnoreCase(data.FIXED) && data.getHeight() > 0){
            height = data.getHeight();
            width = (int) (height * Math.random() + 0.5 * height) + 1;
        }else{
            height = (int) (SIZE * Math.random() + data.getNumRectangles());
            width = (int) (height * Math.random() + 0.5 * height);
        }
        rectangles = new int[data.getNumRectangles()][2];
        xCoord = new ArrayList();
        yCoord = new ArrayList();
        // Init the splits with one line
        int[] x = {0, width};
        int[] y = {0, height};
        xCoord.add(x);
        yCoord.add(y);
    }
    
    // Determine how many splits we can "split up" the rectangle, based 
    // On how many rectangles there are.
    public void determineSplits(){
        if(data.getNumRectangles() % 1000 == 0 && data.getNumRectangles() / 1000 != 1){
            splitsX = 1000;
            splitsY = data.getNumRectangles() / 1000;
        }else if(data.getNumRectangles() % 100 == 0 && data.getNumRectangles() / 100 != 1){
            splitsX = 100;
            splitsY = data.getNumRectangles() / 100;
        }else if(data.getNumRectangles() % 10 == 0 && data.getNumRectangles() / 10 != 1){
            splitsX = 10;
            splitsY = data.getNumRectangles() / 10;
        }else if(data.getNumRectangles() % 5 == 0){
            splitsX = 5;
            splitsY = data.getNumRectangles() / 5;
        }else if(data.getNumRectangles() % 4 == 0 && data.getNumRectangles() / 4 != 1){
            splitsX = 4;
            splitsY = data.getNumRectangles() / 4;
        }else if(data.getNumRectangles() % 3 == 0){
            splitsX = 3;
            splitsY = data.getNumRectangles() / 3;
        }else if(data.getNumRectangles() % 2 == 0){
            splitsX = 2;
            splitsY = data.getNumRectangles() / 2;
        }
    }

    // Retreive the largest line from the coordinates
    private int[] getLargest(ArrayList<int[]> Coord, String type) {
        int[] result = Coord.get(0);
        int value = 0;
        int index = 0;
        // Look for the largest line from the coordinates
        for(int i = 0; i < Coord.size(); i++){
            if(Coord.get(i)[1] - Coord.get(i)[0] > value){
                value = Coord.get(i)[1] - Coord.get(i)[0];
                result = Coord.get(i);
                index = i;
            }
        }
        // Remove the largest line from the coordinates
        if(type.equals("x")){
            xCoord.remove(index);
        }else{
            yCoord.remove(index);
        }
        return result;
    }
}
