/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/**
 * tests the Faculty class
 */
class FacultyTest {

	/** Faculty member first name*/
	private static final String FIRST_NAME = "Zahir";
	/** Faculty member last name */
	private static final String LAST_NAME = "King";
	/** Faculty member ID */
	private static final String ID = "zking";
	/** Faculty member email */
	private static final String EMAIL = "orci.Donec@ametmassaQuisque.com";
	/** Faculty member password */
	private static final String PASSWORD = "MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=";
	/** Faculty member max courses	 */
	private static final int MAX_COURSES = 2;

	/**
	 * Testing constructing student objects, one with default max credits and one without
	 */
	@Test
	public void testFacultyValid() {
		Faculty f1 = assertDoesNotThrow(
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES),
				"Should not throw exception");
		
		assertEquals("Zahir", f1.getFirstName());
		assertEquals("King", f1.getLastName());
		assertEquals("zking", f1.getId());
		assertEquals("orci.Donec@ametmassaQuisque.com", f1.getEmail());
		assertEquals("MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=", f1.getPassword());
		assertEquals(2, f1.getMaxCourses());
	}
	/**
	 * Testing parameters of the student class to ensure successful construction
	 */
	@Test
	
	public void testStudentInvalid() {
		Exception e1 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty("", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES));
		assertEquals("Invalid first name", e1.getMessage());
		
		Exception e2 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, "", ID, EMAIL, PASSWORD, MAX_COURSES));
		assertEquals("Invalid last name", e2.getMessage());
		
		Exception e3 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, "", EMAIL, PASSWORD, MAX_COURSES));
		assertEquals("Invalid id", e3.getMessage());
		
		Exception e4 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, "bob.jones@com", PASSWORD, MAX_COURSES));
		assertEquals("Invalid email", e4.getMessage());
		
		Exception e5 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "", MAX_COURSES));
		assertEquals("Invalid password", e5.getMessage());
		
		Exception e6 = assertThrows(IllegalArgumentException.class,
				() -> new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 25));
		assertEquals("Invalid max courses", e6.getMessage());
		
	}
	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#hashCode()}.
	 */
	@Test
	void testHashCode() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f3 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f4 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f5 = new Faculty(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD, MAX_COURSES);
		Faculty f6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_COURSES);
		Faculty f7 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		
		
		// Test for the same hash code for the same values
		assertEquals(f1.hashCode(), f2.hashCode());
		
		// Test for each of the fields
		assertNotEquals(f1.hashCode(), f3.hashCode());
		assertNotEquals(f1.hashCode(), f4.hashCode());
		assertNotEquals(f1.hashCode(), f5.hashCode());
		assertNotEquals(f1.hashCode(), f6.hashCode());
		assertNotEquals(f1.hashCode(), f7.hashCode());
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setMaxCredits(int)}.
	 */
	@Test
	void testSetMaxCourses() {
		//Construct a valid Faculty Member
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setMaxCourses(10));
		assertEquals("Invalid max courses", e1.getMessage()); //Check correct exception message
		assertEquals(2, f.getMaxCourses()); //Check that max credits didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setPassword(java.lang.String)}.
	 */
	@Test
	void testSetPassword() {
		//Construct a valid Faculty member
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setPassword(null));
		assertEquals("Invalid password", e1.getMessage()); //Check correct exception message
		assertEquals("hashedpassword", f.getPassword()); //Check that password didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setEmail(java.lang.String)}.
	 */
	@Test
	void testSetEmail() {
		//Construct a valid Faculty member
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setEmail("email.ncsu@edu"));
		assertEquals("Invalid email", e1.getMessage()); //Check correct exception message
		assertEquals("email@ncsu.edu", f.getEmail()); //Check that email didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setLastName(java.lang.String)}.
	 */
	@Test
	void testSetLastName() {
		//Construct a valid Faculty member
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setLastName(null));
		assertEquals("Invalid last name", e1.getMessage()); //Check correct exception message
		assertEquals("last", f.getLastName()); //Check that last  name didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#setFirstName(java.lang.String)}.
	 */
	@Test
	void testSetFirstName() {
		//Construct a valid Faculty member
		Faculty f = new Faculty("first", "last", "id", "email@ncsu.edu", "hashedpassword", MAX_COURSES);
		Exception e1 = assertThrows(IllegalArgumentException.class,
						() -> f.setFirstName(null));
		assertEquals("Invalid first name", e1.getMessage()); //Check correct exception message
		assertEquals("first", f.getFirstName()); //Check that first name didn't change
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#equals(java.lang.Object)}.
	 */
	@Test
	void testEqualsObject() {
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f2 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f3 = new Faculty(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_COURSES);
		Faculty f4 = new Faculty(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_COURSES);
		Faculty f5 = new Faculty(FIRST_NAME, LAST_NAME, ID, "different@ncsu.edu", PASSWORD, MAX_COURSES);
		Faculty f6 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_COURSES);
		Faculty f7 = new Faculty("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD);
		
		// Test for equality in both directions
		assertEquals(f1, f2);
		assertEquals(f2, f1);
		assertEquals(f1, f1);
		
		// Test of each of the fields
		assertNotEquals(f1, f3);
		assertNotEquals(f1, f4);
		assertNotEquals(f1, f5);
		assertNotEquals(f1, f6);
		assertNotEquals(f1, f7);
		assertNotEquals(f1, s1);
	}

	/**
	 * Test method for {@link edu.ncsu.csc216.pack_scheduler.user.Faculty#toString()}.
	 */
	@Test
	void testToString() {
		// Compare string with expected toString() output
		Faculty f1 = new Faculty(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_COURSES);
		assertEquals("Zahir,King,zking,orci.Donec@ametmassaQuisque.com,MMlS+rEiw/l1nwKm2Vw3WLJGtP7iOZV7LU/uRuJhcMQ=,2", f1.toString());
	}
}