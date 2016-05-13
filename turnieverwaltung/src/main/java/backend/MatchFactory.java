package backend;

import interfaces.IMatch;

public class MatchFactory {

	private Mannschaft m1 = null;
	private Mannschaft m2 = null;
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
	
	public IMatch build() {
		if(this.m1 != null && this.m2 != null) {
			return new Match(this.m1, this.m2);
		} else if(this.prevMatch1 != null && this.prevMatch2 != null) {
			return new FolgeMatch(this.prevMatch1, this.prevMatch2);
		} else {
			// exception
		}
		return null;
	}
	
}
