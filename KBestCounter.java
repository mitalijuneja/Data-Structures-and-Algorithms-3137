import java.util.PriorityQueue;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Mitali Juneja (mj2944
 * Programming #1 finds the k largest values in a set of data
 * 
 */

public class KBestCounter<T extends Comparable<? super T>> {

    PriorityQueue<T> heap;
    int k;

   
    public KBestCounter(int k) {
        heap = new PriorityQueue<T>();
        this.k = k;
    }

   
    /**
     * 
     * @param x is the next element to process in the set of data
     * Adds the element to the heap if it is one of the k largest seen so far
     */
    public void count(T x) {
    	//if k elements have not yet been seen, add this to the heap
        if (heap.size() < k){
        	heap.add(x);
        }//if this element is greater than the minimum in the heap, deleteMin and insert this element
        else{
        	if (x.compareTo(heap.peek()) > 0){
        		heap.poll();
        		heap.add(x);
        	}
        }
    }

   
    /**
     * 
     * @return sorted list (largest to smallest) of the k largest elements
     * restores the priority queue to its original state after retrieving the k largest elements
     */
    public List<T> kbest() {
        LinkedList<T> kLargest = new LinkedList<T>();
        //restore the heap by inserting each deleted item into a new heap, then changing the reference 
        //of the original heap at the end
        PriorityQueue<T> restoredHeap = new PriorityQueue<T>();
        //deleteMin() and add it to the head of the linked list to keep the list in descending order
        while (heap.size() > 0){
        	T element = heap.poll();
        	kLargest.addFirst(element);
        	restoredHeap.add(element);
        }
        heap = restoredHeap;
        return kLargest;  
    }

}
