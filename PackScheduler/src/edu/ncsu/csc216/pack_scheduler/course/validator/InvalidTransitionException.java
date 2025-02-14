package edu.ncsu.csc216.pack_scheduler.course.validator;

/**
 * Custom exception for invalid transition between states
 * 
 * @author Cooper
 * @author Neelesh
 */
public class InvalidTransitionException extends Exception {
	
	/**
	 * helps to maintain version control of serialized objects
	 */
	private static final long serialVersionUID = 2L;
	
	/**
	 * Constructor with custom message parameter
	 * 
	 * @param message to be used in creating InvalidTransitionException
	 */
	public InvalidTransitionException(String message) {
		super(message);
	}

	/**
	 * Constructor with no parameter, used as default
	 */
	public InvalidTransitionException() {
		
		// default exception message
		this("Invalid FSM Transition.");
	}
}
