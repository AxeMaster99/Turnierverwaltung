package verwaltung;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.Logger;

import backend.MatchFactory;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import stages.RangStage;

public class Steuerung {

	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ArrayList<IMatch> matches = new ArrayList<IMatch>();
	private int anzahlSpalten = 0;
	private int anzahlMatchesZus = 0;
	private static final Logger log = Logger.getLogger(Steuerung.class.getClass());
	
	public Steuerung() {
		
	}
	
	public void erstelleMatches(ObservableList<String> teams) throws Exception {
		this.teams = teams;
		
		Collections.shuffle(teams); // beste ZEILE

		switch (this.teams.size()) {
		case 4:
			anzahlSpalten = 3;
			anzahlMatchesZus = 1;
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
		erstelleSeite(anzahlMatchesZus, (this.teams.size() / 2)-1, this.teams.size() - 1);

		IMatch prevFinal1 = this.matches.get(this.matches.size() / 2 - 1);
		IMatch prevFinal2 = this.matches.get(this.matches.size() - 1);

		this.matches.add(new MatchFactory().addMatch(prevFinal1).addMatch(prevFinal2).isFinalMatch().build());

		for (int i = 0; i < matches.size(); i++) {
			System.out.println(this.matches.get(i).toString());
			System.out.println();
		}

	}

	private void erstelleSeite(int anzahlMatchesZus, int start, int stop) throws Exception {

		int actMatch = start;
		for (int i = start; i <stop; i += 2) {
			if(start >= 1)
				{
				matches.add(new MatchFactory().addMannschaft(teams.get(i+1)).addMannschaft(teams.get(i + 2)).build());
			} else {
				matches.add(new MatchFactory().addMannschaft(teams.get(i)).addMannschaft(teams.get(i + 1)).build());
			}
		}
		for (int i = 0; i < (anzahlMatchesZus / 2); i++) {

			IMatch pm1 = matches.get(actMatch);
			IMatch pm2 = matches.get(actMatch + 1);

			// beste Fabrik
			matches.add(new MatchFactory().addMatch(pm1).addMatch(pm2).build());

			actMatch = actMatch + 2;
		}

	}

	public ArrayList<IMatch> getMatches() {
		return this.matches;
	}

	public int getAnzahlSpalten() {
		return this.anzahlSpalten;
	}

	public void erstelleRangliste() {
		RangStage rangliste = new RangStage(this.matches);
		rangliste.show();
		
	}

}
