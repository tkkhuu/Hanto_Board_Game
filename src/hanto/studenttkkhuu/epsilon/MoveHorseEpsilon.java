package hanto.studenttkkhuu.epsilon;

import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.HORSE;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.AbsMoveHorse;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public class MoveHorseEpsilon extends AbsMoveHorse{

	public MoveHorseEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
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
}
