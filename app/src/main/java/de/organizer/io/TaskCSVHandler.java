package de.organizer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import de.organizer.model.Task;
import de.organizer.util.Category;
import de.organizer.util.Priority;

public final class TaskCSVHandler {
	
	private TaskCSVHandler() {
		// Utility-Klasse darf nicht instanziiert werden
		throw new AssertionError("Utility-Klasse darf nicht instanziiert werden");
	}

	/**
     * Speichert die Liste von Tasks als CSV.
     * @param taskList Liste der zu speichernden Tasks
     * @param file Zieldatei
     * @throws IOException wenn beim Schreiben ein Fehler auftritt
     */
	public static void saveTasksAsCSV(List<Task> taskList, File file) throws IOException {
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			
			writer.write(String.join(";", Task.CSV_HEADER)); //Header
			writer.newLine();
			
			for (Task task : taskList) {
				writer.write(String.join(";", task.toCsvRow()));
				writer.newLine();
			}
		}
	}
		
	/**
     * Liest Tasks aus einer CSV-Datei.
     * @param file Quelldatei
     * @return Liste der eingelesenen Tasks
     * @throws IOException wenn beim Lesen ein Fehler auftritt
     */
	public static List<Task> loadTasksFromCSV(File file) throws IOException {
		
		List<Task> taskList = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			
			String line = reader.readLine(); //Header überspringen
			
			while ((line = reader.readLine()) != null) {
				
				String[] data = line.split(";");
				if (data.length != Task.CSV_HEADER.length) continue; //Sicherheitsabfrage auf Vollständigkeit der Daten
				
				long    id           		= Long.parseLong(data[0]);
				LocalDateTime createdAt		= LocalDateTime.parse(data[1]);
	            Category  category   		= Category.valueOf(data[2]);
                String  title        		= data[3];
                String  description 		= data[4];
                LocalDate dueDate    		= data[5].isEmpty() ? null : LocalDate.parse(data[5]);
                LocalDateTime reminderDate  = data[6].isEmpty() ? null : LocalDateTime.parse(data[6]);
                Priority priority    		= Priority.valueOf(data[7]);
                boolean done        		= Boolean.parseBoolean(data[8]);
				
                // Wichtig! ID und Zeitstempel müssen beibehalten werden -> fromCsv()!
                Task task = Task.fromCsv(id, createdAt, category, title,description, dueDate, reminderDate, priority, done);   
                taskList.add(task);
			}
		}		
		return taskList;
	}
	
}