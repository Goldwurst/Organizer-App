package de.organizer.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.organizer.model.Task;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArrangeTaskListTest {

	private List<Task> tasks;
	
	//Setup
	@BeforeEach
	void setUp() {
		
		IDGenerator.reset();
		
		Task task1 = new Task(IDGenerator.generateID(), "Z Aufgabe", "", LocalDate.of(2025, 1, 1), Priority.MEDIUM);
		Task task2 = new Task(IDGenerator.generateID(), "A Aufgabe", "", LocalDate.of(2015, 1, 1), Priority.HIGH);
		Task task3 = new Task(IDGenerator.generateID(), "M Aufgabe", "", LocalDate.of(2020, 1, 1), Priority.LOW);

		tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		tasks.add(task3);
	}
	
	//Test 1: Sortierung nach Titel
	@Test
	void testSortByTitle() {
		
		ArrangeTaskList.sortByTitle(tasks);
		assertEquals("A Aufgabe", tasks.get(0).getTitle());
		assertEquals("M Aufgabe", tasks.get(1).getTitle());
		assertEquals("Z Aufgabe", tasks.get(2).getTitle());
	}
	
	//Test 2: Sortierung nach Fälligkeit
	@Test
	void testSortByDueDate() {
		
		ArrangeTaskList.sortByDueDate(tasks);
		assertEquals(LocalDate.of(2015, 1, 1), tasks.get(0).getDueDate());
		assertEquals(LocalDate.of(2020, 1, 1), tasks.get(1).getDueDate());
		assertEquals(LocalDate.of(2025, 1, 1), tasks.get(2).getDueDate());
	}
	
	//Test 3: Sortierung nach Priorität
	@Test
	void testSortByPriority() {
		
		ArrangeTaskList.sortByPriority(tasks);
		assertEquals(Priority.LOW, tasks.get(0).getPriority());
		assertEquals(Priority.MEDIUM, tasks.get(1).getPriority());
		assertEquals(Priority.HIGH, tasks.get(2).getPriority());
	}
	
	//Test4: Sortierung nach Priorität und dann nach Fälligkeit
	@Test
	void testSortByPriorityThenDate() {
		
		ArrangeTaskList.sortByPriorityThenDate(tasks);
		assertEquals(Priority.LOW, tasks.get(0).getPriority());
		assertEquals(Priority.MEDIUM, tasks.get(1).getPriority());
		assertEquals(Priority.HIGH, tasks.get(2).getPriority());
		assertEquals(LocalDate.of(2015, 1, 1), tasks.get(2).getDueDate());
	}
}
