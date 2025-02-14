package edu.ncsu.csc216.pack_scheduler.course;

import java.util.Objects;

/**
 * superclass for both courses and events
 * provides core functionality for both subclasses
 */
public abstract class Activity implements Conflict {

	/** Upper possible hour */
	private static final int UPPER_HOUR = 24;
	/** Upper possible minute */
	private static final int UPPER_MINUTE = 60;
	/** Course's title. */
	private String title;
	/** Course's meeting days */
	private String meetingDays;
	/** Course's starting time */
	private int startTime;
	/** Course's ending time */
	private int endTime;
	/**
	 * default constructor for Activity
	 * @param title title of the activity
	 * @param meetingDays meeting days of the activity
	 * @param startTime start time of the activity
	 * @param endTime end time of the activity
	 */
	public Activity(String title, String meetingDays, int startTime, int endTime) {
		super();
		setTitle(title);
		setMeetingDaysAndTime(meetingDays, startTime, endTime);
	}
	/**
	 * used to populate the rows of the course catalog and student schedule
	 * @return rows returns rows of the course catalog and student schedule
	 */
	public abstract String[] getShortDisplayArray();
	/**
	 * used to display the final schedule
	 * @return rows returns the final schedule
	 */
	public abstract String[] getLongDisplayArray();

	/**
	 * Returns the Course's title
	 * 
	 * @return the title
	 */
	public String getTitle() {
	    return title;
	}

	/**
	 * Sets the Course's title
	 * 
	 * @param title the title to set
	 * @throws IllegalArgumentException thrown if the title is null or an empty string
	 */
	public void setTitle(String title) throws IllegalArgumentException{
	    if (title == null || "".equals(title)) {
	        throw new IllegalArgumentException("Invalid title."); 
	    }
	
	    this.title = title;
	}

	/**
	 * Returns the Course's meeting days
	 * 
	 * @return the meetingDays
	 */
	public String getMeetingDays() {
	    return meetingDays;
	}

	/**
	 * Returns the Course's start time
	 * 
	 * @return the startTime
	 */
	public int getStartTime() {
	    return startTime;
	}

	/**
	 * Returns the Course's end time
	 * 
	 * @return the endTime
	 */
	public int getEndTime() {
	    return endTime;
	}

	/**
	 * Sets the Activity's meeting days
	 * There is more implementation specific to courses and events in the respective subclasses
	 * 
	 * @param meetingDays the meetingDays to set
	 * @param startTime   start time of course
	 * @param endTime     end time of course
	 * @throws IllegalArgumentException thrown if the meeting days and times are not valid times
	 */
	public void setMeetingDaysAndTime(String meetingDays, int startTime, int endTime) throws IllegalArgumentException{
	
	        // Check for valid meeting times
	        int startHour = startTime / 100;
	        int startMin = startTime % 100;
	        int endHour = endTime / 100;
	        int endMin = endTime % 100;
	        
	        if (startHour < 0 || startHour >= UPPER_HOUR) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	        if (startMin < 0 || startMin >= UPPER_MINUTE) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	        if (endHour < 0 || endHour >= UPPER_HOUR) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	        if (endMin < 0 || endMin >= UPPER_MINUTE) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	        if (endTime < startTime) {
	            throw new IllegalArgumentException("Invalid meeting days and times.");
	        }
	
	        this.meetingDays = meetingDays;
	        this.startTime = startTime;
	        this.endTime = endTime;
	    }

	/**
	 * Returns a string representation of the Course's meeting days and times.
	 * 
	 * @return Course's meeting days and times.
	 */
	public String getMeetingString() {
	    if ("A".equals(meetingDays)) {
	        return "Arranged";
	    }
	
	    return meetingDays + " " + getTimeString(startTime) + "-" + getTimeString(endTime);
	}

	/**
	 * Returns the time in AM/PM format.
	 * 
	 * @param time as an integer
	 * @return time as a string
	 */
	private String getTimeString(int time) {
	    int hour = time / 100;
	    int min = time % 100;
	    boolean morning = true;
	
	    if (hour >= 12) {
	        hour -= 12;
	        morning = false;
	    }
	    if (hour == 0) {
	        hour = 12;
	    }
	
	    String minS = "" + min;
	    if (min < 10) {
	        minS = "0" + minS;
	    }
	
	    String end = morning ? "AM" : "PM";
	
	    return hour + ":" + minS + end;
	}
	/**
	 * an abstract method to compare two activities
	 * is implemented in both the Course class and Event class
	 * @param activity an activity (either course or event)
	 * @return true or false based on whether or not the two activities are equal
	 */
	public abstract boolean isDuplicate(Activity activity);
	/**
	 * generated hashCode() method from Eclipse source generation
	 */
	@Override
	public int hashCode() {
		return Objects.hash(endTime, meetingDays, startTime, title);
	}
	/**
	 * generated equals() method from Eclipse source generation
	 * checks to make sure all fields are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		return endTime == other.endTime && Objects.equals(meetingDays, other.meetingDays)
				&& startTime == other.startTime && Objects.equals(title, other.title);
	}
	/**
	 * overridden from the conflict class to check whether or not the 
	 * activity has a conflict with the parameterized activity
	 * @param activity activity that is being checked for conflicts
	 * @throws ConflictException thrown if is there is a conflict between the two activites
	 */
	@Override
	public void checkConflict(Activity activity) throws ConflictException {
		
		String d1 = this.getMeetingDays();
		String d2 = activity.getMeetingDays();
		boolean overlaps = false;
		
		for (int i = 0; i < d1.length(); i++) {
			
			if (d2.contains(d1.substring(i, i + 1)) && !"A".equals(d1) && !"A".equals(d2)) {
				overlaps = true;
				break;
			}
		}
		
		if (overlaps) {
			
			int startTime1 = this.getStartTime();
			int endTime1 = this.getEndTime();
			int startTime2 = activity.getStartTime();
			int endTime2 = activity.getEndTime();
			
			if (startTime1 >= startTime2 && startTime1 <= endTime2 || startTime2 >= startTime1 && startTime2 <= endTime1) {
				
				throw new ConflictException("The course cannot be added due to a conflict.");
			}
		}
	}
}