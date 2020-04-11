package tester;

/**
 *
 * Creates file with random generated rectangles based with max of MAXIMUMSIZE
 * based on some GlobalData from GUI
 */
public class RandomTestFileGenerator extends AbstractTestFileGenerator{
    int size;
    
    public RandomTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, 
                path, numberOfFiles);
        size = 0;
        generateFile();
    }
    
    public RandomTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles,
            int size) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, 
                path, numberOfFiles);
        System.out.println("size");
       this.size = size;
       generateFile();
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        addToFileName("Random-");
        // Ini the rectangles array
        int[][] rectangles = new int[data.getNumRectangles()][2];
        int maxSizeX;
        // Make sure that rectangles aren't too big
        // Incase of fixed heigth
        if(data.getType().equalsIgnoreCase(data.FIXED) && 
                MAXIMUMSIZE > data.getHeight()){
            maxSizeX = data.getHeight();
            if(size > 0 && size < data.getHeight()){
                maxSizeX = size;
            }
        }else{
            // Or just because they then become to big to draw
            maxSizeX = MAXIMUMSIZE;
            if(size > 0){
                maxSizeX = size;
            }
        }
        System.out.println(maxSizeX);
        // Then create the random rectangles
        for (int[] rectangle : rectangles) {
            rectangle[0] = (int) Math.round((maxSizeX - 1) * Math.random() + 1);
            rectangle[1] = (int) Math.round((MAXIMUMSIZE - 1) * Math.random() + 1);
        }
        return rectangles;
    }
    
}
