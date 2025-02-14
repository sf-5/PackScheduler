/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.user.schedule.FacultySchedule;

/**
 * The Faculty class represents an individual faculty record. The Faculty class
 * is a “plain old java object” (POJO) consisting mostly of getters and setters.
 * Faculty extends User and its implementation is similar to the implementation
 * of Student. The main difference is that Faculty objects have a number of
 * courses they can teach in a given semester (between 1 and 3 inclusive) rather
 * keeping track of credits as Students do.
 */
public class Faculty extends User {
	/** The schedule of the faculty member. */
	private FacultySchedule schedule;

	/**
	 * the maximum number of courses this specific faculty member can teach
	 */
	private int maxCourses;

	/**
	 * a constant variable representing the maximum number of courses that can be
	 * taught in a single semester
	 */
	public static final int MIN_COURSES = 1;

	/**
	 * a constant variable representing the minimum number of courses that can be
	 * taught in a single semester
	 */
	public static final int MAX_COURSES = 3;

	/**
	 * constructs a Faculty object a sets the relevant fields
	 * 
	 * @param firstName  first name of the faculty member
	 * @param lastName   last name of the faculty member
	 * @param id         id of the faculty member
	 * @param email      email of the faculty member
	 * @param hashPW     hashed password of the faculty member
	 * @param maxCourses maximum amount of courses the faculty member can teach
	 */
	public Faculty(String firstName, String lastName, String id, String email, String hashPW, int maxCourses) {
		super(firstName, lastName, id, email, hashPW);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(hashPW);
		this.schedule = new FacultySchedule(id);
		setMaxCourses(maxCourses);
	}

	/**
	 * Returns the faculty's schedule.
	 * 
	 * @return The faculty's schedule.
	 */
	public FacultySchedule getSchedule() {
		return this.schedule;
	}

	/**
	 * Returns true if the number of scheduled courses is greater than the Faculty’s
	 * maxCourses.
	 * 
	 * @return Whether the number of scheduled courses is greater than the Faculty’s
	 *         maxCourses.
	 */
	public boolean isOverloaded() {
		return schedule.getNumScheduledCourses() > this.maxCourses;
	}

	/**
	 * sets the maximum amount of courses to be taught by the faculty member
	 * 
	 * @param maxCourses the maximum number of courses to be set
	 * @throws IllegalArgumentException thrown if the max courses to be set is less
	 *                                  than the minimum possible or greater than
	 *                                  the maximum possible
	 */
	public void setMaxCourses(int maxCourses) throws IllegalArgumentException {

		if (maxCourses < MIN_COURSES || maxCourses > MAX_COURSES) {
			throw new IllegalArgumentException("Invalid max courses");
		}

		this.maxCourses = maxCourses;
	}

	/**
	 * gets the maximum number of courses that the faculty member can teach
	 * 
	 * @return the maximum number of courses that the faculty member can teach
	 */
	public int getMaxCourses() {
		return this.maxCourses;
	}

	/**
	 * an overridden method that generates a unique hash code for this object
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.maxCourses);
		return result;
	}

	/**
	 * overridden method that is used to compare two objects
	 * 
	 * @param obj object to compare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Faculty other = (Faculty) obj;
		return maxCourses == other.getMaxCourses();
	}

	/**
	 * overridden method that returns a Faculty object in the form of a string
	 * contains all relevant fields
	 */
	@Override
	public String toString() {
		return this.getFirstName() + "," + this.getLastName() + "," + this.getId() + "," + this.getEmail() + ","
				+ this.getPassword() + "," + maxCourses;
	}

}
