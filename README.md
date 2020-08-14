# Data-Structures-and-Algorithms-3137
Spring 2020, Homework Assignments for 3137

### Mitali Juneja (mj2944) homework1
Programming #1
1. Rectangle.java = implements Rectangle class. Also implements Comparable (by area) in order to test binary search method on Rectangle[]
2. CompareByArea.java = Comparator that compares 2 Rectangles by their area
3. CompareByPerimeter.java = Comparator that compares 2 Rectangles by their perimeter
4. Problem1.java = final class that implements findMax() (tested in main())
run using Problem1.java

Programming #2 
1. Problem2.java = implements binary search recursively (binarySearch() is overloaded with a helper function that does the recursive work) (tested in main())
run using Problem2.java

Programming #3
1. MakeChange.java = determines the distinct ways to make change for a given number of cents (makeChange() is overloaded with a helper function that does the recursive work) (tested in main())
run using MakeChange.java, uses a single integer command line argument for the number of cents to make change for


### Mitali Juneja (mj2944) Homework 2
Programming #1
1. SymbolBalance.java = implements the symbol balance methods, includes main() that takes file name as command line argument

Prorgramming #2
1. ExpressionTree.java = implements methods to construct, evaluate expression tree
2. Problem2.java = tests ExpressionTree.java in its main(), which instantiates an expression tree and uses the methods on it

Programming #3 
1. InsertTest.scala = implements get() and insert(), includes main() for testing


### Mitali Juneja (mj2944) Homework 3
Programming #1
1. AvlTree.java = the given code for AVL tree implementation, with some adjustments to the nodes, methods for this problem
2. Problem1.java = implements the methods to construct and AVL tree from the words in a file and the line numbers they appear on, then outputs this result in alphabetical order, includes main() that takes the file name as command line argument

Programming #3
1. QuadraticProbingHashTable.java = the given code for hash table implementation, with additional implementation of an iterator for this problem
2. Problem2.java = implements the methods to spell check a file, outputs all misspelled words and the line numbers they appear on and suggestions based on the 3 methods in the problem, includes main() that takes 3 command line arguments in the following order = dictionary personal_dictionary file_to_check
3. dictionary.txt = sample small dictionary file for this program (1000 words taken from https://www.ef.com/wwen/english-resources/english-vocabulary/top-1000-words/ )


### Mitali Juneja (mj2944) Homework 4
Programming #1
1. KBestCounter.java = the skeleton code for KBestCounter class with count() and kbest() functionality completed
2. TestKBest.java = provided tester class for KBestCounter with main()

Programming #2 
1. Huffman.java = implements the methods for creating a Huffman tree, displaying Huffman encodings, decoding, and encoding a message, includes main() that takes file name for file to construct Huffman tree from as command line argument
2. huffman_test.txt = a short text file to test Huffman.java


### Mitali Juneja (mj2944) Homework 5
Programming #1
1. Edge.java = the provided file, unmodified
2. Vertex.java = the provided file, modified to implement Comparable based on distance field so that a binary min heap could be used in Dijkstra's algorithm
3. BinaryHeap.java = the provided file, modified to store a hashmap that maps items in the heap to their positions in the heap array and include decreaseKey() method
4. Dijkstra.java = the provided file, modified to implement the missing methods
5. Display.java = the provided file, unmodified
