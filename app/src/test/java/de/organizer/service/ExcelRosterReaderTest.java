package de.organizer.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExcelRosterReaderTest {

    @TempDir
    File tempDir;

    @Test
    void testReadRosterAndPrintRoster() throws Exception {
        // Excel-Datei im Temp-Verzeichnis erzeugen
        File file = new File(tempDir, "dienstplan.xlsx");
        try (Workbook wb = new XSSFWorkbook()) {
            Sheet sheet = wb.createSheet();
            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Datum");
            header.createCell(1).setCellValue("Früh");
            header.createCell(2).setCellValue("Spät");
            // Tag 1
            Row row1 = sheet.createRow(1);
            row1.createCell(0).setCellValue(java.util.Date.from(LocalDate.of(2024, 6, 10)
                    .atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
            row1.createCell(1).setCellValue("Anna/Bob");
            row1.createCell(2).setCellValue("Clara");
            // Tag 2
            Row row2 = sheet.createRow(2);
            row2.createCell(0).setCellValue(java.util.Date.from(LocalDate.of(2024, 6, 11)
                    .atStartOfDay(java.time.ZoneId.systemDefault()).toInstant()));
            row2.createCell(1).setCellValue("Dieter");
            row2.createCell(2).setCellValue("");
            // Speichern
            try (FileOutputStream fos = new FileOutputStream(file)) {
                wb.write(fos);
            }
        }

        ExcelRosterReader reader = new ExcelRosterReader();
        List<ExcelRosterReader.DaySchedule> roster = reader.readRoster(file);

        assertEquals(2, roster.size());
        assertEquals(LocalDate.of(2024, 6, 10), roster.get(0).getDate());
        assertEquals(List.of("Anna", "Bob"), roster.get(0).getAssignments().get("Früh"));
        assertEquals(List.of("Clara"), roster.get(0).getAssignments().get("Spät"));
        assertEquals(LocalDate.of(2024, 6, 11), roster.get(1).getDate());
        assertEquals(List.of("Dieter"), roster.get(1).getAssignments().get("Früh"));
        assertNull(roster.get(1).getAssignments().get("Spät"));

        // Optional: Konsolenausgabe testen (hier nur Aufruf, keine Assertion)
        reader.printRoster(roster);
    }
}