package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EmptyStackException;

import org.junit.Test;

/**
 * Tests ArrayStack.
 * 
 * @author Sam Fishburn, Amin Mohamed, and Cooper Lentz
 */
public class LinkedStackTest {
	/**
	 * Tests LinkedStack.
	 */
	@Test
	public void testLinkedStack() {
		LinkedStack<Integer> testStack = new LinkedStack<Integer>(1);
		
		assertDoesNotThrow(() -> testStack.push(0));
		assertEquals(0, testStack.pop());
		
		testStack.push(0);
		assertThrows(IllegalArgumentException.class, () -> testStack.push(1));
		
		testStack.pop();
		assertThrows(EmptyStackException.class, () -> testStack.pop());
		
		assertTrue(testStack.isEmpty());
		
		assertEquals(0, testStack.size());
		
		assertDoesNotThrow(() -> testStack.setCapacity(2));
		assertDoesNotThrow(() -> testStack.push(0));
		assertDoesNotThrow(() -> testStack.push(1));
		assertThrows(IllegalArgumentException.class, () -> testStack.push(1));
		assertThrows(IllegalArgumentException.class, () -> testStack.setCapacity(1));
	}
}
