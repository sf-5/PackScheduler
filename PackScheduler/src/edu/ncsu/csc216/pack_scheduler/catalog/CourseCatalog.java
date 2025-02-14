/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.catalog;

import java.io.FileNotFoundException;

import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.io.CourseRecordIO;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * covers catalog functionality from WolfScheduler
 * @author cllentz
 */
public class CourseCatalog {
	/**
	 * stores the course catalog
	 */
	private SortedList<Course> catalog;
	/**
	 * creates a CourseCatalog object, constructing an empty catalog
	 */
	public CourseCatalog() {
		
		newCourseCatalog();
		
	}
	/**
	 * constructs an empty catalog
	 */
	public void newCourseCatalog() {
		
		this.catalog = new SortedList<Course>();
	}
	/**
	 * returns a 2D array of courses in the catalog to be used in the GUI
	 * @return catalog, a 2D array representation of the catalog ArrayList in WolfScheduler
	 */
	public String[][] getCourseCatalog() {
		String[][] catalogArray = new String[this.catalog.size()][5];
		for (int i = 0; i < catalogArray.length; i++) {
			Course c = catalog.get(i);
			catalogArray[i] = c.getShortDisplayArray();
		}
		return catalogArray;
	}
	/**
	 * get the course from the catalog that has the same name and section as the parameters
	 * @param name name of the course
	 * @param section course section
	 * @return the course object with the corresponding fields above
	 */
	public Course getCourseFromCatalog(String name, String section) {
		
		// iterates through the whole catalog to find a matching course
		for (int i = 0; i < this.catalog.size(); i++) {
			if (this.catalog.get(i).getName().equals(name) && this.catalog.get(i).getSection().equals(section)) {
				return this.catalog.get(i);
			}
		}
		return null;
	}
	/**
	 * creates an empty SortedList for the catalog
	 */
	public void resetCatalog() {
		
		SortedList<Course> blankCatalog = new SortedList<Course>();
		this.catalog = blankCatalog;
	}
	/**
	 * Constructs the catalog by reading in course information
	 * from the given file.  Throws an IllegalArgumentException if the 
	 * file cannot be found.
	 * @throws IllegalArgumentException thrown if the file isn't found
	 * @param fileName file containing list of courses
	 */
	public void loadCoursesFromFile(String fileName) throws IllegalArgumentException {
		try {
			this.catalog = CourseRecordIO.readCourseRecords(fileName);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to read file " + fileName);
		}
	}
	/**
	 * adds a course to the catalog and returns true if successful
	 * @param name name of a Course
	 * @param section section of a Course
	 * @param title title of a Course
	 * @param credits number of credits of the Course
	 * @param instructorId instructorId of a Course
	 * @param enrollmentCap enrollment cap of the Course
	 * @param meetingDays meeting days of a Course
	 * @param startTime start time of the Course
	 * @param endTime end time of the Course
	 * @return true or false
	 */
	public boolean addCourseToCatalog(String name, String title, String section, int credits, String instructorId, int enrollmentCap, String meetingDays, int startTime, int endTime) {
		
		Course course = new Course(name, title, section, credits, instructorId, enrollmentCap, meetingDays, startTime, endTime);
		
		// checks that a course of the same name hasn't been added already
		for (int i = 0; i < this.catalog.size(); i++) {
			Course c = this.catalog.get(i);
			
			if (c.getName().equals(course.getName()) && c.getSection().equals(course.getSection())) {
				return false;
			}
			
		}
		
		// adds the course
		this.catalog.add(course);
		return true;
	}
	/**
	 * removes a course from the student's catalog, returns false if the course is
	 * not in the student's catalog
	 * @param name name of the course
	 * @param section section of the course
	 * @return true or false
	 */
	public boolean removeCourseFromCatalog(String name, String section) {
		// searches the catalog for the class to be removed
		for (int i = 0; i < catalog.size(); i++) {
			
			if (catalog.get(i).getName().equals(name) && catalog.get(i).getSection().equals(section)) {
				
				catalog.remove(i);
				return true;
			}
		}
		return false;
	}
	/**
	 * exports the catalog into a specified file
	 * @param filename filename where the student's catalog will be saved to
	 * @throws IllegalArgumentException thrown if there are issues saving the file
	 */
	public void saveCourseCatalog(String filename) throws IllegalArgumentException{
		// if the method used to write the course record returns a error, throw an error
		try {
			CourseRecordIO.writeCourseRecords(filename, catalog);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("The file cannot be saved");
		}
	}
}
