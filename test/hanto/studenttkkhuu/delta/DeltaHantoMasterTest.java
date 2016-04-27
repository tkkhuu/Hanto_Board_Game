package hanto.studenttkkhuu.delta;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoGameID;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.common.MoveResult;
import hanto.studenttkkhuu.HantoGameFactory;

public class DeltaHantoMasterTest {

	class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from, to;
		
		private MoveData(HantoPieceType type, HantoCoordinate from, HantoCoordinate to) 
		{
			this.type = type;
			this.from = from;
			this.to = to;
		}
	}
	
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
	public void setup() {
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, BLUE);
	}

	// Helper methods
	private HantoCoordinate makeCoordinate(int x, int y) {
		return new TestHantoCoordinate(x, y);
	}

	/**
	 * Make sure that the piece at the location is what you expect
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param color
	 *            piece color expected
	 * @param type
	 *            piece type expected
	 */
	private void checkPieceAt(int x, int y, HantoPlayerColor color, HantoPieceType type) {
		final HantoPiece piece = game.getPieceAt(makeCoordinate(x, y));
		assertEquals(color, piece.getColor());
		assertEquals(type, piece.getType());
	}

	/**
	 * Make a MoveData object given the piece type and the x and y coordinates
	 * of the destination. This creates a move data that will place a piece
	 * (source == null)
	 * 
	 * @param type
	 *            piece type
	 * @param toX
	 *            destination x-coordinate
	 * @param toY
	 *            destination y-coordinate
	 * @return the desired MoveData object
	 */
	private MoveData md(HantoPieceType type, int toX, int toY) {
		return new MoveData(type, null, makeCoordinate(toX, toY));
	}

	private MoveData md(HantoPieceType type, int fromX, int fromY, int toX, int toY) {
		return new MoveData(type, makeCoordinate(fromX, fromY), makeCoordinate(toX, toY));
	}

	/**
	 * Make the moves specified. If there is no exception, return the move
	 * result of the last move.
	 * 
	 * @param moves
	 * @return the last move result
	 * @throws HantoException
	 */
	private MoveResult makeMoves(MoveData... moves) throws HantoException
		{
			MoveResult mr = null;
			for (MoveData md : moves) {
				mr = game.makeMove(md.type, md.from, md.to);
			}
			return mr;
		}
	
	// ================================== Tests ==================================
	
	@Test
	public void redPlacesButterflyFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void bluePlacesButterflyFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}
	
	@Test
	public void bluePlacesSparrowFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(SPARROW, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(SPARROW, piece.getType());
	}
	
	@Test
	public void bluePlacesCrabFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(CRAB, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(CRAB, piece.getType());
	}
	
	@Test
	public void redPlacesCrabFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = game.makeMove(CRAB, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(CRAB, piece.getType());
	}
	
	@Test
	public void blueSparrowFlyOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, BLUE, SPARROW);
		
	}
	
	@Test
	public void redSparrowFlyOneHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, RED, SPARROW);
		
	}
	
	@Test
	public void blueSparrowFlyMoreThanOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, BLUE, SPARROW);
	}
	
	@Test
	public void blueSparrowFlyOverOccupiedHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 3));
		assertEquals(OK, mr);
		checkPieceAt(0, 3, BLUE, SPARROW);
	}
	
	@Test
	public void redSparrowFlyMoreThanOneHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, RED, SPARROW);
	}
	
	@Test
	public void redSparrowFlyOverOccupiedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 3));
		assertEquals(OK, mr);
		checkPieceAt(0, 3, RED, SPARROW);
	}
	
	@Test
	public void blueButterflyWalkOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, BLUE, BUTTERFLY);
	}
	
	@Test (expected = HantoException.class)
	public void blueButterflyWalkTwoHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 1));
	}
	
	@Test
	public void redButterflyWalkOneHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, RED, BUTTERFLY);
	}
	
	@Test (expected = HantoException.class)
	public void redButterflyWalkTwoHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 1));
	}
	
	@Test
	public void blueCrabWalkOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, 0, -1));
		assertEquals(OK, mr);
		checkPieceAt(0, -1, BLUE, CRAB);
	}
	
	@Test
	public void blueCrabWalkTwoHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, BLUE, CRAB);
	}
	
	@Test
	public void blueCrabWalkThreeHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 1));
		assertEquals(OK, mr);
		checkPieceAt(-1, 1, BLUE, CRAB);
	}
	
	@Test
	public void redCrabWalkOneHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, 0, -1));
		assertEquals(OK, mr);
		checkPieceAt(0, -1, RED, CRAB);
	}
	
	@Test
	public void redCrabWalkTwoHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, RED, CRAB);
	}
	
	@Test
	public void redCrabWalkThreeHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 1));
		assertEquals(OK, mr);
		checkPieceAt(-1, 1, RED, CRAB);
	}
	
	@Test(expected=HantoException.class)
	public void blueMoveButterflyToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test(expected=HantoException.class)
	public void redMoveButterflyToDisconnectConfiguration() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveSparrowToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, 2, -2, 2));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveSparrowToDisconnectConfiguration() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, 2, -2, 2));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveCrabToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, 2, -2, 2));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveCrabToDisconnectConfiguration() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, 2, -2, 2));
	}
	
	@Test(expected=HantoException.class)
	public void blueMoveButterflyToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, 0));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveButterflyToSameHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, 0));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveSparrowToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveSparrowToSameHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveCrabToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveCrabToSameHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveButterflyToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, 0, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveButterflyToOccupiedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, 0, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveSparrowToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveSparrowToOccupiedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveCrabToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1),
				md(CRAB, 0, 2), md(CRAB, 0, -1, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveCrabToOccupiedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1),
				md(CRAB, 0, 2), md(CRAB, 0, -1, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveButterflyFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 1, 0, 1, -1));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveButterflyFromEmptyHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 1, 0, 1, -1));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveSparrowFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveSparrowFromEmptyHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveCrabFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveCrabFromEmptyHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveButterflyMoreThanAllowedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, -1, 2));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveButterflyMoreThanAllowedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, -1, 2));
	}
	
	@Test (expected=HantoException.class)
	public void blueMoveCrabMoreThanAllowedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, 0, 3));
	}
	
	@Test (expected=HantoException.class)
	public void redMoveCrabMoreThanAllowedHex() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, 0, 3));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongPieceTypeSparrow() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongPieceTypeCrab() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongPieceTypeButterfly() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), md(BUTTERFLY, 0, -1),
				md(BUTTERFLY, 0, 2), md(CRAB, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongColorTypeSparrow() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongColorTypeCrab() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongColorTypeButterfly() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), md(BUTTERFLY, 0, -1), md(BUTTERFLY, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void blueTryToMoveButterflyeWhenNotEnoughSpace() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void redTryToMoveButterflyWhenNotEnoughSpace() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test (expected = HantoException.class)
	public void blueTryToMoveCrabWhenNotEnoughSpace() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), 
				md(BUTTERFLY, -1, 0), md(BUTTERFLY, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(CRAB, 0, 0, 0, -1));
	}
	
	@Test (expected = HantoException.class)
	public void redTryToMoveCrabWhenNotEnoughSpace() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), 
				md(BUTTERFLY, -1, 0), md(BUTTERFLY, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(CRAB, 0, 0, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void tryToUseTooManyButterflies() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void tryToUseTooManySparrows() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(SPARROW, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4),
				md(SPARROW, 0, -4), md(SPARROW, 0, 5),
				md(SPARROW, 0, -5), md(SPARROW, 0, 6));
	}
	
	@Test (expected=HantoException.class)
	public void tryToUseTooManyCrabs() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(CRAB, 0, -2), md(CRAB, 0, 3),
				md(CRAB, 0, -3), md(CRAB, 0, 4),
				md(CRAB, 0, -4), md(CRAB, 0, 5),
				md(CRAB, 0, -5), md(CRAB, 0, 6));
	}
	
	@Test (expected=HantoException.class)
	public void tryToUsePieceNotInGame() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRANE, 0, -1));
	}
	
	@Test
	public void blueWins() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test
	public void redWins() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.DELTA_HANTO, RED);
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1));
		assertEquals(RED_WINS, mr);
	}
	
	@Test
	public void redSelfLoses() throws HantoException
	{
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), 
				md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 1, 2),
				md(SPARROW, 1, -1, 1, 0), md(SPARROW, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 1, 2, 1, 1));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test(expected=HantoException.class)
	public void tryToPlaceButterflyNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(SPARROW, 0, 1),
				md(SPARROW, -1, 0), md(BUTTERFLY, -2, 0));
	}
	
	@Test(expected=HantoException.class)
	public void tryToPlaceSparrowNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, -2, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToPlaceCrabNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(CRAB, -2, 0));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveByFirstPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(CRAB, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(CRAB, 0, 3),
				md(SPARROW, 0, -3));
	}
	
	@Test(expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveBySecondPlayer() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1),
				md(BUTTERFLY, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(CRAB, 0, 3),
				md(SPARROW, 0, -3), md(SPARROW, 0, 4));
	}
	
	@Test  
	public void blueResigns() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1));
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(mr, RED_WINS);
	}
	
	@Test  
	public void redResigns() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1));
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(mr, BLUE_WINS);
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveAfterGameIsOver() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(CRAB, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(CRAB, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(SPARROW, 0, 3));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveAfterOneResign() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1));
		final MoveResult mr = game.makeMove(null, null, null);
		assertEquals(mr, RED_WINS);
		makeMoves(md(SPARROW, -1, 0));	
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveSparrowBeforeButterflyIsOnBoard() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md (BUTTERFLY, 0, 1), md(SPARROW, 0, 0, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveCrabBeforeButterflyIsOnBoard() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md (BUTTERFLY, 0, 1), md(CRAB, 0, 0, 1, 0));
	}
	
	@Test (expected = HantoException.class)
	public void blueCrabWalkThroughBlockedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1), md(CRAB, -1, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, -1, 1));
	}
	
	

}
