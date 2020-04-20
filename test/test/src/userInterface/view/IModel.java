/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;

import main.*;

/**
 *@author David Birtch
 */
public interface IModel {

	/**
         * Create data model handle. Get game mode, Human player checks. Pre and post checks are made.
         * @param mode the game mode of the game, Human vs computer, human vs human
	 */
	public void gameInstance(int mode);
	
	/**
         * #METHOD
         * Score board
	 */
	public void scoreBoardInstance();
	
        /**
         * #METHOD
         * Set move locations
	 */
	public void setStartLocation(Player currentPlayer, PieceLocation aLoc);
	public void setEndLocation(Player currentPlayer, PieceLocation aLoc);
        
        /**
         * #METHOD
         * Make move
	 */
	public void makeMove(Player currentPlayer);
        
        /**
         * #METHOD
         * Valid move made, piece selected
	 */
	public boolean validSelectionMade();
	
}

