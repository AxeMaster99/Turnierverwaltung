package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.ArrayList;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import backend.Match;

public class SpielBaum extends SceneParent {

	private ArrayList<Match> matches;

	public SpielBaum(Main main, ArrayList<Match> matches) {
		super(main);
		this.matches = matches;

		System.out.println(this.matches.size());

		for (int i = 0; i < this.matches.size(); i++) {
			System.out.print("Begegnung "+ (i+1) + ": " + matches.get(i).getMannschaft1().getName() + " gegen ");
			System.out.println(matches.get(i).getMannschaft2().getName());
		}

	}
}
