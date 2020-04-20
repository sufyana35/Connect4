/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import main.Score;

/**
 * @author Matthew Foggon 
 * 
 * Handle score data file
 *
 */
public class ScoreDataHandler {

	private ScoreDataHandler controller;
	private static final String fileName = "src/score.dat";
	
	public void saveScore(Score score) {
		ObjectOutputStream out = null;
		
        try {
            out = new ObjectOutputStream(new FileOutputStream(fileName));
            out.writeObject(score);
        } catch (IOException e) {
        	System.out.println("saveScore: Unable to write to file.");
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException IOE) {
                	System.out.println("saveScore: Unable to close file.");
                }
            }
        }
	}
	
	
	public Score scoreLoad() {
		ObjectInputStream in = null;
        Score score = null;
        try {
        	File file = new File(fileName); 
        	fileReset(); // This checks if the files exist or if the length of the file is approriate.
            in = new ObjectInputStream(new FileInputStream(file));
            score = (Score)in.readObject();
        } catch (IOException e) {
        	System.out.println("loadScore: Unable to read file.");
        } catch(ClassNotFoundException e) {
        	System.out.println("loadScore: Something went wrong.");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioe) {
                	System.out.println("loadScore: Unable to close file.");
                }
            }
        }
        return score;
    }
	
	private void fileReset() {
		Score score = null;
		File file = new File(fileName);
    	if(!file.exists() || file.length() == 0) {
    		try {
    			System.out.println(fileName + " was not found. Creating new file...");
    			file.createNewFile();
    		} catch (IOException ioe) {
    			System.out.println("Error occured...");
    			System.out.println("Was not able to create a new file.");
    		} finally {
    			System.out.println("Writing new data...");
    			score = new Score(0, 0, 0);
    			controller = new ScoreDataHandler();
        		controller.saveScore(score);
        		System.out.println("New data finished writing...");
    		}
    	}
	}


	public String getWin() {
		controller = new ScoreDataHandler();
		String wins;
		return wins = Integer.toString(controller.scoreLoad().getWins());
	}


	public String getGamesPlayed() {
		controller = new ScoreDataHandler();
		String gamesPlayed;
		return gamesPlayed = Integer.toString(controller.scoreLoad().getGamesPlayed());
	}


	public String getLoss() {
		controller = new ScoreDataHandler();
		String losses;
		return losses = Integer.toString(controller.scoreLoad().getLosses());
	}
}
