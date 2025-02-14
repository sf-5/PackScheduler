package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * An interface for creating stacks, that can be backed with LinkedLists or
 * ArrayLists.
 * 
 * @author Sam Fishburn, Amin Mohamed, and Cooper Lentz
 * @param <E> The type of the object being stored.
 */
public interface Stack<E> {
	/**
	 * Adds the element to the top of the stack.
	 * 
	 * @param element The element to add.
	 * @throws IllegalArgumentException if the capacity is reached.
	 */
	void push(E element) throws IllegalArgumentException;

	/**
	 * Removes and returns the element at the top of the stack.
	 * 
	 * @return The element at the top of the stack.
	 * @throws EmptyStackException If the stack is empty.
	 */
	E pop() throws EmptyStackException;

	/**
	 * Returns true if the stack is empty.
	 * 
	 * @return True if the stack is empty.
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the stack.
	 * 
	 * @return The number of elements in the stack.
	 */
	int size();

	/**
	 * Sets the stack’s capacity.
	 * 
	 * @param capacity The capacity to set.
	 * @throws IllegalArgumentException If the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  stack.
	 */
	void setCapacity(int capacity) throws IllegalArgumentException;
}
