package edu.ncsu.csc216.pack_scheduler.util;

import java.util.EmptyStackException;

/**
 * A stack based on ArrayList.
 * 
 * @author Sam Fishburn, Amin Mohamed, Cooper Lentz
 * @param <E> The type of the object being stored.
 */
public class ArrayStack<E> implements Stack<E> {
	/** The ArrayList backing the ArrayStack. */
	private ArrayList<E> backer;
	/** The capacity of the stack. */
	private int capacity;

	/**
	 * Constructs a new stack with the given capacity.
	 * 
	 * @param capacity The capacity of the new stack.
	 */
	public ArrayStack(int capacity) {
		this.backer = new ArrayList<E>();
		this.capacity = capacity;
	}

	/**
	 * Adds the element to the top of the stack.
	 * 
	 * @param element The element to add.
	 * @throws IllegalArgumentException if the capacity is reached.
	 */
	@Override
	public void push(E element) throws IllegalArgumentException {
		if (this.capacity == this.backer.size()) {
			throw new IllegalArgumentException("Capacity reached.");
		}
		this.backer.add(element);
	}

	/**
	 * Removes and returns the element at the top of the stack.
	 * 
	 * @return The element at the top of the stack.
	 * @throws EmptyStackException If the stack is empty.
	 */
	@Override
	public E pop() throws EmptyStackException {
		if (this.backer.size() == 0) {
			throw new EmptyStackException();
		}
		
		return this.backer.remove(this.backer.size() - 1);
	}

	/**
	 * Returns true if the stack is empty.
	 * 
	 * @return True if the stack is empty.
	 */
	@Override
	public boolean isEmpty() {
		return this.backer.isEmpty();
	}

	/**
	 * Returns the number of elements in the stack.
	 * 
	 * @return The number of elements in the stack.
	 */
	@Override
	public int size() {
		return this.backer.size();
	}

	/**
	 * Sets the stack’s capacity.
	 * 
	 * @param capacity The capacity to set.
	 * @throws IllegalArgumentException If the actual parameter is negative or if it
	 *                                  is less than the number of elements in the
	 *                                  stack.
	 */
	@Override
	public void setCapacity(int capacity) throws IllegalArgumentException {
		if (capacity < 0 || capacity < this.backer.size()) {
			throw new IllegalArgumentException("Invalid capacity.");
		}
		this.capacity = capacity;
	}

}
