import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

//ADD LINE NUMBERS OF MISSPELLED WORDS
/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #2 implements a simple spell check on an input file using 2 dictionary files. 
 *
 */

public class Problem2 {
	
	private QuadraticProbingHashTable<String> dictionary;
	//anticipated characters CHARS (standard alphabetical, a number of special characters, apostrophe)
	//(this covers all characters in the sample dictionary file given)
	private String CHARS = "'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyzÅáâäåçèéêíñóôöûü";
	
	
	public Problem2(){
		dictionary = new QuadraticProbingHashTable<String>();	
	}
	
	
	/**
	 * 
	 * @param sDictName is the String file name of the input standard dictionary file
	 * @param pDictName is the String file name of the input personal dictionary file
	 * 
	 * create a hash table (QuadraticProbingHashTable.java) to store the words (in lowercase) 
	 * in the input dictionaries
	 */
	public void loadDictionary(String sDictName, String pDictName){
		try{
			File standardDict = new File(System.getProperty("user.dir") + "//" + sDictName);
			File personalDict = new File(System.getProperty("user.dir") + "//" + pDictName);
			Scanner scanner = new Scanner(standardDict);
			while (scanner.hasNext()){
				String word = scanner.next();
				dictionary.insert(word.toLowerCase());
			}
			scanner = new Scanner(personalDict);
			while(scanner.hasNext()){
				String word = scanner.next();
				dictionary.insert(word.toLowerCase());
			}
		}catch(Exception e){
			System.out.println("dictionary files not found");
		}
	}
	
	/**
	 * 
	 * @param fileName is the String file name of the input file 
	 * 
	 * Chekcs the words in the input file for their spelling by matching to a dictionary. Removes
	 * punctuation according to removePunct(). 
	 * Prints 3 types of suggestions for misspelled words (word that do not appear in the dictionary)
	 */
	public void checkFile(String fileName){
		try{
			File file = new File(System.getProperty("user.dir") + "//" + fileName);
			Scanner scanner = new Scanner(file);
			int lineNum = 0;
			while (scanner.hasNextLine()){
				//split by line to keep track of line numbers
				String[] line = scanner.nextLine().split(" ");
				lineNum++;
				for (int i = 0; i < line.length; i++){
					String word = removePunct(line[i]);
					//ignore any words lacking alphabetical characters (eg. numbers)
					if (!isAlpha(word)){
						continue;
					}//print suggestions for misspelled words
					if (!dictionary.contains(word.toLowerCase())){
						System.out.println(word + " (line " + lineNum + ")");
						System.out.println("\t suggestions = ");
						System.out.println("\t add one character = " + canAddChar(word.toLowerCase()));
						System.out.println("\t remove one character = " + canRmvChar(word.toLowerCase()));
						System.out.println("\t exchange adjacent characters = " + canSwapAdjChars(word.toLowerCase()));
					}
				}
			}
		}catch(Exception e){
			System.out.println("spell check file not found");
		}
	}
	
	
	/**
	 * 
	 * @param word is the misspelled word
	 * @return a String [space separated list of all dictionary entries formed by adding 1 character 
	 * to param word, otherwise preserving the order of characters]
	 */
	private String canAddChar(String word){
		StringBuilder suggestions = new StringBuilder();
		int[] fileChars = new int[CHARS.length()];
		int[] dictChars = new int[CHARS.length()];
		//create array that tracks the frequency of all characters in misspelled word (array idx = CHARS idx)
		for (int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			fileChars[CHARS.indexOf(c)]++;
		}//use the iterator implemented for QuadraticProbingHashTable to traverse through the dictionary
		java.util.Iterator<String> iter = dictionary.iterator();
		while (iter.hasNext()){
			String dictWord = iter.next();
			//skip dictionary words without length 1 longer than misspelled word (not possible suggestion)
			if (word.length() + 1 != dictWord.length()){
				continue;
			}
			//create array that tracks the frequency of all characters in dictionary word (array idx = CHARS idx)
			for (int i = 0; i < dictWord.length(); i++){
				char c = dictWord.charAt(i);
				dictChars[CHARS.indexOf(c)]++;
			}
			int diff = 0;
			//track the number of differences in frequency of each character between the 2 words
			for (int i = 0; i < fileChars.length; i++){
				diff += Math.abs(dictChars[i] - fileChars[i]);
			}//if there is only 1 difference between the words and they have same relative order of characters, add
			//the dictionary word as a suggestion
			if (diff == 1  && hasRelOrder(word, dictWord)){
				suggestions.append(dictWord + " ");
			}//clear the dictionary frequency array for the next dictionary word
			dictChars = new int[CHARS.length()];
		}
		return new String(suggestions);	
	}
	
	/**
	 * 
	 * @param word is the misspelled word
	 * @return a String [space separated list of all dictionary entries formed by removing 1 character 
	 * to param word, otherwise preserving the order of characters]
	 */
	private String canRmvChar(String word){
		StringBuilder suggestions = new StringBuilder();
		int[] fileChars = new int[CHARS.length()];
		int[] dictChars = new int[CHARS.length()];
		//create array that tracks the frequency of all characters in misspelled word (array idx = CHARS idx)
		for (int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			fileChars[CHARS.indexOf(c)]++;
		}//use the iterator implemented for QuadraticProbingHashTable to traverse through the dictionary
		java.util.Iterator<String> iter = dictionary.iterator();
		while (iter.hasNext()){
			String dictWord = iter.next();
			//skip dictionary words without length 1 shorter than misspelled word (not possible suggestion)
			if (word.length() - 1 != dictWord.length()){
				continue;
			}
			//create array that tracks the frequency of all characters in dictionary word (array idx = CHARS idx)
			for (int i = 0; i < dictWord.length(); i++){
				char c = dictWord.charAt(i);
				dictChars[CHARS.indexOf(c)]++;
			}
			int diff = 0;
			//track the number of differences in frequency of each character between the 2 words
			for (int i = 0; i < fileChars.length; i++){
				diff += Math.abs(dictChars[i] - fileChars[i]);
			}//if there is only 1 difference between the words and they have same relative order of characters, add
			//the dictionary word as a suggestion
			if (diff == 1 && hasRelOrder(word, dictWord)){
				suggestions.append(dictWord + " ");
			}//clear the dictionary frequency array for the next dictionary word
			dictChars = new int[CHARS.length()];
		}
		return new String(suggestions);	
	}
	
		
	/**
	 * 
	 * @param word is the misspelled word
	 * @return a String [space separated list of all dictionary entries formed by swapping any number of
	 * adjacent characters in param word]
	 */
	private String canSwapAdjChars(String word){
		StringBuilder suggestions = new StringBuilder();
		//use the iterator implemented for QuadraticProbingHashTable to traverse through the dictionary
		java.util.Iterator<String> iter = dictionary.iterator();
		while (iter.hasNext()){
			//use prevBeg and prevEnd to track the beginning and end indices of the last 2 characters that
			//were swapped (prevents swapping 2 characters and then swapping them back)
			int prevBeg = -1;
			int prevEnd = -1;
			String dictWord = iter.next();
			String wordCpy = word;
			//test swapping the last 2 characters (store result in lastSwp)
			String lastSwp = wordCpy.substring(0, wordCpy.length() - 2) + wordCpy.charAt(wordCpy.length() - 1) + wordCpy.charAt(wordCpy.length() - 2);
			//if lastSwp matches dictWord, store it as a suggestion and continue to the next word
			if (lastSwp.equals(dictWord)){
				suggestions.append(dictWord + " " );
				continue;
			}//check for equal lengths in the 2 words (otherwise it is not a possible suggestion)
			if (wordCpy.length() == dictWord.length()){
				//iterate through the characters in dictWord checking for possible swaps in the misspelled word
				for (int i = 0; i < wordCpy.length(); i++){
					//use begIdx and endIdx to create the substring of the misspelled word that contains
					//all characters adjacent to the one being checked
					int begIdx = i - 1;
					int endIdx = i + 2;
					//adjust for potential index out of bounds error
					if (begIdx < 0){
						begIdx = 0;
					}
					if (endIdx > wordCpy.length()){
						endIdx = wordCpy.length();
					}
					String substr = wordCpy.substring(begIdx, endIdx);
					char c = dictWord.charAt(i);
					//if substring does not have correct character from dictWord, swap is not possible
					// so skip to next word
					if (substr.indexOf(c) == -1){
						break;
					}//if the index of the correct character is not within 1 (not adjacent), swap is not
					//possible so skip to next word
					else if (Math.abs(dictWord.indexOf(c, begIdx) - wordCpy.indexOf(c)) > 1){
						break;
					}
					//if the correct character is one away, then perform the swap 
					else if (i != wordCpy.indexOf(c, begIdx)){
						//find the beginning and end indices of the 2 characters to swap
						int beg = Math.min(i, begIdx + substr.lastIndexOf(c));
						int end = Math.max(i, begIdx + substr.lastIndexOf(c));
						//if these are the same as the last swap then we are repeating swaps to match 
						//dictWord (i.e. we are double counting 1 character), swap is not
						//possible so skip to next word
						if (prevBeg == beg && prevEnd == end){
							break;
						}//perform the swap and store the beg, end in prevBeg, prevEnd
						wordCpy = wordCpy.substring(0, beg) + reverse(wordCpy.substring(beg, end+1)) + wordCpy.substring(end + 1);
						prevBeg = beg;
						prevEnd = end;
					}//if we swap and reach dictWord, then store it as a suggestion and continue to next word
					if (dictWord.equals(wordCpy)){
						suggestions.append(dictWord + " ");
						break;
					}//if we reach the end of the word, and are still valid for swapping, then 
					//swap is possible so store dictWord as a suggestion
					if (i == wordCpy.length() - 1){
						suggestions.append(dictWord + " ");
					}
				}
			}
		}
		return new String(suggestions);	
	}
	

	/**
	 * 
	 * @param word is the misspelled word
	 * @param suggestion is the suggestion from dictionary 
	 * @return true if word and suggestion have the same relative order of characters, otherwise false
	 */
	private boolean hasRelOrder(String word, String suggestion){
		String shorter;
		String longer;
		int sIdx = 0;
		int lIdx = 0;
		if (word.length() < suggestion.length()){
			shorter = word;
			longer = suggestion;
		}
		else{
			shorter = suggestion;
			longer = word;
		}//iterate through the 2 words comparing characters
		while (sIdx < shorter.length() && lIdx < longer.length()){
			//if the characters are not the same, increment index for the longer word only
			//(this accounts for the fact that suggestion either has an extra character or is missing one)
			if (shorter.charAt(sIdx) != longer.charAt(lIdx)){
				lIdx++;
			}//otherwise, increment for both
			else{
				sIdx++;
				lIdx++;
			}
		}//if we reach the end of both words, then they have same relative order
		if (sIdx == shorter.length() && lIdx == longer.length()){
			return true;
		}//if we reach the end of shorter and are one away from end of longer, then they have
		//same relative order
		if (lIdx + 1 == longer.length() ){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param word is the string to reverse
	 * @return the reversed string
	 */
	private String reverse(String word){
		StringBuilder rev = new StringBuilder();
		for (int i = word.length() - 1; i >= 0; i--){
			rev.append(word.charAt(i));
		}
		return new String(rev);
	}
	
	
	/**
	 * 
	 * @param word is the word to check
	 * @return true if the word has any alphabetical characters, false otherwise
	 */
	private boolean isAlpha(String word){
		for (int i = 0; i < word.length(); i++){
			char c = word.charAt(i);
			if (CHARS.indexOf(c) == -1){
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
	private String removePunct(String word){
		String edited = word;
		//these are the symbols checked for 
		String punctuation = "()[]{}<>\"';:/\\,.?!";
		if (punctuation.indexOf(edited.charAt(0)) != -1){
			edited = edited.substring(1);
		}
		if (punctuation.indexOf(edited.charAt(edited.length() - 1)) != -1){
			edited = edited.substring(0, edited.length() - 1);
		}
		return edited;
	}
	
	
	
	public static void main(String[] args){
		String standardDict = args[0];
		String personalDict = args[1];
		String file = args[2];
		Problem2 test = new Problem2();
		test.loadDictionary(standardDict, personalDict);
		test.checkFile(file);
		
		
	}

}
