package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue interface
 * 
 * @author Amin Mohamed
 * @param <E> the type being stored in the Queue
 */
public interface Queue<E> {
	/**
	 * Adds an element to the back of the Queue
	 * @param element the element to be added
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	void enqueue(E element) throws IllegalArgumentException;
	
	/**
	 * Removes the element at the front of the queue
	 * @return the element that was removed
	 * @throws NoSuchElementException if queue is empty
	 */
	E dequeue() throws NoSuchElementException;
	
	/**
	 * Whether or not a Queue is empty
	 * @return true if queue is empty, else false
	 */
	boolean isEmpty();
	
	/**
	 * The size of the Queue
	 * @return number of elements in the queue
	 */
	int size();
	
	/**
	 * Sets the capacity of the queue
	 * @param capacity the queue's new capacity
	 * @throws IllegalArgumentException if capacity is negative or less than number of elements in queue
	 */
	void setCapacity(int capacity);
	
}