package edu.ncsu.csc216.pack_scheduler.io;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;
import java.io.FileInputStream;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;
/**
 *Handles input and output of student records (writes and reads to/from files, etc.)
 * 
 * @author Cooper Lentz
 * @author Amin Mohamed
 */
public class StudentRecordIO {
	/**
	 * reads in the student records from a valid file and returns a list of student objects
	 * @param fileName name of the file for records to be read from
	 * @return students a list of Student objects read from the input file
	 * @throws FileNotFoundException is thrown if no valid file is found
	 */
	public static SortedList<Student> readStudentRecords(String fileName) throws FileNotFoundException {
		try {
			Scanner s = new Scanner(new FileInputStream(fileName));
			SortedList<Student> students = new SortedList<Student>();
			while (s.hasNextLine()) {
				Student lineResult = readStudent(s.nextLine());
				if (lineResult != null) {
					students.add(lineResult);
				}
			}
			s.close();
			return students;
		}
		catch (FileNotFoundException e) {
			throw new FileNotFoundException("Unable to read file " + fileName);
		}
	}
	/**
	 * a helper method for the readStudentRecords() method that reads an individual line
	 * @param nextLine takes in a line from the student records file
	 * @return student a student object created from a line of the student records file
	 */
	private static Student readStudent(String nextLine) {
		Scanner s = new Scanner(nextLine);
		try {
			Student student = null;
			s.useDelimiter(",");
			
			String firstName = s.next();
			String lastName = s.next();
			String id = s.next();
			String email = s.next();
			String password = s.next();
			int maxCredits = s.nextInt();
				
			s.close();
			student = new Student(firstName, lastName, id, email, password, maxCredits);
			return student;		
		}
		catch (Exception e) {
			s.close();
			return null;
		}
	}
	
	/**
	 * writes student records to a specified output file from a list of student objects
	 * 
	 * @param fileName file to write the student records
	 * @param studentDirectory a list of student objects
	 * @throws IOException is thrown if unable to write to the specified file
	 */
	public static void writeStudentRecords(String fileName, SortedList<Student> studentDirectory) throws IOException {
		PrintStream fileWriter = new PrintStream(new File(fileName));
		for(int i = 0; i < studentDirectory.size(); i++) {
			Student student = studentDirectory.get(i);
			fileWriter.println(student);
		}
		fileWriter.close();
	}

}
