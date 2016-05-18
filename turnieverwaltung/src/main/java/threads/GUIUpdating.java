package threads;

import org.miprojekt.turnieverwaltung.gui.MatchStage;

import interfaces.IMatch;
import javafx.application.Platform;

public class GUIUpdating extends Thread {

	private IMatch match;
	private MatchStage matchStage;

	public GUIUpdating(IMatch match, MatchStage matchStage) {
		this.match = match;
		this.matchStage = matchStage;
	}

	@Override
	public void run() {
		while (match.getToreM1() == match.getToreM2()) {
			System.out.print(""); // ture nichts (ohne diese Zeile funktioniert es nich?!)
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
