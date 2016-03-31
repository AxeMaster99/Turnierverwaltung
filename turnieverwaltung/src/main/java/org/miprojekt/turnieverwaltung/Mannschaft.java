package org.miprojekt.turnieverwaltung;

import java.util.ArrayList;

public class Mannschaft {

	private String name;
	private String liga;
	private int rang;
	private ArrayList<Mannschaftsmitglied> mitglieder = new ArrayList<Mannschaftsmitglied>();

	public Mannschaft(String name) {
		
		this.name = name;
		
		this.mitglieder.add(new Trainer("Hallo"));
		this.mitglieder.add(new Spieler("Anton"));
		this.mitglieder.add(new Spieler("Blubb"));
		this.mitglieder.add(new Spieler("Peter"));
		this.mitglieder.add(new Spieler("Google"));
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Mannschaftsmitglied> getMitglieder() {
		return mitglieder;
	}	
	
}
