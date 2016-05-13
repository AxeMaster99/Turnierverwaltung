package backend;

import interfaces.IMatch;

public class MatchFactory {

	public static IMatch createMatch(Mannschaft m1, Mannschaft m2) {
		return new Match(m1, m2);	
	}
	
	public static IMatch createMatch(Match m1, Match m2) {
		return new FolgeMatch(m1, m2);
	}
	
	public static IMatch createMatch(IMatch m1, IMatch m2) {
		return new FolgeMatch((Match) m1, (Match) m2);
	}
	
}
