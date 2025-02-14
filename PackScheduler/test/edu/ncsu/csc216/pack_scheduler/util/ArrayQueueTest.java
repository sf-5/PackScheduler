/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;

/**
 * tests the ArrayQueue class
 */
class ArrayQueueTest {
	
	/**
	 * the test method for the ArrayQueue test
	 */
	@Test
	void testArrayQueue() {
		
		ArrayQueue<String> queue = new ArrayQueue<String>(5);
		
		queue.enqueue("String1");
		
		assertEquals("String1", queue.dequeue());
		assertThrows(NoSuchElementException.class, () -> {
			queue.dequeue();
		});
		
		queue.enqueue("String1");
		queue.enqueue("String2");
		queue.enqueue("String3");
		queue.enqueue("String4");
		queue.enqueue("String5");
		
		assertThrows(IllegalArgumentException.class, () -> {
			queue.enqueue("String6");
		});
		
		assertEquals("String1", queue.dequeue());
		assertEquals("String2", queue.dequeue());
		assertEquals("String3", queue.dequeue());
		assertEquals("String4", queue.dequeue());
		assertEquals("String5", queue.dequeue());
		
		assertEquals(0, queue.size());
		assertTrue(queue.isEmpty());
		
		queue.enqueue("String1");
		queue.enqueue("String2");
		queue.enqueue("String3");
		
		queue.setCapacity(queue.size());
		
		assertThrows(IllegalArgumentException.class, () -> {
			queue.enqueue("String4");
		});
		
		assertEquals(3, queue.size());
		
		assertThrows(IllegalArgumentException.class, () -> {
			queue.setCapacity(-1);
		});
	}

}
