/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.beta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.MoveResult.*;
import static hanto.common.HantoPlayerColor.*;
import static org.junit.Assert.*;
import hanto.common.*;
import hanto.studenttkkhuu.HantoGameFactory;

import org.junit.*;

/**
 * Test cases for Beta Hanto.
 * @version Sep 14, 2014
 */
public class BetaHantoMasterTest
{
	/**
	 * Internal class for these test cases.
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate
	{
		private final int x, y;
		
		public TestHantoCoordinate(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX()
		{
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY()
		{
			return y;
		}

	}
	
	private static HantoGameFactory factory;
	private HantoGame game;
	
	@BeforeClass
	public static void initializeClass()
	{
		factory = HantoGameFactory.getInstance();
	}
	
	@Before
	public void setup()
	{
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, BLUE);
	}
	
	@Test	// 1
	public void bluePlacesInitialButterflyAtOrigin() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test (expected = HantoException.class) // 2
	public void bluePlacesInitialMoveNotAtOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test (expected = HantoException.class) //3
	public void redMakesFirstSparrowMoveToOrigin() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test //4
	public void redMakesFirstValidSparrowMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test (expected = HantoException.class) //5
	public void redMakesDisconnectedSparrowMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test  //6
	public void blueMakesValidThirdMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test (expected = HantoException.class) //7
	public void blueMakesSecondButterflyMove() throws HantoException
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	@Test  (expected = HantoException.class)//8
	public void blueMakesFourSparrowMove() throws HantoException
	{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Blue
	}
	
	@Test //9
	public void gameDrawAfterSixTurnsAndNoButterflySurrounded() throws HantoException 
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); // Blue
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Red
	
		assertEquals(DRAW, mr);
	}
	
	@Test //10
	public void redWins() throws HantoException 
	{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Red
		
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
	
		assertEquals(RED_WINS, mr);
	}
	
	@Test (expected = HantoException.class) //11
	public void cannotMakeMoveWhenGameOver() throws HantoException{
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Red
	
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0)); // Red
	}
	
	@Test //12
	public void initialGameWithRedMoveFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test (expected = HantoException.class) // 13
	public void redPlacesInitialMoveNotAtOrigin() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 1));
	}
	
	@Test (expected = HantoException.class) // 14
	public void blueMakesFirstSparrowMoveToOriginAfterRed() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
	}
	
	@Test //15
	public void blueMakesFirstValidSparrowMove() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(1, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test (expected = HantoException.class) // 16
	public void blueMakesDisconnectedSparrowMove() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(2, 0));
	}
	
	@Test  // 17
	public void redMakesValidThirdMove() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 1));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 1));
		assertEquals(RED, p.getColor());
		assertEquals(SPARROW, p.getType());
	}
	
	@Test (expected = HantoException.class) // 18
	public void redMakesSecondButterflyMove() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		game.makeMove(SPARROW, null, makeCoordinate(1, 0));
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1));
	}
	
	@Test  (expected = HantoException.class)// 19
	public void redMakesFourSparrowMove() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Red
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
	}
	
	@Test //20
	public void gameDrawAfterSixTurnsAndNoButterflySurroundedRedFirst() throws HantoException 
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Red
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Blue
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Blue
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); // Blue
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(0, -2)); // Blue
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -2)); // Red
		game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Blue
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); // Red
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Blue
	
		assertEquals(DRAW, mr);
	}
	
	@Test //21 
	public void blueWins() throws HantoException 
	{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Red
		
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
	
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test (expected = HantoException.class) //22
	public void cannotMakeMoveWhenGameOverRedMoveFirst() throws HantoException{
		
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, -2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, -2)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Red
	
		
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0)); // Red
	}
	
	@Test //23
	public void betaDefaultBlueMovesFirst() throws HantoException{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece p = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, p.getColor());
		assertEquals(BUTTERFLY, p.getType());
	}
	
	@Test (expected = HantoException.class) //24
	public void bothButterflySurroundedResultDraw() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(2, 0)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(2, -1)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, -1)); // Red
		
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Blue
		
		assertEquals(mr, DRAW);
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Red
	}
	
	@Test (expected = HantoException.class) //25
	public void blueMakesNonSparrowAndButterflyMove() throws HantoException{
		game.makeMove(CRAB, null, makeCoordinate(0, 0)); // Blue
	}
	
	@Test (expected = HantoException.class) //26
	public void redMakesNonSparrowAndButterflyMove() throws HantoException{
		game.makeMove(SPARROW, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(CRAB, null, makeCoordinate(1, 0)); // Blue
	}
	
	@Test (expected = HantoException.class) //27
	public void redFirstAndMakesNonSparrowAndButterflyMove() throws HantoException{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(CRAB, null, makeCoordinate(0, 0)); // Blue
	}
	
	@Test (expected = HantoException.class) //28
	public void redFirstAndBlueMakesNonSparrowAndButterflyMove() throws HantoException{
		game = factory.makeHantoGame(HantoGameID.BETA_HANTO, RED);
		game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		game.makeMove(CRAB, null, makeCoordinate(1, 0));
	}
	
	@Test //29
	public void blueWinsBlueMoveFirst() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, 1)); // Red
		
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 2)); // Blue
	
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test (expected = HantoException.class) //30
	public void redMakesDisconnectedMove() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-3, 0)); // Red
	}
	
	@Test (expected = HantoException.class) //31
	public void redPlaceMoveToOccuppiedHex() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Red
	}
	
	@Test //32
	public void redPlaceButterflyOnFourthTurn() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(2, 0)); // Red
	}
	
	@Test (expected = HantoException.class) //33
	public void redPlaceButterflyOnFifthTurn() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, 0)); // Red
	}
	
	@Test //34
	public void redPlaceButterflyOnSecondTurn() throws HantoException {
		
		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red
		
		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, 0)); // Red
	}
	
	@Test(expected = HantoException.class) //35
	public void bluePlaceMoreThanSixTurn() throws HantoException {

		game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(0, 1)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(1, 0)); // Blue
		game.makeMove(BUTTERFLY, null, makeCoordinate(-1, 1)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(-1, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(1, -1)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(-2, 2)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(2, 0)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(-2, 3)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-2, 1)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(-1, 3)); // Blue
		game.makeMove(SPARROW, null, makeCoordinate(-1, 4)); // Red

		game.makeMove(SPARROW, null, makeCoordinate(2, 5)); // Blue
	}
	
	
	
	
	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y)
	{
		return new TestHantoCoordinate(x, y);
	}
}
