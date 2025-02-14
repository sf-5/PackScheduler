/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * a custom implementation of LinkedList that extends the AbstractList class
 * 
 * @param <E> the data type elements the list is constructed of
 */
public class LinkedAbstractList<E> extends AbstractList<E> {

	private class ListNode {

		/** the data in the node */
		E data;

		/** the next node in the list */
		ListNode next;

		/**
		 * constructs a ListNode object from input data
		 * 
		 * @param data data to be set for the node
		 */
		public ListNode(E data) {
			this.data = data;
		}

		/**
		 * constructs a ListNode object from input data and a pointer to the next node
		 * 
		 * @param data data to be set for the node
		 * @param next the next node in the list
		 */
		public ListNode(E data, ListNode next) {
			this.data = data;
			this.next = next;
		}
	}

	/** the size of the list */
	private int size = 0;

	/** the capacity of the list */
	private int capacity;

	/** the node at the front of the LinkedAbstractList */
	private ListNode front = null;

	/** the node at the back of the LinkedAbstractList */
	private ListNode back = null;

	/**
	 * constructs a LinkedAbstractList and initializes its state
	 * 
	 * @param capacity the capacity of the list
	 */
	public LinkedAbstractList(int capacity) {
		setCapacity(capacity);
	}

	/**
	 * Gets the capacity
	 * 
	 * @return capacity of LinkedAbstractList
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * sets the capacity for the list
	 * 
	 * @param capacity the capacity of the list
	 * @throws IllegalArgumentException thrown if the parameter passed in is less
	 *                                  than 0 or if the capacity is less than the
	 *                                  size
	 */
	public void setCapacity(int capacity) throws IllegalArgumentException {

		if (capacity < 0) {
			throw new IllegalArgumentException("LinkedAbstractList could not be created.");
		}

		this.capacity = capacity;

		if (this.capacity < size) {
			throw new IllegalArgumentException("LinkedAbstractList could not be created.");
		}
	}

	/**
	 * adds an element to the list at the given index
	 * 
	 * @param index   the index for the element to be added at
	 * @param element the element to be added to the list
	 * @throws IllegalArgumentException  thrown if the size is equal to the capacity
	 *                                   or if the element is already in the list
	 * @throws NullPointerException      thrown if the element to add is null
	 * @throws IndexOutOfBoundsException thrown if the index is out of range
	 */
	@Override
	public void add(int index, E element)
			throws IllegalArgumentException, NullPointerException, IndexOutOfBoundsException {

		if (size == capacity) {
			throw new IllegalArgumentException("No more room left.");
		}

		if (element == null) {
			throw new NullPointerException("Element to be added is null.");
		}

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException("Index to be added at is out of range.");
		}

		for (int i = 0; i < size; i++) {
			if (this.get(i).equals(element)) {
				throw new IllegalArgumentException(
						"The element to add is a duplicate of an element already in the list.");
			}
		}

		if (front == null) {
			front = new ListNode(element);
			back = front;
		} else if (index == 0) {
			ListNode current = new ListNode(element, front);
			front = current;
		} else if (index == size) {
			back.next = new ListNode(element, null);
			back = back.next;
		} else {
			ListNode current = front;

			for (int i = 0; i < size; i++) {
				if (i == index - 1) {
					current.next = new ListNode(element, current.next);
					break;
				}
				current = current.next;
			}
		}

		size += 1;

	}

	/**
	 * removes an element to the list at the given index
	 * 
	 * @param index the index for the element to be added at
	 * @throws IndexOutOfBoundsException if the index to be removed at is out of
	 *                                   range
	 */
	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index to be removed at is out of range.");
		}
		// Check for the first element
		if (index == 0) {
			E removedElement = front.data;
			front = front.next;
			size--;
			if (size == 1) {
				back = front;
			}
			return removedElement;
		}
		ListNode current = front;
		// Iterating to the node before the removedElement
		for (int i = 0; i < index - 1; i++) {
			current = current.next;
		}

		E removedElement = current.next.data;
		current.next = current.next.next;
		if (index == size) {
			back = current;
		}
		size--;
		return removedElement;
	}

	/**
	 * sets the element at the specified index to the specified new element
	 * 
	 * @param index   index to be set at
	 * @param element new element to be set at the given index
	 * @return the removed element
	 * @throws NullPointerException      thrown if the element to be set is null
	 * @throws IndexOutOfBoundsException thrown if the index is out of range
	 * @throws IllegalArgumentException  thrown if the element is duplicate of one
	 *                                   already in the list
	 */
	public E set(int index, E element)
			throws NullPointerException, IndexOutOfBoundsException, IllegalArgumentException {

		if (element == null) {
			throw new NullPointerException("Element to be added is null.");
		}

		if (index < 0 || index > size || front == null) {
			throw new IndexOutOfBoundsException("Index to be added at is out of range.");
		}

		for (int i = 0; i < size; i++) {
			if (this.get(i).equals(element)) {
				throw new IllegalArgumentException(
						"The element to add is a duplicate of an element already in the list.");
			}
		}

		ListNode current = front;
		E oldElement;

		for (int i = 0; i < size; i++) {

			if (i == index) {
				oldElement = current.data;
				current.data = element;
				return oldElement;
			}

			current = current.next;
		}

		return null;
	}

	/**
	 * gets the size of the list
	 * 
	 * @return the size of the list
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * gets the element of the list at the given index
	 * 
	 * @return the element of the list at the given index
	 * @throws IndexOutOfBoundsException thrown if the index is out of range
	 */
	@Override
	public E get(int index) throws IndexOutOfBoundsException {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Index is out of range.");
		}

		ListNode current = front;

		for (int i = 0; i < size; i++) {

			if (i == index) {
				return current.data;
			}

			current = current.next;
		}

		return null;
	}

}
