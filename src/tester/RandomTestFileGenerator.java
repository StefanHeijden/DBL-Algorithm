package tester;

/**
 *
 * @author stefa
 */
public class RandomTestFileGenerator extends AbstractTestFileGenerator{
    
    public RandomTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles);
    }
    
    @Override
    public int[][] generateRectangles(){
        int[][] rectangles = new int[data.getNumRectangles()][2];
        for (int[] rectangle : rectangles) {
            rectangle[0] = 1;
            rectangle[1] = 1;
        }
        System.out.println("rectangles length: " + rectangles.length);
        return rectangles;
    }
    
}
