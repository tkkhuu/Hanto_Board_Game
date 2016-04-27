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

package hanto.studenttkkhuu.delta;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.studenttkkhuu.MovePiece;

/**
 * A factory that creates MovePiece classes in Delta Hanto
 *
 */
public class MovablePieceFactoryDelta {
	private static final MovablePieceFactoryDelta instance = new MovablePieceFactoryDelta();

	/**
	 * Default private descriptor.
	 */
	private MovablePieceFactoryDelta() {
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static MovablePieceFactoryDelta getInstance() {
		return instance;
	}
	
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
			int moveCount) {
		
		MovePiece mp = null;
		
		switch (pieceType) {
		case BUTTERFLY:
			mp = new MoveButterflyDelta(listOfCurrentMove, moveCount);
			break;
		case SPARROW:
			mp = new MoveSparrowDelta(listOfCurrentMove, moveCount);
			break;

		case CRAB:
			mp = new MoveCrabDelta(listOfCurrentMove, moveCount);
			break;
			
		default:
			break;
		}

		return mp;
	}
}
