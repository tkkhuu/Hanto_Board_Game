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
import static hanto.common.HantoPlayerColor.BLUE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * An Abstract class for the MovePiece classes
 *
 */
public abstract class AbsMovePiece implements MovePiece{
	
	protected int moveCount;
	protected Map<HantoPiece, HantoCoordinate> pieces;
	protected HantoPlayerColor firstMoveColor = BLUE;

	/**
	 * Check if two Hex are next to each other
	 * @param firstHex The first hex
	 * @param secondHex The second hex
	 * @return True if next to each other
	 */
	protected boolean isNeighborHex(HantoCoordinate firstHex, HantoCoordinate secondHex){
		
		if (firstHex == null || secondHex == null) {
			return false;
		}
		
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
	 * Determine whether a destination hex is disconnected
	 * @param destination
	 * @return True if a disconnection is detected
	 */
	protected boolean isDestinationDisconnected(HantoCoordinate destination) {
		for (HantoCoordinate hc : pieces.values()) {
			if (hc != null) {
				if (isNeighborHex(destination, hc)) {
					return true;
				}
			}
		}

		return false;

	}

	/**
	 * Check whether a move is valid
	 * @param source The source Hex
	 * @param destination The destination Hex
	 * @param color Color of the player making this move
	 * @throws HantoException
	 */
	protected void checkValidity(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor color)
			throws HantoException {
		
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);

		
		if (moveCount == 0) {
			if (destination.getX() != 0 || destination.getY() != 0) {
				throw new HantoException("First move has to be placed at the origin");
			}
		}
		
		if (pieces.containsValue(to)) {
			throw new HantoException("The destination Hex is currently occupied");
		}

		if (!isDestinationDisconnected(to) && (moveCount > 0)) {
			throw new HantoException("The destination Hex is disconnected");
		}

		
	}
	
	/**
	 * Check if the butterfly has been placed by fourth move
	 * @param listOfCurrentMove
	 * @param color
	 * @return True if Butterfly has been placed by fourth move
	 */
	protected boolean isButterflyPlacedByFourthMove(Map<HantoPiece, HantoCoordinate> listOfCurrentMove, HantoPlayerColor color) {

		HantoPiece butterfly = null;

		for (HantoPiece hp : listOfCurrentMove.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY) {
				butterfly = hp;
			}
		}

		if (listOfCurrentMove.get(butterfly) == null) {
			if (color == firstMoveColor && moveCount >= 5) {
				return false;
			} else if (color != firstMoveColor && moveCount >= 6) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check if a move creates a disconnection
	 * @param temp The piece that moved
	 * @throws HantoException
	 */
	protected void checkDisconnection(HantoPiece temp) throws HantoException {
		// Check if there's any disconnection
		List<HantoCoordinate> connection = new ArrayList<HantoCoordinate>();
		connection.add(pieces.get(temp));
		int numberOfOccupiedHex = 0;

		for (HantoCoordinate hc : pieces.values()) {
			if (hc != null) {
				numberOfOccupiedHex++;
			}
		}

		for (int i = 0; i < numberOfOccupiedHex; i++) {
			for (HantoCoordinate hc : pieces.values()) {
				if (hc != null) {
					if (connection.size() > i && isNeighborHex(connection.get(i), hc) && !connection.contains(hc)) {
						connection.add(hc);
					}
				}
			}
		}

		if (numberOfOccupiedHex != connection.size()) {
			throw new HantoException("The move create a disconnection between the pieces");
		}
	}

	/**
	 * Get the piece at the input coordinate
	 * @param coordinate
	 * @return The piece at the input coordinate
	 */
	protected HantoPiece getPieceAt(HantoCoordinate coordinate){
		for (HantoPiece hp : pieces.keySet()) {
			if (pieces.get(hp) != null && pieces.get(hp).equals(coordinate)) {
				return hp;	
			}
		}
		
		return null;
	}
	
	
	/**
	 * This function takes care of a single move
	 * @param path The path that the piece used to move
	 * @param color The color of the player making this move
	 * @return The piece that was moved
	 * @throws HantoException
	 */
	protected abstract HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException;
	
	protected abstract List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException;
	

}
