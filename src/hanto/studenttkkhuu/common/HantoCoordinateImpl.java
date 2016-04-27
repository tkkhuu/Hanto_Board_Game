/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Copyright ©2015 Gary F. Pollice
 *******************************************************************************/

package hanto.studenttkkhuu.common;

import java.util.Arrays;
import java.util.List;

import hanto.common.HantoCoordinate;

/**
 * The implementation for my version of Hanto.
 * @version Mar 2, 2016
 */
public class HantoCoordinateImpl implements HantoCoordinate
{
	final private int x, y;
	
	/**
	 * The only constructor.
	 * @param x the x-coordinate
	 * @param y the y-coordinate
	 */
	public HantoCoordinateImpl(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Copy constructor that creates an instance of HantoCoordinateImpl from an
	 * object that implements HantoCoordinate.
	 * @param coordinate an object that implements the HantoCoordinate interface.
	 */
	public HantoCoordinateImpl(HantoCoordinate coordinate)
	{
		this(coordinate.getX(), coordinate.getY());
	}
	
	@Override
	public int getX()
	{
		return x;
	}

	@Override
	public int getY()
	{
		return y;
	}
	
	/*
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return 0;
	}
	
	
	/*
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof HantoCoordinateImpl)) {
			return false;
		}
		final HantoCoordinateImpl other = (HantoCoordinateImpl) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the list of neighbors coordinate of this coordinate
	 * @return The list of neighbor for this coordinate
	 */
	public List<HantoCoordinate> getNeighbors() {
		
		HantoCoordinate northNeighbor = new HantoCoordinateImpl(x, y + 1);
		HantoCoordinate southNeighbor = new HantoCoordinateImpl(x, y - 1); 
		
		HantoCoordinate northWestNeighbor = new HantoCoordinateImpl(x - 1, y + 1);
		HantoCoordinate southWestNeighbor = new HantoCoordinateImpl(x - 1, y);
		
		HantoCoordinate northEastNeighbor = new HantoCoordinateImpl(x + 1, y);
		HantoCoordinate southEastNeighbor = new HantoCoordinateImpl(x + 1, y - 1);
		
		HantoCoordinate[] listOfNeighbor = {northNeighbor, southNeighbor, northWestNeighbor, southWestNeighbor, northEastNeighbor, southEastNeighbor};
		
		return Arrays.asList(listOfNeighbor);
		
	}


}
