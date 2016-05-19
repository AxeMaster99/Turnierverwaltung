package stages;

import java.util.Optional;

import interfaces.IMatch;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import panes.MatchPane;
import screens.TreeScreen;
import threads.GUIUpdateThread;

public class MatchStage extends Stage {

	public enum Status {
		clickable, running, closed
	}

	private MatchPane matchPane;
	private IMatch match;
	Thread t1;

	private Pane root = new Pane();
	private GridPane grid = new GridPane();

	private Timeline timeline;

	//private final double SPIELMINUTEN = 2; TODO: Spielminuten enablen
	private int timerdauer = 5;

	private Boolean spielGestartet = false;

	private Label l_Spielstand = new Label();
	private Label l_Mannschaft1 = new Label();
	private Label l_Mannschaft2 = new Label();

	private Label l_timer = new Label("Verbleibende Zeit: ");
	private Label l_timerdauer = new Label(timerdauer / 60 + ":0" + timerdauer % 60);

	private Button b_TorMannschaft1 = new Button("Tor M1");
	private Button b_TorMannschaft2 = new Button("Tor M2");
	private Button b_Start_Stopp = new Button("Start");

	private TreeScreen spielBaum = null;

	public MatchStage(IMatch match, MatchPane matchPane) {
		super();
		this.match = match;
		this.matchPane = matchPane;

		this.setOnCloseRequest((WindowEvent) -> {

			if (spielGestartet == false) {
				this.close();
				this.matchPane.setDisable(false);
				if (matchPane.getCurrentStyle().equals("-fx-background-color: orange;")) {
					this.matchPane.statusFarbeAendern(Status.closed);
				} else {
					this.matchPane.statusFarbeAendern(Status.clickable);
				}

			} else {
				if (timeline.getStatus() == javafx.animation.Animation.Status.RUNNING) {
					this.stoppeSpiel();
				}
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Warnung");
				alert.setHeaderText("Wirklich schließen?");
				alert.setContentText(
						"Wollen sie das Fenster wirklich schließen? Der Timer wird dann gestoppt und das Spiel muss später fortgesetzt werden.");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {
					this.matchPane.setDisable(false);
					if (spielGestartet) {
						b_Start_Stopp.setText("Start");
						this.matchPane.statusFarbeAendern(Status.closed);
						b_TorMannschaft1.setDisable(true);
						b_TorMannschaft2.setDisable(true);
					}
					this.spielGestartet = false;
					this.close();

				} else {
					WindowEvent.consume();
					alert.close();
					this.starteSpiel();
				}
			}
		});

		grid.setMinWidth(350);
		grid.setMinHeight(200);
		grid.setAlignment(Pos.CENTER);

		l_Mannschaft1.setText(this.match.getMannschaft1().getName());
		l_Mannschaft2.setText(this.match.getMannschaft2().getName());

		b_TorMannschaft1.setDisable(true);
		b_TorMannschaft2.setDisable(true);

		Font font = new Font(25);

		l_timerdauer.setFont(font);

		l_Spielstand.setFont(font);
		l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());

		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.setHgap(10);
		grid.setVgap(10);

		grid.getColumnConstraints().addAll(col1, col2, col3);

		grid.add(l_Mannschaft1, 0, 0);
		GridPane.setHalignment(l_Mannschaft1, HPos.RIGHT);

		grid.add(l_Spielstand, 1, 0);
		GridPane.setHalignment(l_Spielstand, HPos.CENTER);

		grid.add(l_Mannschaft2, 2, 0);
		GridPane.setHalignment(l_Mannschaft2, HPos.LEFT);

		grid.add(b_TorMannschaft1, 0, 1);
		GridPane.setHalignment(b_TorMannschaft1, HPos.RIGHT);

		grid.add(b_Start_Stopp, 1, 1);
		GridPane.setHalignment(b_Start_Stopp, HPos.CENTER);

		grid.add(b_TorMannschaft2, 2, 1);
		GridPane.setHalignment(b_TorMannschaft2, HPos.LEFT);

		grid.add(l_timer, 0, 2);
		GridPane.setHalignment(l_timer, HPos.LEFT);

		grid.add(l_timerdauer, 1, 2);
		GridPane.setHalignment(l_timerdauer, HPos.CENTER);

		// grid.setGridLinesVisible(true);
		root.getChildren().add(grid);

		Scene scene = new Scene(root, 350, 200);

		this.setTitle(this.match.getMannschaft1().getName() + " vs " + this.match.getMannschaft2().getName());
		this.setScene(scene);
		this.show();

		b_TorMannschaft1.setOnAction((event) -> {
			this.match.incrementToreM1();
			l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());
		});

		b_TorMannschaft2.setOnAction((event) -> {
			this.match.incrementToreM2();
			l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());
		});

		b_Start_Stopp.setOnAction((event) -> {
			if (b_Start_Stopp.getText().equals("Stop")) {

				this.stoppeSpiel();
				b_Start_Stopp.setText("Start");
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);

			} else {
				this.spielGestartet = true;
				this.starteSpiel();
				this.matchPane.statusFarbeAendern(Status.running);
				b_Start_Stopp.setText("Stop");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
			}
		});

	}

	private void stoppeSpiel() {
		timeline.stop();
	}

	private void starteSpiel() {
		this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			timerdauer--;
			if (timerdauer % 60 >= 10) {
				l_timerdauer.setText(timerdauer / 60 + ":" + timerdauer % 60);
			} else {
				l_timerdauer.setText(timerdauer / 60 + ":0" + timerdauer % 60);
			}
		}));

		timeline.setOnFinished((e) -> {
			if (this.match.getToreM1() == this.match.getToreM2()) {
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information");
						alert.setHeaderText("Unentschieden");
						alert.setContentText("Das Spiel endete Unentschieden. Das nächste Tor entscheidet.");
						alert.showAndWait();
					}
				});
				new GUIUpdateThread(match, this).start();
			} else {
				new GUIUpdateThread(match, this).start();
			}
		});

		timeline.setCycleCount(timerdauer);
		timeline.play();
	}

	public void beendeSpiel() {
		this.close();
		this.match.setSieger();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Information");
				alert.setHeaderText("Spiel Beendet");
				alert.setContentText("Das Spiel wurde Beendet. Gewonnen hat: " + match.getSieger());
				alert.showAndWait();
			}
		});
		this.match.getMatchPane().setDisable(false);
		this.spielBaum.updateSpielBaum();
	}

	public void setSpielbaum(TreeScreen spielbaum) {
		this.spielBaum = spielbaum;
	}

	public void setLabelM1(String label) {
		l_Mannschaft1.setText(label);
		if (!l_Mannschaft2.getText().equals("...")) {
			this.setTitle(l_Mannschaft1.getText() + " vs " + l_Mannschaft2.getText());
		}
	}

	public void setLabelM2(String label) {
		l_Mannschaft2.setText(label);
		if (!l_Mannschaft1.getText().equals("...")) {
			this.setTitle(l_Mannschaft1.getText() + " vs " + l_Mannschaft2.getText());
		}
	}

}
