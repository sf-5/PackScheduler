package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ListIterator;

import org.junit.Test;

/**
 * Tests the custom LinkedList class.
 * 
 * @author Sam Fishburn, Amin Mohamed, and Cooper Lentz
 */
public class LinkedListTest {
	/**
	 * Tests the methods of LinkedList.
	 */
	@Test
	public void testLinkedList() {
		LinkedList<Integer> testList = new LinkedList<Integer>();
		
		assertDoesNotThrow(() -> testList.add(0, 2));
		assertDoesNotThrow(() -> testList.add(0, 1));
		assertThrows(IndexOutOfBoundsException.class, () -> testList.add(3, 0));
		assertThrows(NullPointerException.class, () -> testList.add(2, null));
		assertDoesNotThrow(() -> testList.add(2, 3));
		
		assertEquals(2, testList.set(1, 20));
		assertThrows(NullPointerException.class, () -> testList.set(2, null));
		
		assertEquals(3, testList.size());
		
		assertEquals(1, testList.remove(0));
		assertEquals(3, testList.remove(1));
		
		assertEquals(1, testList.size());
		
		ListIterator<Integer> iterator = testList.listIterator(0);
		
		assertTrue(iterator.hasNext());
		assertEquals(20, iterator.next());
		assertTrue(iterator.hasPrevious());
		assertEquals(20, iterator.previous());
	}
}
