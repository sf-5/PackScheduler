/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

/**
 * a custom implementation of a recursive linked list that doesn’t allow for null elements 
 * or duplicate elements as defined by the equals() method.
 * @param <E> the type of data stored in the linked list
 */
public class LinkedListRecursive<E> {
	
	/**
	 * a field that keeps track of the size of the list
	 */
	private int size;
	
	/**
	 * the head of the linked list
	 */
	private ListNode front;
	
	/**
	 * the constructor for LinkedListRecursive, which sets the size to zero and the head of the 
	 * list to a null value
	 */
	public LinkedListRecursive() {
		size = 0;
		front = null;
	}
	
	/**
	 * checks if the linked list is empty
	 * @return true if the list is empty and false if it isn't
	 */
	public boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * gets the size of the list
	 * @return the size of the list (from the field)
	 */
	public int size() {
		return size;
	}
	
	/**
	 * adds an element to the end of the linked list
	 * @param element the element to be added
	 * @return true if the element was successfully added
	 * @throws IllegalArgumentException thrown if the element already exists in the list
	 */
	public boolean add(E element) throws IllegalArgumentException {
		
		if (this.contains(element)) {
			throw new IllegalArgumentException("The element is already in the list.");
		}
		
		if (front == null) {
			front = new ListNode(element, null);
			size++;
			return true;
		}
		
		return front.add(element);
	}
	
	/**
	 * adds an element to a specific index in the list
	 * @param idx the index for the element to be added at
	 * @param element the element to be added to the list
	 * @throws IllegalArgumentException thrown if the list already contains the element attempting to be added
	 * @throws IndexOutOfBoundsException thrown if the index is not in range of the list
	 * @throws NullPointerException thrown if the element is null
	 */
	public void add(int idx, E element) throws IllegalArgumentException, IndexOutOfBoundsException, NullPointerException {
		
		if (this.contains(element)) {
			throw new IllegalArgumentException("The element is already in the list.");
		}
		
		if (idx < 0 || idx > size) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		
		if (element == null) {
			throw new NullPointerException("Element cannot be null.");
		}
		
		if (idx == 0) {
			front = new ListNode(element, front);
			size++;
		}
		else {
			front.add(idx, element);
		}
	}
	
	/**
	 * gets an element at an index of the list
	 * @param idx the index that holds the retrieved element
	 * @return the element at the specified index
	 * @throws IndexOutOfBoundsException thrown if the index is not in range of the list
	 */
	public E get(int idx) throws IndexOutOfBoundsException {
		
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		
		return front.get(idx);
	}
	
	/**
	 * removes the provided element from the list
	 * @param element the element to be removed from the list
	 * @return true if the element was successfully removed and false if it wasn't
	 * @throws NullPointerException thrown if the element is null
	 * @throws IndexOutOfBoundsException thrown if the list is empty
	 */
	public boolean remove(E element) throws NullPointerException, IndexOutOfBoundsException {
		
		if (this.isEmpty()) {
			return false;
		}
		
		if (element == null) {
			throw new NullPointerException("The element to remove is null.");
		}
		
		if (front.data.equals(element)) {
			size--;
			front = front.next;
			return true;
		}
		
		return front.remove(element);
	}
	
	/**
	 * removes an element at an index of the list
	 * @param idx the index of the element to be removed
	 * @return the data that was removed at the specified index
	 * @throws IndexOutOfBoundsException thrown if the index is out of range of the list
	 */
	public E remove(int idx) throws IndexOutOfBoundsException, IllegalArgumentException {
		
		if (idx < 0 || idx >= size || this.isEmpty()) {
			throw new IndexOutOfBoundsException("Index is out of bounds");
		}
		
		if (idx == 0) {
			size--;
			E res = front.data;
			front = front.next;
			return res;
		}
		
		return front.remove(idx);
	}
	
	/**
	 * sets an element at an index of the list
	 * 
	 * @param idx the index for the provided element to be set at
	 * @param element the element to be set at the provided index
	 * @return the element that was previously at the index location
	 * @throws IndexOutOfBoundsException thrown if the index is not in range of the list
	 * @throw NullPointerException thrown if the element to be set is null
	 * @throw IllegalArgumentException thrown if the element to be set is already in the list
	 */
	public E set(int idx, E element) throws IllegalArgumentException, IndexOutOfBoundsException, NullPointerException {
		
		if (idx < 0 || idx >= size) {
			throw new IndexOutOfBoundsException("Index is out of bounds.");
		}
		
		if (element == null) {
			throw new NullPointerException("The element to be set cannot be null.");
		}
		
		if (this.contains(element)) {
			throw new IllegalArgumentException("The element already exists in the list.");
		}
		
		return front.set(idx, element);
	}
	
	/**
	 * checks if an element already exists in the list
	 * @param element the element that is checked
	 * @return true if the element is already in the list and false if it isn't
	 */
	public boolean contains(E element) {

		if (front == null) {
			return false;
		}
		return front.contains(element);
	}
	
	/**
	 * an inner class responsible for storing information about specific nodes in a linked list
	 */
	private class ListNode {
		
		/**
		 * the data in the node
		 */
		public E data;
		
		/**
		 * the next node in the list
		 */
		public ListNode next;
		
		/**
		 * constructs a ListNode object and sets the fields equal to the parameters
		 * @param data the data to be held in the new ListNode
		 * @param next the next ListNode in the sequence
		 */
		public ListNode(E data, ListNode next) {
			
			this.data = data;
			this.next = next;
		}
		
		/**
		 * collaborates with the LinkedListRecursive.add method to add an element to the list based on index
		 * @param idx the index for the element to be added at
		 * @param element the element to be added to the list
		 */
		public void add(int idx, E element) {
			
			if (idx == 1) {
				this.next = new ListNode(element, this.next);
				size++;
			}
			else {
				this.next.add(idx - 1, element);
			}
		}
		
		/**
		 * collaborates with the LinkedListRecursive.add method to add an element the the end of the list
		 * @param element the element to be added to the list
		 * @return true if the element was successfully added and false if it wasn't
		 */
		public boolean add(E element) {
			
			if (next == null) {
				next = new ListNode(element, null);
				size++;
				return true;
			}
			
			return next.add(element);
			
		}
		
		/**
		 * collaborates with the LinkedListRecursive.get method to get the element at a specified index
		 * @param idx the index of the element to be retrieved
		 * @return the element at the specified index
		 */
		public E get(int idx) {
			
			if (idx == 0) {
				return this.data;
			}
			
			return this.next.get(idx - 1);
		}
		
		/**
		 * collaborates with the LinkedListRecursive.remove(int) method to remove the element at a specified index
		 * @param idx the index for the element to be removed at
		 * @return the data that was removed
		 */
		public E remove(int idx) {
			
			if (idx == 1) {
				size--;
				E res = this.next.data;
				this.next = this.next.next;
				return res;
			}
			
			return this.next.remove(idx - 1);
		}
		
		/**
		 * collaborates with the LinkedListRecursive.remove(E) method to remove a specified element from the list
		 * @param element the element to be removed
		 * @return true if the element was successfully removed and false if it wasn't
		 */
		public boolean remove(E element) {
			
			if (this.next.data.equals(element)) {
				size--;
				this.next = this.next.next;
				return true;
			}
			
			return this.next.remove(element);
		}
		
		/**
		 * collaborates with the LinkedListRecursive.set method to set a specified index in the list to a specified element
		 * @param idx the index for the specified element to be set at
		 * @param element the element to be set at the specified index
		 * @return the element that was previously at the specified index
		 */
		public E set(int idx, E element) {
			
			if (idx == 0) {
				E res = this.data;
				this.data = element;
				return res;
			}
			
			return this.next.set(idx - 1, element);
		}
		
		/**
		 * collaborates with the LinkedListRecursive.contains method to 
		 * @param element the element to be checked for duplication
		 * @return true if the element already exists in the list and false if it doesn't
		 */
		public boolean contains(E element) {
			
			if (data.equals(element)) {
				return true;
			}
			
			if (next == null) {
				return false;
			}
			
			return next.contains(element);
		}
	}
}
