package backend.turnier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import GUI.TreeScreen.MatchPane;
import backend.exception.GameNotFinishedException;
import backend.exception.GameUnentschiedenException;
import backend.interfaces.IMatch;

public class Match implements IMatch {
	
	private static final Logger logger = (Logger) LogManager.getLogger("Match");
	
	private static int indexCounter = 1;
	
	private volatile String state="NYO";
	private volatile int toreM1 = 0;
	private volatile int toreM2 = 0;
	
	protected int index;
	protected Mannschaft mannschaft1 = new Mannschaft("...");;
	protected Mannschaft mannschaft2 = new Mannschaft("...");;
	private MatchPane matchPane;
	protected Mannschaft sieger;
	protected Mannschaft verlierer;
	
	private Steuerung steuerung;
	private Boolean unentschieden = false;
	private String turnierType = "Gruppenturnier";
	
	public Match(Steuerung steuerung) {
		this.steuerung = steuerung;
		this.index = indexCounter;
		this.setTurnierType(this.steuerung.getTurnierType());
		indexCounter++;
		this.matchPane = new MatchPane(this.steuerung, this);
	}

	public Match(Steuerung steuerung, Mannschaft m1, Mannschaft m2) {
		this.steuerung = steuerung;
		this.index = indexCounter;
		indexCounter++;
		this.mannschaft1 = m1;
		this.mannschaft2 = m2;
		this.matchPane = new MatchPane(this.steuerung, this);
	}

	public Match(Steuerung steuer, IMatch m1, IMatch m2) {
		this.index = indexCounter;
		indexCounter++;
		this.mannschaft1 = new Mannschaft("...");
		this.mannschaft2 = new Mannschaft("...");
		this.matchPane = new MatchPane(this.steuerung, this);
	}

	public Match(Mannschaft m1, Mannschaft m2) {
		this.mannschaft1 = m1;
		this.mannschaft2 = m2;
	}

	public void setMannschaft1(Mannschaft m1) {
		this.mannschaft1 = m1;
	}

	public Mannschaft getMannschaft1() {
		return mannschaft1;
	}

	public void setMannschaft2(Mannschaft m2) {
		this.mannschaft2 = m2;
	}

	public Mannschaft getMannschaft2() {
		return mannschaft2;
	}

	public int getIndex() {
		return this.index;
	}

	public String toString() {
		String ret = "Begegnung Nr. " + this.index + ": ";
		ret += this.getMannschaft1().getName();
		ret += " gegen " + this.getMannschaft2().getName();
		return ret;
	}

	public void setTurnierType(String tt) {
		this.turnierType = tt;
	}
	
	public void setSieger() throws GameUnentschiedenException {
		if (this.turnierType.equals("KO-Turnier")) {
			if(toreM1 == toreM2) {
				throw new GameUnentschiedenException();
			}
			else if (toreM1 > toreM2) {
				this.sieger = mannschaft1;
				this.verlierer = mannschaft2;
				this.sieger.aendereTordifferenz(toreM1 - toreM2);
				this.verlierer.aendereTordifferenz(toreM2 - toreM1);

			} else {
				this.sieger = mannschaft2;
				this.verlierer = mannschaft1;
				this.sieger.aendereTordifferenz(toreM2 - toreM1);
				this.verlierer.aendereTordifferenz(toreM1 - toreM2);
			}
			this.sieger.addPunkte();
			logger.info("Spiel " + this.index + ": " + this.mannschaft1 + " " + toreM1 + ":" + toreM2 + " " + this.mannschaft2);
		} else {
			if (toreM1 > toreM2) {
				this.sieger = mannschaft1;
				this.verlierer = mannschaft2;
				this.sieger.aendereTordifferenz(toreM1 - toreM2);
				this.verlierer.aendereTordifferenz(toreM2 - toreM1);
				this.sieger.addPunkte();
				logger.info("Spiel " + this.index + ": " + this.mannschaft1 + " " + toreM1 + ":" + toreM2 + " " + this.mannschaft2);
			} else if (toreM1 < toreM2) {
				this.sieger = mannschaft2;
				this.verlierer = mannschaft1;
				this.sieger.aendereTordifferenz(toreM2 - toreM1);
				this.verlierer.aendereTordifferenz(toreM1 - toreM2);
				this.sieger.addPunkte();
				logger.info("Spiel " + this.index + ": " + this.mannschaft1 + " " + toreM1 + ":" + toreM2 + " " + this.mannschaft2);			} else {
				this.unentschieden=true;
				this.mannschaft1.addPunkteUnentschieden();
				this.mannschaft2.addPunkteUnentschieden();
				logger.info("Spiel " + this.index + ": " + this.mannschaft1 + " " + toreM1 + ":" + toreM2 + " " + this.mannschaft2);			}

		}
	}

	public Mannschaft getSieger() throws GameNotFinishedException {
		if(this.sieger == null) {
			throw new GameNotFinishedException();
		}
		return this.sieger;
	}

	public Mannschaft getVerlierer() {
		return this.verlierer;
	}

	public void incrementToreM1() {
		this.toreM1++;
	}

	public void incrementToreM2() {
		this.toreM2++;
	}

	public int getToreM1() {
		return this.toreM1;
	}

	public int getToreM2() {
		return this.toreM2;
	}

	public void setToreM1(int toreM1) {
		this.toreM1 = toreM1;
	}

	public void setToreM2(int toreM2) {
		this.toreM2 = toreM2;
	}

	public MatchPane getMatchPane() {
		return this.matchPane;
	}

	public boolean isGameFinished() {
		if (this.sieger != null || this.unentschieden == true) {
			return true;
		} else {
			return false;
		}
	}

	public boolean getUnentschieden(){
		return this.unentschieden;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public String getState(){
		return this.state;
	}

}
