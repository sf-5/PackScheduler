/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

/**
 * the conflict class defines when an activity has a schedule conflict with another
 */
public interface Conflict {
	
	/**
	 * the method that checks if the given activity has a conflict with the parametrized activity
	 * @param activity the activity to be checked for conflicts
	 * @throws ConflictException a custom exception to describe the conflict error
	 */
	void checkConflict(Activity activity) throws ConflictException;
}
