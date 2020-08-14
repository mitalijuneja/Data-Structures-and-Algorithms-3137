

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1
 *
 */
public class Rectangle implements Comparable<Rectangle>{
	private int length;
	private int width;
	
	
	public Rectangle(int l, int w){
		length = l;
		width = w;
	}
	
	public int getLength(){
		return length;
	}
	
	public int getWidth(){
		return width;
	}
	
	
	/**
	 * compares this Rectangle to other by area (so that binarySearch() can be tested on Rectangle[])
	 */
	public int compareTo(Rectangle other){
		int thisArea = getLength() * getWidth();
		int otherArea = other.getLength() * other.getWidth();
		if (thisArea > otherArea){
			return 1;
		}
		if (thisArea < otherArea){
			return -1;
		}
		return 0;
	}
	
	
}
