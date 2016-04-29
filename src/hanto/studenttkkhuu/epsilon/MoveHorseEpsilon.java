package hanto.studenttkkhuu.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.HORSE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studenttkkhuu.common.AbsMoveHorse;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public class MoveHorseEpsilon extends AbsMoveHorse{

	public MoveHorseEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		// The player made a resignation
		if (source == null && destination == null) {
			checkAvailableMove(color);

			return null;
		}
		
		checkMoveValidity(source, destination, color);

		if (moveCount == 0) {
			firstMoveColor = color;
		}

		final HantoCoordinateImpl to = new HantoCoordinateImpl(destination);

		HantoPiece horsePiece = null;

		if (source == null) { // Place a new piece from outside the board
			for (HantoPiece hp : pieces.keySet()) {
				if (pieces.get(hp) == null && hp.getType() == HORSE && hp.getColor() == color) {
					horsePiece = hp;
					pieces.put(horsePiece, to);
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

	@Override
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY && pieces.get(hp) == null) {
				throw new HantoException("Butterfly must be placed before moving a different piece");
			}
		}
		
		if (path == null) {
			throw new HantoException ("No path found");
		}
		final HantoCoordinateImpl from = new HantoCoordinateImpl(path.get(0));
		

		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getType() == HORSE && hp.getColor() == color && pieces.get(hp) != null
					&& pieces.get(hp).equals(from)) {
				return hp;
			}
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
		
		if (source != null && !from.isStraightLine(destination)) {
			throw new HantoException ("A Horse can only jump in straight line");
		}
		
		for (HantoPiece hc : pieces.keySet()) {
			HantoCoordinate temp = pieces.get(hc);
			if (temp != null && temp.equals(from) && hc.getColor() != color) {
				throw new HantoException("You cannot move a piece of a different player");
			}
		}
		
		for (HantoPiece hc : pieces.keySet()) {
			HantoCoordinate temp = pieces.get(hc);
			if (temp != null && temp.equals(from) && hc.getType() != HORSE) {
				throw new HantoException("You cannot change the type of a piece");
			}
		}
	}

	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException {
		if (isNeighborHex(source, destination)) {
			throw new HantoException("Horse must jump over occupied Hex");
		}
		
		HantoCoordinate[] path = { source, destination };

		int xDifference = destination.getX() - source.getX();
		int yDifference = destination.getY() - source.getY();

		int xIncrementUnit = 0;
		int yIncrementUnit = 0;

		if (xDifference != 0) {
			xIncrementUnit = xDifference / Math.abs(xDifference);
		}

		if (yDifference != 0) {
			yIncrementUnit = yDifference / Math.abs(yDifference);
		}

		int nextNeighborX = source.getX() + xIncrementUnit;
		int nextNeighborY = source.getY() + yIncrementUnit;

		HantoCoordinate nextNeighbor = new HantoCoordinateImpl(nextNeighborX, nextNeighborY);

		while (nextNeighbor.getX() != destination.getX() || nextNeighbor.getY() != destination.getY()) {

			if (!pieces.values().contains(nextNeighbor)) {
				throw new HantoException ("Jumping over empty Hex");
			}
			nextNeighborX += xIncrementUnit;
			nextNeighborY += yIncrementUnit;

			nextNeighbor = new HantoCoordinateImpl(nextNeighborX, nextNeighborY);
		}
		return Arrays.asList(path);
	}
	
	private void checkAvailableMove(HantoPlayerColor color)
			throws HantoPrematureResignationException {
		
		// If there are crab pieces not placed, check if any of them can be placed
		HantoPiece horsePieceNotPlaced = null;
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == HORSE && pieces.get(hp) == null) {
				horsePieceNotPlaced = hp;
				break;
			}
		}
		if (horsePieceNotPlaced != null) {
			List<HantoCoordinate> possibleMoves = getEmptyNeighborsOfSameColor(color);
			int numberOfInvalidPath = 0;
			for (HantoCoordinate toHex : possibleMoves) {
				try{
					movePiece(null, toHex, color);
				} catch (HantoException he) {
					numberOfInvalidPath++;
				}
				
				pieces.put(horsePieceNotPlaced, null);
				
				if (numberOfInvalidPath < possibleMoves.size()) {
					throw new HantoPrematureResignationException();
				}
			}
		}
		
		// If the horse pieces cannot be placed or all used, check the pieces on the board for valid move
		List<HantoPiece> horsePiecesPlaced = new ArrayList<HantoPiece>();
		
		for (HantoPiece p : pieces.keySet()) {
			if (p.getColor() == color && p.getType() == HORSE && pieces.get(p) != null) {
				horsePiecesPlaced.add(p);
			}
		}
		
		for (HantoPiece horsePiece : horsePiecesPlaced) {

			final HantoCoordinateImpl horsePieceHCI = new HantoCoordinateImpl(pieces.get(horsePiece));
			List<HantoCoordinate> possibleMoves = getPossibleMove(horsePieceHCI);
			int numberOfInvalidPath = 0;
			for (HantoCoordinate toHex : possibleMoves) {
				
				try {
					movePiece(horsePieceHCI, toHex, color);
				} catch (HantoException he) {
					
					numberOfInvalidPath++;
				}
				
			}
			pieces.put(horsePiece, horsePieceHCI);
			if (numberOfInvalidPath < possibleMoves.size()) {
				throw new HantoPrematureResignationException();
			}
		}
	}
	
	private List<HantoCoordinate> getPossibleMove(HantoCoordinate source){
		
		List<HantoCoordinate> possibleMoves = new ArrayList<HantoCoordinate>();
		
		// Jump North or South
		int xNorth = source.getX(), yNorth = source.getY();
		int xSouth = source.getX(), ySouth = source.getY();
		HantoCoordinateImpl northMove = new HantoCoordinateImpl(xNorth, yNorth);
		HantoCoordinateImpl southMove = new HantoCoordinateImpl(xSouth, ySouth);
		boolean foundNorth = false, foundSouth = false;
		while (!(foundNorth && foundSouth)) {
			if (getPieceAt(northMove) == null && !possibleMoves.contains(northMove) && !foundNorth) {
				possibleMoves.add(northMove);
				foundNorth = true;
			} else {
				yNorth++;
				northMove = new HantoCoordinateImpl(xNorth, yNorth);
			}
			if (getPieceAt(southMove) == null && !possibleMoves.contains(southMove) && !foundSouth) {
				possibleMoves.add(southMove);
				foundSouth = true;
			} else {
				ySouth--;
				southMove = new HantoCoordinateImpl(xSouth, ySouth);
			}
		}
		
		// Jump North East or South West
		int xNorthEast = source.getX(), yNorthEast = source.getY();
		int xSouthWest = source.getX(), ySouthWest = source.getY();
		HantoCoordinateImpl northEastMove = new HantoCoordinateImpl(xNorthEast, yNorthEast);
		HantoCoordinateImpl southWestMove = new HantoCoordinateImpl(xSouthWest, ySouthWest);
		boolean foundNorthEast = false, foundSouthWest = false;
		while (!(foundNorthEast && foundSouthWest)) {
			if (getPieceAt(northEastMove) == null && !possibleMoves.contains(northEastMove) && !foundNorthEast) {
				possibleMoves.add(northEastMove);
				foundNorthEast = true;
			} else {
				xNorthEast++;
				northEastMove = new HantoCoordinateImpl(xNorthEast, yNorthEast);
			}
			
			if (getPieceAt(southWestMove) == null && !possibleMoves.contains(southWestMove) && !foundSouthWest) {
				possibleMoves.add(southWestMove);
				foundSouthWest = true;
			} else {
				xSouthWest--;
				southWestMove = new HantoCoordinateImpl(xSouthWest, ySouthWest);
			}
		}
		
		// Jump North West or South East
		int xNorthWest = source.getX(), yNorthWest = source.getY();
		int xSouthEast = source.getX(), ySouthEast = source.getY();
		HantoCoordinateImpl northWestMove = new HantoCoordinateImpl(xNorthWest, yNorthWest);
		HantoCoordinateImpl southEastMove = new HantoCoordinateImpl(xSouthEast, ySouthEast);
		boolean foundNorthWest = false, foundSouthEast = false;
		while (possibleMoves.size() < 6) {
			if (getPieceAt(northWestMove) == null && !possibleMoves.contains(northWestMove) && !foundNorthWest) {
				possibleMoves.add(northWestMove);
				foundNorthWest = true;
			} else {
				xNorthWest--; yNorthWest++;
				northWestMove = new HantoCoordinateImpl(xNorthWest, yNorthWest);
			}
			
			if (getPieceAt(southEastMove) == null && !possibleMoves.contains(southEastMove) && !foundSouthEast) {
				possibleMoves.add(southEastMove);
				foundSouthEast = true;
			} else {
				xSouthEast++; ySouthEast--;
				southEastMove = new HantoCoordinateImpl(xSouthEast, ySouthEast);
			}
		}
		return possibleMoves;
	}
}
