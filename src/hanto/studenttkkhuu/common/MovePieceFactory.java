/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.common;

import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;

/**
 * An interface for MovePiece factories
 *
 */
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
	 MovePiece makeMovablePiece(HantoPieceType pieceType, Map<HantoPiece, HantoCoordinate> listOfCurrentMove,
			int moveCount);
	
	
}
