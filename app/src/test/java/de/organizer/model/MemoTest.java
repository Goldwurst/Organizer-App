package de.organizer.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import de.organizer.exception.MissingFieldException;

class MemoTest {

    private Memo memo;

    @BeforeEach
    void setUp() {
        memo = new Memo.Builder()
                .title("Testtitel")
                .content("Testinhalt")
                .build();
    }

    @Test
    void testBuilderSetsFieldsCorrectly() {
        assertNotNull(memo.getId());
        assertNotNull(memo.getCreatedAt());
        assertEquals("Testtitel", memo.getTitle());
        assertEquals("Testinhalt", memo.getContent());
    }

    @Test
    void testSettersAndGetters() {
        memo.setTitle("Neuer Titel");
        memo.setContent("Neuer Inhalt");
        assertEquals("Neuer Titel", memo.getTitle());
        assertEquals("Neuer Inhalt", memo.getContent());
    }

    @Test
    void testToCsvRow() {
        String[] csv = memo.toCsvRow();
        assertEquals(4, csv.length);
        assertEquals(String.valueOf(memo.getId()), csv[0]);
        assertEquals(memo.getCreatedAt().toString(), csv[1]);
        assertEquals(memo.getTitle(), csv[2]);
        assertEquals(memo.getContent(), csv[3]);
    }

    @Test
    void testFromCsvCreatesCorrectMemo() {
        long id = 123L;
        String createdAt = "2024-06-01T12:00";
        Memo m = Memo.fromCsv(id, createdAt, "Titel", "Inhalt");
        assertEquals(id, m.getId());
        assertEquals("Titel", m.getTitle());
        assertEquals("Inhalt", m.getContent());
        assertEquals(createdAt, m.getCreatedAt().toString());
    }

    @Test
    void testMissingContentThrowsException() {
        Memo.Builder builder = new Memo.Builder().title("Titel");
        assertThrows(MissingFieldException.class, builder::build);
    }

    @Test
    void testBlankContentThrowsException() {
        Memo.Builder builder = new Memo.Builder().title("Titel").content("   ");
        assertThrows(MissingFieldException.class, builder::build);
    }
}
