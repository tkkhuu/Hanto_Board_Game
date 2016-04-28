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

package hanto.studenttkkhuu.gamma;


import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;
import static hanto.common.MoveResult.DRAW;
import static hanto.common.MoveResult.OK;

import java.util.HashMap;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.MoveResult;
import hanto.studenttkkhuu.common.BaseHanto;
import hanto.studenttkkhuu.common.HantoPieceImpl;


/**
 * 
 * @author Tri
 *
 */
public class GammaHantoGame extends BaseHanto{
	
	
	public GammaHantoGame() {
		
		mpFactory = new MovablePieceFactoryGamma();
		
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
			throw new HantoException("GammaHanto does not support this piece type");
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
			
		if (moveCount >= 40) {
			gameOver = true;
			return DRAW;
		}
		
		return OK;
	}
	

}
