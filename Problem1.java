import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1 uses an AVL tree (AvlTree.java) to store the words in a file and they lines they
 * appear on.
 */

public class Problem1 {
	
	private AvlTree tree;
	
	
	public Problem1(){	
		tree = new AvlTree();
	}
	
	/**
	 * 
	 * @param name is the String file name of the input file
	 * 
	 * Constructs an AVL tree from the words (in lowercase) in the input file.
	 * Ignores any words lacking alphanumeric characters and strips symbols and punctuation according to 
	 * removeSymPunc(). 
	 * In the tree, each node stores the word and a LinkedList of the line numbers it appears on.
	 */
	public void constructTree(String name) {
		try{
			File file = new File(System.getProperty("user.dir") + "//" + name);
			Scanner scanner = new Scanner(file);
			int lineNum = 0;
			while (scanner.hasNextLine()){
				lineNum++;
				//store the words on this line
				String[] line = scanner.nextLine().split(" ");
				for (int i = 0; i < line.length; i++){
					String word = line[i];
					//convert to lowercase
					word = word.toLowerCase();
					//check if it is a word (has some alphanumeric)
					if (hasOnlySymbols(word)){
						continue;
					}
					//remove punctuation and other symbols from the 1st and last character in word
					word = removeSymPunc(word);
					//insert word into the tree
					tree.insert(word, lineNum);
				}	
			}
		}catch(Exception e){
			System.out.println("File not found");
		}
	}
	
	
	/**
	 * print tree (words and their line numbers) in alphabetical order
	 */
	public void print(){
		tree.printTree();
	}
	
	
	/**
	 * 
	 * @param word is the word to check
	 * @return true if the word has no alphanumeric characters, false otherwise
	 */
	private boolean hasOnlySymbols(String word){
		for (int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			if ((c >= 48 && c <= 57) || (c >= 97 && c <= 122)){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param word is the word to check
	 * @return the word with punctuation in the first and last character removed
	 * 
	 * Any punctuation in text that is not in its own string (eg. " ( ") is typically the first or last 
	 * character in the string (eg. "a, ", "Mr.", etc) so check for punctuation and symbols only in the
	 * first and last character. 
	 */
	private String removeSymPunc(String word){
		//these are the symbols checked for
		String symbols = "()[]{}<>\"';:/\\,.?!";
		if (symbols.indexOf(word.charAt(0)) != -1){
			word = word.substring(1);
		}
		if (symbols.indexOf(word.charAt(word.length() - 1)) != -1){
			word = word.substring(0, word.length() - 1);
		}
		return word;
	}
	
	
	public static void main(String[] args){
		//1 command line argument = file to process
		String fileName = args[0];
		Problem1 test = new Problem1();
		test.constructTree(fileName);
		test.print();
		
	}
	
	

}
