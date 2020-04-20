/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * A Location Class. Holds X,Y Coordinates on the board.
 * 
 * @param xCoordinate X-position
 * @param yCoordinate Y-position
 * @author Sufyan Ahmed
 */

public class PieceLocation {
	public static final int ROWS = CheckersBoard.BOARD_ROWS;
	public static final int COLUMNS = CheckersBoard.BOARD_COLUMNS;
	private int xCoordinate, yCoordinate;

	/**
         * Check coordinates
         * @param x x-position
         * @param y y-position
	 */
	public PieceLocation(int x, int y) throws OutOfBoundsException{
		if(x < 0 || x >= ROWS || y < 0 || y >= COLUMNS) {
			throw new OutOfBoundsException("Illegal coordinates were given.");
		} else {
			xCoordinate = x;
			yCoordinate = y;
		}
	}

	/**
	 * #METHOD for x-position
	 * 
	 * @return X-Coordinate
	 */
	public int getX() { return this.xCoordinate; }

	/**
	 * #METHOD for y-position
	 * 
	 * @return Y-Coordinate
	 */
	public int getY() { return this.yCoordinate; }
	
	/**
         * #METHOD check of location is within the board movement
	 * @return True if the Location is within the boards bounds.
	 */
	public boolean inBoardBounds() {
                return xCoordinate >= 0 && xCoordinate < ROWS && yCoordinate >= 0 && yCoordinate < COLUMNS;
	}
	
	/**
	 * @param otherLoc Location
	 * @return true if the Locations have equal coordinates.
	 */
	public boolean checkIsSameLocation(PieceLocation otherLoc){
		return xCoordinate == otherLoc.getX() && yCoordinate == otherLoc.getY();
	}

        @Override
	public String toString() {
		return xCoordinate + "," + yCoordinate;
	}

}
