package tester;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author stefa
 */
public class TypedTestFileGenerator extends AbstractTestFileGenerator{
    int[][] rectangles;
    ArrayList<String> types;
    ArrayList<String> sizes;
    // The mean and variance determine how 
    int mean;
    int variance;
    boolean alreadyInit;
    
    public TypedTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, path, numberOfFiles);
        alreadyInit = false;
    }
    
    public TypedTestFileGenerator(String containerType, int containerHeight, 
            boolean rotationsAllowed, int numRectangles, String path, int numberOfFiles,
                ArrayList<String> types, ArrayList<String> sizes) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles, path, numberOfFiles);
        this.sizes = sizes;
        this.types = types;
        alreadyInit = true;
    }
    
    @Override
    // Generate rectangles at random
    public int[][] generateRectangles(){
        mean = 50;
        variance = 25;
        rectangles = new int[data.getNumRectangles()][2];
        types = new ArrayList();
        sizes = new ArrayList();
        
        // Get types from input screen
        getTypes();
        
        // Then create rectangles for each type chosen at random
        for(int i = 0; i < data.getNumRectangles(); i++){
            rectangles[i] = createRectangle();
        }
        return rectangles;
    }

    private int[] createRectangle() {
        int x = 0;
        int y = 0;
        // Pick random type and size
        String type = types.get(new Random().nextInt(types.size())); 
        String size = sizes.get(new Random().nextInt(sizes.size())); 
        switch(size.toLowerCase()){
        case "small":
            x = mean - variance;
            y = mean - variance;
            break;
        case "medium":
            x = mean;
            y = mean;
            break;
        case "big":
            x = mean + variance;
            y = mean + variance;
            break;
        default:
            break;
        }
        double average = (x + y) / 2;
        switch(type){
        case "tall":
            x = x - (int) ((Math. random() * 0.2 + 0.2) * average);
            y = y + (int) ((Math. random() * 0.2 + 0.2) * average);
            break;
        case "wide":
            x = x + (int) ((Math. random() * 0.2 + 0.2) * average);
            y = y - (int) ((Math. random() * 0.2 + 0.2) * average);
            break;
        case "squarish":
            x = x + (int) ((Math. random() * 0.4 - 0.2) * average);
            y = y + (int) ((Math. random() * 0.4 - 0.2) * average);
            break;
        }
        
        // Make sure x and y are legal
        if(x < 1){
            x = 1;
        }
        if(y < 1){
            y = 1;
        }
        if(data.getType().equals(data.FIXED) && y > data.getHeight()){
            y = data.getHeight();
        }
        
        int[] rectangle = {x, y};
        return rectangle;
    }

    // Gets the types from a input screen
    private void getTypes() {
        if(!alreadyInit){
            // Init check boxes
            JCheckBox smallField = new JCheckBox("Small boxes");
            JCheckBox mediumField = new JCheckBox("Medium boxes");
            JCheckBox bigField = new JCheckBox("Big boxes");
            JCheckBox wideField = new JCheckBox("Wide boxes");
            JCheckBox tallField = new JCheckBox("Tall boxes");
            JCheckBox squarishField = new JCheckBox("Squarish boxes");

            // Add boxes to the popupscreen
            JPanel myPanel = new JPanel();
            myPanel.add(smallField);
            myPanel.add(mediumField);
            myPanel.add(bigField);
            myPanel.add(wideField);
            myPanel.add(tallField);
            myPanel.add(squarishField);

            // Add the types to the list
            int result = JOptionPane.showConfirmDialog(null, myPanel, 
                     "Please choose types of rectangles", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if(smallField.isSelected()){
                    sizes.add("small");
                }
                if(mediumField.isSelected()){
                    sizes.add("medium");
                }
                if(bigField.isSelected()){
                    sizes.add("big");
                }
                if(wideField.isSelected()){
                    types.add("wide");
                }
                if(tallField.isSelected()){
                    types.add("tall");
                }
                if(squarishField.isSelected()){
                    types.add("squarish");
                }
            }

            // Make sure there is at least 1 type
            if(types.size() < 1){
                types.add("squarish");
            }

            // Make sure there is at least 1 size
            if(sizes.size() < 1){
                sizes.add("medium");
            }
        }
        
        // Create new filename
        String name = "";
        if(sizes.size() < 3){
            for(String size: sizes){
                name = name + size;
            }
             name = name + "-";
        }else{
            name = name + "AllSizes-";
        }
        
        if(types.size() < 3){
            for(String type: types){
                name = name + type;
            }
            name = name + "-";
        }else{
            name = name + "AllTypes-";
        }
        
        if(data.getType().equalsIgnoreCase(data.FREE)){
            name = name + "r" + data.getNumRectangles() + "-hf-r";
        }else{
            name = name + "r" + data.getNumRectangles() + "-h" + data.getHeight() + "-r";
        }
        if(data.getRA()){
            name = name + "y.in";
        }else{
            name = name + "n.in";
        }
        setFileName(name);
    }
}
