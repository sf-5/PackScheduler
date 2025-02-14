/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;

/**
 * Test cases for CourseRoll class
 */
class CourseRollTest {
	
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
	 * Test for Constructor
	 */
	@Test
	void testForConstructor() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(10, c);
		assertEquals(10, roll.getEnrollmentCap());
		assertEquals(10, roll.getOpenSeats());
		
		assertThrows(IllegalArgumentException.class, () -> {
			new CourseRoll(10, null);
		});
	}
	/**
	 * Test for setEnrollmentCap method
	 */
	@Test
	void testForSetEnrollmentCap() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(50, c);
		roll.setEnrollmentCap(100);
		assertEquals(100, roll.getEnrollmentCap());
		assertEquals(100, roll.getOpenSeats());
		assertThrows(IllegalArgumentException.class, () -> roll.setEnrollmentCap(500));
		roll.setEnrollmentCap(10);
		roll.enroll(new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, LAST_NAME, ID, "different.bob@gmail.com", PASSWORD, MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 14));
		roll.enroll(new Student("Lane", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student(FIRST_NAME, "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student("Lane", "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS));
		roll.enroll(new Student("Lane", "Bob", ID, EMAIL, PASSWORD, MAX_CREDITS));
		
		assertEquals(1, roll.getNumberOnWaitlist());
		
		assertThrows(IllegalArgumentException.class, () -> {
			roll.enroll(new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS));
		});
	}
	
	/**
	 * tests the enroll() method
	 */
	@Test
	void testEnroll() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(10, c);
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		

		roll.enroll(s1);
		assertEquals(9, roll.getOpenSeats());
		roll.enroll(s2);
		assertEquals(8, roll.getOpenSeats());
		
		assertThrows(IllegalArgumentException.class, () -> {
			roll.enroll(null);
		});
		
		assertThrows(IllegalArgumentException.class, () -> {
			roll.enroll(s3);
		});
	}
	
	/**
	 * tests the canEnroll() method
	 */
	@Test
	void testCanEnroll() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(10, c);
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s4 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CREDITS);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, "different.bob@gmail.com", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CREDITS);
		Student s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 14);
		Student s9 = new Student("Lane", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s10 = new Student(FIRST_NAME, "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s11 = new Student("Lane", "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS);
		
		roll.enroll(s1);
		roll.enroll(s2);
		
		assertFalse(roll.canEnroll(s3));
		
		roll.enroll(s4);
		roll.enroll(s5);
		roll.enroll(s6);
		roll.enroll(s7);
		roll.enroll(s8);
		roll.enroll(s9);
		roll.enroll(s10);
		roll.enroll(s11);
		
	}
	
	/**
	 * Tests waitlist functionality
	 */
	@Test
	void testWaitlist() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(10, c);
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s4 = new Student("different", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s5 = new Student(FIRST_NAME, LAST_NAME, "different", EMAIL, PASSWORD, MAX_CREDITS);
		Student s6 = new Student(FIRST_NAME, LAST_NAME, ID, "different.bob@gmail.com", PASSWORD, MAX_CREDITS);
		Student s7 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, "different", MAX_CREDITS);
		Student s8 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, 14);
		Student s9 = new Student("Lane", LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s10 = new Student(FIRST_NAME, "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s11 = new Student("Lane", "Berg", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s12 = new Student("Amin", "Mohamed", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s13 = new Student("Luke", "Skywalker", ID, EMAIL, PASSWORD, MAX_CREDITS);
		
		roll.enroll(s1);
		roll.enroll(s2);		
		roll.enroll(s4);
		roll.enroll(s5);
		roll.enroll(s6);
		roll.enroll(s7);
		roll.enroll(s8);
		roll.enroll(s9);
		roll.enroll(s10);
		roll.enroll(s11);
		roll.enroll(s12);
		roll.enroll(s13);
		assertEquals(2, roll.getNumberOnWaitlist());
		roll.drop(s13);
		assertEquals(1, roll.getNumberOnWaitlist());
	}
	
	/**
	 * tests the drop() method
	 */
	@Test
	void testDrop() {
		Course c = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1300, 1445);
		CourseRoll roll = new CourseRoll(10, c);
		Student s1 = new Student(FIRST_NAME, LAST_NAME, ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s2 = new Student(FIRST_NAME, "different", ID, EMAIL, PASSWORD, MAX_CREDITS);
		Student s3 = new Student("Lane", "Berg", "lberg", "bob@gmail.com", PASSWORD, MAX_CREDITS);
		
		roll.enroll(s1);
		roll.enroll(s2);
		
		roll.drop(s1);
		
		assertEquals(9, roll.getOpenSeats());
		
		assertThrows(IllegalArgumentException.class, () -> {
			roll.drop(null);
		});
		
		assertDoesNotThrow(() -> {
			roll.drop(s3);
		});
	}
}
