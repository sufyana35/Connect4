/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * Player colour, used by other checker components
 * 
 * ("B") Black, ("R") RED
 * 
 * @author Calum
 * 
 */
public enum PlayerColour {
	BLACK("B"), RED("R");
	private final String stringValue;

	/**
         * #METHOD set colour by string value
	 * 
	 * @param stringValue
	 */
	PlayerColour(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * Returns the original value of the colour
	 * 
	 * @return Ordinal corresponding to a single enumeration
	 */
	public int getOrdinal() {
		return this.ordinal();
	}

	@Override
	public String toString() {
		return this.stringValue;
	}
}
