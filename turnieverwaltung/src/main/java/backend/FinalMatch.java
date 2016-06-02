package backend;

import interfaces.IMatch;
import verwaltung.Steuerung;

public final class FinalMatch extends FolgeMatch implements IMatch {

	public FinalMatch(Steuerung steuerung,IMatch prevMatch1, IMatch prevMatch2) {
		super(steuerung,prevMatch1, prevMatch2);
	}
	
	public String toString() {
		return "Finale: " + super.toString();
	}
	
}
