/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.gamma;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.studenttkkhuu.HantoGameFactory;
import hanto.studenttkkhuu.MovePiece;

/**
 * A factory class that make MovablePiece Classes
 * @author Tri
 *
 */
public class MovablePieceFactoryGamma extends HantoGameFactory{
	private static final MovablePieceFactoryGamma instance = new MovablePieceFactoryGamma();

	/**
	 * Default private descriptor.
	 */
	private MovablePieceFactoryGamma() {
		// Empty, but the private constructor is necessary for the singleton.
	}

	/**
	 * @return the instance
	 */
	public static MovablePieceFactoryGamma getInstance() {
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
			mp = new MoveButterflyGamma(listOfCurrentMove, moveCount);
			break;
		case SPARROW:
			mp = new MoveSparrowGamma(listOfCurrentMove, moveCount);
			break;

		default:
			break;
		}

		return mp;
	}
}
