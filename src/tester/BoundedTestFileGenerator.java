package tester;

import javax.swing.JOptionPane;

/**
 *
 * Creates file with random generated rectangles based with max of MAXIMUMSIZE
 * based on some GlobalData from GUI and the bounds asked for is 2 JPop-ups
 */
public class BoundedTestFileGenerator extends AbstractTestFileGenerator{
    
    public BoundedTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, path);
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        addToFileName("Bounded");
        int max;
        String input = JOptionPane.showInputDialog("Please input max: ");
        max = Integer.parseInt(input);
        if(max < 1){
            max = 1;
        }
        
        int min;
        input = JOptionPane.showInputDialog("Please input min: ");
        min = Integer.parseInt(input);
        if(min > max){
            min = max;
        }
        
        // Ini the rectangles array
        int[][] rectangles = new int[data.getNumRectangles()][2];
        int maxSizeX;
        // Make sure that rectangles aren't too big
        // Incase of fixed heigth
        if(data.getType().equalsIgnoreCase(data.FIXED) && 
                MAXIMUMSIZE > data.getHeight()){
            maxSizeX = data.getHeight();
        }else{
            // Or just because they then become to big to draw
            maxSizeX = MAXIMUMSIZE;
        }
        
        if(max > maxSizeX){
           max = maxSizeX;
        }
        // Then create the random rectangles
        for (int[] rectangle : rectangles) {
            rectangle[0] = (int) Math.round((max - min) * Math.random() + min);
            rectangle[1] = (int) Math.round((max - min) * Math.random() + min);
        }
        return rectangles;
    }
    
}

