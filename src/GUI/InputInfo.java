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
    String[] types;
    final String BIG = "big";
    int big;
    final String WIDE = "wide";
    int wide;
    final String TALL = "tall";
    int tall;
    final String AVERAGE = "average";
    int average;
    final String SMALL = "small";
    int small;
    
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
        big = 0;
        wide = 0;
        tall = 0;
        average = 0;
        small = 0;
        // First calculate lower and upperbounds in order to determine 
        // what type the retangle is
        int upperboundX = (int) (meanX + sdX);
        int lowerboundX = (int) (meanX - sdX);
        int upperboundY = (int) (meanY + sdY);
        int lowerboundY = (int) (meanY - sdY);
        
        int counter = 0;
        // Based on the bounds determine the type
        for(int[] r: data.getRectangles()){
            // If the rectangle is larger then upperbounds then its a big rectangle
            if(r[0] > upperboundX && r[1] > upperboundY){
                types[counter] = BIG;
                big++;
            }
            // If the rectangle is larger then upperbound of X and lower then 
            // lowerbound of Y then its a wide rectangle
            if(r[0] > upperboundX && r[1] < lowerboundY){
                types[counter] = WIDE;
                wide++;
            }
            // If the rectangle is larger then upperbound of Y and lower then 
            // lowerbound of X then its a wide rectangle
            if(r[0] < lowerboundX && r[1] > upperboundY){
                types[counter] = TALL;
                tall++;
            }
            // If the rectangle is smaller then lowerbounds then its a small rectangle
            if(r[0] < lowerboundX && r[1] < lowerboundY){
                types[counter] = SMALL;
                small++;
            }
            // If not any of the above types, then it is average
            if(types[counter] == null || types[counter].equalsIgnoreCase("")){
                types[counter] = AVERAGE;
                average++;
            }
            // Next rectangle
            System.out.println("Rectangle at i: " + counter + " is " + types[counter]);
            counter++;
        }
    }
    
    // Return the type of the rectangle at an certain index
    public String getTypeRectangleAt(int index){
        if(index > 0 && index < types.length){
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
        text = text + "Rectangle types " + "\n";
        text = text + "Big: " + big + "\n";
        // If we can rotate, wide and tall are the same
        if(data.getRA()){
            text = text + "Wide/Tall: " + (wide + tall) + "\n";
        }else{ // if not show both of them
            text = text + "Wide: " + wide + "\n";
            text = text + "Tall: " + tall + "\n";
        }
        text = text + "Average: " + average + "\n";
        text = text + "Small: " + small;
        return text;
    }
    
}
