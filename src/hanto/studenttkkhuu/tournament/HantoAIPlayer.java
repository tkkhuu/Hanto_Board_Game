package hanto.studenttkkhuu.tournament;

import hanto.tournament.HantoMoveRecord;

public interface HantoAIPlayer {

	/**
	 * Function to determine and make a valid move
	 * @param opponentsMove
	 * @return Move record made
	 */
	HantoMoveRecord makeMove(HantoMoveRecord opponentsMove);
	
}
