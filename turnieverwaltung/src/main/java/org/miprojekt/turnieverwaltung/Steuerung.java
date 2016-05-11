package org.miprojekt.turnieverwaltung;

import java.util.ArrayList;
import java.util.Collections;
import backend.Mannschaft;
import backend.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Steuerung {

	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ArrayList<Match> matches = new ArrayList<Match>();

	public void setTeams(ObservableList<String> teams) {

		this.teams = teams;
		// Collections.shuffle(teams);

		// Matches erstellen 
		for (int i = 0; i < teams.size() / 2; i = i + 2) {
			Match m = new Match(new Mannschaft(teams.get(i)), new Mannschaft(teams.get(i + 1)));
			matches.add(m);
		}
	}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}

}
