/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * an implementation of the stack data structure using an ArrayList
 * @param <E> the type of element that the list is comprised of
 */
public class LinkedStack<E> implements Stack<E> {
	
	/** the abstract list that is implemented as a stack */
	private LinkedAbstractList<E> list;
	
	/**
	 * constructor for LinkedStack that delegates to LinkedAbstractList and sets the capacity
	 * @param capacity the capacity of the stack
	 */
	public LinkedStack(int capacity) {
		list = new LinkedAbstractList<E>(capacity);
	}
	
	/**
	 * adds an element to the top of the stack
	 */
	@Override
	public void push(E element) throws IllegalArgumentException {
		try {
			list.add(0, element);
		}
		catch (Exception e) {
			throw new IllegalArgumentException("Capacity has been reached.");
		}
	}
	
	/**
	 * removes the top element in the stack
	 * @throws EmptyStackException thrown if the stack is empty
	 * @return the element that was removed from the stack
	 */
	@Override
	public E pop() throws EmptyStackException {
		
		if (list.size() == 0) {
			throw new EmptyStackException();
		}
		return list.remove(0);	
	}

	/**
	 * determines if the stack is empty
	 * @return true if the stack is empty and false if it isn't
	 */
	@Override
	public boolean isEmpty() {
		return list.size() == 0 || list == null;
	}
	
	/**
	 * gets the size of the stack
	 * @return the size of the stack
	 */
	@Override
	public int size() {
		return list.size();
	}
	
	/**
	 * sets the capacity of the stack
	 */
	@Override
	public void setCapacity(int capacity) throws IllegalArgumentException {
		
		if (capacity < 0 || capacity < list.size()) {
			throw new IllegalArgumentException("Invalid capacity to be set.");
		}
		
		list.setCapacity(capacity);
	}	
}