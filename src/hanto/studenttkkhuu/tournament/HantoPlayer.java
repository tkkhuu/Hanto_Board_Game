/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.tournament;

import hanto.common.HantoGameID;
import hanto.common.HantoPlayerColor;
import hanto.tournament.HantoGamePlayer;
import hanto.tournament.HantoMoveRecord;

/**
 * Description
 * @version Oct 13, 2014
 */
public class HantoPlayer implements HantoGamePlayer
{
	// =============== Game Properties ===============
//	private HantoGameID gameID;
	
	// =============== Player Properties ===============
//	private HantoPlayerColor color;
//	private boolean moveFirst;
	private HantoAIPlayer myAIPlayer;
	
	

	
	/*
	 * @see hanto.tournament.HantoGamePlayer#startGame(hanto.common.HantoGameID, hanto.common.HantoPlayerColor, boolean)
	 */
	@Override
	public void startGame(HantoGameID version, HantoPlayerColor myColor,
			boolean doIMoveFirst)
	{
		myAIPlayer = new EpsilonHantoAIPlayer(myColor, doIMoveFirst);
	}

	/*
	 * @see hanto.tournament.HantoGamePlayer#makeMove(hanto.tournament.HantoMoveRecord)
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove)
	{
		HantoMoveRecord move = myAIPlayer.makeMove(opponentsMove);

		return move;
	}
}
