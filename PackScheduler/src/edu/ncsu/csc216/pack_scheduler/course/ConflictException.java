package edu.ncsu.csc216.pack_scheduler.course;

/**
 * the custom exception ConflictException is thrown when an activity
 * has a conflict with another activity in the schedule
 */
public class ConflictException extends Exception {
	
	/**
	 * constructor used when a message is specified
	 * follows one path construction paradigm
	 * @param message client-specified message
	 */
	public ConflictException(String message) {
		super(message);
	}
	/**
	 * default constructor
	 * follows one path of construction paradigm
	 */
	public ConflictException() {
		this("Schedule conflict.");
	}
	
	/**
	 * special field that acts as a version control mechanism for serializable classes
	 */
	private static final long serialVersionUID = 1L;
	
}
