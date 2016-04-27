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

import static hanto.common.HantoPieceType.SPARROW;

import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.AbsMoveSparrow;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * This class takes care of moving the sparrow pieces in Beta
 *
 */
public class MoveSparrowBeta extends AbsMoveSparrow{

	/**
	 * Constructor for MoveSparrowBeta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveSparrowBeta(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		checkValidity(source, destination, color);
		
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);

		for (HantoPiece hp : pieces.keySet()) {
			
			if (pieces.get(hp) == null && hp.getType() == SPARROW && hp.getColor() == color) {
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
		
		if (!isButterflyPlacedByFourthMove(pieces, color)) {
			throw new HantoException("Butterfly must be placed by fourth turn");
		}
		
		if (source != null) {
			throw new HantoException("BetaHanto does not allow moving an existing piece on the board");
		}
	}

	@Override
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException {
		// TODO Auto-generated method stub
		return null;
	}






}
