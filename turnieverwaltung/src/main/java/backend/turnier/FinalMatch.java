package backend.turnier;

import backend.interfaces.IMatch;

public final class FinalMatch extends FolgeMatch {

	public FinalMatch(Steuerung steuerung,IMatch prevMatch1, IMatch prevMatch2) {
		super(steuerung,prevMatch1, prevMatch2);
	}
	
	public String toString() {
		return "Finale: " + super.toString();
	}
	
}
