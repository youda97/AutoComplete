package ca.uwo.eng.se2205b.lab03;

import org.junit.Test;

import java.util.SortedSet;
import java.util.TreeSet;

/**
 *  Tests for Trie implementation
 */
public class TrieTest {

    private Trie underTest = new LinkedTrie();

    @Test
    public void sizeAndIsEmpty() throws Exception {
        // Check empty tree, after adding and removing elements
        underTest.put("start");
        underTest.put("star");
        underTest.put("starter");
        assert (underTest.isEmpty() == false);
    }

    @Test
    public void put() throws Exception {
        // Check what happens when adding and contains
        underTest.put("start");
        underTest.put("star");
        underTest.put("starter");
        assert (underTest.contains("star") == true);
    }

    @Test
    public void putAll() throws Exception {
        // make sure it works compared to put
        SortedSet<String> sortedNames = new TreeSet<>();
        sortedNames.add("Java");
        sortedNames.add("SQL");
        sortedNames.add("HTML");
        sortedNames.add("CSS");
        int counter = underTest.putAll(sortedNames);
        assert (counter == 4);
    }

    @Test
    public void getNextN() throws Exception {
        // Make sure you get the results you expect
        underTest.put("start");
        underTest.put("star");
        underTest.put("starter");
        SortedSet<String> output = new TreeSet<>();
        String word = "s";
        output = underTest.getNextN(word, 4);
        for (String elem : output) {
          assert (underTest.contains(elem) == true);
        }
    }

}