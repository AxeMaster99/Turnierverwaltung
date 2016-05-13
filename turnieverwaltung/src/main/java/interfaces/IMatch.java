package interfaces;

import org.miprojekt.turnieverwaltung.gui.MatchPane;

import backend.Mannschaft;

public interface IMatch {

	Mannschaft getMannschaft1();
	Mannschaft getMannschaft2();

	MatchPane getMatchPane();
	
}
