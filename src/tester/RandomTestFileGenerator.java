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
        int maxSizeX;
        if(data.getType().equalsIgnoreCase(data.FIXED) && MAXIMUMSIZE > data.getHeight()){
            maxSizeX = data.getHeight();
        }else{
            maxSizeX = MAXIMUMSIZE;
        }
        for (int[] rectangle : rectangles) {
            rectangle[0] = (int) Math.round((maxSizeX - 1) * Math.random() + 1);
            rectangle[1] = (int) Math.round((MAXIMUMSIZE - 1) * Math.random() + 1);
        }
        return rectangles;
    }
    
}
