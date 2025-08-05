package de.organizer.io;

import de.organizer.model.Task;
import de.organizer.util.Category;
import de.organizer.util.Priority;
import de.organizer.util.IDGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskCSVHandlerTest {

    private File tempFile;

    @BeforeEach
    void setUp() throws IOException {
        tempFile = File.createTempFile("tasks", ".csv");
        tempFile.deleteOnExit();
        IDGenerator.reset();
    }

    @Test
    void testSaveAndLoadTasks() throws IOException {
        Task task1 = new Task.Builder()
                .category(Category.WORK)
                .title("Test 1")
                .description("Beschreibung 1")
                .dueDate(LocalDate.of(2025, 1, 1))
                .reminderDate(null)
                .priority(Priority.HIGH)
                .build();
   
        Task task2 = new Task.Builder()
                .category(Category.SHOPPING)
                .title("Test 2")
                .description("Beschreibung 2")
                .dueDate(LocalDate.of(2025, 1, 1))
                .reminderDate(LocalDateTime.now())
                .priority(Priority.LOW)
                .done(true)
                .build();
        

        TaskCSVHandler.saveTasksAsCSV(List.of(task1, task2), tempFile);
        
        List<Task> loadedTasks = TaskCSVHandler.loadTasksFromCSV(tempFile);

        assertEquals(2, loadedTasks.size());
        assertEquals("Test 1", loadedTasks.get(0).getTitle());
        assertEquals("Test 2", loadedTasks.get(1).getTitle());
        assertTrue(loadedTasks.get(1).isDone());
    }

    @Test
    void testFileIsCreated() {
        assertTrue(tempFile.exists());
    }
}