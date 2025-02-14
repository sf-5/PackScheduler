/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * test the InvalidTransitionException class
 * ensures it presents the correct error message
 */
public class InvalidTransitionExceptionTest {
	
	/**
	 * tests the InvalidTransitionException(String) constructor
	 */
	@Test
	void testConflictExceptionString() {
		InvalidTransitionException ite = new InvalidTransitionException("Custom exception message");
		assertEquals("Custom exception message", ite.getMessage());
	}

	/**
	 * tests the InvalidTransitionException() constructor
	 */
	@Test
	void testConflictException() {
		InvalidTransitionException ite = new InvalidTransitionException();
		assertEquals("Invalid FSM Transition.", ite.getMessage());
	}
}
