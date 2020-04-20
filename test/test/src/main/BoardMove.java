/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;


/**
 * Class containing information and checks on a movement from one location to the next.
 * @author Sufyan 
 *
 */
public class BoardMove {
	private boolean valid;
	private CheckersBoard checkersBoard;
	private PieceLocation start, end;
	private Player owner;
	
	/**
	 * @param player The player making the Move.
	 * @param start The start location.
	 * @param end The end location.
         * @param board
	 * @param silentMovementChecks Whether the checks should be done silently or not.
	 */
	public BoardMove(Player player, PieceLocation start, PieceLocation end, CheckersBoard board, boolean silentMovementChecks){
		this.owner = player;
		this.start = start;
		this.end = end;
		this.checkersBoard = board;
		this.valid = validMove(silentMovementChecks);
		
	}
	
	/**
	 * @return start Location
	 */
	public PieceLocation getStart(){
		return start;
	}
	
	/**
	 * @return end Location
	 */
	public PieceLocation getEnd(){
		return end;
	}
	
	/**
	 * @return true if the Move is a valid one.
	 */
	public boolean isValid() { 
		return valid;
	}
	
	/**
	 * Checks to see if a given move is a Jump or not (moving across two spaces)
	 * @param start The starting location
	 * @param end The ending location
	 * @return true if the movement is a Jump.
	 */
	public boolean isJump(PieceLocation start, PieceLocation end){
                return checkersBoard.deltaX(start,end) == 2 && checkersBoard.deltaY(start,end) == 2;
	}
	
	/**
	 * Accessor method that will check the general case legality of a move.
	 * Ensures that all moves performed are legal.
	 * 
	 * 
         * @param silentMovementChecks
	 * @param owner the owner owner of the moved Piece.
	 * @param start Starting Location.
	 * @param end Ending location.
	 * 
	 * @return true if the move is valid.
	 */
	public boolean validMove(boolean silentMovementChecks) {
		int maxDistance;
		if (isJump(start,end)) {
			maxDistance = 2;
		} else {
			maxDistance = 1;
		}
		
		if (checkersBoard.squareCheck(start) == null) {
			if(!silentMovementChecks) {System.out.println("No piece on that starting position."); }
			return false;
		} else if (checkersBoard.squareCheck(end) != null) {
			if(!silentMovementChecks) { System.out.println("The end position is already taken."); }
			return false;
		} else if (checkersBoard.squareCheck(start).getPlayerColour() != owner.getColour()) {
			if(!silentMovementChecks) {System.out.println("That is not your piece.");}
			return false;
		} else if (checkersBoard.deltaX(start, end) != maxDistance || checkersBoard.deltaY(start, end) != maxDistance) {
			if(!silentMovementChecks) {System.out.println("You cannot move that far."); }
			return false;
		} else if (checkersBoard.squareCheck(start).isKing() == false) {
			if (end.getY() >= start.getY() && owner.getColour() == PlayerColour.RED) {
				if(!silentMovementChecks) { System.out.println("That piece can only move up."); }
				return false;
			} else if (end.getY() <= start.getY() && owner.getColour() == PlayerColour.BLACK) {
				if(!silentMovementChecks) { System.out.println("That piece can only move down."); }
				return false;
			} 
		} 
		
		if(isJump(start,end) == false) { return true; } 
		else { return validJump(silentMovementChecks); } // A series of checks pertaining to Jumps will be made

	}

	/**
	 * Accessor method that contains checks applicable to a piece that is jumping.
	 * @param silentMovementChecks Whether the checks should be done silently or not.
	 * @return true if the movement is a legal jump.
	 */
	public boolean validJump(boolean silentMovementChecks) {
		int tempY = (end.getY() + start.getY()) / 2;
		int tempX = (end.getX() + start.getX()) / 2;
		PieceLocation middle;
		
		try {
			middle = new PieceLocation(tempX, tempY);
		} catch(OutOfBoundsException oobe) {
			return false;
		}
 
		if (checkersBoard.squareCheck(middle) == null) {
			if(!silentMovementChecks) { System.out.println("No piece to jump over."); }
			return false;
		} else if (checkersBoard.squareCheck(middle).getPlayerColour() == checkersBoard.squareCheck(start).getPlayerColour()) {
			if(!silentMovementChecks) { System.out.println("You cannot jump your own chip."); }
			return false;
		} else if (start.getX() + 1 == middle.getX()) {
			if (start.getX() + 2 != end.getX()) {
				if(!silentMovementChecks) { System.out.println("You can only jump in a straight line."); }
				return false;
			}
		} else if (start.getX() - 1 == middle.getX()) {
			if (start.getX() - 2 != end.getX()) {
				if(!silentMovementChecks) { System.out.println("You can only jump in a straight line."); }
				return false;
			}
		}
		
		return true;
	}

        @Override
	public String toString(){
		if(start != null && end != null){
			return (start.toString()+"to "+end.toString()+". Valid: "+valid);
		}
		return("Invalid");
	}
}
