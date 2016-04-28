/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2016 Gary F. Pollice
 *******************************************************************************/

package hanto.studenttkkhuu.beta;

import hanto.common.*;
import hanto.studenttkkhuu.common.BaseHanto;
import hanto.studenttkkhuu.common.HantoPieceImpl;
import hanto.studenttkkhuu.common.MovePiece;

import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.HantoPieceType.*;

import java.util.HashMap;
import java.util.Map;

/**
 * BetaHanto Game
 * @version Mar 16, 2016
 */
public class BetaHantoGame extends BaseHanto
{
	private static MovablePieceFactoryBeta mpFactory = MovablePieceFactoryBeta.getInstance();
	
	public BetaHantoGame() {
		
		pieces = new HashMap<HantoPiece, HantoCoordinate>();
		
		pieces.put(redButterfly, null);
		pieces.put(blueButterfly, null);
		
		for (int i = 0; i < 5; i++) {
			pieces.put(new HantoPieceImpl(BLUE, SPARROW), null);
			pieces.put(new HantoPieceImpl(RED, SPARROW), null);
		}
	}
	
	/*
	 * @see hanto.common.HantoGame#makeMove(hanto.common.HantoPieceType, hanto.common.HantoCoordinate, hanto.common.HantoCoordinate)
	 */
	@Override
	public MoveResult makeMove(HantoPieceType pieceType, HantoCoordinate source,
			HantoCoordinate destination) throws HantoException
	{
		if (gameOver) {
			throw new HantoException("Game is over");
		}
		
		if (pieceType != BUTTERFLY && pieceType != SPARROW) {
			throw new HantoException("BetaHanto does not support this piece type");
		}
		
		MovePiece mp = mpFactory.makeMovablePiece(pieceType, pieces, moveCount);

		Map<HantoPiece, HantoCoordinate> updatedList = mp.movePiece(source, destination, whichColor);

		if (updatedList == null) {
			throw new HantoException("Invalid move for piece type: " + pieceType.toString());
		} else {
			pieces = updatedList;
		}

		switchPlayerTurn();
		
		incrementMoveCount();

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
		
		int numberOfBlueSparrowUsed = 0, numberOfRedSparrowUsed = 0;
		
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getType() == SPARROW) {
				if (pieces.get(hp) != null && hp.getColor() == BLUE) {
					numberOfBlueSparrowUsed++;
				}
				
				else if (pieces.get(hp) != null && hp.getColor() == RED) {
					numberOfRedSparrowUsed++;
				}
			}
		}
		
		if (numberOfBlueSparrowUsed == 5 && numberOfRedSparrowUsed == 5) {
			gameOver = true;
			return DRAW;
		}
		
		return OK;
	}

}
