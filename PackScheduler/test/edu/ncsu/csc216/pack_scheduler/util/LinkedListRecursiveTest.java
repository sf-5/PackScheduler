/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * the test class for LinkedListRecursive
 */
class LinkedListRecursiveTest {

	/**
	 * Tests the methods of LinkedListRecursive.
	 */
	@Test
	public void testLinkedList() {
		LinkedListRecursive<String> testList = new LinkedListRecursive<String>();
		
		assertDoesNotThrow(() -> testList.add(0, "String2"));
		assertDoesNotThrow(() -> testList.add(0, "String1"));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.add(3, "String0"));
		assertThrows(NullPointerException.class, () -> testList.add(2, null));
		assertDoesNotThrow(() -> testList.add(2, "String3"));
		
		assertEquals("String2", testList.set(1, "String20"));
		
		assertEquals(3, testList.size());
		
		assertEquals("String1", testList.remove(0));
		assertEquals("String3", testList.remove(1));
		
		assertEquals(1, testList.size());
		assertFalse(testList.isEmpty());
		
		assertThrows(IllegalArgumentException.class, () -> testList.add("String20"));
		assertDoesNotThrow(() -> testList.remove(0));
		assertTrue(testList.add("String0"));
		assertTrue(testList.add("String1"));
		
		assertThrows(IllegalArgumentException.class, () -> testList.add(0, "String1"));
		
		assertThrows(IndexOutOfBoundsException.class, () -> testList.get(5));
		assertEquals("String1", testList.get(1));
		
		assertDoesNotThrow(() -> testList.add("String2"));
		assertDoesNotThrow(() -> testList.add("String3"));
		assertDoesNotThrow(() -> testList.add("String4"));
		assertThrows(NullPointerException.class, () -> testList.remove(null));
		assertTrue(testList.remove("String0"));
		assertTrue(testList.remove("String4"));
		
		assertDoesNotThrow(() -> testList.add("String4"));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.remove(5));
		assertEquals("String4", testList.remove(3));
		
		assertThrows(IndexOutOfBoundsException.class, () -> testList.set(5, "String5"));
	}

}
