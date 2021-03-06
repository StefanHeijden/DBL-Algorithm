package logic;

/**
 *
 * @author stefa
 */
public class Grid {
    
    private int[][] placement;
    private boolean[] rotations;
    private double density = -1; //a double because fractions can occur
    
    public int[][] getPlacement() { 
        return placement;
    }
    
    // only use this if computeFinalDensity has been called, else returns -1     
    public double getDensity() {
        return density;
    }
    
    //computes density of final placement, only call if placement is full
    //density is set to the percentage the bounding box or strip is filled
    public void computeFinalDensity(GlobalData data) {
        //first compute sum of rectangle area because is used in both variants
        double areaRectangles = 0; //used to store sum of area of all rectangles
        for (int[] rectangle : data.getRectangles()) { //loop through all rectangles
            double areaCurrentRectangle = rectangle[0] * rectangle[1];
            areaRectangles += areaCurrentRectangle;
        }
            
        // computation different for strip packing vs bounding box variants   
        if (data.getType().equals(data.FREE)) { //if free
            //density = (\sum (area rectangles)) / ((\max xCoor) * (\max yCoor))
            double maxXCoor = 0; //stores max x coordinate
            double maxYCoor = 0; //stores max y coordinate
            
            //this loop is used to compute maxXCoor and maxYCoor
            for (int i = 0; i < data.getNumRectangles(); i++) { //loop through all rectangles
                // currentMaxXCoor = current placement x + width of rectangle
                double currentMaxXCoor = placement[i][0] + data.getRectangles()[i][0];
                // currentMaxYCoor = current placement y + height of rectangle
                double currentMaxYCoor = placement[i][1] + data.getRectangles()[i][1];
                
                if (currentMaxXCoor > maxXCoor) { //if needed update maxXCoor
                    maxXCoor = currentMaxXCoor;
                } if (currentMaxYCoor > maxYCoor) { // if needed update maxYCoor
                    maxYCoor = currentMaxYCoor;
                }
            }
            
            density = 100 * (areaRectangles / (maxXCoor * maxYCoor)); //set density
                        
        } else if (data.getType().equals(data.FIXED)) { //if fixed
            //density = (\sum (area rectangles)) / ((\max xCoor) * (containerHeight))
            double maxXCoor = 0; //stores max x coordinate
            double maxYCoor = data.getHeight(); //stores max y coordinate
            
            for (int i = 0; i < data.getNumRectangles(); i++) {
                // currentMaxXCoor = current placement x + width of rectangle
                double currentMaxXCoor = placement[i][0] + data.getRectangles()[i][0];
                
                if (currentMaxXCoor > maxXCoor) { // if needed update maxXCoor
                    maxXCoor = currentMaxXCoor;
                }
            }
            
            density = 100 * (areaRectangles / (maxXCoor * maxYCoor)); //set density
            
        }
    }
    
    public void storePlacement(int[][] placement) {
        this.placement = placement;
    }

    public boolean[] getRotations() {
        return rotations;
    }
    
    public void setRotationsLength(int length) {
        this.rotations = new boolean[length];
    }
    
    public void setRotationsIndexI(boolean rotation, int i) {
        this.rotations[i] = rotation;
    }
    
    public void storeRotations(boolean[] rotations) {
        this.rotations = rotations;
    }
}
