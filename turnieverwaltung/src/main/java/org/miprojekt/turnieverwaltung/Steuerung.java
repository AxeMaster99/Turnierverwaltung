package org.miprojekt.turnieverwaltung;

import java.util.ArrayList;
import java.util.Collections;

import backend.FolgeMatch;
import backend.Mannschaft;
import backend.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Steuerung {

	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ArrayList<Match> matches = new ArrayList<Match>();


	public void erstelleMatches(ObservableList<String> teams) {
		this.teams=teams;
		
		Collections.shuffle(teams); //beste ZEILE
		
		int anzahlSpalten = 0;
		int anzahlMatchesZus = 0;

		switch (this.teams.size()) {
		case 2:
			anzahlSpalten = 1;
			anzahlMatchesZus = 0;
			break;
		case 4:
			anzahlSpalten = 3;
			anzahlMatchesZus = 0;
			break;
		case 8:
			anzahlSpalten = 5;
			anzahlMatchesZus = 2;
			break;
		case 16:
			anzahlSpalten = 7;
			anzahlMatchesZus = 6;
			break;
		case 32:
			anzahlSpalten = 9;
			anzahlMatchesZus = 14;
			break;
		default:
			anzahlSpalten = 0;
		}

		ArrayList<Match> matchesLinks = new ArrayList<Match>();
		ArrayList<Match> matchesRechts = new ArrayList<Match>();

		int actMatch = 0;
		for(int i = 0; i < teams.size() / 2; i += 2) {
			matchesLinks.add(new Match(new Mannschaft(teams.get(i)), new Mannschaft(teams.get(i+1))));
			
		}
		for (int i = 0; i < (anzahlMatchesZus / 2); i++) {

			Match pm1 = matchesLinks.get(actMatch);
			Match pm2 = matchesLinks.get(actMatch + 1);

			matchesLinks.add(new FolgeMatch(pm1, pm2));
			actMatch = actMatch + 2;
		}

		for (int i = 0; i < matchesLinks.size(); i++) {
			System.out.println("Begegnung Nr. " + (i + 1));
			System.out.println("m1:" + matchesLinks.get(i).getMannschaft1().getName());
			System.out.println("m2:" + matchesLinks.get(i).getMannschaft2().getName());
			if (matchesLinks.get(i) instanceof FolgeMatch) {
				System.out.println("PrevMatch: " + ((FolgeMatch) matchesLinks.get(i)).getPrevMatch1().getIndex());
				System.out.println("PrevMatch: " + ((FolgeMatch) matchesLinks.get(i)).getPrevMatch2().getIndex());
			}
			System.out.println();
		}
	}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}

}
