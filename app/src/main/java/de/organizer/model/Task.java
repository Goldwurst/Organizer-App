package de.organizer.model;

import java.time.LocalDate;

import de.organizer.util.Priority;

public class Task {
	
	private long id; //Eindeutige ID einer Aufgabe
	private String title; //Titel der Aufgabe
	private String description; //Detaillierte Beschreibung
	private LocalDate dueDate; //Fälligkeitsdatum der Aufgabe
	private Priority priority; //Priorität (4 = dringend, 3 = hoch, 2 = mittel, 1 = niedrig)
	private boolean done; //Ist die Aufgabe erledigt?
	private LocalDate createdAt;
	
	public Task(long id, String title, String description, LocalDate dueDate, Priority priority) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
	
		done = false;
		createdAt = LocalDate.now();
	}
	
	public Task(long id, String title, String description, LocalDate dueDate, Priority priority, Boolean done) {
		
		this.id = id;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.priority = priority;
		this.done = done;
		
		createdAt = LocalDate.now();
	}
	
	@Override
	public String toString() {
		return String.format("[%d] %s (Fällig: %s, Prio: %s, Erledigt: %s)",
								id, title, dueDate, priority, done ? "\u2705" : "\u274C");
	}

	public String[] getExportData() {
		
		String[] data = new String[ ]{
				String.valueOf(id),
				title,
				description,
				String.valueOf(dueDate),
				priority.name(),
				String.valueOf(done)
		};
		
		return data;
	}
	
	public void toggleDone() {
		done = !done;
	}
	
	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}


	
	
}

