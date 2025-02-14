/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.user.schedule;

import edu.ncsu.csc216.pack_scheduler.course.ConflictException;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.util.ArrayList;

/**
 * the class the defines a schedule object to be used in the Student class
 */
public class Schedule {

	/** the title of the schedule */
	private String title;

	/** a custom ArrayList of Courses representing the schedule */
	ArrayList<Course> schedule;

	/**
	 * Schedule constructor that initializes the title to a default value and makes
	 * a new empty ArrayList of courses
	 */
	public Schedule() {

		this.title = "My Schedule";
		schedule = new ArrayList<Course>();
	}

	/**
	 * getter method that returns the title of the schedule
	 * 
	 * @return the title of the schedule
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * gets every course in the schedule in a 2D array format
	 *
	 * @return the schedule in a 2D array format
	 */
	public String[][] getScheduledCourses() {
		String[][] scheduleArray = new String[this.schedule.size()][5];
		for (int i = 0; i < scheduleArray.length; i++) {
			Course c = this.schedule.get(i);
			scheduleArray[i] = c.getShortDisplayArray();
		}
		return scheduleArray;
	}

	/**
	 * adds a course to the end of the schedule and returns true if the course was
	 * added
	 * 
	 * @param c the course to be added to the schedule
	 * @return true if the course was added and false if it wasn't
	 * @throws IllegalArgumentException thrown if the course to be added is a
	 *                                  duplicate of a course already in the
	 *                                  schedule OR if there is a conflict with a
	 *                                  course already in the schedule
	 */
	public boolean addCourseToSchedule(Course c) throws IllegalArgumentException {

		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				throw new IllegalArgumentException("You are already enrolled in " + c.getName());
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				throw new IllegalArgumentException("The course cannot be added due to a conflict.");
			}
		}

		schedule.add(schedule.size(), c);
		return true;
	}

	/**
	 * removes a course from the schedule
	 * 
	 * @param c the course to be removed from the schedule
	 * @return true if the course was removed and false if there was not a course to
	 *         remove
	 */
	public boolean removeCourseFromSchedule(Course c) {
		
		if (c == null) {
			return false;
		}

		for (int i = 0; i < schedule.size(); i++) {
			if (c.equals(schedule.get(i))) {
				schedule.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * creates a new schedule as an ArrayList and resets the title to the default
	 * value
	 */
	public void resetSchedule() {

		ArrayList<Course> newSchedule = new ArrayList<Course>();
		schedule = newSchedule;
		title = "My Schedule";
	}

	/**
	 * sets the title to a new title
	 * 
	 * @param title the title to be set
	 * @throws IllegalArgumentException thrown if the title to be set is null
	 */
	public void setTitle(String title) throws IllegalArgumentException {
		
		if (title == null) {
			throw new IllegalArgumentException("Title cannot be null.");
		}
		
		this.title = title;
	}
	
	/**
	 * determines whether or not a course can be added to the schedule
	 * @param c the course that is analyzed for eligibility in the schedule
	 * @return true of the course can be added and false if it can't
	 */
	public boolean canAdd(Course c) {
		
		if (c == null) {
			return false;
		}
		
		for (int i = 0; i < schedule.size(); i++) {
			if (c.getName().equals(schedule.get(i).getName())) {
				return false;
			}
			try {
				c.checkConflict(schedule.get(i));
			} catch (ConflictException e) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * gets the total number of credits in the schedule
	 * @return an integer representing the number of credits in the schedule
	 */
	public int getScheduleCredits() {
		
		int count = 0;
		
		for (int i = 0; i < this.schedule.size(); i++) {
			count += this.schedule.get(i).getCredits();
		}
		
		return count;
	}
}
