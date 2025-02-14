
/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.io;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.FileInputStream;
import java.io.File;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.manager.RegistrationManager;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Reads Course records from text files.  Writes a set of CourseRecords to a file.
 * 
 * @author Sarah Heckman
 * @author Cooper Lentz
 */
public class CourseRecordIO { 
	
	/**
     * Reads course records from a file and generates a list of valid Courses.  Any invalid
     * Courses are ignored.  If the file to read cannot be found or the permissions are incorrect
     * a File NotFoundException is thrown.
     * @param fileName file to read Course records from
     * @return a list of valid Courses
     * @throws FileNotFoundException if the file cannot be found or read
     */
	
	public static SortedList<Course> readCourseRecords(String fileName) throws FileNotFoundException {
	    Scanner fileReader = new Scanner(new FileInputStream(fileName));  //Create a file scanner to read the file
	    SortedList<Course> courses = new SortedList<Course>(); //Create an empty array of Course objects
	    while (fileReader.hasNextLine()) { //While we have more lines in the file
	        try { //Attempt to do the following
	            //Read the line, process it in readCourse, and get the object
	            //If trying to construct a Course in readCourse() results in an exception, flow of control will transfer to the catch block, below
	            Course course = readCourse(fileReader.nextLine()); 

	            //Create a flag to see if the newly created Course is a duplicate of something already in the list  
	            boolean duplicate = false;
	            //Look at all the courses in our list
	            for (int i = 0; i < courses.size(); i++) {
	                //Get the course at index i
	                Course current = courses.get(i);
	                //Check if the name and section are the same
	                if (course.getName().equals(current.getName()) &&
	                        course.getSection().equals(current.getSection())) {
	                    //It's a duplicate!
	                    duplicate = true;
	                    break; //We can break out of the loop, no need to continue searching
	                }
	            }
	            //If the course is NOT a duplicate
	            if (!duplicate) {
	                courses.add(course); //Add to the ArrayList!
	            } //Otherwise ignore
	        } catch (IllegalArgumentException e) {
	        	// empty catch block
	        }
	    }
	    //Close the Scanner b/c we're responsible with our file handles
	    fileReader.close();
	    //Return the ArrayList with all the courses we read!
	    return courses;
	}
	/**
	 * takes in a line in a file of courses and converts it into a course object
	 * @param nextLine an individual line passed in to be converted to a Course object
	 * @return a Course object
	 * @throws IllegalArgumentException thrown if there are too many fields in the line for one course
	 */
	private static Course readCourse(String nextLine) throws IllegalArgumentException {
		Scanner s = new Scanner(nextLine);
		s.useDelimiter(","); //each attribute of a course in the file is separated by a comma
		
		// catches any exceptions resulting from too many tokens or not enough tokens
		try {
			//local variables to store course fields
			String name = s.next();
			String title = s.next();
			String section = s.next();
			int credits = s.nextInt();
			String instructorId = s.next();
			int enrollmentCap = s.nextInt();
			String meetingDays = s.next();
			System.out.println();
			System.out.println(nextLine);
			System.out.println(instructorId);
			//if the course is arranged, the line is checked for extraneous fields
			if ("A".equals(meetingDays)) {
				if (s.hasNext()) {
					s.close();
					throw new IllegalArgumentException("Invalid course name.");
				} else {
					s.close();
					return new Course(name, title, section, credits, null, enrollmentCap, meetingDays);
				}
			}
			
			// a non-arranged course
			int startTime = s.nextInt();
			int endTime = s.nextInt();
//			
			// if there are extraneous fields, and exception is thrown
			if (s.hasNext()) {
				s.next();
				s.close();
				throw new IllegalArgumentException("Invalid course name.");
			} else {
				s.close();
				Course course = new Course(name, title, section, credits, null, enrollmentCap, meetingDays, startTime, endTime);
				
				Faculty faculty = RegistrationManager.getInstance().getFacultyDirectory().getFacultyById(instructorId);
				
				if (faculty != null) {
					faculty.getSchedule().addCourseToSchedule(course);
				}
				
				return course;
			}
			
		}
		catch (Exception e) {
			s.close();
			throw new IllegalArgumentException("Invalid course name.");
		}
	}

	/**
     * Writes the given list of Courses to 
     * @param fileName file to write schedule of Courses to
     * @param courses list of Courses to write
     * @throws IOException if cannot write to file
     */
	
	public static void writeCourseRecords(String fileName, SortedList<Course> courses) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for (int i = 0; i < courses.size(); i++) {
		    fileWriter.println(courses.get(i).toString());
		}

		fileWriter.close();
		
	}

}
