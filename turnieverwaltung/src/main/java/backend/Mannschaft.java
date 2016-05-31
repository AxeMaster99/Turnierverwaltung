package backend;

public class Mannschaft {
	private String name = "...";
	// private Spieler spieler;
	private int punkte = 0;
	private int tordifferenz = 0;

	public Mannschaft() {
		this.name = "...";
	}

	public Mannschaft(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void addPunkte() {
		this.punkte += 3;
	}

	public void aendereTordifferenz(int tore) {
		this.tordifferenz += tore;
	}

	public int getPunkte() {
		return punkte;
	}

	public int getTordifferenz() {
		return tordifferenz;
	}

	public void addPunkteUnentschieden() {
		this.punkte += 1;
	}

}
