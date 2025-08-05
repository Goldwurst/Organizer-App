package de.organizer.model;

import de.organizer.util.Category;
import de.organizer.util.Priority;
import de.organizer.exception.MissingFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskBuilderCreatesTaskWithMandatoryFields() {
        Task task = new Task.Builder()
                .category(Category.WORK)
                .title("Testaufgabe")
                .priority(Priority.HIGH)
                .build();

        assertNotNull(task.getId());
        assertNotNull(task.getCreatedAt());
        assertEquals(Category.WORK, task.getCategory());
        assertEquals("Testaufgabe", task.getTitle());
        assertEquals(Priority.HIGH, task.getPriority());
        assertFalse(task.isDone());
    }

    @Test
    void testTaskBuilderThrowsExceptionIfMandatoryFieldsMissing() {
        Task.Builder builder = new Task.Builder();
        Exception exception = assertThrows(MissingFieldException.class, builder::build);
        assertTrue(exception.getMessage().contains("Kategorie"));
    }

    @Test
    void testToggleDone() {
        Task task = new Task.Builder()
                .category(Category.SHOPPING)
                .title("Erledigen")
                .priority(Priority.LOW)
                .build();

        assertFalse(task.isDone());
        task.toggleDone();
        assertTrue(task.isDone());
    }

    @Test
    void testToCsvRow() {
        LocalDateTime now = LocalDateTime.now();
        Task task = Task.fromCsv(42L, now, Category.HEALTH, "CSV-Test", "Beschreibung",
                LocalDate.of(2024, 6, 1), null, Priority.MEDIUM, true);

        String[] csvRow = task.toCsvRow();
        assertEquals("42", csvRow[0]);
        assertEquals(now.toString(), csvRow[1]);
        assertEquals(Category.HEALTH.name(), csvRow[2]);
        assertEquals("CSV-Test", csvRow[3]);
        assertEquals("Beschreibung", csvRow[4]);
        assertEquals("2024-06-01", csvRow[5]);
        assertEquals("", csvRow[6]);
        assertEquals(Priority.MEDIUM.name(), csvRow[7]);
        assertEquals("true", csvRow[8]);
    }
}

