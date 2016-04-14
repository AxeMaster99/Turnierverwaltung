package org.miprojekt.turnieverwaltung;

public class Steuerung {

	private Turnier turnier;
	
	public Steuerung() {
		this.turnier = new Turnier(this);
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}
	
}
