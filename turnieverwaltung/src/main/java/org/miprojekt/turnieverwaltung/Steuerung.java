package org.miprojekt.turnieverwaltung;

public class Steuerung {

	private GUI gui;
	private Turnier turnier;
	
	public Steuerung() {
		this.gui = new GUI(this);
		this.turnier = new Turnier(this);
	}

	public GUI getGui() {
		return gui;
	}

	public void setGui(GUI gui) {
		this.gui = gui;
	}

	public Turnier getTurnier() {
		return turnier;
	}

	public void setTurnier(Turnier turnier) {
		this.turnier = turnier;
	}
	
}
