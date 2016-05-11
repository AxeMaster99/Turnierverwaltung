package backend;

public class Match {

	Mannschaft mannschaft1;
	Mannschaft mannschaft2;
	
	public Match(Mannschaft m1, Mannschaft m2) {
		this.mannschaft1 = m1;
		this.mannschaft2 = m2;
	}
	
	public Mannschaft getMannschaft1() {
		return mannschaft1;
	}
	
	public Mannschaft getMannschaft2() {
		return mannschaft2;
	}
	
	
}