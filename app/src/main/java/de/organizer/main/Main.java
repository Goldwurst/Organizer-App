package de.organizer.main;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import de.organizer.io.TaskCSVHandler;
import de.organizer.model.Task;
import de.organizer.service.ArrangeTaskList;
import de.organizer.util.IDGenerator;
import de.organizer.util.Priority;

public class Main {

	public static void main(String[] args) {
		
		        Task t1 = new Task(IDGenerator.generateID(), "Einkaufen", "Milch und Brot", LocalDate.of(2025, 8, 1), Priority.MEDIUM);
		        Task t2 = new Task(IDGenerator.generateID(), "Java üben", "Teil 3 erledigen", LocalDate.now(), Priority.HIGH);
		        Task t3 = new Task(IDGenerator.generateID(), "Zimmer aufräumen", "", LocalDate.now().plusDays(2), Priority.LOW);

		        t2.toggleDone();
		        
		        System.out.println(t1);
		        System.out.println(t2);
		        System.out.println(t3);
		        
		        List<Task> taskList = new ArrayList<>();
		        taskList.add(t1);
		        taskList.add(t2);
		        taskList.add(t3);
		        
		        File file = new File("tasklist.csv");
		        new TaskCSVHandler().saveTasksAsCSV(taskList, file);
		        
		        List<Task> newTaskList = new TaskCSVHandler().openTasksFromCSV(file);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        ArrangeTaskList.sortByPriority(newTaskList);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        ArrangeTaskList.sortByDueDate(newTaskList);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        ArrangeTaskList.sortByTitle(newTaskList);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        ArrangeTaskList.sortByPriorityThenDate(newTaskList);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        ArrangeTaskList.sortByNewest(newTaskList);
		        for (Task task : newTaskList) {
		        	System.out.println(task.toString());
		        }
		        
		        
	}
}

