package logic;

/**
 *
 * @author stefa
 */
public class Grid {
    
    private int[][] placement;
    private boolean[] rotations;
    private float density = -1;
    private GlobalData data;
    
    public int[][] getPlacement() {
        return placement;
    }
    
    // only use this if computeFinalDensity has been called, else returns -1     
    public float getDensity() {
        return density;
    }
    
    //computes density of final placement, only call if placement is full
    public void computeFinalDensity(String containerType, int containerHeight) {
        //first compute sum of rectangle area because is used in both variants
        int areaRectangles = 0; //used to store sum of area of all rectangles
        for (int[] rectangle: placement) { //loop through all rectangles
            int areaCurrentRectangle = rectangle[0] * rectangle[1];
            areaRectangles += areaCurrentRectangle;
        }
            
        // computation different for strip packing vs bounding box variants   
        if (containerType.equals(data.FREE)) { //if free
            //density = (\sum (area rectangles)) / ((\max xCoor) * (\max yCoor))
            
        } else if (containerType.equals(data.FIXED)) { //if fixed
            //density = (\sum (area rectangles)) / ((\max xCoor) * (containerHeight))
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
