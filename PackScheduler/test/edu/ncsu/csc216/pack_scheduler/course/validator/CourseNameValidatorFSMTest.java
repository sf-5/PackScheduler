package edu.ncsu.csc216.pack_scheduler.course.validator;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.jupiter.api.Test;

class CourseNameValidatorFSMTest {

	/**  Constructs course name validator finite state machine*/
	CourseNameValidatorFSM validator = new CourseNameValidatorFSM();
	
	/**
	 * Tests for exception thrown from non digit and non letter first character
	 */
	@Test
	public void testNonAlphanumericThrownException() {
		
		Exception e1 = assertThrows(InvalidTransitionException.class,
				() -> validator.isValid("$SCA116"));
		
		assertEquals("Course name can only contain letters and digits.", e1.getMessage());
	}
	
	/** 
	 * Tests for a valid transition from initial state
	 */
	@Test
	public void testInitialStateValid() {
		try {
			assertTrue(validator.isValid("C116"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from initial state
	 */
	@Test
	public void testInitialStateInvalid() {
		try {
			validator.isValid("116C");
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must start with a letter.", e.getMessage());
		}
	}
	
	/**
	 * Tests for a valid transition from L state
	 */
	@Test
	public void testLStateValid() {
		try {
			assertTrue(validator.isValid("CS116"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from L state
	 */
	@Test
	public void testLStateInvalid() {
		try {
			assertTrue(validator.isValid("C#1"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests for a valid transition from LL state
	 */
	@Test
	public void testLLStateValid() {
		try {
			assertTrue(validator.isValid("CSC116"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from LL state
	 */
	@Test
	public void testLLStateInvalid() {
		try {
			assertTrue(validator.isValid("CS#116"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only contain letters and digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests for a valid transition from LLL state
	 */
	@Test
	public void testLLLStateValid() {
		try {
			assertTrue(validator.isValid("ACSC116"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from LLLL state
	 */
	@Test
	public void testLLLLStateInvalid() {
		try {
			assertTrue(validator.isValid("ACSCC116"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot start with more than 4 letters.", e.getMessage());
		}
	}
	
	/**
	 * Tests for a valid transition from D state
	 */
	@Test
	public void testDStateValid() {
		try {
			assertTrue(validator.isValid("CSC116"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from D state
	 */
	@Test
	public void testDStateInvalid() {
		try {
			assertTrue(validator.isValid("CSC1SS"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from DD state
	 */
	@Test
	public void testDDStateInvalid() {
		try {
			assertTrue(validator.isValid("CSC11C"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name must have 3 digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests for a valid transition from DDD state
	 */
	@Test
	public void testDDDStateValid() {
		try {
			assertTrue(validator.isValid("CSC116A"));
		} catch (InvalidTransitionException e) {
			fail("Unexcepted exception: " + e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from DDD state
	 */
	@Test
	public void testDDDStateInvalid() {
		try {
			assertTrue(validator.isValid("CSC1166"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have 3 digits.", e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from suffix state
	 */
	@Test
	public void testSuffixStateInvalidDigit() {
		try {
			assertTrue(validator.isValid("CSC116A6"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name cannot contain digits after the suffix.", e.getMessage());
		}
	}
	
	/**
	 * Tests for an invalid transition from suffix state
	 */
	@Test
	public void testSuffixStateInvalidLetter() {
		try {
			assertTrue(validator.isValid("CSC116AA"));
		} catch (InvalidTransitionException e) {
			assertEquals("Course name can only have a 1 letter suffix.", e.getMessage());
		}
	}
}
