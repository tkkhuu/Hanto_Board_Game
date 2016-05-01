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

/**
 * 
 * An abstraction for all MoveHorse classes
 *
 */
public abstract class AbsMoveHorse extends AbsMovePiece{

	/**
	 * Constructor for MoveHorse classes
	 * @param pieces List of pieces in this game
	 * @param moveCount Number of moves made
	 */
	public AbsMoveHorse(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		this.pieces = pieces;
		this.moveCount = moveCount;
	}
	
	
}
