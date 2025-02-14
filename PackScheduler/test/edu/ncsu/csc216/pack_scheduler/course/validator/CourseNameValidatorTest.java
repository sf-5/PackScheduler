package edu.ncsu.csc216.pack_scheduler.course.validator;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Test cases for CourseNameValidator
 */
class CourseNameValidatorTest {

	/**
	 * Tests for exception thrown from non digit and non letter first character
	 */
	@Test
	public void testNonAlphanumericThrownException() {

		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("$SCA116"));

		assertEquals("Course name can only contain letters and digits.", e1.getMessage());
	}

	/**
	 * Tests for a valid transition from initial state
	 */
	@Test
	public void testInitialStateValid() throws InvalidTransitionException {

		CourseNameValidator course = new CourseNameValidator();

		assertTrue(course.isValid("CSC116"));
	}

	/**
	 * Tests for an invalid transition from initial state
	 */
	@Test
	public void testInitialStateInvalid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("116C"));

		assertEquals("Course name must start with a letter.", e1.getMessage());

	}

	/**
	 * Tests for a valid transition from L state
	 */
	@Test
	public void testLStateValid() throws InvalidTransitionException {
		CourseNameValidator course = new CourseNameValidator();

		assertTrue(course.isValid("CS116"));

	}

	/**
	 * Tests for an invalid transition from L state
	 */
	@Test
	public void testLStateInvalid() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("C#1"));

		assertEquals("Course name can only contain letters and digits.", e1.getMessage());

	}

	/**
	 * Tests for a valid transition from LL state
	 */
	@Test
	public void testLLStateValid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();

		assertTrue(c.isValid("CSC116"));

	}

	/**
	 * Tests for an invalid transition from LL state
	 */
	@Test
	public void testLLStateInvalid() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CS#116"));

		assertEquals("Course name can only contain letters and digits.", e1.getMessage());

	}

	/**
	 * Tests for a valid transition from LLL state
	 */
	@Test
	public void testLLLStateValid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();

		assertTrue(c.isValid("ACSC116"));

	}

	/**
	 * Tests for an invalid transition from LLLL state
	 */
	@Test
	public void testLLLLStateInvalid() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("ACSCC116"));

		assertEquals("Course name cannot start with more than 4 letters.", e1.getMessage());

	}

	/**
	 * Tests for a valid transition from D state
	 */
	@Test
	public void testDStateValid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		assertTrue(c.isValid("CSC116"));

	}

	/**
	 * Tests for an invalid transition from D state
	 */
	@Test
	public void testDStateInvalid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC1SS"));

		assertEquals("Course name must have 3 digits.", e1.getMessage());
	}

	/**
	 * Tests for an invalid transition from DD state
	 */
	@Test
	public void testDDStateInvalid() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC11C"));

		assertEquals("Course name must have 3 digits.", e1.getMessage());
	}

	/**
	 * Tests for a valid transition from DDD state
	 */
	@Test
	public void testDDDStateValid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		assertTrue(c.isValid("CSC116A"));
	}

	/**
	 * Tests for an invalid transition from DDD state
	 */
	@Test
	public void testDDDStateInvalid() throws InvalidTransitionException {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC1166"));

		assertEquals("Course name can only have 3 digits.", e1.getMessage());

	}

	/**
	 * Tests for an invalid transition from suffix state
	 */
	@Test
	public void testSuffixStateInvalidDigit() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC116A6"));

		assertEquals("Course name cannot contain digits after the suffix.", e1.getMessage());

	}

	/**
	 * Tests for an invalid transition from suffix state
	 */
	@Test
	public void testSuffixStateInvalidLetter() {
		CourseNameValidator c = new CourseNameValidator();
		Exception e1 = assertThrows(InvalidTransitionException.class, () -> c.isValid("CSC116AA"));

		assertEquals("Course name can only have a 1 letter suffix.", e1.getMessage());
		
	
	}
}
