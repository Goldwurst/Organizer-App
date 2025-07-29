package de.organizer.model;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.organizer.util.Priority;

import java.time.LocalDate;

public class TaskTest {

	private Task task;
	
	//Setup-Block vor jedem Test
	@BeforeEach
	void setUp() {
		task = new Task(1L, "Testaufgabe", "Beschreibung", LocalDate.of(2025,  8, 1), Priority.HIGH);
	}
	
	//Test 1: done-Status prüfen
	@Test
	void testInitialDoneState() {
		assertFalse(task.toString().contains("\u2705"), "Task sollte initial unerledigt sein");
	}
	
	//Test 2: Toggle-Verhalten prüfen
	@Test
	void testToggleDone() {
		task.toggleDone();
		assertTrue(task.toString().contains("\u2705"), "Task sollte nach toggle erledigt sein");
	}
	
	//Test 3: Exportdaten prüfen
	@Test
	void testExportData() {
		String[] data = task.getExportData();
		assertEquals("1", data[0]);
		assertEquals("Testaufgabe", data[1]);
		assertEquals("Beschreibung", data[2]);
		assertEquals("2025-08-01", data[3]);
		assertEquals("HIGH", data[4]);
		assertEquals("false", data[5]);
	}	
}

