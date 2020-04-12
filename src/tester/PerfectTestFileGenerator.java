
package tester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
    int SIZE = 200; // Gives an estimate of how big the rectangle will be
    boolean needsOneMoreRectangle;
    boolean[] done;
    
    public PerfectTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, 
                path, numberOfFiles);
        generateFile();
    }
    
    public PerfectTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles,
            int size) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, 
                path, numberOfFiles);
       this.SIZE = size * (int) Math.sqrt(numRectangles * 1.0);
       generateFile();
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        // Add string Perfect to testfile name, so that we know what kind of 
        // Test file it is
        addToFileName("Perfect-");
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
        int nor = data.getNumRectangles();
        if(data.getNumRectangles() == 5 || data.getNumRectangles() == 7){
            nor--;
        }
        int[][] rectanglesTemp1 = new int[nor][2];
        int counter = 0;
        for(int[] y: yCoord){
            for(int[] x: xCoord){
                rectanglesTemp1[counter][0] = x[1] - x[0];
                rectanglesTemp1[counter][1] = y[1] - y[0];
                counter++;
            }
        }
        int goon = 0;
        done = new boolean[nor];
        while(goon < (int) (nor / 2)){
            // pick random rectangle
            int index =  new Random().nextInt(rectanglesTemp1.length); 
            ArrayList<Integer> adjuctend = new ArrayList();
            // Determine left rectangle then vary the split between them
            
            if(!(index % xCoord.size() == 0) && !done[index] && !done[index - 1]){
                adjuctend.add(index - 1);
                int totalx = rectanglesTemp1[index][0] + rectanglesTemp1[index - 1][0];
                int totaly = rectanglesTemp1[index][1] + rectanglesTemp1[index - 1][1];
                // x
                rectanglesTemp1[index][0] = totalx;
                rectanglesTemp1[index - 1][0] = totalx;
                // y
                rectanglesTemp1[index][1] = (int) ((totaly - 2) * Math.random()) + 1;
                rectanglesTemp1[index - 1][1] = totaly - rectanglesTemp1[index][0];
                // Make sure these rectangles are only updated once
                done[index] = true;
                done[index - 1] = true;
            }
            /*
            // Determine right rectangle then vary the split between them
            if(!(index % xCoord.size() == xCoord.size() - 1)){
                adjuctend.add(index + 1);
                int total = rectanglesTemp1[index][0] + rectanglesTemp1[index + 1][0];
                rectanglesTemp1[index][0] = (int) ((total - 2) * Math.random()) + 1;
                rectanglesTemp1[index + 1][0] = total - rectanglesTemp1[index][0];
            }
            
            // Determine under rectangle then vary the split between them
            if(!(index < xCoord.size())){
                int total = rectanglesTemp1[index][1] + rectanglesTemp1[index - xCoord.size()][1];
                rectanglesTemp1[index][1] = (int) ((total - 2) * Math.random()) + 1;
                rectanglesTemp1[index - xCoord.size()][1] = total - rectanglesTemp1[index][1];
            }
            // Determine upper rectangle then vary the split between them
            if(!(index >= nor - xCoord.size())){
                int total = rectanglesTemp1[index][1] + rectanglesTemp1[index + xCoord.size()][1];
                rectanglesTemp1[index][1] = (int) ((total - 2) * Math.random()) + 1;
                rectanglesTemp1[index + xCoord.size()][1] = total - rectanglesTemp1[index][1];
            }
            */
            goon++;
        }
        
        // First add them to an Arraylist such that we can shuffle it
        // Easier at the end
        ArrayList<ArrayList<Integer>> rectanglesTemp2 = new ArrayList();
        for(int i = 0; i < nor; i++){
            ArrayList<Integer> rectanglesTemp3 = new ArrayList();
            rectanglesTemp3.add(rectanglesTemp1[i][0]);
            rectanglesTemp3.add(rectanglesTemp1[i][1]);
            // If rotation is alowed, shuffle the x and y
            if(data.getRA()){
                Collections.shuffle(rectanglesTemp3);
            }
            rectanglesTemp2.add(rectanglesTemp3);
        }
        if(needsOneMoreRectangle){
                ArrayList<Integer> rectanglesTemp3 = new ArrayList();
                int x = (int) (width / 5) + 1;
                rectanglesTemp3.add(x);
                rectanglesTemp3.add(height);
                // If rotation is alowed, shuffle the x and y
                if(data.getRA()){
                    Collections.shuffle(rectanglesTemp3);
                }
                rectanglesTemp2.add(rectanglesTemp3);
        }
        Collections.shuffle(rectanglesTemp2); 
        // Add the temp list to the rectangles, such that we can create the file
        counter = 0;
        for(ArrayList<Integer> rectangle: rectanglesTemp2){
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
        if(data.getNumRectangles() == 5 || data.getNumRectangles() == 7 ){
            splitsX = 2;
            splitsY = data.getNumRectangles() / 2;
            needsOneMoreRectangle = true;
        }else{
            needsOneMoreRectangle = false;
        }
        if(data.getNumRectangles() % 1000 == 0 && data.getNumRectangles() / 1000 != 1){
            splitsX = 1000;
            splitsY = data.getNumRectangles() / 1000;
        }else if(data.getNumRectangles() % 100 == 0 && data.getNumRectangles() / 100 != 1){
            splitsX = 100;
            splitsY = data.getNumRectangles() / 100;
        }else if(data.getNumRectangles() % 10 == 0 && data.getNumRectangles() / 10 != 1){
            splitsX = 10;
            splitsY = data.getNumRectangles() / 10;
        }else if(data.getNumRectangles() % 5 == 0 && data.getNumRectangles() / 4 != 1){
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
    
    public int getWidth(){
        return width;
    }
}
