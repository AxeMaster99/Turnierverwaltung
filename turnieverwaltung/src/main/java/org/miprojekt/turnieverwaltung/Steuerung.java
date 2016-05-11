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
		// teams durchmischen
		Collections.shuffle(teams);

		// Matches erstellen
		for (int i = 0; i < teams.size() - 1; i = i + 2) {
			Match m = new Match(new Mannschaft(teams.get(i)), new Mannschaft(teams.get(i + 1)));
			matches.add(m);
		}
		System.out.println(matches.size());
		/*
		 * System.out.println(matches);
		 * 
		 * for(int i = 0; i < this.teams.size()-1; i+=2) { System.out.println(
		 * "Begegnung " +((i+2)/2)+": "); System.out.println(this.teams.get(i)+
		 * " gegen "+ this.teams.get(i+1)); System.out.println(""); } //diese
		 * Ausgabe entspricht eigentlich der darüber! nur zu Testzwecken for(int
		 * i=0; i<this.matches.size();i++){
		 * System.out.print(matches.get(i).getMannschaft1().getName() +" gegen "
		 * ); System.out.println(matches.get(i).getMannschaft2().getName());
		 * 
		 * }
		 */}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}

}
