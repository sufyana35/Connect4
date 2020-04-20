/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.view;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import main.*;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import userInterface.controller.FrameSwitcher;

/**
 * @author David Birtch
 *
 */
public class ImagePanel extends JPanel {
	
	private Image image;
	private int upperBound = 157;
	private int leftBound = 291;
	private String panelName;
    
        private ImageIcon blueHats = new ImageIcon("GUIImages/Other/BlueHat.png"); 
	private ImageIcon redHats = new ImageIcon("GUIImages/Other/RedHat.png"); 
	private ImageIcon blueKing = new ImageIcon("GUIImages/Other/BlueHatKing.png"); 
	private ImageIcon redKing = new ImageIcon("GUIImages/Other/RedHatKing.png"); 
	private PlayerColour colour;
        private FrameSwitcher frameSwitcher;

	public ImagePanel(Image image, String panelName) {
		this.image = image;
		this.panelName = panelName;
		Dimension size = new Dimension(image.getWidth(null), image.getHeight(null));
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		setSize(size);
		setLayout(null);
	}

	public void paintComponent(Graphics g) {		
		g.drawImage(image,  0 , 0 ,  null);
		
		if (panelName != "GamePanel"){
            return;
            }
        frameSwitcher = frameSwitcher.getInstance();
        frameSwitcher.updateGUI();
        if (frameSwitcher.highlight()){
            PieceLocation square = frameSwitcher.highlightSquare();
            g.setColor(Color.RED);
            g.drawRect(square.getX()*44+leftBound, square.getY()*44+upperBound,
        44,44);
            }
        String[][] boardArray = frameSwitcher.stringBoard();
        for(int row = 0; row <= 7; row++){
            for (int column = 0; column <=7; column++){
				
                if(boardArray[column][row] == null){
                    continue;
                }
                if(null != boardArray[column][row])switch (boardArray[column][row]) {
                    case "Black King":
                        redKing.paintIcon(this, g, row*44+leftBound, column*44+upperBound);
                        break;
                    case "Black":
                        redHats.paintIcon(this, g, row*44+leftBound, column*44+upperBound);
                        break;
                    case "Red King":
                        blueKing.paintIcon(this, g, row*44+leftBound, column*44+upperBound);
                        break;
                    case "Red":
                        blueHats.paintIcon(this, g, row*44+leftBound, column*44+upperBound);
                        break;
                    default:
                        break;
                }

            }
        }
        
	}

}
