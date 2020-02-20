package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.*;
import tester.AbstractTestFileGenerator;
import tester.RandomTestFileGenerator;

/**
 *
 * GUI class
 */
public class SimpleGUI {
    int testInt = 0;
    // These are the groups and input neeeded for TestFileGenerator menu
    static ButtonGroup group1;
    static ButtonGroup group2;
    static ButtonGroup group3;
    static ButtonGroup group4;
    static String[] titelsGroup1 = {"free", "fixed"};
    static String[] titelsGroup2 = {"no rotation", "rotation"};
    static String[] titelsGroup3 = {"4", "6", "10", "25", "10000"};
    static String[] titelsGroup4 = {"Random generation", 
                                    "Random generation with bounds",
                                    "Squares only"
    };// TitelGroup4 has to be added manually to the switch in generateTestFile()
      // in the class GenerateTestFile inside the class GUI!!

    /**
     * // This main method can now just be used for testing, but can later be implemented in MAIN
     */
    public static void main(String[] args) {
        // Use scanner to ask for what kind of input the user wants
        JFrame frame = new JFrame("Simple GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200,800);
        
        // Create menu
        JMenuBar menuBar = new JMenuBar();
        addTestGeneratorMenu(frame, menuBar);
        System.out.println("TO DO: addway to insert height");
         
        // Create buttons for generating 
        JButton generateFileButton = new JButton("Generate");
        generateFileButton.addActionListener(new GenerateTestFile());
        // Add button and menu to frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(generateFileButton); // Adds Button to content pane of frame
        frame.setVisible(true);
        
        JButton buttonPackingSolver = new JButton ("running Packing Solver");

    }
    
    // This method adds a menu that can be used for generating test files
    public static void addTestGeneratorMenu(JFrame frame, JMenuBar menuBar){

        //Build the first menu.
        JMenu menu = new JMenu("Generate test file");
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu can be used to generate test files");
        menuBar.add(menu);

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

    // Simple actionListener for button so that test file is created
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

        private String getSelected(ButtonGroup group) {
           String result = "";
           for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
                AbstractButton button = buttons.nextElement();

                if (button.isSelected()) {
                    result = button.getText();
                }
            }
           return result;
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
}