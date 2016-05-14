package interfaces;

import org.miprojekt.turnieverwaltung.gui.MatchPane;

import backend.Mannschaft;

public interface IMatch {

	public Mannschaft getMannschaft1();

	public Mannschaft getMannschaft2();

	public MatchPane getMatchPane();

	public void incrementToreM1();

	public void incrementToreM2();

	public int getToreM1();

	public int getToreM2();

	public int getIndex();
	

}
