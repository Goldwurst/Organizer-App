package de.organizer.util;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class IDGenerator {
	
	private static long currentID = 0;
	//Thread-sichere Map für ID -> Zeitstempel
	private static final Map<Long, LocalDateTime> idTimestamps = Collections.synchronizedMap(new HashMap<>());
	
	//static -> globale Klassenvariable, die für alle Instanzen zählt
	//synchronized -> sichert gegen gleichzeitiges Zugreifen ab
	
	public static synchronized long generateID() {
		
		long id = ++currentID;
		idTimestamps.put(id, LocalDateTime.now());
		return id;
	}
	
	public static LocalDateTime getTimestamp(long id) {
		return idTimestamps.get(id);
	}
	
	public static synchronized void reset() {
		currentID = 0;
		idTimestamps.clear();
	}
}
