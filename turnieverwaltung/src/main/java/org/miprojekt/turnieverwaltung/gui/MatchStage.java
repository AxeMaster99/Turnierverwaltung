package org.miprojekt.turnieverwaltung.gui;


import interfaces.IMatch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MatchStage extends Stage {

	private IMatch match;
	private Label l_Spielstand = new Label();
	private Label l_Mannschaft1 = new Label();
	private Label l_Mannschaft2 = new Label();
	private Button b_TorMannschaft1 = new Button("Tor M1");
	private Button b_TorMannschaft2 = new Button("Tor M2");
	private Button b_Start_Stopp = new Button("Start");

	public MatchStage(IMatch match) {
		super();
		this.match = match;

		Pane root = new Pane();
		GridPane grid = new GridPane();
		grid.setMinWidth(300);
		grid.setMinHeight(200);
		grid.setAlignment(Pos.CENTER);

		l_Mannschaft1.setText(this.match.getMannschaft1().getName());
		l_Mannschaft2.setText(this.match.getMannschaft2().getName());

		b_TorMannschaft1.setDisable(true);
		b_TorMannschaft2.setDisable(true);

		Font font = new Font(25);

		l_Spielstand.setFont(font);
		l_Spielstand.setText(this.match.getToreM1() + ":" + this.match.getToreM2());

		
		ColumnConstraints col1 = new ColumnConstraints(100);
		ColumnConstraints col2 = new ColumnConstraints(100);
		ColumnConstraints col3 = new ColumnConstraints(100);
		grid.setHgap(10);
		grid.setVgap(10);
		
		grid.getColumnConstraints().addAll(col1,col2,col3);
		
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
		
		root.getChildren().add(grid);

		Scene scene = new Scene(root, 300, 200);

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
				
				
				
				b_Start_Stopp.setText("Start");
				b_TorMannschaft1.setDisable(true);
				b_TorMannschaft2.setDisable(true);

			} else {
				b_Start_Stopp.setText("Stop");
				b_TorMannschaft1.setDisable(false);
				b_TorMannschaft2.setDisable(false);
			}
		});

	}

}
