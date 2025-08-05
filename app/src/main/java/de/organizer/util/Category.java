package de.organizer.util;

/**
 * Das Enum Category definiert verschiedene Kategorien für die Anwendung.
 * Jede Kategorie ist mit einem deutschen Label versehen.
 *
 * Kategorien:
 * - WORK: Arbeit
 * - PERSONAL: Privat
 * - SHOPPING: Einkaufen
 * - HEALTH: Gesundheit
 * - FINANCE: Finanzen
 * - OTHER: Sonstiges
 *
 * Methoden:
 * - getLabel(): Gibt das Label der Kategorie zurück.
 * - toString(): Gibt das Label als String zurück.
 */

public enum Category {

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
		return label != null ? label : "";
	}
	
}
