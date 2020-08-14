
import java.util.Comparator;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1 Area Comparator
 *
 */

public class CompareByArea implements Comparator<Rectangle>{
	
	/**
	 * evaluates the Rectangles by area
	 */
	public int compare (Rectangle rect, Rectangle other){
		int rectArea = rect.getLength() * rect.getWidth();
		int otherArea = other.getLength() * other.getWidth();
		
		if (rectArea > otherArea){
			return 1;
		}
		else if (otherArea > rectArea){
			return -1;
		}
		return 0;
	}
		

}
