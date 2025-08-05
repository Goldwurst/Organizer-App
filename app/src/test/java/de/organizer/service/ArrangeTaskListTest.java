package de.organizer.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.organizer.model.Task;
import de.organizer.util.Category;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ArrangeTaskListTest {

    private List<Task> tasks;

    @BeforeEach
    void setUp() {
        IDGenerator.reset();
        tasks = new ArrayList<>();
        tasks.add(new Task.Builder()
                .category(Category.OTHER)
                .title("Z Aufgabe")
                .dueDate(LocalDate.of(2025, 1, 1))
                .priority(Priority.MEDIUM)
                .build());
        tasks.add(new Task.Builder()
                .category(Category.WORK)
                .title("A Aufgabe")
                .dueDate(LocalDate.of(2015, 1, 1))
                .priority(Priority.HIGH)
                .build());
        tasks.add(new Task.Builder()
                .category(Category.FINANCE)
                .title("M Aufgabe")
                .dueDate(LocalDate.of(2020, 1, 1))
                .priority(Priority.LOW)
                .build());
    }

    @Test
    void testSortByTitle() {
        ArrangeTaskList.sortByTitle(tasks);
        assertEquals("A Aufgabe", tasks.get(0).getTitle());
        assertEquals("M Aufgabe", tasks.get(1).getTitle());
        assertEquals("Z Aufgabe", tasks.get(2).getTitle());
    }

    @Test
    void testSortByDueDate() {
        ArrangeTaskList.sortByDueDate(tasks);
        assertEquals(LocalDate.of(2015, 1, 1), tasks.get(0).getDueDate());
        assertEquals(LocalDate.of(2020, 1, 1), tasks.get(1).getDueDate());
        assertEquals(LocalDate.of(2025, 1, 1), tasks.get(2).getDueDate());
    }

    @Test
    void testSortByPriority() {
        ArrangeTaskList.sortByPriority(tasks);
        assertEquals(Priority.LOW, tasks.get(0).getPriority());
        assertEquals(Priority.MEDIUM, tasks.get(1).getPriority());
        assertEquals(Priority.HIGH, tasks.get(2).getPriority());
    }

    @Test
    void testSortByPriorityThenDate() {
        ArrangeTaskList.sortByPriorityDueDate(tasks);
        assertEquals(Priority.LOW, tasks.get(0).getPriority());
        assertEquals(Priority.MEDIUM, tasks.get(1).getPriority());
        assertEquals(Priority.HIGH, tasks.get(2).getPriority());
        assertEquals(LocalDate.of(2015, 1, 1), tasks.get(2).getDueDate());
    }

    @Test
    void testSortEmptyList() {
        List<Task> empty = new ArrayList<>();
        ArrangeTaskList.sortByTitle(empty);
        ArrangeTaskList.sortByDueDate(empty);
        ArrangeTaskList.sortByPriority(empty);
        ArrangeTaskList.sortByPriorityDueDate(empty);
        assertTrue(empty.isEmpty());
    }

    @Test
    void testSortWithEqualValues() {
        List<Task> equalTasks = new ArrayList<>();
        equalTasks.add(new Task.Builder()
                .category(Category.WORK)
                .title("Test")
                .dueDate(LocalDate.of(2022, 1, 1))
                .priority(Priority.HIGH)
                .build());
        equalTasks.add(new Task.Builder()
                .category(Category.WORK)
                .title("Test")
                .dueDate(LocalDate.of(2022, 1, 1))
                .priority(Priority.HIGH)
                .build());
        ArrangeTaskList.sortByTitle(equalTasks);
        ArrangeTaskList.sortByDueDate(equalTasks);
        ArrangeTaskList.sortByPriority(equalTasks);
        ArrangeTaskList.sortByPriorityDueDate(equalTasks);
        assertEquals("Test", equalTasks.get(0).getTitle());
        assertEquals("Test", equalTasks.get(1).getTitle());
    }
}