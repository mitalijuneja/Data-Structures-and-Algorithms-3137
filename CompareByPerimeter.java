
import java.util.Comparator;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1 Perimeter Comparator
 *
 */

public class CompareByPerimeter implements Comparator<Rectangle> {

	/**
	 * evaluates the Rectangles by perimeter
	 */
	public int compare(Rectangle rect, Rectangle other){
		int rectPerim = 2*rect.getLength() + 2*rect.getWidth();
		int otherPerim = 2*other.getLength() + 2*other.getWidth();
		
		if (rectPerim > otherPerim){
			return 1;
		}
		else if (otherPerim > rectPerim){
			return -1;
		}
		return 0;	
	}	
}
