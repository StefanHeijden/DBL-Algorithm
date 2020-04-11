package tester;

import java.util.ArrayList;

/**
 *
 * @author stefa
 */
public class CreateMultipleTestFiles {
    // Choose from: R(random), RB(random with bounds), S(sqaured),
    // P (perfect), AP (almost perfect), T (typed). for typed also specify types 
    // and sizes which is done in the main method before creating the files
    static String TestFile = "P";
    static int numberOfFiles = 3;
    static String containerType = "FREE";
    static boolean rotationsAllowed = false;
    static int numRectangles = 10;
    
    static final int MAXHEIGHT = 300;
    
    // Some other variables not to be changed:
    static ArrayList<String> types = new ArrayList();
    static ArrayList<String> sizes = new ArrayList();
    private static final String PATH = "./../DBL-Algorithm/testfiles/";
    /**
     * // This main method can be used for testing
     */
    public static void main(String[] args) {
        // FOR TYPED ONLY!!
        // Comment what you DONT need
        sizes.add("small");
        sizes.add("medium");
        sizes.add("big");
        types.add("wide");
        types.add("tall");
        types.add("squarish");
        
        
        for(int i = 0; i < numberOfFiles; i++){
            generateTestFile(i);
        }
    }
    
    
    static private void generateTestFile(int currentFile) {
        int containerHeight = -1;
        if(containerType.equalsIgnoreCase("FIXED")){
            containerHeight = (int) (Math.random() * MAXHEIGHT) + 10;
        }
        
        
        switch(TestFile) {
          case "R":
            new RandomTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
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
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
            break;
          case "AP":
            new AlmostPerfectTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile);
            break;
          case "T":
            new TypedTestFileGenerator(containerType, 
                    containerHeight, rotationsAllowed, numRectangles, PATH, currentFile,
                        types, sizes);
            break;
          default:
            // code block
        }
    }
}
