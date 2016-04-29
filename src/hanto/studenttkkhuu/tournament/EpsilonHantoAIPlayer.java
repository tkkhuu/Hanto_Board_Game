package hanto.studenttkkhuu.tournament;

import static hanto.common.HantoGameID.EPSILON_HANTO;
import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoGame;
import hanto.common.HantoPiece;
import hanto.common.HantoPieceType;
import hanto.common.HantoPlayerColor;
import hanto.studenttkkhuu.HantoGameFactory;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;
import hanto.studenttkkhuu.common.HantoPieceImpl;
import hanto.tournament.HantoMoveRecord;

public class EpsilonHantoAIPlayer {
	// =============== Game State ===============
	private Map<HantoPiece, HantoCoordinate> pieces;
	private boolean doIMoveFirst;
	private HantoPlayerColor myColor;
	private int moveCount = 0;
	private HantoGame game;
	private final Random rand = new Random();
	
	private HantoPiece myButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
	private HantoPiece opponentButterfly = new HantoPieceImpl(RED, BUTTERFLY);
	
	private HantoPiece mySparrows[] = {new HantoPieceImpl(BLUE, SPARROW), new HantoPieceImpl(BLUE, SPARROW)};
	private HantoPiece opponentSparrows[] = {new HantoPieceImpl(RED, SPARROW), new HantoPieceImpl(RED, SPARROW)};
	
	private HantoPiece myCrabs[] = {new HantoPieceImpl(BLUE, CRAB), 
											new HantoPieceImpl(BLUE, CRAB),
											new HantoPieceImpl(BLUE, CRAB),
											new HantoPieceImpl(BLUE, CRAB),
											new HantoPieceImpl(BLUE, CRAB),
											new HantoPieceImpl(BLUE, CRAB)};
	
	private HantoPiece opponentCrabs[] = {	new HantoPieceImpl(RED, CRAB), 
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB)};
	
	private HantoPiece myHorses[] = {	new HantoPieceImpl(BLUE, HORSE), 
												new HantoPieceImpl(BLUE, HORSE),
												new HantoPieceImpl(BLUE, HORSE),
												new HantoPieceImpl(BLUE, HORSE)};
	
	private HantoPiece opponentHorses[] = {new HantoPieceImpl(RED, HORSE), 
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE)};

	public EpsilonHantoAIPlayer(HantoPlayerColor myColor, boolean doIMoveFirst) {
		
		HantoPlayerColor oppositeColor = (myColor == RED ? BLUE : RED);
		
		game = (doIMoveFirst ? HantoGameFactory.getInstance().makeHantoGame(EPSILON_HANTO, myColor)
				: HantoGameFactory.getInstance().makeHantoGame(EPSILON_HANTO, oppositeColor));

		this.doIMoveFirst = doIMoveFirst;
		this.myColor = myColor;
		
		pieces = new HashMap<HantoPiece, HantoCoordinate>();

		if (this.myColor == RED) {
			myButterfly = new HantoPieceImpl(RED, BUTTERFLY);
			mySparrows = new HantoPieceImpl[]{new HantoPieceImpl(RED, SPARROW), new HantoPieceImpl(RED, SPARROW)};
			myCrabs = new HantoPieceImpl[]{	new HantoPieceImpl(RED, CRAB), 
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB),
											new HantoPieceImpl(RED, CRAB)};
			myHorses = new HantoPieceImpl[]{new HantoPieceImpl(RED, HORSE), 
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE)};


			
			opponentButterfly = new HantoPieceImpl(BLUE, BUTTERFLY);
			opponentSparrows = new HantoPieceImpl[]{new HantoPieceImpl(BLUE, SPARROW), new HantoPieceImpl(BLUE, SPARROW)};
			opponentCrabs = new HantoPieceImpl[]{	new HantoPieceImpl(BLUE, CRAB), 
													new HantoPieceImpl(BLUE, CRAB),
													new HantoPieceImpl(BLUE, CRAB),
													new HantoPieceImpl(BLUE, CRAB),
													new HantoPieceImpl(BLUE, CRAB),
													new HantoPieceImpl(BLUE, CRAB)};
			opponentHorses = new HantoPieceImpl[]{	new HantoPieceImpl(BLUE, HORSE), 
													new HantoPieceImpl(BLUE, HORSE),
													new HantoPieceImpl(BLUE, HORSE),
													new HantoPieceImpl(BLUE, HORSE)};
		} else {

			// Adding the Butterflies
			pieces.put(myButterfly, null);
			pieces.put(opponentButterfly, null);

			// Adding the Sparrows
			for (int i = 0; i < 2; i++) {
				pieces.put(mySparrows[i], null);
				pieces.put(opponentSparrows[i], null);
			}

			// Adding the Crabs
			for (int i = 0; i < 6; i++) {
				pieces.put(myCrabs[i], null);
				pieces.put(opponentCrabs[i], null);
			}

			// Adding the Horses
			for (int i = 0; i < 4; i++) {
				pieces.put(myHorses[i], null);
				pieces.put(opponentHorses[i], null);
			}
		}
	}

	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		
		HantoMoveRecord moveMade = null;
		try {
			// Making first move in first turn, has to be placed at origin (0, 0)
			if (doIMoveFirst && opponentsMove == null) {
				game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
				pieces.put(myButterfly, new HantoCoordinateImpl(0, 0));
				moveMade = new HantoMoveRecord(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
			}

			// Making second move in first turn, has to be placed next to origin (0, 0)
			else if (!doIMoveFirst && opponentsMove != null) {
				recordOpponentMove(opponentsMove); // Record opponent's move
				game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 1));
				pieces.put(myButterfly, new HantoCoordinateImpl(0, 1));
				moveMade = new HantoMoveRecord(BUTTERFLY, null, new HantoCoordinateImpl(0, 1));
			}

			// Normal moves
			else {
				recordOpponentMove(opponentsMove); // Record opponent's move
			}

			moveCount++;
		} catch (HantoException e) {
			e.printStackTrace();
		}
		return moveMade;
	}

	private void recordOpponentMove(HantoMoveRecord opponentsMove) throws HantoException {
		
		HantoPieceType opponentsPieceType = opponentsMove.getPiece();
		final HantoCoordinate opponentFrom = opponentsMove.getFrom();
		final HantoCoordinate opponentTo = opponentsMove.getTo();
		
		switch (opponentsPieceType) {
		case BUTTERFLY:
			game.makeMove(BUTTERFLY, opponentFrom, opponentTo);
			pieces.put(opponentButterfly, opponentTo);
			break;
			
		case SPARROW:
			game.makeMove(SPARROW, opponentFrom, opponentTo);
			for (int i = 0; i < opponentSparrows.length; i++) {
				if (opponentFrom == null && pieces.get(opponentSparrows[i]) == null) {
					pieces.put(opponentSparrows[i], opponentTo);
					break;
				}
				else if (pieces.get(opponentSparrows[i]).equals(opponentFrom)){
					pieces.put(opponentSparrows[i], opponentTo);
					break;
				}
			}
			
			break;

		case CRAB:
			game.makeMove(CRAB, opponentFrom, opponentTo);
			for (int i = 0; i < opponentCrabs.length; i++) {
				if (opponentFrom == null && pieces.get(opponentCrabs[i]) == null) {
					pieces.put(opponentCrabs[i], opponentTo);
					break;
				}
				else if (pieces.get(opponentCrabs[i]).equals(opponentFrom)){
					pieces.put(opponentCrabs[i], opponentTo);
					break;
				}
			}
			break;

		case HORSE:
			game.makeMove(HORSE, opponentFrom, opponentTo);
			for (int i = 0; i < opponentHorses.length; i++) {
				if (opponentFrom == null && pieces.get(opponentHorses[i]) == null) {
					pieces.put(opponentHorses[i], opponentTo);
					break;
				}
				else if (pieces.get(opponentHorses[i]).equals(opponentFrom)){
					pieces.put(opponentHorses[i], opponentTo);
					break;
				}
			}
			break;
			
		default:
			break;
		}
		
		moveCount++; // Accounting for opponent's move
	}
	
}
