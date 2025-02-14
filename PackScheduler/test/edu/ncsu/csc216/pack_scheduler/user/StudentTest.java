/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * testing class for the Student class
 * @author cllentz
 */
public class StudentTest {
	
	/** Student first name*/
	private static final String FIRST_NAME = "Zahir";
	/** Student last name */
	private static final String LAST_NAME = "King";
	/** Student ID */
	private static final String ID = "zking";
	/** Student email */
	private static final String EMAIL = "orci.Donec@ametmassaQuisque.com";
	/** Student password */
	private static final String PASSWORD = "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=";
	/** Student max credits	 */
	private static final int MAX_CREDITS = 15;

	/**
	 * Testing constructing student objects, one with default max credits and one without
	 */
	@Test
	public void testStudentValid() {
		Student s1 = assertDoesNotThrow(
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS),
				"Should not throw exception");
		Student s2 = assertDoesNotThrow(
				() -> new Student (FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD),
				"Should not throw exception");
		
		assertEquals("Zahir", s1.getFirstName());
		assertEquals("King", s1.getLastName());
		assertEquals("zking", s1.getId());
		assertEquals("orci.Donec@ametmassaQuisque.com", s1.getEmail());
		assertEquals("MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", s1.getPassword());
		assertEquals(15, s1.getMaxCredits());
		assertEquals(18, s2.getMaxCredits());
	}
	/**
	 * Testing parameters of the student class to ensure successful construction
	 */
	@Test
	
	public void testStudentInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Student("", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid first name", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, "", ID, EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid last name", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_CREDITS));
		assertEquals("Invalid id", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, "bob.jones@com", PASSWORD, MAX_CREDITS));
		assertEquals("Invalid email", e4.getMessage());
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "", MAX_CREDITS));
		assertEquals("Invalid password", e5.getMessage());
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 25));
		assertEquals("Invalid max credits", e6.getMessage());
		
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#hashCode()}.
	 */
	@Test
	void testHashCode() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s3 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		Student s4 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different");
		Student s7 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD);
		
		
		// Test for the same hash code for the same values
		assertEquals(s1.hashCode(), s2.hashCode());
		
		// Test for each of the fields
		assertNotEquals(s1.hashCode(), s3.hashCode());
		assertNotEquals(s1.hashCode(), s4.hashCode());
		assertNotEquals(s1.hashCode(), s5.hashCode());
		assertNotEquals(s1.hashCode(), s6.hashCode());
		assertNotEquals(s1.hashCode(), s7.hashCode());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setMaxCredits(int)}.
	 */
	@Test
	void testSetMaxCredits() {
		//Construct a valid Student
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setMaxCredits(2));
		assertEquals("Invalid max credits", e1.getMessage()); //Check correct exception message
		assertEquals(18, s.getMaxCredits()); //Check that max credits didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setPassword(java.lang.String)}.
	 */
	@Test
	void testSetPassword() {
		//Construct a valid Student
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals("hashedpassword", s.getPassword()); //Check that password didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setEmail(java.lang.String)}.
	 */
	@Test
	void testSetEmail() {
		//Construct a valid Student
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setEmail("email.ncsu@edu"));
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals("email@ncsu.edu", s.getEmail()); //Check that email didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setLastName(java.lang.String)}.
	 */
	@Test
	void testSetLastName() {
		//Construct a valid Student
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals("last", s.getLastName()); //Check that last  name didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#setFirstName(java.lang.String)}.
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Student
		Student s = new Student("first", "last", "id", "email@ncsu.edu", "hashedpassword");
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> s.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		assertEquals("first", s.getFirstName()); //Check that first name didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s3 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD);
		Student s4 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different");
		Student s7 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD);
		
		// Test for equality in both directions
		assertEquals(s1, s2);
		assertEquals(s2, s1);
		
		// Test of each of the fields
		assertNotEquals(s1, s3);
		assertNotEquals(s1, s4);
		assertNotEquals(s1, s5);
		assertNotEquals(s1, s6);
		assertNotEquals(s1, s7);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Student#toString()}.
	 */
	@Test
	void testToString() {
		// Compare string with expected toString() output
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		assertEquals("Zahir,King,zking,orci.Donec@ametmassaQuisque.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,15", s1.toString());
	}
	/**
	 * tests the overridden compareTo method in the Student class
	 * Students must be ordered by first name, then last name, then unity ID
	 */
	@Test
	void testCompareTo() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s2 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Student s3 = new Student("Xavier", LAST_NAME, ID, EMAIL, PASSWORD);
		Student s4 = new Student(FIRST_NAME, "Jackson", ID, EMAIL, PASSWORD);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "zkinn", EMAIL, PASSWORD);
		
		assertEquals(s1.compareTo(s2), 0);
		assertEquals(s2.compareTo(s1), 0);
		assertTrue(s1.compareTo(s3) > 0);
		assertTrue(s3.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s4) > 0);
		assertTrue(s4.compareTo(s1) < 0);
		assertTrue(s1.compareTo(s5) < 0);
		assertTrue(s5.compareTo(s1) > 0);
	}
	
	/**
	 * tests the canAdd() method
	 */
	@Test
	void testCanAdd() {
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		Course c1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		
		assertTrue(s1.canAdd(c1));
		s1.getSchedule().addCourseToSchedule(c1);
		assertFalse(s1.canAdd(c1));
	}
}
