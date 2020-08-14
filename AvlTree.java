import java.util.LinkedList;




// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Modified for Problem1.java (Node, additional helper methods for the tree, etc)
 * 
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 * 
 */
public class AvlTree
{
    /**
     * Construct the tree.
     */
    public AvlTree( )
    {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param w the word to insert, line is the corresponding line number
     */
    public void insert(String w, int line)
    {
    	root = insert(w, root, line);
    }

    
    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public String findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).word;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public String findMax( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).word;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return index 0 = if the word is in tree, index 1 = if the line number is in LL
     */
    public boolean[] contains(String w, int line)
    {
        return contains( w, root, line);
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( )
    {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( )
    {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    private static final int ALLOWED_IMBALANCE = 1;
    
    // Assume t is either balanced or within one of being balanced
    private AvlNode balance( AvlNode t )
    {
        if( t == null )
            return t;
        
        if( height( t.left ) - height( t.right ) > ALLOWED_IMBALANCE )
            if( height( t.left.left ) >= height( t.left.right ) )
                t = rotateWithLeftChild( t );
            else
                t = doubleWithLeftChild( t );
        else
        if( height( t.right ) - height( t.left ) > ALLOWED_IMBALANCE )
            if( height( t.right.right ) >= height( t.right.left ) )
                t = rotateWithRightChild( t );
            else
                t = doubleWithRightChild( t );

        t.height = Math.max( height( t.left ), height( t.right ) ) + 1;
        return t;
    }
    
    public void checkBalance( )
    {
        checkBalance( root );
    }
    
    private int checkBalance( AvlNode t )
    {
        if( t == null )
            return -1;
        
        if( t != null )
        {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }
        
        return height( t );
    }
    
    
    /**
     * Internal method to insert into a subtree.
     * @param w the word to insert.
     * @param t the node that roots the subtree.
     * @param line is the corresponding line
     * @return the new root of the subtree.
     */
    private AvlNode insert(String w, AvlNode t, int line)
    {
        if( t == null ){
        	AvlNode n= new AvlNode(w);
        	n.lineNums.add(line);
        	return n;
        }

        int compareResult = w.compareTo( t.word );
        
        if( compareResult < 0 )
            t.left = insert( w, t.left, line);
        else if( compareResult > 0 )
            t.right = insert( w, t.right, line);
        else{
        	if (!t.lineNums.contains(line)){
        		t.lineNums.add(line);
        	}
        }
        return balance( t );
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode findMin( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.left != null )
            t = t.left;
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode findMax( AvlNode t )
    {
        if( t == null )
            return t;

        while( t.right != null )
            t = t.right;
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return index 0 = if word is in tree, index 1 = if the line number is in LL
     */
    private boolean[] contains(String w, AvlNode t, int line)
    {
        while( t != null )
        {
            int compareResult = w.compareTo( t.word );
            
            if( compareResult < 0 )
                t = t.left;
            else if( compareResult > 0 )
                t = t.right;
            else{
                if (t.lineNums.contains(line)){
                	return new boolean[]{true, true};// Match
                }
                return new boolean[]{true, false};
            }
        }

        return new boolean[]{false, false};   // No match
    }

    /**
     * Internal method to print a subtree in sorted order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode t )
    {
        if( t != null )
        {
            printTree( t.left );
            printNode(t);
            printTree( t.right );
        }
    }
  
    
    /**
     * 
     * @param t is the node to print
     * 
     * Print the node in the following format (word = [space separated string of line numbers])
     */
    private void printNode(AvlNode t){
    	StringBuilder sb = new StringBuilder(t.word + " = ");
    	for(int line : t.lineNums){
    		sb.append(line + " ");
    	}
    	System.out.println(new String(sb));
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode t )
    {
        return t == null ? -1 : t.height;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithLeftChild( AvlNode k2 )
    {
        AvlNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = Math.max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode rotateWithRightChild( AvlNode k1 )
    {
        AvlNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = Math.max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode doubleWithLeftChild( AvlNode k3 )
    {
        k3.left = rotateWithRightChild( k3.left );
        return rotateWithLeftChild( k3 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode doubleWithRightChild( AvlNode k1 )
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }

    private static class AvlNode
    {
            // Constructors
        AvlNode(String w)
        {
            word = w;
            lineNums = new LinkedList<Integer>();
        }

        

        String word;      
        LinkedList<Integer> lineNums;
        AvlNode left;        
        AvlNode right;       
        int height;       
    }

      /** The tree root. */
    private AvlNode root;


    
}
