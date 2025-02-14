package edu.ncsu.csc216.pack_scheduler.user;

import java.util.Objects;

/**
 * Represents a general User in the PackScheduler system, including fields for 
 * first name, last name, ID, email, and password. This class provides methods 
 * for setting and getting these fields with validation.
 */
public abstract class User {

	/**
	 * first name for student
	 */
	private String firstName;
	/**
	 * last name for student
	 */
	private String lastName;
	/**
	 * id of the student
	 */
	private String id;
	/**
	 * email of the student
	 */
	private String email;
	/**
	 * password of the student
	 */
	private String password;
	/**
	 * constructor for User following one path of construction paradigm
	 * @param firstName first name of the user
	 * @param lastName last name of the user
	 * @param id id of the user
	 * @param email email of the user
	 * @param password password of the user
	 */
	public User(String firstName, String lastName, String id, String email, String password) {
		
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setId(id);
		this.setEmail(email);
		this.setPassword(password);
		
	}

	/**
	 * Sets the student's password
	 * @param hashPW -  the student's new hashed password
	 * @throws IllegalArgumentException if password is invalid
	 */
	public void setPassword(String hashPW) {
		if (hashPW == null || hashPW.length() == 0) {
			throw new IllegalArgumentException("Invalid password");
		}
	this.password = hashPW;
	}

	/**
	 * sets the email of the student
	 * @param email - email of the student
	 * @throws IllegalArgumentException if email is invalid
	 */
	public void setEmail(String email) {
		
		// check that the email if not null or empty
		if (email == null || email.length() == 0) {
			throw new IllegalArgumentException("Invalid email");
		}
		int period = -1;
		int atSymbol = -1;
		
		for (int i = 0; i < email.length(); i++) {
			if (email.substring(i, i + 1).equals(".")) {
				period = i;
			}
			if (email.substring(i, i + 1).equals("@")) {
				atSymbol = i;
			}
		}
		if (period == -1 || atSymbol == -1 || period < atSymbol) {
			throw new IllegalArgumentException("Invalid email");
		}
		
		this.email = email;
	}

	/**
	 * sets the id of the student
	 * @param id id of the student
	 * @throws IllegalArgumentException if id is invalid
	 */
	protected void setId(String id) {
	
		if (id == null || id.length() == 0) {
			throw new IllegalArgumentException("Invalid id");
		}
	this.id = id;
	}

	/**
	 * sets the last name of the student
	 * @param lastName last name of the student
	 * @throws IllegalArgumentException if last name is invalid
	 */
	public void setLastName(String lastName) {
		
		if (lastName == null || lastName.length() == 0) {
			throw new IllegalArgumentException("Invalid last name");
		}
	this.lastName = lastName;
	}

	/**
	 * sets the first name of the student
	 * @param firstName first name of the student
	 * @throws IllegalArgumentException if first name is invalid
	 */
	public void setFirstName(String firstName) {
		
		if (firstName == null || firstName.length() == 0) {
			throw new IllegalArgumentException("Invalid first name");
		}
	this.firstName = firstName;
	}

	/**
	 * gets the hashed password
	 * @return password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * gets the email
	 * @return email
	 */
	public String getEmail() {
		return this.email;
	}

	/**
	 * gets first name
	 * @return firstName
	 */
	public String getFirstName() {
		return this.firstName;
	}

	/**
	 * gets last name
	 * @return lastName
	 */
	public String getLastName() {
		return this.lastName;
	}

	/**
	 * gets Id
	 * @return id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * overridden method to generate hash code
	 */
	@Override
	public int hashCode() {
		return Objects.hash(email, firstName, id, lastName, password);
	}
	/**
	 * overridden method to compare two objects
	 * @param obj object to compare
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(email, other.email) && Objects.equals(firstName, other.firstName)
				&& Objects.equals(id, other.id) && Objects.equals(lastName, other.lastName)
				&& Objects.equals(password, other.password);
	}
	
	
}