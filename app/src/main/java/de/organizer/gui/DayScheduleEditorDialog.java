package de.organizer.gui;

import de.organizer.service.ExcelRosterReader.DaySchedule;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.datatransfer.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialog zur Bearbeitung eines DaySchedule.
 *
 * Zeigt eine 2-spaltige Tabelle: links TimeSlot, rechts die zugeordneten Namen (kommasepariert).
 * Per Drag & Drop kannst du die ZUORDNUNGEN der Namen tauschen.
 * Änderungen werden beim Klick auf "Speichern" zurück in das DaySchedule geschrieben.
 */
public class DayScheduleEditorDialog extends JDialog {
	
	private final DaySchedule daySchedule;											// Originaldaten -> werden beim Speichern aktualisiert
	private final List<String> timeSlots = new ArrayList<>();						// geordnete Liste der Schichtzeiten (= Zeilenreihenfolge)
	private final Map<String, List<String>> assignments = new LinkedHashMap<>();	// Arbeitskopie der Zuordnungen (timeSlots -> Namen)
	
	private JTable table;
	private ScheduleTableModel tableModel;
	
	/**Konstruktor für den Dialog.
	 * @param parent Elternfenster (Frame oder Dialog)
	 * @param daySchedule DaySchedule, dessen Zuordnungen bearbeitet werden sollen
	 */
	public DayScheduleEditorDialog(Frame parent, DaySchedule daySchedule) {
		
		super(parent, "Dienstplan bearbeiten: " + daySchedule.getDate(), ModalityType.APPLICATION_MODAL);
		this.daySchedule = daySchedule;
		
		initData();
		initUi();
		
		setSize(800, 500);
		setLocationRelativeTo(parent);
	}
	
	/** Kopiert die Daten aus einem DaySchedule in eine bearbeitbare Arbeitskopie. */
	private void initData() {
		
		daySchedule.getAssignments().forEach((slot, names) -> {
			timeSlots.add(slot);
			assignments.put(slot,  new ArrayList<>(names)); // eigene Namensliste je Schicht
		});
	}
	
	/** Baut Oberflächen-Elemente zusammen (Tabelle, Buttons, Layout, D'n'D) */
	private void initUi() {
		
		model = new ScheduleTableModel(); // custom class
		table = new JTable(model);
		table.setFillsViewportHeight(true); // Füllt den verfügbaren Platz
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // nur ein Element gleichzeitig auswählbar
		
		// Spaltenbreite / Renderer
		table.getColumnModel().getColumn(0).setPreferredWidth(250); // TimeSlot-Spalte
		table.getColumnModel().getColumn(1).setPreferredWidth(500); // Namen-Spalte
		
		// Drag & Drop; ganze Zeilen (Assignments) vertauschen
		table.setDragEnabled(true);
		table.setDropMode(DropMode.ON); // ON -> Drop "auf" dem Element unter dem Cursor
		table.setTransferHandler(new RowSwapTransferHandler()); // custom class
		
		
		JButton buttonSave = new JButton("Speichern");
		buttonSave.addActionListener(e -> saveAndClose());	// Speichern und Dialog schließen
		
		JButton buttonCancel = new JButton("Abbrechen");
		buttonCancel.addActionListener(e -> dispose()); // Dialog schließen ohne Änderungen
		
		JButton buttonReset = new JButton("Zurücksetzen");
		buttonReset.setToolTipText("Änderungen verwerfen und Original neu laden");
		buttonReset.addActionListener(e -> reloadFromSchedule()); // Originaldaten wiederherstellen
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.add(buttonSave);
		bottomPanel.add(buttonReset);
		bottomPanel.add(buttonCancel);
		
		getContentPane().setLayout(new BorderLayout(8, 8));	// Abstand (Padding) zwischen Komponenten
		getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
		getContentPane().add(bottomPanel, BorderLayout.SOUTH);	
	}
	
	/** Lädt die Daten aus dem Original-Schedule erneut in die Arbeitskopie. */
	private void reloadFromSchedule() {
		
		timeSlots.clear();
		assignments.clear();
		initData();
		model.fireTableDataChanged(); // komplette Tabelle neu zeichnen
	}
	
	/** Schreibt die Änderungen zurück in das übergebene DaySchedule und schließt den Dialog. */
	private void saveAndClose() {
		// Original-Map vollständig ersetzen, Reihenfolge bleibt erhalten
		daySchedule.getAssignments().clear();
		
		for (String slot : timeSlots) {
			daySchedule.getAssignments().put(slot, new ArrayList<>(assignments.get(slot)));
		}
		dispose(); // Dialog schließen
	}
	
	/** TableModel: 2 Spalten (Schichtzeit, Namen). */
	private class ScheduleTableModel extends AbstractTableModel {
		
		private final String[] COLS = {"Schichtzeit", "Namen"}; // Spaltenüberschriften
		
		@Override public int getRowCount() { return timeSlots.size(); }
		@Override public int getColumnCount() { return COLS.length; }
		@Override public String getColumnName(int column) { return COLS[column]; }
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			
			String slot = timeSlots.get(rowIndex);
			if (columnIndex == 0) return slot;	// Schichtzeit
			List<String> names = assignments.get(slot);
			return String.join(", ",  names); // Namen kommasepariert
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return columnIndex == 1; // Nur die Namen-Spalte ist editierbar
		}
		
		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			
			if (columnIndex != 1) return; // Nur Namen-Spalte bearbeiten
			String slot = timeSlots.get(rowIndex);
			// Freitext in Namensliste zurückschreiben (Komma-separiert)
			String namesString = aValue == null ? "" : aValue.toString();
			String[] names = namesString.split(",");
			List<String> nameList = new ArrayList<>();
			for (String name : names) {	
				name.trim();
				
				if (!name.isEmpty()) 
					nameList.add(name);
			}
			assignments.put(slot, nameList);
			fireTableRowsUpdated(rowIndex, rowIndex); // Zeile aktualisieren
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
