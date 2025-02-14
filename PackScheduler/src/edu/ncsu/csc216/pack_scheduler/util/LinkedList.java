package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A custom, double linked list that uses an iterator to improve efficiency.
 * Doesn't allow duplicate values.
 * 
 * @param <E> The type of the object being stored in the linked list.
 * @author Sam Fishburn, Amin Mohamed, and Cooper Lentz
 */
public class LinkedList<E> extends AbstractSequentialList<E> {
	/** The front of node of the list. */
	private ListNode front;
	/** The back node of the list. */
	private ListNode back;
	/** The size of the list. */
	private int size;

	/**
	 * Constructs a new LinkedList object.
	 */
	public LinkedList() {
		this.front = new ListNode(null);
		this.back = new ListNode(null);

		this.front.next = this.back;
		this.back.prev = this.front;

		this.size = 0;
	}

	/**
	 * Returns the size of the list.
	 * 
	 * @return The size of the list.
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Adds a new element to the list at a given index.
	 * 
	 * @param index The index the element should be added at.
	 * @param e     The data to be added
	 * @throws IllegalArgumentException If the data is already in the list.
	 */
	@Override
	public void add(int index, E e) throws IllegalArgumentException {
		if (this.contains(e)) {
			throw new IllegalArgumentException();
		} else {
			super.add(index, e);
		}
	}
	
	/**
	 * Sets the element at the given index to new data.
	 * 
	 * @param index The index of the element to be changed.
	 * @param e The data the old data should be changed to.
	 * @return The original data at that index.
	 * @throws IllegalArgumentException If the data is already in the list.
	 */
	@Override
	public E set(int index, E e) throws IllegalArgumentException {
		if (this.contains(e)) {
			throw new IllegalArgumentException();
		} else {
			return super.set(index, e);
		}
	}

	/**
	 * Returns a list iterator at the given index.
	 * 
	 * @param index The index that the list iterator should start at.
	 * @return The list iterator.
	 */
	@Override
	public ListIterator<E> listIterator(int index) throws IndexOutOfBoundsException {
		return new LinkedListIterator(index);
	}

	/**
	 * LinkedList Iterator
	 */
	private class LinkedListIterator implements ListIterator<E> {
		/** The previous node in the list. */
		public ListNode previous;
		/** The next node in the list. */
		public ListNode next;
		/** The index of the previous node in the list. */
		public int previousIndex;
		/** The index of the next node in the list. */
		public int nextIndex;
		/** The last listnode retrieved by the iterator. */
		public ListNode lastRetrieved;

		/**
		 * Constructs a new ListIterator object.
		 * 
		 * @param index The index of the next node of the iterator.
		 * @throws IndexOutOfBoundsException If the index is invalid.
		 */
		public LinkedListIterator(int index) throws IndexOutOfBoundsException {
			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}

			ListNode current = front;

			for (int i = 0; i < index; i++) {
				current = current.next;
			}

			this.previous = current;
			this.next = this.previous.next;
			this.previousIndex = index - 1;
			this.nextIndex = index;
			this.lastRetrieved = null;
		}

		/**
		 * Returns whether there is a next item in the list.
		 * 
		 * @return Whether there is a next item in the list.
		 */
		@Override
		public boolean hasNext() {
			System.out.println(this.next.data);
			return this.next.data != null;
		}

		/**
		 * Returns the data of the next node in the list.
		 * 
		 * @return The data of the next node in the list.
		 * @throws NoSuchElementException If the previous element is the end of the
		 *                                list.
		 */
		@Override
		public E next() throws NoSuchElementException {
			if (this.next.data == null) {
				throw new NoSuchElementException();
			} else {
				E data = this.next.data;

				this.lastRetrieved = this.next;
				this.previous = this.next;
				this.next = this.next.next;

				return data;
			}
		}

		/**
		 * Returns whether there is a previous item in the list.
		 * 
		 * @return Whether there is a previous item in the list.
		 */
		@Override
		public boolean hasPrevious() {
			return this.previous.data != null;
		}

		/**
		 * Returns the data in the previous node, and moves the iterator to the previous
		 * index.
		 * 
		 * @return The data in the previous node.
		 * @throws NoSuchElementException If the next element is the start of the list.
		 */
		@Override
		public E previous() throws NoSuchElementException {
			if (this.previous.data == null) {
				throw new NoSuchElementException();
			} else {
				E data = this.previous.data;

				this.lastRetrieved = this.previous;
				this.next = this.previous;
				this.previous = this.previous.prev;

				return data;
			}
		}

		/**
		 * Returns the index of the next item in the list.
		 * 
		 * @return The index of the next item in the list.
		 */
		@Override
		public int nextIndex() {
			return this.nextIndex;
		}

		/**
		 * Returns the index of the previous item in the list.
		 * 
		 * @return The index of the previous item in the list.
		 */
		@Override
		public int previousIndex() {
			return this.previousIndex;
		}

		@Override
		public void remove() {
			if (this.lastRetrieved == null) {
				throw new IllegalStateException();
			} else if (this.lastRetrieved == this.previous) {
				this.previous = this.previous.prev;
			} else if (this.lastRetrieved == this.next) {
				this.next = this.next.next;
			}
			
			this.previous.next = this.next;
			this.lastRetrieved = null;
			size--;
		}

		/**
		 * Sets the data of the last node called to new data.
		 * 
		 * @param e The new data to set.
		 * @throws IllegalStateException If there hasn't been a call to the iterator
		 *                               since the last addition or removal to the list.
		 * @throws NullPointerException  If the data is null.
		 */
		@Override
		public void set(E e) throws IllegalStateException, NullPointerException {
			if (this.lastRetrieved == null) {
				throw new IllegalStateException();
			} else if (e == null) {
				throw new NullPointerException();
			} else {
				this.lastRetrieved.data = e;
			}
		}

		/**
		 * Adds a new node between the previous and next nodes. Replaces previous with
		 * the new node, and sets the last node accessed to null.
		 * 
		 * @param e The data to be added.
		 * @throws NullPointerException If the data passed in is null.
		 */
		@Override
		public void add(E e) throws NullPointerException {
			if (e == null) {
				throw new NullPointerException();
			}

			this.previous.next = new ListNode(e, this.previous, this.next);
			this.previous = this.previous.next;
			this.lastRetrieved = null;
			size++;
		}

	}

	/**
	 * A node for the custom linked list, containing data, the next node in the
	 * list, and the previous node in the list.
	 * 
	 * @author Sam Fishburn, Amin Mohamed, and Cooper Lentz
	 */
	private class ListNode {
		/** The data being stored. */
		public E data;
		/** The next node in the linked list. */
		public ListNode next;
		/** The previous node in the list. */
		public ListNode prev;

		/**
		 * Constructs a new ListNode, with just the data.
		 * 
		 * @param data The data to set.
		 */
		public ListNode(E data) {
			this.data = data;
		}

		/**
		 * Constructs a new ListNode, with all fields.
		 * 
		 * @param data The data to set.
		 * @param prev The previous node in the list.
		 * @param next The next node in the list.
		 */
		public ListNode(E data, ListNode prev, ListNode next) {
			this.data = data;
			this.next = next;
			this.prev = prev;
		}
	}
}
