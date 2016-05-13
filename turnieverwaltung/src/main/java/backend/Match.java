package backend;

import org.miprojekt.turnieverwaltung.gui.MatchPane;

import interfaces.IMatch;

public class Match implements IMatch {

	private static int indexCounter = 1;
	private int index;
	protected Mannschaft mannschaft1;
	protected Mannschaft mannschaft2;
	private MatchPane matchPane;
	
	public Match(Mannschaft m1, Mannschaft m2) {
		this.matchPane = new MatchPane(m1.getName(), m2.getName());
		index = indexCounter;
		indexCounter++;
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
	
	public MatchPane getMatchPane() {
		return this.matchPane;
	}
	
	public String toString() {
		String ret = "Begegnung Nr. " + (this.index + 1);
		ret += "\nMannschaft1:" + this.mannschaft1.getName();
		ret += "\nMannschaft2:" + this.mannschaft2.getName();
		return ret;
	}
	
}