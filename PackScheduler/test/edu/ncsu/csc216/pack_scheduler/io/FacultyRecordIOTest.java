package edu.ncsu.csc216.pack_scheduler.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.fail;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.pack_scheduler.user.Faculty;
import edu.ncsu.csc216.pack_scheduler.util.LinkedList;

/**
 * Tests FacultyRecordIO
 * @author Amin Mohamed
 */
class FacultyRecordIOTest {

	/** Path to valid student records file */
	private String facultyRecordsFile = "test-files/faculty_records.txt"; 
	/** Path to test file that will be written to */
	private String actualRecordsFile = "test-files/actual_faculty_records.txt";
	/** Path to file containing expected results of writing to actualRecordsFile */
	private String expectedRecordsFile = "test-files/expected_faculty_records.txt";
	
	/** expected record of faculty #1 */
	private String validFaculty0 = "Ashely,Witt,awitt,mollis@Fuscealiquetmagna.net,pw,2";
	/** expected record of faculty #2 */
	private String validFaculty1 = "Fiona,Meadows,fmeadow,pharetra.sed@et.org,pw,3";
	/** expected record of faculty #3 */
	private String validFaculty2 = "Brent,Brewer,bbrewer,sem.semper@orcisem.co.uk,pw,1";
	/** expected record of faculty #4 */
	private String validFaculty3 = "Halla,Aguirre,haguirr,Fusce.dolor.quam@amalesuadaid.net,pw,3";
	/** expected record of faculty #5 */
	private String validFaculty4 = "Kevyn,Patel,kpatel,risus@pellentesque.ca,pw,1";
	/** expected record of faculty #6 */
	private String validFaculty5 = "Elton,Briggs,ebriggs,arcu.ac@ipsumsodalespurus.edu,pw,3";
	/** expected record of faculty #7 */
	private String validFaculty6 = "Norman,Brady,nbrady,pede.nonummy@elitfermentum.co.uk,pw,1";
	/** expected record of faculty #8 */
	private String validFaculty7 = "Lacey,Walls,lwalls,nascetur.ridiculus.mus@fermentum.net,pw,2";

	/** Array of valid, SORTED student records (sorted by first name)*/
	private String [] validFacultyList = {validFaculty0, validFaculty1, validFaculty2, validFaculty3, validFaculty4, validFaculty5, 
			validFaculty6, validFaculty7};

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
	        
	        for (int i = 0; i < validFacultyList.length; i++) {
	            validFacultyList[i] = validFacultyList[i].replace(",pw,", "," + hashPW + ",");
	        }
	    } catch (NoSuchAlgorithmException e) {
	        fail("Unable to create hash during setup");
	    }
	}
	
	/**
	 * Tests readFacultyRecords
	 */
	@Test
	public void testReadFacultyRecords() throws FileNotFoundException{
		LinkedList<Faculty> validFaculty = FacultyRecordIO.readFacultyRecords(facultyRecordsFile);
		// make sure Faculty LinkedList is the right size
		assertEquals(validFacultyList.length, validFaculty.size());			
		// make sure Students are in the right order
		for(int i = 0; i < validFaculty.size(); i++) {
			Faculty f = validFaculty.get(i);
			assertEquals(validFacultyList[i], f.toString());
		}

		// make sure FileNotFoundException is thrown if the file doesn't exist
		assertThrows(FileNotFoundException.class, () -> StudentRecordIO.readStudentRecords("does-not-exist.txt"));
	}

	/**
	 * Tests writeFacultyRecords
	 */
	@Test
	public void testWriteStudentRecords() {
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		faculty.add(new Faculty("Fiona", "Meadows", "fmeadow", "pharetra.sed@et.org", hashPW, 3));
		faculty.add(new Faculty("Brent", "Brewer", "bbrewer", "sem.semper@orcisem.co.uk", hashPW, 1));
		try {
			FacultyRecordIO.writeFacultyRecords(actualRecordsFile, faculty);
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
		LinkedList<Faculty> faculty = new LinkedList<Faculty>();
		faculty.add(new Faculty("Ashely", "Witt", "awitt", "mollis@Fuscealiquetmagna.net", hashPW, 2));
		Exception exception = assertThrows(IOException.class, 
				() -> FacultyRecordIO.writeFacultyRecords("/home/sesmith5/actual_student_records.txt", faculty));
		assertEquals("/home/sesmith5/actual_student_records.txt (No such file or directory)", exception.getMessage());
	}


}
