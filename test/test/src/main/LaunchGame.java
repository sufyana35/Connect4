/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor
@author Calum
 */
package main;

import javax.swing.SwingWorker;

public class LaunchGame extends SwingWorker{
	int mode = -1;

	
	/**
         * #METHOD constructor, set mode of the the gameBoard to determine player numbers, 1 or 2
         * @param mode integer value, 1 AI, 2 player VS player
	 */
	public LaunchGame(int mode) {
		this.mode = mode;
	}
	
	/**
         * Run gameBoard methods
	 */
	public void runGame() {
		GameBoard gameBoard = GameBoard.getInstance();
		gameBoard.initialize(mode);
		gameBoard.play();
	}
	@Override
	protected String doInBackground() throws Exception {
		runGame();
		return "Start game";
	}

}
