package backend.turnier;

import backend.interfaces.IMatch;
import main.Steuerung;

public final class FinalMatch extends FolgeMatch {

	public FinalMatch(Steuerung steuerung,IMatch prevMatch1, IMatch prevMatch2) {
		super(steuerung,prevMatch1, prevMatch2);
	}
	
	public String toString() {
		return "Finale: " + super.toString();
	}
	
}
