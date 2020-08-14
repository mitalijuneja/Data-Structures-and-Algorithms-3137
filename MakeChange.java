

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Mitali Juneja (mj2944)
 * Programming #3 recursively determines how many ways to make change given quarters, dimes, nickels 
 *
 */

public class MakeChange {
	//throughout the class, index 0 represents quarters, index 1 = dimes, index 2 = nickels
	private int[] COIN_VALS = {25, 10, 5};
	private String[] COIN_NAMES = {"quarters", "dimes", "nickels"};
	
	/**
	 * 
	 * @param cents is the number of cents to make change for
	 * 
	 * Uses a helper function to get all distinct ways to make change for a given number of cents and prints them
	 */
	public void makeChange(int cents){
		ArrayList<String> combinations = new ArrayList<>();
		int[] coinsUsed = new int[3];
		combinations = makeChange(cents, combinations, coinsUsed);
		//if combinations has size 0, then there are no ways to make change for this number of cents
		if (combinations.size() == 0){
			System.out.println(cents + " can't be changed");
		}//otherwise there is at least one way to make change for this number of cents
		else{
			System.out.println("change for " + cents);
		}
		for (int i = 0; i < combinations.size(); i++){
			//for this combination, parse the frequencies of each coin type (index 0 will be quarters, 1 = dimes, 2 = nickels)
			String[] coinNums = combinations.get(i).split(" ");
			StringBuilder sb = new StringBuilder();
			//match the frequency of each coin with its type so that this information can be printed for all combinations
			for (int j = 0; j < coinNums.length; j++){
				sb.append(coinNums[j] + " " + COIN_NAMES[j] + " ");
			}
			System.out.println(new String(sb)); 
		}
	}
	
	/**
	 * 
	 * @param cents is the number of cents to make change for
	 * @param combinations is an ArrayList storing the combinations for making change that have been determined so far
	 * @param coinsUsed is an array storing the number of coins used of each type in the current combination being created
	 * (index 0 has the number of quarters, index 1 = number of dimes, index 2 = number of nickels)
	 * @return an ArrayList storing all the combinations for making change
	 * 
	 * Helper function that recursively determines all distinct ways to make change for a given number of cents
	 * Each element of combinations AL is a space separated string "X Y Z" representing 1 combination where X = number 
	 * quarters, Y = number dimes, Z = number nickels
	 */
	private ArrayList<String> makeChange(int cents, ArrayList<String> combinations, int[] coinsUsed){
		//one of the base cases - if a recursive call reaches below 0 cents, then the original number of cents cannot be changed so
		//no combination is added to the AL of combinations
		if (cents < 0){
			return combinations;
		}//other base case - if a recursive call reaches 0 cents, then it is a valid way to make change
		if (cents == 0){
			//ensure that this way is not a permutation of another way to make change by comparing its coin frequencies with those
			//of all existing combinations. Only if this is a new combination, add it to the AL of combinations
			String combination = coinsUsed[0] + " " + coinsUsed[1] + " " + coinsUsed[2];
			if (combinations.indexOf(combination) == -1){
				combinations.add(combination);
			}
			return combinations;
		}//for the number of cents passed in, initiate a recursive call that starts by using each of the 3 possible coins (25,10,5)
		//copy coinsUsed and update the frequency of the coin being added for the next recursive call before passing it in
		for (int i = 0; i < coinsUsed.length; i++){
			int[] newCoinsUsed = Arrays.copyOf(coinsUsed, coinsUsed.length);
			newCoinsUsed[i]++;
			combinations = makeChange(cents - COIN_VALS[i], combinations, newCoinsUsed);
		}//return the updated AL combinations which has newly created ways of making change in it
		return combinations;
	}
	
	//tests Programming #3 (uses command line argument)
	public static void main(String[] args){
		int cents = Integer.parseInt(args[0]);
		new MakeChange().makeChange(cents);
	}

}
