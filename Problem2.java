

import java.util.Arrays;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #2 implements recursive binary search
 *
 */

public class Problem2 {
	
	/**
	 * 
	 * @param a is a sorted array in which to search for x
	 * @param x is the element to search for
	 * @return index of x in a if x is found, -1 otherwise
	 */
	public static <T extends Comparable<T>>
		int binarySearch(T[] a, T x){
		
		int beg = 0;
		int end = a.length - 1;
		return binarySearch(a, x, beg, end);
	}
	
	
	private static <T extends Comparable<T>>
		int binarySearch(T[] a, T x, int beg, int end){
		//subarray is marked by its left and right indices (beg, end) in a

		//if there is only one Rectangle left in the subarray to consider, determine if it is the target Rectangle x
		if (end - beg <= 0){
			if (a[beg].compareTo(x) == 0){
				return beg;
			}
			else{
				return -1;
			}
		}
		//calculate the middle index and determine if the Rectangle at the mid is the target Rectangle x
		int mid = beg + (end - beg)/2;
		if (a[mid].compareTo(x) == 0){
			return mid;
		}//otherwise, if the Rectangle at mid is greater than the target Rectangle x, examine the first half of this subarray
		else if (a[mid].compareTo(x) > 0){
			return binarySearch(a, x, beg, mid - 1);
		}//otherwise, the Rectangle at mid must be less than the target Rectangle x, so examine the second half of this subarray
		else{
			return binarySearch(a, x, mid + 1, end);
		}
	}
	
	
	//main() tests Programming #2
	public static void main(String[] args){
		//creates Rectangle[] of random length filled with Rectangles of random size (always includes Rectangle (11,5)
		//to test binary search on this Rectangle
		int numRect = (int)(10 * Math.random()) + 1;
		Rectangle[] arr = new Rectangle[numRect];
		arr[0] = new Rectangle(11, 5);
		for (int i = 1; i < arr.length; i++){
			int length = (int)(20 * Math.random()) + 1;
			int width = (int)(20 * Math.random()) + 1;
			arr[i] = new Rectangle(length, width);
		}
		//sorts the array and prints the sorted array, then finds the index of the target Rectangle (11,20)
		Arrays.sort(arr);
		for (int i = 0; i < arr.length; i++){
			System.out.println("Rectangle" + i + " = length = " + arr[i].getLength() + " width= " + arr[i].getWidth());
		}
		System.out.println("Rectangle(11,5) is at index " + Problem2.binarySearch(arr, new Rectangle(11,5)));
	}

}
