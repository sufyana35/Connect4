/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.controller;

import javax.swing.JPanel;

import userInterface.view.GamePanel;
import userInterface.view.IModel;
import userInterface.view.MainFrame;
import userInterface.view.PanelListener;
import userInterface.view.ScorePanel;
import main.*;

/**
 *	FrameSwitcher will listen to events coming from the MainFrame
 *	@author Matthew Foggon
 * 
 */
public class FrameSwitcher implements PanelListener {

	private JPanel gamePanel = new GamePanel(); // Create an instance of the GamePanel
	private JPanel scorePanel = new ScorePanel(); // Create an instance of the ScorePanel
	private IModel modelController; // Create a handle on the ModelController
	private int gameMode;
	private MainFrame frame;
	private static FrameSwitcher instance;
        private boolean highlight;
        private PieceLocation highlightSquare;
        private String[][] stringBoard = new String[8][8];
	
	private FrameSwitcher(MainFrame frame) {
		this.frame = frame;
	}

        @Override
	public JPanel gamePanel(int gameMode) {
		this.gameMode = gameMode; // Store the given game mode in an instance variable.
		if (modelController != null) {
			modelController.gameInstance(gameMode);
		}
		return gamePanel;
	}

        @Override
	public JPanel scoreBoard() {
		if (modelController != null) {
			modelController.scoreBoardInstance();
		}
		return scorePanel;
	}

        //update the board
    public void updateGUI(){
        Piece[][] boardArray = GameBoard.getInstance().getBoard().getArray();
        if (boardArray == null){
            CheckersBoard firstBoard = new CheckersBoard();
            firstBoard.boardInitialize();
            boardArray = firstBoard.getArray();
            }
        for(int row = 0; row <= 7; row++){
			for (int column = 0; column <=7; column++){
				
				if(boardArray[column][row] == null){
					stringBoard[column][row] = null;
				} 
				else if(boardArray[column][row].getPlayerColour() == PlayerColour.BLACK) {
					if(boardArray[column][row].isKing()) {
						stringBoard[column][row] = "Black King";
					} else {
						stringBoard[column][row] = "Black";
					}

				} 
				else {
					if(boardArray[column][row].isKing()) {
                                                stringBoard[column][row] = "Red King";
					} else {
						stringBoard[column][row] = "Red";
					}
				}
			}
		}
        gamePanel.updateUI();
    }
    public void updateGUI(PieceLocation square){
        highlightSquare = square;
        highlight = true;
        gamePanel.updateUI();
        }
        
    public void updateGUI(Player player, int errorCode){
    
    
    }       
    public void setHighlight(boolean set){
        highlight = set;
        }
    public PieceLocation highlightSquare(){
        return highlightSquare;
        }
    public boolean highlight(){
        return highlight;
        }
	public static FrameSwitcher getInstance(MainFrame frame){
		if (instance == null){
			instance = new FrameSwitcher(frame);
		}
		return instance;
	}
    public static FrameSwitcher getInstance(){
        while(instance == null){
            pause(1);
            }
        return instance;
        }
    public String[][] stringBoard(){
        return stringBoard;
        }


	public void setCoordinates(IModel modelController) {
		this.modelController = modelController;
	}
    /**
        *Used to pause the thread to allow the MainFrame to initialize the 
        *FrameSwitcher before the ImagePanel needs it.
        *Pre-conditions: none.
        *Post-conditions: The thread has been paused for a number of seconds.
        *@param timeToWaitInSeconds: int The number of seconds to pause the thread for.
        *@author 
        */
	private static void pause(int timeToWaitInSeconds) {
		int timeToMilli = 1000 * timeToWaitInSeconds;
		long t0, t1;
        t0 =  System.currentTimeMillis();
        do{
            t1 = System.currentTimeMillis();
        }
        while (t1 - t0 < timeToMilli);
	}
	public IModel getModelController() { return modelController; }
	
}
