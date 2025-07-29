package de.organizer.util;

public enum Priority {
	
	//Definition der Prioritäten und dazugehörige Bezeichnungen
	LOW (1, "Niedrig"),
	MEDIUM (2,"Mittel"),
	HIGH (3, "Hoch"),
	URGENT (4, "Dringend");
	
	//final -> da unveränderbar
	private final int level; 
	private final String label;
	
	//Konstruktor
	Priority (int level, String label) {
		this.level = level;
		this.label = label;
	}

	//toString() überschreiben, damit die dt. Bezeichnungen angezeigt werden
	@Override
	public String toString() {
		return label;
	}
	
	public int getLevel() {
		return level;
	}

	public String getLabel() {
		return label;
	}
	
}

