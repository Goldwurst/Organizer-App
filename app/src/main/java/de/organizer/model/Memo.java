package de.organizer.model;

import java.time.LocalDateTime;

import de.organizer.exception.MissingFieldException;
import de.organizer.util.IDGenerator;

/**
 * Modelliert eine Notiz mit eindeutiger ID, Erstellungszeitpunkt und weiteren Eigenschaften.
 */
public class Memo {
	
// ----------------------- Felder ---------------------------

	private final long id; 					// Eindeutige ID der Notiz
	private final LocalDateTime createdAt; 	// Erstellungszeitpunkt der Notiz
	private String title; 					// Titel der Notiz
	private String content; 				// Inhalt der Notiz


// ----------------------- Getter und Setter ---------------------------

	public long getId() {return id;}
	
	public LocalDateTime getCreatedAt() {return createdAt;}

	public String getTitle() {return title;}

	public void setTitle(String title) {this.title = title;}

	public String getContent() {return content;}

	public void setContent(String content) {this.content = content;}

//------------------------- CSV-Konstanten und -Methoden --------------

	/**
	 * Header für CSV-Export. Diese Reihenfolge muss mit toCsvRow() übereinstimmen.
	 */
	public static final String[] CSV_HEADER = {"id", "createdAt", "title", "content"};

	/**
	 * Wandelt die Notiz in eine CSV-Zeile um.
	 * @return CSV-Zeile als String
	 */
	public String[] toCsvRow() {
		return new String[] {
				String.valueOf(id),
				createdAt.toString(),
				title,
				content
		};
	}
	
	/**
	 * Factory-Methode nur für CSV-Einleser, um bestehende ID+Zeitstempel zu übernehmen.
	 */
	public static Memo fromCsv(long id, String createdAt, String title, String content) {
		Memo memo = new Memo.Builder()
				.title(title)
				.content(content)
				.buildInternal(id, createdAt);
		return memo;
	}
	
	// ----------------------- Builder ---------------------------
	/**
	 * Builder-Klasse zum Erstellen von Memo-Objekten.
	 */
	public static class Builder {
		
		private long id;
		private LocalDateTime createdAt;
		private String title;
		private String content;
		
		/**
		 * Setzt den Titel der Notiz (optional).
		 */
		public Builder title(String title) {
			this.title = title;
			return this;
		}
		/**
		 * Setzt den Inhalt der Notiz (Pflicht).
		 */
		public Builder content(String content) {
			this.content = content;
			return this;
		}
		
		/**
		 * Baut das Memo-Objekt mit automatisch generierter ID und Erstellungszeitpunkt.
		 */
		public Memo build() {
			
			this.id = IDGenerator.generateID(); // Eindeutige ID generieren
			this.createdAt = LocalDateTime.now(); // Aktueller Zeitstempel
			
			validateMandatoryFields();
			
			return new Memo(this);
		}
		
		/**
		 * Baut das Memo-Objekt mit vorgegebenen ID und Erstellungszeitpunkt.
		 */
		public Memo buildInternal(long id, String createdAt) {
			this.id = id; // Vorhandene ID übernehmen
			this.createdAt = LocalDateTime.parse(createdAt); // Vorhandenen Zeitstempel übernehmen

			validateMandatoryFields();

			return new Memo(this);
		}
		
		/**
		 * Validiert die Pflichtfelder.
		 */
		private void validateMandatoryFields() {
			if (content == null || content.isBlank()) {
				throw new MissingFieldException("Content darf nicht leer sein.");
			}
		}
	}
	
	// ----------------------- Konstruktor ---------------------------
	private Memo(Builder builder) {
		this.id = builder.id;
		this.createdAt = builder.createdAt;
		this.title = builder.title;
		this.content = builder.content;
	}
}




















