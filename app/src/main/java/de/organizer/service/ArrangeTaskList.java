package de.organizer.service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import de.organizer.model.Task;

public class ArrangeTaskList {
    
	// Konstruktor -> Klasse uninstanziierbar
	private ArrangeTaskList() {
		throw new AssertionError("Utility-Klasse darf nicht instanziiert werden");
	}
	
    /**
     * Sortiert Tasks nach Prioritätslevel, null-Werte ans Ende.
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByPriority(List<Task> taskList) {
        taskList.sort(by((Task t) -> t.getPriority().getLevel()));
    }
    
    /**
     * Sortiert Tasks nach Kategorie-Label, null-Werte ans Ende.
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByCategory(List<Task> taskList) {
        taskList.sort(by((Task t) -> t.getCategory().getLabel()));
    }
    
    /**
     * Sortiert Tasks nach Fälligkeitsdatum, null-Werte ans Ende.
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByDueDate(List<Task> taskList) {
        taskList.sort(by(Task::getDueDate));
    }
    
    /**
     * Sortiert Tasks nach Titel (case-insensitive).
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByTitle(List<Task> taskList) {
        taskList.sort(Comparator.comparing(Task::getTitle, String.CASE_INSENSITIVE_ORDER));
    }
    
    /**
     * Sortiert Tasks zuerst nach Priorität und dann nach Fälligkeitsdatum.
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByPriorityDueDate(List<Task> taskList) {
        taskList.sort(by((Task t) -> t.getPriority().getLevel())
                .thenComparing(by(Task::getDueDate))
        );
    }
    
    /**
     * Sortiert Tasks zuerst nach Kategorie und dann nach Fälligkeitsdatum.
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByCategoryDueDate(List<Task> taskList) {
        taskList.sort(by((Task t) -> t.getCategory().getLabel())
                .thenComparing(by(Task::getDueDate))
        );
    }
    
    /**
     * Sortiert Tasks nach Erstellzeitpunkt (neuste zuerst).
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByNewest(List<Task> taskList) {
        taskList.sort(
            Comparator.comparing(Task::getCreatedAt, Comparator.nullsFirst(null))
                      .reversed()
        );
    }
    
    /**
     * Sortiert Tasks nach Erstellzeitpunkt (älteste zuerst).
     * @param taskList Liste der zu sortierenden Tasks
     */
    public static void sortByOldest(List<Task> taskList) {
        taskList.sort(by(Task::getCreatedAt));
    }
    
    /**
     * Helper: Comparator für naturalOrder() mit nullsLast().
     *
     * @param <Type> Ein Typ, der Comparable implementiert
     * @return Comparator für Type mit null-Werten am Ende
     */
    private static <Type extends Comparable<? super Type>> Comparator<Type> naturalNullsLast() {
        return Comparator.nullsLast(Comparator.naturalOrder());
    }
    
    /**
     * Helper: Comparator zur Vereinfachung -> inkludiert naturalNullsLast()-Methode.
     *
     * @param <Task> Ein Task, der Comparable implementiert
     * @param keyExtractor -> Schlüssel, nachdem sortiert wird
     * @return Comparator für Task mit null-Werten am Ende
     */
    public static <Type extends Comparable<? super Type>> Comparator<Task> by(
    		Function<Task, Type> keyExtractor) {
    	
    	return Comparator.comparing(keyExtractor, naturalNullsLast());
    }
}

