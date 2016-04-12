package org.miprojekt.turnieverwaltung;

import GUI.Hauptfenster;

public class GUI implements ISteuerbar {
	
	private Steuerung steuerung;
	
	public GUI(Steuerung steuerung) {
		this.steuerung = steuerung;
		
		new Hauptfenster();

	}

}
