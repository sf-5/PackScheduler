	/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.course.validator.CourseNameValidator;
import edu.ncsu.csc216.pack_scheduler.course.validator.InvalidTransitionException;

/**
 * Creates the Course object and checks that all fields are valid.
 */
public class Course extends Activity implements Comparable<Course> {
    /** Length of section number */
    private static final int SECTION_LENGTH = 3;
    /** Max number of possible credits for a course */
    private static final int MAX_CREDITS = 5;
    /** Min number of possible credits for a course */
    private static final int MIN_CREDITS = 1;
    /** Course's name. */
    private String name;
    /** Course's section. */
    private String section;
    /** Course's credit hours */
    private int credits;
    /** Course's instructor */
    private String instructorId;
    /** The validator object that ensures that a course name is valid */
    private CourseNameValidator validator;
    /** A list of students enrolled in the course (the roll) */
    private CourseRoll roll;
    
    /**
     * Constructs a Course object with values for all fields.
     * 
     * @param name         name of Course
     * @param title        title of Course
     * @param section      section of Course
     * @param credits      credit hours for Course
     * @param instructorId instructor's unity id
     * @param enrollmentCap the enrollment cap of the Course
     * @param meetingDays  meeting days for Course as series of chars
     * @param startTime    start time for Course
     * @param endTime      end time for Course
     */
    public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays,
            int startTime, int endTime) {
        super(title, meetingDays, startTime, endTime);
		setName(name);
        setSection(section);
        setCredits(credits);
        setInstructorId(instructorId);
        roll = new CourseRoll(enrollmentCap, this);
    } 

    /**
     * Creates a Course with the given name, title, section, credits, instructorId,
     * and meetingDays for courses that are arranged.
     * 
     * @param name         name of Course
     * @param title        title of Course
     * @param section      section of Course
     * @param credits      credit hours for Course
     * @param instructorId instructor's unity id
     * @param enrollmentCap enrollment cap of the Course
     * @param meetingDays  meeting days for Course as series of chars
     */
    public Course(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays) {
        this(name, title, section, credits, instructorId, enrollmentCap, meetingDays, 0, 0);
    }

    /**
     * Returns the Course's name
     * 
     * @return the name
     */
    public String getName() {
        return name;

    }

    /**
     * Sets the Course's name
     * 
     * @param name the name to set
     * @throws IllegalArgumentException if name is null or does not follow the data format
     */
    private void setName(String name) throws IllegalArgumentException{
    	if (name == null) {
    		throw new IllegalArgumentException("Invalid course name.");
    	}
    	validator = new CourseNameValidator();
    	
    	try {
    		if (!validator.isValid(name) || name == null) {
    			throw new IllegalArgumentException("Invalid course name.");
    		}
    	}
    	catch(InvalidTransitionException e) {
    		throw new IllegalArgumentException("Invalid course name.");
    	}

        this.name = name;
    }

    /**
     * Returns the Course's section
     * 
     * @return the section
     */
    public String getSection() {
        return section;
    }

    /**
     * Sets the Course's section
     * 
     * @param section the section to set
     * @throws IllegalArgumentException thrown if the section is null or doesn't follow the data format
     */
    public void setSection(String section) throws IllegalArgumentException{
        if (section == null || section.length() != SECTION_LENGTH) {
            throw new IllegalArgumentException("Invalid section.");
        }
        for (int i = 0; i < section.length(); i++) {
            if (!Character.isDigit(section.charAt(i))) {
                throw new IllegalArgumentException("Invalid section.");
            }
        }
        this.section = section;
    }

    /**
     * Returns the Course's credits
     * 
     * @return the credits
     */
    public int getCredits() {
        return credits;
    }

    /**
     * Sets the Course's credits
     * 
     * @param credits the credits to set
     * @throws IllegalArgumentException if there are too few or too many credits
     */
    public void setCredits(int credits) throws IllegalArgumentException {
        if (credits < MIN_CREDITS || credits > MAX_CREDITS) {
            throw new IllegalArgumentException("Invalid credits.");
        }

        this.credits = credits;
    }

    /**
     * Returns the Course's instructor id
     * 
     * @return the instructorId
     */
    public String getInstructorId() {
        return instructorId;
    }

    /**
     * Sets the Course's instructor id
     * 
     * @param instructorId the instructorId to set
     * @throws IllegalArgumentException thrown if the instructor id is null or an empty string
     */
    public void setInstructorId(String instructorId) throws IllegalArgumentException {
    	if ("".equals(instructorId)) {
    		throw new IllegalArgumentException("Invalid instructor id.");
    	} else {
    		this.instructorId = instructorId;
    	}
    }
    
    /**
     * Overridden from Activity class to provide more specific functionality
     * Handles the case in which classes are virtual
     * Does not allow Sundays or Saturdays
     * @param meetingDays meeting days of the course
     * @param startTime start time of the course
     * @param endTime end time of the course
     * @throws IllegalArgumentException thrown if the meeting days do not follow specifications
     */
    @Override
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) throws IllegalArgumentException{
    	
    	// checks the make sure the meeting days exist
    	if (meetingDays == null || "".equals(meetingDays)) {
	        throw new IllegalArgumentException("Invalid meeting days and times.");
	    }
	
	    // Handle arranged
	    if ("A".equals(meetingDays)) {
	    	if (startTime != 0 || endTime != 0) {
	    		throw new IllegalArgumentException("Invalid meeting days and times.");
	    	}
	        super.setMeetingDaysAndTime("A", 0, 0);
	    }
	    else {
	    	// Check for valid characters in meeting days
	    	int countM = 0;
	    	int countT = 0;
	    	int countW = 0;
	    	int countH = 0;
	        int countF = 0;
	        for (int i = 0; i < meetingDays.length(); i++) {
	        	char c = meetingDays.charAt(i);
	    		switch (c) {
	    			case 'M':
	    				countM++;
	    		        break;
	    		    case 'T':
	    		        countT++;
	    		        break;
	    		    case 'W':
	    		        countW++;
	    		        break;
	    		    case 'H':
	    		        countH++;
	    		        break;
	    		    case 'F':
	    		        countF++;
	    		        break;
	    		default:
	    		    throw new IllegalArgumentException("Invalid meeting days and times.");
	    		}
	        }
	        
	        if (countM > 1 || countT > 1 || countW > 1 || countH > 1 || countF > 1) {
	        	throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	        
	        super.setMeetingDaysAndTime(meetingDays, startTime, endTime);
	    }
	}  
    /**
     * checks if two activities are duplicate courses
     */
	@Override
	public boolean isDuplicate(Activity activity) {
		
		return activity instanceof Course && ((Course) activity).getName().equals(this.name);
	}

	/**
     * Returns a comma separated value String of all Course fields.
     * 
     * @return String representation of Course
     */
    @Override
    public String toString() {
        if ("A".equals(getMeetingDays())) {
            return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getCourseRoll().getEnrollmentCap() + "," + getMeetingDays();
        }
        return name + "," + getTitle() + "," + section + "," + credits + "," + instructorId + "," + getCourseRoll().getEnrollmentCap() + "," + getMeetingDays() + ","
                + getStartTime() + "," + getEndTime();
    }
    
    /**
     * overridden hashCode() method that created a custom hash
     */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(credits, instructorId, name, section);
		return result;
	}
	/**
	 * overridden equals() method that determines if all the fields are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		return credits == other.credits && Objects.equals(instructorId, other.instructorId)
				&& Objects.equals(name, other.name) && Objects.equals(section, other.section);
	}
	/**
	 * generated via Eclipse source generation
	 */
	@Override
	public String[] getShortDisplayArray() {
		return new String[] {getName(), getSection(), getTitle(), getMeetingString(), String.valueOf(getCourseRoll().getOpenSeats())};
	}
	/**
	 * generated via Eclipse source generation
	 */
	@Override
	public String[] getLongDisplayArray() {
		return new String[] {getName(), getSection(), getTitle(), String.valueOf(getCredits()), getInstructorId(), getMeetingString(), ""};
	}
	
	/**
	 * getter method for the course roll field
	 * @return the roll for the course
	 */
	public CourseRoll getCourseRoll() {
		return this.roll;
	}
	/**
	 * overridden method from the comparable class used to compare course objects
	 * returns less than if name, then section is less than
	 * returns greater than if the name, then section is greater than
	 * returns 0 if the course is the same
	 */
	@Override
	public int compareTo(Course c) {

		if (this.name.compareTo(c.name) < 0) {
			
			return -1;
		}
		else if (this.name.compareTo(c.name) > 0) {
			return 1;
		}
		else {
			
			if (this.getSection().compareTo(c.getSection()) < 0) {
				
				return -1;
			}
			else if (this.getSection().compareTo(c.getSection()) > 0) {
				
				return 1;
			}
			else {
				
				return 0;
			}
		}
	}
  
}