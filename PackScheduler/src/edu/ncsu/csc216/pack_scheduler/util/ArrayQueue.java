package edu.ncsu.csc216.pack_scheduler.util;

import java.util.NoSuchElementException;

/**
 * Queue implementation using ArrayLists
 * @author Amin Mohamed
 * @param <E> the type of object being stored
 */
public class ArrayQueue<E> implements Queue<E> {

	/** The ArrayList the Queue is based on */
	ArrayList<E> list = new ArrayList<E>();
	
	/** Capacity of the ArrayQueue */
	int capacity;
	
	/**
	 * Creates a new ArrayQueue
	 * @param cap the capacity of the ArrayQueue
	 */
	public ArrayQueue(int cap){
		setCapacity(cap);
	}

	/**
	 * Adds an element to the back of the Queue
	 * @param element the element to be added
	 * @throws IllegalArgumentException if capacity has been reached
	 */
	@Override
	public void enqueue(E element) { 
		if(capacity == size()) {
			throw new IllegalArgumentException("Capacity has been reached");
		}
		list.add(element);
	}
	
	/**
	 * Removes the element at the front of the queue
	 * @return the element that was removed
	 * @throws NoSuchElementException if queue is empty
	 */
	@Override
	public E dequeue() {
		if(this.isEmpty()) {
			throw new NoSuchElementException("Queue is empty.");
		} else {
			return list.remove(0);
		}
	}
	
	/**
	 * Whether or not a Queue is empty
	 * @return true if queue is empty, else false
	 */
	@Override
	public boolean isEmpty() {
		return size() == 0;
	}
	
	/**
	 * The size of the Queue
	 * @return number of elements in the queue
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * Sets the capacity of the queue
	 * @param newCap the queue's new capacity
	 * @throws IllegalArgumentException if capacity is negative or less than number of elements in queue
	 */
	@Override
	public void setCapacity(int newCap) throws IllegalArgumentException {
		
		if (newCap < 0 || newCap < size()) {
			throw new IllegalArgumentException("Invalid capacity to be set.");
		}
		this.capacity = newCap;
	}
	
}