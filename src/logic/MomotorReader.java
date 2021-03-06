package logic;

import java.util.Scanner;

/**
 *
 * @author leighton
 */
public class MomotorReader extends AbstractReader{
    
    public MomotorReader(String path) {
        super(path);
    }
    
    @Override
    public String[] createArrayOfInput() {
        Scanner sc = new Scanner(System.in);
        int count = 0;
        String[] firstThreeLines = new String[3];
        while (count < 3) { //reading the first three lines, to create other part
            String line = "";
            if (sc.hasNextLine()) {
                line = sc.nextLine();
                count++;
            }
            firstThreeLines[count - 1] = line;
        }
        //counts number of rectangles
        int numRectangles = Integer.parseInt(firstThreeLines[2].substring(22));
        //because there are three lines in front of rectangles
        int ArraySize = numRectangles + 3;
        String[] textData = new String[ArraySize];//array to store input in
        for (int i = 0; i < 3; i++) {
            textData[i] = firstThreeLines[i]; //copy first three lines
        }
        
        int counter2 = 0;
        while (counter2 < numRectangles) { //read all lines with rectangles
            String line = "";
            if (sc.hasNextLine()) {
                line = sc.nextLine();

            }
            textData[counter2 + 3] = line; //store these lines in textData
            counter2++;
        }
        
        return textData;
    }
    
}
