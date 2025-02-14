/**
 * 
 */
package edu.ncsu.csc216.pack_scheduler.util;

import java.util.AbstractList;

/**
 * a custom ArrayList class
 * @param <E> represents the type that the ArrayList takes
 */
public class ArrayList<E> extends AbstractList<E> {
	
	/** a constant value for initializing the list size. The value should be 10. */
	private static final int INIT_SIZE = 10;
	
	/** an array of type E */
	private E[] list;
	
	/** the size of the list */
	private int size;
	
	/**
	 * constructor for ArrayList
	 * creates an empty ArrayList and initializes the list to a capacity of 10 (via INIT_SIZE constant)
	 */
	@SuppressWarnings("unchecked")
	public ArrayList() {
		
		this.size = 0;
		list = (E[]) new Object[INIT_SIZE];
	}
	
	/**
	 * adds an element to the ArrayList at a specified index
	 * @param i index to add the element at
	 * @param element element to be added
	 * @throws NullPointerException thrown if the element to add is null
	 * @throws IllegalArgumentException thrown if the element to add is duplicate of an element already in the ArrayList
	 * @throws IndexOutOfBoundsException thrown if the index is less than 0 or greater than the length
	 */
	public void add(int i, E element) throws NullPointerException, IllegalArgumentException, IndexOutOfBoundsException{
		
		// throws an exception if the element passed in is zero
		if (element == null) {
			throw new NullPointerException();
		}
		
		// ensures that the element to be added is not already in the ArrayList
		for (int k = 0; k < this.size(); k++) {
			
			if (list[k].equals(element)) {
				throw new IllegalArgumentException();
			}
		}
		
		// ensures that the index is not out of bounds of the ArrayList
		if (i < 0 || i > this.size()) {
			throw new IndexOutOfBoundsException();
		}
		
		 // doubles the size of the array if all the slots in the array are used
		if (this.list.length == this.size()) {
			growArray();
		}
		
		// adds the element to the ArrayList
		if (i == this.size()) {
			this.list[i] = element;
		}
		else {
			
			// shifts all the elements in the ArrayList
			E temp = this.get(i);
			this.list[i] = element;
		
			for (int j = this.size() - 1; j > i; j--) {
			
				list[j + 1] = list[j];
			}
		
			this.list[i + 1] = temp;
		}
		this.size += 1;
	}
	
	/**
	 * doubles the list in size to make room for more elements
	 */
	@SuppressWarnings("unchecked")
	private void growArray() {
		
		E[] newList = (E[]) new Object[this.list.length * 2];
		
		for (int i = 0; i < this.size(); i++) {
			newList[i] = this.list[i];
		}
		
		this.list = newList;
	}
	
	/**
	 * removes an element from an ArrayList by a specified index
	 * @param i index at which an element is removed from the list
	 * @return the element in the list that was removed
	 * @throws IndexOutOfBoundsException thrown if the index to be removed at is outside the bounds of the list
	 */
	public E remove(int i) throws IndexOutOfBoundsException {
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException(); 
		} 
		
		E remove = this.list[i];
		
		for (int j = i; j < this.size(); j++) {
			this.list[j] = this.list[j + 1];
		}
		
		this.size -= 1;		
		return remove;
	}
	
	/**
	 * sets an element in an ArrayList (given by a specified index) to another specified element
	 * @param index index to be set to a different value
	 * @param element element to be set at the given index
	 * @return the original element at the location i
	 * @throws NullPointerException thrown if the element to be set is null
	 * @throws IllegalArgumentException thrown if the element to be set already exists in the list
	 * @throws IndexOutOfBoundsException thrown if the index for the element to be set at is outside the bounds of the list
	 */
	public E set(int index, E element) throws NullPointerException, IllegalArgumentException, IndexOutOfBoundsException{
		
		E ogElement;
		
		if (element == null) {
			throw new NullPointerException("Element is null.");
		}
		
		for (int i = 0; i < this.size(); i++) {
			if (this.list[i].equals(element)) {
				throw new IllegalArgumentException("The element already exists in the list.");
			}
		}
		
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index is out of range.");
		}
		
		ogElement = list[index];
		list[index] = element;
		
		return ogElement;
	}
	
	/**
	 * returns the element in an ArrayList at a specified index
	 * @param i index that is requested
	 * @return element at the specified index
	 * @throws IndexOutOfBoundsException thrown if the index is out of bounds of the ArrayList
	 */
	public E get(int i) throws IndexOutOfBoundsException{
		
		if (i < 0 || i >= size()) {
			throw new IndexOutOfBoundsException("Index is out of range.");
		}
		return this.list[i];
	}
	
	/**
	 * gives the size of the ArrayList by counting the elements in the array that hold a value
	 * @return the size of the ArrayList
	 */
	public int size() {
		return this.size;
	}
}
