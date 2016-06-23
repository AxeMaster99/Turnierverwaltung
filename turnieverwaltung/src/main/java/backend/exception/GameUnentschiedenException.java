package backend.exception;

@SuppressWarnings("serial")
public class GameUnentschiedenException extends Exception {

	public GameUnentschiedenException (){
			super("Spiele im KO-System dürfen nicht Unentschieden enden.");
		}
}
