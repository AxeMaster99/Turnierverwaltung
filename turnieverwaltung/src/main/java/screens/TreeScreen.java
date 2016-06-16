package screens;

import java.util.ArrayList;
import java.util.Spliterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.sun.javafx.stage.StageHelper;

import stages.GroupMatchStage;
import stages.MatchStage;
import stages.MatchStage.Event;
import stages.TreeMatchStage;
import backend.FinalMatch;
import backend.FolgeMatch;
import interfaces.IMatch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import panes.MatchPane;
import verwaltung.Steuerung;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class TreeScreen extends Pane {

	private Canvas canvas = new Canvas(1400, 700);
	private GraphicsContext gc = canvas.getGraphicsContext2D();
	private int anzahlTeams;
	private Steuerung steuerung;
	private int cnt = 0;

	private ArrayList<IMatch> matches;

	private static final Logger logger = (Logger) LogManager.getLogger("TreeScreen");

	public TreeScreen(Steuerung steuerung, ArrayList<IMatch> matches, int anzahlTeams) throws Exception {

		this.steuerung = steuerung;
		this.anzahlTeams = anzahlTeams;
		this.matches = matches;

		// Picture in background
		ImageView bgImageView = this.setImage();
		this.getChildren().add(bgImageView);

		this.setStyle("-fx-background-color: black;");
		canvas.setMouseTransparent(true);
		this.getChildren().add(canvas);

		this.zeichneSpielBaumLinks();
		this.zeichneSpielBaumRechts();
		this.zeichneLinien();
		this.zeichneFinale();
		this.zeichneLegende(anzahlTeams);

		// Menü
		MenuBar menubar = this.setMenuBar();
		this.getChildren().add(menubar);

	}

	private MenuBar setMenuBar() {

		MenuBar menubar = new MenuBar();
		Menu toolMenu = new Menu("Tools");
		MenuItem ranglisteMenuItem = new MenuItem("Rangliste");
		toolMenu.getItems().add(ranglisteMenuItem);

		ranglisteMenuItem.setOnAction((WindowEvent) -> {
			logger.info("Zeige Rangliste an");
			this.steuerung.erstelleRangliste();
		});

		menubar.getMenus().addAll(toolMenu);
		menubar.prefWidthProperty().bind(this.widthProperty());
		return menubar;
	}

	private ImageView setImage() {

		Image bgImage = new Image("images/boden_wiese.jpg");
		ImageView bgImageView = new ImageView();
		bgImageView.setImage(bgImage);
		bgImageView.setOpacity(0.7);
		bgImageView.fitHeightProperty().bind(this.steuerung.getMain().getStage().heightProperty());
		bgImageView.fitWidthProperty().bind(this.steuerung.getMain().getStage().widthProperty());

		bgImageView.setOnMouseReleased((event) -> {
			@SuppressWarnings("restriction")
			ObservableList<Stage> stages = StageHelper.getStages();
			Stage[] stagesArray = new Stage[stages.size()];
			for (int i = 0; i < stages.size(); i++) {
				stagesArray[i] = stages.get(i);
			}
			for (int i = 0; i < stagesArray.length; i++) {
				if (stagesArray[i] instanceof TreeMatchStage) {
					TreeMatchStage treeMatchStage = (TreeMatchStage) stagesArray[i];
					treeMatchStage.switchState(Event.hide);
				}
			}
		});
		return bgImageView;
	}

	private void zeichneLegende(int unterstesPane) {
		MatchPane mp = this.matches.get(anzahlTeams / 4 - 1).getMatchPane();
		double mpY = mp.getTranslateY() + 70;

		Font font = Font.font("Arial", FontWeight.BOLD, 16);

		Label l0_legende = new Label("Legende:");
		l0_legende.setFont(font);
		l0_legende.setTextFill(Color.WHITE);
		l0_legende.setTranslateX(10);
		l0_legende.setTranslateY(mpY + 7);

		Label l1_orange = new Label("Spiel Unterbrochen");
		l1_orange.setFont(font);
		l1_orange.setTextFill(Color.WHITE);
		l1_orange.setTranslateX(140);
		l1_orange.setTranslateY(mpY + 7);

		Label l2_green = new Label("Spiel Beendet");
		l2_green.setFont(font);
		l2_green.setTextFill(Color.WHITE);
		l2_green.setTranslateX(340);
		l2_green.setTranslateY(mpY + 7);

		Label l3_yellow = new Label("Spiel Laufend");
		l3_yellow.setFont(font);
		l3_yellow.setTextFill(Color.WHITE);
		l3_yellow.setTranslateX(540);
		l3_yellow.setTranslateY(mpY + 7);

		Label l4_white = new Label("Spiel noch nicht gestartet");
		l4_white.setFont(font);
		l4_white.setTextFill(Color.WHITE);
		l4_white.setTranslateX(740);
		l4_white.setTranslateY(mpY + 7);

		Rectangle rectangleOrange = new Rectangle(30, 30, Color.ORANGE);
		rectangleOrange.setStroke(Color.BLACK);
		rectangleOrange.setArcWidth(10);
		rectangleOrange.setArcHeight(10);
		rectangleOrange.relocate(100, mpY);
		this.getChildren().add(rectangleOrange);

		Rectangle rectangleGreen = new Rectangle(30, 30, Color.rgb(127, 255, 0));
		rectangleGreen.setStroke(Color.BLACK);
		rectangleGreen.setArcWidth(10);
		rectangleGreen.setArcHeight(10);
		rectangleGreen.relocate(300, mpY);
		this.getChildren().add(rectangleGreen);

		Rectangle rectangleYellow = new Rectangle(30, 30, Color.YELLOW);
		rectangleYellow.setStroke(Color.BLACK);
		rectangleYellow.setArcWidth(10);
		rectangleYellow.setArcHeight(10);
		rectangleYellow.relocate(500, mpY);
		this.getChildren().add(rectangleYellow);

		Rectangle rectangleWhite = new Rectangle(30, 30, Color.WHITE);
		rectangleWhite.setStroke(Color.BLACK);
		rectangleWhite.setArcWidth(10);
		rectangleWhite.setArcHeight(10);
		rectangleWhite.relocate(700, mpY);

		Label l5_hide = new Label(
				"Geöffnete Match-Fenster lassen sich per Klick auf einen beliebigen Punkt des Bildes schließen.");
		l5_hide.setFont(font);
		l5_hide.setTextFill(Color.WHITE);
		l5_hide.setTranslateX(10);
		l5_hide.setTranslateY(mpY + 35);
		this.getChildren().addAll(rectangleWhite, l1_orange, l2_green, l3_yellow, l4_white, l0_legende, l5_hide);
	}

	private void zeichneLinien() {
		gc.setFill(Color.GOLD);
		gc.setStroke(Color.GOLD);
		gc.setLineWidth(3);

		int anzahlMatchesZus = steuerung.getAnzahlMatchesZus();

		matches
			.stream()
			.filter(match -> (match instanceof FolgeMatch))
			.filter(match -> !(match instanceof FinalMatch))
			.limit(anzahlMatchesZus)
			.forEach(match -> {
				
					MatchPane fm = match.getMatchPane();
					MatchPane m1 = ((FolgeMatch) match).getPrevMatch1().getMatchPane();
					MatchPane m2 = ((FolgeMatch) match).getPrevMatch2().getMatchPane();

					double fmX;
					double fmY;
					double m1X;
					double m1Y;
					double m2X;
					double m2Y;

					if (cnt < anzahlMatchesZus / 2) {
						fmX = fm.getTranslateX();
						fmY = fm.getTranslateY() + fm.getMinHeight() / 2;
						m1X = m1.getTranslateX() + 110;
						m1Y = m1.getTranslateY() + fm.getMinHeight() / 2;
						m2X = m2.getTranslateX() + 110;
						m2Y = m2.getTranslateY() + fm.getMinHeight() / 2;
					} else {
						fmX = fm.getTranslateX() + 110;
						fmY = fm.getTranslateY() + fm.getMinHeight() / 2;
						m1X = m1.getTranslateX();
						m1Y = m1.getTranslateY() + fm.getMinHeight() / 2;
						m2X = m2.getTranslateX();
						m2Y = m2.getTranslateY() + fm.getMinHeight() / 2;
					}
					gc.strokeLine(fmX, fmY, m1X, m1Y);
					gc.strokeLine(fmX, fmY, m2X, m2Y);
					cnt++;
				});
		cnt=0;

		/*
		 * for (int i = 0; i < this.matches.size() / 2; i++) { if
		 * (this.matches.get(i) instanceof FolgeMatch && !(this.matches.get(i)
		 * instanceof FinalMatch)) {
		 * 
		 * MatchPane fm = this.matches.get(i).getMatchPane(); MatchPane m1 =
		 * ((FolgeMatch) this.matches.get(i)).getPrevMatch1().getMatchPane();
		 * MatchPane m2 = ((FolgeMatch)
		 * this.matches.get(i)).getPrevMatch2().getMatchPane();
		 * 
		 * double fmX = fm.getTranslateX(); double fmY = fm.getTranslateY() +
		 * fm.getMinHeight() / 2; double m1X = m1.getTranslateX() + 110; double
		 * m1Y = m1.getTranslateY() + fm.getMinHeight() / 2; double m2X =
		 * m2.getTranslateX() + 110; double m2Y = m2.getTranslateY() +
		 * fm.getMinHeight() / 2;
		 * 
		 * gc.setFill(Color.GOLD); gc.setStroke(Color.GOLD); gc.setLineWidth(3);
		 * gc.strokeLine(fmX, fmY, m1X, m1Y); gc.strokeLine(fmX, fmY, m2X, m2Y);
		 */
	}

	/*
	 * private void zeichneLinienRechts() { gc.setFill(Color.GOLD);
	 * gc.setStroke(Color.GOLD); gc.setLineWidth(3); matches .stream()
	 * .skip(this.matches.size()/2) .filter(match -> (match instanceof
	 * FolgeMatch)) .filter(match -> !(match instanceof FinalMatch))
	 * .forEach(match -> { MatchPane fm = match.getMatchPane(); MatchPane m1 =
	 * ((FolgeMatch) match).getPrevMatch1().getMatchPane(); MatchPane m2 =
	 * ((FolgeMatch) match).getPrevMatch2().getMatchPane();
	 * 
	 * double fmX = fm.getTranslateX() + 110; double fmY = fm.getTranslateY() +
	 * fm.getMinHeight() / 2;; double m1X = m1.getTranslateX(); double m1Y =
	 * m1.getTranslateY() + fm.getMinHeight() / 2;; double m2X =
	 * m2.getTranslateX(); double m2Y = m2.getTranslateY() + fm.getMinHeight() /
	 * 2;;
	 * 
	 * gc.strokeLine(fmX, fmY, m1X, m1Y); gc.strokeLine(fmX, fmY, m2X, m2Y); });
	 */

	/*
	 * for (int i = this.matches.size() / 2; i < this.matches.size(); i++) { if
	 * (this.matches.get(i) instanceof FolgeMatch && !(this.matches.get(i)
	 * instanceof FinalMatch)) {
	 * 
	 * MatchPane fm = this.matches.get(i).getMatchPane(); MatchPane m1 =
	 * ((FolgeMatch) this.matches.get(i)).getPrevMatch1().getMatchPane();
	 * MatchPane m2 = ((FolgeMatch)
	 * this.matches.get(i)).getPrevMatch2().getMatchPane();
	 * 
	 * double fmX = fm.getTranslateX() + 110; double fmY = fm.getTranslateY() +
	 * fm.getMinHeight() / 2;; double m1X = m1.getTranslateX(); double m1Y =
	 * m1.getTranslateY() + fm.getMinHeight() / 2;; double m2X =
	 * m2.getTranslateX(); double m2Y = m2.getTranslateY() + fm.getMinHeight() /
	 * 2;;
	 * 
	 * // logger.info(fmBoundsX + " | "+fmBoundsY);
	 * 
	 * gc.setFill(Color.GOLD); gc.setStroke(Color.GOLD); gc.setLineWidth(3);
	 * gc.strokeLine(fmX, fmY, m1X, m1Y); gc.strokeLine(fmX, fmY, m2X, m2Y); } }
	 * }
	 */

	private void zeichneSpielBaumLinks() {
		int offset = 0;
		int posX = 10;
		int posY = 40;
		int matchesInSpalte = anzahlTeams / 4;
		int actMatch = 0;
		int sprungY = 70;
		int sprungX = 140;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				MatchPane pane = this.matches.get(actMatch).getMatchPane();
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprungY;
				actMatch++;
			}
			matchesInSpalte = matchesInSpalte / 2;
			offset += sprungY / 2;
			sprungY = sprungY * 2;
			posY = 40 + offset;
			posX += sprungX;
		}
	}

	private void zeichneSpielBaumRechts() {
		int offset = 0;
		int posX = (this.steuerung.getAnzahlSpalten() * 130) - 20;
		int posY = 40;
		int matchesInSpalte = anzahlTeams / 4;
		int actMatch = this.matches.size() / 2;
		int sprungY = 70;
		int sprungX = 140;

		while (matchesInSpalte > 0) {
			for (int i = 0; i < matchesInSpalte; i++) {
				MatchPane pane = this.matches.get(actMatch).getMatchPane();
				pane.setTranslateX(posX);
				pane.setTranslateY(posY);
				this.getChildren().add(pane);
				posY += sprungY;
				actMatch++;
			}
			matchesInSpalte = matchesInSpalte / 2;
			offset += sprungY / 2;
			sprungY = sprungY * 2;
			posY = 40 + offset;
			posX -= sprungX;
		}
	}

	private void zeichneFinale() {

		MatchPane finaleMatchPane = this.matches.get(this.matches.size() - 1).getMatchPane();

		this.getChildren().add(finaleMatchPane);

		// ermittle Position finalMatchPane
		double x1 = this.matches.get(this.matches.size() / 2 - 1).getMatchPane().getTranslateX() + 110;
		double x2 = this.matches.get(this.matches.size() - 2).getMatchPane().getTranslateX();
		double mitte = x1 + (x2 - x1) / 2;

		// finalMatchPane positionieren
		finaleMatchPane.setTranslateX(mitte - 55);
		finaleMatchPane.setTranslateY(this.matches.get(this.matches.size() / 2 - 1).getMatchPane().getTranslateY());

		// Striche zu finalMatchPane zeichnen
		double fmX = finaleMatchPane.getTranslateX();
		double fmY = finaleMatchPane.getTranslateY() + finaleMatchPane.getMinHeight() / 2;

		double m1X = this.matches.get(this.matches.size() / 2 - 1).getMatchPane().getTranslateX() + 110;
		double m1Y = this.matches.get(this.matches.size() / 2 - 1).getMatchPane().getTranslateY()
				+ finaleMatchPane.getMinHeight() / 2;
		double m2X = this.matches.get(this.matches.size() - 2).getMatchPane().getTranslateX();
		double m2Y = this.matches.get(this.matches.size() - 2).getMatchPane().getTranslateY()
				+ finaleMatchPane.getMinHeight() / 2;

		gc.setFill(Color.GOLD);
		gc.setStroke(Color.GOLD);
		gc.setLineWidth(3);
		gc.strokeLine(fmX, fmY, m1X, m1Y);
		gc.strokeLine(fmX, fmY, m2X, m2Y);
	}

	public void updateSpielBaum() {

		logger.info("update spielbaum");

		for (int i = 0; i < this.matches.size(); i++) {
			if (this.matches.get(i) instanceof FolgeMatch) {

				FolgeMatch actMatch = (FolgeMatch) this.matches.get(i);

				if (((FolgeMatch) this.matches.get(i)).getPrevMatch1().isGameFinished()) {
					IMatch prevMatch1 = actMatch.getPrevMatch1();
					this.matches.get(i).setMannschaft1(prevMatch1.getSieger());
					this.matches.get(i).getMatchPane().updateMannschaftsLabelM1();

				}

				if (((FolgeMatch) this.matches.get(i)).getPrevMatch2().isGameFinished()) {
					IMatch prevMatch2 = actMatch.getPrevMatch2();
					this.matches.get(i).setMannschaft2(prevMatch2.getSieger());
					this.matches.get(i).getMatchPane().updateMannschaftsLabelM2();
				}
			}

			if (this.matches.get(i) instanceof FinalMatch && this.matches.get(i).isGameFinished()) {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Sieger des Turniers");
				alert.setContentText("Der Sieger des Turniers ist: " + this.matches.get(i).getSieger());
				alert.showAndWait();
			}
		}
	}
}
