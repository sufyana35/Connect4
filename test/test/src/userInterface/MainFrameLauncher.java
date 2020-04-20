/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package userInterface;

import javax.swing.SwingUtilities;

import main.GameBoard;

import userInterface.controller.FrameSwitcher;
import userInterface.controller.ModelController;
import userInterface.view.MainFrame;

public class MainFrameLauncher {

	private ModelController modelController;
	private static FrameSwitcher controller;
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {  //Netbean creation
                    try{
                        runGUI(); // Runs the GUI
                    }
                    catch (Exception event) {
                    }
                });
		
	}
        
        /**
         * Connection model between the model and controller
	 */
	public static void runGUI() {
		ModelController modelController = new ModelController(); // Create an instance of the modelController
		MainFrame frame = new MainFrame(); // Create an instance of the MainFrame
		controller = FrameSwitcher.getInstance(frame); // Create an instance of the FrameSwitcher
		frame.start(); // Start the frame
		frame.SwitchFrames(controller); // Set the controller
		controller.setCoordinates(modelController); // Set a handle on the modelController for the frameSwitcher
	}

}
