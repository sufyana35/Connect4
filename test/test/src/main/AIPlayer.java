/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;
import java.util.Random;

import algorithm.AIMiniMax;
import algorithm.StateBoard;

/**
 * Class containing methods to generate new computer movements
 * @author Neil
 */
public class AIPlayer extends Player {
	private Random generator = new Random();
	private BoardMove bestMove;

	/**
         * #METHOD player constructor
	 * @param aColour colour AI player
	 * @param board Instance of the checkersBoard
	 */
	public AIPlayer(PlayerColour aColour,CheckersBoard board) {
		super(aColour,board);
		checkIsHuman = false;
	}
	
	/**
         * Set player start location
         * @param start
	 */
        @Override
	public void setStart(PieceLocation start) {
		currentStart = start;
	}
	
	/**
         * Set player end location to given value
         * @param end location value
	 */
        @Override
	public void setEnd(PieceLocation end) {
		currentEnd = end;
	}
	
	/**
	 * Set start position
	 */
        @Override
	public void setStart(){
		if(bestMove == null){
			setBestMove();
		}
		currentStart = bestMove.getStart();
	}
	
	/**
	 * set end position
	 */
        @Override
	public void setEnd(){
		// A null bestMove indicates that the player must continue to move in a given direction.
		if(bestMove == null){
			PieceLocation[] possibleEnds = checkersBoard.squareCheck(currentStart).emptyJumps(this, checkersBoard);
			currentEnd = possibleEnds[generator.nextInt(possibleEnds.length)];
		} else {
			currentEnd = bestMove.getEnd();
		}
		
		System.out.println(currentStart.toString() + "    " + currentEnd.toString());
		
		bestMove = null;
		
	}
	
	/**
	 * set player best movement based on the AIMINIMAX algorithm
	 * if no best moves are found do a random move
            New CheckersBoard
            New AI Mini Max code
            Evaluate moves, if no move is found choose a random move
	 */
	public void setBestMove(){
		pause(1);
		StateBoard stateBoard = new StateBoard(this,checkersBoard, this);
		AIMiniMax minimax = new AIMiniMax(this);
		minimax.evaluateMovementTree(0,stateBoard,1);
		bestMove = minimax.best;
		
		if(bestMove == null){
			Piece selectedPiece = myPieces[generator.nextInt(myPieces.length)];
			BoardMove[] availableMoves = selectedPiece.getAllMoves(this, checkersBoard, false);
			bestMove = availableMoves[generator.nextInt(availableMoves.length)];
		}
	}

	/**
         * #METHOD
         * Pauses the AI player move by a number of seconds
         * @param timeToWaitInSeconds wait by this number of duration
	 */
	private void pause(int timeToWaitInSeconds) {
		int timeToMilli = 1000 * timeToWaitInSeconds;
		long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
            t1 = System.currentTimeMillis();
        }
        while (t1 - t0 < timeToMilli);
	}
}
