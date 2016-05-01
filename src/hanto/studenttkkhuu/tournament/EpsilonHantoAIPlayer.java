/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package hanto.studenttkkhuu.tournament;

import static hanto.common.HantoGameID.EPSILON_HANTO;
import static hanto.common.HantoPieceType.BUTTERFLY;
import static hanto.common.HantoPieceType.CRAB;
import static hanto.common.HantoPieceType.HORSE;
import static hanto.common.HantoPieceType.SPARROW;
import static hanto.common.HantoPlayerColor.BLUE;
import static hanto.common.HantoPlayerColor.RED;

import java.util.List;
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
import hanto.studenttkkhuu.epsilon.MoveCrabEpsilon;
import hanto.studenttkkhuu.epsilon.MoveHorseEpsilon;
import hanto.studenttkkhuu.epsilon.MoveSparrowEpsilon;
import hanto.tournament.HantoMoveRecord;

/**
 * An AI player to play Epsilon Hanto
 *
 */
public class EpsilonHantoAIPlayer implements HantoAIPlayer{
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
	
	private HantoPiece opponentHorses[] = {	new HantoPieceImpl(RED, HORSE), 
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE),
											new HantoPieceImpl(RED, HORSE)};

	/**
	 * Constructor for EpsilonHantoAIPlayer
	 * @param myColor Color of this player
	 * @param doIMoveFirst Whether this player move first
	 */
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

	/**
	 * Function to calculate a valid move to be made
	 * @param opponentsMove The latest move of the opponent
	 * @return A move record to pass to the opponent
	 */
	@Override
	public HantoMoveRecord makeMove(HantoMoveRecord opponentsMove) {
		
		
		HantoMoveRecord moveMade = null;
		try {
			
			// Making first move in first turn, has to be placed at origin (0, 0)
			if (doIMoveFirst && opponentsMove == null) {
				game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
				pieces.put(myButterfly, new HantoCoordinateImpl(0, 0));
				moveMade = new HantoMoveRecord(BUTTERFLY, null, new HantoCoordinateImpl(0, 0));
				moveCount++;
//				
			}

			// Making second move in first turn, has to be placed next to origin (0, 0)
			else if (!doIMoveFirst && opponentsMove != null && moveCount == 0) {
				recordOpponentMove(opponentsMove); // Record opponent's move
				game.makeMove(BUTTERFLY, null, new HantoCoordinateImpl(0, 1));
				pieces.put(myButterfly, new HantoCoordinateImpl(0, 1));
				moveMade = new HantoMoveRecord(BUTTERFLY, null, new HantoCoordinateImpl(0, 1));
				moveCount++;
			}

			// Normal moves
			else if (moveCount > 0) {
				recordOpponentMove(opponentsMove); // Record opponent's move
				if (moveMade == null) moveMade = placeHorse();
				if (moveMade == null) moveMade = placeSparrow();
				if (moveMade == null) moveMade = placeCrab();
				
				while (moveMade == null) {
					if (moveMade == null) moveMade = moveRandomHorse();
					if (moveMade == null) moveMade = moveRandomSparrow();
					if (moveMade == null) moveMade = moveRandomCrab();
				}
				moveCount++;
				
			}
			
			else {
				moveMade = new HantoMoveRecord(null, null, null);
			}
			
			
		} catch (HantoException e) {
			e.printStackTrace();
			//System.out.println(e.getMessage());
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
				else if (pieces.get(opponentSparrows[i]) != null && opponentFrom != null && pieces.get(opponentSparrows[i]).equals(opponentFrom)){
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
				else if (pieces.get(opponentCrabs[i]) != null && opponentFrom != null && pieces.get(opponentCrabs[i]).equals(opponentFrom)){
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
				else if (pieces.get(opponentHorses[i]) != null && opponentFrom != null && pieces.get(opponentHorses[i]).equals(opponentFrom)){
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
	
	private HantoMoveRecord placeHorse() throws HantoException {
		//HashMap<HantoPiece, HantoCoordinate> pieces = new HashMap<>(this.pieces);
		HantoMoveRecord moveMade = null;
		HantoCoordinate horse0 = pieces.get(myHorses[0]);
		HantoCoordinate horse1 = pieces.get(myHorses[1]);
		HantoCoordinate horse2 = pieces.get(myHorses[2]);
		HantoCoordinate horse3 = pieces.get(myHorses[3]);
		
		HantoPiece horseToBePlaced = null;
		HantoCoordinate horseNewPos = null;
		
		
		for (HantoPiece horse : myHorses) {
			if (pieces.get(horse) == null) {
				MoveHorseEpsilon moveHorse = new MoveHorseEpsilon(pieces, moveCount);
				List<HantoCoordinate> possibleMoves = moveHorse.getPossibleMoves(myColor, pieces, horse);
				if (possibleMoves.size() > 0) {
					horseToBePlaced = horse;
					horseNewPos = possibleMoves.get(0);
					moveMade = new HantoMoveRecord(HORSE, null, horseNewPos);
					break;
				}
			}
		}

		pieces.put(myHorses[0], horse0);
		pieces.put(myHorses[1], horse1);
		pieces.put(myHorses[2], horse2);
		pieces.put(myHorses[3], horse3);

		if (horseNewPos != null) {
			game.makeMove(HORSE, null, horseNewPos);
			pieces.put(horseToBePlaced, horseNewPos);
		}
		return moveMade;
	}
	
	private HantoMoveRecord placeSparrow() throws HantoException {
		
		HantoMoveRecord moveMade = null;
		
		HantoCoordinate sparrow0 = pieces.get(mySparrows[0]);
		HantoCoordinate sparrow1 = pieces.get(mySparrows[1]);
		HantoPiece sparrowToBePlaced = null;
		HantoCoordinate sparrowNewPos = null;
		
		for (HantoPiece sparrow : mySparrows) {
			if (pieces.get(sparrow) == null) {
				MoveSparrowEpsilon moveSparrow = new MoveSparrowEpsilon(pieces, moveCount);
				List<HantoCoordinate> possibleMoves = moveSparrow.getPossibleMoves(myColor, pieces, sparrow);
				if (possibleMoves.size() > 0) {
					sparrowToBePlaced = sparrow;
					sparrowNewPos = possibleMoves.get(0);
					moveMade = new HantoMoveRecord(SPARROW, null, sparrowNewPos);
					break;
				}
			}
		}
		pieces.put(mySparrows[0], sparrow0);
		pieces.put(mySparrows[1], sparrow1);
		
		if (sparrowNewPos != null) {
			game.makeMove(SPARROW, null, sparrowNewPos);
			pieces.put(sparrowToBePlaced, sparrowNewPos);
		}
		
		
		return moveMade;
	}
	
	private HantoMoveRecord placeCrab() throws HantoException {
		
		HantoMoveRecord moveMade = null;
		
		HantoCoordinate crab0 = pieces.get(myCrabs[0]);
		HantoCoordinate crab1 = pieces.get(myCrabs[1]);
		HantoCoordinate crab2 = pieces.get(myCrabs[2]);
		HantoCoordinate crab3 = pieces.get(myCrabs[3]);
		HantoCoordinate crab4 = pieces.get(myCrabs[4]);
		HantoCoordinate crab5 = pieces.get(myCrabs[5]);
		HantoPiece crabToBePlaced = null;
		HantoCoordinate crabNewPos = null;
		
		for (HantoPiece crab : myCrabs) {
			if (pieces.get(crab) == null) {
				MoveCrabEpsilon moveCrab = new MoveCrabEpsilon(pieces, moveCount);
				List<HantoCoordinate> possibleMoves = moveCrab.getPossibleMoves(myColor, pieces, crab);
				if (possibleMoves.size() > 0) {
					crabToBePlaced = crab;
					crabNewPos = possibleMoves.get(0);
					moveMade = new HantoMoveRecord(CRAB, null, crabNewPos);
					break;
				}
			}
		}
		
		pieces.put(myCrabs[0], crab0);
		pieces.put(myCrabs[1], crab1);
		pieces.put(myCrabs[2], crab2);
		pieces.put(myCrabs[3], crab3);
		pieces.put(myCrabs[4], crab4);
		pieces.put(myCrabs[5], crab5);
		
		if (crabNewPos != null) {
			game.makeMove(CRAB, null, crabNewPos);
			pieces.put(crabToBePlaced, crabNewPos);
		}
		
		return moveMade;
	}

	private HantoMoveRecord moveRandomHorse() throws HantoException {
		HantoMoveRecord moveMade = null;
		HantoPiece myHorse = myHorses[randInt(0, 3)];
		HantoCoordinate horse0 = pieces.get(myHorses[0]);
		HantoCoordinate horse1 = pieces.get(myHorses[1]);
		HantoCoordinate horse2 = pieces.get(myHorses[2]);
		HantoCoordinate horse3 = pieces.get(myHorses[3]);
		HantoCoordinate horseNewPos = null;
		HantoCoordinate horseCurPos = pieces.get(myHorse);

		MoveHorseEpsilon moveHorse = new MoveHorseEpsilon(pieces, moveCount);
		List<HantoCoordinate> possibleMoves = moveHorse.getPossibleMoves(myColor, pieces, myHorse);

		
		if (possibleMoves.size() > 1) {
			horseNewPos = possibleMoves.get(randInt(0, possibleMoves.size() - 1));

			moveMade = new HantoMoveRecord(HORSE, horseCurPos, horseNewPos);
//			System.out.println("moved horse from: " + horseCurPos.getX() + ", " + horseCurPos.getY() + " to: "
//					+ horseNewPos.getX() + ", " + horseNewPos.getY());
		}

		pieces.put(myHorses[0], horse0);
		pieces.put(myHorses[1], horse1);
		pieces.put(myHorses[2], horse2);
		pieces.put(myHorses[3], horse3);
		
		if (horseNewPos != null && !pieces.values().contains(horseNewPos)) {
		game.makeMove(HORSE, horseCurPos, horseNewPos);
		pieces.put(myHorse, horseNewPos);
		}
		
		return moveMade;
	}
	
	private HantoMoveRecord moveRandomSparrow() throws HantoException {
		HantoMoveRecord moveMade = null;
		HantoPiece mySparrow = mySparrows[randInt(0, 1)];
		HantoCoordinate sparrow0 = pieces.get(mySparrows[0]);
		HantoCoordinate sparrow1 = pieces.get(mySparrows[1]);
		HantoCoordinate sparrowNewPos = null;
		HantoCoordinate sparrowCurPos = pieces.get(mySparrow);

		
		MoveSparrowEpsilon moveSparrow = new MoveSparrowEpsilon(pieces, moveCount);
		List<HantoCoordinate> possibleMoves = moveSparrow.getPossibleMoves(myColor, pieces, mySparrow);
		if (possibleMoves.size() > 0) {
			sparrowNewPos = possibleMoves.get(randInt(0, possibleMoves.size() - 1));
			moveMade = new HantoMoveRecord(SPARROW, sparrowCurPos, sparrowNewPos);
//			System.out.println("moved sparrow from: " + sparrowCurPos.getX() + ", " + sparrowCurPos.getY() + " to: " + sparrowNewPos.getX() + ", " + sparrowNewPos.getY());
			
		}

		pieces.put(mySparrows[0], sparrow0);
		pieces.put(mySparrows[1], sparrow1);
		
		if (sparrowNewPos != null) {
			game.makeMove(SPARROW, sparrowCurPos, sparrowNewPos);
			pieces.put(mySparrow, sparrowNewPos);
		}
		
		return moveMade;
	}

	private HantoMoveRecord moveRandomCrab() throws HantoException {
		
		HantoMoveRecord moveMade = null;
		HantoPiece myCrab = myCrabs[randInt(0, 1)];
		
		HantoCoordinate crab0 = pieces.get(myCrabs[0]);
		HantoCoordinate crab1 = pieces.get(myCrabs[1]);
		HantoCoordinate crab2 = pieces.get(myCrabs[2]);
		HantoCoordinate crab3 = pieces.get(myCrabs[3]);
		HantoCoordinate crab4 = pieces.get(myCrabs[4]);
		HantoCoordinate crab5 = pieces.get(myCrabs[5]);
		HantoCoordinate crabNewPos = null;
		HantoCoordinate crabCurPos = pieces.get(myCrab);

		
		MoveCrabEpsilon moveCrab = new MoveCrabEpsilon(pieces, moveCount);
		List<HantoCoordinate> possibleMoves = moveCrab.getPossibleMoves(myColor, pieces, myCrab);
		if (possibleMoves.size() > 0) {
			crabNewPos = possibleMoves.get(randInt(0, possibleMoves.size() - 1));
			moveMade = new HantoMoveRecord(CRAB, crabCurPos, crabNewPos);
//			System.out.println("moved crab from: " + crabCurPos.getX() + ", " + crabCurPos.getY() + " to: " + crabNewPos.getX() + ", " + crabNewPos.getY());
			
		}
		
		pieces.put(myCrabs[0], crab0);
		pieces.put(myCrabs[1], crab1);
		pieces.put(myCrabs[2], crab2);
		pieces.put(myCrabs[3], crab3);
		pieces.put(myCrabs[4], crab4);
		pieces.put(myCrabs[5], crab5);
		
		if (crabNewPos != null) {
			game.makeMove(CRAB, crabCurPos, crabNewPos);
			pieces.put(myCrab, crabNewPos);
		}

		return moveMade;
	}
	
	private int randInt(int min, int max) {

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}

	public Map<HantoPiece, HantoCoordinate> getBoard() {
		return pieces;
	}
}
