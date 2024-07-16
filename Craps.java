/**
 *Program Name:		Craps.java
 *Purpose:			To write a program simulating the game of Craps.
 *@author			Ahmed Ibrahim, 1182926
 *Date:				1 Apr 2024 
 */

//PseudoCode
/* 1. Print the intro to the game
 * 2. Initialize playerNames, bankRolls, betAmounts, MIN_BET, ANTE, and gameOver &
 *    call helper methods rollDice(), checkDiceRoll(), and adjustBankBalances().
 * 3. Prompt the user to enter the number of players and their names &
      Print a brief explanation of the game rules if requested.
 * 4. Set shooterID to the first player.
 * 	  Start a while loop until gameOver is true.
      print the current shooter and their bank roll.
 * 5. Call Helper methods from the helper class to prompt the shooter & opponents to
 * 	  enter their bets.
 * 6. Roll the dice using the rollDice() method and then call the checkDiceRoll() 
 *    method to check the winner of the pass.
 * 7. call the method adjustBankBalance() to adjust bank balances of the players &
 *    call the printBankBalance() method to print balances on the screen.
 * 8. Check to see if the bank roll of the shooter is 0, if so, the shooter then become
 *    the next player through the getNextShooter() method, if not continue the program.
 * 9. Check if the sum of players' ANTES in the games is equal to one of the players' bank roll
 *    using the checkForWinner() method, if so print the winner to the screen and exit the loop,
 *    if not continue.
 * 10.Prompt the user if he wishes to continue being the shooter or pass the dice, if the shooter
 *    passes the dice, call the getNextShooter() method, if not continue 
 * 11.Continue until loop is exited.
 * 
 */

import java.util.Scanner;

public class Craps
{
	public static void main(String[] args)
	{
		//Declare Constants and create scanner object
		final int ANTE = 100;
		final int MIN_BET = 10;
		Scanner input = new Scanner(System.in);
		
		//Print Introduction
		System.out.println("Welcome to Casino Fanshawe! The game here is craps, so we need to get some information about your party...");
		System.out.println();
		
		//Declare main variables & Arrays and assign them to helper methods in the helper class
		boolean gameOver = false;
		String[] playerNames = CrapsHelper.createPlayerArray();	
		int[] bankRolls = CrapsHelper.createBankRollArray(playerNames.length);
		int[] betAmounts = new int[playerNames.length];		
		
		//Print names of the players to the screen 
		System.out.print("\nWelcome to the game ");
		for(int i = 0; i < playerNames.length; i++) 
		{
			System.out.print(playerNames[i]);
			if(i < playerNames.length - 1) 
			{
				System.out.print(", ");
			}
		}		
		
		//Prompt the shooter for showing the rules and call the showRules() method
		System.out.print("\nWould you like a brief explanation of the rules of the game? Enter Y for yes, N for no? ");
		char rulesQA = input.next().charAt(0);
		switch(rulesQA)
		{
		case 'y': CrapsHelper.showRules(); break;
		case 'Y': CrapsHelper.showRules(); break;
		default: System.out.print("OK, you know the rules...let's play some craps! "); break;
		}
		System.out.println();
		System.out.println();
		
		//Set the shooter ID at the beginning to the first player
		int shooterID = 0;
		
		//Start the main (gameOver) loop
		while(!gameOver)
		{
			//Prompt the user to enter his bet and call the getShooterBet() method
		    System.out.printf("%s you are the shooter!! \n", playerNames[shooterID]);
		    CrapsHelper.getShooterBet(playerNames, shooterID, bankRolls, MIN_BET, betAmounts);
		    
		    //Call the GetOpponentBet() method
		    CrapsHelper.getOpponentBet(playerNames, shooterID, bankRolls, MIN_BET, betAmounts);
		    
		    //Declare and assign the variable dice to the return value of the rollDice() method
		    int dice = CrapsHelper.rollDice();
		    System.out.printf("***** Rolling the dice...and the result is: %d! *****\n", dice);
		    System.out.println();
		    
		    //Check the first dice roll by calling checkDiceRoll() method to find the winner of the pass
		    CrapsHelper.checkDiceRoll(dice, playerNames, shooterID, bankRolls, betAmounts);
		    
		    //adjust players' bank rolls by calling the adjustBankBalances() method
		    CrapsHelper.adjustBankBalances(bankRolls, betAmounts, shooterID);
		    
		    //Call printPlayerBankBalances() to print bank rolls to the screen
		    CrapsHelper.printPlayerBankBalances(playerNames, bankRolls);
		    
		    /*Check to see if the shooter has 0 in his bank roll after the pass, if so call the 
		      getNextShooter() method if not continue with the loop*/
		    if (bankRolls[shooterID] == 0) 
		    {		    	System.out.printf("Sorry %s, you have crapped out!\n\n", playerNames[shooterID]);
		        shooterID = CrapsHelper.getNextShooter(shooterID, bankRolls);
		        continue; 
		    }
		    
		    /*Check to see if there is winner by calling checkForWinner() method, if so call the 
		      identitfyWinner method and print the name of the winner to the screen*/
		    int totalPlayers = playerNames.length;
		    int totalMoney = totalPlayers * ANTE;
		    if(CrapsHelper.checkForWinnner(bankRolls, totalMoney))
		    {
		        String winner = CrapsHelper.identifyWinner(playerNames, bankRolls, totalMoney);
		        System.out.printf("Congratulations %s, you are the winner!!\n", winner);
		        break;  
		    }
		    
		    /*Prompt the user to either pass or shoot again, if the user chooses to pass call
		      the getNextShooter() method, if not continue with the loop*/
		    System.out.printf("%s, do you want to roll again or pass the dice? "
		                      +"Press Y to roll or press P to pass the dice to the next shooter: ", 
		                      playerNames[shooterID]);
		    char shooterChoice = input.next().charAt(0);
		    if(shooterChoice == 'P' || shooterChoice == 'p')
		    {
		        shooterID = CrapsHelper.getNextShooter(shooterID, bankRolls);
		    } 
		}
		
		input.close();
	}
	//End of main method
}
//End of class