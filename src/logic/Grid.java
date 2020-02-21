package logic;

/**
 *
 * @author stefa
 */
public class Grid {
    
    private int[][] placement;
    private boolean[] rotations; 
    
    public int[][] getPlacement() {
        return placement;
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
