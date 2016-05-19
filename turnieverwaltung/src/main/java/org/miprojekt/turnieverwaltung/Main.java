package org.miprojekt.turnieverwaltung;

import java.util.HashMap;

import org.miprojekt.turnieverwaltung.gui.windows.Mannschaftsnamen;
import org.miprojekt.turnieverwaltung.gui.windows.Settings;
import org.miprojekt.turnieverwaltung.gui.windows.SpielBaum;
import org.miprojekt.turnieverwaltung.gui.windows.Splashscreen;

import com.sun.glass.ui.Window;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	private final int WINDOWWIDTH = 1400;
	private final int WINDOWHEIGHT = 650;
	private Stage stage;
	private HashMap<String, Scene> scenes = new HashMap<String, Scene>();
	
	public static void main(String args[]) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		this.stage = stage;	
		stage.setTitle("Turnierverwaltung");
				
		this.scenes.put("splashscreen", new Scene(new Splashscreen(this), WINDOWWIDTH, WINDOWHEIGHT));
		this.scenes.put("settings", new Scene(new Settings(this), 500, 200));
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
		this.scenes.put(screenName, new Scene(new Mannschaftsnamen(this,anzahlMannschaften),500,630));
		this.getStage().setScene(this.getScreen(screenName));
	}
	
	public void setSpielBaumScreen(String screenName, ObservableList<String> teams) throws Exception {
		this.scenes.put(screenName, new Scene(new SpielBaum(this,teams)));
		this.getStage().setScene(this.getScreen(screenName));
	}
	
}
