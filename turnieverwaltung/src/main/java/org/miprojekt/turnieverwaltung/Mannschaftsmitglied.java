package org.miprojekt.turnieverwaltung;

import java.util.Date;

abstract public class Mannschaftsmitglied {

	protected String vorname;
	protected String nachname;
	protected int nr;
	protected Date geburtsdatum;

	public Mannschaftsmitglied(String vorname) {
		this.vorname = vorname;
	}
	
	public String getVorname() {
		return this.vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getNachname() {
		return nachname;
	}

	public void setNachname(String nachname) {
		this.nachname = nachname;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public Date getGeburtsdatum() {
		return geburtsdatum;
	}

	public void setGeburtsdatum(Date geburtsdatum) {
		this.geburtsdatum = geburtsdatum;
	}

}
