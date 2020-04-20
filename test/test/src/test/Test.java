/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import main.*;
import algorithm.*;
import userInterface.controller.*;

public class Test {
    private CheckersBoard board = new CheckersBoard();
    private GameBoard game;
    private Player playerOne = new HumanPlayer(PlayerColour.RED, board);
    private Player playerTwoHuman = new HumanPlayer(PlayerColour.RED, board);
    private Player playerTwo = new AIPlayer(PlayerColour.BLACK, board);
    private boolean passed = true;
    private PieceLocation start;
    private PieceLocation end;
    
    public Test(){
        System.out.print(testBoardHumanMove() + testBoardAIMove() + testBoardInvalidMove()
            + testGameInitialization());
        if (passed){
            System.out.println("\n\nALL TESTS PASSED.");
            }
    }
    
    public String testBoardHumanMove(){
        board.boardInitialize();
        try{
            start = new PieceLocation(1,5);
            end = new PieceLocation(2,4);
            BoardMove move1 = new BoardMove(playerOne, start, end, board, false);
            board.movePiece(playerOne, move1);
            if (board.getArray()[5][1] == null && board.getArray()[4][2] != null) {
                return "\nTestBoardHumanMove PASSED";
            }else{
                passed = false;
                return "\nThe board did not move the proper pieces";
            }
        }catch(OutOfBoundsException oobe){
            passed = false;
            return "\nBoard move Locations improperly initialized";
        }    
    }
    public String testBoardAIMove(){
        board.boardReset();
        playerTwo = new AIPlayer(PlayerColour.BLACK, board);
        Piece[][] firstBoard = board.getArray();
        Piece[][] movedBoard = board.getArray();
        if (firstBoard != movedBoard){
            passed = false;
            return "\nThe AI player did not correctly set a move to be made";
        }else{
            return "\nTestBoardAIMove PASSED";
        }
    }
    public String testBoardInvalidMove(){
        board.boardReset();
        try{
            start = new PieceLocation(7,5);
            end = new PieceLocation(8,4);
            BoardMove move3 = new BoardMove(playerOne, start, end, board, true);
            board.movePiece(playerOne, move3);
            if (board.getArray()[5][7] == null){
                passed = false;
                return "\nTestBoardInvalidMove FAILED, piece was moved to an invalid location";
            }else{
                return "\nTestBoardInvalidMove PASSED.";
                }
        }catch(OutOfBoundsException oobe){
            return "\nTestBoardInvalidMove PASSED.";
            }
        }
    public String testGameInitialization(){
        game = game.getInstance();
        game.initialize(2);
        board = game.getBoard();
        playerOne = game.getRed();
        playerTwoHuman = game.getBlack();
        if (playerOne.getPieces().length != 12 || playerTwoHuman.getPieces().length != 12){
            passed = false;
            return "\nTestGameInitialization FAILED, player piece arrays not full.";

        }else{
            return "\nTestGameInitialization PASSED.";
            } 
    }   

    public static void main(String[] args){
        Test test = new Test();
    }
}
