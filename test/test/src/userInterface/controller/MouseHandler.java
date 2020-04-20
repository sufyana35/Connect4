/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.controller;

import main.*;
import userInterface.view.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import main.PieceLocation;
import main.OutOfBoundsException;

/**
 * MouseHandler class to take input from the gui. Uses mouse listeners to
 * identify the board piece selected.
 * 
 * @author Matthew Foggon
 */

public class MouseHandler implements MouseListener, MouseMotionListener {
	private Component component;
	private IModel modelController;
    private FrameSwitcher frameSwitcher;
	private Cursor cursorShape;
	
	private int squareLength, leftBound, rightBound, upperBound, lowerBound;
	private int clickNumber = 0;
	private boolean inBound;
	
	private GameBoard game;
	private Player player;
	
	private PieceLocation start, end;
	private int xBoardCoord, yBoardCoord;
	
	/**
         * This a constructor that is used to add the mouse handler to the proper
         * bounds
	 * 
	 * @param component Mouse handler
	 * @param topLeft top left side of board
	 * @param squareLength length of a single square on board
	 */
	public MouseHandler(Component component, int topLeftX, int topLeftY,
		int squareLength, IModel modelController) {
		this.game = GameBoard.getInstance();
		this.squareLength = squareLength;
		this.component = component;
		this.modelController = modelController;
		leftBound = topLeftX;
		upperBound = topLeftY;
		rightBound = 8 * squareLength + leftBound;
		lowerBound = 8 * squareLength + upperBound;

		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		cursorShape = component.getCursor();

	}

        @Override
	public void mousePressed(MouseEvent mouseEvent) {

	}

        @Override
	public void mouseReleased(MouseEvent mouseEvent) {

	}

        @Override
	public void mouseEntered(MouseEvent mouseEvent) {

	}

        @Override
	public void mouseDragged(MouseEvent mouseEvent) {

	}

	/**
         * MouseMoved listener for cursor within the boundaries of the board
	 * 
	 * @param mouseEvent The mouse event listened for.
	 */
        @Override
	public void mouseMoved(MouseEvent mouseEvent) {

		if (mouseEvent.getX() > leftBound && mouseEvent.getX() < rightBound
				&& mouseEvent.getY() > upperBound && mouseEvent.getY() < lowerBound) {
			component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			inBound = true;

		} else {
			component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			inBound = false;
		}
	}

        @Override
	public void mouseExited(MouseEvent mouseEvent) {

	}

	/**
	 * Listen for mouse clicks on the screen
	 * 
	 * @param mouseEvent mouse listener
	 */
        @Override
	public void mouseClicked(MouseEvent mouseEvent) {
                //If player is not Human or null, mouse events are ignored
		if(game.currentPlayer().CheckIsHuman() == false || game.currentPlayer() == null) {
			return;
		} else {
			player = game.currentPlayer();
		}
		
                //Jump piece code, select end point of jump
		if(game.currentPlayer().isContinueMove() == true){
			clickNumber = 1;
		}
		this.frameSwitcher = FrameSwitcher.getInstance();
		if (inBound) {
			xBoardCoord = (int) Math.floor((mouseEvent.getX() - leftBound)
					/ squareLength);
			yBoardCoord = (int) Math.floor((mouseEvent.getY() - upperBound)
					/ squareLength);
			clickNumber++;
		}
		
		
		if (clickNumber == 1) {
			try {
				
				start = new PieceLocation(xBoardCoord, yBoardCoord);
				modelController.setStartLocation(player,start);
				
				if(!modelController.validSelectionMade()) {
					clickNumber = 0;
					start = null;
					return;
                }
                frameSwitcher.updateGUI(start);
				
			} catch (OutOfBoundsException e1) {
			}
		} else if (clickNumber == 2) {
			try {
                frameSwitcher.setHighlight(false);
                frameSwitcher.updateGUI();
				end = new PieceLocation(xBoardCoord, yBoardCoord);
				modelController.setEndLocation(player, end);
				
				if(!modelController.validSelectionMade()){
					clickNumber = 0;
					start = null;
					end = null;
					return;
				} else {
					System.out.println(start.toString() + "    " + end.toString());
					modelController.makeMove(player);
                    if (game.currentPlayer().isContinueMove()==true){
                        frameSwitcher.updateGUI(end);
                        }
				}
				
			} catch (OutOfBoundsException e1) {
			}
			clickNumber = 0;
		}
		xBoardCoord = 0;
		yBoardCoord = 0;

	}
}
