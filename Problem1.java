

import java.util.Arrays;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1
 *
 */

public final class Problem1 {
	
	/**
	 * 
	 * @param arr is the array to find the largest Rectangle in
	 * @param areaComp is a Comparator that by area
	 * @param perimComp is a Comparator that evaluates by perimeter
	 * @return the largest Rectangle, evaluated first by area, then by perimeter
	 */
	public static Rectangle findMax (Rectangle[] arr, CompareByArea areaComp, CompareByPerimeter perimComp){
		int maxIdx = 0;
		for (int i = 1; i < arr.length; i++){
			if (areaComp.compare(arr[i], arr[maxIdx]) > 0){
				maxIdx = i;
			}
			if (areaComp.compare(arr[i], arr[maxIdx]) == 0 && perimComp.compare(arr[i], arr[maxIdx]) > 0){
				maxIdx = i;
			}
		}
		return arr[maxIdx];
	}
	
	//main() tests Programming #1
	public static void main(String[] args){
		//creates Rectangle[] of random length filled with Rectangles of random size 
		int numRect = (int)(10 * Math.random()) + 1;
		Rectangle[] arr = new Rectangle[numRect];
		for (int i = 0; i < arr.length; i++){
			int length = (int)(20 * Math.random()) + 1;
			int width = (int)(20 * Math.random()) + 1;
			arr[i] = new Rectangle(length, width);
		}//prints all the Rectangles 
		for (int i = 0; i < arr.length; i++){
			System.out.println("Rectangle" + i + " = length = " + arr[i].getLength() + " width= " + arr[i].getWidth());
		}
		//uses findMax to compare all Rectangles in the array to find the largest one
		Rectangle max = Problem1.findMax(arr, new CompareByArea(), new CompareByPerimeter());
		System.out.println("Max = length = " + max.getLength() + " width = " + max.getWidth());
	}
	

}
