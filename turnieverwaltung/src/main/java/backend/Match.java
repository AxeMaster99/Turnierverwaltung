package backend;

import org.miprojekt.turnieverwaltung.gui.MatchPane;
import org.miprojekt.turnieverwaltung.gui.windows.SpielBaum;

import interfaces.IMatch;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class Match implements IMatch {
	
	
	private static int indexCounter = 1;
	private int index;
	protected Mannschaft mannschaft1 = new Mannschaft("...");;
	protected Mannschaft mannschaft2 = new Mannschaft("...");;
	private MatchPane matchPane;
	protected Mannschaft sieger;
	private int toreM1 = 0;
	private int toreM2 = 0;

	public Match() {
		this.index = indexCounter;
		indexCounter++;
		this.matchPane = new MatchPane(this);
	}

	public Match(Mannschaft m1, Mannschaft m2) {
		this.index = indexCounter;
		indexCounter++;
		this.mannschaft1 = m1;
		this.mannschaft2 = m2;
		this.matchPane = new MatchPane(this);
	}

	public Match(IMatch m1, IMatch m2) {
		this.index = indexCounter;
		indexCounter++;
		this.mannschaft1 = new Mannschaft("...");
		this.mannschaft2 = new Mannschaft("...");
		this.matchPane = new MatchPane(this);
	}

	public void setMannschaft1(Mannschaft m1) {
		this.mannschaft1 = m1;
	}
	
	public Mannschaft getMannschaft1() {
		return mannschaft1;
	}

	public void setMannschaft2(Mannschaft m2) {
		this.mannschaft2 = m2;
	}
	
	public Mannschaft getMannschaft2() {
		return mannschaft2;
	}

	public int getIndex() {
		return this.index;
	}

	public String toString() {
		String ret = "Begegnung Nr. " + this.index;
		ret += "\nMannschaft1:" + this.getMannschaft1().getName();
		ret += "\nMannschaft2:" + this.getMannschaft2().getName();
		return ret;
	}

	public void setSieger() {
		if (toreM1 > toreM2) {
			this.sieger = mannschaft1;
		} else {
			this.sieger = mannschaft2;
		}

		System.out.println("Das Spiel endetete " + toreM1 + ":" + toreM2 + ". " + sieger + " hat gewonnen.");
		this.matchPane.setLabelErgebnis(toreM1,toreM2);
	}

	public Mannschaft getSieger() {
		return this.sieger;
	}

	public void incrementToreM1() {
		this.toreM1++;
	}

	public void incrementToreM2() {
		this.toreM2++;
	}

	public int getToreM1() {
		return this.toreM1;
	}

	public int getToreM2() {
		return this.toreM2;
	}
	
	public void setToreM1(int toreM1) {
		this.toreM1=toreM1;
	}

	public void setToreM2(int toreM2) {
		this.toreM2=toreM2;
	}

	public MatchPane getMatchPane() {
		return this.matchPane;
	}
	
	public boolean isGameFinished() {
		if(this.sieger != null) {
			return true;
		} else {
			return false;
		}
	}
	
	

}