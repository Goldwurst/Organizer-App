package de.organizer.util;

public class IDGenerator {
	
	private static long currentID = 0;
	
	//static -> globale Klassenvariable, die für alle Instanzen zählt
	//synchronized -> sichert gegen gleichzeitiges Zugreifen ab
	
	public static synchronized long generateID() {
		return ++currentID;
	}
	
	public static synchronized void reset() {
		currentID = 0;
	}
}
