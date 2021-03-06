
package algorithms;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.stream.IntStream;
import logic.GlobalData;
import logic.Grid;

/**
 * Last change: switched permutation[0] to permutation[lastIndex], dont know if correct -> need to test
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
    private int[][][] rectanglesWithIndex = new int[global.getNumRectangles()][2][2];
    private int[][][] emptyCurrentPlacement = new int[global.getNumRectangles()][2][2];
    private boolean[] rotated = new boolean[global.getNumRectangles()]; //index corresponds to indexing of rectangles
    private int[][][] rectanglesWithIndexRotations = new int[global.getNumRectangles()][2][2];

    //method to run the algorithm, it will choose which variant to run in another method
    @Override
    public void run() {
        //intialize rectanglesWithIndex, see representation above for more detail
        //and intialize indexes of emptyCurrentPlacement to -1 to indicate that index is not yet placed
        for (int i = 0; i < global.getNumRectangles(); i++) {
            // the [x, y] copying
            rectanglesWithIndex[i][0] = global.getRectangles()[i];
            rectanglesWithIndexRotations[i][0] = global.getRectangles()[i];
//            System.out.println("rectangle" + i + ": " + global.getRectangles()[i][0] + " " + global.getRectangles()[i][1]);
            // indexing the rectangle
            rectanglesWithIndex[i][1][0] = i;
            rectanglesWithIndexRotations[i][1][0] = i;
            //initialize index of empty currentPlacements to -1
            emptyCurrentPlacement[i][1][0] = -1;

//            System.out.println("rectanglesWithIndex:");
//            for (int j = 0; j < rectanglesWithIndex.length; j++) {
//                System.out.println(rectanglesWithIndex[j][0][0] + " " + rectanglesWithIndex[j][0][1] + " " + rectanglesWithIndex[j][1][0]);
//            }
        }

        // call the FNR variant
        if (global.getType().equals(global.FREE) && ! global.getRA()) {
            //run with all rectangles, no current placement and origin as start corner
            int[][] permutations = computePermutations(rectanglesWithIndex);
            for (int[] permutation : permutations) {
                computeFNR(rectanglesWithIndex, originCorner, emptyCurrentPlacement, permutation);
            }
        }
        //call the FR variant
        else if (global.getType().equals(global.FREE) && global.getRA()) {
            //withinBounds() and overlaps() needs to be adapted to allow for rotations
            //computeFNR(rectanglesWithIndex, originCorner, emptyCurrentPlacement);
        }
        //call the SPNR variant
        else if (global.getType().equals(global.FIXED) && ! global.getRA()) {
            //computeSPNR();
            //computeFNR(rectanglesWithIndex, originCorner, emptyCurrentPlacement);
        }
        //call the SPR variant
        else if (global.getType().equals(global.FIXED) && global.getRA()) {
            //withinBounds() and overlaps() needs to be adapted to allow for rotations
            for (int i = 0; i < global.getNumRectangles(); i++) { //try do rotations if needed
                if (rectanglesWithIndex[i][0][1] > global.getHeight()) {
                    rotated[i] = true;
                    int tempY = rectanglesWithIndexRotations[i][0][1];
                    rectanglesWithIndexRotations[i][0][1] = rectanglesWithIndexRotations[i][0][0]; //switch y for x
                    rectanglesWithIndexRotations[i][0][0] = tempY; // switch x for y
                }
            }

            //computeFNR(rectanglesWithIndexRotations, originCorner, emptyCurrentPlacement);
        }
        //store the best placement

        if (bestPlacement == null) {
            System.out.println("BestPlacement is null");
        }
        grid.storePlacement(bestPlacement.getPlacement());
    }
    
    //TODO: adapt description of algorithm
    /* computes the Free Not Rotated variant and stores result, first parameter:
    rectangles that have to be placed on first call, on recursive calls rectangles
    that still have to be placed. Second parameter: for the placed rectangles this
    is the placement in which they are placed. Third parameter stores the corners
    for which it tries all permutations, initialization for this variant should be
    with the origin (0, 0)
    currentPlacement is of form: [ [[x0,y0], i0], [[x1,y1], i0], [[x2,y2], i2] ]
    where x an y are the placement coordinates and i is the rectangle index**/
    public void computeFNR(int[][][] rectanglesToPlace, int[][] corners, int[][][] currentPlacement, int[] permutation) {
        //if Rectangles to place has no more rectangles to place, so done with placing
        if (!(rectanglesToPlace.length > 0)) {
//            System.out.println("end of recursive calls is reached");
            //store results as grid object
            Grid finalPlacement = new Grid();
            //create and store placement

//            for (int i = 0; i < currentPlacement.length; i++) {
//                System.out.println(currentPlacement[i][0][0] + " " + currentPlacement[i][0][1] + "  " + currentPlacement[i][1][0]);
//            }

            int[][] placement = computePlacement(currentPlacement);
            finalPlacement.storePlacement(placement);
            // compute the density of this placement
            finalPlacement.computeFinalDensity(global);
            double density = finalPlacement.getDensity();
            // check if this is best solution so far
            if (density > maxDensity) {
                maxDensity = density; //store as current best density
                bestPlacement = finalPlacement; //store as current best placement
                System.out.println("intermediate placement:");
                for (int i = 0; i < currentPlacement.length; i++) {
                    System.out.println(currentPlacement[i][0][0] + " " + currentPlacement[i][0][1] + "  " + currentPlacement[i][1][0]);
                }
            }
        }

//        System.out.println("corners:");
//        for (int i = 0; i < corners.length; i++) {
//            System.out.println(corners[i][0] + " " + corners[i][1]);
//        }
        if (rectanglesToPlace.length > 0) { //only run further computations if there are rectangles to place
            //loop over all corners
            for (int[] corner : corners) {
                //gets all possible permutations for rectangles that have not been placed
//                int[][] permutations = computePermutations(rectanglesToPlace);
//                System.out.println("permutations:");
//                for (int i = 0; i < permutations.length; i++) {
//                    for (int j = 0; j < permutations[0].length; j++) {
//                        System.out.print(permutations[i][j] + " ");
//                    }
//                    System.out.println();
//                }
//                for (int[] permutation : permutations) {
                    int lastIndex = permutation.length - 1;
                    //if first rectangle of the permutation can be placed=
//                    System.out.println("RTP:");
//                    for (int j = 0; j < rectanglesToPlace.length; j++) {
//                        System.out.println(rectanglesToPlace[j][0][0] + " " + rectanglesToPlace[j][0][1] + "  " + rectanglesToPlace[j][1][0]);
//                    }
                    if (validPlacement(permutation[lastIndex], corner, currentPlacement)) { //permutation go from back to front in placement order
                        //                    System.out.println("currentPlacement:");
                        //                    for (int i = 0; i < currentPlacement.length; i++) {
                        //                        System.out.println(currentPlacement[i][0][0] + " " + currentPlacement[i][0][1] + "  " + currentPlacement[i][1][0]);
                        //                    }
                        //                    System.out.println();

                        //create new current placement
                        //int[][][] newCurrentPlacement = currentPlacement.clone();
                        int[][][] newCurrentPlacement = new int[currentPlacement.length][2][2];
                        for (int i = 0; i < currentPlacement.length; i++) {
                            newCurrentPlacement[i][0][0] = currentPlacement[i][0][0];
                            newCurrentPlacement[i][0][1] = currentPlacement[i][0][1];
                            newCurrentPlacement[i][1][0] = currentPlacement[i][1][0];
                        }

                        //stores the [x,y] where rectangle i is placed
//                        System.out.println("current corner to be placed: " + corner[0] + " " + corner[1]);
                        newCurrentPlacement[permutation[lastIndex]][0] = corner;
                        //stores the i of rectangle i
                        newCurrentPlacement[permutation[lastIndex]][1][0] = permutation[lastIndex];
                        //update rectanglesToPlace by removing just placed rectangle
                        int[][][] updatedRTBP = removeRectangle(permutation[lastIndex], rectanglesToPlace);

//                        System.out.println("updatedRTBP:");
//                        for (int j = 0; j < updatedRTBP.length; j++) {
//                            System.out.println(updatedRTBP[j][0][0] + " " + updatedRTBP[j][0][1] + "  " + updatedRTBP[j][1][0]);
//                        }

                        //compute updated corners
                        int[][] newCorners = computeCorners(corners, permutation[lastIndex], corner);
                        //                    System.out.println("newCorners:");
                        //                    for (int i = 0; i < corners.length; i++) {
                        //                        System.out.println(corners[i][0] + " " + corners[i][1]);
                        //                    }
                        //recursive call with updated parameters
                        //                    System.out.println("recursive call FNR reached");

                        // remove the last index of the permutation and store in newPermutation
                        int[] newPermutation = new int[permutation.length-1];
//                        System.out.println("old permutation: ");
//                        for (int i = 0; i < permutation.length; i++) {
//                            System.out.print(permutation[i] + " ");
//                        }
                        System.arraycopy(permutation, 0, newPermutation, 0, permutation.length-1);

//                        System.out.println("new permutation: ");
//                        for (int i = 0; i < newPermutation.length; i++) {
//                            System.out.print(newPermutation[i] + " ");
//                        }

                        computeFNR(updatedRTBP, newCorners, newCurrentPlacement, newPermutation);
                    }
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
    
    /** The intent of this method is to convert currentPlacement to placement,
     since all different permutations the order of placements might have changed
     So this method basically reorders currentPlacement and removes the index*/
    private int[][] computePlacement(int[][][] currentPlacement) {
//        System.out.println("currentPlacement indexes:");
//        for (int i = 0; i < currentPlacement.length; i++) {
//            System.out.print(currentPlacement[i][1][0] + " ");
//        }
        //representation of the to be returned placement
        int[][] placement = new int[currentPlacement.length][2];
        //create array with all rectangleIndexes in the order of occurence in currentPlacement
        int[] rectangleOrder = new int[currentPlacement.length];
        for (int i = 0; i < currentPlacement.length; i++) { //loop to take out indexes
            rectangleOrder[i] = currentPlacement[i][1][0]; //the index
        }
        //search where placement coordinates in currentPlacement are
        for (int i = 0; i < currentPlacement.length; i++) {
            //assigns placement in correct order based on indexOrder
//            System.out.println("rectangleOrder:");
//            for (int j = 0; j < rectangleOrder.length; j++) {
//                System.out.print(rectangleOrder[j] + " ");
//            }
//            System.out.println("index:" + i);
//            System.out.println("test findIndex: " + findIndex(rectangleOrder, i));
            placement[i] = currentPlacement[findIndex(rectangleOrder, i)][0];
        }
        return placement;
    }
    
    /* take in int array and int element and outputs index of that element in the array **/
    private int findIndex(int[] array, int element) {
        int length = array.length;
        //returns the first result if it is there, otherwise -1
        return IntStream.range(0, length).filter(i -> element == array[i]).findFirst().orElse(-1);
    }
    
    /** this method will remove a rectangle from the rectanglesToBePlaced,
     * given the rectangle index as parameter and rectanglesToPlace, 
     * it returns the updated 3d array, without adapting the input */
    private int[][][] removeRectangle(int indexRectangleToRemove, int[][][] rectanglesToBePlaced) {
        //representation for updated rectanglesToBePlaced
        int[][][] updatedRTBP = new int[rectanglesToBePlaced.length - 1][][];
        for (int i = 0; i < rectanglesToBePlaced.length; i++) {
            //find where the rectangle is based on its index compute further with that stored in value i
            if (indexRectangleToRemove == rectanglesToBePlaced[i][1][0]) {
                //for loop to go copy values, note length is 1 shorter than previous loop
                for (int j = 0; j < updatedRTBP.length; j++) {
                    //case distinction based on whether index has been found
                    if (j < i) { //rectangle not encountered
                        updatedRTBP[j] = rectanglesToBePlaced[j];
                    } else if (j == i) { //rectangle encountered
                        updatedRTBP[j] = rectanglesToBePlaced[j + 1];
                    } else { // j > i, so past to be removed rectangle
                        updatedRTBP[j] = rectanglesToBePlaced[j + 1];
                    }
                }
            }
        }
//        System.out.println("UpdatedRTBP in removeRectangle(): ");
//        for (int i = 0; i < updatedRTBP.length; i++) {
//            System.out.println(updatedRTBP[i][0][0] + " " + updatedRTBP[i][0][1] + " " + updatedRTBP[i][1][0]);
//        }
        return updatedRTBP; //return the updated rectangleToBePlaced, input not adapted
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
        int width = getWidthRectangleIndex(IndexPlacedRectangle); //placed rectangle width
        int height = getHeightRectangleIndex(IndexPlacedRectangle);//placed rectangle height

//        System.out.println("width:" + width);
//        System.out.println("height:" + height);
        
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
        newCorners[corners.length] = newCorner1;
        newCorners[corners.length + 1] = newCorner2;
        
        return newCorners;
    }
    
    /* computes and returns all possbile permutations of given rectangles, so all
    different orderings. Result is all different permutations as an array. The 
    permutation itself is by the index of the rectangle. Gets computed in Permutations**/
    private int[][] computePermutations (int[][][] rectanglesToPlace) {   
        //need to know how many rectangles to permutate for rectangles still to place
        int numRectangles = rectanglesToPlace.length;
        //need to get all the indexes of the rectangles to be permutated
        int[] rectangleIndexes = new int[numRectangles];
        
        for (int i = 0; i < numRectangles; i++) { //loop over all rectangles to be placed
            //take out the index
            rectangleIndexes[i] = rectanglesToPlace[i][1][0];
        }
        
        Permutations computePermutations = new Permutations(); // make permutation object
        // compute permutations and store in permutations
        int[][] permutations = computePermutations.compute(numRectangles, rectangleIndexes);
        return permutations;
    }
    
    private boolean validPlacement(int toBePlacedRectangleIndex, int[] placementCoordinates, 
            int[][][] currentPlacement) {
        if (!overlaps(toBePlacedRectangleIndex, placementCoordinates, currentPlacement)) {
//            System.out.println("does not overlap");
        }

        if (withinBounds(toBePlacedRectangleIndex, placementCoordinates)) {
//            System.out.println("within bounds");
        }
        //overlaps() has to be false and withinBounds() has to be true for valid placement
        return (!overlaps(toBePlacedRectangleIndex, placementCoordinates, currentPlacement)
                && withinBounds(toBePlacedRectangleIndex, placementCoordinates));
    }
    
    //TODO needs to be adapted to allow for rotations
    private boolean overlaps(int toBePlacedRectangleIndex, int[] placementCoordinates, 
            int[][][] currentPlacement) {
        //idea is to use Rectangle class of java awt, first cast it to a Rectangle object
        if (! global.getRA()) { //if no rotations
            //Rectangles for which intersection computations have to be done
            ArrayList<Rectangle> rectanglesAWP = castRectangle(toBePlacedRectangleIndex, 
                    placementCoordinates, currentPlacement);

            System.out.println("overlap check");
            System.out.println("toBePlacedRectangleIndex: " + toBePlacedRectangleIndex);
            System.out.println("placementCoordinates: " + placementCoordinates[0] + " " + placementCoordinates[1]);
            System.out.println("currentPlacement:");
            for (int i = 0; i < currentPlacement.length; i++) {
                System.out.println(currentPlacement[i][0][0] + " " + currentPlacement[i][0][1] + "  " + currentPlacement[i][1][0]);
            }
            System.out.println("rectanglesAWP");
            for (Rectangle r : rectanglesAWP) {
                System.out.println("x:" + r.x + " y:" + r.y + " width:" + r.width + " height:" + r.height);
            }



            //compute whether anything intersects, basically same code as isLegal() 
            //from DrawPanel method is public but does not have parameters, 
            //therefore not used but basically duplicate, apart from handling result
            for (Rectangle r1 : rectanglesAWP) {
                for (Rectangle r2 : rectanglesAWP) {
                    //if  rectangles intersect and not the same implies overlap
                    if (r1 != r2) {
                        System.out.println("different rectangle");
                        if (r1.intersects(r2)) {
                            System.out.println("intersects");
                            return true;
                        }

                    }
                }
            }
            System.out.println("does not overlap");
            return false;
        } else {
            return true; //assume while not implemented placement is not valid
        }
    }
    
    //TODO needs to be adapted to allow for rotations
    /** this method computes and casts the current placement (+ rectangle to be placed)
     to ArrayList of rectangle objects and returns that*/
    private ArrayList<Rectangle> castRectangle(int toBePlacedRectangleIndex, int[] placementCoordinates, 
            int[][][] currentPlacement) {
        //ArrayList of rectangle objects, used to check intersections at castRectangle()
        ArrayList<Rectangle> currentPlacementRectanglesAWT = new ArrayList();
        
        // in following loop all currently placed rectangles are added
        for (int[][] rectangle : currentPlacement) { //each rectangle, form: [[x,y],[i]]
            if (rectangle[1][0] != -1) { //only for rectangles that have been actually placed
                //get required data for Rectangle class

                int rectWidth = getWidthRectangleIndex(rectangle[1][0]);
                int rectHeight = getHeightRectangleIndex(rectangle[1][0]);

//                System.out.println("rectWidth:" + rectWidth);
//                System.out.println("rectHeight: " + rectHeight);

                if (rectWidth != 0 && rectHeight != 0) { //dont cast an empty rectangle, since currentPlacement is initialized with zeros
                    //rectangle class uses placement from top left corner, so x = x, y += height
                    int xTopLeft = rectangle[0][0];
                    int yTopLeft = rectangle[0][1]; //+ rectHeight;

                    //create new rectangle object with above parameters as initialization
                    Rectangle newRectangle = new Rectangle(xTopLeft, yTopLeft, rectWidth, rectHeight);
                    currentPlacementRectanglesAWT.add(newRectangle);
                }
            }
        }
        
        //now still the to be placed rectangle has to be added, note that above
        //placement should always be valid since it has already been placed.
        int rectWidth = getWidthRectangleIndex(toBePlacedRectangleIndex);
        int rectHeight = getHeightRectangleIndex(toBePlacedRectangleIndex);
        
        //rectangle class uses placement from top left corner, so x = x, y += height
        int xTopLeft = placementCoordinates[0];
        int yTopLeft = placementCoordinates[1];// + rectHeight;
        
        //create the rectangle object with above values
        Rectangle toBePlacedRectangleCasted = new Rectangle(xTopLeft, yTopLeft, rectWidth, rectHeight);
        
        //add the toBePlacedRectangle
        currentPlacementRectanglesAWT.add(toBePlacedRectangleCasted);
        
        return currentPlacementRectanglesAWT;
    }
    
    //TODO needs to be adapted to allow for rotations
    private boolean withinBounds(int toBePlacedRectangleIndex, int[] placementCoordinates) {      
        //if placement corner is not in first quadrant or does not touch x- or y-axis  
        //and in first quadrant than some part not in first quadrant so not within bounds
        if (! (placementCoordinates[0] >= 0 && placementCoordinates[1] >= 0)) {
            return false;
        }
        
        //maximal y coordinate of given placed rectangle
        int maxYOfRectangle = placementCoordinates[1] + getHeightRectangleIndex(toBePlacedRectangleIndex);
        
        //if FNR variant
        if (global.getType().equals(global.FREE) && ! global.getRA()) {
            return true; //if it passed first test this should be fine
            //check still here because not everything implemented
        }
        
        //if SPNR variant
        if (global.getType().equals(global.FIXED) && ! global.getRA()) {
            //does not exceed height limit
            if (maxYOfRectangle < global.getHeight()) {
                return true;
            }
        }
        
        return false;// assume if variant not implemented that it is not valid
    }
    //TODO make quicker implementation of this by just finding it by index
    private int getWidthRectangleIndex(int index) {
        int width = 0; //placed rectangle width
        for (int i = 0; i < rectanglesWithIndex.length; i++) {
            //if rectangle with index indexPlacedRectangle
            if (rectanglesWithIndex[i][1][0] == index) {
                width = rectanglesWithIndex[i][0][0];
//                System.out.println("width in function:" + width);
            }
        }
        return width;
    }

    //TODO make quicker implementation of this by just finding it by index
    private int getHeightRectangleIndex(int index) {
        int height = 0; //placed rectangle height
        for (int i = 0; i < rectanglesWithIndex.length; i++) {
            //if rectangle with index indexPlacedRectangle
            if (rectanglesWithIndex[i][1][0] == index) {
                height = rectanglesWithIndex[i][0][1];
//                System.out.println("height in function" + height);
            }
        }
        return height;
    }
    
    
    
}
