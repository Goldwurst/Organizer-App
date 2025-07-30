package de.organizer.io;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import java.time.LocalDate;

import java.util.List;

import de.organizer.model.Task;
import de.organizer.util.Category;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;

public class TaskCSVHandlerTest {

	private TaskCSVHandler handler;
	private File tempFile;
	
	//Setup
	@BeforeEach
	void setUp() throws IOException {
		
		handler = new TaskCSVHandler();
		tempFile = File.createTempFile("tasks", ".csv");
		tempFile.deleteOnExit(); //wird beim Beenden gelöscht
		IDGenerator.reset(); //zurücksetzen!		
	}
	
	//Test: Speichern und Laden von Task-Listen
	@Test
	void testSaveAndLoadTasks() {
		
		Task task1 = new Task(IDGenerator.generateID(), Category.FINANCE, "Test 1", "Beschreibung 1", LocalDate.of(2025, 1, 1), null, Priority.HIGH);
		Task task2 = new Task(IDGenerator.generateID(), null, "Test 2", "Beschreibung 2", LocalDate.now(), LocalDate.now(), Priority.URGENT);
		task2.setDone(true);
		
		handler.saveTasksAsCSV(List.of(task1, task2), tempFile); //Speichern
		
		List<Task> loadedTasks = handler.openTasksFromCSV(tempFile); //Laden
		
		assertEquals(2, loadedTasks.size()); //Anzahl der Tasks überprüfen
		
		Task loaded1 = loadedTasks.get(0);
		Task loaded2 = loadedTasks.get(1);
		
		//Vergleich der Daten
		assertEquals("Test 1", loaded1.getTitle());
		assertFalse(loaded1.toString().contains("\u2705"));
		
		assertEquals("Test 2", loaded2.getTitle());
		assertTrue(loaded2.toString().contains("\u2705"));		
	}
	
	//Test: Existiert die temporäre Datei überhaupt?
	@Test
	void testFileIsCreated() {
		assertTrue(tempFile.exists(), "Temporäre Datei sollte existieren");
	}
}

