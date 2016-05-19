package panes;

import javafx.scene.layout.Pane;
import verwaltung.Main;
import verwaltung.Steuerung;

public class SceneParent extends Pane {

	protected Main main;
	protected Steuerung steuerung = new Steuerung();

	public SceneParent(Main main) {
		this.main = main;
	}

}
