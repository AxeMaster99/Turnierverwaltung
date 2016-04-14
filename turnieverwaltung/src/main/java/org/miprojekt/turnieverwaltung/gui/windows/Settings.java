package org.miprojekt.turnieverwaltung.gui.windows;
import org.miprojekt.turnieverwaltung.Main;
import org.miprojekt.turnieverwaltung.gui.SceneParent;

import javafx.scene.control.Button;

public class Settings extends SceneParent {

	public Settings(Main main) {
		super(main);
		
		Button b = new Button("Hallo");
		this.getChildren().add(b);
		
	}
	
}
