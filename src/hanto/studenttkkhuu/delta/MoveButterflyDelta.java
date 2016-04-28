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
import hanto.studenttkkhuu.common.AbsMoveButterfly;

/**
 * A class that takes care of moving the butterfly piece in Delta
 *
 */
public class MoveButterflyDelta extends AbsMoveButterfly{
	
	/**
	 * Constructor for MoveButterflyDelta
	 * 
	 * @param pieces
	 *            List of Pieces on the board
	 * @param moveCount
	 *            Number of move made
	 */
	public MoveButterflyDelta(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

}
