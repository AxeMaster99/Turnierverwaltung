package backend.exception;

@SuppressWarnings("serial")
public class GameNotFinishedException extends Exception {

	public GameNotFinishedException() {
		super("Das Spiel wurde noch nicht beendet, es steht noch kein Sieger fest.");
	}
}
