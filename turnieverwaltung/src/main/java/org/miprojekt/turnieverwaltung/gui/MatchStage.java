package org.miprojekt.turnieverwaltung.gui;

import org.miprojekt.turnieverwaltung.Steuerung;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MatchStage extends Stage {

	private Label l_Spielstand = new Label();
	private Label l_Mannschaft1 = new Label();
	private Label l_Mannschaft2 = new Label();
	private Button b_TorMannschaft1 = new Button("Tor M1");
	private Button b_TorMannschaft2 = new Button("Tor M2");
	private Button b_Start_Stopp = new Button("Start");

	private int ToreM1 = 0;
	private int ToreM2 = 0;

	public MatchStage(String nameMannschaft1, String nameMannschaft2) {
		super();

		Pane root = new Pane();
		GridPane grid = new GridPane();
		grid.setMinWidth(300);
		grid.setMinHeight(200);
		grid.setAlignment(Pos.CENTER);

		l_Mannschaft1.setText(nameMannschaft1);
		l_Mannschaft2.setText(nameMannschaft2);

		b_TorMannschaft1.setDisable(true);
		b_TorMannschaft2.setDisable(true);

		Font font = new Font(25);

		l_Spielstand.setFont(font);
		l_Spielstand.setText(ToreM1 + ":" + ToreM2);

		grid.add(l_Mannschaft1, 0, 0);
		grid.add(l_Spielstand, 1, 0);
		grid.add(l_Mannschaft2, 2, 0);
		grid.add(b_TorMannschaft1, 0, 1);
		grid.add(b_Start_Stopp, 1, 1);
		grid.add(b_TorMannschaft2, 2, 1);

		GridPane.setMargin(l_Spielstand, new Insets(0, 20, 0, 20));

		root.getChildren().add(grid);

		Scene scene = new Scene(root, 300, 200);

		this.setTitle(nameMannschaft1 + " vs " + nameMannschaft2);
		this.setScene(scene);
		this.show();

		b_TorMannschaft1.setOnAction((event) -> {
			ToreM1++;
			l_Spielstand.setText(ToreM1 + ":" + ToreM2);
		});

		b_TorMannschaft2.setOnAction((event) -> {
			ToreM2++;
			l_Spielstand.setText(ToreM1 + ":" + ToreM2);
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
