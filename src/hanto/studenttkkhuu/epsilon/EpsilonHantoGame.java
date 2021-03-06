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
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;

import java.util.HashMap;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studenttkkhuu.common.BaseHanto;
import hanto.studenttkkhuu.common.HantoPieceImpl;

/**
 * An implementation of Epsilon Hanto Game
 *
 */
public class EpsilonHantoGame extends BaseHanto {

	public EpsilonHantoGame () {
		mpFactory = new MovablePieceFactoryEpsilon();
		
		pieces = new HashMap<HantoPiece, HantoCoordinate>();
		
		// Adding the Butterflies
		pieces.put(redButterfly, null);
		pieces.put(blueButterfly, null);
		
		// Adding the Sparrows
		for (int i = 0; i < 2; i++) {
			pieces.put(new HantoPieceImpl(BLUE, SPARROW), null);
			pieces.put(new HantoPieceImpl(RED, SPARROW), null);
		}
		
		// Adding the Crabs
		for (int i = 0; i < 6; i++) {
			pieces.put(new HantoPieceImpl(BLUE, CRAB), null);
			pieces.put(new HantoPieceImpl(RED, CRAB), null);
		}
		
		// Adding the Horses
		for (int i = 0; i < 4; i++) {
			pieces.put(new HantoPieceImpl(BLUE, HORSE), null);
			pieces.put(new HantoPieceImpl(RED, HORSE), null);
		}
	}
	
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate source, HantoCoordinate destination)
			throws HantoException {
		
		if (gameOver) {
			throw new HantoException("Game is over");
		}
		
		if (pieceType == null && source == null && destination == null) {
			checkValidResignation(source, destination);
			gameOver = true;
			return (whichColor == RED ? BLUE_WINS : RED_WINS);
		}
		
		if (pieceType != BUTTERFLY && pieceType != SPARROW && pieceType != CRAB && pieceType != HORSE) {
			throw new HantoException("Epsilon Hanto does not support this piece type");
		}

		createMove(pieceType, source, destination);

		return checkGameOverCondition();
	}
	
	/**
	 * Check if the game over condition has met
	 * @return OK, DRAW, RED_WINS or BLUE_WINS
	 */
	@Override
	protected MoveResult checkGameOverCondition(){
		
		MoveResult result = super.checkGameOverCondition();
		
		if (result != null) {
			return result;
		}
		
		return OK;
	}

	private void checkValidResignation(HantoCoordinate source, HantoCoordinate destination) throws HantoException {
		
		// ========== Check if player can still move butterfly ==========

		createMove(BUTTERFLY, source, destination);
		whichColor = (whichColor == RED ? BLUE : RED);
		moveCount--;

		// ========== Check if player can still move sparrow ==========

		// Player can still move sparrow
		createMove(SPARROW, source, destination);
		whichColor = (whichColor == RED ? BLUE : RED);
		moveCount--;

		// ========== Check if player can still move horse ==========

		createMove(HORSE, source, destination);
		whichColor = (whichColor == RED ? BLUE : RED);
		moveCount--;

		// ========== Check if player can still move crab ==========

		createMove(CRAB, source, destination);
		whichColor = (whichColor == RED ? BLUE : RED);
		moveCount--;
	}
}
