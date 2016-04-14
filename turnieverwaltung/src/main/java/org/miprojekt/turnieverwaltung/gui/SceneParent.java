package org.miprojekt.turnieverwaltung.gui;

import org.miprojekt.turnieverwaltung.Main;

import javafx.scene.layout.StackPane;

public class SceneParent extends StackPane {

	protected Main main;
	
	public SceneParent(Main main) {
		this.main = main;
	}
	
}
