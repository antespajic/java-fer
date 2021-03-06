package hr.fer.zemris.java.custom.collections;

/**
 * Resizable-array implementation of the Collection. Implements all optional
 * collection operations, and permits all elements, including null. This class
 * provides methods to manipulate the size of the array that is used internally
 * to store the list.
 * 
 * The size, isEmpty, get, run in constant time. The add operation runs in
 * amortized constant time, that is, adding n elements requires O(n) time. All
 * of the other operations run in linear time (roughly speaking). The constant
 * factor is low compared to that for the LinkedList implementation.
 * 
 * @author Ante Spajic
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * Default initial capacity.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * The size of the ArrayIndexedCollection (the number of elements it
	 * contains).
	 */
	private int size;
	private int capacity;

	/**
	 * The array buffer into which the elements of the ArrayList are stored. The
	 * capacity of the ArrayIndexedCollection is the length of this array
	 * buffer.
	 */
	private Object[] elements;

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity
	 *            the initial capacity of the collection
	 * @throws IllegalArgumentException
	 *             if the specified initial capacity is negative
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 0) {
			throw new IllegalArgumentException();
		}
		this.size = 0;
		this.capacity = initialCapacity;
		this.elements = new Object[capacity];
	}

	/**
	 * Constructs an empty list with an initial capacity of 16.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructs a ArrayIndexedCollection containing the elements of the
	 * specified collection, in the order they are returned by the collection's
	 * iterator.
	 *
	 * @param other
	 *            the collection whose elements are to be placed into this list
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, other.size());
	}

	/**
	 * Constructs a ArrayIndexedCollection with specified initial capacity
	 * containing the elements of the specified collection, in the order they
	 * are returned by the collection's iterator. If user provides initial
	 * capacity smaller than collection size, it will be ignored and capacity
	 * will be equal to collection size.
	 *
	 * @param other
	 *            the collection whose elements are to be placed into this list
	 * @param initialCapacity
	 *            the initial capacity of the collection
	 * @throws NullPointerException
	 *             Thrown if the specified collection is <tt>null</tt>
	 * @throws IllegalArgumentException
	 *             Thrown if the initial capacity is lower than 0.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
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
			throw new IllegalArgumentException();
		}
		if (size == capacity) {
			elements = resize(elements);
		}
		elements[size++] = value;

	}

	/**
	 * Helper method used to double the capacity of an array when attempted to
	 * add an element after the capacity has been exceeded
	 * 
	 * @param elements
	 *            Array of elements that needs its capacity doubled
	 * @return Resized array of elements
	 */
	private Object[] resize(Object[] elements) {
		capacity *= 2;
		Object[] resized = new Object[capacity];
		for (int i = 0; i < elements.length; i++) {
			resized[i] = elements[i];
		}
		return resized;
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
	 * Removes the specified element at given index.
	 * 
	 * @param index
	 *            Index of element to be removed
	 * @throws IndexOutOfBoundsException
	 *             Thrown if specified index is not in this collection
	 */
	public void remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(
					"Specified index is not in this collection");
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		size--;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * hr.fer.zemris.java.custom.collections.Collection#remove(java.lang.Object)
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		// check if it contains specified value
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#toArray()
	 */
	@Override
	public Object[] toArray() {
		Object[] povratni = new Object[size];
		int j = 0;
		for (int i = 0; i < size; i++) {
			if (elements[i] != null) {
				povratni[j++] = elements[i];
			}
		}
		return povratni;
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
		for (int i = 0; i < elements.length; i++) {
			if (elements[i] != null) {
				processor.process(elements[i]);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Collection#clear()
	 */
	@Override
	public void clear() {
		for (int i = 0; i < elements.length; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index
	 *            index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException
	 *             Thrown if specified index greater than size of collection
	 */
	public Object get(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		return elements[index];
	}

	/**
	 * Inserts the specified element at the specified position in this
	 * collection. Shifts the element currently at that position (if any) and
	 * any subsequent elements to the right (adds one to their indices).
	 *
	 * @param value
	 *            element to be inserted
	 * @param position
	 *            position at which the specified element is to be inserted
	 * @throws IllegalArgumentException
	 *             Thrown to indicate that a method has been passed an illegal
	 *             or inappropriate argument.
	 * @throws IndexOutOfBoundsException
	 *             Thrown if specified index greater than size of collection
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new IllegalArgumentException(
					"Storing null values is not supported");
		}
		if (position < 0 || position >= size) {
			throw new IndexOutOfBoundsException(
					"You specified invalid position, please add something within bounds from 0 to "
							+ (size - 1));
		}
		if (size + 1 >= capacity) {
			elements = resize(elements);
		}
		for (int i = position; i < size; i++) {
			elements[i + 1] = elements[i];
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Returns the index of the first occurrence of the specified element in
	 * this list, or -1 if this list does not contain the element. More
	 * formally, returns the lowest index i such that value on that index is
	 * equal to requested value or -1 if there is no such index.
	 * 
	 * @param value
	 *            Value of which you need the index
	 * @return the index of the first occurrence of the specified element in
	 *         this list, or -1 if this list does not contain the element
	 */
	public int indexOf(Object value) {
		if (value != null) {
			for (int i = size - 1; i >= 0; i--) {
				if (value.equals(elements[i])) {
					return i;
				}
			}
		}
		return -1;
	}

}
