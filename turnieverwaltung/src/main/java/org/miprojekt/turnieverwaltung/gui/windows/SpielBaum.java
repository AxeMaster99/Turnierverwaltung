package org.miprojekt.turnieverwaltung.gui.windows;

import java.util.ArrayList;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import backend.Match;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SpielBaum extends SceneParent {

	private ArrayList<Match> matches;

	public SpielBaum(Main main, ArrayList<Match> matches) {
		super(main);
		this.matches = matches;

		System.out.println(this.matches.size());

		for (int i = 0; i < this.matches.size(); i++) {
			System.out.println("Begegnung Nr. " + (i + 1));
			System.out.println("m1:" + this.matches.get(i).getMannschaft1().getName());
			System.out.println("m2:" + this.matches.get(i).getMannschaft2().getName());
			System.out.println();
		}

		for (int i = 0; i < this.matches.size(); i++) {
			Pane p = new Pane();
			// p.setStyle("-fx-background-color: white");
			p.setTranslateX(10);
			p.setTranslateY(i * 50);
			GridPane grid = new GridPane();
			Label m1label = new Label(this.matches.get(i).getMannschaft1().getName());
			Label m2label = new Label(this.matches.get(i).getMannschaft2().getName());
			grid.add(m1label, 0, 0);
			grid.add(m2label, 0, 1);
			p.getChildren().add(grid);
			this.getChildren().add(p);
		}

	}
}
