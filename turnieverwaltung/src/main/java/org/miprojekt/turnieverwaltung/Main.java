package org.miprojekt.turnieverwaltung;

import java.util.HashMap;

import org.miprojekt.turnieverwaltung.gui.SceneParent;
import org.miprojekt.turnieverwaltung.gui.windows.Mannschaftsnamen;
import org.miprojekt.turnieverwaltung.gui.windows.Settings;
import org.miprojekt.turnieverwaltung.gui.windows.Splashscreen;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private final int WINDOWWIDTH = 800;
	private final int WINDOWHEIGHT = 600;
	private Stage stage;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	
	public static void main(String args[]) {
		new Steuerung();
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;
		
		stage.setTitle("Turnierverwaltung");
				
		this.scenes.put("splashscreen", new Scene(new Splashscreen(this), WINDOWWIDTH, WINDOWHEIGHT));
		this.scenes.put("settings", new Scene(new Settings(this), WINDOWWIDTH, WINDOWHEIGHT));
		stage.setScene(this.scenes.get("splashscreen"));
		stage.show();
	}
	public Stage getStage() {
		return this.stage;
	}
	
	public Scene getScreen(String key) {
		return this.scenes.get(key);
	}

	public void setMannschaftsnamenScreen(int anzahlMannschaften, String screenName) {
		this.scenes.put(screenName, new Scene(new Mannschaftsnamen(this,anzahlMannschaften),WINDOWWIDTH,WINDOWHEIGHT));
		this.getStage().setScene(this.getScreen(screenName));
	}

	public void erstelleMannschaften(ObservableList<String> teams) {
		//TODO: implement!
	}
	
}
