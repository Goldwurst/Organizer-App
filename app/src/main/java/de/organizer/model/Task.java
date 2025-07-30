package de.organizer.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import de.organizer.util.Category;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;

public class Task {
	
	private long id; //Eindeutige ID einer Aufgabe
	private String title; //Titel der Aufgabe
	private String description; //Detaillierte Beschreibung
	private LocalDate dueDate; //Fälligkeitsdatum der Aufgabe
	private Priority priority; //Priorität (4 = dringend, 3 = hoch, 2 = mittel, 1 = niedrig)
	private boolean done; //Ist die Aufgabe erledigt?
	private LocalDateTime createdAt; //Wann wurde die Aufgabe erstellt?
	private LocalDate reminderDate; //Erinnerung
	private Category category; //Kategorie
	
	public Task(long id, Category category, String title, String description, 
					LocalDate dueDate, LocalDate reminderDate, Priority priority) {
		
		this.id = id;
		this.category = category;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.reminderDate = reminderDate;
		this.priority = priority;
	
		done = false;
		createdAt = IDGenerator.getTimestamp(id);
	}
	
	public Task(long id, Category category, String title, String description,
					LocalDate dueDate, LocalDate reminderDate, Priority priority, Boolean done) {
		
		this.id = id;
		this.category = category;
		this.title = title;
		this.description = description;
		this.dueDate = dueDate;
		this.reminderDate = reminderDate;
		this.priority = priority;
		this.done = done;
		
		createdAt = IDGenerator.getTimestamp(id);
	}
	
	@Override
	public String toString() {
		return String.format("[%d] %s (Fällig: %s, Erinnerung: %s, Prio: %s, Erledigt: %s)",
								id, title, dueDate, (reminderDate != null) ? reminderDate.toString() : "", priority, done ? "\u2705" : "\u274C");
	}

	public String[] getExportData() {
		
		String[] data = new String[ ]{
				String.valueOf(id),
				category.name(),
				title,
				description,
				String.valueOf(dueDate),
				(reminderDate == null) ? "" : String.valueOf(reminderDate),
				priority.name(),
				String.valueOf(done)
		};
		
		return data;
	}
	
	public void toggleDone() {
		done = !done;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public Priority getPriority() {
		return priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDate getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(LocalDate reminderDate) {
		this.reminderDate = reminderDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	


	
	
}

