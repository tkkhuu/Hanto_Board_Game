package hanto.studenttkkhuu;

import static hanto.common.HantoPieceType.BUTTERFLY;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public abstract class AbsMoveButterfly extends AbsMovePiece{

	/**
	 * Constructor for MoveButterflyDelta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public AbsMoveButterfly(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		this.pieces = pieces;
		this.moveCount = moveCount;
	}
	
	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {

		checkMoveValidity(source, destination, color);

		final HantoCoordinate to = new HantoCoordinateImpl(destination);

		HantoPiece butterflyPiece = null;

		if (source == null) { // Place the new butterfly piece from off the
								// board
			for (HantoPiece hp : pieces.keySet()) {
				if (pieces.get(hp) == null && hp.getType() == BUTTERFLY && hp.getColor() == color) {
					butterflyPiece = hp;
					pieces.put(butterflyPiece, to);
					return pieces;
				}
			}
		}

		else { // Move an existing piece
			List<HantoCoordinate> path = getPath(source, destination);
			HantoPiece temp = moveToHex(path, color);
			pieces.put(temp, to);
			checkDisconnection(temp);

			return pieces;
		}

		return null;
	}
	
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {

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

		HantoPiece butterflyPiece = null;

		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getType() == BUTTERFLY && hp.getColor() == color && pieces.get(hp) != null
					&& pieces.get(hp).equals(from)) {
				butterflyPiece = hp;
				break;
			}
		}
		return butterflyPiece;
	}

	private void checkMoveValidity(HantoCoordinate source, HantoCoordinate destination, HantoPlayerColor color)
			throws HantoException {

		super.checkValidity(source, destination, color);

		HantoCoordinateImpl from = null;

		if (source != null) {
			from = new HantoCoordinateImpl(source);
		}

		if (from != null && !pieces.containsValue(from)) {
			throw new HantoException("The source Hex is empty");
		}

		if (moveCount > 1 && source == null) {
			for (HantoPiece hp : pieces.keySet()) {
				if (pieces.get(hp) != null && isNeighborHex(pieces.get(hp), destination) && hp.getColor() != color) {
					throw new HantoException(
							"Beside first move of each color, the pieces cannot be placed next to opposite color");
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
			if (temp != null && temp.equals(from) && hc.getType() != BUTTERFLY) {
				throw new HantoException("You cannot change the type of a piece");
			}
		}

	}

	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination) throws HantoException {

		// ========================= Move One Hex =========================
		if (isNeighborHex(source, destination)) {
			HantoCoordinate[] path = { source, destination };
			return Arrays.asList(path);
		} else {
			throw new HantoException("A piece can only walk to one adjacent Hex");
		}

	}
}
