package GUI;

import GUI.GroupScreen.GroupMatchStage;
import GUI.TreeScreen.TreeMatchStage;
import backend.interfaces.IMatch;
import javafx.application.Platform;

public class WaitForButtonThread extends Thread {

	private IMatch match;
	private MatchStage matchStage;
	

	public WaitForButtonThread(IMatch match, MatchStage matchStage) {
		this.match = match;
		this.matchStage = matchStage;	
	}

	@Override
	public void run() {
		
		while (this.match.getToreM1() == this.match.getToreM2()) {} // nix tun solange Spiel noch Unentschieden
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				match.getMatchPane().setLabelErgebnis(match.getToreM1(), match.getToreM2());
				if (matchStage instanceof TreeMatchStage) {
					((TreeMatchStage) matchStage).beendeSpiel();
				} else {
					((GroupMatchStage) matchStage).beendeSpiel();
				}
			}
		});
		interrupt();
	}
}
