package hanto.studenttkkhuu.epsilon;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.studenttkkhuu.common.MovePiece;
import hanto.studenttkkhuu.common.MovePieceFactory;

public class MovablePieceFactoryEpsilon implements MovePieceFactory {

	/**
	 * Factory that creates MovablePiece classes
	 * 
	 * @param pieceType
	 *            Type of the piece
	 * @param listOfCurrentMove
	 *            List of pieces on the board
	 * @param moveCount
	 *            Number of move made
	 * @return The MovablePiece class of the corresponding type
	 */
	@Override
	public MovePiece makeMovablePiece(HantoPieceType pieceType, Map<HantoPiece, HantoCoordinate> listOfCurrentMove,
			int moveCount) {
		MovePiece mp = null;

		switch (pieceType) {
		case BUTTERFLY:
			mp = new MoveButterflyEpsilon(listOfCurrentMove, moveCount);
			break;
		case SPARROW:
			mp = new MoveSparrowEpsilon(listOfCurrentMove, moveCount);
			break;

		case CRAB:
			mp = new MoveCrabEpsilon(listOfCurrentMove, moveCount);
			break;

		case HORSE:
			mp = new MoveHorseEpsilon(listOfCurrentMove, moveCount);
			break;
		default:
			break;
		}

		return mp;
	}

}
