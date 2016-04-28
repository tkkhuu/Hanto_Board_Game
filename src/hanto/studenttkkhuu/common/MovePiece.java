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
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;

/**
 * An interface for movable pieces
 * @author Tri
 *
 */
public interface MovePiece {
	
	/**
	 * The function that move a piece from one hex to another hex
	 * @param source The source hex
	 * @param destination The destination hex
	 * @param color The color of the player making this move
	 * @return An updated list of pieces on the board
	 * @throws HantoException
	 */
	Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException;

}
