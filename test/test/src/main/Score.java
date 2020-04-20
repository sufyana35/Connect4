/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.Serializable;

/**
 * @author Calum
 *
 */
public class Score implements Serializable {

	private int wins;
	private int losses;
	private int gamesPlayed;

	public Score() {
		
	}
	
	/**
         *      @param wins
	 *  	@param The number of wins
         *      @param playedGames
	 *      @param losses The number of losses
	 *  	@param gamesPlayer The number of games played
	 *  	@return Return the instance of the games view.
	 */
	public Score(int wins, int losses, int playedGames) {
		super();
		this.wins = wins;
		this.losses = losses;
		this.gamesPlayed = playedGames;
	}
	
	/**
	 * @return integer value for the amount of losses
	 */
	public int getLosses() {
		return losses;
	}
	/**
	 * @param losses the amount of losses
	 */
	public void setLosses(int losses) {
		this.losses = losses;
	}
        /**
	 * @return integer value for amount of wins
	 */
	public int getWins() {
		return wins;
	}
	/**
	 * @param wins the amount of wins
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}
	/**
	 * @return integer value for the amount of games played
	 */
	public int getGamesPlayed() {
		return gamesPlayed;
	}
	/**
	 * @param gamesPlayed the amount of games played
	 */
	public void setGamesPlayed(int gamesPlayed) {
		this.gamesPlayed = gamesPlayed;
	}
	/**
	 * @return new integer value for the amount of wins.
	 */
	public int appendWins() {
		return wins = getWins() + 1;
	}
	/**
	 * @return new integer value for the amount of losses.
	 */
	public int appendLosses() {
		return losses = getLosses() + 1;
	}
	/**
	 * @return new integer value for the amount of games played.
	 */
	public int appendGamesPlayed() {
		return gamesPlayed = getGamesPlayed() + 1;
	}
	
	
}
