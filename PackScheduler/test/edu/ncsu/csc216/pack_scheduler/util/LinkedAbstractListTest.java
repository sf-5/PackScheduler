/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * the class that tests the LinkedAbstractList class
 */
class LinkedAbstractListTest {

	/**
	 * tests the LinkedAbstractList constructor
	 * ensures that the LinkedAbstractList is empty upon construction
	 */
	@Test
	void testConstructor() {
		
		LinkedAbstractList<String> linkedAbstractList = new LinkedAbstractList<String>(10);
		assertEquals(0, linkedAbstractList.size());
	}
	
	/**
	 * tests the add() method
	 */
	@Test
	void testAdd() {
		
		// tests adding 
		LinkedAbstractList<String> linkedAbstractList = new LinkedAbstractList<String>(11);
		linkedAbstractList.add(0, "String1");
		assertEquals(1, linkedAbstractList.size());
		assertEquals("String1", linkedAbstractList.get(0));
		linkedAbstractList.add(1, "String2");
		assertEquals("String2", linkedAbstractList.get(1));
		
		linkedAbstractList.add(0, "String0.5");
		assertEquals(3, linkedAbstractList.size());
		assertEquals("String0.5", linkedAbstractList.get(0));
		
		linkedAbstractList.add(2, "String1.5");
		assertEquals(4, linkedAbstractList.size());
		assertEquals("String0.5", linkedAbstractList.get(0));
		assertEquals("String1", linkedAbstractList.get(1));
		assertEquals("String1.5", linkedAbstractList.get(2));
		assertEquals("String2", linkedAbstractList.get(3));
		
		linkedAbstractList.add(4, "String3");
		assertEquals(5, linkedAbstractList.size());
		assertEquals("String3", linkedAbstractList.get(4));
		
		// tests exceptions
		assertThrows(NullPointerException.class, () -> {
			linkedAbstractList.add(3, null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			linkedAbstractList.add(2, "String1");
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedAbstractList.add(1000, "String");
		});
		
		// tests growing
		linkedAbstractList.add(5, "String 5");
		linkedAbstractList.add(6, "String 6");
		linkedAbstractList.add(7, "String 7");
		linkedAbstractList.add(8, "String 8");
		linkedAbstractList.add(9, "String 9");
		linkedAbstractList.add(10, "String 10");
		assertEquals(11, linkedAbstractList.size());
	}
	
	/**
	 * Tests removing an element from an ArrayList
	 */
	@Test
	void testForRemovingAnElement() {
		LinkedAbstractList<String> linkedAbstractList = new LinkedAbstractList<String>(10);
		linkedAbstractList.add(0, "String1");
		assertEquals(1, linkedAbstractList.size());
		assertEquals("String1", linkedAbstractList.get(0));
		linkedAbstractList.add(1, "String2");
		assertEquals("String2", linkedAbstractList.get(1));
		
		linkedAbstractList.remove(0);
		assertEquals(1, linkedAbstractList.size());
		
		linkedAbstractList.add(1, "String 3");
		linkedAbstractList.add(2, "String 4");
		linkedAbstractList.add(3, "String 5");
		linkedAbstractList.add(4, "String 6");
		
		linkedAbstractList.remove(2);
		assertEquals("String 5", linkedAbstractList.get(2));
		linkedAbstractList.remove(3);
		assertEquals(3, linkedAbstractList.size());
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedAbstractList.remove(5);
		});
		
//		assertThrows(IndexOutOfBoundsException.class, () -> {
//			linkedAbstractList.remove(-1);
//		});
	}
	
	/**
	 * Tests setting an element in an ArrayList
	 */
	@Test
	void testSet() {
		
		LinkedAbstractList<String> linkedAbstractList = new LinkedAbstractList<String>(10);
		
		linkedAbstractList.add(0, "String 1");
		linkedAbstractList.add(1, "String 2.5");
		linkedAbstractList.add(2, "String 3");
		assertEquals("String 2.5", linkedAbstractList.set(1, "String 2"));
		assertEquals(3, linkedAbstractList.size());
		assertEquals("String 2", linkedAbstractList.get(1));
		
		assertThrows(NullPointerException.class, () -> {
			linkedAbstractList.set(0, null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			linkedAbstractList.set(2, "String 1");
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedAbstractList.set(1000, "String");
		});
	}
	
	/**
	 * Tests getting an element from an ArrayList; ensures an incorrect index throws an exception
	 */
	@Test
	void testGet() {
		
		LinkedAbstractList<String> linkedAbstractList = new LinkedAbstractList<String>(10);
		
		linkedAbstractList.add(0, "String 1");
		linkedAbstractList.add(1, "String 2.5");
		linkedAbstractList.add(2, "String 3");
		
		assertEquals("String 3", linkedAbstractList.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedAbstractList.get(3);
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			linkedAbstractList.get(-1);
		});
	}

}
