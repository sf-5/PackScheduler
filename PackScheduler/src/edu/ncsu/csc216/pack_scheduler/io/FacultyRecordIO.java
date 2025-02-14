package edu.ncsu.csc216.pack_scheduler.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Manages reading and writing Faculty records 
 * to and from a file
 * @author Amin Mohamed
 */
public class FacultyRecordIO {
	
	/**
	 * reads the faculty records from a valid file and returns a LinkedList of Faculty members
	 * @param fileName name of the file for records to be read from
	 * @return faculty a list of Faculty objects read from the input file
	 * @throws FileNotFoundException is thrown if no valid file is found
	 */
	public static LinkedList<Faculty> readFacultyRecords(String fileName) throws FileNotFoundException {
		try {
			Scanner s = new Scanner(new FileInputStream(fileName));
			LinkedList<Faculty> faculty = new LinkedList<Faculty>();
			while (s.hasNextLine()) {
				Faculty lineResult = readFaculty(s.nextLine());
				if (lineResult != null) {
					faculty.add(lineResult);
				}
			}
			s.close();
			return faculty;
		}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Unable to read file " + fileName);
		}
	}
	/**
	 * a helper method for the readFacultyRecords() method that reads an individual line
	 * @param nextLine takes in a line from the student records file
	 * @return Faculty a Faculty object created from a line of the faculty records file
	 */
	private static Faculty readFaculty(String nextLine) {
		Scanner s = new Scanner(nextLine);
		try {
			Faculty faculty = null;
			s.useDelimiter(",");
			
			String firstName = s.next();
			String lastName = s.next();
			String id = s.next();
			String email = s.next();
			String password = s.next();
			int maxCourses = s.nextInt();
				
			s.close();
			faculty = new Faculty(firstName, lastName, id, email, password, maxCourses);
			return faculty;		
		}
		catch (Exception e) {
			s.close();
			return null;
		}
	}
	
	/**
	 * writes Faculty records to a specified output file from a LinkedList of Faculty objects
	 * 
	 * @param fileName file to write the faculty records
	 * @param facultyDirectory a LinkedList of Faculty objects
	 * @throws IOException is thrown if unable to write to the specified file
	 */
	public static void writeFacultyRecords(String fileName, LinkedList<Faculty> facultyDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for(int i = 0; i < facultyDirectory.size(); i++) {
			Faculty faculty = facultyDirectory.get(i);
			fileWriter.println(faculty);
		}
		fileWriter.close();
	}
}
