/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * tests the ArrayList class
 */
class ArrayListTest {
	
	/**
	 * tests the ArrayList constructor
	 * ensures that the ArrayList is empty upon construction
	 */
	@Test
	void testConstructor() {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		assertEquals(0, arrayList.size());
	}
	
	/**
	 * tests the add() method
	 */
	@Test
	void testAdd() {
		
		// tests adding 
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(0, "String1");
		assertEquals(1, arrayList.size());
		assertEquals("String1", arrayList.get(0));
		arrayList.add(1, "String2");
		assertEquals("String2", arrayList.get(1));
		
		arrayList.add(0, "String0.5");
		assertEquals("String0.5", arrayList.get(0));
		assertEquals(3, arrayList.size());
		
		arrayList.add(2, "String1.5");
		assertEquals(4, arrayList.size());
		assertEquals("String0.5", arrayList.get(0));
		assertEquals("String1", arrayList.get(1));
		assertEquals("String1.5", arrayList.get(2));
		assertEquals("String2", arrayList.get(3));
		
		arrayList.add(4, "String3");
		assertEquals(5, arrayList.size());
		assertEquals("String3", arrayList.get(4));
		
		// tests exceptions
		assertThrows(NullPointerException.class, () -> {
			arrayList.add(3, null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			arrayList.add(2, "String1");
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.add(1000, "String");
		});
		
		// tests growing
		arrayList.add(5, "String 5");
		arrayList.add(6, "String 6");
		arrayList.add(7, "String 7");
		arrayList.add(8, "String 8");
		arrayList.add(9, "String 9");
		arrayList.add(10, "String 10");
		assertEquals(11, arrayList.size());
	}
	
	/**
	 * Tests removing an element from an ArrayList
	 */
	@Test
	void testForRemovingAnElement() {
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add(0, "String1");
		assertEquals(1, arrayList.size());
		assertEquals("String1", arrayList.get(0));
		arrayList.add(1, "String2");
		assertEquals("String2", arrayList.get(1));
		
		arrayList.remove(0);
		assertEquals(1, arrayList.size());
		
		arrayList.add(1, "String 3");
		arrayList.add(2, "String 4");
		arrayList.add(3, "String 5");
		
		arrayList.remove(2);
		assertEquals("String 5", arrayList.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.remove(4);
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.remove(-1);
		});
	}
	
	/**
	 * Tests setting an element in an ArrayList
	 */
	@Test
	void testSet() {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		arrayList.add(0, "String 1");
		arrayList.add(1, "String 2.5");
		arrayList.add(2, "String 3");
		assertEquals("String 2.5", arrayList.set(1, "String 2"));
		assertEquals(3, arrayList.size());
		assertEquals("String 2", arrayList.get(1));
		
		assertThrows(NullPointerException.class, () -> {
			arrayList.set(0, null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			arrayList.set(2, "String 1");
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.set(1000, "String");
		});
	}
	
	/**
	 * Tests getting an element from an ArrayList; ensures an incorrect index throws an exception
	 */
	@Test
	void testGet() {
		
		ArrayList<String> arrayList = new ArrayList<String>();
		
		arrayList.add(0, "String 1");
		arrayList.add(1, "String 2.5");
		arrayList.add(2, "String 3");
		
		assertEquals("String 3", arrayList.get(2));
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.get(3);
		});
		
		assertThrows(IndexOutOfBoundsException.class, () -> {
			arrayList.get(-1);
		});
	}
}
