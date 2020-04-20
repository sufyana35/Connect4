/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * Hold information on piece status, colours, locations and king status
 * 
 * @author Sufyan Ahmed
 */

public class Piece {
	private boolean king;
	private PlayerColour playerColour;
	private PieceLocation pieceLocation;

	/**
         * #CONSTRUCTOR for player playerColour
	 * 
	 * @param playerColour PlayerColour enumeration
         * @param location of piece
	 */
	public Piece(PlayerColour playerColour, PieceLocation location) {
		this.playerColour = playerColour;
		this.pieceLocation = location;
		
	}

	/*
	 * Make king
	 */
	private void makeKing() { king = true; }
	
	/**
	 * Check if piece to be is king
	 * @return king
	 */
	public boolean isKing() { return king; }

	/**
	 * determine pieceLocation
	 * @return pieceLocation
	 */
	public PieceLocation getPieceLocation(){ return pieceLocation; }
	
	/**
	 * #Method to set pieceLocation and make pieces king
	 * @param newLoc new pieceLocation of piece
	 */
	public void setPieceLocation(PieceLocation newLoc) {
		if(newLoc.inBoardBounds() == false) {
			return;
		}
		
		this.pieceLocation = newLoc;
		if(newLoc.getY() == 0 && this.playerColour == PlayerColour.RED){
			makeKing();
		} else if(newLoc.getY() == 7 && this.playerColour == PlayerColour.BLACK){
			makeKing();
		}
	}

	/**
	 * get playerColour
	 * @return PlayerColour enumeration piece
	 */
	public PlayerColour getPlayerColour() {
		return playerColour;
	}
	
	/**
	 * Method of returning pieceLocation of all empty square pieces
         * @param owner
         * @param checkersBoard
	 * @param player player piece
	 * @param start start pieceLocation of piece
	 * @return holds values of all empty arrays that a player can move into
	 */
	public PieceLocation[] emptyMoves(Player owner, CheckersBoard checkersBoard){
		boolean silentMovementChecks = true;
		int numMoves = 0;
		PieceLocation[] maxMoves = new PieceLocation[4];

		for(int x = -1; x <= 1; x += 2) {		// Check all squares of distance +/-1 
			for(int y = -1; y <= 1; y += 2){	// diagonally from piece
				int tempX = pieceLocation.getX() + x;
				int tempY = pieceLocation.getY() + y;
				PieceLocation tempLoc;
				
				try {
					tempLoc = new PieceLocation(tempX,tempY);
				} catch (OutOfBoundsException oobe) {
					continue;
				}
				
				BoardMove move = new BoardMove(owner,pieceLocation,tempLoc, checkersBoard, silentMovementChecks);
				if(tempLoc.inBoardBounds() && move.isValid()) { 
					maxMoves[numMoves] = tempLoc;	// All valid locations will be added to a temporary Array
					numMoves++;
				}
 			}
		}

		PieceLocation[] legalMoves = new PieceLocation[numMoves];
                System.arraycopy(maxMoves, 0, legalMoves, 0, numMoves); // A new array of correct length is generated, Netbean creation
            // and returned
		return legalMoves;
	}	
	
	/**
         * This code returns an array of locations that are empty from a given pieceLocation for player jumps
         * @param owner
         * @param checkersBoard
	 * @param player player piece
	 * @param start pieceLocation of piece
	 * @return values the player can make to move pieces to empty values
         * Max jumps 4 pieces
	 */
	public PieceLocation[] emptyJumps(Player owner, CheckersBoard checkersBoard){
		boolean silentMovementChecks = true;
		int numMoves = 0;
		PieceLocation[] maxJumps = new PieceLocation[4];

		for(int x = -2; x <= 2; x += 4){		// Check all squares of distance +/- 2
			for(int y = -2; y <= 2; y += 4){	// diagonally from piece
				int tempX = pieceLocation.getX() + x;
				int tempY = pieceLocation.getY() + y;
				PieceLocation tempLoc;
				
				try {
					tempLoc = new PieceLocation(tempX,tempY);
				} catch (OutOfBoundsException oobe) {
					continue;
				}
				
				BoardMove move = new BoardMove(owner,pieceLocation,tempLoc, checkersBoard, silentMovementChecks);
				if (tempLoc.inBoardBounds() && move.isValid()){
					maxJumps[numMoves] = tempLoc;	// All valid locations will be added to a temporary Array
					numMoves++;
				}
			}
		}

		PieceLocation[] legalJumps = new PieceLocation[numMoves];
		for(int index = 0; index < numMoves; index++){	// A new array of correct Length is generated
			legalJumps[index] = maxJumps[index];		// and returned
		}
		return legalJumps;
	}
	
	
	/**
         * Return a list of movements for selected piece within board game
	 * @param owner who owns the piece B or R
	 * @param checkersMove Board instance
	 * @param jumpsOnly search for jumps only
	 * @return BoardMove[] containing all the values where the player can move
	 */
	public BoardMove[] getAllMoves(Player owner, CheckersBoard checkersMove, boolean jumpsOnly){
		PieceLocation[] moves = emptyMoves(owner, checkersMove);
		PieceLocation[] jumps = emptyJumps(owner, checkersMove);
		
		BoardMove[] allMoves;
		
		if(owner.getColour() != playerColour){
			return new BoardMove[0];
		}
		
		if(jumpsOnly){
			allMoves = new BoardMove[jumps.length];
		} else {
			allMoves = new BoardMove[moves.length + jumps.length];
		}
		
		int index = 0;
		if(jumpsOnly == false){
			for(PieceLocation move: moves){
				BoardMove m = new BoardMove(owner,pieceLocation,move,checkersMove,true);
				allMoves[index] = m;
				index++;
			}
		}
		
		for(PieceLocation jump: jumps){
			BoardMove m = new BoardMove(owner,pieceLocation,jump,checkersMove,true);
			allMoves[index] = m;
			index++;
		}
		
		return allMoves;
		
	}
	
        @Override
	public String toString() {
		if(king) {
			return playerColour.toString();
		} else {
			return playerColour.toString().toLowerCase();
		}
	}
}
