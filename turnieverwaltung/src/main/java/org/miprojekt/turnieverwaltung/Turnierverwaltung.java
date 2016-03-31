package org.miprojekt.turnieverwaltung;

import java.util.ArrayList;

public class Turnierverwaltung {

	private Spielplan spielplan;
	private ArrayList<Mannschaft> mannschaften = new ArrayList<Mannschaft>();

	public Turnierverwaltung() {

		mannschaften.add(new Mannschaft("VFB"));
		mannschaften.add(new Mannschaft("Bayern München"));

		for (Mannschaft m : mannschaften) {

			System.out.println(m.getName() + " hat folgende Mitglieder: ");

			for (Mannschaftsmitglied mm : m.getMitglieder()) {

				if (mm instanceof Trainer) {
					System.out.println(mm.getVorname() + " (Trainer)");
				} else {
					System.out.println(mm.getVorname() + " (Spieler)");
				}
			}

			System.out.print("\n");

		}

	}

}
