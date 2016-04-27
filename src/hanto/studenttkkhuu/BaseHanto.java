/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studenttkkhuu;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;
import hanto.studenttkkhuu.common.HantoPieceImpl;

/**
 * An Abstract Class for different version of HantoGame
 *
 */
public abstract class BaseHanto implements HantoGame {
	
	protected boolean gameOver = false;
	protected HantoPlayerColor whichColor = BLUE;
	protected int moveCount = 0;
	
	protected HantoPiece blueButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	protected HantoPiece redButterfly = new HantoPieceImpl(RED, BUTTERFLY);
	
	protected Map<HantoPiece, HantoCoordinate> pieces;

	
	/**
	 * Switch turn between RED and BLUE player
	 */
	protected void switchPlayerTurn(){
		whichColor = ((whichColor == RED) ? BLUE : RED);
	}
	
	/**
	 * Increment the move count after a player made a move
	 */
	protected void incrementMoveCount(){
		moveCount++;
	}
	
	/**
	 * Check if two Hex are next to each other
	 * @param firstHex The first hex
	 * @param secondHex The second hex
	 * @return True if next to each other
	 */
	private boolean isNeighborHex(HantoCoordinate firstHex, HantoCoordinate secondHex){
		
		return 	// The two neighbors that has the same X
				( (firstHex.getX() == secondHex.getX()) && 
				((firstHex.getY() == secondHex.getY() - 1) || (firstHex.getY() == secondHex.getY() + 1)) ) 
				||
				// The two neighbors that has the same Y
				( (firstHex.getY() == secondHex.getY()) &&
				((firstHex.getX() == secondHex.getX() - 1) || (firstHex.getX() == secondHex.getX() + 1)) ) 
				|| 
				// The other two neighbors
				( (firstHex.getX() == secondHex.getX() - 1) && (firstHex.getY() == secondHex.getY() + 1) ) 
				|| 
				( (firstHex.getY() == secondHex.getY() - 1) && (firstHex.getX() == secondHex.getX() + 1) );
	}
	
	
	/**
	 * Set the color that gets the first move for the game
	 * @param c Color to get the first move
	 */
	public void setFirstMoveColor(HantoPlayerColor c){
		whichColor = c;
	}
	
	
	@Override
	public HantoPiece getPieceAt(HantoCoordinate coordinate)
	{
		final HantoCoordinateImpl where = new HantoCoordinateImpl(coordinate);
		
		for (HantoPiece hp : pieces.keySet()){
			HantoCoordinate temp = pieces.get(hp);
			if (pieces.get(hp) != null) {
				if (temp.equals(where)) {
					return hp;
				}
			}
		}
		
		return null;
	}
	
	/**
	 * Check if the game over condition has met
	 * @return OK, DRAW, RED_WINS or BLUE_WINS
	 */
	protected MoveResult checkGameOverCondition(){
		
		if(pieces == null) {
			return OK;
		}
		
		if (pieces.get(blueButterfly) == null 
				&& pieces.get(redButterfly) == null) {
			return OK;
		}
		
		int numberOfHexSurroundingBlue = 0, numberOfHexSurroundingRed = 0;
		for (HantoCoordinate hc : pieces.values()) {
		
			if (hc != null && pieces.get(blueButterfly) != null && isNeighborHex(pieces.get(blueButterfly), hc)) {
				numberOfHexSurroundingBlue++;
			}
			if (hc != null && pieces.get(redButterfly) != null && isNeighborHex(pieces.get(redButterfly), hc)) {
				numberOfHexSurroundingRed++;
			}
		}

		if (numberOfHexSurroundingBlue == 6 && numberOfHexSurroundingRed == 6) {
			gameOver = true;
			return DRAW;
		}
		
		if (numberOfHexSurroundingBlue == 6){
			gameOver = true;
			return RED_WINS;
		}
		
		if (numberOfHexSurroundingRed == 6){
			gameOver = true;
			return BLUE_WINS;
		}
		
		return null;
	}
	
	/*
	 * @see hanto.common.HantoGame#getPrintableBoard()
	 */
	@Override
	public String getPrintableBoard()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
