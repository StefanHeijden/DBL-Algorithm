package algorithms;

/**
 *
 * @author Leighton
 * 19/03/2020
 * 
 * Class purpose is to compute all possible permutations of certain elements
 * It is used in the BruteForceAlgorithm
 */
public class Permutations {
    
    //counter used in storePermutation to count the index of where to store
    private int count = 0;
    
    /*used as store for the permutations, of the form (but different order):
    [[1,2,3], [1,3,2], [2,3,1], etc....]*/
    private int[][] permutations;
    
    public int[][] compute(int n, int[] toBeClonedElements) {
        // clone the elements, because it is going to be adapted by heapsAlgorithm
        int[] elements = toBeClonedElements.clone();
        int numOfPermutations = computeFaculty(n); //number of possible permutations
        //make of appropriate size, first dimension num of perm second is number of elements
        permutations = new int[numOfPermutations][n];
        //run heaps algorithm, result gets stored in permutations
        heapsAlgorithm(n, elements);
        return permutations;
    }
    
    //implementation of Heap's algorithm, for more information look into that, 
    //recursive implementation of course. First parameter is number of elements.
    //second parameter is the actual array of elements. 
    private void heapsAlgorithm(int n, int[] elements) {
        if (n == 1) { //everytime it gets to a new permutation
            //clone, because will change afterwards, to not have all same permutations
            int[] newElements = elements.clone();
            //actually store the permutations
            storePermutation(newElements);
            
        } else { //needs to go on to compute new permutation
            for (int i = 0; i < n - 1; i++) {
                heapsAlgorithm(n - 1, elements);
                if (n % 2 == 0) { // if even
                    swap(elements, i, n - 1); //swap elements
                } else { //not even
                    swap(elements, 0, n - 1); //swap elements
                }
            }
            heapsAlgorithm(n - 1, elements);
        }
    }
    
    /* swaps two elements of an input array */
    private void swap(int[] input, int a, int b) {
        int tmp = input[a]; //store temporarily
        input[a] = input[b]; //swap b to a
        input[b] = tmp; //swap a to b
    }
    
    /* prints the representation, so permutations */
    private void printPermutations() {
        for (int[] permutation : permutations) { //loop over permutations
            for (int i = 0; i < permutation.length; i++) { //loop over elements
                System.out.print(permutation[i] + " ");
            }
            System.out.println();
        }
    }
    
    /* stores the permutation in permutations **/
    private void storePermutation(int[] elements) {
        permutations[count] = elements; //store permutation in permutations
        count += 1; //increase counter to store next permutation at different index
    }
    
    /** this method computes and returns n!, precondition: n > 0*/
    private int computeFaculty(int n) {
        if (n == 1) { //if n = 1 return one to stop the recursive calls
            return 1;
        }
        
        int result = n * computeFaculty(n - 1); //recursively compute result
        return result; //finally if all recursive calls done return result
    }
}

