package org.miprojekt.turnieverwaltung.gui.windows;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class SpielBaum_Screen extends SceneParent {

	public SpielBaum_Screen(Main main) {
		super(main);

		System.out.println(steuerung.getMatches().size());
		
		for(int i = 0; i < steuerung.getMatches().size(); i++) {
			System.out.println("Begegnung Nr. "+(i+1));
			System.out.println("m1:" + steuerung.getMatches().get(i).getMannschaft1().getName());
			System.out.println("m1:" + steuerung.getMatches().get(i).getMannschaft2().getName());
			System.out.println();
		}
		
		for (int i = 0; i < steuerung.getMatches().size(); i++) {
			Pane p = new Pane();
			// p.setStyle("-fx-background-color: white");
			p.setTranslateX(10);
			p.setTranslateY(i*50);
			GridPane grid = new GridPane();
			Label m1label = new Label(steuerung.getMatches().get(i).getMannschaft1().getName());
			Label m2label = new Label(steuerung.getMatches().get(i).getMannschaft2().getName());
			grid.add(m1label, 0, 0);
			grid.add(m2label, 0, 1);
			p.getChildren().add(grid);
			this.getChildren().add(p);
		}
		
	}

}
