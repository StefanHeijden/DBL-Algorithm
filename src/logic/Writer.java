package logic;

/**
 * 
 *  This class turns the current solution into output that is accepted by
 *  momoter
 */
public class Writer {
    GlobalData data;
    Grid grid;
    
    public Writer(GlobalData data, Grid grid){
        this.data = data;
        this.grid = grid;
    }
    
    // output data
    public void writeOutput() {
        // Write the input Data
        writeGlobalData();
        
        // Then write the placement of the rectangles
        int[][] placement = grid.getPlacement();
        System.out.println("placement of rectangles");        
        
        // If it is without rotation
        if (!data.getRA()) {
            // Then place it simple x and y coordinates without yes / no
            for (int[] i: placement) {
                System.out.println(i[0] + " " + i[1]);
            }
        } else {  // If it is with rotation
            for (int i = 0; i < placement.length; i++) {
                // Then place it simple x and y coordinates with yes / no
                if (grid.getRotations()[i]) {
                    System.out.println("yes " + placement[i][0] + " " + 
                            placement[i][1]);
                } else {
                    System.out.println("no " + placement[i][0] + " " + 
                            placement[i][1]);
                }
            }
        }
    }
    
    public void writeGlobalData(){
        //err stream is used to print to momotor output to debug
        //System.err.println("Start print Instance used, by momotor testing");
        
        // Print First line
        System.out.print("container height: ");
        //System.err.print("container height: ");// to show feedback in momotor
        System.out.print(data.getType());
        //System.err.print(data.getType());// to show feedback in momotor
        if(data.getType().equals(data.FIXED)){
            System.out.print(" " + data.getHeight());
            //System.err.print(" " + data.getHeight());//to show feedback in momotor
        }
        System.out.println();
        //System.err.println(); //to show feedback in momotor
        
        // Print Second Line
        System.out.print("rotations allowed: ");
        //System.err.print("rotations allowed: "); //to show feedback in momotor
        if(data.getRA()){
            System.out.println("yes");
           // System.err.println("yes");//to show feedback in momotor
        }else{
            System.out.println("no");
            //System.err.println("no");//to show feedback in momotor
        }
        
        // Print Third line
        System.out.print("number of rectangles: ");
        System.err.print("number of rectangles: ");//to show feedback in momotor
        System.out.println(data.getNumRectangles());
        System.err.println(data.getNumRectangles());//to show feedback in momotor
        
        // Print Rectangles Input
        int[][] rectangles = data.getRectangles();
        for (int i = 0; i < data.getNumRectangles(); i++) {
            System.err.println("Rectangle " + i + ": " + 
                    rectangles[i][0] + ", " + rectangles[i][1]); //for debugging
            //the above line shows the input instances on momotor
            System.out.println(rectangles[i][0] + " " + rectangles[i][1]);
        }
    }
}
