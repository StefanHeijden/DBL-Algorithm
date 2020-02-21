package GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;
import javax.swing.*;
import tester.AbstractTestFileGenerator;
import tester.RandomTestFileGenerator;
import main.PackingSolver;

/**
 *
 * GUI class, not simple anymore btw
 * Used for testing PackingSolver
 */
public class SimpleGUI {
    static JFrame frame;
    static DrawPanel panel;
    static JTextArea textArea;
    static final int FRAMEHEIGHT = 1000;
    static final int FRAMEWIDTH = 1600;
    static final int BUTTONHEIGHT = 50;
    static final int BUTTONWIDTH = 200;
    static final int TEXTAREAHEIGHT = 200;
    // These are the groups and input neeeded for TestFileGenerator menu
    static ButtonGroup group1;
    static ButtonGroup group2;
    static ButtonGroup group3;
    static ButtonGroup group4;
    static ButtonGroup pathGroup;
    static String[] titelsGroup1 = {"free", "fixed"};
    static String[] titelsGroup2 = {"no rotation", "rotation"};
    static String[] titelsGroup3 = {"4", "6", "10", "25", "10000"};
    static String[] titelsGroup4 = {"Random generation", 
                                    "Random generation with bounds",
                                    "Squares only"
    };// TitelGroup4 has to be added manually to the switch in generateTestFile()
      // in the class GenerateTestFile inside the class GUI!!

        // You can add you file path here 
    private static final String pathLeigthon = "E:/TUe/PT/Courses/Y3/"
                + "DBL algorithms/testcases/";
    private static final String pathStefan = "C:/Users/stefa/Documents/DBL-Algorithm/testfiles/";
    private static final String pathEzra = "";
    private static final String pathYana = "C:/Users/yana/Documents/"
            +"DBL-Algorithm/testcases/";
    private static final String pathJodi = "C:/Users/s165698/Documents/DBL Algorithms/";
    
    
    //choose you path
    private static final String path = pathStefan;
    
    /**
     * // This main method can be used for testing
     */
    public static void main(String[] args) {
        // Create frame
        frame = new JFrame("GUI for PackingSolver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAMEWIDTH, FRAMEHEIGHT);
        frame.setLayout(null);
        
        // Create menu
        JMenuBar menuBar = new JMenuBar();
        addTestGeneratorMenu(menuBar);
        addFileMenu(menuBar);
        
        // Create buttons
        // Create button for generating test files
        JButton generateFileButton = new JButton("Generate");
        generateFileButton.addActionListener(new GenerateTestFile());
        generateFileButton.setBounds(FRAMEWIDTH - BUTTONWIDTH, 10, 
                BUTTONWIDTH, BUTTONHEIGHT);
        
        // Create button for running test files
        JButton buttonPackingSolver = new JButton ("Run PackingSolver");
        buttonPackingSolver.addActionListener(new RunPackingSolver());  
        buttonPackingSolver.setBounds(FRAMEWIDTH - BUTTONWIDTH, 15 + BUTTONHEIGHT, 
                BUTTONWIDTH, BUTTONHEIGHT);

        // Create repaint button
        JButton repaintButton = new JButton ("Repaint");
        repaintButton.addActionListener(new Repaint());  
        repaintButton.setBounds(FRAMEWIDTH - BUTTONWIDTH, 20 + 2 * BUTTONHEIGHT, 
                BUTTONWIDTH, BUTTONHEIGHT);
        
        // Create pannel for rectangles to be drawn on
        panel = new DrawPanel();
        panel.setBackground(new Color(200, 200, 200));  
        panel.setBounds(0, 0, FRAMEWIDTH - BUTTONWIDTH, FRAMEHEIGHT);
        
        // Create text field with info
        textArea = new JTextArea();
        textArea.setBounds(FRAMEWIDTH - BUTTONWIDTH, FRAMEHEIGHT - TEXTAREAHEIGHT, 
                BUTTONWIDTH, TEXTAREAHEIGHT);
        textArea.setText("container height: \n" +
                        "rotations allowed: \n" +
                        "number of rectangles: \n");
        textArea.setEditable(false);
        // Add all components to frame
        frame.setJMenuBar(menuBar);
        frame.add(generateFileButton);
        frame.add(buttonPackingSolver);
        frame.add(repaintButton);
        frame.add(panel);
        frame.add(textArea);
        frame.setVisible(true);
    }
    
    // This method adds a menu that can be used for generating test files
    public static void addTestGeneratorMenu(JMenuBar menuBar){

        //Build the first menu.
        JMenu menu = new JMenu("Generate test file");
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu can be used to generate test files");

        // Create and add a group of radio button menu items for selecting free or fixed
        group1 = new ButtonGroup();
        createMenuRadioButtons(group1, menu, titelsGroup1);
        // Create and add a group of radio button menu items for rotation allowed
        group2 = new ButtonGroup();
        createMenuRadioButtons(group2, menu, titelsGroup2);
        // Create and add a group of radio button menu items for number of rectangles
        group3 = new ButtonGroup();
        createMenuRadioButtons(group3, menu, titelsGroup3);
        // Create and add a group of radio button menu items for way the rectangles are generated
        group4 = new ButtonGroup();
        createMenuRadioButtons(group4, menu, titelsGroup4);
        
        menuBar.add(menu);
    }
    
    public static void createMenuRadioButtons(ButtonGroup group, JMenu menu, String[] titelsGroup){
        menu.addSeparator();
        JRadioButtonMenuItem rbMenuItem;
        for(String s: titelsGroup){
            rbMenuItem = new JRadioButtonMenuItem(s);
            rbMenuItem.setSelected(true);
            group.add(rbMenuItem);
            menu.add(rbMenuItem);
        }
    }
        
    public static void addFileMenu(JMenuBar menuBar){
        // Obtain all the file paths from the path folder
        List<String> files = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.forEach((p) -> {
                files.add(p.toString());
            });
        }catch(IOException e){
            System.out.println("Getting files failed");
        }
        
        // Remove the empty path(s) from the arrayList
        boolean done = false;
        while(!done){
            done = true;
            String remove = "";
            for(String s: files){
                if(s.length() <= path.length()){
                    remove = s;
                    System.out.println(s);
                    done = false;
                }
            }
            files.remove(remove);
        }
        
        // Obtain the file names from the Arraylist and add them into a String[]
        String[] file = new String[files.size()];
        for(int i = 0; i < file.length; i++){
            file[i] = (files.get(i).substring(path.length()));
        }
        
        //Build the second menu.
        JMenu menu = new JMenu("Select File");
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu can be used to test files");
        pathGroup = new ButtonGroup();
        createMenuRadioButtons(pathGroup, menu, file);
        
        // Add the menu to the menubar
        menuBar.add(menu);
    }


    /*
     * This class is used for the action of the run GenerateTestFile button
     * It generates a new testfile in destination path, based on the selected
     * radio buttons in the menu
     *
    */
    static class GenerateTestFile implements ActionListener{
        AbstractTestFileGenerator createFile;
        public GenerateTestFile(){

        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String containerType; 
            int containerHeight = 0;
            boolean rotationsAllowed; 
            int numRectangles;
            // Get type from radiobuttons
            containerType = getSelected(group1);
            // Get container heigth
            if(containerType.equalsIgnoreCase("fixed")){
                 String heigth = JOptionPane.showInputDialog("Please input heigth: ");
                 containerHeight = Integer.parseInt(heigth);
            }
            System.out.println("containerHeight" + containerHeight);
            // Get rotations
            rotationsAllowed = getSelected(group2).equalsIgnoreCase(titelsGroup2[1]);
            numRectangles = Integer.parseInt(getSelected(group3));
            //  Generate a new test file
            generateTestFile(containerType, containerHeight, rotationsAllowed, 
                    numRectangles, getSelected(group4));
        }

        private void generateTestFile(String containerType, int containerHeight, 
                boolean rotationsAllowed, int numRectangles, String selected) {
            switch(selected) {
              case "Random generation":
                createFile = new RandomTestFileGenerator(containerType, containerHeight, rotationsAllowed, numRectangles);
                break;
              case "Random generation with bounds":
                  
                //createFile = new RandomTestFileGenerator(containerType, containerHeight, rotationsAllowed, numRectangles);
                break;
              case "Squares only":
                //createFile = new RandomTestFileGenerator(containerType, containerHeight, rotationsAllowed, numRectangles);
                break;
              default:
                // code block
            }
            
        }
    }

    /*
     * This class is used for the action of the run PackingSolver button
     * This runs the selected file in the menubar in PackingSolver
     * and puts the result on screen
    */
    static class RunPackingSolver implements ActionListener {
        PackingSolver packingSolver;
        
        @Override
        public void actionPerformed(ActionEvent e){
            // Run PackingSolver
            System.out.println("----------------------------------------- " +
                    "Run file: " + getSelected(pathGroup) + 
                    " -----------------------------------------");
            packingSolver = new PackingSolver();
            packingSolver.runFromGUI(path + getSelected(pathGroup));
            
            // Put result on screen
            // Obtain rectangles and location (result)
            int[][] rectangles = packingSolver.getRectangles();
            int[][] placement = packingSolver.getPlacement();
            // Create rectangle list to draw rectangles
            List<BetterRectangle> dRectangles = new ArrayList<>();
            
            // Make sure the rectangles.length equals the placement.length
            if(rectangles.length != placement.length){
                System.out.println("ERROR: rectangles.length != placement.length ");
            }
            
            // Create new rectangle to be drawn from the rectangles
            int maxWeight = calcMaxweigth(rectangles);
            for(int i = 0; (i < rectangles.length) && (i < placement.length); i++){
                // Also add index and weight to color it
                dRectangles.add(new BetterRectangle(rectangles[i][0], 
                        rectangles[i][1], i, maxWeight));
                // then move the rectangle to its placed spot
                dRectangles.get(i).move(placement[i][0], placement[i][1]);
            }
            // Make sure the panel contains the rectangles and draws them
            panel.setRectangles(dRectangles);
            panel.scale();
            panel.specialRepaint();
            updateTextArea(packingSolver.getGlobalData());
        }

        // Calculate the maxWeight for coloring
        private int calcMaxweigth(int[][] rectangles) {
            int weight = 0;
            for(int[] r: rectangles){
                if(weight < r[0] * r[1]){
                    weight = r[0] * r[1];
                }
            }
            return weight;
        }

        private void updateTextArea(String[] text) {
            textArea.setText("");
            for(String s: text){
                textArea.append(s);
            }
        }
    
    }
    
    /*
     * This class is used for the action of the repaint button
     *
    */
    static class Repaint implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(panel.canRepaint()){
                panel.specialRepaint();
            }
        }
        
    }
    
    private static String getSelected(ButtonGroup group) {
       String result = "";
       for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                result = button.getText();
            }
        }
       return result;
    }
}
    