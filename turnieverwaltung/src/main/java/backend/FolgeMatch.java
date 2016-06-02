package backend;

import interfaces.IMatch;
import verwaltung.Steuerung;

public class FolgeMatch extends Match {

	protected IMatch prevMatch1;
	protected IMatch prevMatch2;
	
	public FolgeMatch(Steuerung steuerung,IMatch prevMatch1, IMatch prevMatch2) {
		super(steuerung);
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
		String ret = "Begegnung Nr. " + this.index + ": ";
		ret += " Sieger Match " + this.prevMatch1.getIndex();
		ret += " gegen Sieger Match " + this.prevMatch2.getIndex();
		return ret;
	}
	
}
