package hanto.studenttkkhuu.epsilon;

import static hanto.common.HantoPieceType.*;
import static hanto.common.HantoPlayerColor.*;
import static hanto.common.MoveResult.BLUE_WINS;
import static hanto.common.MoveResult.OK;
import static hanto.common.MoveResult.RED_WINS;
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
import hanto.common.HantoPrematureResignationException;
import hanto.common.MoveResult;
import hanto.studenttkkhuu.HantoGameFactory;

public class EpsilonHantoMasterTest {

	class MoveData {
		final HantoPieceType type;
		final HantoCoordinate from, to;

		private MoveData(HantoPieceType type, HantoCoordinate from, HantoCoordinate to) {
			this.type = type;
			this.from = from;
			this.to = to;
		}
	}

	/**
	 * Internal class for these test cases.
	 * 
	 * @version Sep 13, 2014
	 */
	class TestHantoCoordinate implements HantoCoordinate {
		private final int x, y;

		public TestHantoCoordinate(int x, int y) {
			this.x = x;
			this.y = y;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getX()
		 */
		@Override
		public int getX() {
			return x;
		}

		/*
		 * @see hanto.common.HantoCoordinate#getY()
		 */
		@Override
		public int getY() {
			return y;
		}

	}

	private static HantoGameFactory factory;
	private HantoGame game;

	@BeforeClass
	public static void initializeClass() {
		factory = HantoGameFactory.getInstance();
	}

	@Before
	public void setup() {
		// By default, blue moves first.
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, BLUE);
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
	private MoveResult makeMoves(MoveData... moves) throws HantoException {
		MoveResult mr = null;
		for (MoveData md : moves) {
			mr = game.makeMove(md.type, md.from, md.to);
		}
		return mr;
	}

	// ================================== Tests ==================================

	@Test (expected = HantoException.class)
	public void placeButterflyNotAtOrigin() throws HantoException {
		game.makeMove(BUTTERFLY, null, makeCoordinate(1, 0));
	}
	
	@Test
	public void redPlacesButterflyFirst() throws HantoException {
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}

	@Test
	public void bluePlacesButterflyFirst() throws HantoException {
		final MoveResult mr = game.makeMove(BUTTERFLY, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(BUTTERFLY, piece.getType());
	}
	
	@Test
	public void redPlacesSparrowFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
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
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
		final MoveResult mr = game.makeMove(CRAB, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(CRAB, piece.getType());
	}
	
	@Test
	public void bluePlacesHorseFirst() throws HantoException
	{
		final MoveResult mr = game.makeMove(HORSE, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(BLUE, piece.getColor());
		assertEquals(HORSE, piece.getType());
	}
	
	@Test
	public void redPlacesHorseFirst() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
		final MoveResult mr = game.makeMove(HORSE, null, makeCoordinate(0, 0));
		assertEquals(OK, mr);
		final HantoPiece piece = game.getPieceAt(makeCoordinate(0, 0));
		assertEquals(RED, piece.getColor());
		assertEquals(HORSE, piece.getType());
	}
	
	// ++++++++++++++++++++++++++ SPARROWS ++++++++++++++++++++++++++
	
	@Test
	public void sparrowFlyOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, -1, 0));
		assertEquals(OK, mr);
		checkPieceAt(-1, 0, BLUE, SPARROW);
		
	}
	
	@Test
	public void sparrowFlyMoreThanOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, BLUE, SPARROW);
	}
	
	@Test
	public void sparrowFlyOverOccupiedHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 3));
		assertEquals(OK, mr);
		checkPieceAt(0, 3, BLUE, SPARROW);
	}
	
	@Test (expected = HantoException.class)
	public void sparrowFlyOverFiveHexDistant() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -2),
				md(SPARROW, 0, 3), md(SPARROW, 0, -2, 0, 4));
	}
	
	// ++++++++++++++++++++++++++ BUTTERFLY ++++++++++++++++++++++++++
	
	@Test
	public void butterflyWalkOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 0));
		assertEquals(OK, mr);
		checkPieceAt(1, 0, BLUE, BUTTERFLY);
	}
	
	@Test (expected = HantoException.class)
	public void butterflyWalkTwoHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 1, 1));
	}
	
	// ++++++++++++++++++++++++++ CRAB ++++++++++++++++++++++++++
	
	@Test
	public void crabWalkOneHex() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, 0, -1));
		assertEquals(OK, mr);
		checkPieceAt(0, -1, BLUE, CRAB);
	}
	
	@Test (expected = HantoException.class)
	public void crabWalkTwoHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 0));
	}
	
	@Test (expected = HantoException.class)
	public void crabWalkThreeHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 1, -1), md(CRAB, 0, 2), md(CRAB, 1, -1, -1, 1));
	}
	
	@Test (expected = HantoException.class)
	public void crabWalkThroughBlockedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 1, 1), md(CRAB, -1, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, -1, 1));
	}
	
	// ++++++++++++++++++++++++++ HORSE ++++++++++++++++++++++++++
	
	@Test
	public void horseJumpOverOneHexVertically() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(HORSE, 0, -1),
				md(HORSE, 2, 0), md(HORSE, 0, -1, 0, 1));
		assertEquals(OK, mr);
		checkPieceAt(0, 1, BLUE, HORSE);
	}
	
	@Test (expected = HantoException.class)
	public void horseJumpOverEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(HORSE, 0, -1),
				md(HORSE, 2, 0), md(HORSE, 0, -1, 2, -1));
	}
	
	@Test
	public void horseJumpOverMultipleHexHorizontally() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 1, 0), md(HORSE, -1, 0),
				md(HORSE, 2, 0), md(HORSE, -1, 0, 3, 0));
		assertEquals(OK, mr);
		checkPieceAt(3, 0, BLUE, HORSE);
	}
	
	@Test
	public void horseJumpOverMultipleHexDiagonally() throws HantoException
	{
		final MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, -1, 1), md(HORSE, 1, -1),
				md(HORSE, -2, 2), md(HORSE, 1, -1, -3, 3));
		assertEquals(OK, mr);
		checkPieceAt(-3, 3, BLUE, HORSE);
	}
	
	@Test (expected = HantoException.class)
	public void horseJumpInNonStraightLine() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1),
				md(HORSE, 0, 2), md(HORSE, 1, -1), md(HORSE, 1, -1, -1, 0));
	}
	
	// // ++++++++++++++++++++++++++ ERRORS Checking ++++++++++++++++++++++++++
	
	@Test (expected=HantoException.class)
	public void moveButterflyToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void moveSparrowToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, 2, -2, 2));
	}
	
	@Test (expected=HantoException.class)
	public void moveCrabToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, -1), md(CRAB, 0, -1, 0, -2));
	}
	
	@Test (expected=HantoException.class)
	public void moveHorseToDisconnectConfiguration() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1), md(HORSE, 0, 2), md(HORSE, 0, -1, 0, 4));
	}
	
	@Test (expected=HantoException.class)
	public void moveButterflyToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, 0, 0));
	}
	
	@Test (expected=HantoException.class)
	public void moveSparrowToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void moveCrabToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void moveHorseToSameHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1), md(HORSE, 0, 2), md(HORSE, 0, -1, 0, -1));
	}
	
	@Test (expected=HantoException.class)
	public void moveButterflyToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(BUTTERFLY, 0, 0, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void moveSparrowToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1),
				md(SPARROW, 0, 2), md(SPARROW, 0, -1, 0, 1));
	}

	@Test (expected=HantoException.class)
	public void moveCrabToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1),
				md(CRAB, 0, 2), md(CRAB, 0, -1, 0, 1));
	}
	
	@Test (expected=HantoException.class)
	public void moveHorseToOccupiedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1),
				md(HORSE, 0, 2), md(HORSE, 0, -1, 0, 2));
	}
	
	@Test (expected=HantoException.class)
	public void moveButterflyFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 1, 0, 1, -1));
	}
	
	@Test (expected=HantoException.class)
	public void moveSparrowFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(SPARROW, 0, -1), md(SPARROW, 0, 2), md(SPARROW, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void moveCrabFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(CRAB, 0, 2), md(CRAB, -1, 1, 1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void moveHorseFromEmptyHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1),
				md(HORSE, 0, 2), md(HORSE, 1, -1, 0, 2));
	}
	
	@Test (expected=HantoException.class)
	public void moveButterflyMoreThanAllowedHex() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(BUTTERFLY, 0, 0, -1, 2));
	}
	
	@Test (expected=HantoException.class)
	public void moveCrabMoreThanAllowedHex() throws HantoException
	{
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
				md(SPARROW, 0, 2), md(HORSE, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongPieceTypeButterfly() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), md(BUTTERFLY, 0, -1),
				md(BUTTERFLY, 0, 2), md(CRAB, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveWrongPieceTypeHorse() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1), md(HORSE, 0, -1),
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
	public void tryToMoveWrongColorTypeHorse() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), md(HORSE, 0, -1), md(HORSE, 0, -1, -1, 0));
	}
	
	@Test (expected=HantoException.class)
	public void tryToMoveButterflyWhenNotEnoughSpace() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1), md(SPARROW, 0, 3),
				md(BUTTERFLY, 0, 0, 0, -1));
	}
	
	@Test (expected = HantoException.class)
	public void tryToMoveCrabWhenNotEnoughSpace() throws HantoException
	{
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
				md(SPARROW, 0, -3), md(SPARROW, 0, 4));
	}
	
	@Test (expected=HantoException.class)
	public void tryToUseTooManyCrabs() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1), 
				md(CRAB, 0, -1), md(CRAB, 0, 2),
				md(CRAB, 0, -2), md(CRAB, 0, 3),
				md(CRAB, 0, -3), md(CRAB, 0, 4),
				md(CRAB, 0, -4), md(CRAB, 0, 5),
				md(CRAB, 0, -5), md(CRAB, 0, 6),
				md(CRAB, 0, -6), md(CRAB, 0, 7),
				md(CRAB, 0, -7), md(CRAB, 0, 8));
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
				md(SPARROW, 1, -1), md(SPARROW, 2, 1),
				md(SPARROW, 1, -1, 1, 0), md(CRAB, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(CRAB, -2, 3),
				md(HORSE, 0, -1), md(HORSE, 3, 1),
				md(HORSE, 0, -1, 0, 2));
		assertEquals(BLUE_WINS, mr);
	}
	
	@Test
	public void redWins() throws HantoException
	{
		game = factory.makeHantoGame(HantoGameID.EPSILON_HANTO, RED);
		MoveResult mr = makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(CRAB, -1, 2),
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
				md(SPARROW, 1, -1, 1, 0), md(CRAB, -1, 2),
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
	
	@Test (expected=HantoException.class)
	public void tryToPlaceHorseNextToOpponent() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(HORSE, -2, 0));
	}
	
	@Test (expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveByFirstPlayer() throws HantoException
	{
		makeMoves(md(SPARROW, 0, 0), md(SPARROW, 0, 1),
				md(CRAB, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(CRAB, 0, 3),
				md(HORSE, 0, -3));
	}
	
	@Test (expected=HantoException.class)
	public void butterflyNotPlacedByFourthMoveBySecondPlayer() throws HantoException
	{
		makeMoves(md(CRAB, 0, 0), md(CRAB, 0, 1),
				md(BUTTERFLY, 0, -1), md(SPARROW, 0, 2),
				md(SPARROW, 0, -2), md(CRAB, 0, 3),
				md(SPARROW, 0, -3), md(HORSE, 0, 4));
	}
	
	@Test(expected=HantoException.class)
	public void tryToMoveAfterGameIsOver() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md(BUTTERFLY, 0, 1),
				md(SPARROW, -1, 0), md(CRAB, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, 0, 2),
				md(SPARROW, 1, -1, 1, 0), md(CRAB, -1, 2),
				md(SPARROW, -1, 0, -1, 1), md(HORSE, 0, 3));
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
	
	@Test (expected=HantoException.class)
	public void tryToMoveHorseBeforeButterflyIsOnBoard() throws HantoException
	{
		makeMoves(md(HORSE, 0, 0), md (BUTTERFLY, 0, 1), md(HORSE, 0, 0, 0, 2));
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenAButterflyCanStillMakeAMove() throws HantoException
	{
		makeMoves(md(HORSE, 0, 0), md (BUTTERFLY, 0, 1), md(CRAB, 0, -1));
		game.makeMove(null, null, null);
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenAButterflyCanStillBePlaced() throws HantoException
	{
		makeMoves(md(HORSE, 0, 0), md (BUTTERFLY, 0, 1), md(CRAB, 0, -1), md(HORSE, 0, 2));
		game.makeMove(null, null, null);
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenASparrowCanBePlaced() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md (BUTTERFLY, 0, 1), 
				md(CRAB, -1, 0), md(CRAB, 1, 1),
				md(CRAB, 1, -1), md(CRAB, -1, 2),
				md(CRAB, -2, 0));
		game.makeMove(null, null, null);
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenASparrowCanBeMoved() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md (BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2));
		game.makeMove(null, null, null);
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenAHorseCanStillBePlaced() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md (BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2),
				md(CRAB, -2, 0), md(CRAB, 2, 1),
				md(CRAB, 2, -2), md(CRAB, -1, 3));
		game.makeMove(null, null, null);
	}
	
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenAHorseCanStillMakeMove() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md (BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2),
				md(HORSE, -2, 0), md(HORSE, 2, 1),
				md(HORSE, 2, -2), md(HORSE, -2, 3),
				md(CRAB, -3, 0), md(CRAB, 3, 1),
				md(CRAB, 3, -3), md(CRAB, -3, 4),
				md(HORSE, -4, 0), md(HORSE, 4, 1),
				md(HORSE, 4, -4), md(HORSE, -4, 5));
		game.makeMove(null, null, null);
	}
		
	@Test (expected = HantoPrematureResignationException.class)
	public void resignWhenACrabCanStillMakeAMove() throws HantoException
	{
		makeMoves(md(BUTTERFLY, 0, 0), md (BUTTERFLY, 0, 1), 
				md(SPARROW, -1, 0), md(SPARROW, 1, 1),
				md(SPARROW, 1, -1), md(SPARROW, -1, 2),
				md(HORSE, -2, 0), md(HORSE, 2, 1),
				md(HORSE, 2, -2), md(HORSE, -2, 3),
				md(CRAB, -3, 0), md(CRAB, 3, 1),
				md(CRAB, 3, -3), md(CRAB, -3, 4),
				md(HORSE, -4, 0), md(HORSE, 4, 1),
				md(HORSE, 4, -4), md(HORSE, -4, 5),
				md(CRAB, -5, 0), md(CRAB, 5, 1),
				md(CRAB, 5, -5), md(CRAB, -5, 6),
				md(CRAB, -6, 0), md(CRAB, 6, 1),
				md(CRAB, 6, -6), md(CRAB, -6, 7));
		game.makeMove(null, null, null);
	}
}
