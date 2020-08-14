

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #2 demonstrates ExpressionTree methods on a postfix expression
 *
 */

public class Problem2 {
	
	public static void main(String[] args){
	
		
		String postfixExpr = "15 7 1 1 + - / 3 * 2 1 1 + + -";
		ExpressionTree tree = new ExpressionTree(postfixExpr);
		System.out.println(postfixExpr + "\n");
		System.out.println("expression evaluates to = " + tree.eval());
		System.out.println("postfix expression = " + tree.postfix());
		System.out.println("prefix expression = " + tree.prefix());
		System.out.println("infix expression = " + tree.infix());
		
	}

}
