package stages;


import interfaces.IMatch;
import interfaces.IMatchStage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import panes.MatchPane;
import threads.GUIUpdateThread;
//import screens.SettingsScreen;

public abstract class MatchStage extends Stage implements IMatchStage {

	public enum Event {
		start_stop, hide, close, click, timer_finished, hover
	}

	protected MatchPane matchPane;
	protected IMatch match;
	protected Thread t1;

	protected Pane root = new Pane();
	protected GridPane grid = new GridPane();

	protected Timeline timeline;
	protected int matchTimer = -1; // Every Game gets his individual timer,
									// initial value -1 to check if timer has
									// had runtime before on resuming a paused
									// Match
	// private final double SPIELMINUTEN = 2; TODO: Spielminuten enablen
	protected static int timerdauer = 5; // bisher aus Testgründen noch sekunden,
										// kann bei "Release" auf Minuten
										// gesetzt werden

	protected Label l_Spielstand = new Label();
	protected Label l_Mannschaft1 = new Label();
	protected Label l_Mannschaft2 = new Label();

	protected Label l_timer = new Label("Verbleibende Zeit: ");
	protected Label l_timerdauer = new Label(String.format("%02d:%02d", timerdauer / 60, timerdauer % 60));

	protected Button b_TorMannschaft1 = new Button("Tor M1");
	protected Button b_TorMannschaft2 = new Button("Tor M2");
	protected Button b_Start_Stopp = new Button("Start");
	protected Button b_hide = new Button("Hide");

	public MatchStage(IMatch match) {
		super();
		this.match = match;

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

		grid.add(b_hide, 2, 2);
		GridPane.setHalignment(b_hide, HPos.LEFT);

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
	}

	public MatchStage(IMatch match, MatchPane matchPane) {
		this(match);
		this.matchPane = matchPane;
	}

	protected void stoppeSpiel() {
		timeline.stop();
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

	public static void setTimerdauer(int duration) {
		timerdauer = duration;
	}

}
