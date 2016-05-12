package org.miprojekt.turnieverwaltung;

import java.util.ArrayList;
import java.util.Collections;

import org.miprojekt.turnieverwaltung.gui.MatchPane;

import backend.FinalMatch;
import backend.FolgeMatch;
import backend.Mannschaft;
import backend.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;

public class Steuerung {

	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ArrayList<Match> matches = new ArrayList<Match>();
	private ArrayList<Match> matchesLinks = new ArrayList<Match>();
	private ArrayList<Match> matchesRechts = new ArrayList<Match>();
	

	public void erstelleMatches(ObservableList<String> teams) {
		this.teams = teams;

		Collections.shuffle(teams); // hässlichste ZEILE

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

		erstelleSeite(anzahlMatchesZus, 0, this.teams.size() / 2);
		erstelleSeite(anzahlMatchesZus, (this.teams.size() / 2) - 1, this.teams.size() - 1);
		
		//matchesRechts = erstelleSeite(anzahlMatchesZus, (this.teams.size() / 2) - 1, this.teams.size() - 1);

		if(matchesLinks == matchesRechts) {
			System.out.println("same");
		}
		
		// FinalMatch finale = new
		// FinalMatch(matchesLinks.get(matchesLinks.size()), null);
	}

	private void erstelleSeite(int anzahlMatchesZus, int start, int stop) {
		
		int actMatch = start;
		for (int i = start; i < stop; i += 2) {
			matches.add(new Match(new Mannschaft(teams.get(i)), new Mannschaft(teams.get(i + 1))));
		}
		for (int i = 0; i < (anzahlMatchesZus / 2); i++) {

			Match pm1 = matches.get(actMatch);
			Match pm2 = matches.get(actMatch + 1);

			matches.add(new FolgeMatch(pm1, pm2));
			actMatch = actMatch + 2;
		}

		for (int i = start; i < matches.size(); i++) {
			System.out.println("Begegnung Nr. " + (i + 1));
			System.out.println("m1:" + matches.get(i).getMannschaft1().getName());
			System.out.println("m2:" + matches.get(i).getMannschaft2().getName());
			if (matches.get(i) instanceof FolgeMatch) {
				System.out.println("PrevMatch: " + ((FolgeMatch) matches.get(i)).getPrevMatch1().getIndex());
				System.out.println("PrevMatch: " + ((FolgeMatch) matches.get(i)).getPrevMatch2().getIndex());
			}
			System.out.println();
		}

	}

	public ArrayList<Match> getMatches() {
		return this.matches;
	}
	
	public ArrayList<Match> getMatchesLinks() {
		return this.matchesLinks;
	}
	
	public ArrayList<Match> getMatchesRechts() {
		return this.matchesRechts;
	}

}
