# lab-03-trees

See lab document: https://uwoece-se2205b-2017.github.io/labs/03-trees


## Q1: Implementing a Binary Search Tree

1. How would you use a BST to implement a Map?

    If the BST is used as a map, each node also will store a value associated with the key. You can store multiple pairs of `key,value` that you want and for example can lookup a value by its key efficiently.

2. What is the complexity of the operations, put(), remove() and height()? Answer the previous for a guaranteed balanced and unbalanced  tree.
  
    O(log(n)) if guaranteed balanced and O(n) if garanteed unbalanced

## Q2: Implementing a Trie

1. What is the complexity of verifying a word of length k is in the Trie? Is there a structure we've covered in class that can beat this?Justify your answer. Be sure to consider k in your answer.
  
     O(nk), where n is the number of words and k is the length of the longest word. Array Based Representation is better than linked.
    This is called a hash array mapped trie (HAMT). It is an implementation of an associative array that combines the characteristics of a  hash table and an array mapped trie. It achieves almost hash table-like speed while using memory much more economically. Also, a hash   table may have to be periodically resized, an expensive operation, whereas HAMTs grow dynamically.

2. What structure is a Trie a "specialization" of?
  
    A tree of sets
