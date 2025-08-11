
package de.organizer.service;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Liest einen Dienstplan aus einer Excel-Datei ein und stellt ihn in der Konsole dar.
 * Mit Logging zur Protokollierung von Fehlern und wichtigen Ereignissen.
 */
public class ExcelRosterReader {

    // Logger für Protokollierung von Warnungen, Fehlern und Infos
    private static final Logger LOGGER = Logger.getLogger(ExcelRosterReader.class.getName());

    // Datumsformat für die Konsolenausgabe
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("EEE, dd.MM.yy");

    /**
     * Liest den Dienstplan aus der gegebenen Excel-Datei. Die erste Zeile
     * enthält die Zeitfenster (Shifts), die erste Spalte die Datumsangaben.
     *
     * @param excelFile Die .xlsx- oder .xls-Datei mit dem Dienstplan
     * @return Liste der Tagespläne
     * @throws IOException Bei Eingabe-/Ausgabefehlern
     * @throws InvalidFormatException Bei ungültigem Excel-Format
     */
    public List<DaySchedule> readRoster(File excelFile) throws IOException, InvalidFormatException {
        List<DaySchedule> dayScheduleList = new ArrayList<>();

        // Versucht, die Excel-Datei zu öffnen und zu lesen
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile))) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIt = sheet.iterator();
            if (!rowIt.hasNext()) {
                LOGGER.warning("Leeres Sheet: Keine Daten gefunden.");
                return dayScheduleList; // Leeres Sheet
            }

            // 1. Header-Zeile: Spalten 1..n sind TimeSlots
            Row headerRow = rowIt.next();
            List<String> timeSlots = new ArrayList<>();

            // Liest die Zeitfenster aus der Header-Zeile (ab Spalte 1)
            for (int index = 1; index < headerRow.getLastCellNum(); index++) {
                Cell cell = headerRow.getCell(index);
                if (cell != null) {
                    timeSlots.add(cell.getStringCellValue().trim());
                }
            }

            // 2. Datenzeilen: Jede Zeile ein DaySchedule
            int rowNum = 1; // Für Logging (erste Datenzeile ist Zeile 2)
            while (rowIt.hasNext()) {
                Row row = rowIt.next();
                rowNum++;

                Cell dateCell = row.getCell(0);
                if (dateCell == null) {
                    LOGGER.info("Leere oder fehlerhafte Zeile bei Index " + rowNum + " übersprungen.");
                    continue; // Leere oder kaputte Zeile -> nächste Zeile
                }

                // Versucht, das Datum aus der Zelle zu lesen
                LocalDate date;
                try {
                    if (dateCell.getCellType() != CellType.NUMERIC) {
                        LOGGER.warning("Ungültiges Datum in Zeile " + rowNum + ". Zeile wird übersprungen.");
                        continue;
                    }
                    date = dateCell.getDateCellValue()
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate();
                } catch (Exception e) {
                    LOGGER.log(Level.WARNING, "Fehler beim Parsen des Datums in Zeile " + rowNum, e);
                    continue;
                }

                DaySchedule daySchedule = new DaySchedule(date);

                // Für jeden TimeSlot die Namen einlesen
                for (int index = 0; index < timeSlots.size(); index++) {
                    Cell nameCell = row.getCell(index + 1); // index 0 -> dateCell

                    if (nameCell != null) {
                        String nameCellString;
                        // Nur String-Zellen werden verarbeitet
                        if (nameCell.getCellType() == CellType.STRING) {
                            nameCellString = nameCell.getStringCellValue().trim();
                        } else {
                            nameCellString = "";
                            LOGGER.fine("Unerwarteter Zelltyp für Namen in Zeile " + rowNum + ", Spalte " + (index + 2));
                        }

                        if (!nameCellString.isEmpty()) {
                            // Namen durch "/" getrennt
                            String[] names = nameCellString.split("/");
                            for (String name : names) {
                                daySchedule.addAssignment(timeSlots.get(index), name.trim());
                            }
                        }
                    } else {
                        LOGGER.fine("Keine Zuweisung für " + timeSlots.get(index) + " am " + date);
                    }
                }
                dayScheduleList.add(daySchedule);
            }
        } catch (Exception e) {
            // Fehler beim Einlesen der Datei werden protokolliert
            LOGGER.log(Level.SEVERE, "Fehler beim Einlesen der Excel-Datei: " + excelFile.getAbsolutePath(), e);
            throw e;
        }
        LOGGER.info("Dienstplan erfolgreich eingelesen: " + dayScheduleList.size() + " Tage gefunden.");
        return dayScheduleList;
    }

    /**
     * Gibt den Dienstplan in der Konsole aus.
     * @param daySchedules Liste aller Tagespläne
     */
    public void printRoster(List<DaySchedule> daySchedules) {
        for (DaySchedule ds : daySchedules) {
            System.out.println(ds.getDate().format(DATE_FORMAT));
            ds.getAssignments().forEach((timeSlot, names) -> {
                System.out.println("  " + timeSlot + ": " + String.join(", ", names));
            });
            System.out.println();
        }
    }

    /**
     * Repräsentiert den Plan für einen einzelnen Tag: Datum und Namenszuordnungen.
     */
    public static class DaySchedule {

        private final LocalDate date;
        // Map: Zeitfenster -> Liste der Namen
        private final Map<String, List<String>> assignments = new LinkedHashMap<>();

        public DaySchedule(LocalDate date) {
            this.date = date;
        }

        /**
         * Fügt eine Namenszuweisung für ein Zeitfenster hinzu.
         * @param timeSlot Bezeichnung des Dienstzeitfensters (z.B.: "10:30-17:00")
         * @param name     Name des Mitarbeiters
         */
        public void addAssignment(String timeSlot, String name) {
            assignments.computeIfAbsent(timeSlot, k -> new ArrayList<>()).add(name);
        }

        public LocalDate getDate() {
            return date;
        }

        public Map<String, List<String>> getAssignments() {
            return assignments;
        }
    }
}
