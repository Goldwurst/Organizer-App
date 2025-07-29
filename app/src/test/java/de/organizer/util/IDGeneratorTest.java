package de.organizer.util;


import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IDGeneratorTest {

	//Setup-Block vor jedem Test
	@BeforeEach
	void resetBeforeEachTest() {
		IDGenerator.reset();
	}
	
	//Test 1: Ist die erste generierte ID 1?
	@Test
	void testFirstGeneratedIdIsOne() {
		long id = IDGenerator.generateID();
		assertEquals(1L, id, "Die erste generierte ID sollte 1 sein");
	}
	
	//Test 2: Erhöhen sich die IDs um 1?
	@Test
	void testIdsAreSequentiel() {
		long id1 = IDGenerator.generateID();
		long id2 = IDGenerator.generateID();
		long id3 = IDGenerator.generateID();
		
		assertEquals(1L, id1, "Die erste generierte ID sollte 1 sein");
		assertEquals(2L, id2, "Die erste generierte ID sollte 2 sein");
		assertEquals(3L, id3, "Die erste generierte ID sollte 3 sein");
	}
}

