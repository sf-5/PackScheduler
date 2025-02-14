/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue implementation using Linked Lists
 * @param <E> the type of element in the queue
 */
public class LinkedQueue<E> implements Queue<E> {
	
	/** the linked list that is used to implement the queue */
	private LinkedAbstractList<E> list;
	
	/**
	 * constructor for the queue that delegates to the LinkedAbstractList and sets a capacity
	 * @param capacity the capacity of the queue
	 */
	public LinkedQueue(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * adds an element to the back of the queue (end of the linked list)
	 * @throws IllegalArgumentException thrown if any issues arise adding the element
	 */
	@Override
	public void enqueue(E element) throws IllegalArgumentException {
		try {
			list.add(list.size(), element);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Capacity has been reached.");
		}
	}
	
	/**
	 * removes and returns the element at the front of the queue (head of the linked list)
	 * @throws NoSuchElementException thrown if the queue is empty
	 * @return the element that was removed
	 */
	@Override
	public E dequeue() throws NoSuchElementException {
		
		if (list.size() == 0) {
			throw new NoSuchElementException("The queue is empty.");
		}
		
		return list.remove(0);
	}
	
	/**
	 * determines if the queue is empty
	 * @return true of the queue is empty and false if it isn't
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0 || list == null;
	}
	
	/**
	 * gets the size of the queue
	 * @return the size of the queue
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * sets the queue's capacity
	 * @throws IllegalArgumentException thrown if the capacity value is invalid
	 */
	@Override
	public void setCapacity(int capacity) throws IllegalArgumentException {
		
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity value to be set.");
		}
		
		list.setCapacity(capacity);		
	}
	
	/**
	 * Gets the queue's capacity
	 * @return capacity of queue
	 */
	public int getCapacity() {
		return list.getCapacity();
	}

}
