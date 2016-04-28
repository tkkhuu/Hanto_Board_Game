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

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.studenttkkhuu.common.AbsMoveCrab;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * A class that takes care of moving the Crab piece in Delta Hanto
 * @author Tri
 *
 */
public class MoveCrabDelta extends AbsMoveCrab {

	/**
	 * Constructor for MoveCrabDelta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveCrabDelta (Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}
	
	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination){
		
		// ========================= Move One Hex =========================
		if (isNeighborHex(source, destination)) {
			HantoCoordinate[] path = {source, destination};
			return Arrays.asList(path);
		}
		
		// ========================= Move Two Hex =========================
		final HantoCoordinateImpl from = new HantoCoordinateImpl(source);
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		
		List<HantoCoordinate> sourceNeighbors = from.getNeighbors();
		List<HantoCoordinate> destinationNeighbors = to.getNeighbors();
		
		for (HantoCoordinate m : sourceNeighbors) {
			if (destinationNeighbors.contains(m) && getPieceAt(m) == null) {
				HantoCoordinate[] path = {source, m ,destination};
				return Arrays.asList(path);
			}
		}
		
		// ========================= Move Three Hex =========================
		HantoCoordinate sourceNeigh = null;
		HantoCoordinate destinationNeigh = null;
		
		for (HantoCoordinate s : sourceNeighbors) {
			for (HantoCoordinate d : destinationNeighbors) {
				if (isNeighborHex(s, d) && getPieceAt(s) == null && getPieceAt(d) == null && getNumberOfOccupiedHex(s) > 1 &&getNumberOfOccupiedHex(d) >= 1) {
					sourceNeigh = s;
					destinationNeigh = d;
				}
			}
		}
		
		if (sourceNeigh != null && destinationNeigh != null) {
			HantoCoordinate[] path = {source, sourceNeigh, destinationNeigh, destination};
			return Arrays.asList(path);
		}
		
		// ========================= No Path Found =========================
		return null;
		
	}
	
	private int getNumberOfOccupiedHex(HantoCoordinate coordinate) {
		int result = 0;
		for (HantoCoordinate hc : pieces.values()) {
			if (isNeighborHex(hc, coordinate)) {
				result++;
			}
		}
		return result;
	}



}
