/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * A class containing the subtypes of HumanPlayer and AIPlayer holding the information of player and pieces.
 * 
 * @param myPieces - Array pieces containing the player pieces
 * @param playerColour - Denoting the player colour
 * @param human - Check if player is human or not
 * @param Board - Board object instance
 * 
 * @author Calum
 * 
 */
public abstract class Player {
	protected boolean myTurn;
	protected Piece[] myPieces;
	protected final PlayerColour playerColour;
	protected CheckersBoard checkersBoard;
	protected PieceLocation currentStart, currentEnd;
	protected boolean checkIsHuman;
	
	protected boolean continueMove = false;

	/**
         * #Constructor to create player and colours, check human status and instance of checkersBoard game
         * 
	 * @param playerColour colour player
         * @param checkersBoard instance
	 */
	public Player(PlayerColour playerColour, CheckersBoard checkersBoard) {
		this.playerColour = playerColour;
		this.checkersBoard = checkersBoard;
		updatePieces();
	}
	
	/**
	 * @return True if player is human
	 */
	public boolean CheckIsHuman(){
		return checkIsHuman;
	}

	/**
	 * @return playerColour
	 */
	public PlayerColour getColour() {
		return this.playerColour;
	}

	/**
         * #Mutator update pieces array with state of checkersBoard
	 */
	public void updatePieces() {
		this.myPieces = checkersBoard.getPieces(this.playerColour);
	}

	/**
	 * @return Piece[] myPieces, array of all possible pieces on the checkersBoard
	 */
	public Piece[] getPieces() {
		return this.myPieces;
	}
        
        public abstract void setStart(PieceLocation start);
	public abstract void setEnd(PieceLocation end);
	public abstract void setStart();
	public abstract void setEnd();
	
	public PieceLocation getStart() { return currentStart; }
	public PieceLocation getEnd() { return currentEnd; }
	
	/**
         * Make player move
	 */
	public void makeCurrentMove() {
		
		if(currentStart == null || currentEnd == null || myTurn == false) {
			if(checkIsHuman == false) {
				setStart();
				setEnd();
			} else {
				return;
			}
		}
		
		BoardMove move = new BoardMove(this,currentStart,currentEnd,checkersBoard,false);
		
		if (move.isValid() == false) {
			return;
		} else if (!move.isJump(currentStart,currentEnd) && checkersBoard.turnComplete() == 1){
			System.out.println("That piece can only continue by jumping.");
			currentEnd = null;
			return;
		}
		
		checkersBoard.movePiece(this,move);
		
		if(move.isJump(currentStart,currentEnd) 
		&& checkersBoard.squareCheck(currentEnd).emptyJumps(this,checkersBoard).length > 0) {
			System.out.println("This piece can continue to jump.");
			continueMove = true;
			currentStart = currentEnd;
			setEnd();
		} else {
			resetLocations();
			myTurn = false;
		}
	}
	
	public boolean validStartSelected() {
		return (currentStart != null);
	}
	
	public boolean validEndSelected() {
		return (currentEnd != null);
	}
	
	public void resetLocations(){
		continueMove = false;
		currentStart = null;
		currentEnd = null;
	}
	
	public void myTurn(){
		myTurn = true;
	}
	
	public boolean isMyTurn(){
		return myTurn;
	}
	
	public boolean isContinueMove(){
		return continueMove;
	}
	
        @Override
	public String toString(){
		return "Player: " + this.playerColour;
	}
}
