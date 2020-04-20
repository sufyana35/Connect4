/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm;

import main.*;

/**
 * MiniMax Algorithm to recursively search a tree of movements and evaluate the ideal path.
 * @author Neil
 *
 */
public class AIMiniMax {
	static final int MAX_DEPTH = 4;
	public BoardMove best;
	Player player, opponent;
	
	/**
	 * Constructor for the AIMiniMax class.
	 * @param player Player tree search
	 */
	public AIMiniMax(Player player){
		this.player = player;
		switch(player.getColour()){
			case BLACK: opponent = GameBoard.getInstance().getRed();
						break;
			case RED: opponent = GameBoard.getInstance().getBlack();
						break;
		}
	}
	
	/**
         * Evaluation tree search method to finding the best possible move based on the scoring each node states
         * @param depth searches for future possible moves, the higher the more difficult the AI is
         * @param someBoard last state board
         * @param signFactor multiply the score by every turn change - from the players root prespective
         * @return best score (integer) discovered
         * 
	 */
	public int evaluateMovementTree(int depth, StateBoard someBoard, int signFactor){
		if(depth >= MAX_DEPTH || someBoard.gameOver()){
			return someBoard.calculateScore();
		}
		
		BoardMove[] moves = someBoard.findMoves();
		
		int posValue = Integer.MIN_VALUE;
		for(BoardMove m : moves){
			
			StateBoard newState = makeMove(someBoard,m);
			
			int newValue = 0;
			
			// If the move is a jump that can be continued, a separate function is called to evalutate that tree.
			if(newState.currentBoard.turnComplete() == 1){
				newValue = signFactor * evaluateChainJumpTree(depth, newState, signFactor, m);
			} else {
				newValue = signFactor * evaluateMovementTree(depth + 1, newState, -signFactor);
			}
			
			// The highest scored node will have its root set as the best movement.
			if(newValue > posValue) { 
				if(depth == 0){
					best = m;
				}
				posValue = newValue;
			}
		}
		
		return signFactor * posValue;
		
		
	}
	
	/*
	 * Secondary evaluation tree for a chain of Jumps.
	 */
	private int evaluateChainJumpTree(int depth, StateBoard someBoard, int signFactor, BoardMove parentMove){
		Player player = someBoard.whoseTurn;
		CheckersBoard checkersBoard = someBoard.currentBoard;
		BoardMove[] moves = checkersBoard.squareCheck(parentMove.getEnd()).getAllMoves(player, checkersBoard, true);
		
		if(moves.length == 0){
			return signFactor * someBoard.calculateScore();
		}
		
		int posValue = Integer.MIN_VALUE;
		for(BoardMove m : moves){
			StateBoard newState = makeMove(someBoard,m);
			int newValue = 0;
			
			if(newState.currentBoard.turnComplete() == 1){
				newValue = signFactor * evaluateChainJumpTree(depth, newState, signFactor, m);
			} else {
				newValue = signFactor * evaluateMovementTree(depth + 1, newState, -signFactor);
			}
			
			if(newValue > posValue) { 
				posValue = newValue;
			}
		}
		
		return posValue;
	}
	
	/*
	 * #METHOD to generate a new StateBoard from a given movement.
	 */
	StateBoard makeMove(StateBoard lastState, BoardMove aMove){
		StateBoard newState;
		
		// switch players
		if(lastState.whoseTurn.getColour() == player.getColour()){
			newState = new StateBoard(opponent, lastState.currentBoard, player);
		} 
		else {
			newState = new StateBoard(player, lastState.currentBoard, player);
		}
		
		newState.currentBoard.movePiece(lastState.whoseTurn, aMove);
		
                //If it is the players turn (move), it is still their turn
		if(newState.currentBoard.turnComplete() == 1){
			newState.whoseTurn = lastState.whoseTurn;
		}
		
		return newState;
	}
	
}
