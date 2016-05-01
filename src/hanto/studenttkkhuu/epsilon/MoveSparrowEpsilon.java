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

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studenttkkhuu.common.AbsMoveSparrow;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

/**
 * A class that takes care of moving a Sparrow in Epsilon Hanto
 */
public class MoveSparrowEpsilon extends AbsMoveSparrow{

	public MoveSparrowEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
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
			throw new HantoException("Sparrow can only fly upto 4 hexes distance");
		}
		
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
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException {
		
		final HantoCoordinateImpl from = new HantoCoordinateImpl(source);
		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);
		
		HantoCoordinate[] path = { source, destination };
		
		List<HantoCoordinate> firstLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> secondLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> thirdLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> fourthLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> hexVisited = new ArrayList<HantoCoordinate>();
		
		// Find the neighbors on the first layer
		firstLayer.addAll(from.getNeighbors()); 
		hexVisited.addAll(firstLayer);
		
		if (firstLayer.contains(to)) {
			return Arrays.asList(path);
		}
		
		// Find the neighbor on the second layer
		for (HantoCoordinate hc : firstLayer){
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl (hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					secondLayer.add(hc_neigh);
				}
			}
		}
		
		if (secondLayer.contains(to)) {
			return Arrays.asList(path);
		}
		
		// Find the neighbor on the third layer
		for (HantoCoordinate hc : secondLayer) {
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl(hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					thirdLayer.add(hc_neigh);
				}
			}
		}
		
		if (thirdLayer.contains(to)) {
			return Arrays.asList(path);
		}
		
		// Find the neighbor on the fourth layer
		for (HantoCoordinate hc : thirdLayer) {
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl(hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					fourthLayer.add(hc_neigh);
				}
			}
		}
		
		if (fourthLayer.contains(to)) {
			return Arrays.asList(path);
		}
		
		return null;
	}
	
	private void checkAvailableMove(HantoPlayerColor color)
			throws HantoPrematureResignationException {

		List<HantoPiece> sparrowPiecesPlaced = new ArrayList<HantoPiece>();
		
		for (HantoPiece p : pieces.keySet()) {
			if (p.getColor() == color && p.getType() == SPARROW){// && pieces.get(p) != null) {
				sparrowPiecesPlaced.add(p);
			}
		}
		
		for (HantoPiece sparrowPiece : sparrowPiecesPlaced) {
			List<HantoCoordinate> possibleMoves = getPossibleMoves(color, pieces, sparrowPiece);
			int numberOfValidPath = possibleMoves.size();
			
			if (numberOfValidPath >0 ) {
				throw new HantoPrematureResignationException();
			}
		}
	}
	
	
	private List<HantoCoordinate> getPossibleMove(HantoCoordinate source) {
		
		final HantoCoordinateImpl from = new HantoCoordinateImpl(source);
		
		List<HantoCoordinate> firstLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> secondLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> thirdLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> fourthLayer = new ArrayList<HantoCoordinate>();
		List<HantoCoordinate> hexVisited = new ArrayList<HantoCoordinate>();
		
		// Find the neighbors on the first layer
		firstLayer.addAll(from.getNeighbors()); 
		hexVisited.addAll(firstLayer);
		
		// Find the neighbor on the second layer
		for (HantoCoordinate hc : firstLayer){
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl (hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					secondLayer.add(hc_neigh);
				}
			}
		}
		
		// Find the neighbor on the third layer
		for (HantoCoordinate hc : secondLayer) {
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl(hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					thirdLayer.add(hc_neigh);
				}
			}
		}
		
		// Find the neighbor on the fourth layer
		for (HantoCoordinate hc : thirdLayer) {
			HantoCoordinateImpl temp_hc = new HantoCoordinateImpl(hc);
			for (HantoCoordinate hc_neigh : temp_hc.getNeighbors()) {
				if (!hexVisited.contains(hc_neigh)) {
					hexVisited.add(hc_neigh);
					fourthLayer.add(hc_neigh);
				}
			}
		}
		
		List<HantoCoordinate> coordinateToBeRemoved = new ArrayList<>();
		
		for (HantoCoordinate hc : hexVisited) {
			if (!isDestinationConnected(hc) || getPieceAt(hc) != null) {
				coordinateToBeRemoved.add(hc);
			}
		}
		
		for (HantoCoordinate hc : coordinateToBeRemoved) {
			hexVisited.remove(hc);
		}

		
		return hexVisited;
		
	}

	
	/**
	 * Get the possible moves of a specific piece
	 * @param color Color of the piece
	 * @param pieces List of pieces in the game
	 * @param sparrowPiece Piece to be made
	 * @return List of hexes the piece can move to
	 */
	public List<HantoCoordinate> getPossibleMoves(HantoPlayerColor color, Map<HantoPiece, HantoCoordinate> pieces, HantoPiece sparrowPiece) {
		if (pieces.get(sparrowPiece) == null) { // This piece has not been placed
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
				pieces.put(sparrowPiece, null);
			}
			
			return possibleValidMoves;
		} 
		
		else {
			final HantoCoordinateImpl sparrowPieceHCI = new HantoCoordinateImpl(pieces.get(sparrowPiece));
			List<HantoCoordinate> possibleMoves = getPossibleMove(sparrowPieceHCI);
			List<HantoCoordinate> possibleValidMoves = new ArrayList<HantoCoordinate>();
			
			for (HantoCoordinate toHex : possibleMoves) {
				try {
					movePiece(sparrowPieceHCI, toHex, color);
					possibleValidMoves.add(toHex);
				} catch (HantoException he) {
					System.out.print("");
//					he.printStackTrace();
				}
				pieces.put(sparrowPiece, sparrowPieceHCI);
			}
			
			return possibleValidMoves;
			
		}
	}
}
