package hanto.studenttkkhuu;

import static hanto.common.HantoPieceType.SPARROW;

import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public abstract class AbsMoveSparrow extends AbsMovePiece{
	
	public AbsMoveSparrow(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
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
				if (pieces.get(hp) == null && hp.getType() == SPARROW && hp.getColor() == color) {
					sparrowPiece = hp;
					pieces.put(sparrowPiece, to);
					return pieces;
				}
			}
		} else { // Move an existing piece
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
			if (temp != null && temp.equals(from) && hc.getType() != SPARROW) {
				throw new HantoException("You cannot change the type of a piece");
			}
		}
	}
	
	
}
