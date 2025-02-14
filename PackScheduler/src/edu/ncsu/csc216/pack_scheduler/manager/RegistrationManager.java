package edu.ncsu.csc216.pack_scheduler.manager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Properties;

import edu.ncsu.csc216.pack_scheduler.catalog.CourseCatalog;
import edu.ncsu.csc216.pack_scheduler.course.Course;
import edu.ncsu.csc216.pack_scheduler.course.roll.CourseRoll;
import edu.ncsu.csc216.pack_scheduler.directory.FacultyDirectory;
import edu.ncsu.csc216.pack_scheduler.directory.StudentDirectory;
import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc216.pack_scheduler.user.User;
import edu.ncsu.csc216.pack_scheduler.user.schedule.Schedule;

/**
 * The RegistrationManager handles course catalogs, student directories, and
 * user credentials to control access to features within the system. This
 * includes hashing passwords, managing logged-in users, and enrolling students
 * in courses.
 */
public class RegistrationManager {

	/** instance field implementing singleton pattern **/
	private static RegistrationManager instance = new RegistrationManager();
	/** course catalog **/
	private CourseCatalog courseCatalog = new CourseCatalog();
	/** directory of students **/
	private StudentDirectory studentDirectory = new StudentDirectory();
	/** Directory of Faculty **/
	private FacultyDirectory facultyDirectory = new FacultyDirectory();
	/** administrative user (kept separate from student) **/
	private User registrar;
	/** user of the manager **/
	private User currentUser;

	/** Hashing algorithm */
	private static final String HASH_ALGORITHM = "SHA-256";
	/** gives the name of the properties file to be used in testing */
	private static final String PROP_FILE = "registrar.properties";

	/**
	 * constructor is private so that no other class can control the creation of an
	 * instance
	 */
	private RegistrationManager() {
		createRegistrar();
	}

	/**
	 * Adds a course to a given faculty member.
	 *
	 * @param course  The course to be added to the faculty member.
	 * @param faculty The faculty member to be changed.
	 * @return If the update is successful.
	 */
	public boolean addFacultyToCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser.equals(this.registrar)) {
			return faculty.getSchedule().addCourseToSchedule(course);
		} else {
			throw new IllegalArgumentException("User is not the registrar.");
		}
	}

	/**
	 * Removes a course from a given faculty member.
	 *
	 * @param course  The course to be removed from the faculty member.
	 * @param faculty The faculty member to be changed.
	 * @return If the update is successful.
	 */
	public boolean removeFacultyFromCourse(Course course, Faculty faculty) {
		if (currentUser != null && currentUser.equals(this.registrar)) {
			return faculty.getSchedule().removeCourseFromSchedule(course);
		} else {
			throw new IllegalArgumentException("User is not the registrar.");
		}
	}

	/**
	 * Resets given faculty member's schedule.
	 *
	 * @param faculty The faculty member to be changed.
	 */
	public void resetFacultySchedule(Faculty faculty) {
		if (currentUser != null && currentUser.equals(this.registrar)) {
			faculty.getSchedule().resetSchedule();
		} else {
			throw new IllegalArgumentException("User is not the registrar.");
		}
	}

	/**
	 * Method that creates a registrar
	 * 
	 * @throws IllegalArgumentException thrown if there is any problem with
	 *                                  input/output
	 */
	private void createRegistrar() throws IllegalArgumentException {
		Properties prop = new Properties();

		try (InputStream input = new FileInputStream(PROP_FILE)) {
			prop.load(input);

			String hashPW = hashPW(prop.getProperty("pw"));

			registrar = new Registrar(prop.getProperty("first"), prop.getProperty("last"), prop.getProperty("id"),
					prop.getProperty("email"), hashPW);
		} catch (IOException e) {
			throw new IllegalArgumentException("Cannot create registrar.");
		}
	}

	/**
	 * Hashes a user's password
	 * 
	 * @param pw password that is supposed to be hashed
	 * @return hashed password
	 * @throws IllegalArgumentException if the password cannot be hashed
	 */
	private String hashPW(String pw) throws IllegalArgumentException {
		try {
			MessageDigest digest1 = MessageDigest.getInstance(HASH_ALGORITHM);
			digest1.update(pw.getBytes());
			return Base64.getEncoder().encodeToString(digest1.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalArgumentException("Cannot hash password");
		}
	}

	/**
	 * gets the instance of registration manager (following singleton pattern)
	 * 
	 * @return instance of RegistrationManager
	 */
	public static RegistrationManager getInstance() {
		return instance;
	}

	/**
	 * gets the course catalog
	 * 
	 * @return course catalog
	 */
	public CourseCatalog getCourseCatalog() {
		return courseCatalog;
	}

	/**
	 * gets the student directory
	 * 
	 * @return student directory
	 */
	public StudentDirectory getStudentDirectory() {
		return studentDirectory;
	}

	/**
	 * gets the Faculty directory
	 * 
	 * @return faculty directory
	 */
	public FacultyDirectory getFacultyDirectory() {
		return facultyDirectory;
	}

	/**
	 * performs the login operation
	 * 
	 * @param id       id of the user
	 * @param password password of the user
	 * @return true or false depending on the success of the operation
	 * @throws IllegalArgumentException thrown if the user attempting to login does
	 *                                  not exist
	 */
	public boolean login(String id, String password) throws IllegalArgumentException {

		String localHashPW = hashPW(password);

		// Check if a user is already logged in
		if (currentUser != null) {
			return false; // Prevents login if there's already a logged-in user
		}

		// Logs in registrar if the credentials match
		if (registrar.getId().equals(id)) {
			if (registrar.getPassword().equals(localHashPW)) {
				currentUser = registrar;
				return true;
			} else {
				return false;
			}
		} else {
			Student s = studentDirectory.getStudentById(id);

			if (s == null) {
				Faculty f = facultyDirectory.getFacultyById(id);
				if (f == null) {
					throw new IllegalArgumentException("User doesn't exist.");
				}
				// Logs in as a Faculty member if credentials match
				if (f.getPassword().equals(localHashPW)) {
					currentUser = f;
					return true;
				}
			}
			// Logs in a student if the credentials match
			if (s != null && s.getPassword().equals(localHashPW)) {
				currentUser = s;
				return true;
			}
		}

		return false;
	}

	/**
	 * performs the logout operation
	 */
	public void logout() {
		currentUser = null;
	}

	/**
	 * gets the current user of the registration manager
	 * 
	 * @return the current user of the registration manager
	 */
	public User getCurrentUser() {
		return currentUser;
	}

	/**
	 * makes a new course catalog and student directory, effectively clearing the
	 * data
	 */
	public void clearData() {
		courseCatalog.newCourseCatalog();
		studentDirectory.newStudentDirectory();
		facultyDirectory.newFacultyDirectory();
	}

	/**
	 * Returns true if the logged in student can enroll in the given course.
	 * 
	 * @param c Course to enroll in
	 * @return true if enrolled
	 * @throws IllegalArgumentException if enrolling student into the course action
	 *                                  is illegal
	 */
	public boolean enrollStudentInCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			CourseRoll roll = c.getCourseRoll();

			if (s.canAdd(c) && roll.canEnroll(s)) {
				schedule.addCourseToSchedule(c);
				roll.enroll(s);
				return true;
			}

		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	/**
	 * Returns true if the logged in student can drop the given course.
	 * 
	 * @param c Course to drop
	 * @return true if dropped
	 * @throws IllegalArgumentException if action to drop student from course is
	 *                                  illegal
	 */
	public boolean dropStudentFromCourse(Course c) {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			c.getCourseRoll().drop(s);
			return s.getSchedule().removeCourseFromSchedule(c);
		} catch (IllegalArgumentException e) {
			return false;
		}
	}

	/**
	 * Resets the logged in student's schedule by dropping them from every course
	 * and then resetting the schedule.
	 * 
	 * @throws IllegalArgumentException if resetting schedule action is illegal
	 */
	public void resetSchedule() {
		if (!(currentUser instanceof Student)) {
			throw new IllegalArgumentException("Illegal Action");
		}
		try {
			Student s = (Student) currentUser;
			Schedule schedule = s.getSchedule();
			String[][] scheduleArray = schedule.getScheduledCourses();
			for (int i = 0; i < scheduleArray.length; i++) {
				Course c = courseCatalog.getCourseFromCatalog(scheduleArray[i][0], scheduleArray[i][1]);
				c.getCourseRoll().drop(s);
			}
			schedule.resetSchedule();
		} catch (IllegalArgumentException e) {
			// do nothing
		}
	}

	/**
	 * registrar class, extension of user class does not need to exist outside of
	 * this class, so it is an inner class
	 */
	private static class Registrar extends User {
		/**
		 * Create a registrar user.
		 * 
		 * @param firstName first name of the registrar
		 * @param lastName  last name of the registrar
		 * @param id        id of the registrar
		 * @param email     email of the registrar
		 * @param hashPW    hashed password of the registrar
		 */
		public Registrar(String firstName, String lastName, String id, String email, String hashPW) {
			super(firstName, lastName, id, email, hashPW);
		}
	}
}
