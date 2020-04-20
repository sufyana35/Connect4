/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;

import javax.swing.JPanel;

/**
 * @author David Birtch
 */
public interface PanelListener {

	/**
	 *  	@param gameMode  The mode of the game, determined by which button is clicked.
	 *  	@return Return the instance of the games view.
	 * 
	 */
	public JPanel gamePanel(int number);
	
	
	
	/**
	 *  	@return Return the instance of the score boards view.
	 */
	public JPanel scoreBoard();
	
}
