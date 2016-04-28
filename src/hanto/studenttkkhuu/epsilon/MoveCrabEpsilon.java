package hanto.studenttkkhuu.epsilon;

import static hanto.common.HantoPieceType.CRAB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import hanto.common.HantoCoordinate;
import hanto.common.HantoException;
import hanto.common.HantoPiece;
import hanto.common.HantoPlayerColor;
import hanto.common.HantoPrematureResignationException;
import hanto.studenttkkhuu.common.AbsMoveCrab;
import hanto.studenttkkhuu.common.HantoCoordinateImpl;

public class MoveCrabEpsilon extends AbsMoveCrab{
	
	public MoveCrabEpsilon(Map<HantoPiece, HantoCoordinate> pieces, int moveCount) {
		super(pieces, moveCount);
	}

	@Override
	public Map<HantoPiece, HantoCoordinate> movePiece(HantoCoordinate source, HantoCoordinate destination,
			HantoPlayerColor color) throws HantoException {
		
		// The player made a resignation
		if (source == null && destination == null) {
			
			checkAvailableMove(color);
			
			return null;
		}
		
		return super.movePiece(source, destination, color);
	}
	
	@Override
	protected HantoPiece moveToHex(List<HantoCoordinate> path, HantoPlayerColor color) throws HantoException {
		
		if (path == null) {
			throw new HantoException("Crab can only walk one Hex at a time");
		}
		
		return super.moveToHex(path, color);
	}

	@Override
	protected List<HantoCoordinate> getPath(HantoCoordinate source, HantoCoordinate destination){
		
		// ========================= Move One Hex =========================
		if (isNeighborHex(source, destination)) {
			HantoCoordinate[] path = {source, destination};
			return Arrays.asList(path);
		}
		return null;
	}
	
	private void checkAvailableMove(HantoPlayerColor color)
			throws HantoPrematureResignationException {
		
		// If there are crab pieces not placed, check if any of them can be placed
		HantoPiece crabPieceNotPlaced = null;
		for (HantoPiece hp : pieces.keySet()) {
			if (hp.getColor() == color && hp.getType() == CRAB && pieces.get(hp) == null) {
				crabPieceNotPlaced = hp;
				break;
			}
		}
		if (crabPieceNotPlaced != null) {
			List<HantoCoordinate> possibleMoves = getEmptyNeighborsOfSameColor(color);
			int numberOfInvalidPath = 0;
			for (HantoCoordinate toHex : possibleMoves) {
				try{
					movePiece(null, toHex, color);
				} catch (HantoException he) {
					pieces.put(crabPieceNotPlaced, null);
					System.out.println(he.getMessage() + " X: " + toHex.getX() + " Y: " + toHex.getY());
					numberOfInvalidPath++;
				}
				
				if (numberOfInvalidPath < possibleMoves.size()) {
					throw new HantoPrematureResignationException();
				}
			}
		}
		
		// If the crab pieces cannot be placed or all used, check the pieces on the board for valid move
		List<HantoPiece> crabPiecesPlaced = new ArrayList<HantoPiece>();
		
		for (HantoPiece p : pieces.keySet()) {
			if (p.getColor() == color && p.getType() == CRAB && pieces.get(p) != null) {
				crabPiecesPlaced.add(p);
			}
		}
		
		for (HantoPiece crabPiece : crabPiecesPlaced) {

			final HantoCoordinateImpl crabPieceHCI = new HantoCoordinateImpl(pieces.get(crabPiece));
			List<HantoCoordinate> possibleMoves = crabPieceHCI.getNeighbors();
			int numberOfInvalidPath = 0;
			for (HantoCoordinate toHex : possibleMoves) {
				try {
					movePiece(crabPieceHCI, toHex, color);
				} catch (HantoException he) {
					pieces.put(crabPiece, crabPieceHCI);
					System.out.println(he.getMessage() + " X: " + toHex.getX() + " Y: " + toHex.getY());
					numberOfInvalidPath++;
				}
			}

			if (numberOfInvalidPath < possibleMoves.size()) {
				throw new HantoPrematureResignationException();
			}
		}
	}

}
