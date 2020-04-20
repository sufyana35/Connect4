/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import main.CheckersBoard;
import main.PlayerColour;
import main.GameBoard;
import main.PieceLocation;
import main.BoardMove;
import main.OutOfBoundsException;
import main.Piece;
import main.Player;

/**
 * A representation of a single CheckersBoard State, including information on its evaluated score.
 * @author Neil
 *
 */
public class StateBoard{
	Player whoseTurn, otherPlayer;
	Player rootPlayer;
	int redScore, blackScore;
	int netScore;
	boolean terminalNode;
	CheckersBoard currentBoard;
	Piece[] piecesAvailable;
	
	/**
	 * Constructor method for the BoardState.
	 * @param whoseTurn The Player whose turn it is.
	 * @param lastBoard The last instance of the CheckersBoard to be generated from.
	 * @param rootPlayer The Root Player, for use in the evaluative functions.
	 */
	public StateBoard(Player whoseTurn, CheckersBoard lastBoard, Player rootPlayer){
		this.whoseTurn = whoseTurn;
		
		switch(whoseTurn.getColour()){
		case BLACK: otherPlayer = GameBoard.getInstance().getRed();
					break;
		case RED: otherPlayer = GameBoard.getInstance().getBlack();
					break;
		}
		
		this.rootPlayer = rootPlayer;
		this.currentBoard = lastBoard.cloneBoard(lastBoard);
		piecesAvailable = currentBoard.getPieces(whoseTurn.getColour());
	}
	
	/**
	 * #METHOD find all possible moves for current player
	 * @return Move[] containing all moves available to the current Player.
	 */
	public BoardMove[] findMoves(){
		BoardMove[] possibleMoves = new BoardMove[64];
		int numMoves = 0;
		
		int index = 0;
		for(Piece pieces : piecesAvailable){
			for(BoardMove m : piecesAvailable[index].getAllMoves(whoseTurn, currentBoard, false)){
				possibleMoves[numMoves] = m;
				numMoves++;
			}
			index++;
		}
		
		BoardMove[] allMovesTrimmed = new BoardMove[numMoves];
                System.arraycopy(possibleMoves, 0, allMovesTrimmed, 0, numMoves); //netbeans
		return allMovesTrimmed;
		
	}
	
	/**
	 * Evaluation function on the board.
	 * @return Integer score of the board, positive if favoring the Root player.
	 */
	public int calculateScore() {
		for(int row = 0; row < CheckersBoard.BOARD_ROWS; row++) {
			for(int col = 0; col < CheckersBoard.BOARD_ROWS; col++){
				PieceLocation loc = null;
				try {
					loc = new PieceLocation(col,row);
				} catch (OutOfBoundsException e) {
					System.exit(1);
				}
				if (currentBoard.squareCheck(loc) == null){
					continue;
				} 
				
				else if (currentBoard.squareCheck(loc).getPlayerColour() == PlayerColour.RED){
					redScore++;
					
					if((1 < loc.getX() || loc.getX() < 6) &&
							(1 < loc.getY() || loc.getY() < 6)){
						redScore++;
					} 
					
					if(currentBoard.squareCheck(loc).isKing()) {
						redScore+=2;
					} 
					
					if(whoseTurn.getColour() == PlayerColour.RED){
						if(currentBoard.squareCheck(loc).getAllMoves(whoseTurn, currentBoard, false).length == 0){
							redScore-=1;
						}
					} else {
						if(currentBoard.squareCheck(loc).getAllMoves(otherPlayer, currentBoard, false).length == 0){
							blackScore-=1;
						}
					}
				} 
				
				else if (currentBoard.squareCheck(loc).getPlayerColour() == PlayerColour.BLACK){
					blackScore++;
					
					if((1 < loc.getX() || loc.getX() < 6) &&
							(1 < loc.getY() || loc.getY() < 6)){
						blackScore++;
					} 
					
					if(currentBoard.squareCheck(loc).isKing()){
						blackScore+=2;
					}
					
					if(whoseTurn.getColour() == PlayerColour.BLACK){
						if(currentBoard.squareCheck(loc).getAllMoves(whoseTurn, currentBoard, false).length == 0){
							blackScore-=1;
						}
					} else {
						if(currentBoard.squareCheck(loc).getAllMoves(otherPlayer, currentBoard, false).length == 0){
							redScore-=1;
						}
					}
				}
			}
		}

		if(currentBoard.getPieces(rootPlayer.getColour()).length == 0){
			netScore = Integer.MIN_VALUE;
			return netScore;
		} else if(currentBoard.getPieces(otherPlayer.getColour()).length == 0) {
			netScore = Integer.MAX_VALUE;
			return netScore;
		}
		
		switch(rootPlayer.getColour()){
			case BLACK: 
					netScore = blackScore - redScore;
					break;
					
			case RED: netScore = redScore - blackScore;
					break;
		}
		
		return netScore;
		
	}
	
	/**
	 * @return True if the game is over.
	 */
	boolean gameOver(){
		return currentBoard.getPieces(PlayerColour.BLACK).length == 0 ||
                        currentBoard.getPieces(PlayerColour.RED).length == 0;
	}
}
