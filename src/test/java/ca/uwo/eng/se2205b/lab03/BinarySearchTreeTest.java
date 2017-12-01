package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

/**
 * Tests for Binary Search Tree
 */
public class BinarySearchTreeTest {

    private final BinarySearchTree<Integer> underTest = new BinarySearchTree<>();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        // 10 20 5 3 14 8
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);
        assert (underTest.isEmpty() == false);

       underTest.remove(10);
       assert (underTest.contains(10) == false);

    }

    @Test
    public void height() throws Exception {
        // check an empty tree and after adding/removing
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);
        assert (underTest.height() == 3);
    }

    @Test
    public void put() throws Exception {
        // check the return result, adding/removing

        assert (underTest.put(10) == true);
        assert (underTest.remove(10) == true);
    }

    @Test
    public void remove() throws Exception {
        // Removing nodes, remember the cases
        underTest.put(10);
        assert (underTest.remove(10) == true);
    }


    @Test
    public void iterator() throws Exception {
        // Check the three different types of iteration
    }

    @Test
    public void contains() throws Exception {
        // Actually in the tree, not in..
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);
        assert (underTest.contains(10) == true);
    }

    @Test
    public void isComplete() throws Exception {
        // Check the null condition, complete, incomplete..
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);
        assert (underTest.isProper() == false);
    }

    @Test
    public void isFull() throws Exception {
        // Check the null condition, complete, incomplete..
        underTest.put(10);
        underTest.put(20);
        underTest.put(5);
        underTest.put(3);
        underTest.put(14);
        underTest.put(8);
        assert (underTest.isBalanced() == true);
    }

}