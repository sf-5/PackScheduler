/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.course.Course;

/**
 * the class that tests the Schedule class to ensure complete implementation
 */
class ScheduleTest {

	/**
	 * tests the constructor for schedule
	 */
	@Test
	void testSchedule() {

		Schedule schedule = new Schedule();

		assertEquals("My Schedule", schedule.getTitle());
		assertEquals(0, schedule.getScheduledCourses().length);
	}

	/**
	 * tests the addCourseToSchedule method
	 */
	@Test
	void testAddCourseToSchedule() {

		Schedule schedule = new Schedule();
		schedule.addCourseToSchedule(
				new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100));
		schedule.addCourseToSchedule(
				new Course("CSC216", "Intro to Programming - Java", "002", 3, "spbalik", 10, "MW", 1120, 1310));
		schedule.addCourseToSchedule(
				new Course("CSC316", "Intro to Programming - Java", "003", 3, "tbdimitr", 10, "TH", 1120, 1310));
		assertEquals(3, schedule.getScheduledCourses().length);
		assertEquals("CSC316", schedule.getScheduledCourses()[2][0]);

		assertThrows(IllegalArgumentException.class, () -> {
			schedule.addCourseToSchedule(
					new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100));
		});

		assertThrows(IllegalArgumentException.class, () -> {
			schedule.addCourseToSchedule(
					new Course("CSC416", "Intro to Programming - Java", "002", 3, "jtking", 10, "TH", 1010, 1130));
		});
	}

	/**
	 * tests the removeCourseFromSchedule
	 */
	@Test
	void testRemoveCourseFromSchedule() {
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100);
		Course c2 = new Course("CSC216", "Intro to Programming - Java", "002", 3, "spbalik", 10, "MW", 1120, 1310);
		Course c3 = new Course("CSC316", "Intro to Programming - Java", "003", 3, "tbdimitr", 10, "TH", 1120, 1310);
		schedule.addCourseToSchedule(c1);
		schedule.addCourseToSchedule(c2);
		schedule.addCourseToSchedule(c3);

		schedule.removeCourseFromSchedule(c1);
		assertEquals(2, schedule.getScheduledCourses().length);
		assertEquals("CSC216", schedule.getScheduledCourses()[0][0]);
		schedule.removeCourseFromSchedule(c2);
		assertEquals(1, schedule.getScheduledCourses().length);
		assertEquals("CSC316", schedule.getScheduledCourses()[0][0]);

		assertFalse(schedule.removeCourseFromSchedule(c2));
		assertFalse(schedule.removeCourseFromSchedule(null));
	}

	/**
	 * tests the resetSchedule method
	 */
	@Test
	void testResetSchedule() {
		Schedule schedule = new Schedule();
		schedule.addCourseToSchedule(
				new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100));
		schedule.addCourseToSchedule(
				new Course("CSC216", "Intro to Programming - Java", "002", 3, "spbalik", 10, "MW", 1120, 1310));
		schedule.addCourseToSchedule(
				new Course("CSC316", "Intro to Programming - Java", "003", 3, "tbdimitr", 10, "TH", 1120, 1310));

		schedule.resetSchedule();
		assertEquals(0, schedule.getScheduledCourses().length);
		assertEquals("My Schedule", schedule.getTitle());
	}

	/**
	 * tests the getScheduledCourses method
	 */
	@Test
	void testGetScheduledCourses() {
		Schedule schedule = new Schedule();
		schedule.addCourseToSchedule(
				new Course("CSC116", "Intro to Programming - Java", "001", 3, "jdyoung2", 10, "MW", 910, 1100));
		schedule.addCourseToSchedule(
				new Course("CSC216", "Intro to Programming - Java", "002", 3, "spbalik", 10, "MW", 1120, 1310));
		schedule.addCourseToSchedule(
				new Course("CSC316", "Intro to Programming - Java", "003", 3, "tbdimitr", 10, "TH", 1120, 1310));

		assertEquals("CSC116", schedule.getScheduledCourses()[0][0]);
		assertEquals("CSC216", schedule.getScheduledCourses()[1][0]);
		assertEquals("CSC316", schedule.getScheduledCourses()[2][0]);
		assertEquals("001", schedule.getScheduledCourses()[0][1]);
		assertEquals("002", schedule.getScheduledCourses()[1][1]);
		assertEquals("003", schedule.getScheduledCourses()[2][1]);
		assertEquals("Intro to Programming - Java", schedule.getScheduledCourses()[0][2]);
	}
	
	/**
	 * tests the setTitle method
	 */
	@Test
	void testSetTitle() {
		Schedule schedule = new Schedule();
		assertEquals("My Schedule", schedule.getTitle());
		schedule.setTitle("New Title");
		assertEquals("New Title", schedule.getTitle());
		
		assertThrows(IllegalArgumentException.class, () -> {
			schedule.setTitle(null);
		});
	}
	
	/**
	 * tests the canAdd() method
	 */
	@Test
	void testCanAdd() {
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Course c2 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "sesmith5", 10, "MW", 1330, 1445);

		assertTrue(schedule.canAdd(c1));
		schedule.addCourseToSchedule(c1);
		assertFalse(schedule.canAdd(c1));
		assertFalse(schedule.canAdd(c2));
		assertFalse(schedule.canAdd(null));
	}
	
	/**
	 * tests the getScheduleCredits() method
	 */
	@Test
	void testGetScheduleCredits() {
		Schedule schedule = new Schedule();
		Course c1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Course c2 = new Course("CSC316", "Data Structures and Algorithms", "001", 3, "sesmith5", 10, "MW", 1446, 1500);
		
		assertEquals(0, schedule.getScheduleCredits());
		schedule.addCourseToSchedule(c1);
		assertEquals(3, schedule.getScheduleCredits());
		schedule.addCourseToSchedule(c2);
		assertEquals(6, schedule.getScheduleCredits());
	}
}
