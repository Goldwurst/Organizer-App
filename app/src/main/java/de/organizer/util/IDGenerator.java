package de.organizer.util;

/**
 * Utility-Klasse zur Generierung fortlaufender eindeutiger IDs.
 * Die Klasse ist nicht instanziierbar und thread-sicher.
 */
public final class IDGenerator {

    /** Aktuelle ID, wird bei jedem Aufruf erhöht. */
    private static long currentID = 0;

    /** Privater Konstruktor verhindert Instanziierung. */
    private IDGenerator() {
        // Utility-Klasse
    }

    /**
     * Generiert eine neue eindeutige ID.
     * Thread-sicher durch synchronized.
     * @return die nächste eindeutige ID
     */
    public static synchronized long generateID() {
        return ++currentID;
    }

    /**
     * Setzt die ID zurück auf 0.
     * Thread-sicher durch synchronized.
     */
    public static synchronized void reset() {
        currentID = 0;
    }
}
