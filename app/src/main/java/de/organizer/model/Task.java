package de.organizer.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import de.organizer.exception.MissingFieldException;
import de.organizer.util.Category;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;

/**
 * Repräsentiert eine Aufgabe mit ID, Zeitstempel und weiteren Eigenschaften.
 * Der Builder kapselt die Erzeugungslogik für ID und createdAt.
 */
public class Task {
	
	// ---------------------- Felder ---------------------------
	
	private final long id; 					//Eindeutige ID einer Aufgabe
	private final LocalDateTime createdAt; 	//Wann wurde die Aufgabe erstellt?
	private Category category; 				//Kategorie
	private String title; 					//Titel der Aufgabe
	private String description; 			//Detaillierte Beschreibung
	private LocalDate dueDate; 				//Fälligkeitsdatum der Aufgabe
	private LocalDateTime reminderDate; 	//Erinnerung
	private Priority priority; 				//Priorität (4 = dringend, 3 = hoch, 2 = mittel, 1 = niedrig)
	private boolean done; 					//Ist die Aufgabe erledigt?

		
	//------------------------- Getter und Setter ---------------------------
	
	public long getId() {return id;}
	
	public LocalDateTime getCreatedAt() {return createdAt;}
	
	public Category getCategory() {return category;}
	
	public String getTitle() {return title;}
	
	public String getDescription() {return description;}

	public LocalDate getDueDate() {return dueDate;}

	public LocalDateTime getReminderDate() {return reminderDate;}

	public Priority getPriority() {return priority;}

	public boolean isDone() {return done;}
	
	public void setDone(boolean done) { this.done = done; }

	public void toggleDone() { this.done = !this.done; }
	
	//------------------------- CSV-Konstanten und -Methoden --------------
	/**
     * Header für CSV-Export. Diese Reihenfolge muss mit toCsvRow() übereinstimmen.
     */
	public static final String[] CSV_HEADER = {
			"id", "createdAt", "category", "title", "description", "dueDate",
			"reminderDate", "priority", "done"
	};
	
	/**
     * Liefert die Wertezeile für den CSV-Export als String-Array.
     * @return Werte in der Reihenfolge von CSV_HEADER
     */
	public String[] toCsvRow() {
		return new String[] {
				String.valueOf(id),
				createdAt.toString(),
				category.name(),
				title,
				description != null ? description : "",
				dueDate != null ? dueDate.toString() : "",
				reminderDate != null ? reminderDate.toString() : "",
				priority.name(),
				String.valueOf(done)
		};
	}
    
    /**
     * Factory-Methode nur für CSV-Einleser, um bestehende ID+Zeitstempel zu übernehmen.
     */
    public static Task fromCsv(long id,
                        LocalDateTime createdAt,
                        Category category,
                        String title,
                        String description,
                        LocalDate dueDate,
                        LocalDateTime reminderDate,
                        Priority priority,
                        boolean done) {
        Task task = new Task.Builder()
                     .category(category)
                     .title(title)
                     .description(description)
                     .dueDate(dueDate)
                     .reminderDate(reminderDate)
                     .priority(priority)
                     .done(done)
                     .buildInternal(id, createdAt);
        return task;
    }
	
	//------------------ Builder ---------------------
	/**
     * Builder-Klasse für Task. Kapselt die Erzeugungslogik für ID und createdAt.
     */
	public static class Builder {
		
		// Felder im Builder spiegeln die Felder der Task wider
        private long id;                        // wird im build() gesetzt
        private LocalDateTime createdAt;        // wird im build() gesetzt
        private Category category;
        private String title;
        private String description;
        private LocalDate dueDate;
        private LocalDateTime reminderDate;
        private Priority priority;
        private boolean done = false;           // Standardwert: nicht erledigt
	
        /**
         * Setzt die Kategorie (Pflicht)
         */
        public Builder category(Category category) {
        	this.category = category;
        	return this;
        }
	
        /**
         * Setzt den Titel (optional)
         */
        public Builder title(String title) {
        	this.title = title;
        	return this;
        }
	
        /**
         * Setzt den Beschreibung (optional)
         */
        public Builder description(String description) {
        	this.description = description;
        	return this;
        }
	
        /**
         * Setzt den Fälligkeitsdatum (optional)
         */
        public Builder dueDate(LocalDate dueDate) {
        	this.dueDate = dueDate;
        	return this;
        }
	
        /**
         * Setzt den Erinnerungsdatum (optional)
         */
        public Builder reminderDate(LocalDateTime reminderDate) {
        	this.reminderDate = reminderDate;
        	return this;
        }
	
        /**
         * Setzt den Priorität (Pflicht)
         */
        public Builder priority(Priority priority) {
        	this.priority = priority;
        	return this;
        }
	
        /**
         * Setzt den Erledigungsstatus (optional, Standard: false)
         */
        public Builder done(boolean done) {
        	this.done = done;
        	return this;
        }
        
	 	/**
	 	 * Baut die Task-Instanz. Generiert ID und Timestamp automatisch.
	 	 * @return Neue Task-Instanz mit allen gesetzten Werten.
	 	 */
        public Task build() {
        	// Generiere eindeutige ID
        	this.id = IDGenerator.generateID();
        	// Setze aktuellen Zeitstempel
        	this.createdAt = LocalDateTime.now();
        	
        	// Validierungs-Checks
        	validateMandatoryFields();
        	
        	// Erzeuge und liefere den Task
        	return new Task(this);
        }
        
        /**
    	 * Alternative Build-Methode, um beim Lesen einer CSV ID und createdAt beizubehalten
    	 * @param csvId -> ausgelesene Id aus CSV
    	 * @param csvCreatedAt -> ausgelesener Zeitstempel
    	 * @return Task.Instanz mit übernommenen Werten
    	 */
    	private Task buildInternal(long csvId, LocalDateTime csvCreatedAt) {
    	    this.id = csvId;
    	    this.createdAt = csvCreatedAt;
    	    
    	    // validiere Pflichtfelder wie in build()
    	    validateMandatoryFields();
    	    
    	    return new Task(this);
    	}
    	
    	/**
    	 * Methode, um Pflichtfelder auf Inhalt zu prüfen
    	 */
    	private void validateMandatoryFields() {
    		
    		//MissingFieldException -> custom Exception
    		if (category == null) throw new MissingFieldException("Kategorie muss gesetzt sein");
    	    if (title == null || title.isBlank()) throw new MissingFieldException("Titel muss gesetzt sein");
    	    if (priority == null) throw new MissingFieldException("Priorität muss gesetzt sein");
    	}
	}
	
	// Privater Konstruktor für Builder
    private Task(Builder builder) {
        this.id = builder.id;
        this.createdAt = builder.createdAt;
        this.category = builder.category;
        this.title = builder.title;
        this.description = builder.description;
        this.dueDate = builder.dueDate;
        this.reminderDate = builder.reminderDate;
        this.priority = builder.priority;
        this.done = builder.done;
    }
}

