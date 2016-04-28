package hanto.studenttkkhuu.common;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;

public interface MovePieceFactory {

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
	public MovePiece makeMovablePiece(HantoPieceType pieceType, Map<HantoPiece, HantoCoordinate> listOfCurrentMove,
			int moveCount);
	
	
}
