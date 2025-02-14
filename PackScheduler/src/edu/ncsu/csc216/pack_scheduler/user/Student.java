package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * student class is a class for the student
 */
public class Student extends User implements Comparable<Student> {
	/**
	 * maximum amount of credits of the student
	 */
	private int maxCredits;
	
	/**
	 * maximum possible credits any student can have
	 */
	public static final int MAX_CREDITS = 18;
	
	/** the schedule of the specific student (accessed through the get method) */
	private Schedule schedule = new Schedule();

	/**
	 * main constructor for the student class (following one path of construction paradigm)
	 * @param firstName first name of the student
	 * @param lastName last name of the student
	 * @param id id of the student
	 * @param email email of the student
	 * @param hashPW hashed password of the student
	 * @param maxCredits maximum credits of the student
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW, int maxCredits) {
		super(firstName, lastName, id, email, hashPW);
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(hashPW);
		this.setMaxCredits(maxCredits);
	}
	
/**
 * Sets the student's max credits
 * @param maxCredits -  the student's new max credits
 * @throws IllegalArgumentException thrown if there are too many or too few max credits
 */
	public void setMaxCredits(int maxCredits) throws IllegalArgumentException {
		if (maxCredits < 3 || maxCredits > 18) {
			throw new IllegalArgumentException("Invalid max credits");
		}
	this.maxCredits = maxCredits; 
	}

/**
	 * secondary constructor of the student class (calls the constructor above)
	 * @param firstName first name of the student
	 * @param lastName last name of the student
	 * @param id id of the student
	 * @param email email of the student
	 * @param hashPW hashed password of the files
	 */
	public Student(String firstName, String lastName, String id, String email, String hashPW) {
		this(firstName, lastName, id, email, hashPW, 18); // calls the main constructor
	}
	
	/**
	 * gets the max credits
	 * @return maxCredits
	 */
	public int getMaxCredits() {
		return this.maxCredits;
	}
	/**
	 * overridden method that returns a student object in the form of a string
	 * contains all relevant fields
	 */
	@Override
	public String toString() {
		return 	this.getFirstName() + ","  + this.getLastName() + "," + this.getId() + "," + this.getEmail() + "," + this.getPassword() + "," + maxCredits;
	}
	/**
	 * overridden compareTo method that compares one Student to another
	 * Students are ordered by first name, then by last name, then by ID
	 */
	@Override
	public int compareTo(Student s) {
			
		if (this.getLastName().compareTo(s.getLastName()) < 0) {
			
			return -1;
		}
		else if (this.getLastName().compareTo(s.getLastName()) > 0) {
			
			return 1;
		}
		else {
			
			if (this.getFirstName().compareTo(s.getFirstName()) < 0) {
				
				return -1;
			}
			else if (this.getFirstName().compareTo(s.getFirstName()) > 0) {
				
				return 1;
			}
			else {

				if (this.getId().compareTo(s.getId()) < 0) {
					
					return -1;
				}
				else if (this.getId().compareTo(s.getId()) > 0) {
					
					return 1;
				}
				else {
					
					return 0;
				}
			}
		}
	}
	/**
	 * overridden method to generate a hash code
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(maxCredits);
		return result;
	}
	/**
	 * overridden method to compare two objects
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
		Student other = (Student) obj;
		return maxCredits == other.maxCredits;
	}
	
	/**
	 * the getter method for the schedule of the student
	 * @return the schedule of the student 
	 */
	public Schedule getSchedule() {
		return schedule;
	}
	
	/**
	 * determines if the given course can be added to the student's schedule
	 * @param c the course whose compatibility with the schedule is being determined
	 * @return true of the course can be added to the student's schedule and false if it can't
	 */
	public boolean canAdd(Course c) {
		
		if (!getSchedule().canAdd(c) || getSchedule().getScheduleCredits() + c.getCredits() > maxCredits) {
			return false;
		}
		
		return c.getCourseRoll().canEnroll(this);
	}
}
