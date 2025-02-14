package edu.ncsu.csc216.pack_scheduler.user.schedule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;

/**
 * Tests the Schedule object.
 * @author Sarah Heckman
 */
public class FacultyScheduleTest {

	/** Course catalog */
	private CourseCatalog catalog;

	/**
	 * Resets course_records.txt for use in other tests.
	 * @throws Exception thrown if there is an error
	 */
	@Before
	public void setUp() throws Exception {
		RegistrationManager.getInstance().clearData();
		
		//Reset course_records.txt so that it's fine for other needed tests
		Path sourcePath = FileSystems.getDefault().getPath("test-files", "starter_course_records.txt");
		Path destinationPath = FileSystems.getDefault().getPath("test-files", "course_records.txt");
		try {
			Files.deleteIfExists(destinationPath);
			Files.copy(sourcePath, destinationPath);
		} catch (IOException e) {
			fail("Unable to reset files");
		}
		
		catalog = new CourseCatalog();
		catalog.loadCoursesFromFile("test-files/course_records.txt");
	}
	
	/**
	 * Tests FacultySchedule().
	 */
	@Test
	public void testFacultySchedule() {
		FacultySchedule schedule = new FacultySchedule("sesmith5");
		assertEquals("Schedule should start with a length of 0, but did not.", 0, schedule.getScheduledCourses().length);	
		assertEquals("Schedule should start with zero courses, but did not.", 0, schedule.getNumScheduledCourses());
	}
	
	/**
	 * Test Schedule.addCourseToSchedule().
	 */
	@Test
	public void testAddCourseToSchedule() {
//		FacultySchedule schedule = new FacultySchedule("sesmith5");
		Faculty f = new Faculty("Sarah", "Heckman", "sesmith5", "sesmith5@ncsu.edu", "pw", 2);
		FacultySchedule schedule = f.getSchedule();
		
		//Add course to schedule
		Course course = catalog.getCourseFromCatalog("CSC216", "001");
		schedule.addCourseToSchedule(course);
		String [][] actSchedule = schedule.getScheduledCourses();
		assertEquals("Added CSC216-001 to schedule.  Length of getScheduledCourses() should be 1, but was not.", 1, actSchedule.length);
		assertEquals("Added CSC216-001 to schedule.  Value at [0][0] should be CSC216, but was not.", "CSC216", actSchedule[0][0]);
		assertEquals("Added CSC216-001 to schedule.  Value at [0][1] should be 001, but was not.", "001", actSchedule[0][1]);
		assertEquals("Added CSC216-001 to schedule.  Value at [0][2] should be Software Development Fundamentals, but was not.", "Software Development Fundamentals", actSchedule[0][2]);
		assertEquals("Added CSC216-001 to schedule.  Value at [0][3] should be TH 1:30PM-2:45PM, but was not.", "TH 1:30PM-2:45PM", actSchedule[0][3]);
		assertEquals("Added CSC216-001 to schedule.  getNumScheduledCourses() should return 1, but did not.", 1, schedule.getNumScheduledCourses());
		assertEquals("Added CSC216-001 to schedule. Instructor should now be sesmith5, but was not.", "sesmith5", course.getInstructorId());
		assertFalse("After adding 1 course when max courses is 2, faculty is not overloaded, but returned true", f.isOverloaded());
		
//		//Attempt to add a duplicate course
//		try {
//			Course csc216002 = catalog.getCourseFromCatalog("CSC216", "002");
//			schedule.addCourseToSchedule(csc216002);
//			fail("Added CSC216-001 to schedule.  Cannot add CSC216-002 to schedule, but was able to.");
//		} catch (IllegalArgumentException e) {
//			assertEquals("Already assigned CSC216", e.getMessage());
//			assertEquals("Added CSC216-001 to schedule.  getNumScheduledCourses() should return 1, but did not.", 1, schedule.getNumScheduledCourses());
//		}
		
		//Attempt to add a null course
		try {
			schedule.addCourseToSchedule(null);
			fail("Added null to schedule.  Cannot add null to schedule, but was able to.");
		} catch (NullPointerException e) {
			assertEquals("Added CSC216-001 to schedule. Attempting to add a null course shouldn't change the schedule. Length of getScheduledCourses() should be 1, but was not.", 1, actSchedule.length);
			assertEquals("Added CSC216-001 to schedule.  getNumScheduledCourses() should return 1, but did not.", 1, schedule.getNumScheduledCourses());
		}
		
		//Add another course
		Course csc226 = catalog.getCourseFromCatalog("CSC226", "001");
		schedule.addCourseToSchedule(csc226);
		actSchedule = schedule.getScheduledCourses();
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Length of getScheduledCourses() should be 2, but was not.", 2, actSchedule.length);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [0][0] should be CSC216, but was not.", "CSC216", actSchedule[0][0]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [0][1] should be 001, but was not.", "001", actSchedule[0][1]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [0][2] should be Software Development Fundamentals, but was not.", "Software Development Fundamentals", actSchedule[0][2]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [0][3] should be TH 1:30PM-2:45PM, but was not.", "TH 1:30PM-2:45PM", actSchedule[0][3]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [1][0] should be CSC226, but was not.", "CSC226", actSchedule[01][0]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [1][1] should be 001, but was not.", "001", actSchedule[1][1]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [1][2] should be , but was not.", "Discrete Mathematics for Computer Scientists", actSchedule[1][2]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  Value at [1][3] should be TH 1:30PM-2:45PM, but was not.", "MWF 9:35AM-10:25AM", actSchedule[1][3]);
		assertEquals("Added CSC216-001 and CSC226-001 to schedule.  getNumScheduledCourses() should return 2, but did not.", 2, schedule.getNumScheduledCourses());
		assertEquals("Added CSC216-001 and CSC226-001 to schedule. CSC226-001 instructor should now be sesmith5, but was not.", "sesmith5", csc226.getInstructorId());
		assertFalse("After adding 2 courses when max courses is 2, faculty is not overloaded, but returned true", f.isOverloaded());
		
		//Attempt to add a conflicting course
		try {
			schedule.addCourseToSchedule(catalog.getCourseFromCatalog("CSC116", "001"));
			fail("Added conflicting course to schedule.  Cannot add conflict to schedule, but was able to.");
		} catch (IllegalArgumentException e) {
			assertEquals("Added CSC216-001 and CSC226-001 to schedule. Attempting to add a CSC116-001 course shouldn't change the schedule. Length of getScheduledCourses() should be 2, but was not.", 2, actSchedule.length);
			assertEquals("Added CSC216-001 and CSC226-001 to schedule.  getNumScheduledCourses() should return 2, but did not.", 2, schedule.getNumScheduledCourses());
			assertNull("Added CSC216-001 and CSC226-001 to schedule.  Attempting to add CSC116-001 (conflicting) shouldn't work and the instructor should remain null, but did not.", catalog.getCourseFromCatalog("CSC116", "001").getInstructorId());
		}
		
		//Attempt to add another instructor to a course with a schedule
		FacultySchedule schedule2 = new FacultySchedule("jdyoung2");
		try {
			schedule2.addCourseToSchedule(csc226);
			fail("Attempted to add CSC226-001 to jdyoung2 schedule when already assigned to sesmith5.  Should throw an IllegalArgumentException, but it did not.");
		} catch (IllegalArgumentException e) {
			assertEquals("Attempting to add CSC226-001 to jdyoung2 schedule when already assigned to sesmith5 threw IAE.  Message should be The course already has an instructor., but was incorrect.", "The course already has an instructor.", e.getMessage());
		}
		
		//Add a third course
		Course csc116 = catalog.getCourseFromCatalog("CSC116", "002");
		schedule.addCourseToSchedule(csc116);
		assertTrue("After adding 3 courses when max courses is 2, faculty is overloaded, but returned false", f.isOverloaded());
	}
	
	/**
	 * Test FacultySchedule.resetSchedule()
	 */
	@Test
	public void testResetSchedule() {
		FacultySchedule schedule = new FacultySchedule("sesmith5");
		
		//Add some courses and reset schedule
		schedule.addCourseToSchedule(catalog.getCourseFromCatalog("CSC216", "001"));
		schedule.addCourseToSchedule(catalog.getCourseFromCatalog("CSC226", "001"));
		schedule.addCourseToSchedule(catalog.getCourseFromCatalog("CSC116", "002"));
		assertEquals("Schedule.resetSchedule() - Added CSC216-001, CSC226-001, and CSC116-002 as setup for remove test.", 3, schedule.getScheduledCourses().length);
		
		schedule.resetSchedule();
		assertEquals("Schedule.resetSchedule() - Added CSC216-001, CSC226-001, and CSC116-002.  Reset schedule.  Length should be 0, but was not.", 0, schedule.getScheduledCourses().length);
		
		//Check that resetting doesn't break future adds
		schedule.addCourseToSchedule(catalog.getCourseFromCatalog("CSC230", "001"));
		assertEquals("Schedule.resetSchedule() - Added CSC216-001, CSC226-001, and CSC116-002.  Reset schedule.  Added CSC230-001 to ensure schedule is not broken.  Length should be 1, but was not.", 1, schedule.getScheduledCourses().length);
	}
}