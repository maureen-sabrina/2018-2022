/*
 * Maureen Lynch
 * Spring 2020
 */
import java.util.ArrayList;
import java.util.Random;


public class Lab5Exercises {

	public static void main(String[] args) {
		ArrayList<Integer> randomNumList = new ArrayList<>();
		Random generator = new Random();
		for(int i=0; i<20; i++) {
			randomNumList.add(i, (generator.nextInt(6) + 1));
		}
		System.out.println(longestRun(randomNumList));
		System.out.println();
		bulgarianSolitare();
	}
	
	
	// Part 1: Longest Run
	// PC:
	//	- Create an ArrayList with 20 random integers, each representing a random die
	//	toss and ranging from 1-6
	// 	- Set variable for longest run to zero and set a variable for current run to 1. 
	//	Set variables for tracking index of the longest run's start and end.
	//	- Loop through the ArrayList, and create instance variables currentDie and
	//		nextDie, equivalent to the current ArrayList index value and the next one.
	//		- Every time you loop, check if the current die number is the same as the 
	//		next die number in the list. If it is, increase currentRun by one. Else,
	//		set the currentRun back to one. Last, compare currentRun to LongestRun
	//		if currentRun is longer than longest run, set longestRun to currentRun 
	//		and longestDie to currentDie, and also set the index variables to the 
	//		current index.
	//	- Return longestDie and longestRun
	
	public static String longestRun(ArrayList<Integer> randomNumList) {
		//ArrayList<Integer> randomNumList = new ArrayList<>();
		//Random generator = new Random();
		//for(int i=0; i<20; i++) {
		//	randomNumList.add(i, (generator.nextInt(6) + 1));
		//}
		int longestRun 		= 0;
		int currentRun 		= 1;
		int longestRunIndexStart = 0;
		int longestRunIndexEnd   = 0;
		for(int i=0; i<20-1; i++) {
			int currentDie = randomNumList.get(i);
			int nextDie    = randomNumList.get(i+1);
			if (currentDie == nextDie) {
				currentRun++;
			}
			else {
				currentRun = 1;
			}
			if(currentRun>longestRun) {
				longestRun 		     = currentRun;
				longestRunIndexStart = i-currentRun+2;
				longestRunIndexEnd   = i+1;
			}
		}
		String endRandomNumList = "";
		for(int i=0; i<20; i++) {
			if(i == longestRunIndexStart) {
				endRandomNumList = endRandomNumList + "(" + randomNumList.get(i) + " ";
			}
			else if(i == longestRunIndexEnd) {
				endRandomNumList = endRandomNumList + randomNumList.get(i) + ") ";
			}
			else {
				endRandomNumList = endRandomNumList + randomNumList.get(i) + " ";
			}
		}
		return endRandomNumList;
	}
	
	
	// Part 2: Bulgarian Solitare -- CHECK IF THE GAME IS PLAYED RIGHT
	// PC: 
	//	- Generate starting decks
	//	- Play the game by taking away cards from current decks and creating a new
	//	deck each turn (each loop)
	//		- during each loop, check for the end requirements (has decks with sizes
	//		1-9). If it does, change hasOneThroughNine to true
	//	- print end result
	
	public static ArrayList<Integer> bulgarianSolitare() {
		
		System.out.println("-------------------------------");
		System.out.println("Let's play Bulgarian Solitare!");
		System.out.println("-------------------------------");
		System.out.println();
		ArrayList<Integer> cardDistributions = new ArrayList<>();
		Random generator = new Random();
		int cardsLeft = 45;
		
		// Generate the starting decks
		
		for(int i=0; i<=4; i++) {
			int currentDeck = generator.nextInt(cardsLeft);
			cardDistributions.add(i,currentDeck);
			cardsLeft = cardsLeft - currentDeck;
		}
		cardDistributions.add(5, cardsLeft);
		System.out.println(cardDistributions);
		
		// Play the game
		
		Boolean hasOneThroughNine = false; 
		while(!hasOneThroughNine) {
			int currentTax = 0;
			for(int i=0; i<cardDistributions.size(); i++) {
				if(cardDistributions.get(i) == 0) {
					cardDistributions.remove(i);
					i--;
				}
				else {
					cardDistributions.set(i, cardDistributions.get(i) -1);
					currentTax++;
				}
			}
			cardDistributions.add(currentTax);
			System.out.println(cardDistributions);
			
			// here is where we check if its oneThroughNine--if it is, we stop the 
			// while loop by changing hasOneThroughNine to true. If it isn't, we 
			// keep going by not changing hasOneThroughNine
						
			if(cardDistributions.size() == 9) {
				if(cardDistributions.contains(1) 
						&& cardDistributions.contains(2)
						&& cardDistributions.contains(3)
						&& cardDistributions.contains(4)
						&& cardDistributions.contains(5)
						&& cardDistributions.contains(6)
						&& cardDistributions.contains(7)
						&& cardDistributions.contains(8)
						&& cardDistributions.contains(9)) 
				{
					hasOneThroughNine = true;
				}
			}
		}
		System.out.println("-------------------------------");
		return cardDistributions;
	}
	
	
	// Part 3: Matrix Library --> See other class
}
















