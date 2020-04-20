/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import userInterface.controller.ScoreDataHandler;

/**
 * Create GUI interface for scoreboard
 * @author David Birtch
 */
public class ScorePanel extends JPanel {

	private ScoreDataHandler controller = new ScoreDataHandler();
	private JLabel wins, losses, gamesPlayed;
	
        /**
         * GUI code for scoreboard panel
         */
	public ScorePanel() {
		setLayout(null);
		ImagePanel background = new ImagePanel(new ImageIcon("GUIImages/Other/ScoreBoard.png").getImage(),"ScoreBoard");

                //create label
		wins = new JLabel(controller.getWin());
                //set font
		wins.setFont(new Font("Serif", Font.PLAIN, 45));
		wins.setBounds(500, 195, 50, 50);
		
                //create label
		losses = new JLabel(controller.getLoss());
                //set font
		losses.setFont(new Font("Serif", Font.PLAIN, 45));
                //set location
		losses.setBounds(500, 290, 50, 50);
		
                //create label
		gamesPlayed = new JLabel(controller.getGamesPlayed());
                //set font
		gamesPlayed.setFont(new Font("Serif", Font.PLAIN, 45));
                //set location
		gamesPlayed.setBounds(500, 400, 50, 50);
		
		add(wins);
		add(losses);
		add(gamesPlayed);
		add(background);
	}
}
