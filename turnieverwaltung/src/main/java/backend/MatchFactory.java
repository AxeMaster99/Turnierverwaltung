package backend;

import interfaces.IMatch;
import verwaltung.Steuerung;

public abstract class MatchFactory {

	public static IMatch build(Steuerung s, String m1, String m2) {
		return new Match(s,  new Mannschaft(m1), new Mannschaft(m2));
	}
	
	public static IMatch build(Steuerung s, IMatch m1, IMatch m2) {
		return new FolgeMatch(s, m1, m2);
	}

	public static IMatch build(Steuerung s, IMatch m1, IMatch m2, boolean isFinal) {
		return new FinalMatch(s, m1, m2);
	}
	
}
