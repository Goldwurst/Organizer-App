package de.organizer.util;

/**
 * Die {@code Priority}-Enum definiert verschiedene Prioritätsstufen
 * für Aufgaben oder Objekte innerhalb der Anwendung.
 * Jede Priorität besitzt eine numerische Stufe und eine deutschsprachige Bezeichnung.
 *
 * <ul>
 *   <li>{@link #LOW} - Niedrige Priorität</li>
 *   <li>{@link #MEDIUM} - Mittlere Priorität</li>
 *   <li>{@link #HIGH} - Hohe Priorität</li>
 *   <li>{@link #URGENT} - Dringende Priorität</li>
 * </ul>
 */
public enum Priority {

    LOW (1, "Niedrig"),

    MEDIUM (2,"Mittel"),

    HIGH (3, "Hoch"),

    URGENT (4, "Dringend");

    /**
     * Numerische Stufe der Priorität.
     */
    private final int level;

    /**
     * Deutschsprachige Bezeichnung der Priorität.
     */
    private final String label;

    /**
     * Erstellt eine neue Prioritätsstufe mit gegebener Stufe und Bezeichnung.
     *
     * @param level numerische Stufe der Priorität
     * @param label deutschsprachige Bezeichnung
     */
    Priority (int level, String label) {
        this.level = level;
        this.label = label;
    }

    /**
     * Gibt die deutschsprachige Bezeichnung der Priorität zurück.
     *
     * @return Bezeichnung der Priorität
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Gibt die numerische Stufe der Priorität zurück.
     *
     * @return numerische Stufe
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gibt die deutschsprachige Bezeichnung der Priorität zurück.
     *
     * @return Bezeichnung der Priorität
     */
    public String getLabel() {
        return label;
    }
}

