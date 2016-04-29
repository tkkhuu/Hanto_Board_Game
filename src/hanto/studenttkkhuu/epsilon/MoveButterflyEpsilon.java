package hanto.studenttkkhuu.epsilon;

import java.util.List;
import java.util.Map;

import static hanto.common.HantoPieceType.BUTTERFLY;
import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studenttkkhuu.common.AbsMoveButterfly;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public class MoveButterflyEpsilon extends AbsMoveButterfly{

	private HantoPiece butterflyPiece;
	
	public MoveButterflyEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}
	
	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == BUTTERFLY) {
				butterflyPiece = hp;
			}
		}
		
		// The player made a resignation
		if (source == null && destination == null) {
			
			checkAvailableMove(color, pieces);
			
			return pieces;
		}
		
		return super.movePiece(source, destination, color);
	}

	private void checkAvailableMove(HantoPlayerColor color, Map<HantoPiece, HantoCoordinate> pieces)
			throws HantoPrematureResignationException {
		
		// If Butterfly has not been placed, reject the resignation because
		// it is guaranteed that there's still valid move.

		if (pieces.get(butterflyPiece) == null) {
			throw new HantoPrematureResignationException();
		}
		final HantoCoordinateImpl butterflyPieceHCI = new HantoCoordinateImpl (pieces.get(butterflyPiece));
		List<HantoCoordinate> possibleMoves = butterflyPieceHCI.getNeighbors();
		int numberOfInvalidPath = 0;
		for (HantoCoordinate toHex : possibleMoves) {
			try{
				movePiece(butterflyPieceHCI, toHex, color);
			} catch (HantoException he) {
				numberOfInvalidPath++;
			}
			pieces.put(butterflyPiece, butterflyPieceHCI);
		}
		
		if (numberOfInvalidPath < possibleMoves.size()) {
			throw new HantoPrematureResignationException();
		}
	}
	


}
