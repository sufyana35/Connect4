/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;

import userInterface.controller.*;
import main.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

/*
 * @author David Birtch
 */

public class GamePanel extends JPanel implements ActionListener {

    //declaring variables
    private JButton btnQuit;
    private ImageIcon quitButton, rollOverQuitButton;
    private IModel modelController;
    private MouseHandler mouseHandler; 
    private PieceLocation squareSelected;
    private int topLeftX = 292;
    private int topLeftY = 158;
    private static final int SQUARE_LENGTH = 44;
	
	public GamePanel() {
		setLayout(null);
                //Main background for all panels
		ImagePanel background = new ImagePanel(new ImageIcon("GUIImages/Other/FormalBackground.png").getImage(), "GamePanel");
		// set the quit button
		quitButton = new ImageIcon("GUIImages/Buttons/gamePanelQuitButton.png"); 
		rollOverQuitButton = new ImageIcon("GUIImages/Buttons/rollOverGamePanelQuitButton.png"); 
		btnQuit = new JButton(quitButton);
                //hide quit button
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setFocusPainted(false);
		// button set
		btnQuit.setBounds(435, 520, 71, 46);
		btnQuit.setRolloverIcon(rollOverQuitButton);
		btnQuit.addActionListener(this);
		// mousehandler location
		modelController = new ModelController();
                mouseHandler = new MouseHandler(this, topLeftX, topLeftY, SQUARE_LENGTH, modelController); 
		add(btnQuit);
		add(background);
		
	}
	
	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		JButton source = (JButton)actionEvent.getSource();
		// Exit the game if quit is clicked
		if(source == btnQuit) {
			System.exit(0);
		}
	}
	
        //Set square setter method
	public void setSquareSelected(PieceLocation location) {
		squareSelected = location;
	}

}
