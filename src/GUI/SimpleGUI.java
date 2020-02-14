/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.*;
import tester.*;

/**
 *
 * GUI class
 */
public class SimpleGUI {
    
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
        JButton button = new JButton("Generate");
        // -----------------------------------------Add action to button such that file is generated
        System.out.println("TO DO: add button or something to start generating");
        // Add button and menu to frame
        frame.setJMenuBar(menuBar);
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);

    }
    
    // This method adds a menu that can be used for generating test files
    public static void addTestGeneratorMenu(JFrame frame, JMenuBar menuBar){

        //Build the first menu.
        JMenu menu = new JMenu("Generate test file");
        menu.getAccessibleContext().setAccessibleDescription(
                "This menu can be used to generate test files");
        menuBar.add(menu);

        //a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
        JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Free");
        rbMenuItem.setSelected(true);
        group.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("Fixed");
        group.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        // A second group of radio button menu items for number of rectangles
        menu.addSeparator();
        ButtonGroup group2 = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("4");
        rbMenuItem.setSelected(true);
        group2.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("6");
        group2.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("10");
        group2.add(rbMenuItem);
        menu.add(rbMenuItem);

        rbMenuItem = new JRadioButtonMenuItem("25");
        group2.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("10000");
        group2.add(rbMenuItem);
        menu.add(rbMenuItem);
        
         // A third group of radio button menu items for way the rectangles are generated
        menu.addSeparator();
        ButtonGroup group3 = new ButtonGroup();
        rbMenuItem = new JRadioButtonMenuItem("Random generation");
        rbMenuItem.setSelected(true);
        group3.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("Rectangles differ a lot");
        group3.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("Rectangles almost the same");
        group3.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        rbMenuItem = new JRadioButtonMenuItem("Rectangles almost the same");
        group3.add(rbMenuItem);
        menu.add(rbMenuItem);
        
        menuBar.add(menu);
    }
}

