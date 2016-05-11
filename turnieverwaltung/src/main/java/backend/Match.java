package backend;

public class Match {

	private static int indexCounter = 1;
	private int index;
	protected Mannschaft mannschaft1;
	protected Mannschaft mannschaft2;
	
	public Match(Mannschaft m1, Mannschaft m2) {
		index = indexCounter;
		indexCounter++;
		System.out.println("index erstellet "+index);
		this.mannschaft1 = m1;
		this.mannschaft2 = m2;
	}
	
	public Mannschaft getMannschaft1() {
		return mannschaft1;
	}
	
	public Mannschaft getMannschaft2() {
		return mannschaft2;
	}
	
	public int getIndex() {
		return this.index;
	}
	
}