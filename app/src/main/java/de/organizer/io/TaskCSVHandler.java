package de.organizer.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import de.organizer.model.Task;
import de.organizer.util.Category;
import de.organizer.util.Priority;

public class TaskCSVHandler {

	public void saveTasksAsCSV(List<Task> taskList, File file) {
		
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(file))){
			
			writer.write("id;title;description;dueDate;priority;done");
			writer.newLine();
			
			for (Task task : taskList) {
				String line = String.join(";", task.getExportData());
				writer.write(line);
				writer.newLine();
			}
		}
		catch(IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Beim Speichern ist ein Fehler aufgetreten", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public List<Task> openTasksFromCSV(File file) {
		
		List<Task> taskList = new ArrayList<>();
		
		try(BufferedReader reader = new BufferedReader(new FileReader(file))){
			
			reader.readLine();
			
			String line;
			while ((line = reader.readLine()) != null) {
				
				String[] data = line.split(";");
				if (data.length != 8) continue;
				
				taskList.add(new Task(Long.parseLong(data[0]), Category.valueOf(data[1]), data[2], data[3], LocalDate.parse(data[4]),
						(data[5] == "") ? null : LocalDate.parse(data[5]), Priority.valueOf(data[6]), Boolean.parseBoolean(data[7])));
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Beim Laden ist ein Fehler aufgetreten", "Fehler", JOptionPane.ERROR_MESSAGE);
		}
		
		return taskList;
	}
	
}