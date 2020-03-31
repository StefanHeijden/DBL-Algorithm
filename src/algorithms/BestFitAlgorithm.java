/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//TO DO:
//TESTING
//ADDING WHAT IT NEEDS TO DO IF THERE IS NO SOLUTION
//ADDING ROTATING OF RECTANGLES
//MAYBE ADDING EASIER WAY OR SORTING BOLDBLACKVERTICALLINES
//MAYBE ADDING EASIER WAY OF UPDATING SLOTS

package algorithms;

import logic.GlobalData;
import logic.Grid;
import java.util.ArrayList;
import java.util.Arrays;
//import java.util.Collections;

/**
 *
 * @author Jodi
 */
public class BestFitAlgorithm extends AbstractAlgorithm {
    int [][] rectangles; //with width and height
    int gridHeight;
    double optimalWidth;
           
    //determining the optimal width of the sheet
    double totalArea = 0;

    //array with the placement policies
    String[] policies;
        
        
    //arrays with the solutions
    ArrayList<ArrayList<ArrayList<Integer>>> placedRectanglesSolutions = new ArrayList<>();
    ArrayList<ArrayList<ArrayList<Integer>>> placesSolutions = new ArrayList<>();
    int widthSolution = 1000000000;
    int indexSolution = -1;
    int [][] finalPlaces;
        
    public BestFitAlgorithm(Grid grid, GlobalData data) {
        super(grid, data);
        //gets the data and gives every rectangle a number
        rectangles = new int[data.getRectangles().length][3];
        int i = 0;
        for(int[] r: data.getRectangles()){
            rectangles[i][0] = r[0];
            rectangles[i][1] = r[1];
            rectangles[i][2] = i;
            i++;
        }
        
        gridHeight = global.getHeight();
               
        //determining the optimal width of the sheet
        for (i=0; i<rectangles.length; i++){
            totalArea = totalArea + (rectangles[i][0] * rectangles[i][1]);
        }
        optimalWidth = totalArea/gridHeight;
        
        //array with the placement policies
        policies = new String[3];
        policies[0] = "upperLeftCorner";
        policies[1] = "fattestNeighboringPiece";
        policies[2] = "thinnestNeighboringPiece";
        
        finalPlaces = new int [rectangles.length][2];
    }
    
    //the coordinates if the rectangles is placed the upper left corner of a slot
    public int[] UpperLeftCorner (int[] slot, int[] rectangle){
        System.out.println(Arrays.toString(slot)+"slot in coor");
        //System.out.println(Arrays.toString(rectangle)+"rec in coor");
        int xSlot = slot[0];
        int ySlot = slot[1] + slot[2] - rectangle[1];
        int[] coordinates = {xSlot, ySlot};
        //System.out.println(Arrays.toString(coordinates)+"coor");
        return coordinates;
    }
    
    //takes the upper left corner of a rectangle when it is placed to the side
    //of the slot with the neighboring rectangle with the highest width
    public int[] FattestNeighboringPiece (int[] slot, ArrayList<ArrayList<Integer>> placedRectangles, ArrayList<ArrayList<Integer>> places, int[] rectangle){
        int xSlot = slot[0];
        int yLowerSlot = slot[1];
        int yUpperSlot = slot[1] + slot[2];
        int[] coordinates = new int[2];
      
   
        //index for the upper neighboring rectangle
        int u = -1;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the upper left corner of the slot
            if(places.get(i).get(0) == yUpperSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                u = i;
            }
        }
        
        //index for the lower neighboring rectangle
        int l = -1;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the lower left corner of the slot
            if((places.get(i).get(0) + placedRectangles.get(i).get(0)) == yLowerSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                l = i;
            }
        }
          
        if(l!=-1 && u!=-1){
            //if the width of the lower neighbor is bigger than the width of the upper neighbor
            if(placedRectangles.get(1).get(0) > placedRectangles.get(u).get(0)){
                //the rectangle has to be placed in the lower left corner of the slot
                coordinates[0] = xSlot;
                coordinates[1] = yLowerSlot;
            }
            //if the width of the lower neighbor is not bigger than the width of the upper neighbor
            else{
                //the rectangle has to be placed in the upper left corner of the slot
                coordinates[0] = xSlot;
                coordinates[1] = yUpperSlot - rectangle[1];
            }
        }
        else {
            //the rectangle has to be placed in the upper left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yUpperSlot - rectangle[1];
        }
        return coordinates;
    }
    
     //takes the upper left corner of a rectangle when it is placed to the side
    //of the slot with the neighboring rectangle with the highest width
    public int[] ThinnestNeighboringPiece (int[] slot, ArrayList<ArrayList<Integer>> placedRectangles, ArrayList<ArrayList<Integer>> places, int[] rectangle){
        int xSlot = slot[0];
        int yLowerSlot = slot[1];
        int yUpperSlot = slot[1] + slot[2];
        int[] coordinates = new int[2];
        
        //index for the upper neighboring rectangle
        int u = -1;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the upper left corner of the slot
            if(places.get(i).get(1) == yUpperSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                u = i;
            }
        }
        
        //index for the lower neighboring rectangle
        int l = -1;
        for(int i = 0; i < placedRectangles.size(); i++){
            //takes the neighboring rectangle on the lower left corner of the slot
            if((places.get(i).get(1) + placedRectangles.get(i).get(1)) == yLowerSlot && places.get(i).get(0) <= xSlot && xSlot < (places.get(i).get(0) + placedRectangles.get(i).get(0))){
                l = i;
            }
        }
        
        if(l!=-1 && u!=-1){
            //if the width of the lower neighbor is smaller than the width of the upper neighbor
            if(placedRectangles.get(1).get(0) < placedRectangles.get(u).get(0)){
                //the rectangle has to be placed in the lower left corner of the slot
                coordinates[0] = xSlot;
                coordinates[1] = yLowerSlot;
            }
            //if the width of the lower neighbor is not smaller than the width of the upper neighbor
            else{
                //the rectangle has to be placed in the upper left corner of the slot
                coordinates[0] = xSlot;
                coordinates[1] = yUpperSlot - rectangle[1];
            }
        }
        else {
            //the rectangle has to be placed in the upper left corner of the slot
            coordinates[0] = xSlot;
            coordinates[1] = yUpperSlot - rectangle[1];
        }
        return coordinates;
    }
    
    //adds an item to an two dimentional array
    public int[][] AddingToArray (int[][] array, int variables,  int[] item){
        
        int length = array.length;
        int[][] newArray = new int[length + 1][variables];
        
        for (int i = 0; i < length; i++){
            System.arraycopy(array[i], 0, newArray[i], 0, variables);
        }   

        newArray[length] = item.clone(); 

        return newArray;
    } 
    
    //adds an item to an three array
    public int[][][] AddingToArray (int[][][] array, int rectangles, int variables, int[][] item){
        int length = array.length;
        int[][][] newArray = new int[length + 1][][];
        
        for (int i = 0; i < length; i++){
            for (int j = 0; j < rectangles; j++){
                System.arraycopy(array[i][j], 0, newArray[i][j], 0, variables);
            }
        }   
  
        newArray[length] = item.clone(); 
        
        return newArray;
    } 
    
    //delete element from array
     public int[][] DeleteElement(int[][] array, int[] element){
        int[][] tempArray = array.clone();
        int[][] newArray = new int[tempArray.length-1][2];
        
        int indexToDelete = 0;
        
        for(int k = 0; k < tempArray.length-1; k++){
          if(tempArray[k] != element){
            indexToDelete = k;
          }
        }
        for(int indexBefore=0; indexBefore<indexToDelete; indexBefore++){
         newArray[indexBefore] = tempArray[indexBefore].clone();
        }
        for(int indexAfter=indexToDelete; indexAfter<tempArray.length-1; indexAfter++){
          newArray[indexAfter] = tempArray[indexAfter+1].clone();
        }
        return newArray;
    }
    
    @Override
    public void run() {

        
        for (String policy : policies) {
            //identify the slot at the beginning
            int[] beginSlot = {0, 0, gridHeight}; //with x, y of lower left corner and heigth
            ArrayList<ArrayList<Integer>> slots = new ArrayList<>();
            slots.add(new ArrayList<>());
            slots.get(slots.size()-1).addAll(Arrays.asList(Arrays.stream(beginSlot).boxed().toArray(Integer[]::new)));
            //identify the boldBlackVertcalLines at the beginning
            int [][] boldBlackVertcalLines = {{0, 0, gridHeight}}; //with x, lower y and higher y
           
            int[][] notPlacedRectangles = rectangles.clone(); //with width, height and number
            ArrayList<ArrayList<Integer>> placedRectangles = new ArrayList<>(); //with width and height
            ArrayList<ArrayList<Integer>> places = new ArrayList<>(); //with x and y of lower left corner
            while (notPlacedRectangles.length > 0) {
                //checks if there is a rectangle which can fit in a slot
                boolean canFit = false;
                for(int i=0; i<notPlacedRectangles.length; i++){
                    for(int j=0; j<slots.size(); j++){
                        if(rectangles[i][1] < slots.get(j).get(2)){
                            canFit = true;
                        } 
                        else {
                        
                        }
                    }
                }
                if (canFit){
                    //safe the highest score and the rectangle and place of the highest score
                    double highestScore = -1000000000;
                    int[] allocationSlot = new int[3]; //with x, y and height 
                    int[] allocationRectangle = new int[3]; //with width, height and number
                    int[] allocationPlace = new int[2]; //with width and height;
                    //check every allocation
                    for (int[] notPlacedRectangle : notPlacedRectangles) {
                        for (int j = 0; j<slots.size(); j++) {
                            //if rectangle fits in the slot;
                            if (notPlacedRectangle[1] < slots.get(j).get(2)) {
                                //possible values to compute the score with
                                double W = notPlacedRectangle[0]; //width of the piece
                                double H = notPlacedRectangle[1]; //height of the piece
                                //double A = W * H; //area of piece
                                double SW = slots.get(j).get(0); //sloth width, relative to base of sheet
                                double SHL = slots.get(j).get(2) - notPlacedRectangle[1]; //difference between slot and piece heights
                                //double GH = gridHeight; //height of grid;
                                double GW = optimalWidth * 1.5; //width of optimum solution multiplied by 1.5
                                //double ERC = ; //ephemeral random constant (to be determined if necessarily);
                                double score = (SHL / (GW - W)) - (SW + H);
                                //if the score is better, safe the score, rectangle and place
                                if (score > highestScore) {
                                    highestScore = score;
                                    allocationRectangle = notPlacedRectangle;
                                    //Arrays.asList(Arrays.stream(beginSlots).boxed().toArray(Integer[]::new)
                                    //allocationSlot = Arrays.asList(Arrays.stream(slots.get(j)).boxed().toArray(Integer[]::new);
                                    System.out.println(slots+"slots");
                                    for(int a=0; a<slots.get(j).size();a++){
                                        allocationSlot[a] = slots.get(j).get(a);
                                    }
                                }
                            }
                        }
                    }
                    System.out.println((Arrays.toString(allocationSlot)+"alloslot"));
                    if(null != policy) //computing the exact place of the allocation
                        switch (policy) {
                        case "upperLeftCorner":
                            allocationPlace = UpperLeftCorner(allocationSlot, allocationRectangle);
                            break;
                        case "fattestNeighboringPiece":
                            System.out.println("--------------START WITH FATTESTNEIGHBORINGPIECE-------------");
                            System.out.println(Arrays.toString(allocationSlot));
                            System.out.println((placedRectangles));
                            System.out.println((places));
                            System.out.println(Arrays.toString(allocationRectangle));
                            allocationPlace = FattestNeighboringPiece(allocationSlot, placedRectangles, places, allocationRectangle);
                            System.out.println(Arrays.toString(allocationPlace));
                            System.out.println("--------------END WITH FATTESTNEIGHBORINGPIECE-------------");
                            break;
                        case "thinnestNeighboringPiece":
                            System.out.println("--------------START WITH THINNESTNEIGHBORINGPIECE-------------");
                            System.out.println(Arrays.toString(allocationSlot));
                            System.out.println((placedRectangles));
                            System.out.println((places));
                            allocationPlace = ThinnestNeighboringPiece(allocationSlot, placedRectangles, places, allocationRectangle);
                            System.out.println(Arrays.toString(allocationPlace));
                            System.out.println("--------------END WITH THINNESTNEIGHBORINGPIECE-------------");
                            break;
                        default:
                            System.out.println("MIAUW");
                            break;
                        }
                    
                    //storing the location
                    places.add(new ArrayList<>());
                    places.get(places.size()-1).addAll(Arrays.asList(Arrays.stream(allocationPlace).boxed().toArray(Integer[]::new)));
                    //storing the rectangle
                    placedRectangles.add(new ArrayList<>());
                    placedRectangles.get(placedRectangles.size()-1).addAll(Arrays.asList(Arrays.stream(allocationRectangle).boxed().toArray(Integer[]::new)));
                    
                    
                    //deleting rectangle from notPlacedRectangles
                    int[][] tempNotPlacedRectangles = notPlacedRectangles.clone();
                    notPlacedRectangles = new int[tempNotPlacedRectangles.length-1][3];
                    for(int k = 0; k < tempNotPlacedRectangles.length; k++){
                        if(tempNotPlacedRectangles[k] == allocationRectangle){
                            // shifting elements
                            for(int j = k; j < tempNotPlacedRectangles.length - 1; j++){
                                notPlacedRectangles[j] = tempNotPlacedRectangles[j+1].clone();
                            }
                            for(int l = 0; l<k; l++){
                                notPlacedRectangles[l] = tempNotPlacedRectangles[l].clone();
                            }
                        }
                    }
                    
                    System.out.println("----------------START WITH UPDATING BOLD LINES---------------------");
                    System.out.println("Bold lines:");
                    for(int j=0; j<boldBlackVertcalLines.length;j++){
                        System.out.println(Arrays.toString(boldBlackVertcalLines[j]));
                    }
                    //updating boldBlackVertcalLines
                    int[] rightSideOfAllocationRectangle = {allocationPlace[0]+allocationRectangle[0], allocationPlace[1], allocationPlace[1] + allocationRectangle[1]}; //with x, lowest y and highest y
                    System.out.println("Right side new rectangle:");
                    System.out.println(Arrays.toString(rightSideOfAllocationRectangle));
                    for (int[] boldBlackVertcalLine : boldBlackVertcalLines) {
                        int lowestYBoldBlackVertcalLine = boldBlackVertcalLine[1];
                        int highestYBoldBlackVertcalLine = boldBlackVertcalLine[2];
                        //if boldBlackVerticalLines element is completely under rightSideOfAllocationRectangle
                        if (rightSideOfAllocationRectangle[2]>=highestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>=rightSideOfAllocationRectangle[1]) {
                            System.out.println("BOLD0");
                            //delete line from array
                            boldBlackVertcalLines = DeleteElement(boldBlackVertcalLines, boldBlackVertcalLine);
                        } else if (rightSideOfAllocationRectangle[2]>=highestYBoldBlackVertcalLine && highestYBoldBlackVertcalLine>rightSideOfAllocationRectangle[1]) {
                            System.out.println("BOLD1");
                            //only hold bottom part of line
                            boldBlackVertcalLine[2] = rightSideOfAllocationRectangle[1];
                        } else if (rightSideOfAllocationRectangle[2]>lowestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine>=rightSideOfAllocationRectangle[1]) {
                            System.out.println("BOLD2");
                            //only hold upper part of line
                            boldBlackVertcalLine[1] = rightSideOfAllocationRectangle[2];
                        } else if (rightSideOfAllocationRectangle[2]<highestYBoldBlackVertcalLine && lowestYBoldBlackVertcalLine<rightSideOfAllocationRectangle[1]) {
                            System.out.println("BOLD");
                            //add under part of line to array
                            boldBlackVertcalLines = AddingToArray(boldBlackVertcalLines, 3, boldBlackVertcalLine);
                            boldBlackVertcalLines[boldBlackVertcalLines.length-1][2] = rightSideOfAllocationRectangle[1];
                            //add upper part of line to array
                            boldBlackVertcalLines = AddingToArray(boldBlackVertcalLines, 3, boldBlackVertcalLine);
                            boldBlackVertcalLines[boldBlackVertcalLines.length-1][1] = rightSideOfAllocationRectangle[2];
                            //delete line from array
                            boldBlackVertcalLines = DeleteElement(boldBlackVertcalLines, boldBlackVertcalLine);
                        }
                        //System.out.println(Arrays.toString(boldBlackVertcalLines[i])+"bold");
                    }
                    
                    //adding right side of allocation rectangle to boldBlackVertcalLines
                    boldBlackVertcalLines = AddingToArray (boldBlackVertcalLines, 3,  rightSideOfAllocationRectangle);
                    //System.out.println(Arrays.toString(rightSideOfAllocationRectangle)+"rightside");
                    for(int j=0; j<boldBlackVertcalLines.length;j++){
                        System.out.println(Arrays.toString(boldBlackVertcalLines[j]));
                    }
                    System.out.println("----------------END WITH UPDATING BOLD LINES---------------------");
                    
                    //sorting boldBlackVertcalLines on first column in descending order
                    
                    //Maybe it can be done simpler?
                    //Collections.sort(boldBlackVertcalLines, Collections.reverseOrder());
                    
                    boolean needToBeSwapped;
                    do {
                        needToBeSwapped = false;
                        for (int i = 0; i < boldBlackVertcalLines.length - 1; i++) {
                            //check if needed to swap
                            if (boldBlackVertcalLines[i][0] < boldBlackVertcalLines[i + 1][0]) {
                                 
                                //swap
                                int[] temp = boldBlackVertcalLines[i].clone();
                                boldBlackVertcalLines[i] = boldBlackVertcalLines[i + 1].clone();
                                boldBlackVertcalLines[i + 1] = temp.clone();
                       
                                needToBeSwapped = true;
                            }
                        }
                    } while (needToBeSwapped);
                      
                    // Update slots based on de bold lines
                    System.out.println("-----------------------START WITH SLOTS-------------------------");
                    System.out.println("Old slots:");
                    System.out.println(slots);
                    int[][] dashyLines = updateDashedLines(boldBlackVertcalLines);
                    System.out.println("Resulting slots: ");
                    for(int[] line: dashyLines){
                        int[] newSlot = createSlot(line, boldBlackVertcalLines);
                        slots.add(new ArrayList<>());
                        slots.get(slots.size()-1).addAll(Arrays.asList(Arrays.stream(newSlot).boxed().toArray(Integer[]::new)));
                        System.out.println(Arrays.toString(newSlot));
                    }
                    System.out.println("New slots:");
                    System.out.println(slots);
                    System.out.println("-----------------------DONE WITH SLOTS-------------------------");
                }
                else{
                    //go further with the next policy
                    break;
                }
            }
            
            //add solution to solutions
            placedRectanglesSolutions.add(new ArrayList<>());
            placedRectanglesSolutions.get(placedRectanglesSolutions.size()-1).addAll(placedRectangles);
            
            placesSolutions.add(new ArrayList<>());
            placesSolutions.get(placesSolutions.size()-1).addAll(places);
        } 
        
        //System.out.println(placedRectanglesSolutions+"rect");
        //System.out.println(placesSolutions+"places");
        
        //deciding which solution is the best
        for(int i = 0; i < placedRectanglesSolutions.size(); i++){
            //keeps the width of the solution of the placing policy
            int widthPolicySolution = 0; 
            for(int j = 0; j < rectangles.length; j++){
            
                //the x value of the right side of the rectangle
                int highestXofRectangle = placedRectanglesSolutions.get(i).get(j).get(0) + placesSolutions.get(i).get(j).get(0);
                if(highestXofRectangle > widthPolicySolution){
                    widthPolicySolution = highestXofRectangle;
                }
            }
            if(widthPolicySolution < widthSolution){
                indexSolution = i;
            }
        }
        
        
        //the places of the solution converted to an array
        int[][] placesSolution = new int[placesSolutions.get(indexSolution).size()][2];
        for(int i=0; i<placesSolutions.get(indexSolution).size();i++){
            ArrayList<Integer> locationOutput = placesSolutions.get(indexSolution).get(i);
            for(int j=0; j<locationOutput.size();j++){
                placesSolution[i][j] = locationOutput.get(j); 
            }
        }
        
        //the rectangles of the solution converted to an array
        int[][] placedRectanglesSolution = new int[placedRectanglesSolutions.get(indexSolution).size()][3];
        for(int i=0; i<placedRectanglesSolutions.get(indexSolution).size();i++){
            ArrayList<Integer> rectangleOutput = placedRectanglesSolutions.get(indexSolution).get(i);
            for(int j=0; j<rectangleOutput.size();j++){
                placedRectanglesSolution[i][j] = rectangleOutput.get(j);
            }
        } 
        
        //sort the places so it matches the rectangles
        for(int i = 0; i < placedRectanglesSolution.length; i++){
            finalPlaces[placedRectanglesSolution[i][2]] = placesSolution[i].clone();
        }
        
        //puts the values in the gui
        grid.storePlacement(finalPlaces);
    } 
    
    // Create all the dashed line based on the boldlines
    public int[][] updateDashedLines(int[][] boldBlackVertcalLines){
        System.out.println("boldLines: ");
        for(int[] i: boldBlackVertcalLines){
            System.out.println(Arrays.toString(i));
        }
        ArrayList<int[]> dL = new ArrayList(); // The dashed lines
        
        ArrayList<Integer> alreadyChecked = new ArrayList();
        ArrayList<int[]> bL; // Keeps track of bLine that are used to draw the dashed  
        // Check for each bold black line
        for(int r = 0; r < boldBlackVertcalLines.length; r++){
            // First make sure this line wasn't already checked
            if(!alreadyChecked.contains(r)){
                // Then start with obtaining all black lines
                // that determine whether a dashedline can be drawn
                bL = new ArrayList();  
                // Start with adding itself, since that space cannot be used
                // for a dashedline
                int[] line = {boldBlackVertcalLines[r][1], boldBlackVertcalLines[r][2]};
                bL.add(line);
                int x = boldBlackVertcalLines[r][0];
                // then check each other bold black line
                for(int c = 0; c < boldBlackVertcalLines.length; c++){
                    // Make sure you dont check the same line
                    // and line 1 (r) is more to the left then line 2 (c)
                    if(r != c && boldBlackVertcalLines[r][0] <= boldBlackVertcalLines[c][0]){
                        // Then add the black line
                        int [] bline = {boldBlackVertcalLines[c][1], boldBlackVertcalLines[c][2]};
                        bL.add(bline);
                        // If he x is the same make sure it is not checked
                        if(boldBlackVertcalLines[r][0] == boldBlackVertcalLines[c][0]){
                            alreadyChecked.add(c);
                        }
                    }
                }
                // Finaly create dashedLines based on the black lines
                addDashLines(dL, bL, x);
            }
        }
        
        // Remove duplicates
        for(int r = 0; r < dL.size(); r ++){
            for(int c = r + 1; c < dL.size(); c ++){
                // if lower y and upper y are the same, one should be removed
                if(dL.get(r)[1] == dL.get(c)[1] && dL.get(r)[2] == dL.get(c)[2]){
                    dL.remove(c);
                }
            }
        }
        
        // Convert the dashlines to table of integers
        // since this is the format we use in other code
        int [][] result = new int[dL.size()][2]; 
        for(int i = 0; i < dL.size(); i++){
            result[i] = dL.get(i);
        }
        System.out.println("Resulting dashed lines: ");
        for(int[] i: dL){
            System.out.println(Arrays.toString(i));
        }
        return result;
    }

    // This method adds dashlines at the empty spots between the black lines
    private void addDashLines(ArrayList<int[]> dL, ArrayList<int[]> bL, int x) {
        // Order the bL, such that for each i < i + 1: bL.get(i)[1] <= bL.get(i + 1)[0]
        order(bL);
        
        // Check the first bL to make sure it starts at 0
        if(bL.get(0)[0] > 0){
            // If not add a dashedline there
            int[] line = {x, 0, bL.get(0)[0]};
            dL.add(line);
        }

        // then check for holes between the ordened lines
        for(int i = 0; i < bL.size() - 1; i++){
            // If there is a spot between 2 lines
            if(bL.get(i)[1] < bL.get(i+1)[0]){
                // add spots are added to dL as dashedLines
                int[] line = {0, bL.get(i)[1], bL.get(i+1)[0] - bL.get(i)[1]};
                dL.add(line);
            }
        }
        
        // Check the final bL to make sure it ends add gridHeight
        if(bL.get(bL.size() - 1)[1] < gridHeight){
            // If not add a dashedline there
            int[] line = {x, bL.get(bL.size() - 1)[1], gridHeight - bL.get(bL.size() - 1)[1]};
            dL.add(line);
        }
    }

    // Orders the list of bold lines such that they are in order from lowest to
    // highest line
    private ArrayList<int[]> order(ArrayList<int[]> bL) {
        ArrayList<int[]> result = new ArrayList();
        // For each bold line check all others that are still left
        for(int r = 0; r < bL.size(); r ++){
            for(int c = r; c < bL.size(); c ++){
                // If lines r upperY is smaller then c lowerY
                if(bL.get(c)[0] < bL.get(r)[0]){
                    // then switch those 2
                    int[] temp = {bL.get(c)[0], bL.get(c)[1]};
                    bL.get(c)[0] = bL.get(r)[0];
                    bL.get(c)[1] = bL.get(r)[1];
                    bL.get(r)[0] = temp[0];
                    bL.get(r)[1] = temp[1];
                }
            }
            
        }
        return result;
    }
    
    // Creates a slot based on the dashedline and all of the bold lines
    // It looks for the highest black line that is under the dashedline
    // such that you are left with a rectangle between the dashline and 
    // surrounding boldlines
    public int[] createSlot(int[] dashedLine, int[][] bL){
        int[] slot = new int[3];
        slot[1] = dashedLine[1];
        slot[2] = dashedLine[2];
        slot[0] = 0;
        for(int[] line: bL){
            // look for the highest black line that is under the dashedline
            
            if((line[0] > slot[0]) && (line[0] < dashedLine[0]) && 
                    (line[1] >= slot[1]) && (line[2] <= slot[1] + slot[2]) ){
                // Then save that height as the slots height
                slot[0] = line[0];
            }
        }
        
        return slot;
    }
}
