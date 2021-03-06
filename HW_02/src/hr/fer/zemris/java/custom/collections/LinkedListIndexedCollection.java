package hr.fer.zemris.java.custom.collections;

/**
 * Doubly-linked list implementation of the collection class. Implements all
 * optional list operations, and permits all elements (excluding {@code null}).
 * 
 * All of the operations perform as could be expected for a doubly-linked list.
 * Operations that index into the list will traverse the list from the beginning
 * or the end, whichever is closer to the specified index.
 * 
 * @author Ante Spajic
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * The size of the LinkedListIndexedCollection (the number of elements it
	 * contains).
	 */
	private int size;

	/**
	 * Pointer to first node.
	 */
	private ListNode first;

	/**
	 * Pointer to last node.
	 */
	private ListNode last;

	/**
	 * This represents one node in the List, stores previous and next element as
	 * well as the value of the node
	 * 
	 * @author Ante Spajic
	 * 
	 */
	private static class ListNode {

		ListNode previous;
		ListNode next;
		Object value;

	}

	/**
	 * Constructs an empty LinkedListIndexedCollection.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * Constructs a list containing the elements of the specified collection.
	 *
	 * @param other
	 *            the collection whose elements are to be placed into this list
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	public LinkedListIndexedCollection(Collection other) {
		this();
		addAll(other);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#size()
	 */
	@Override
	public int size() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#add(java.lang.Object)
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new IllegalArgumentException("You cannot add " + value
					+ " to this collection.");
		}

		ListNode novi = new ListNode();
		novi.value = value;
		if (first == null) {
			last = novi;
			first = last;
		} else {
			novi.previous = last;
			last.next = novi;
			last = last.next;
		}
		size++;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#contains(java.lang.Object
	 * )
	 */
	@Override
	public boolean contains(Object value) {
		return indexOf(value) != -1;
	}

	/**
	 * Removes the specified element at given index
	 * 
	 * @param index
	 *            Index of element to be removed
	 * @throws IndexOutOfBoundsException
	 *             Thrown if specified index is not in this collection
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Invalid index specified");
		}
		ListNode target = first;
		for (; target != null && index != 0; target = target.next, index--)
			;
		unlink(target);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		if (value == null) {
			throw new IllegalArgumentException(
					"Invalid collection argument specified: " + value);
		}

		int index = indexOf(value);
		if (index != -1 && size != 0) {
			remove(index);
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		Object[] result = new Object[size];
		int i = 0;
		for (ListNode node = first; node != null; node = node.next) {
			result[i++] = node.value;
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#forEach(hr.fer.zemris
	 * .java.custom.collections.Processor)
	 */
	@Override
	public void forEach(Processor processor) {
		for (ListNode node = first; node != null; node = node.next) {
			processor.process(node.value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		ListNode node = first.next;
		while (node != null) {
			ListNode next = node.next;
			node.next = node.previous = null;
			node.value = null;
			node = next;
		}
		first.next = first.previous = first = null;
		size = 0;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this collection
	 * @throws IndexOutOfBoundsException
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("Specified index: " + index
					+ "is out of this collection bounds.");
		}
		ListNode node;
		// check in which half of list is index and from there find the element
		// complexity O(n/2+1)
		if (index < (size >> 1)) {
			node = first;
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		} else {
			node = last;
			for (int i = size - 1; i > index; i--) {
				node = node.previous;
			}
		}
		return node.value;
	}

	/**
	 * Inserts the specified element at the specified position in this list.
	 * Shifts the element currently at that position (if any) and any subsequent
	 * elements to the right (adds one to their indices).
	 *
	 * @param position
	 *            position at which the specified element is to be inserted
	 * @param element
	 *            element to be inserted
	 * @throws IndexOutOfBoundsException
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Specified position: "
					+ position + "is out of this collection bounds.");
		}
		if (position == size || size == 0) {
			// inserting element into empty list or end of list we delegate
			// further to add
			add(value);
			return;
		}

		ListNode newElement = new ListNode();
		newElement.value = value;

		if (position == 0) {
			ListNode old = first;
			first = newElement;
			first.next = old;
			if (old != null) {
				old.previous = first;
			}
			if (last == null) {
				last = first;
			}
		} else {
			// inserting element somewhere in the middle of list
			ListNode oldElement = first;
			// find the position to add
			for (int i = 0; i < position; i++) {
				oldElement = oldElement.next;
			}
			// inserting in the middle of the two old elements
			newElement.next = oldElement;
			newElement.previous = oldElement.previous;
			oldElement.previous.next = newElement;
			oldElement.previous = newElement;
		}
		size++;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in
	 * this list, or -1 if this list does not contain the element. More
	 * formally, returns the lowest index i such that value on that index is
	 * equal to requested value or -1 if there is no such index.
	 *
	 * @param value
	 *            element to search for
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 */
	public int indexOf(Object value) {
		int i = 0;
		for (ListNode node = first; node != null; node = node.next, i++) {
			if (value.equals(node.value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Private helper method. Unlinks non-null node.
	 * 
	 * @param node
	 *            Node to be unlinked
	 */
	private void unlink(ListNode node) {
		ListNode next = node.next;
		ListNode prev = node.previous;

		if (prev == null) {
			first = next;
		} else {
			prev.next = next;
			node.previous = null;
		}

		if (next == null) {
			last = prev;
		} else {
			next.previous = prev;
			node.next = null;
		}

		node.value = null;
		size--;
	}

}
