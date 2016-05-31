package verwaltung;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import backend.MatchFactory;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import screens.GroupScreen;
import screens.SettingsScreen;
import screens.SplashScreen;
import screens.TeamScreen;
import screens.TreeScreen;
import stages.RangStage;

public class Steuerung {

	private String turnierType;
	private TreeScreen spielBaum;
	private Main main;
	private RangStage rangliste;
	private ObservableList<String> teams = FXCollections.observableArrayList();
	private ArrayList<IMatch> matches = new ArrayList<IMatch>();
	private int anzahlSpalten = 0;
	private int anzahlMatchesZus = 0;
	private GroupScreen groupScreen;
	
	private static final Logger logger = (Logger) LogManager.getLogger("Steuerung");
	
	public Steuerung(Main main) {
		this.main = main;
		rangliste = new RangStage(this);
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
		erstelleSeite(anzahlMatchesZus, (this.teams.size() / 2) - 1, this.teams.size() - 1);

		IMatch prevFinal1 = this.matches.get(this.matches.size() / 2 - 1);
		IMatch prevFinal2 = this.matches.get(this.matches.size() - 1);

		// this.matches.add(new MatchFactory(this).addMatch(prevFinal1).addMatch(prevFinal2).isFinalMatch().build());
		this.matches.add(MatchFactory.build(this, prevFinal1, prevFinal2, true));
		
		for (int i = 0; i < matches.size(); i++) {
			logger.info(this.matches.get(i).toString());
		}

	}

	private void erstelleSeite(int anzahlMatchesZus, int start, int stop) throws Exception {

		int actMatch = start;
		for (int i = start; i < stop; i += 2) {
			if (start >= 1) {
				// matches.add(new MatchFactory(this).addMannschaft(teams.get(i + 1)).addMannschaft(teams.get(i + 2)).build());
				matches.add(MatchFactory.build(this, teams.get(i+1), teams.get(i+2)));
			} else {
				// matches.add(new MatchFactory(this).addMannschaft(teams.get(i)).addMannschaft(teams.get(i + 1)).build());
				matches.add(MatchFactory.build(this, teams.get(i), teams.get(i+1)));
			}
		}
		for (int i = 0; i < (anzahlMatchesZus / 2); i++) {

			IMatch pm1 = matches.get(actMatch);
			IMatch pm2 = matches.get(actMatch + 1);

			// beste Fabrik
			// matches.add(new MatchFactory(this).addMatch(pm1).addMatch(pm2).build());
			matches.add(MatchFactory.build(this, pm1, pm2));
			
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
		rangliste.show();
	}

	/**
	 * sets the TeamScreen with width=500 and height=630. The screen isn't
	 * maximized and its size is set to scene.
	 * 
	 * @param anzahlMannschaften
	 *            number of teams that were chosen in the last screen.
	 * @param screenName
	 *            the key of the screen which will be set.
	 */
	public void setTeamScreen(int anzahlMannschaften, String screenName) {
		main.getScenes().put(screenName, new Scene(new TeamScreen(this, anzahlMannschaften), 500, 630));
		main.getStage().setScene(main.getScene(screenName));
		main.getStage().setMaximized(false);
		main.getStage().sizeToScene();
		main.getStage().centerOnScreen();
	}

	/**
	 * sets the TreeScreen with width and height maximized.
	 * 
	 * @param screenName
	 *            the key of the screen which will be set.
	 * @param teams
	 *            an observable list of the teams.
	 * @throws Exception
	 */
	public void setTreeScreen(String screenName, ObservableList<String> teams) throws Exception {
		spielBaum = new TreeScreen(this,teams);
		main.getScenes().put(screenName, new Scene(spielBaum));
		main.getStage().setScene(main.getScene(screenName));
		main.getStage().setMaximized(true);
	}

	/**
	 * sets the SettingsScreen with width=500 and height=200. The stage's size
	 * is set to the scene-size and centered to the screen.
	 */
	public void setSettingsScreen() {
		main.getScenes().put("settings", new Scene(new SettingsScreen(this), 500, 200));
		main.getStage().setScene(main.getScene("settings"));
		main.getStage().setMaximized(false);
		main.getStage().sizeToScene();
		main.getStage().centerOnScreen();
	}

	/**
	 * sets the SplashScreen with Maximized width and height.
	 */
	public void setSplashScreen() {
		main.getScenes().put("splashscreen", new Scene(new SplashScreen(this)));
		main.getStage().setScene(main.getScene("splashscreen"));
		main.getStage().setMaximized(true);
	}
	
	public void setGroupScreen(String screenName, ObservableList<String> teams) {
		this.groupScreen = new GroupScreen(this,teams);
		main.getScenes().put(screenName, new Scene(this.groupScreen));
		main.getStage().setScene(main.getScene("groupscreen"));
		main.getStage().setMaximized(true);
	}

	public Main getMain() {
		return this.main;
	}

	public void updateSpielBaum() {
		this.spielBaum.updateSpielBaum();
	}
	
	public RangStage getRangStage() {
		return this.rangliste;
	}

	public void setTurnierType(String turnierType) {
		this.turnierType = turnierType;
	}
	
	public String getTurnierType() {
		return turnierType;
	}

}
