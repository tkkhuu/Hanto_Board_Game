package hanto.common;

/**
* Exception that is only thrown when a player resigns while there
* is still a valid move available for that player.
* @version Oct 6, 2014
*/
public class HantoPrematureResignationException extends HantoException {

	public HantoPrematureResignationException(){
		super("You resigned when you have a valid move available.");
	}
	
}
