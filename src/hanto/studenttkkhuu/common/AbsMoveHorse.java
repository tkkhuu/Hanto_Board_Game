package hanto.studenttkkhuu.common;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;

public abstract class AbsMoveHorse extends AbsMovePiece{

	public AbsMoveHorse(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		this.pieces = pieces;
		this.moveCount = moveCount;
	}
	
	
}
