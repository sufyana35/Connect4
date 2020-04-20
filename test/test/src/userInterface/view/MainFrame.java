/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * JFRAME. JPANELS are switched according to the FrameSwitcher class.
 * @author David Birtch
 */
public class MainFrame extends JFrame implements ActionListener {

	private JFrame mainContainer = new JFrame("Hat Checkers");
	private PanelListener panelListener; // Create a handle on the FrameSwitcher
	private JPanel mainMenu;
	private static JButton btnSinglePlayer, btnMultiPlayer, btnScoreBoards,
	btnQuit;
	private ImagePanel background;
	private ImageIcon singlePlayerButton, rollOverSinglePlayerButton,
	multiPlayerButton, scoreBoardButton, rollOverScoreBoardButton,
	rollOverMultiPlayerButton, quitButton, rollOverQuitButton;

	/**
	 * #METHOD
         * Initialise GUI and start Main Menu Always
	 */
	public void start() {
		mainContainer(mainMenu());
	}

	/**
         * #METHOD
	 * @param frame Takes a JPanel, which will be displayed.
	 * @return Returns the container (JFrame)
	 */
	private JFrame mainContainer(JPanel frame) {
		mainContainer.getContentPane().add(frame);
		mainContainer.pack();

		mainContainer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainContainer.setSize(925, 608);
		mainContainer.setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		mainContainer.setLocation(dim.width / 2 - mainContainer.getSize().width
				/ 2, dim.height / 2 - mainContainer.getSize().height / 2);
		mainContainer.setResizable(false);

		return mainContainer;
	}
	
    /**
     * #METHOD
     * Code for creating the main menu buttons and identifying the images relating to it
     * @return the view (buttons)
     */
	private JPanel mainMenu() {
		mainMenu = new JPanel();
		mainMenu.setLayout(null);
		
		background = new ImagePanel(new ImageIcon(
				"GUIImages/Other/newBackground.png").getImage(), "MainMenu");
		
		
		singlePlayerButton = new ImageIcon("GUIImages/Buttons/singlePlayerButton.png");
		rollOverSinglePlayerButton = new ImageIcon(
				"GUIImages/Buttons/rollOverSinlgePlayerButton.png");
		btnSinglePlayer = new JButton(singlePlayerButton);
		btnSinglePlayer.setOpaque(false);
		btnSinglePlayer.setContentAreaFilled(false);
		btnSinglePlayer.setBorderPainted(false);
		btnSinglePlayer.setFocusPainted(false);
		btnSinglePlayer.setBounds(320, 25, 275, 130);
		btnSinglePlayer.setRolloverIcon(rollOverSinglePlayerButton);
		btnSinglePlayer.addActionListener(this);
		mainMenu.add(btnSinglePlayer);
		
		multiPlayerButton = new ImageIcon("GUIImages/Buttons/multiPlayerButton.png");
		rollOverMultiPlayerButton = new ImageIcon(
				"GUIImages/Buttons/rollOverMultiPlayerButton.png");
		btnMultiPlayer = new JButton(multiPlayerButton);
		btnMultiPlayer.setOpaque(false);
		btnMultiPlayer.setContentAreaFilled(false);
		btnMultiPlayer.setBorderPainted(false);
		btnMultiPlayer.setFocusPainted(false);
		btnMultiPlayer.setBounds(320, 170, 275, 130);
		btnMultiPlayer.setRolloverIcon(rollOverMultiPlayerButton);
		btnMultiPlayer.addActionListener(this);
		mainMenu.add(btnMultiPlayer);

		scoreBoardButton = new ImageIcon("GUIImages/Buttons/scoreBoardButton.png");
		rollOverScoreBoardButton = new ImageIcon(
				"GUIImages/Buttons/rollOverScoreBoardButton.png");
		btnScoreBoards = new JButton(scoreBoardButton);
		btnScoreBoards.setOpaque(false);
		btnScoreBoards.setContentAreaFilled(false);
		btnScoreBoards.setBorderPainted(false);
		btnScoreBoards.setFocusPainted(false);
		btnScoreBoards.setBounds(13, 380, 225, 73);
		btnScoreBoards.setRolloverIcon(rollOverScoreBoardButton);
		btnScoreBoards.addActionListener(this);
		mainMenu.add(btnScoreBoards);

		quitButton = new ImageIcon("GUIImages/Buttons/quitButton.png");
		rollOverQuitButton = new ImageIcon("GUIImages/Buttons/rollOverQuitButton.png");
		btnQuit = new JButton(quitButton);
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setFocusPainted(false);
		btnQuit.setBounds(45, 460, 120, 85);
		btnQuit.setRolloverIcon(rollOverQuitButton);
		btnQuit.addActionListener(this);
		mainMenu.add(btnQuit);
		
		mainMenu.add(background);
		
		return mainMenu;
	}

    /**
     * #ACCESSOR panel listener
     * @param panelListener what Jpanel
     */
	public void SwitchFrames(PanelListener panelListener) {
		this.panelListener = panelListener;
	}

        @Override
	public void actionPerformed(ActionEvent actionEvent) {
		JButton source = (JButton)actionEvent.getSource();
		int gameMode = -1;
		if (source == btnSinglePlayer) {
			if (panelListener != null) { 
				gameMode = 1;
				mainContainer.getContentPane().removeAll();
				mainContainer.invalidate();
				mainContainer(panelListener.gamePanel(gameMode));
				mainContainer.validate();
				mainContainer.repaint();
			}
		} else if (source == btnMultiPlayer) {
			if (panelListener != null) {
				gameMode = 2;
				mainContainer.getContentPane().removeAll();
				mainContainer.invalidate();
				mainContainer(panelListener.gamePanel(gameMode));
				mainContainer.validate();
				mainContainer.repaint();
			}
		} else if (source == btnScoreBoards) {
			if (panelListener != null) {
				mainContainer.getContentPane().removeAll();
				mainContainer.invalidate();
				mainContainer(panelListener.scoreBoard());
				mainContainer.validate();
				mainContainer.repaint();
			}
		} else {
			System.exit(0);
		}
	}

}
