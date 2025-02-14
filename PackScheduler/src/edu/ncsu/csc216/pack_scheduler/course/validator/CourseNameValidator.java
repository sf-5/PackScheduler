/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * CourseNameValidator class uses the FSM which uses the state
 * pattern to check for valid course names.
 * 
 * @author Neelesh Yaddanapudi
 */
public class CourseNameValidator {

	/** private field for counting letters */
	private int letterCount;

	/** private field for counting digits */
	private int digitCount;

	/** State instance for state number */
	private State stateNumber;

	/** State instance for state letter */
	private State stateLetter;

	/** State instance for state initial */
	private State stateInitial;

	/** State instance for state suffix */
	private State stateSuffix;

	/** State instance for current state */
	private State currentState;

	/** Constant value for course name prefix */
	private static final int MAX_PREFIX_LETTERS = 4;

	/** Constant value for course number length */
	private static final int COURSE_NUMBER_LENGTH = 3;

	/**
	 * Constructs a new CourseNameValidator that works with the state pattern
	 * to check for valid course names.
	 */
	public CourseNameValidator() {
		stateInitial = new InitialState();
		stateLetter = new LetterState();
		stateNumber = new NumberState();
		stateSuffix = new SuffixState();

	}

	/**
	 * isValid check whether a course name is valid
	 *  
	 * @param name name of the course
	 * @return true is letter count is between 0 to 6 and digit count is 3
	 * @throws InvalidTransitionException if course name is invalid exception is thrown
	 */
	public boolean isValid(String name) throws InvalidTransitionException {
		letterCount = 0;
		digitCount = 0;
		currentState = stateInitial;

			for (int i = 0; i < name.length(); i++) {
				char c = name.charAt(i);

				if (Character.isLetter(c)) {
					currentState.onLetter();
					letterCount++;
				} else if (Character.isDigit(c)) {
					currentState.onDigit();
					digitCount++;
				} else {
					currentState.onOther();
				}
			}

			return letterCount > 0 && letterCount < 6 && digitCount == COURSE_NUMBER_LENGTH;
	}

	/**
	 * Abstract class that shows state in state pattern
	 *  which includes concrete classes.
	 */
	private abstract class State {

		/** Handles transition when a letter encountered 
		 * 
		 * @throws InvalidTransitionException if the transition is incorrect
		 */
		public abstract void onLetter() throws InvalidTransitionException;

		/** Handles transition when a digit encountered 
		 * 
		 * @throws InvalidTransitionException if the transition is incorrect
		 */
		public abstract void onDigit() throws InvalidTransitionException;

		/**
		 * Handles transition when a character other than a letter or a digit is 
		 * encountered
		 * 
		 * @throws InvalidTransitionException if the transition is incorrect
		 */
		public void onOther() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only contain letters and digits.");
		}

	}

	/**
	 * Initial state when the course name begins with a letter
	 */
	private class InitialState extends State {
		
		/**
		 * Course name has a letter then goes to the onLetter method to set the
		 * current state to letter state
		 */
		@Override
		public void onLetter() {
			currentState = new LetterState();

		}

		/**
		 * On digit method if the course name has a digit
		 * 
		 * @throws InvalidTransitionException if the course name starts with a digit
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name must start with a letter.");
		}
	}

	/**
	 * Letter state represents the letter portion of the course name
	 */
	private class LetterState extends State {

		/**
		 * On letter method if the course name has a letter when it is in the Letter state
		 * 
		 * @throws InvalidTransitionException if the course name cannot start with more 
		 * than 4 letters.
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (letterCount < MAX_PREFIX_LETTERS) {
				currentState = stateLetter;
			} else {
				throw new InvalidTransitionException("Course name cannot start with more than 4 letters.");
			}
		}

		/**
		 * On digit method if the course name has a digit when it is in the Letter state
		 */
		@Override
		public void onDigit() {
			currentState = stateNumber;

		}
	}

	/**
	 * Number state represents the number portion of the course name
	 */
	private class NumberState extends State {
		
		/**
		 * On letter method if the course name has a letter when it is in the Number state 
		 * 
		 * @throws InvalidTransitionException if the course doesn't have 3 digits
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			if (digitCount == COURSE_NUMBER_LENGTH) {
				currentState = stateSuffix;
			} else if (digitCount < COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name must have 3 digits.");
			}
		}

		/**
		 * On digit method if the course name has a letter when it is in the Number state 
		 * 
		 *  @throws InvalidTransitionException if the course has more than 3 digits
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			if (digitCount >= COURSE_NUMBER_LENGTH) {
				throw new InvalidTransitionException("Course name can only have 3 digits.");
			}
		}
	}

	/**
	 * Suffix state represent the suffix of the course name
	 */
	private class SuffixState extends State {
		/**
		 * On letter method if the course name has a letter when it is in the Suffix state 
		 * 
		 * @throws InvalidTransitionException if the course has more than 1 suffix letter
		 */
		@Override
		public void onLetter() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name can only have a 1 letter suffix.");
		}

		/**
		 * On digit method if the course name has a letter when it is in the Suffix state 
		 * 
		 * @throws InvalidTransitionException if course contains digits after the suffix 
		 */
		@Override
		public void onDigit() throws InvalidTransitionException {
			throw new InvalidTransitionException("Course name cannot contain digits after the suffix.");
		}
	}

}
