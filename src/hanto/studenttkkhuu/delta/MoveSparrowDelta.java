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

package hanto.studenttkkhuu.delta;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.AbsMoveSparrow;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * This class takes care of moving a Sparrow piece in Delta Hanto
 *
 */
public class MoveSparrowDelta extends AbsMoveSparrow{
	
	
	/**
	 * Constructor for MoveSparrowDelta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveSparrowDelta (Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	
	@Override
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY && pieces.get(hp) == null) {
				throw new HantoException("Butterfly must be placed before moving a different piece");
			}
		}
		final HantoCoordinateImpl from = new HantoCoordinateImpl(path.get(0));
		

		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getType() == SPARROW && hp.getColor() == color && pieces.get(hp) != null
					&& pieces.get(hp).equals(from)) {
				return hp;
			}
		}

		return null;
		
	}
	
	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) {
		HantoCoordinate[] path = { source, destination };
		return Arrays.asList(path);
	}

}
