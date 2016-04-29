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

package hanto.studenttkkhuu.beta;

import static hanto.common.HantoPieceType.BUTTERFLY;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.AbsMoveButterfly;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * A class that takes care of moving the butterfly piece in Beta Hanto
 *
 */
public class MoveButterflyBeta extends AbsMoveButterfly{

	/**
	 * Constructor for MoveButterflyBeta
	 * @param pieces List of pieces placed on the board
	 * @param moveCount Number of move made
	 */
	public MoveButterflyBeta(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		checkValidity(source, destination, color);
		
		HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY) {
				pieces.put(hp, to);
				return pieces;
			}
		}

		
		return null;
	}
	
	@Override
	protected void checkValidity(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor color)
			throws HantoException {

		super.checkValidity(source, destination, color);

		for (HantoPiece hp : pieces.keySet()) {
			if (pieces.get(hp) != null && hp.getColor() == color && hp.getType() == BUTTERFLY) {
				throw new HantoException("The Butterfly piece has been placed");
			}
		}
	}


	
	
	
	
}
