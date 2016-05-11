package org.miprojekt.turnieverwaltung.gui;

import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.Steuerung;

import javafx.scene.layout.StackPane;

public class SceneParent extends StackPane {

	protected Main main;
	protected Steuerung steuerung = new Steuerung();

	public SceneParent(Main main) {
		this.main = main;
	}

}
