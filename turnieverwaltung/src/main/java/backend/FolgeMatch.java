package backend;

import interfaces.IMatch;

public class FolgeMatch extends Match {

	private Match prevMatch1;
	private Match prevMatch2;
	
	public FolgeMatch(Match prevMatch1, Match prevMatch2) {
		super(new Mannschaft("..."), new Mannschaft("..."));
		this.prevMatch1 = prevMatch1;
		this.prevMatch2 = prevMatch2;
	}
	
	public Match getPrevMatch1() {
		return this.prevMatch1;
	}
	
	public Match getPrevMatch2() {
		return this.prevMatch2;
	}
	
	public String toString() {
		String ret = "Folgematch\n";
		ret = super.toString();
		ret += "\nVorheriges Match1: " + this.prevMatch1.getIndex();
		ret += "\nVorheriges Match2: " + this.prevMatch2.getIndex();
		return ret;
	}
	
}
