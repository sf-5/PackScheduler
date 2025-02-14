package edu.ncsu.csc216.pack_scheduler.io;

//import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Student;
import edu.ncsu.csc217.collections.list.SortedList;

/**
 * Tests the StudentRecordIO Class
 * @author Amin Mohamed
 */
class StudentRecordIOTest {
	
	/** Path to valid student records file */
	private String studentRecordsFile = "test-files/student_records.txt"; 
	/** Path to test file that will be written to */
	private String actualRecordsFile = "test-files/actual_student_records.txt";
	/** Path to file containing expected results of writing to actualRecordsFile */
	private String expectedRecordsFile = "test-files/expected_student_records.txt";
	
	/** expected record of student #1 */
	private String validStudent0 = "Zahir,King,zking,orci.Donec@ametmassaQuisque.com,pw,15";
	/** expected record of student #2 */
	private String validStudent1 = "Cassandra,Schwartz,cschwartz,semper@imperdietornare.co.uk,pw,4";
	/** expected record of student #3 */
	private String validStudent2 = "Shannon,Hansen,shansen,convallis.est.vitae@arcu.ca,pw,14";
	/** expected record of student #4 */
	private String validStudent3 = "Demetrius,Austin,daustin,Curabitur.egestas.nunc@placeratorcilacus.co.uk,pw,18";
	/** expected record of student #5 */
	private String validStudent4 = "Raymond,Brennan,rbrennan,litora.torquent@pellentesquemassalobortis.ca,pw,12";
	/** expected record of student #6 */
	private String validStudent5 = "Emerald,Frost,efrost,adipiscing@acipsumPhasellus.edu,pw,3";
	/** expected record of student #7 */
	private String validStudent6 = "Lane,Berg,lberg,sociis@non.org,pw,14";
	/** expected record of student #8 */
	private String validStudent7 = "Griffith,Stone,gstone,porta@magnamalesuadavel.net,pw,17";
	/** expected record of student #9 */
	private String validStudent8 = "Althea,Hicks,ahicks,Phasellus.dapibus@luctusfelis.com,pw,11";
	/** expected record of student #10 */
	private String validStudent9 = "Dylan,Nolan,dnolan,placerat.Cras.dictum@dictum.net,pw,5";

	/** Array of valid, SORTED student records (sorted by first name)*/
	private String [] validStudents = {validStudent3, validStudent6, validStudent4, validStudent5, validStudent2, validStudent8, 
			validStudent0, validStudent9, validStudent1, validStudent7};

	/** Hashed version of the password (to be set during setup) */
	private String hashPW;
	/** Hashing method to hash student passwords */
	private static final String HASH_ALGORITHM = "SHA-256";
	
	/**
	 * Helper method to compare two files for the same contents
	 * @param expFile expected output
	 * @param actFile actual output
	 * Source: https://pages.github.ncsu.edu/engr-csc217/labs/02-lab/02-lab-studentrecordio.html
	 */
	private void checkFiles(String expFile, String actFile) {
		try (Scanner expScanner = new Scanner(new FileInputStream(expFile));
			 Scanner actScanner = new Scanner(new FileInputStream(actFile));) {
			
			while (expScanner.hasNextLine()  && actScanner.hasNextLine()) {
				String exp = expScanner.nextLine();
				String act = actScanner.nextLine();
				assertEquals(exp, act); 
			}
			if (expScanner.hasNextLine()) {
				fail("The expected results expect another line " + expScanner.nextLine());
			}
			if (actScanner.hasNextLine()) {
				fail("The actual results has an extra, unexpected line: " + actScanner.nextLine());
			}
			
			expScanner.close();
			actScanner.close();
		} catch (IOException e) {
			fail("Error reading files.");
		}
	}
	
	/**
	 * Helper method to hash student passwords during setup
	 * Source: https://pages.github.ncsu.edu/engr-csc217/labs/02-lab/02-lab-studentrecordio.html
	 */
	@BeforeEach
	public void setUp() {
	    try {
	        String password = "pw";
	        MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
	        digest.update(password.getBytes());
	        hashPW = Base64.getEncoder().encodeToString(digest.digest());
	        
	        for (int i = 0; i < validStudents.length; i++) {
	            validStudents[i] = validStudents[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	/**
	 * Tests readStudentRecords
	 */
	@Test
	public void testReadStudentRecords() throws FileNotFoundException{
		SortedList<Student> validStudentsList = StudentRecordIO.readStudentRecords(studentRecordsFile);
		// make sure Student ArrayList is the right size
		assertEquals(validStudents.length, validStudentsList.size());			
		// make sure Students are in the right order
		for(int i = 0; i < validStudentsList.size(); i++) {
			Student s = validStudentsList.get(i);
			assertEquals(validStudents[i], s.toString());
		}

		// make sure FileNotFoundException is thrown if the file doesn't exist
		assertThrows(FileNotFoundException.class, () -> StudentRecordIO.readStudentRecords("does-not-exist.txt"));
	}

	/**
	 * Tests writeStudentRecords
	 */
	@Test
	public void testWriteStudentRecords() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		try {
			StudentRecordIO.writeStudentRecords(actualRecordsFile, students);
			// make sure file is written correctly
			checkFiles(expectedRecordsFile, actualRecordsFile);
		} catch (IOException e) {
			fail("Could not write to test file");
		}
	}
	
	/**
	 * Tests if writeStudentRecords throws an IOException 
	 * when writing to a file without permission
	 * Source: https://pages.github.ncsu.edu/engr-csc217/labs/02-lab/02-lab-studentrecordio.html
	 */
	@Test
	public void testWriteStudentRecordsNoPermissions() {
		SortedList<Student> students = new SortedList<Student>();
		students.add(new Student("Zahir", "King", "zking", "orci.Donec@ametmassaQuisque.com", hashPW, 15));
		
		Exception exception = assertThrows(IOException.class, 
				() -> StudentRecordIO.writeStudentRecords("/home/sesmith5/actual_student_records.txt", students));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}

}
