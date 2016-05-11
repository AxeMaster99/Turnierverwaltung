package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.ArrayList;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.MatchPane;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import backend.FolgeMatch;
import backend.Mannschaft;
import backend.Match;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SpielBaum extends SceneParent {

	private ArrayList<Match> matches;

	public SpielBaum(Main main, ArrayList<Match> matches) {
		super(main);
		this.matches = matches;

		int anzahlSpalten = 0;
		int anzahlMatchesZus = 0;

		switch (this.matches.size()*2) {
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
		for(int i = 0; i < this.matches.size() / 2; i++) {
			matchesLinks.add(this.matches.get(i));
		}
		for(int i = 0; i < (anzahlMatchesZus / 2); i++) {
			// matchesLinks.add(new FolgeMatch(matchesLinks.get(actMatch)), new FolgeMatch(matchesLinks.get(actMatch)));
			
			Match pm1 = matchesLinks.get(actMatch);
			Match pm2 = matchesLinks.get(actMatch+1);
			
			matchesLinks.add(new FolgeMatch(pm1, pm2));
			actMatch = actMatch+2;
		}
		
		matchesLinks.size();
		
		for (int i = 0; i < matchesLinks.size(); i++) {
			System.out.println("Begegnung Nr. " + (i + 1));
			System.out.println("m1:" + matchesLinks.get(i).getMannschaft1().getName());
			System.out.println("m2:" + matchesLinks.get(i).getMannschaft2().getName());
			if(matchesLinks.get(i) instanceof FolgeMatch) {
				System.out.println("PrevMatch: "+((FolgeMatch) matchesLinks.get(i)).getPrevMatch1().getIndex());
				System.out.println("PrevMatch: "+((FolgeMatch) matchesLinks.get(i)).getPrevMatch2().getIndex());
			}
			System.out.println();
		}
		
	}
	
}
