/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.gamma;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.AbsMoveSparrow;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * The MovablePiece class for Sparrow
 * 
 * @author Tri
 *
 */
public class MoveSparrowGamma extends AbsMoveSparrow {

	/**
	 * Constructor for MoveSparrow
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveSparrowGamma(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
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
		final HantoCoordinateImpl to = new HantoCoordinateImpl(path.get(1));
		

		HantoCoordinateImpl secondCommonNeighBor = null, firstCommonNeighbor = null;

		for (HantoCoordinate hc : pieces.values()) {
			if (hc != null && isNeighborHex(from, hc) && isNeighborHex(to, hc)) {
				if (firstCommonNeighbor == null) {
					firstCommonNeighbor = new HantoCoordinateImpl(hc);
				} else if (secondCommonNeighBor == null) {
					secondCommonNeighBor = new HantoCoordinateImpl(hc);
				}
			}
		}

		if (firstCommonNeighbor != null && secondCommonNeighBor != null) {
			throw new HantoException("The way is blocked to make a slide");
		}

		HantoPiece sparrowPiece = null;

		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getType() == SPARROW && hp.getColor() == color && pieces.get(hp) != null
					&& pieces.get(hp).equals(from)) {
				sparrowPiece = hp;
				break;
			}
		}

		return sparrowPiece;
	}

	
	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException{
		
		// ========================= Move One Hex =========================
		if (isNeighborHex(source, destination)) {
			HantoCoordinate[] path = {source, destination};
			return Arrays.asList(path);
		} else {
			throw new HantoException("A piece can only walk to one adjacent Hex");
		}
		
	}

}
