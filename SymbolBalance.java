
import java.io.File;
import java.util.Scanner;
import java.util.Stack;
import java.io.FileNotFoundException;

import java.util.regex.Pattern;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * referenced syntax for Regex Positive Lookbehind from = https://www.regular-expressions.info/lookaround.html
 * 
 * Programming #1 implements a stack based algorithm for symbol balance
 *
 */



public class SymbolBalance {

	private File userFile;
	//store the opening and closing symbols in Strings, with corresponding symbols aligned by index (comments handled separately below)
	private String openSymbols = "{([<\"'";
	private String closeSymbols = "})]>\"'";

	
	public SymbolBalance(String fileName){
		userFile = new File(System.getProperty("user.dir") + "//" + fileName);
	}
	
	
	/**
	 * runs checkSymbols() and prints which character caused an error or if there are no errors in the file
	 * if checkSymbols() throws a FileNotFoundException, then prints this message
	 */
	public void readFile(){
		try{
			String[] result = checkSymbols();
			//if empty String arr returned from checkSymbols(), no balancing errors detected
			if (result.length == 0){
				System.out.println("all symbols are balanced");
			}
			else{
				if (result[1].equals("*")){
					result[1] = "multiline comment";
				}
				System.out.println("line " + result[0] + " error with " + result[1]);
			}
		}catch (Exception e){//handles FileNotFoundException thrown by Scanner in checkSymbols()
			System.out.println("File not found");
		}
	}
	
	
	
	/**
	 * 
	 * @return a String[], if there is an error = length 2 index 0 has error line number, index 1 has error symbol, 
	 * if no error = length 0
	 * @throws FileNotFoundException if the specified file does not exist or cannot be opened
	 * 
	 * uses a stack based algorithm to check for balance among all symbols in openSymbols and closedSymbols instance variables and 
	 * multiple line comments
	 * in the stack, all of these symbols represent themselves except for multiple line comments which are denoted by '*'
	 */
	private String[] checkSymbols() throws FileNotFoundException{
			Scanner scanner = new Scanner(userFile);
			Stack<Character> stack = new Stack<Character>();
			int lineNum = 0;
			
			while (scanner.hasNextLine()){
				lineNum++;
				//checks the file line by line
				String line = scanner.nextLine();
				//if the top of the stack is not currently '*' (not currently in a multiple line comment), then remove any
				//escaped quotes (" inside strings, ' inside chars)
				if (!stack.isEmpty() && stack.peek() != '*'){
					line = removeEscQuotes(line);					
				}
				//iterate through each character in the line
				for (int i = 0; i < line.length(); i++){
					char ch = line.charAt(i);
					//prevents errors with multiple line string or character literals (if we reach the end of the line and are still
					//inside a string, then there is an imbalance with "" or ''
					if (i == line.length() - 1){
						if (!stack.isEmpty() && (stack.peek() == '"' || stack.peek() == '\'')){
							if (ch != stack.peek()){
								return new String[] {lineNum + "", stack.peek() + ""};
							}
						}
					}
					//handles inside a multiple line comment
					if (!stack.isEmpty() && stack.peek() == '*'){
						//if there is a sequence */ then that is the end of the comment
						if (ch == '*'){
							if (i < line.length() - 1 && line.charAt(i + 1) == '/'){
								stack.pop();
							}
						}
					}//detects the start of a multiple line comment 
					else if (ch == '*'){
						//if there is a sequence /*, then push '*' on the stack to indicate that we are now in a multiple line comment
						if (i > 0 && line.charAt(i - 1) == '/'){
							stack.push(ch);
						}
					}//handles inside a string literal
					else if (!stack.isEmpty() && stack.peek() == '"'){
						//if there is another " then this is the end of the string (since all escaped quotes have been removed already)
						if (ch == '"'){
							stack.pop();
						}
					}//handles inside a character literal
					else if (!stack.isEmpty() && stack.peek() == '\''){
						//if there is another ' then this is the end of the character literal (since all escaped quotes have been 
						//removed already)
						if (ch == '\''){
							stack.pop();
						}
					}//handles single line comments
					else if (ch == '/'){
						//if there is a sequence // then skip to the next line
						if (i > 0 && line.charAt(i - 1) == '/'){
							break;
						}
					}//handles opening symbols
					else if (openSymbols.indexOf(ch) != -1){
						//if there is an opening symbol, push it on the stack
						stack.push(ch);
					}//handles closing symbols
					else if (closeSymbols.indexOf(ch) != -1){
						//if the stack is still not empty, pop off the top character, which should be the corresponding opening 
						//character
						if (!stack.isEmpty()){
							char open = stack.pop();
							//if the opening and closing symbols do not match, return the imbalanced symbol
							if (openSymbols.indexOf(open) != closeSymbols.indexOf(ch)){
								return new String[] {lineNum + "", ch + ""};
							}  
						}//if the stack is already empty, return this closing symbol that is imbalanced 
						else{
							return new String[] {lineNum + "", ch + ""};
						}
					}
				}
			}//handles if the stack is not empty at the end of the file (return the top character on the stack which is imbalanced)
			if (!stack.isEmpty()){
				return new String[] {lineNum + "", stack.pop() + ""};
			}
			//if there are no balancing errors in the file, return an empty String[]
			return new String[0];
	}
	
	
	
	/**
	 * 
	 * @param line is the line to remove escaped quotes from
	 * @return the line with no escaped quotes
	 * 
	 * Uses positive lookbehind regex to remove all " that are escaped and all ' that are escaped in the line
	 */
	private String removeEscQuotes(String line){
		//uses a positive lookbehind regex (link used for lookbehind syntax is cited at top of class) to ignore quotes that are preceded
		//by escape characters (since this indicates that the quote is inside either a string or character literal and should be ignored)
		
		//this regex is looking for all double quotes " (Pattern) that are preceded by (?<=) an escape sequence (\\)
		String regex = "(?<=\\\\)" + Pattern.quote("\"");
		//replace all occurrences of the regex with empty String (removes any escaped double quotes)
		line = line.replaceAll(regex, "");
		
		//this regex is looking for all single quotes ' (Pattern) that are preceded by (?<=) an escape sequence(\\)
		regex = "(?<=\\\\)" + Pattern.quote("'");
		//replace all occurrences of the regex with empty String (removes any escaped single quotes)
		line = line.replaceAll(regex, "");
		
		return line;
	}
	
 
	public static void main (String[] args){
		String fileName = args[0];
		SymbolBalance sb = new SymbolBalance(fileName);
		sb.readFile();
		
	}

}
