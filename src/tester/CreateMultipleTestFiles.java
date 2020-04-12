package tester;

import java.util.ArrayList;

/**
 *
 * @author stefa
 */
public class CreateMultipleTestFiles {
    // ------------------------ IMPORTANT ----------------------------------
    // Choose from: R(random), RB(random with bounds), S(sqaured),
    // P (perfect), AP (almost perfect), T (typed). for typed also specify types 
    // and sizes which is done in the main method before creating the files
    static String TestFile = "P";
    static int numberOfFiles = 5;
    static String containerType = "free";
    static boolean rotationsAllowed = false;
    // Specify here what different total different number of rectangles you want
    // For example {4, 6, 10, 25, 10000} would be for this project standard
    static int[] AllnumRectangles = {4, 5, 6};
    // Specify maxHeight for a fixed type run, for each file a random height
    // from 10 - maxHeight will be created
    static final int MAXHEIGHT = 300;
    // Average size of the rectangles can be set here:
    // if set to -1, it creates a random size from 0 to MAXHEIGHT for each file
    static  int AverageSizeRectangles = 300;
    // ---------------------------------------------------------------------
    
    // Some other variables not to be changed:
    static ArrayList<String> types = new ArrayList();
    static ArrayList<String> sizes = new ArrayList();
    private static final String PATH = "./../DBL-Algorithm/testMultipleFiles/";
    /**
     * // This main method can be used for testing
     */
    public static void main(String[] args) {
        // ------------------------ IMPORTANT ----------------------------------
        // FOR TYPED ONLY!!
        // Comment what you DONT need
        sizes.add("small");
        sizes.add("medium");
        sizes.add("big");
        types.add("wide");
        types.add("tall");
        types.add("squarish");
        // ---------------------------------------------------------------------
        
        // Create multiple file for each different number of rectangles 
        for(int o = 0; o < AllnumRectangles.length; o++){
            for(int i = 0; i < numberOfFiles; i++){
                int index = i + numberOfFiles * o;
                generateTestFile(index, AllnumRectangles[o]);
            }
        }
    }
    
    
    static private void generateTestFile(int currentFile, int numRectangles) {
        int containerHeight = -1;
        if(containerType.equalsIgnoreCase("FIXED")){
            containerHeight = (int) (Math.random() * MAXHEIGHT) + 10;
            if(AverageSizeRectangles < 0){
                AverageSizeRectangles = (int) (Math.random() * 
                        (containerHeight - 1)) + 1;
            }
        }else{
            if(AverageSizeRectangles < 0){
                AverageSizeRectangles = (int) (Math.random() * MAXHEIGHT) + 1;
            }
        }
        
        switch(TestFile) {
          case "R":
            new RandomTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile, AverageSizeRectangles);
            break;
          case "RB":
            new BoundedTestFileGenerator(containerType, 
              containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
            break;
          case "S":
            new SquareTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
            break;
          case "P":
            new PerfectTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile, AverageSizeRectangles);
            break;
          case "AP":
            new AlmostPerfectTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
            break;
          case "T":
            new TypedTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile,
                        types, sizes, AverageSizeRectangles);
            break;
          default:
            // code block
        }
    }
}
