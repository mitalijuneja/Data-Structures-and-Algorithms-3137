
import java.util.Stack;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #2 implements an Expression Tree and its traversal methods
 *
 */
public class ExpressionTree {
	
	ExpressionNode root;
	
	
	/**
	 * 
	 * @param postfixExpr is the postfix expression that represents this expression tree
	 * 
	 * Constructs the expression tree for this postfix expression using the algorithm described in class (for 
	 * operands, constructs a node and pushes them onto the stack. For operators, constructs a node and pops 2 nodes off of 
	 * the stack and uses those as the operands. Then, pushes the the node onto the stack).
	 */
	public ExpressionTree(String postfixExpr){
		Stack<ExpressionNode> stack = new Stack<ExpressionNode>();
		String[] tokens = postfixExpr.split(" ");
		for (int i = 0; i < tokens.length; i++){
			try{
				int operand = Integer.parseInt(tokens[i]);
				ExpressionNode node = new ExpressionNode(operand);
				stack.push(node);
				
			}catch(Exception e){
				char operator = tokens[i].charAt(0);
				ExpressionNode node = new ExpressionNode(operator);
				node.right = stack.pop();
				node.left = stack.pop();
				stack.push(node);
			}
		}
		root = stack.pop();
	}
	
	/**
	 * 
	 * @return the result of evaluating this expression tree
	 */
	public int eval(){
		return eval(root);
	}
	
	/**
	 * 
	 * @return the postfix expression representing this expression tree
	 */
	public String postfix(){
		return postfix(root, "");
	}
	
	
	/**
	 * 
	 * @return the prefix expression representing this expression tree
	 */
	public String prefix(){
		return prefix(root, "");
	}
	
	
	/**
	 * 
	 * @return the infix expression representing this expression tree
	 */
	public String infix(){
		return infix(root, " ( ");
	}
	
	
	/**
	 * 
	 * @param root is the root of the expression subtree currently being evaluated
	 * @return the result of evaluating this expression tree
	 * 
	 * Recursive helper function that evaluates the expression tree using its postorder traversal (postfix expression)
	 */
	private int eval(ExpressionNode root){
		// base case for leaf, return the operand
		if (!root.isOperator){
			return root.data;
		}
		int left = eval(root.left);
		int right = eval(root.right);
		//evaluate using the root (operator) and the results of the left and right subtrees (operands)
		return applyOperator(root.operator, left, right);
	}

	
	/**
	 * 
	 * @param root is the root of the expression subtree currently being evaluated
	 * @param postfixExpr is the string representing the postfix expression for the tree
	 * @return the postfix expression representing this expression tree
	 * 
	 * Recursive helper function that creates the postfix expression using a postorder traversal
	 */
	private String postfix(ExpressionNode root, String postfixExpr){
		//base case for leaf, return the current expression and then this operand
		if (!root.isOperator){
			return postfixExpr + " " + root.data;
		}
		postfixExpr = postfix(root.left, postfixExpr);
		postfixExpr = postfix(root.right, postfixExpr);
		//add the root (operator) to the postfix expression
		postfixExpr += " " + root.operator;
		return postfixExpr;
	}
	
	
	
	/**
	 * 
	 * @param root is the root of the expression subtree currently being evaluated
	 * @param prefixExpr is the string representing the prefix expression for the tree
	 * @return the prefix expression representing this expression tree
	 * 
	 * Recursive helper function that creates the prefix expression using a preorder traversal
	 */
	private String prefix(ExpressionNode root, String prefixExpr){
		// base case for leaf, return the current expression and then this operand
		if (!root.isOperator){
			return prefixExpr + " " + root.data;
		}
		//add the root (operator) to the prefix expression
		prefixExpr += " " + root.operator;
		prefixExpr = prefix(root.left, prefixExpr);
		prefixExpr = prefix(root.right, prefixExpr);
		return prefixExpr;
	}
	
	
	/**
	 * 
	 * @param root is the root of the expression subtree currently being evaluated
	 * @param infixExpr is the string representing the infix expression for the tree
	 * @return the infix expression representing the expression tree
	 * 
	 * Recursive helper function that creates the infix expression using an inorder traversal
	 */
	
	private String infix(ExpressionNode root, String infixExpr){
		// base case for leaf, return the current expression and then this operand
		if (!root.isOperator){
			return infixExpr + " " + root.data;
		}
		//if the left child is an operator (not a leaf) open parenthesis
		if (root.left.isOperator){
			infixExpr += " ( ";
		}
		infixExpr = infix(root.left, infixExpr);
		// add the root (operator) to the infix expression
		infixExpr += " " + root.operator;
		//if the right child is an operator (not a leaf) open parenthesis
		if (root.right.isOperator){
			infixExpr += " ( ";
		}
		infixExpr = infix(root.right, infixExpr);
		//close parenthesis on this expression
		return infixExpr + " ) ";
	}
	
	
	/**
	 * 
	 * @param operator
	 * @param left is the left operand
	 * @param right is the right operand
	 * @return result of evaluating left operator right
	 */
	private int applyOperator(char operator, int left, int right){
		if (operator == '+'){
			return left + right;
		}
		if (operator == '-'){
			return left - right;
		}
		if (operator == '*'){
			return left * right;
		}
		return left / right;

	}
	
	
	
	
	private static class ExpressionNode{
		int data;
		char operator;
		boolean isOperator;
		ExpressionNode left;
		ExpressionNode right;
		
		public ExpressionNode(int value){
			data = value;
		}
		
		public ExpressionNode(char op){
			operator = op;
			isOperator = true;
		}
	}

}
