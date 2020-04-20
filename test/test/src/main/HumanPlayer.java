/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.util.Scanner;


/**
 * A human Player Subclass. Contains methods to take user input and turn it into a Location.
 * @author Calum
 *
 */
public class HumanPlayer extends Player {
	private Scanner scanner = new Scanner(System.in);

	/**
	 * Constructor method to call the Player super's constructor given some parameters.
	 * @param playerColour The PlayerColour of the HumanPlayer to be made.
	 * @param checkersBoard The instance of the CheckersBoard.
	 */
	public HumanPlayer(PlayerColour playerColour,CheckersBoard checkersBoard) {
		super(playerColour,checkersBoard);
		checkIsHuman = true;
	}
	
	/**
	 * Empty method that will have no effect on call- this is to retain polymorphism with the AIPlayer.
	 */
        @Override
	public void setStart(){
	}
	
	/**
	 * Empty method that will have no effect on call- this is to retain polymorphism with the AIPlayer.
	 */
        @Override
	public void setEnd(){
	}
	
	/**
	 * Sets the Player's start location to a given location.
         * @param start location
	 */
        @Override
	public void setStart(PieceLocation start){
		if(start.inBoardBounds() == false){
			System.out.println("That is an invalid location.");
			return;
		} else if (checkersBoard.squareCheck(start) == null) {
			System.out.println("There is no piece there.");
			return;
		} else if (checkersBoard.squareCheck(start).getPlayerColour() != this.playerColour) {
			System.out.println("That is not your piece to move.");
			return;
		}
		
		currentStart = start;
	}
	
	/**
	 * Sets the Player's end location to a given location.
         * @param end
	 */
        @Override
	public void setEnd(PieceLocation end){
		if(end.inBoardBounds() == false){
			System.out.println("That is an invalid location.");
			return;
		} else if (checkersBoard.squareCheck(end) != null) {
			System.out.println("That square is already occupied.");
			return;
		} 
		currentEnd = end;
	}

}
