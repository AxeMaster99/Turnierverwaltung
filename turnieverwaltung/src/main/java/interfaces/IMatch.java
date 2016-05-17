package interfaces;

import org.miprojekt.turnieverwaltung.gui.MatchPane;

import backend.Mannschaft;
import backend.Match;

public interface IMatch {

	public Mannschaft getMannschaft1();

	public Mannschaft getMannschaft2();

	public MatchPane getMatchPane();

	public void incrementToreM1();

	public void incrementToreM2();

	public int getToreM1();

	public int getToreM2();

	public int getIndex();
	
	public void setSieger();
	
	public Mannschaft getSieger();
	
	public void setToreM1(int toreM1);

	public void setToreM2(int toreM2);
	
	public boolean isGameFinished();
	
	public void setMannschaft1(Mannschaft m1);
	
	public void setMannschaft2(Mannschaft m2);

}
