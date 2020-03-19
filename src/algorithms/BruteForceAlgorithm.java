
package algorithms;

import java.util.ArrayList;
import logic.GlobalData;
import logic.Grid;

/**
 *
 * @author Leighton
 * 
 * The purpose of this algorithm is to compute good and if possible perfect
 * packings for small instances. It tries out all different orderings of all 
 * rectangles in trying to fill each corner made by a combination of either 
 * rectangles or the boundaries. It computes the density for each packing
 * if the density is bigger than previous packing then it stores that packing
 * if the density is 100% (so a perfect packing) it immediately returns this
 * placement as a result. Else it will return highest packing within the time limit
 */
public class BruteForceAlgorithm extends AbstractAlgorithm{

    public BruteForceAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
    }
    
    double maxDensity = 0;//used by computations to check for best solution so far
    Grid bestPlacement; //stores best placement (during computation intermediate best placment)
    final int[] origin = {0, 0}; //coordinates of origin
    final int[][] originCorner = {origin}; //used as intialization for corners
    //used to initialize computations, of form [[[x0,y0], i0], [[x1,y1], i1]]
    //but with length of rectangles, i is for indexing the rectangle; x,y for width/height
    private int[][][] rectanglesWithIndex;
    
    //method to run the algorithm, it will choose which variant to run in another method
    @Override
    public void run() {
        //intialize rectanglesWithIndex, see representation above for more detail
        for (int i = 0; i < global.getNumRectangles(); i++) {
            // the [x, y] copying
            rectanglesWithIndex[i][0] = global.getRectangles()[i];
            // indexing the rectangle
            rectanglesWithIndex[i][1][0] = i;
        }
        
        // call the FNR variant 
        if (global.getType().equals(global.FREE) && ! global.getRA()) {
            //run with all rectangles, no current placment and origin as start corner
            computeFNR(rectanglesWithIndex, null, originCorner, null);
        } 
        //call the FR variant
        else if (global.getType().equals(global.FREE) && global.getRA()) {
            computeFR();
        }
        //call the SPNR variant
        else if (global.getType().equals(global.FIXED) && ! global.getRA()) {
            computeSPNR();
        }
        //call the SPR variant
        else if (global.getType().equals(global.FIXED) && global.getRA()) {
            computeSPR();
        }  
    }
    
    //TODO: adapt description of algorithm
    /* computes the Free Not Rotated variant and stores result, first parameter:
    rectangles that have to be placed on first call, on recursive calls rectangles
    that still have to be placed. Second parameter: for the placed rectangles this
    is the placement in which they are placed. Third parameter stores the corners
    for which it tries all permutations, initialzation for this variant should be
    with the origin (0, 0)**/
    public void computeFNR(int[][][] rectanglesToPlace, Grid semiFinalPlacement, 
            int[][] corners, int[][][] currentPlacement) {
        //if Rectangles to place has no more rectangles to place
        if (! (rectanglesToPlace.length > 0)) {
            // compute the density of this placement
            semiFinalPlacement.computeFinalDensity(global);
            double density = semiFinalPlacement.getDensity();
            // check if this is best solution so far
            if (density > maxDensity) {
                maxDensity = density; //store as current best density
                bestPlacement = semiFinalPlacement; //store as current best placement
            }
        }
        
        //pseudocode for the following section:
        //for each corner 
        //  for each permutation of Rectangles to Place
        //      if first rectangle can be placed
        //          place first rectangle
        //          and store in currentPlacement
        //          and update rectangles to place
        //          FNR(RectanglesToPlace, currentPlacement);
        //      else if rectangle cannot be placed at corner
        //          do not place it, do not continue recursive calls
        
        //loop over all corners
        for (int[] corner : corners) {
            //gets all possible permutations for rectangles that have not been placed
            int[][] permutations = computePermutations(rectanglesToPlace);
            for (int[] permutation : permutations) {
                //stores the [x,y] where rectangle i is placed
                currentPlacement[permutation[0]][0] = corner;
                //stores the i of rectangle i
                currentPlacement[permutation[0]][1][0] = permutation[0];
            }
        } 
    }
    
    /* computes the Free Rotations allowed variant and returns result **/
    private Grid computeFR() {
        Grid emptyGrid = new Grid();
        return emptyGrid;
    }
    
    /* computes the Strip Packing No rotations variant and returns result **/
    private Grid computeSPNR() {
        Grid emptyGrid = new Grid();
        return emptyGrid;
    }
    
    /*computes the Strip packing rotations allowed variant and returns result **/
    private Grid computeSPR() {
        Grid emptyGrid = new Grid();
        return emptyGrid;
    }
    
    /* computes and returns 2d array of corners that can be filled. Initialization
    should be with the origin: (0, 0). Computes new corners by placing new rectangle
    and adding width or height and have this as new corner. First parameter is
    current corners, second parameter is index of rectangle placed and last parameter
    is the corner the rectangle is placed at**/
    private int[][] computeCorners(int[][] oldCorners, int IndexPlacedRectangle, 
            int[] placedAtCorner) {
        //representation for new corners, one smaller because a corner has to be removed
        int[][] corners = new int[oldCorners.length - 1][];
        //get index of placedAtCorner in corners
        for (int i = 0; i < oldCorners.length; i++) { //loop over all corners
            //if corner is the same corner as placedAtCorner, note not duplicates
            if (oldCorners[i][0] == placedAtCorner[0] && oldCorners[i][1] == placedAtCorner[1]) {
                for(int j = 0; j < oldCorners.length - 1; j++) { //loop over all oldCorners except one
                    if (j < i) { //just copy if placedAtCorner not encountered yet
                        corners[j] = oldCorners[j];
                    } else if (j == i) { //so if placedAtCorner is encountered
                        corners[j] = oldCorners[j + 1]; //skip this value in oldCorners
                    } else { // if j > i
                        corners[j] = oldCorners[j + 1];// still need to account for skipping
                    }
                }
            }
        }
        
        //done removing placedAtCorner, start adding new corners
        //first need to get dimensions of rectangle that was placed
        int width = 0; //placed rectangle width
        int height = 0;//placed rectangle height
        
        for (int i = 0; i < rectanglesWithIndex.length; i++) {
            //if rectangle with index indexPlacedRectangle
            if (rectanglesWithIndex[i][1][0] == IndexPlacedRectangle) {
                width = rectanglesWithIndex[i][0][0];
                height = rectanglesWithIndex[i][0][1];
            }
        }
        
        //compute new corners
        int[][] newCorners = new int[corners.length + 2][]; //two corners will be added
        int[] newCorner1 = new int[2]; //first new corner
        int[] newCorner2 = new int[2]; //second new corner
        
        //first new corner is top left corner of newly placed rectangle
        newCorner1[0] = placedAtCorner[0];
        newCorner1[1] = placedAtCorner[1] + height;
        
        //second new corner is bottom right corner of newly placed rectangle
        newCorner2[0] = placedAtCorner[0] + width;
        newCorner2[1] = placedAtCorner[1];
        
        //copying corners array, so last two indexes of newCorners still empty
        System.arraycopy(corners, 0, newCorners, 0, corners.length); //newCorners except last 2
        
        //finally add both new corners
        newCorners[corners.length + 1] = newCorner1;
        newCorners[corners.length + 2] = newCorner2;
        
        return newCorners;
    }
    
    /* computes and returns all possbile permutations of given rectangles, so all
    different orderings. Result is all different permutations as an array. The 
    permutation itself is by the index of the rectangle. Gets computed in Permutations**/
    private int[][] computePermutations (int[][][] rectanglesToPlace) {   
        //need to know how many rectangles to permutate for rectangles still to place
        int numRectangles = rectanglesToPlace.length;
        //need to get all the indexes of the rectangles to be permutated
        int [] rectangleIndexes = new int[numRectangles];
        
        for (int i = 0; i < numRectangles; i++) { //loop over all rectangles to be placed
            //take out the index
            rectangleIndexes[i] = rectanglesToPlace[i][1][0];
        }
        
        Permutations computePermutations = new Permutations(); // make permutation object
        // compute permutations and store in permutations
        int[][] permutations = computePermutations.compute(numRectangles, rectangleIndexes);
        return permutations;
    }
    
    
}
