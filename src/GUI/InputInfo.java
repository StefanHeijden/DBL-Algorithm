package GUI;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import logic.GlobalData;

/**
 *
 * This class uses Global Data to calculate some general info 
 * like mean, standard deviation and types of the blocks.
 */
public final class InputInfo {
    GlobalData data;
    double mean = 0;
    double meanX = 0;
    double meanY = 0;
    double sd = 0;
    double sdX = 0;
    double sdY = 0;
    final double FIX1 = 0.5;
    final double FIX2 = 0.5;
    String[] sizes;
    final String BIG = "big";
    int big;
    final String AVERAGE = "average";
    int average;
    final String SMALL = "small";
    int small;
    String[] types;
    final String WIDE = "wide";
    int wide;
    final String TALL = "tall";
    int tall;
    final String SQUARE = "square";
    int square;
    final String SQUARISH = "squarish";
    int squarish;
    
    
    public InputInfo(GlobalData data){
        this.data = data;
        calcMean();
        calcSD();
        calcTypes();
    }
    
    // Calculate the mean of the X and Y of the rectangles
    public void calcMean(){
        // Make sure the means are 0
        mean = 0;
        meanX = 0;
        meanY = 0;
        // Loop over each rectangle and add the X and / or Y
        for(int[] rectangle: data.getRectangles()){
            mean = mean + (rectangle[0] * rectangle[1]);
            meanX = meanX + rectangle[0];
            meanY = meanY + rectangle[1];
        }
        // Divide the sum by the number of rectangles
        if(data.getRectangles().length > 1){
            mean = mean / data.getRectangles().length;
            meanX = meanX / data.getRectangles().length;
            meanY = meanY / data.getRectangles().length;
        }
    }
    
    // Calculate the variance of the X and Y of the rectangles
    public void calcSD(){
        // Make sure the sd are 0
        sd = 0;
        sdX = 0;
        sdY = 0;
        // Loop over each rectangle and add ((X and / or Y) - mean) ^ 2
        for(int[] rectangle: data.getRectangles()){
            sd = sd + ((rectangle[0] * rectangle[1]) - mean) * ((rectangle[0] * rectangle[1]) - mean);
            sdX = sdX + (rectangle[0] - meanX) * (rectangle[0] - meanX);
            sdY = sdY + (rectangle[1] - meanY) * (rectangle[1] - meanY);
        }
        // Divide the sum by the number of rectangles - 1 (n - 1)
        if(data.getRectangles().length > 1){
            sd = sd / (data.getRectangles().length - 1);
            sdX = sdX / (data.getRectangles().length - 1);
            sdY = sdY / (data.getRectangles().length - 1);
        }
        // And take the square root to obtian the variance
        sd = Math.sqrt(sd);
        sdX = Math.sqrt(sdX);
        sdY = Math.sqrt(sdY);
    }
    
    public void calcTypes(){
        types = new String[data.getRectangles().length];
        sizes = new String[data.getRectangles().length];
        big = 0;
        average = 0;
        small = 0;
        wide = 0;
        tall = 0;
        square = 0;
        squarish =0;
        
        // First calculate lower and upperbounds in order to determine 
        // what size the retangle is
        int upperbound = (int) (mean + (sd * FIX1));
        int lowerbound = (int) (mean - (sd * FIX1));
        
        int counter = 0;
        // Based on the bounds determine the type and size of each rectangle
        for(int[] r: data.getRectangles()){   
            // First determine the size
            // If the rectangle is larger then upperbound then its a big rectangle
            if(r[0] * r[1] > upperbound){
                sizes[counter] = BIG;
                big++;
            }
            // If the rectangle is smaller then lowerbound then its a small rectangle
            if(r[0] * r[1] < lowerbound){
                sizes[counter] = SMALL;
                small++;
            }
            // If not any of the above sizes, then it is average
            if(sizes[counter] == null || sizes[counter].equalsIgnoreCase("")){
                sizes[counter] = AVERAGE;
                average++;
            }
            
            // Then determine what type the rectangle is using its own bounds
            double meanR = (r[0] + r[1] / 2) * FIX2;
            int difference = Math.abs(r[0] - r[1]);
            // If there is a significant difference between the width and height
            if(difference > meanR){
                // Then it is wide if width > height
                if(r[0] > r[1]){
                    sizes[counter] = WIDE;
                    wide++;
                }else{
                    // or tall of height > width
                    types[counter] = TALL;
                    tall++;
                }
            }else{
                // If there isn't much of a differnce then it is squarish
                if(r[0] == r[1]){ // and a square if width = height
                    types[counter] = SQUARE;
                    square++;
                }else{
                    types[counter] = SQUARISH;
                    squarish++;
                }
            }
            
            // Next rectangle
            counter++;
        }
    }
    
    // Return the type of the rectangle at an certain index
    public String getSizeRectangleAt(int index){
        if(index >= 0 && index < sizes.length){
            return sizes[index];
        }else{
            return "";
        }
    }
    
    // Return the type of the rectangle at an certain index
    public String getTypeRectangleAt(int index){
        if(index >= 0 && index < types.length){
            return types[index];
        }else{
            return "";
        }
    }
    
    public double getMean(){
        return mean;
    }
    
    public double getSD(){
        return sd;
    }
    
    public String toText(){
        // Round the mean and standard deviation to 3 decimals
        DecimalFormat df = new DecimalFormat("#.####");
        df.setRoundingMode(RoundingMode.CEILING);
        
        // Add text of text box to string
        String text = "";
        text = text + "Mean: " + df.format(mean) + "\n";
        text = text + "Variance: " + df.format(sd) + "\n";
        text = text + "Mean of X: " + df.format(meanX) + "\n";
        text = text + "Variance of X: " + df.format(sdX) + "\n";
        text = text + "Mean of Y: " + df.format(meanY) + "\n";
        text = text + "Variance of Y: " + df.format(sdY) + "\n";
        text = text + "Rectangle sizes " + "\n";
        text = text + "Big: " + big + "\n";
        text = text + "Average: " + average + "\n";
        text = text + "Small: " + small + "\n";
        text = text + "Rectangle types " + "\n";
        // If we can rotate, wide and tall are the same
        if(data.getRA()){
            text = text + "Wide/Tall: " + (wide + tall) + "\n";
        }else{ // if not show both of them
            text = text + "Wide: " + wide + "\n";
            text = text + "Tall: " + tall + "\n";
        }
        text = text + "Squarish: " + squarish + "\n";
        text = text + "Squares: " + square;
        return text;
    }
    
}
