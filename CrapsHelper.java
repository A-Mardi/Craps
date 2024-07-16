/**
 *Program Name:		CrapsHelper.java
 *Purpose:			This method contains helper methods and to be called from
 *					the main method Craps.java
 *@author			Ahmed Ibrahim, 1182926
 *Date:				12 Mar 2024 
 */

import java.util.Scanner;

public class CrapsHelper
{
	
	/*Declaring Magic numbers and the scanner object and variables that will be available to use
	for all the methods below*/
	final static int ANTE = 100;
	final static int MIN_BET = 10;
	public static Scanner input = new Scanner(System.in);
	public static int actionAmount = 0;
	public static boolean winOrLose = false; //Declare boolean WinOrLose variable and make it available for
											 //both the checkDiceRoll() & adjustBankBalances() methods
	
	
	/* Prompts the user for number of player then validates it is between 2 and 6.
	 * Then prompts the user for names and stores them in playerArray of the same size as the number  */
	public static String [] createPlayerArray()
	{
		System.out.print("Enter the number of players for this game (minimum of 2 to maximum of 6): ");
		int num = input.nextInt();
		while(num > 6 || num < 2)
		{
			System.out.print("Error, invalid entry. Minimum of 2 players to Maximum of 6: ");
			num = input.nextInt();
			input.nextLine();
		}
		
		String[] playerArray = new String[num];
		for(int i = 0; i<playerArray.length; i++)
		{
			System.out.printf("Enter first name of player# %d and press ENTER: ", i + 1);
		    playerArray[i] = input.next();
		}
		
		return playerArray;
	}
	
	
	/* Creates a bankRollArray of the same size as the playerArray size to be passed as an argument
	 * in the main method*/
	public static int[] createBankRollArray(int num)
	{
		int[] bankRollArray = new int[num];
		for(int i = 0; i < bankRollArray.length; i++)
		{
			bankRollArray[i] = ANTE;
		}
		return bankRollArray;
	}
	
	
	/* Prints a short and brief overview of the rules of the game of craps.*/
	public static void showRules()
	{
		System.out.println();
		System.out.println("				==============				");
		System.out.println("In craps, players start with a \"come-out roll.\" Rolling a 7 or 11 wins, while 2, 3, or 12 loses."
				+ " \nOther numbers set a \"point.\" The shooter aims to hit the point again before rolling a 7,"
				+ " \nrolling a 7 ends the turn and results in a loss, \"sevening out,\" passing the dice.");
		System.out.println("				==============				");
	}
	
	
	/* Prompts the shooter for a bet, validates it is an exact multiple of 10, larger than 10 and
	 * up to the shooter's bank balance. Then updates the actionAmount by the bet and then print it to the screen.*/
	public static void getShooterBet(String[] playerArray, int shooterID, int[] bankRollArray, int Min_Bet, int[] betAmmountArray)
	{
//		actionAmount = 0;
		System.out.printf("You have $%d in your bank roll and minimum bet is $%d. Enter your bet amount: ", bankRollArray[shooterID], MIN_BET);
		int shooterBet = input.nextInt();
		while(!(shooterBet >= MIN_BET && shooterBet % MIN_BET == 0 && shooterBet <= bankRollArray[shooterID]))
		{
			System.out.print("Bet must be at least $10, exact multiple of 10 and up to your bank balance: ");
			shooterBet = input.nextInt();
		}
		actionAmount = shooterBet;
		betAmmountArray[shooterID] = shooterBet;
		System.out.printf("\nThe Action Amount Before Oppnents' bets is: %d\n", actionAmount);
		System.out.println();
	}
	
	
	/* Prompts the opponents for their bets and validates it is an exact multiple of 10, larger than and
	 * up to the actionAmount or the players' bank balances and prints the bets to the screen.*/
	public static void getOpponentBet(String[] playerArray, int shooterID, int[] bankRollArray, int minBet, int[] betAmountArray)
	{
		//Assign every value in betAmountArray to zero before all players make their bets
		for(int i = 0; i < betAmountArray.length; i++)
		{
			betAmountArray[i] = 0;
		}
		//Initializing variables
		int i = 1;
		int oppBet = 0;
		for(i = 0; i < playerArray.length; i++)
		{
			if(i == shooterID || bankRollArray[i] < MIN_BET)
			{
				continue;
			}
			if(actionAmount < MIN_BET)
			{
				System.out.println("The action is covered, no more bets can be made");
				break;
			}
			
			System.out.printf("%s, how much of the action do you want?\r\n"
							+ "Enter your bet, minimum of $%d up to $%d, or your bank balance, whichever is less:", playerArray[i], MIN_BET, actionAmount);
				oppBet = input.nextInt();
				while(!(oppBet >= MIN_BET && oppBet <= actionAmount && oppBet % MIN_BET == 0 
								&& oppBet <= bankRollArray[i]))
				{
					System.out.printf("Bet must be at least $10, exact multiple of 10 "
								+ "and minimum of $%d up to $%d, or your bank balance, whichever is less: ", 
								MIN_BET, actionAmount );
					oppBet = input.nextInt();
				}
			System.out.println();
			actionAmount -= oppBet;
			betAmountArray[i] = oppBet;    	   
		}
		int totalOppBets = 0;
		for (int j = 0; j < playerArray.length; j++) 
		    {
				if(j == shooterID)
				{
					continue;
				}
		        totalOppBets += betAmountArray[j];
		    }
	    actionAmount = totalOppBets;
		System.out.println();
	}
	
	
	/*Returns a random integer between 2 and 12 inclusive.*/
	public static int rollDice()
	{
		int dice = 2 + (int)(Math.random() * ((12 - 2) + 1));
		return dice;
	}
	
	
	/* Takes the diceRoll, playerArray names, shooter ID, bankRollArray and betAmountArray as parameters.
	 * Then checks if the shooter won or lost the pass based on the rules of craps. Then prints the win or loss
	 * according to the outcome of the diceRoll.*/
	public static void checkDiceRoll(int diceRoll, String[] playerArray, int shooterID, int[] bankRollArray, int[] betAmountArray)
	{
		//boolean winOrLose = false;
		if(diceRoll == 7 || diceRoll == 11)
		{
			winOrLose = true;
			System.out.printf("Congratulations %s, You have rolled a natural. You win!\n", playerArray[shooterID]);
		}
		else if(diceRoll == 2 || diceRoll == 3 || diceRoll == 12)
		{
			winOrLose = false;
			System.out.printf("Sorry %s, You have crapped out. You lose!\n", playerArray[shooterID]);
		}
		else 
		{
			System.out.printf("OK %s, your point is %d. To win, you need to roll your point again, but if you roll a 7, you lose.\n", playerArray[shooterID], diceRoll);
			System.out.println("Rolling the dice again to try for your point...");
			int newRoll = rollDice();

			while(newRoll != diceRoll && newRoll != 7)
			{
				newRoll = rollDice();
				System.out.printf("Rolling...you rolled a %d\n", newRoll);
			}
			if(newRoll == 7)
			{
				winOrLose = false;
				System.out.printf("Sorry %s, You rolled a 7. You lose!\n", playerArray[shooterID]);
			}
			else
			{
				winOrLose = true;
				System.out.printf("Congratulations %s! You rolled your point! You win!\n", playerArray[shooterID]);
			}
		}
		System.out.println();
	}
	
	
	/* Takes bankRollArray, betAmountArray and shooterID as parameters.
	 * Adjusts bank balances of shooter and opponent based on the result of the pass.*/
	public static void adjustBankBalances(int[] bankRollArray, int[] betAmountArray, int shooterID)
	{

		if(winOrLose)
		{
			for(int i = 0; i < bankRollArray.length; i++)
			{
				if (i == shooterID) 
	            {
	                bankRollArray[i] += actionAmount;  
	            } 
				else 
	            {
					if (betAmountArray[i] <= bankRollArray[i]) 
		            {
		                bankRollArray[i] -= betAmountArray[i];
		            }
		            else
		            {
		                bankRollArray[i] = 0; 
		            }  
	            }
			}
		}
		else
		{
			for(int i = 0; i < bankRollArray.length; i++)
	        {
	            if (i == shooterID) 
	            {
	                if (actionAmount <= bankRollArray[i]) 
	                {
	                    bankRollArray[i] -= actionAmount;  
	                }
	                else
	                {
	                    bankRollArray[i] = 0; 
	                }
	            } 
	            else 
	            {
	                bankRollArray[i] += betAmountArray[i];  
	            }
	        }
		}		
	}
	
	
	/* Takes the playerArray, bankRollArray as parameters.
	 * Prints the player's bank rolls to the screen based on the value stored in the bankRollArray.*/
	public static void printPlayerBankBalances(String[] playerArray, int[] bankRollArray)
	{
		System.out.println("After this pass, here are the bankroll balances for everyone:");
		for(int i = 0; i < playerArray.length; i++)
		{
			System.out.printf("%s has $%d.\n", playerArray[i], bankRollArray[i]);
		}
		System.out.println();
		
	}
	
	
	/* Takes the bankRollArray and the totalMoney integer as parameters.
	 * check if one player has all the money in the game if so returns a true value, if not a false value.*/
	public static boolean checkForWinnner(int[] bankRollArray, int totalMoney)
	{
		for (int balance : bankRollArray) 
		{
	        if (balance >= totalMoney) 
	        {
	            return true;   
	        }
	    }
	    return false;
	}
	
	
	/* Takes the shooterID and bankRollArray as parameters.
	 * returns the next shooter ID based on the current shooter ID.*/
	public static int getNextShooter(int shooterID, int[] bankRollArray)
	{
		int nextShooterID = (shooterID + 1) % bankRollArray.length;
		
		while(bankRollArray[nextShooterID] <= 0)
		{
			nextShooterID = (nextShooterID + 1) % bankRollArray.length;
		}
		
		return nextShooterID;
	}
	
	
	/* Takes the playerArray, bankRollArray and the totalMoney integer as parameters.
	 * check if one player has all the money in the game if so returns the name of the player
	 * that has all the totalMoney in his bankRoll, if not returns a null.*/
	public static String identifyWinner(String[] playerArray, int[] bankRollArray, int totalMoney)
	{
		for (int i = 0; i < bankRollArray.length; i++) 
		{
	        if(bankRollArray[i] == totalMoney)
	        {
	        	return playerArray[i];
	        }
	    }
		return null;
	}
}
//End of class