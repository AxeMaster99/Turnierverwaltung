package backend.turnier;

import backend.interfaces.IMatch;

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

	public static IMatch build(Steuerung steuerung, Mannschaft mannschaft, Mannschaft mannschaft2) {
		return new Match(steuerung, mannschaft, mannschaft2);
	}

	public static IMatch build(Mannschaft m1, Mannschaft m2) {
		return new Match(m1, m2);
	}
	
	public static boolean isInstanceOf(IMatch match, String className) {
		if(match.getClass().getName().equals(className)) {
			return true;
		} else {
			return false;
		}
	}
	
}
