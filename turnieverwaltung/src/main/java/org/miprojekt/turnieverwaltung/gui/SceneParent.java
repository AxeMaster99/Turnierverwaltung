package org.miprojekt.turnieverwaltung.gui;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.Steuerung;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SceneParent extends Pane {

	protected Main main;
	protected Steuerung steuerung = new Steuerung();

	public SceneParent(Main main) {
		this.main = main;
	}

}
