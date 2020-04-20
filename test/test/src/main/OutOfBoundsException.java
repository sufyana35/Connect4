/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 * Custom exception for cases involving out-of-bound locations.
 * @author Calum
 *
 */
public class OutOfBoundsException extends Exception {
	public OutOfBoundsException(){
		super();
	}
	
	public OutOfBoundsException(String error){
		super(error);
	}
}
