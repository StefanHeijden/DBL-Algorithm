/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tester;

/**
 *
 * @author stefa
 */
public class RandomTestFileGenerator extends AbstractTestFileGenerator{
    
    public RandomTestFileGenerator(String containerType, int containerHeight, boolean rotationsAllowed, int numRectangles) {
        super(containerType, containerHeight, rotationsAllowed, numRectangles);
    }
    
    @Override
    public void generateRectangles(){
        System.out.print("TO DO: random generation of rectangles");
    }
    
}
