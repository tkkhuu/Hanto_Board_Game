/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.epsilon;

import static hanto.common.HantoPieceType.CRAB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studenttkkhuu.common.AbsMoveCrab;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * A class that takes care of moving a Crab in Epsilon Hanto
 */
public class MoveCrabEpsilon extends AbsMoveCrab{
	
	public MoveCrabEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		// The player made a resignation
		if (source == null && destination == null) {
			
			checkAvailableMove(color);
			
			return pieces;
		}
		
		return super.movePiece(source, destination, color);
	}
	
	@Override
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		
		if (path == null) {
			throw new HantoException("Crab can only walk one Hex at a time");
		}
		
		return super.moveToHex(path, color);
	}

	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination){
		
		// ========================= Move One Hex =========================
		if (isNeighborHex(source, destination)) {
			HantoCoordinate[] path = {source, destination};
			return Arrays.asList(path);
		}
		return null;
	}
	
	private void checkAvailableMove(HantoPlayerColor color)
			throws HantoPrematureResignationException {
		
		// If the crab pieces cannot be placed or all used, check the pieces on the board for valid move
		List<HantoPiece> crabPiecesPlaced = new ArrayList<HantoPiece>();
		
		for (HantoPiece p : pieces.keySet()) {
			if (p.getColor() == color && p.getType() == CRAB){// && pieces.get(p) != null) {
				crabPiecesPlaced.add(p);
			}
		}
		
		for (HantoPiece crabPiece : crabPiecesPlaced) {

//			final HantoCoordinateImpl crabPieceHCI = new HantoCoordinateImpl(pieces.get(crabPiece));
			List<HantoCoordinate> possibleMoves = getPossibleMoves(color, pieces, crabPiece);//getPo();
			int numberOfInvalidPath = possibleMoves.size();
			
			if (numberOfInvalidPath > 0) {
				throw new HantoPrematureResignationException();
			}
		}
	}
	
	/**
	 * Get the possible moves of a specific piece
	 * @param color Color of the piece
	 * @param pieces List of pieces in the game
	 * @param crabPiece Piece to be made
	 * @return List of hexes the piece can move to
	 */
	public List<HantoCoordinate> getPossibleMoves(HantoPlayerColor color, Map<HantoPiece, HantoCoordinate> pieces, HantoPiece crabPiece) {
		if (pieces.get(crabPiece) == null) { // This piece has not been placed
			List<HantoCoordinate> possibleMoves = getEmptyNeighborsOfSameColor(color, pieces);
			List<HantoCoordinate> possibleValidMoves = new ArrayList<HantoCoordinate>();
			for (HantoCoordinate toHex : possibleMoves) {
				try{
					movePiece(null, toHex, color);
					possibleValidMoves.add(toHex);
				} catch (HantoException he) {
					System.out.print("");
					//he.printStackTrace();
				}
				pieces.put(crabPiece, null);
			}
			
			return possibleValidMoves;
		} 
		
		else {
			final HantoCoordinateImpl crabPieceHCI = new HantoCoordinateImpl(pieces.get(crabPiece));
			List<HantoCoordinate> possibleMoves = crabPieceHCI.getNeighbors();
			List<HantoCoordinate> possibleValidMoves = new ArrayList<HantoCoordinate>();
			
			for (HantoCoordinate toHex : possibleMoves) {
				try {
					movePiece(crabPieceHCI, toHex, color);
					possibleValidMoves.add(toHex);
				} catch (HantoException he) {
					System.out.print("");
//					he.printStackTrace();
				}
				pieces.put(crabPiece, crabPieceHCI);
			}
			
			return possibleValidMoves;
			
		}
	}

}
