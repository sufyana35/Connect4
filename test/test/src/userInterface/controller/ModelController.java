/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.controller;

import main.*;
import userInterface.view.IModel;
import javax.swing.SwingUtilities;

/**
 * @author Matthew Foggon
 */
public class ModelController implements IModel {
	private boolean validSelectionMade = false;
	GameBoard game = GameBoard.getInstance();
        
        /**
         * GameBoard mode identifier, single player or multi player. For human plays
         * the location will be constantly checked by the PlayerHuman class.
	 */
        @Override
	public void gameInstance(int mode) {
		LaunchGame gameLauncher = new LaunchGame(mode);
		gameLauncher.execute();
	}
        
        /**
         * #METHOD
         * Create model handle data. Gather stored data containing the wins and losses for the computer and user.
         * If a file does not exist one will be made with zero wins and losses.
	 */
        @Override
	public void scoreBoardInstance() {
	}
	
        @Override
	public void setStartLocation(Player currentPlayer, PieceLocation aLoc){
		validSelectionMade = false;
		currentPlayer.setStart(aLoc);
		
		if(currentPlayer.validStartSelected() == false){
		
                } else {
			validSelectionMade = true;
		}
	}
	
        @Override
	public void setEndLocation(Player currentPlayer, PieceLocation aLoc){
		validSelectionMade = false;
		if(aLoc.checkIsSameLocation(currentPlayer.getStart())) {
			currentPlayer.resetLocations();
			return;
		}
		currentPlayer.setEnd(aLoc);
		
		if(currentPlayer.validEndSelected() == false){
			currentPlayer.resetLocations();
		} else {
			validSelectionMade = true;
		}
		
	}
	
        @Override
	public void makeMove(Player currentPlayer){
		game.currentPlayer().makeCurrentMove();
	}
	
        @Override
	public boolean validSelectionMade(){
		return validSelectionMade;
	}

}
