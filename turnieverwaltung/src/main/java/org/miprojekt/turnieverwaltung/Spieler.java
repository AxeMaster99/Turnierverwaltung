package org.miprojekt.turnieverwaltung;

public class Spieler extends Mannschaftsmitglied {
	
	enum Position {
		Torwart, Abwehr, Mittelfeld, Sturm
	}
	
	private Position position;
	private int tore;
	
	public Spieler(String vorname) {
		super(vorname);
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getTore() {
		return tore;
	}

	public void torGeschossen () {
		this.tore++;
	}
	
}
