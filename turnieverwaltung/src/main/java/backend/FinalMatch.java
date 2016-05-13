package backend;

import interfaces.IMatch;

public final class FinalMatch extends FolgeMatch implements IMatch {

	public FinalMatch(IMatch prevMatch1, IMatch prevMatch2) {
		super(prevMatch1, prevMatch2);
	}
	
	public String toString() {
		return "Finale\n" + super.toString();
	}
	
}
