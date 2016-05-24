package backend;

import interfaces.IMatch;
import verwaltung.Steuerung;

public class MatchFactory {

	private String m1Name;
	private String m2Name;
	private IMatch prevMatch1;
	private IMatch prevMatch2;
	private boolean isFinalMatch;
	private Steuerung steuerung;
	
	public MatchFactory (Steuerung steuerung){
		this.steuerung = steuerung;
	}

	public IMatch build() throws Exception {
		IMatch createdMatch = null;
		if(this.isFinalMatch == true) {
			System.out.println("try to create finale");
			createdMatch = new FinalMatch(steuerung,prevMatch1, prevMatch2);
			// Exception wenn prevmatch1 und 2 nicht drin
		} else if(this.prevMatch1 == null && this.prevMatch2 == null && !this.m1Name.isEmpty() && !this.m2Name.isEmpty()) {
			System.out.println("try to create match");
			createdMatch = new Match(steuerung,new Mannschaft(m1Name), new Mannschaft(m2Name));
		} else if(this.prevMatch1 != null && this.prevMatch2 != null) {
			System.out.println("try to create folgematch");
			createdMatch = new FolgeMatch(steuerung,this.prevMatch1, this.prevMatch2);
		} else {
			throw new Exception("Exception from Factory");
		}
		return createdMatch;
	}
	
	public MatchFactory isFinalMatch() {
		this.isFinalMatch = true;
		return this;
	}

	public MatchFactory addMatch(IMatch m) throws Exception {
		if(this.prevMatch1 == null) {
			this.prevMatch1 = m;
		} else if(prevMatch2 == null) {
			this.prevMatch2 = m;
		} else {
			throw new Exception("Exception from Factory addMatch");
		}
		return this;
	}
	
	public MatchFactory addMannschaft(String m) {
		if(this.m1Name == null) {
			this.m1Name = m;
		} else if(this.m2Name == null) {
			this.m2Name = m;
		} else {
			// exception
		}
		return this;
	}
	
}
