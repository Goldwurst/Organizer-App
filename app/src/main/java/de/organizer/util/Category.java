package de.organizer.util;

public enum Category {

	//Kategorien definieren und mit dt.Begriffen labeln
	WORK("Arbeit"),
	PERSONAL("Privat"),
	SHOPPING("Einkaufen"),
	HEALTH("Gesundheit"),
	FINANCE("Finanzen"),
	OTHER("Sonstiges");
	
	private final String label;
	
	Category(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}
	
	@Override
	public String toString() {
		return label;
	}
	
}
