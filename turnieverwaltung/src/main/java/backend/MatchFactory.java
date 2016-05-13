package backend;

import interfaces.IMatch;

public class MatchFactory {

	private Mannschaft mannschaft1 = null;
	private Mannschaft mannschaft2 = null;
	private Match prevMatch1 = null;
	private Match prevMatch2 = null;
	
	public MatchFactory addMatch(IMatch m) {
		if(this.prevMatch1 == null) {
			this.prevMatch1 = (Match) m;
		} else if(prevMatch2 == null) {
			this.prevMatch2 = (Match) m;
		} else {
			// exception
		}
		return this;
	}
	
	public MatchFactory addMannschaft(String m) {
		if(this.mannschaft1 == null) {
			this.mannschaft1 = new Mannschaft(m);
		} else if(this.mannschaft2 == null) {
			this.mannschaft2 = new Mannschaft(m);
		} else {
			// exception
		}
		return this;
	}
	
	public IMatch build() {
		IMatch createdMatch = null;
		if(this.mannschaft1 != null && this.mannschaft2 != null) {
			createdMatch = new Match(this.mannschaft1, this.mannschaft2);
		} else if(this.prevMatch1 != null && this.prevMatch2 != null) {
			createdMatch = new FolgeMatch(this.prevMatch1, this.prevMatch2);
		} else {
			// exception
		}
		return createdMatch;
	}
	
}
