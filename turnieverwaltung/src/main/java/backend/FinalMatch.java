package backend;

import interfaces.IMatch;

public final class FinalMatch extends FolgeMatch {

	public FinalMatch() {
		super();
	}

	public void setPrevMatch1(IMatch m) {
		this.prevMatch1 = (Match) m;
	}
	
	public void setPrevMatch2(IMatch m) {
		this.prevMatch2 = (Match) m;
	}
	
	public String toString() {
		return "Finale\n" + super.toString();
	}
	
}
