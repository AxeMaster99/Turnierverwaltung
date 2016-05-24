package panes;

import javafx.scene.layout.Pane;
import verwaltung.Main;
import verwaltung.Steuerung;

public class SceneParent extends Pane {

	protected Steuerung steuerung;

	
	public SceneParent(Steuerung steuerung) {
		this.steuerung = steuerung;
	}
	
	public Steuerung getSteuerung(){
		return steuerung;
		
	}

}
