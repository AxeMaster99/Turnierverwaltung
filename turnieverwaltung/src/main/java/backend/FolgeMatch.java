package backend;

import interfaces.IMatch;

public class FolgeMatch extends Match {

	protected IMatch prevMatch1;
	protected IMatch prevMatch2;
	
	public FolgeMatch(IMatch prevMatch1, IMatch prevMatch2) {
		super();
		this.prevMatch1 = prevMatch1;
		this.prevMatch2 = prevMatch2;
	}

	public IMatch getPrevMatch1() {
		return this.prevMatch1;
	}
	
	public IMatch getPrevMatch2() {
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
