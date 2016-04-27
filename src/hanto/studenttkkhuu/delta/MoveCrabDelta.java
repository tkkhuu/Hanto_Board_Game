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

import static hanto.common.HantoPieceType.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.AbsMovePiece;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * A class that takes care of moving the Crab piece in Delta Hanto
 * @author Tri
 *
 */
public class MoveCrabDelta extends AbsMovePiece {

	/**
	 * Constructor for MoveCrabDelta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveCrabDelta (Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		this.pieces = pieces;
		this.moveCount = moveCount;
	}
	
	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		checkMoveValidity(source, destination, color);

		if (moveCount == 0) {
			firstMoveColor = color;
		}

		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);

		HantoPiece sparrowPiece = null;

		if (source == null) { // Place a new piece from outside the board
			for (HantoPiece hp : pieces.keySet()) {
				if (pieces.get(hp) == null && hp.getType() == CRAB && hp.getColor() == color) {
					sparrowPiece = hp;
					pieces.put(sparrowPiece, to);
					return pieces;
				}
			}
		} else {
			
			List<HantoCoordinate> path = getPath(source, destination);
			HantoPiece temp = moveToHex(path, color);
			pieces.put(temp, to);

			checkDisconnection(temp);
			return pieces;
			
		}
		return null;
	}
	
	private void checkMoveValidity(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor color)
			throws HantoException {

		super.checkValidity(source, destination, color);
		
		HantoCoordinateImpl from = null;
		
		if (source != null){
			from = new HantoCoordinateImpl(source);
		}
		
		if (from != null && !pieces.containsValue(from)) {
			throw new HantoException("The source Hex is empty");
		}

		if (!isButterflyPlacedByFourthMove(pieces, color)) {
			throw new HantoException("Butterfly must be placed by fourth turn");
		}

		if (moveCount > 1 && source == null) {
			for (HantoPiece hp : pieces.keySet()) {
				if (pieces.get(hp) != null && isNeighborHex(pieces.get(hp), destination) && hp.getColor() != color) {
					throw new HantoException("Beside first move, the pieces cannot be placed next to opposite color");
				}
			}
		}
		
		for (HantoPiece hc : pieces.keySet()) {
			HantoCoordinate temp = pieces.get(hc);
			if (temp != null && temp.equals(from) && hc.getColor() != color) {
				throw new HantoException("You cannot move a piece of a different player");
			}
		}
		
		for (HantoPiece hc : pieces.keySet()) {
			HantoCoordinate temp = pieces.get(hc);
			if (temp != null && temp.equals(from) && hc.getType() != CRAB) {
				throw new HantoException("You cannot change the type of a piece");
			}
		}
	}
	
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY && pieces.get(hp) == null) {
				throw new HantoException("Butterfly must be placed before moving a different piece");
			}
		}
		
		if (path == null) {
			throw new HantoException ("No path found");
		}
		
		HantoPiece sourcePiece = null;
		int numberOfHexTravelled = 0;
		
		for (int i = 0; i < path.size() - 1; i++) {
			final HantoCoordinateImpl from = new HantoCoordinateImpl(path.get(i));
			final HantoCoordinateImpl to = new HantoCoordinateImpl(path.get(i + 1));
			HantoCoordinateImpl secondCommonNeighBor = null, firstCommonNeighbor = null;
			
			for (HantoCoordinate hc : pieces.values()) {
				if (isNeighborHex(from, hc) && isNeighborHex(to, hc)) {
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
			
			numberOfHexTravelled++;
			
			for (HantoPiece hp : pieces.keySet()) {
				if (hp.getType() == CRAB && hp.getColor() == color && pieces.get(hp) != null && pieces.get(hp).getX() == from.getX() && pieces.get(hp).getY() == from.getY()) {
					sourcePiece = hp;
				}
			}
		}
		
		return (numberOfHexTravelled == path.size() - 1 ? sourcePiece : null);
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
