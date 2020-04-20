/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import userInterface.controller.ScoreDataHandler;
import javax.swing.SwingUtilities;

public class GameBoard {
	private static GameBoard instance;
	private CheckersBoard mainBoard;
	private Player playerRed, playerBlack, playerCurrent;
	private ScoreDataHandler file = new ScoreDataHandler();
	private int wins = file.scoreLoad().getWins();
	private int losses = file.scoreLoad().getLosses();
	private int gamesPlayed = file.scoreLoad().getGamesPlayed();
	private Score score;
	
	// instantiation of the GameBoard. empty
	private GameBoard(){	}
	
	/**
         * #METHOD Obtain single instance of game. Create one if none exists
	 * @return instance The single instance of a game
         * @author sufyan
	 */
	public static synchronized GameBoard getInstance(){
		if(instance == null){
			instance = new GameBoard();
		}
		return instance;
	}
	
	/**
         * Initialisation method to create the game and players
	 * @param mode values corresponding to the player values (1 and 2)
	 */
	public void initialize(int mode) {
		mainBoard = new CheckersBoard();
		mainBoard.boardInitialize();
		
		switch(mode) {
			case 1:
				playerBlack = new AIPlayer(PlayerColour.BLACK,mainBoard);
				playerRed = new HumanPlayer(PlayerColour.RED,mainBoard); 
				break;
			case 2: 
				playerBlack = new HumanPlayer(PlayerColour.BLACK,mainBoard);
				playerRed = new HumanPlayer(PlayerColour.RED,mainBoard); 
				break;
		}
		
		playerCurrent = playerRed;
	}
	
	/**
         * Loop, continue game until no one player can move
	 */
	public void play() {
		int turn = 1;
		while(!checkGameOver()) {
			mainBoard.resetTurn();
			
			switch(turn) {
				case 0: playerCurrent = playerBlack;
						turn +=1;
						break;
				case 1: playerCurrent = playerRed;
						turn -=1;
						break;
			}
			
			if(checkGameOver()) break;
			
			System.out.println("Turn: "+ playerCurrent.toString());
			playerCurrent.myTurn();
			
                        //End loop only when state board has chnaged, for example when a peice has been moved
			while(playerCurrent.isMyTurn() == true) {
				SwingUtilities.isEventDispatchThread();
				if(!playerCurrent.CheckIsHuman()){
					playerCurrent.makeCurrentMove();
				}
				
				try{
					Thread.sleep(60);
				}catch (InterruptedException ie){
					ie.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @return current player B or R
	 */
	public Player currentPlayer() { 
		return playerCurrent; 
	}
	
	/**
	 * @return red player
	 */
	public Player getRed() {
		return playerRed;
	}
	
	/**
	 * @return black player
	 */
	public Player getBlack() {
		return playerBlack;
	}

	/**
	 * @return the board game objects
	 */
	public CheckersBoard getBoard() {
		return this.mainBoard;
	}
	
	/**
	 * @return check if the game has ended
	 */
	public boolean checkGameOver(){
		playerRed.updatePieces();
		playerBlack.updatePieces();
		
		if (playerRed.getPieces().length == 0 || playerBlack.getPieces().length == 0){
			appendScore();
			return true;
		}
		
            for (Piece piece : playerCurrent.getPieces()) {
                if (piece.getAllMoves(playerCurrent, mainBoard, false).length > 0) {
                    return false;
                }
            }
		
		return true;
	}
	
	private boolean isSinglePlayer() {
		return(!playerBlack.CheckIsHuman());
	}
	
	/*
	 * Amend the score file
	 */
	private void appendScore() {
		score = new Score(wins, losses, gamesPlayed);
		
		if(isSinglePlayer()) {
			if(playerBlack.getPieces().length == 0) {
				score.appendWins();
				score.appendGamesPlayed();
				file.saveScore(score);
			}
		} else {
			score.appendLosses();
			score.appendGamesPlayed();
			file.saveScore(score);
		}
	}
	
}
