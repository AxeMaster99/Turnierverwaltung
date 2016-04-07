package org.miprojekt.turnieverwaltung;

public class Turnier implements ISteuerbar {

	private Steuerung steuerung;
	
	public Turnier(Steuerung steuerung) {
		this.steuerung = steuerung;
	}
	
}
