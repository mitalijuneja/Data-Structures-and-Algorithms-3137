import java.util.Map;
import java.util.HashMap;


// BinaryHeap class
//
// CONSTRUCTION: with optional capacity (that defaults to 100)
//               or an array containing initial items
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// Comparable deleteMin( )--> Return and remove smallest item
// Comparable findMin( )  --> Return smallest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Mitali Juneja (mj2944)
 * provided BinaryHeap class was modified to store a hashmap that maps binary heap values 
 * to their position in the heap and include decreaseKey() method
 * 
 * 
 * Implements a binary heap.
 * Note that all "matching" is based on the compareTo method.
 * @author Mark Allen Weiss
 */
public class BinaryHeap<AnyType extends Comparable<? super AnyType>>
{
    /**
     * Construct the binary heap.
     */
    public BinaryHeap( )
    {
        this( DEFAULT_CAPACITY );
        positions = new HashMap<AnyType, Integer>();
    }

    /**
     * Construct the binary heap.
     * @param capacity the capacity of the binary heap.
     */
    public BinaryHeap( int capacity )
    {
        currentSize = 0;
        array = (AnyType[]) new Comparable[ capacity + 1 ];
        positions = new HashMap<AnyType, Integer>();
    }
    
    /**
     * Construct the binary heap given an array of items.
     */
    public BinaryHeap( AnyType [ ] items )
    {
            currentSize = items.length;
            array = (AnyType[]) new Comparable[( currentSize + 2 ) * 11 / 10 ];
            positions = new HashMap<AnyType, Integer>();

            int i = 1;
            for( AnyType item : items ){
            	if (item != null){
            		//store position in hashmap
            		positions.put(item, i);
            	}
                array[ i++ ] = item;
            }
            buildHeap( );
    }
    
    
    /**
     * 
     * @param x is the item whose priority has been decreased and needs to be percolated up
     * find its position in the heap using the hashmap in O(1)) and percolate up from that position
     */
    
    public void decreaseKey(AnyType x){
    	int pos = positions.get(x);
    	//percolateUp from pos
    	int parent;
        AnyType tmp = array[ pos ];

        for( ; pos / 2 > 0; pos = parent )
        {
            parent = pos / 2;
            if (array[parent].compareTo(tmp) > 0){
            	array[pos] = array[parent];
            	//update the positions of the parents in the hashmap as they are moved down
            	positions.replace(array[pos], pos);
            }
            else{
            	break;
            }
        }
        array[ pos ] = tmp;
        //update the position of this element in the hashmap after it is moved up
        positions.replace(x, pos);
    }

    /**
     * Insert into the priority queue, maintaining heap order.
     * Duplicates are allowed.
     * @param x the item to insert.
     */
    public void insert( AnyType x )
    {
        if( currentSize == array.length - 1 )
            enlargeArray( array.length * 2 + 1 );

            // Percolate up
        int hole = ++currentSize;
        for( array[ 0 ] = x; x.compareTo( array[ hole / 2 ] ) < 0; hole /= 2 ){
            array[ hole ] = array[ hole / 2 ];
            //update position in hashmap as parent moves down
            positions.replace(array[hole], hole);
        }
        array[ hole ] = x;
        //store position in hashmap after it has been inserted
        positions.put(x, hole);
    }


    private void enlargeArray( int newSize )
    {
            AnyType [] old = array;
            array = (AnyType []) new Comparable[ newSize ];
            for( int i = 0; i < old.length; i++ )
                array[ i ] = old[ i ];        
    }
    
    /**
     * Find the smallest item in the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType findMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return array[ 1 ];
    }

    /**
     * Remove the smallest item from the priority queue.
     * @return the smallest item, or throw an UnderflowException if empty.
     */
    public AnyType deleteMin( )
    {
        if( isEmpty( ) )
            throw new UnderflowException( );

        AnyType minItem = findMin( );
        //remove its position from hashmap
        positions.remove(minItem);
        array[ 1 ] = array[ currentSize-- ];
        percolateDown( 1 );

        return minItem;
    }

    /**
     * Establish heap order property from an arbitrary
     * arrangement of items. Runs in linear time.
     */
    private void buildHeap( )
    {
        for( int i = currentSize / 2; i > 0; i-- ){
            percolateDown( i );
        } 
    }

    /**
     * Test if the priority queue is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( )
    {
        return currentSize == 0;
    }

    /**
     * Make the priority queue logically empty.
     */
    public void makeEmpty( )
    {
        currentSize = 0;
        positions.clear();
    }

    private static final int DEFAULT_CAPACITY = 10;

    private int currentSize;      // Number of elements in heap
    private AnyType [ ] array; // The heap array
    private Map<AnyType, Integer> positions;

    /**
     * Internal method to percolate down in the heap.
     * @param hole the index at which the percolate begins.
     */
    private void percolateDown( int hole )
    {
    	int child;
        AnyType tmp = array[ hole ];

        for( ; hole * 2 <= currentSize; hole = child )
        {
            child = hole * 2;
            if( child != currentSize &&
                    array[ child + 1 ].compareTo( array[ child ] ) < 0 )
                child++;
            if( array[ child ].compareTo( tmp ) < 0 ){
                array[ hole ] = array[ child ];
                //update position in hashmap as children move up
                positions.replace(array[hole], hole);
            }
            else
                break;
        }
        array[ hole ] = tmp;
        if (array[hole] != null){
        	//upadte position of the element in hashmap after it moves down
        	positions.replace(tmp, hole);
        }
        
    }

        // Test program
    public static void main( String [ ] args )
    {
        int numItems = 10000;
        BinaryHeap<Integer> h = new BinaryHeap<>( );
        int i = 37;

        for( i = 37; i != 0; i = ( i + 37 ) % numItems )
            h.insert( i );
        for( i = 1; i < numItems; i++ )
            if( h.deleteMin( ) != i )
                System.out.println( "Oops! " + i );
    }
}
