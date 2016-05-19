package threads;

import interfaces.IMatch;
import javafx.application.Platform;
import stages.MatchStage;

public class GUIUpdateThread extends Thread {

	private IMatch match;
	private MatchStage matchStage;

	public GUIUpdateThread(IMatch match, MatchStage matchStage) {
		this.match = match;
		this.matchStage = matchStage;
	}

	@Override
	public void run() {
		while (match.getToreM1() == match.getToreM2()) {
			System.out.print(""); // tue nichts (ohne diese Zeile funktioniert es nich?!)
		}
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				matchStage.beendeSpiel();
			}
		});
		interrupt();
	}
}
