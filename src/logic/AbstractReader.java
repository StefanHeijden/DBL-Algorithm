package logic;

/**
 *
 *  This class turns the current solution into output that is accepted by
 *  momoter
 * @author Stefan setup of class, implementation reader Leighton & Stefan
 */
public class AbstractReader {
    
    private String containerType;
    private int containerHeight = -1; //default of free variant
    private boolean rotationsAllowed;
    private int[][] rectangles;
    private int numRectangles;
    String path;
    
    public AbstractReader(String path) {
        this.path = path;
    }
    
    /** reads the number of lines the input file has and returns it
     * need to read the lines because array is faster implementation.
     */
    public int readLines() {
        System.out.println("DO NOT USE ABSTRACTREADER!!!!");
        
        return -1;
    }
    
    public String[] createArrayOfInput() {
        System.out.println("DO NOT USE ABSTRACTREADER!!!!");
        return new String[1];
    }
    
    
    /** reads input and returns it as GlobalData object */
    public GlobalData read() {
        GlobalData data;
        // read input
        String[] textData = createArrayOfInput();
        
        // store the input in data
        String containerHeightTypeString = textData[0].substring(18);
        if (containerHeightTypeString.equals("free")) {
            containerType = "free";
        } else {
            containerType = containerHeightTypeString.substring(0,5); //"fixed"
            try {
                containerHeight = Integer.parseInt(
                        containerHeightTypeString.substring(6));
            }
            catch (NumberFormatException e) {
                System.out.println("NumberFormatException: " + e.getMessage());
            }
        }
        
        //if rotations yes set to true otherwise to false
        rotationsAllowed = textData[1].substring(19).equals("yes");
        
        numRectangles = Integer.parseInt(textData[2].substring(22));
        
        rectangles = new int[numRectangles][2]; //used to store rectangles
         
        for (int i  = 3; i < textData.length; i++) { //loop through all rectan.
            String currentRectangleString = textData[i];
            // Create a array of characters to loop over
            char[] charArray = currentRectangleString.toCharArray();          
            int x = 0; // X coordinate for rectangle
            int y = 0;// Y coordinate for rectangle
            boolean first = true; // Determines whether x or y is changed
            int counter = 0; // Keeps tracks of position of the digit in string
            
            // Loop over each character
            for (int o = charArray.length-1; o >= 0 ; o--){
                char c = charArray[o];
                // If character is a digit
                if(Character.isDigit(c)){
                    // Then parse digit into an integer
                    int digit = Character.getNumericValue(c);
                    // Check whether it is for the x or y coordinate
                    if(first){
                        // Then add integer based on its possition
                        y = y + digit * pow(10, counter);
                    }else{
                        // Then add integer based on its possition
                        x = x + digit * pow(10, counter);
                    }
                // Increment counter for keeping track of decimals
                counter++;
                }else{ // If it is not a digit then its a space
                    // As such reset the counter and make sure that the y is 
                    // handeld next
                    first = false;
                    counter = 0;
                }
            }
            // Add the rectangle
            int[] currentRectangle = {x, y};
            rectangles[i-3] = currentRectangle;
        }
        
        data = new GlobalData(containerType, containerHeight, rotationsAllowed,
                rectangles, numRectangles);
        return data;
    }
    
    public int pow(int a, int b){
        int result = 1;
        for(int i = 0; i < b; i++){
            result = result * a;
        }
        return result;
    }
}
