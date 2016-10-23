/*
 * TCSS 342 - Spring 2016
 * Assignment 1 - Burger Baron
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The main class is used to read in the input file and tests and runs the
 * Burger class.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-08-2016)
 */
public class Main {

	private static String SINGLE = "Single";
	private static String DOUBLE = "Double";
	private static String TRIPLE = "Triple";

	/**
	 * A burger.
	 */
	private static Burger myBurger;

	/**
	 * A burger is a Baron Burger.
	 */
	private static boolean myIsBaron;

	/**
	 * Total number of the procession order.
	 */
	private static int myOrderNumber;

	/**
	 * Runs the program and tests the program elements.
	 * 
	 * @param args
	 *            The args.
	 */
	public static void main(String[] args) {

		// reads .txt file
		Scanner input = null;
		try {
			input = new Scanner(new File("sample.txt"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		if (!input.hasNextLine()) {
			throw new NoSuchElementException("No order line in the file");
		}

		if (myOrderNumber > 99) {
			System.out.println("Maximum processing order number is '99'");
		}

		// parses line
		while (input.hasNextLine() && myOrderNumber < 100) {
			String line = input.nextLine();
			if (line.startsWith("/*")) {
				line = input.nextLine();
			}
			System.out.println("Processing Order " + myOrderNumber + ": " + line);
			parseLine(line);
			System.out.println(myBurger.toString());
			System.out.println();
		}
		input.close();

		
		
		
		// tests MyStack class
		System.out.println("--- <test MyStack class> ----------------------");
		testMyStack();
		System.out.println();

		// tests Burger class
		System.out.println("--- <test Burger class> ----------------------");
		testBurger();
		System.out.println();

	}

	/**
	 * Parses a line of input from the file and outputs the burger.
	 * 
	 * @param line
	 *            The line from the input file.
	 */
	public static void parseLine(String line) {

		// splits line by space(" ")
		// http://javadevnotes.com/java-split-string-into-arraylist-examples
		ArrayList<String> tokenList = new ArrayList<String>();
		String[] token = line.split(" ");
		for (String s : token) {
			tokenList.add(s);
		}

		// Stores data of tokenList to full form of ArrayList 
		//form: <patty count> <patty type> <burger type>
		ArrayList<String> burgerList = new ArrayList<String>();
		if (tokenList.get(0).equals("Burger")) {
			burgerList.add(SINGLE);
			burgerList.add(Burger.BEEF);
			burgerList.addAll(tokenList);
		} else if (tokenList.get(0).equals("Baron")) {
			burgerList.clear();
			burgerList.add(SINGLE);
			burgerList.add(Burger.BEEF);
			burgerList.addAll(tokenList);
		} else if (tokenList.get(0).equals(Burger.BEEF) || tokenList.get(0).equals(Burger.CHICKEN)
				|| tokenList.get(0).equals(Burger.VEGGIE)) {
			burgerList.clear();
			burgerList.add(SINGLE);
			burgerList.addAll(tokenList);
		} else
			if (tokenList.get(0).equals(SINGLE) || tokenList.get(0).equals(DOUBLE) || tokenList.get(0).equals(TRIPLE)) {
			burgerList.clear();
			burgerList.add(tokenList.get(0));
			if (tokenList.get(1).equals(Burger.BEEF) || tokenList.get(1).equals(Burger.CHICKEN)
					|| tokenList.get(1).equals(Burger.VEGGIE)) {
				for (int i = 1; i < tokenList.size(); i++) {
					burgerList.add(tokenList.get(i));
				}
			} else {
				burgerList.add(Burger.BEEF);
				for (int i = 1; i < tokenList.size(); i++) {
					burgerList.add(tokenList.get(i));
				}
			}
		}

		// creates burger
		if (burgerList.get(2).equals("Burger")) {
			myIsBaron = false;
			myBurger = new Burger(myIsBaron);
		} else if (burgerList.get(2).equals("Baron")) {
			myIsBaron = true;
			myBurger = new Burger(myIsBaron);
		}

		// finds and stores exceptions and additions/omissions to separate
		// ArrayList
		ArrayList<String> addition = new ArrayList<String>();
		ArrayList<String> exception = new ArrayList<String>();
		if (myIsBaron && burgerList.size() > 4 && burgerList.get(5).equals("no")) {
			for (int i = 6; i < burgerList.size(); i++) {
				if (burgerList.get(i).equals("but") && !burgerList.get(i + 1).equals("no")) {
					for (int j = i + 1; j < burgerList.size(); j++) {
						exception.add(burgerList.get(j));
					}
					break;
				}
				addition.add(burgerList.get(i));
			}
		} else if (!myIsBaron && burgerList.size() > 3 && !burgerList.get(4).equals("no")) {
			for (int i = 4; i < burgerList.size(); i++) {
				if (burgerList.get(i).equals("but") && burgerList.get(i + 1).equals("no")) {
					for (int j = i + 2; j < burgerList.size(); j++) {
						exception.add(burgerList.get(j));
					}
					break;
				}
				addition.add(burgerList.get(i));
			}
		}

		// "with" or "with no" options
		for (int i = 0; i < addition.size(); i++) {
			if (addition.get(i).equals(Burger.CHEESE_CATEGORY) || addition.get(i).equals(Burger.SAUCE_CATEGORY)
					|| addition.get(i).equals(Burger.VEGGIES_CATEGORY)) {
				if (myIsBaron) {
					myBurger.removeCategory(addition.get(i));
				} else {
					myBurger.addCategory(addition.get(i));
				}
			} else {
				if (myIsBaron) {
					myBurger.removeIngredient(addition.get(i));
				} else {
					myBurger.addIngredient(addition.get(i));
				}
			}
		}

		// "but" or "but no" options
		for (int i = 0; i < exception.size(); i++) {
			if (exception.get(i).equals(Burger.CHEESE_CATEGORY) || exception.get(i).equals(Burger.SAUCE_CATEGORY)
					|| exception.get(i).equals(Burger.VEGGIES_CATEGORY)) {
				if (myIsBaron) {
					myBurger.addCategory(exception.get(i));
				} else {
					myBurger.removeCategory(exception.get(i));
				}
			} else {
				if (myIsBaron) {
					myBurger.addIngredient(exception.get(i));
				} else {
					myBurger.removeIngredient(exception.get(i));
				}
			}
		}

		// adds patties
		if (burgerList.get(0).equals(DOUBLE)) {
			myBurger.addPatty();
		} else if (burgerList.get(0).equals(TRIPLE)) {
			myBurger.addPatty();
			myBurger.addPatty();
		}

		// changes patty
		if (!burgerList.get(1).equals(Burger.BEEF)) {
			myBurger.changePatties(burgerList.get(1));
		}

		// increases processing order number.
		myOrderNumber++;
	}

	/**
	 * Tests MyStack class.
	 */
	public static void testMyStack() {
		MyStack<String> s = new MyStack<String>();

		// tests push()
		s.push("hi");
		s.push("test");

		// tests size()
		System.out.println("size(): (expected: 2) " + s.size());

		// tests toString()
		System.out.println("toString(): (expected: [test, hi]) " + s.toString());

		// tests peek()
		System.out.println("peek(): (expected: test) " + s.peek());

		// tests pop()
		System.out.println("pop(): (expected: test)" + s.pop());

		// tests isEmpty()
		System.out.println("isEmpty(): (expected: false) " + s.isEmpty());
		while (!s.isEmpty()) {
			s.pop();
		}
		System.out.println("isEmpty9): (expected: true) " + s.isEmpty());

	}

	/**
	 * Tests Burger class.
	 */
	public static void testBurger() {

		// tests Burger
		Burger burger = new Burger(false);
		System.out.println("Burger (default): " + burger.toString());
		burger.addCategory(Burger.SAUCE_CATEGORY);
		System.out.println("Add Sauce category: " + burger.toString());
		burger.removeCategory(Burger.SAUCE_CATEGORY);
		System.out.println("Remove Sauce category: " + burger.toString());
		burger.addIngredient(Burger.MUSHROOMS);
		System.out.println("Add Mushrooms: " + burger.toString());
		burger.addIngredient(Burger.ONIONS);
		System.out.println("Add Onions: " + burger.toString());
		burger.addIngredient(Burger.PEPPERJACK);
		System.out.println("Add Pepperjack: " + burger.toString());
		burger.removeIngredient(Burger.ONIONS);
		System.out.println("Remove Onions: " + burger.toString());
		burger.addPatty();
		System.out.println("Add Patty: " + burger.toString());
		burger.changePatties(Burger.CHICKEN);
		System.out.println("Change Patty: " + burger.toString());
		burger.addPatty();
		System.out.println("Add Patty: " + burger.toString());

		System.out.println();

		// tests Baron Burger
		Burger baron = new Burger(true);
		System.out.println("Baron Burger (defalt): " + baron.toString());
		baron.removeCategory(Burger.SAUCE_CATEGORY);
		System.out.println("Remove Sauce category: " + baron.toString());
		baron.removeCategory(Burger.VEGGIES_CATEGORY);
		System.out.println("Remove Veggies category: " + baron.toString());
		baron.addIngredient(Burger.MUSHROOMS);
		System.out.println("Add Mushrooms: " + baron.toString());
		baron.addCategory(Burger.SAUCE_CATEGORY);
		System.out.println("Add Sauce category: " + baron.toString());
		baron.addPatty();
		System.out.println("Add Patty: " + baron.toString());
		baron.addPatty();
		System.out.println("Add Patty: " + baron.toString());
		baron.changePatties(Burger.CHICKEN);
		System.out.println("Change Patties: " + baron.toString());
	}

}
