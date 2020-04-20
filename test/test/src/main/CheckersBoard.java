/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Observable;

/**
 * CheckersBoard class containing the 8x8 grid.
 * The board is a 2 dimensional array that holds the value of the players
 * each piece holds a 'piece' class
 * @author sufyan, calum
 */
public class CheckersBoard extends Observable{
	public static final int BOARD_ROWS = 8;
	public static final int BOARD_COLUMNS = 8;
	private Piece[][] arrayBoard = new Piece[8][8];
	private int turnComplete = 0;
	
	/**
         * 
         * initialise the board game and create pieces. It holds a piece or a null object.
	 * 
	 * @author Sufyan Ahmed
	 */
	public void boardInitialize() {
		for (int row = 0; row <= BOARD_ROWS - 1; row++) {
			for (int column = 0; column <= BOARD_COLUMNS - 1; column++) {
				try{
					if (row <= 2) {
						if ((row == 1) && (column % 2 == 1)) {
							arrayBoard[row][column] = new Piece(PlayerColour.BLACK, new PieceLocation(column,row));
						} else if ((row != 1) && (column % 2 == 0)) {
							arrayBoard[row][column] = new Piece(PlayerColour.BLACK, new PieceLocation(column,row));
						}
					}
					if (row >= 5) {
						if ((row == 6) && (column % 2 == 0)) {
							arrayBoard[row][column] = new Piece(PlayerColour.RED, new PieceLocation(column,row));
						} else if ((row != 6) && (column % 2 == 1)) {
							arrayBoard[row][column] = new Piece(PlayerColour.RED, new PieceLocation(column,row));
						}
					}
				// An exception error, placed just incase if a fatal error occurs
				} catch (OutOfBoundsException oobe) {
					System.out.println("Fatal error initializing the Board.");
					System.exit(1);
				}
			}
		}
	}

	/**
	 * @return BoardPiece[][] of the CheckersBoard.
	 */
	public Piece[][] getArray(){
		return this.arrayBoard;
	}	
	
	/**
         * #METHOD to clear board
         * Reset the board values to all null
	 */
	public void boardReset() {
		for (int row = 0; row <= BOARD_ROWS - 1; row++){
			for (int column = 0; column <= BOARD_COLUMNS - 1; column++){
				arrayBoard[row][column] = null;
			}
		}
		boardInitialize();
	}
	
	/**
         * Check board for board piece locations
	 * 
	 * @param square what square / board piece to be checked
	 * 
	 * @return arrayBoard[x][y] the location of the piece / board piece. 
 Return null if not any found
	 * 
	 * @author 
	 */
	public Piece squareCheck(PieceLocation square) {
		if(!square.inBoardBounds()){ return null; }
		return arrayBoard[square.getY()][square.getX()];
	}

	/**
         * Get board pieces and search for a particular colour / player
	 * @param playerColour colour of piece / player
	 * @return array of containing colour pieces
	 */
	public Piece[] getPieces(PlayerColour playerColour) {
		Piece[] tempPiece = new Piece[12];
		int tempIndex = 0;
		for(int i = 0; i < BOARD_ROWS; i++) {
			for(int j = 0; j < BOARD_COLUMNS; j++){
				if(arrayBoard[i][j] != null && arrayBoard[i][j].getPlayerColour() == playerColour) {
					tempPiece[tempIndex] = arrayBoard[i][j];	//A temp array filled with correct pieces
					tempIndex++;
				}
			}
		}

		Piece[] myPieces = new Piece[tempIndex];
                System.arraycopy(tempPiece, 0, myPieces, 0, tempIndex); // generate size of array
		return myPieces;
	}
	
	/*
         * Encapsulation and private method for board
            Chnages the state position of the board
	 */
	private void setSquare(PieceLocation square, Piece piece) throws OutOfBoundsException {
		if(!square.inBoardBounds()) {
			throw new OutOfBoundsException("A piece was set at an invalid location.");
		}
		arrayBoard[square.getY()][square.getX()] = piece;
		if(piece != null){
			piece.setPieceLocation(square);
		}
	}
	
	/**
         * #ACCESSOR METHOD : Player turn over
	 * @return denoting the status of the current turn (0-2)
	 */
	public int turnComplete(){ return turnComplete; }
	
	
	/**
         * Reset turn and clear changes on board
	 */
	public void resetTurn(){ 
		clearChanged();
		turnComplete = 0; 
	}
        /**
         * Continue turn
         */
	private void continueTurn(){ 
		setChanged();
		turnComplete = 1; 
	}
        /**
         * End current player turn
         */
	private void endTurn(){ 
		setChanged();
		turnComplete = 2; 
	}


	/**
         * 
         * Move player positions across the board given by its coordinates locations &
         * removing any pieces if applicable
	 * 
	 * @param player piece to move
	 * @param start location values
	 * @param end location values
	 * @author 
	 */
	public void movePiece(Player player, BoardMove move) {
		resetTurn();
		if (move.isValid() == false) {return;}
		PieceLocation start = move.getStart();
		PieceLocation end = move.getEnd();
		
		try {
			if (move.isJump(start,end)) {
				int tempY = (end.getY() + start.getY()) / 2;
				int tempX = (end.getX() + start.getX()) / 2;
				PieceLocation middle = new PieceLocation(tempX, tempY);
				
				setSquare(middle,null);
				setSquare(end,squareCheck(start));
				setSquare(start,null);
				
				if(squareCheck(end).emptyJumps(player, this).length > 0){
					continueTurn();
				} else {
					endTurn();
				}
				
			} else {
				endTurn();
				setSquare(end,squareCheck(start));
				setSquare(start,null);
			}
			
		}
		catch (OutOfBoundsException oobe){
			System.out.println("A movement was made outside of the board's boundaries.");
		}
	}

	/**
	 * @param start Location
	 * @param end Location
	 * @return The absolute-value X-difference between the two locations.
	 */
	public int deltaX(PieceLocation start, PieceLocation end) {
		return Math.abs((start.getX() - end.getX()));
	}
	
	/**
	 * @param start Location
	 * @param end Location
	 * @return The absolute-value Y-difference between the two locations.
	 */
	public int deltaY(PieceLocation start, PieceLocation end) {
		return Math.abs((start.getY() - end.getY()));
	}

	
	/**
         * Print to screen. Used to identify what is being held on the board.
	 * 
	 * @author 
	 */
	public void printArray() {
		for (int i = -1; i <= 7; i++) {
			for (int j = -1; j <= 7; j++) {

				if ( i == -1 && j == -1 ) {
					System.out.print("    ");
				} else if ( i == -1 ) {
					if (j == 7) {
						System.out.print(j + "\n\n");
					} else {
						System.out.print(j + " ");
					}
				} else if (j == -1 ) {
					System.out.print(i + "   ");
				} 

				else if (j == 7) {
					if (arrayBoard[i][j] == null) {
						System.out.print("_" + "\n");
					} else {
						System.out.print((arrayBoard[i][j]) + "\n");
					}
				} else {
					if (arrayBoard[i][j] == null) {
						System.out.print("_" + " ");
					} else {
						System.out.print((arrayBoard[i][j]) + " ");
					}

				}
			}
		}
		System.out.print("\n");
	}


	/**
         * #METHOD create an clone of the board for modelling purposes
	 * @param lastBoard clone last board
	 * @return new cloned board
	 */
	public CheckersBoard cloneBoard(CheckersBoard lastBoard){
		CheckersBoard checkersBoard = new CheckersBoard();
		for(int row = 0; row < CheckersBoard.BOARD_ROWS; row++){
			for(int col = 0; col < CheckersBoard.BOARD_COLUMNS; col++){
				PieceLocation tempLoc = null;
				try {
					tempLoc = new PieceLocation(row,col);
				} catch (OutOfBoundsException e) {
					System.exit(1);
				}
				
				if(lastBoard.squareCheck(tempLoc) == null){
				} else {
					try {
						checkersBoard.setSquare(tempLoc,new Piece(lastBoard.squareCheck(tempLoc).getPlayerColour(), tempLoc));
					} catch (OutOfBoundsException e) {
					}
				}
				
			}
		}
		
		return checkersBoard;
	}
}

