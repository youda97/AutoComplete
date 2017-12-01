package ca.uwo.eng.se2205b.lab03;

import java.util.*;

/**
 * Implement a Trie via linking Nodes.
 */
public class LinkedTrie implements Trie {

    public class TrieNode {

        private HashMap<Character, TrieNode> children;
        private char character;
        private boolean isWord;
        private String leafWord;

        public TrieNode() {
            children = null;
        }

        public TrieNode(char c) {
            children = new HashMap<Character, TrieNode>();
            this.character = c;
        }

        public HashMap<Character, TrieNode> getChildren() {

            return children;
        }

        public char getCharacter() {

            return character;
        }

        public boolean isWord() {

            return isWord;
        }

        public void setIsWord(boolean word) {

            isWord = word;
        }
    }

    private TrieNode root;
    private int size;

    public LinkedTrie() {
        root = new TrieNode(' ');
        size = 0;
    }

    @Override
    public int size() {

        return size;
    }

    @Override
    public boolean isEmpty() {

        return size() == 0;
    }

    @Override
    public boolean put(String word) {
        HashMap<Character, TrieNode> children = root.children;
        boolean isAdded = false;
        if (word.length() > 0) {
            int i =0;
            TrieNode t = null;
            for (i = 0; i < word.length(); i++) {
                char c = word.toLowerCase().charAt(i);
                if (children.containsKey(c)) {
                    t = children.get(c);
                } else {
                    t = new TrieNode(c);
                    children.put(c, t);
                    isAdded = true;
                }
                children = t.children;
                if (i == word.length() - 1) { //set word node
                    t.isWord = true;
                    t.leafWord = word.toLowerCase();
                    size++;
                }
            }
        }
        return isAdded;
    }

    @Override
    public int putAll(SortedSet<? extends String> words) {
        int counter = 0;
        Iterator it = words.iterator();
        while (it.hasNext()) {
            // Get one word
            String w = (String) it.next();
            if (put(w)) counter++;
        }
        return counter;
    }

    @Override
    public SortedSet<String> getNextN(String prefix, int N) throws IllegalArgumentException {
        if (prefix == null || prefix == "") {
            throw new IllegalArgumentException("Prefix is not valid!");
        }
        SortedSet<String> result = new TreeSet<>();
        TrieNode location = searchWord(prefix);
        if (location != null) {
            autocomplete(location, result, N);
        } else {
            return null;
        }
        return result;
    }


    private TrieNode searchWord(String word) {
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        int i;
        for (i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.children;
            } else {
                return null;
            }
        }
        if (i == word.length()) {
            return t;
        }
        return null;
    }

    public void autocomplete(TrieNode node, SortedSet<String> result, int limit) {

        if (node.isWord()) {
            if (result.size() < limit || limit == -1)
            result.add(node.leafWord);
        }
        for (char key : node.getChildren().keySet()) {
            autocomplete(node.getChildren().get(key), result, limit);
        }
    }

    @Override
    public boolean contains(String word) throws IllegalArgumentException {
        Map<Character, TrieNode> children = root.getChildren();
        TrieNode t = null;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (children.containsKey(c)) {
                t = children.get(c);
                children = t.getChildren();
            } else
                return false;
        }
        return true;
    }

}
