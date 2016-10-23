/*
 * TCSS 342 - Spring 2016
 * Assignment 1 - Burger Baron
 */

import java.util.EmptyStackException;

/**
 * MyStack class is for Burger class.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-08-2016)
 */
public class MyStack<Type> {

	private MyStackNode<Type> myTop;
	private int mySize;

	/**
	 * Constructor that initializes an empty MyStack.
	 */
	public MyStack() {
		myTop = null;
		mySize = 0;
	}

	/**
	 * Returns true if the MyStack is empty.
	 * 
	 * @return True if the MyStack is empty.
	 */
	public boolean isEmpty() {
		return myTop == null;
	}

	/**
	 * Adds the item to the top of the MyStack.
	 * 
	 * @param item
	 *            The item.
	 */
	public void push(Type item) {
		MyStackNode<Type> current = myTop;
		myTop = new MyStackNode<Type>();
		myTop.myData = item;
		myTop.myNext = current;
		mySize++;
	}

	/**
	 * Removes and returns the item on the top of the MyStack.
	 * 
	 * @return The item on the top of the MyStack.
	 */
	public Type pop() {
		if (myTop == null) {
			throw new EmptyStackException();
		}
		Type data = myTop.myData;
		myTop = myTop.myNext;
		mySize--;
		return data;
	}

	/**
	 * Returns the item on the top of the MyStack.
	 * 
	 * @return The item on the top of the MyStack.
	 */
	public Type peek() {
		if (myTop == null) {
			throw new EmptyStackException();
		}
		return myTop.myData;
	}

	/**
	 * Returns the number of items in the MyStack.
	 * 
	 * @return The number of items in the MyStack.
	 */
	public int size() {
		return mySize;
	}

	/**
	 * Converts the contents of the MyStack to a String for display.
	 */
	public String toString() {
		if (mySize == 0) {
			return "bottom[]top";
		}
		MyStackNode<Type> temp = myTop;
		String result = "top [";
		String s = temp.myData.toString();
		result += s;
		temp = temp.myNext;
		while (temp != null) {
			result += ", ";
			result += temp.myData.toString();
			temp = temp.myNext;
		}

		result += "] bottom";
		return result;
	}

	/**
	 * MyStackNode class is an inner class for MyStack class.
	 * 
	 * @author Jieun Lee
	 * @version 1.0 (04-08-2016)
	 */
	@SuppressWarnings("hiding")
	private class MyStackNode<Type> {

		/**
		 * A data.
		 */
		private Type myData;

		/**
		 * A MyStackNode for next.
		 */
		private MyStackNode<Type> myNext;

		/**
		 * Constructs an empty MyStackNode.
		 */
		public MyStackNode() {
			myData = null;
			myNext = null;
		}

		/**
		 * Constructs a MyStackNode with given data.
		 * 
		 * @param theData
		 */
		@SuppressWarnings("unused")
		public MyStackNode(Type theData) {
			myData = theData;
			myNext = null;
		}
	}
}
