/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

//import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * tests the activity class for scheduling conflicts
 */
class ActivityTest {

	@Test
	void test() {
		
		Activity a1 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a2 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "TH", 1330, 1445);
		Activity a5 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "A");
		
		assertDoesNotThrow(() -> a1.checkConflict(a2));
		assertDoesNotThrow(() -> a2.checkConflict(a1));
		assertDoesNotThrow(() -> a5.checkConflict(a1));
		assertDoesNotThrow(() -> a1.checkConflict(a5));
		
		Activity a3 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "MW", 1330, 1445);
		Activity a4 = new Course("CSC216", "Software Development Fundamentals", "001", 3, "sesmith5", 10, "M", 1330, 1445);
		
		Exception e1 = assertThrows(ConflictException.class, () -> a3.checkConflict(a4));
		assertEquals("The course cannot be added due to a conflict.", e1.getMessage());
		
		Exception e2 = assertThrows(ConflictException.class, () -> a4.checkConflict(a3));
		assertEquals("The course cannot be added due to a conflict.", e2.getMessage());
		
		
	}

}
