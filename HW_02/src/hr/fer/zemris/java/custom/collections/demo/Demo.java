package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Processor;

import java.util.Arrays;

public class Demo {

	public static void main(String[] args) {
		ArrayIndexedCollection col = new ArrayIndexedCollection(2);
		col.add(new Integer(20));
		col.add("New York");
		col.add("San Francisco"); // here the internal array is reallocated to 4
		System.out.println(col.contains("New York")); // writes: true
		col.remove(1); // removes "New York"; shifts "San Francisco" to position
		// 1
		System.out.println(col.get(1)); // writes: "San Francisco"
		System.out.println(col.size()); // writes: 2
		col.add("Los Angeles");
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection(col);
		class P extends Processor {
			public void process(Object o) {
				System.out.println(o);
			}
		}
		;

		System.out.println("col1 elements:");
		col.forEach(new P());
		System.out.println("col1 elements again:");
		System.out.println(Arrays.toString(col.toArray()));
		System.out.println("col2 elements:");
		col2.forEach(new P());
		System.out.println("col2 elements again:");
		System.out.println(Arrays.toString(col2.toArray()));
		System.out.println(col.contains(col2.get(1))); // true
		System.out.println(col2.contains(col.get(1))); // true

		// My tests
		System.out.println();
		System.out.println("===My tests===");

		LinkedListIndexedCollection list = new LinkedListIndexedCollection();
		list.addAll(col2);
		list.forEach(new P());
		System.out.println();

		list.clear();
		list.forEach(new P());

		list.add(Math.PI);
		list.add("Ante");
		list.add(Arrays.toString(col2.toArray()));
		// list.add(null);
		list.forEach(new P());

		list.insert(0, 3);
		list.insert("Zadnja pozicija", list.size());
		list.forEach(new P());

		System.out.println();
		for (int i = 0, n = list.size(); i < n; i++) {
			Object element = list.get(i);
			System.out.println("Position " + list.indexOf(element) + ": "
					+ element);
		}

		System.out.println();
		list.remove(0);
		list.remove((Object) 0);
		list.insert("Ante", 3);
		list.forEach(new P());

		// demonstration of proper remove by index
		System.out.println();
		list.remove(list.size() - 1);
		list.forEach(new P());

	}

}
