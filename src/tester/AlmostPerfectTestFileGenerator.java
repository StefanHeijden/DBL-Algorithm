package tester;

/**
 *
 * @author stefa
 */
public class AlmostPerfectTestFileGenerator extends PerfectTestFileGenerator{
    
    public AlmostPerfectTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, path, numberOfFiles);
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        addToFileName("Almost");
        super.generateRectangles();
        // Give one rectangle an offset, which will most likely create a 
        // set of rectangles without perfect fit
        int offset = (int) (getWidth() / 5);
        if(offset < 1){
            offset = 1;
        }
        rectangles[0][0] = rectangles[0][0] + offset;
        return rectangles;
    }
}
