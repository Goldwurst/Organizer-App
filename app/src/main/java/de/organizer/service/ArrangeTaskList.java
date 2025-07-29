package de.organizer.service;


import java.util.Comparator;
import java.util.List;

import de.organizer.model.Task;

public class ArrangeTaskList {
	
	public static void sortByPriority(List<Task> taskList) {
		taskList.sort(Comparator.comparing((Task t) -> t.getPriority().getLevel()));
	}
	
	public static void sortByDueDate(List<Task> taskList) {
		taskList.sort(Comparator.comparing(Task::getDueDate));
	}
	
	public static void sortByTitle(List<Task> taskList) {
		taskList.sort(Comparator.comparing(Task::getTitle, String.CASE_INSENSITIVE_ORDER));
	}
	
	public static void sortByPriorityThenDate(List<Task> taskList) {
		taskList.sort(Comparator
				.comparing((Task t) -> t.getPriority().getLevel())
				.thenComparing(Task::getDueDate));
	}
	
	public static void sortByNewest(List<Task> taskList) {
		taskList.sort(Comparator.comparing(Task::getCreatedAt).reversed());
	}
	
	public static void sortByOldest(List<Task> taskList) {
		taskList.sort(Comparator.comparing(Task::getCreatedAt));
	}
}

