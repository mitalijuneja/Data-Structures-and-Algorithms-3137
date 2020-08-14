import java.io.File;
import java.util.Scanner;
import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.HashMap;


/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #1 demonstrates Huffman encoding
 *
 */

public class Huffman {
	
	private HuffmanNode root;
	private HashMap<Character, String> codes;
	
	
	public Huffman(){
		codes = new HashMap<>();
	}
	
	
	/**
	 * 
	 * @param filename is the name of the input file to be used to generate the Huffman tree
	 * calculate the frequencies of the characters in the input file (accepts all 128 ASCII characters)
	 * uses the algorithm from class to generate a Huffman tree using a priority queue
	 */
	public void createTree(String filename){
		try{ //calculate the frequencies of all characters in the file
			File file = new File(System.getProperty("user.dir") + "//" + filename);
			Scanner scanner = new Scanner(file);
			int[] freq = new int[128];
			while (scanner.hasNextLine()){
				String line = scanner.nextLine();
				for (int i = 0; i < line.length(); i++){
					char c = line.charAt(i);
					freq[c]++;
				}
			}
			
			//for each character that appears in the file (nonzero frequency), create a HuffmanNode and add it
			//to the heap
			PriorityQueue<HuffmanNode> heap = new PriorityQueue<HuffmanNode>(128, new HuffmanCompare());
			for (int i = 0; i < freq.length; i++){
				if (freq[i] != 0){
					HuffmanNode node = new HuffmanNode((char)i, freq[i]);
					heap.add(node);
				}
			}//construct the tree by continuously performing deleteMin() and using these as the children of a new
			//Huffman node
			while (heap.size() > 1){
				HuffmanNode left = heap.poll();
				HuffmanNode right = heap.poll();
				HuffmanNode node = new HuffmanNode((char)0, left.freq + right.freq);
				node.left = left;
				node.right = right;
				heap.add(node);	
			}
			root = heap.poll();
		}catch(Exception e){
			System.out.println("file not found");
		}
	}
	
	
	/**
	 * 
	 * @param encTxt is a String of characters encoded according to this Huffman tree
	 * @return the decoded String represented by encTxt
	 */
	public String decodeText(String encTxt){
		StringBuilder sb = new StringBuilder();
		HuffmanNode n = root;
		try{//traverse the tree according to encTxt and record every leaf reached in the tree, return "Error" if
			//an error occurs
			for (int i = 0; i < encTxt.length(); i++){
				if (encTxt.charAt(i) == '0'){
					n = n.left;
				}
				else{
					n = n.right;
				}
				if (n.left == null && n.right == null){
					sb.append(n.character);
					n = root;
				}
			}
			if (sb.length() == 0 && encTxt.length() != 0){
				return "Error";
			}
			return new String(sb);
		}catch(Exception e){
			return "Error";
		}
	}
	
	
	/**
	 * 
	 * @param decTxt is a String of characters to encode
	 * @return the encoded representation of decTxt according to this Huffman tree
	 */
	public String encodeText(String decTxt){
		StringBuilder sb = new StringBuilder();
		//look up each character in the HashMap storing all codes
		for (int i = 0; i < decTxt.length(); i++){
			 String code = codes.get(decTxt.charAt(i));
			 if (code == null){
				 return "Error";
			 }
			sb.append(code);
		}
		return new String(sb);
	}
	
	
	/**
	 * recursive driver for getCodes(root, code)
	 */
	public void getCodes(){
		System.out.println("char\tcode");
		if (root != null){
			getCodes(root, "");
		}
	}
	
	
	/**
	 * 
	 * @param root is the root of this subtree of the Huffman tree
	 * @param code is the code (path) traversed so far in the Huffman tree
	 * prints the codes in a table format by recursively traversing the tree and printing all of its paths
	 * adds each character and its code to a HashMap for O(1) lookups
	 */
	private void getCodes(HuffmanNode root, String code){
		if (root.left != null){
			getCodes(root.left, code + "0");
		}
		if (root.right != null){
			getCodes(root.right, code + "1");
		}
		if (root.left == null && root.right == null){
			System.out.println(root.character + "\t" + code);
			codes.put(root.character, code);
		}
	}
	
	
	
	/**
	 * 
	 * @author Mitali Juneja
	 * node object for a Huffman tree, each node stores the character and its frequency 
	 *
	 */
	private static class HuffmanNode{
		char character;
		int freq;
		HuffmanNode left;
		HuffmanNode right;
		
		
		public HuffmanNode(char c, int f){
			character = c;
			freq = f;
		}
	}
	
	/**
	 * 
	 * @author Mitali Juneja
	 * comparator for HuffmanNode object compares by each node's frequency attribute (frequency of that character in 
	 * the input file)
	 * defines the min heap ordering condition for the heap used to build the Huffman tree in createTree()
	 */
	private class HuffmanCompare implements Comparator<HuffmanNode>{
		
		public int compare(HuffmanNode node, HuffmanNode other){
			if (node.freq > other.freq){
				return 1;
			}
			if (node.freq <  other.freq){
				return -1;
			}
			return 0;
		}
	}
	
	public static void main (String[] args){
		String filename = args[0];
		Huffman test = new Huffman();
		test.createTree(filename);
		test.getCodes();
		Scanner scanner = new Scanner(System.in);
		System.out.print("Enter encoded text = ");
		String encTxt = scanner.nextLine();
		String decResult = test.decodeText(encTxt);
		if (decResult.equals("Error")){
			System.out.println("Error decoding text");
		}
		else{
			System.out.print("Decoded text = " + decResult);
		}
		
		System.out.print("\nEnter text to encode = ");
		String decTxt = scanner.nextLine();
		String encResult = test.encodeText(decTxt);
		if (encResult.equals("Error")){
			System.out.println("Error encoding text");
		}
		else{
			System.out.print("Encoded text = " + encResult);
		}

	}

}
