/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course.roll;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.util.LinkedAbstractList;
import edu.ncsu.csc216.pack_scheduler.util.LinkedQueue;

/**
 * CourseRoll represents the students enrolled in a course. It maintains a list
 * of students, handles enrollment capacity, and provides methods to add or
 * remove students from the course.
 */
public class CourseRoll {

	/** a custom LinkedAbstractList of Students representing the course roll */
	LinkedAbstractList<Student> roll;

	/** Student waitlist when classes get full */
	private LinkedQueue<Student> waitlist;

	/** A private field for the roll's enrollment capacity */
	private int enrollmentCap;

	/** The smallest class size */
	private static final int MIN_ENROLLMENT = 10;

	/** The largest class size */
	private static final int MAX_ENROLLMENT = 250;

	/** The course the CourseRoll is associated with */
	private Course course;
	/**
	 * Constructor for the CourseRoll
	 * 
	 * @param rollCapacity the enrollment capacity for the classes
	 * @param course       the course the CourseRoll is associated with
	 * @throws IllegalArgumentException if course is null
	 */
	public CourseRoll(int rollCapacity, Course course) {
		if (course == null) {
			throw new IllegalArgumentException("Course cannot be null.");
		}
		roll = new LinkedAbstractList<>(MAX_ENROLLMENT);
		setEnrollmentCap(rollCapacity);
		waitlist = new LinkedQueue<Student>(10);
		this.course = course;
	}

	/**
	 * To get the enrollment capacity
	 * 
	 * @return the enrollment capacity
	 */
	public int getEnrollmentCap() {
		return this.enrollmentCap;
	}

	/**
	 * To set the enrollment capacity
	 * 
	 * @param rollCapacity the enrollment capacity of the class
	 * @throws IllegalArgumentException if the enrollment capacity is less than the
	 *                                  min enrollment, greater than the max
	 *                                  enrollment or less than the current
	 *                                  enrollment capacity
	 */
	public void setEnrollmentCap(int rollCapacity) throws IllegalArgumentException {
		if (rollCapacity < MIN_ENROLLMENT || rollCapacity > MAX_ENROLLMENT) {
			throw new IllegalArgumentException(
					"Enrollment cap must be between " + MIN_ENROLLMENT + " and " + MAX_ENROLLMENT);
		}
		if (rollCapacity < roll.size()) {
			throw new IllegalArgumentException(
					"Enrollment capacity cannot be less than the current number of enrolled students");
		}
		this.enrollmentCap = rollCapacity;
		this.roll.setCapacity(rollCapacity);
	}

	/**
	 * To enroll student to the course roll
	 * 
	 * @param s s is the student from the student directory
	 * @throws IllegalArgumentException if the student that is trying to enroll is
	 *                                  invalid
	 */
	public void enroll(Student s) throws IllegalArgumentException {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
		if (roll.size() == enrollmentCap) {
			// no room in roll, add to waitlist if we can
			if (!canEnroll(s)) {
				throw new IllegalArgumentException(
						"Student " + s.getFirstName() + " " + s.getLastName() + " cannot be added.");
			} else {
				// add to waitlist
				waitlist.enqueue(s);
			}
		} else {
			try {
				roll.add(s);
			} catch (Exception e) {
				throw new IllegalArgumentException("Unable to add student to course roll.");
			}
		}
	}

	/**
	 * To remove a student from the course roll
	 * 
	 * @param s s is the student from the student directory
	 * @throws IllegalArgumentException if the student that is to be removed is
	 *                                  invalid
	 */
	public void drop(Student s) throws IllegalArgumentException {
		if (s == null) {
			throw new IllegalArgumentException("Student cannot be null.");
		}
//		System.out.println(roll.toString());
//		System.out.println(s.toString());
//		System.out.println(roll.indexOf(s));
		try {
			int indexRoll = roll.indexOf(s);
			if (indexRoll != -1) {
				roll.remove(indexRoll);
				if(waitlist.size() > 0) {
					// remove the first student in the waitlist
					Student waitlisted = waitlist.dequeue();
					roll.add(waitlisted);
					// add the course to the student's schedule
					waitlisted.getSchedule().addCourseToSchedule(course);
				}
			} else {
				// student may be in the waitlist
				LinkedQueue<Student> backup = new LinkedQueue<Student>(10);
				while(waitlist.size() > 0) {
					Student waitlistS = waitlist.dequeue();
					if(!waitlistS.equals(s)) {
						backup.enqueue(waitlistS);
					}
				}
				while(backup.size() > 0) {
					waitlist.enqueue(backup.dequeue());
				}
			}
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to remove student from course roll.");
		}
	}
	
	/**
	 * Gets the size of the waitlist
	 * @return number of Students on the waitlist
	 */
	public int getNumberOnWaitlist() {
		return waitlist.size();
	}

	/**
	 * gets the number of open seats the course
	 * 
	 * @return the number of open seats in the course
	 */
	public int getOpenSeats() {
		return this.enrollmentCap - roll.size();
	}

	/**
	 * determines whether or not a given student can enroll in the course
	 * 
	 * @param s the student to potentially add to the course
	 * @return true if the student can enroll and false if they can't
	 */
	public boolean canEnroll(Student s) {
		if (roll.contains(s)) {
			return false;
		}
		if (!(getOpenSeats() == 0)) {
			// check if student can be added to the waitlist
			return waitlist.getCapacity() > waitlist.size();
		} else {
			return true;
		}
	}
}
