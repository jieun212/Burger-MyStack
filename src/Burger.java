/*
 * TCSS 342 - Spring 2016
 * Assignment 1 - Burger Baron
 */

/**
 * Burger class that adds patties, changes type of patties, adds ingredients to
 * a burger, and removes ingredients from a burger.
 * 
 * @author Jieun Lee
 * @version 1.0 (04-08-2016)
 */
public class Burger {

	/**
	 * Bun.
	 */
	public static String BUN = "Bun";

	/**
	 * Beef patty.
	 */
	public static String BEEF = "Beef";

	/**
	 * Veggie patty.
	 */
	public static String VEGGIE = "Veggie";

	/**
	 * Chicken patty.
	 */
	public static String CHICKEN = "Chicken";

	/**
	 * Cheese_Category.
	 */
	public static String CHEESE_CATEGORY = "Cheese";

	/**
	 * Sauce_Category.
	 */
	public static String SAUCE_CATEGORY = "Sauce";

	/**
	 * Veggies_Category.
	 */
	public static String VEGGIES_CATEGORY = "Veggies";

	/**
	 * Cheddar_Cheese.
	 */
	public static String CHEDDAR = "Cheddar";

	/**
	 * Mozzarella_Cheese.
	 */
	public static String MOZZARELLA = "Mozzarella";

	/**
	 * Pepperjack_Cheese.
	 */
	public static String PEPPERJACK = "Pepperjack";

	/**
	 * Ketchup_Sauce.
	 */
	public static String KETCHUP = "Ketchup";

	/**
	 * Mustard_Sauce.
	 */
	public static String MUSTARD = "Mustard";

	/**
	 * Mayonaise_Sauce.
	 */
	public static String MAYO = "Mayonnaise";

	/**
	 * Baron_Sauce.
	 */
	public static String BARON_SAUCE = "Baron-Sauce";

	/**
	 * Lettuce_Veggies.
	 */
	public static String LETTUCE = "Lettuce";

	/**
	 * Tomato_Veggies.
	 */
	public static String TOMATO = "Tomato";

	/**
	 * Onions_Veggies.
	 */
	public static String ONIONS = "Onions";

	/**
	 * Pickle_Veggies.
	 */
	public static String PICKLE = "Pickle";

	/**
	 * Mushrooms_Veggies.
	 */
	public static String MUSHROOMS = "Mushrooms";
	
	
	

	
	/**
	 * The Burger Baron's official recipe.
	 */
	private MyStack<String> myOfficialRecipe;

	/**
	 * The current burger.
	 */
	private MyStack<String> myBurger;

	
	
	
	/**
	 * Constructor.
	 * 
	 * @param theWorks
	 *            if true, then BaronBurger. Otherwise, Burger.
	 */
	public Burger(boolean theWorks) {
		myOfficialRecipe = new MyStack<String>();
		myBurger = new MyStack<String>();

		myOfficialRecipe.push(BUN); // bottom
		myOfficialRecipe.push(KETCHUP);
		myOfficialRecipe.push(MUSTARD);
		myOfficialRecipe.push(MUSHROOMS);
		myOfficialRecipe.push(BEEF);
		myOfficialRecipe.push(CHEDDAR);
		myOfficialRecipe.push(MOZZARELLA);
		myOfficialRecipe.push(PEPPERJACK);
		myOfficialRecipe.push(ONIONS);
		myOfficialRecipe.push(TOMATO);
		myOfficialRecipe.push(LETTUCE);
		myOfficialRecipe.push(BARON_SAUCE);
		myOfficialRecipe.push(MAYO);
		myOfficialRecipe.push(BUN);
		myOfficialRecipe.push(PICKLE); // top

		if (theWorks) { // BaronBurger
			MyStack<String> temp = new MyStack<String>();
			while (!myOfficialRecipe.isEmpty()) {
				temp.push(myOfficialRecipe.pop());
			}
			while (!temp.isEmpty()) {
				String pop = temp.pop();
				myBurger.push(pop);
				myOfficialRecipe.push(pop);
			}
		} else { // Burger
			myBurger.push(BUN);
			myBurger.push(BEEF);
			myBurger.push(BUN);
		}
	}

	/**
	 * Changes type of patty with given patty.
	 * 
	 * @param pattyType
	 *            The patty.
	 */
	public void changePatties(String pattyType) {
		MyStack<String> temp = new MyStack<String>();

		while (!myBurger.isEmpty()) {
			String patty = myBurger.peek();
			if (patty.equals(BEEF)) {
				temp.push(pattyType);
			} else {
				temp.push(patty);
			}
			myBurger.pop();
		}
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}

	}

	/**
	 * Adds patty.
	 */
	public void addPatty() {
		MyStack<String> copyRecipe = new MyStack<String>();
		MyStack<String> temp = new MyStack<String>();

		// copies official recipe.
		while (!myOfficialRecipe.isEmpty()) {
			temp.push(myOfficialRecipe.pop());
		}
		while (!temp.isEmpty()) {
			String pop = temp.pop();
			copyRecipe.push(pop);
			myOfficialRecipe.push(pop);
		}

		// gets type of patty.
		String patty = myBurger.pop();
		while (!patty.equals(BEEF) && !patty.equals(CHICKEN) && !patty.equals(VEGGIE)) {
			temp.push(patty);
			patty = myBurger.pop();
		}
		myBurger.push(patty);
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}
		if (!patty.equals(BEEF)) {
			while (!copyRecipe.peek().equals(BEEF)) {
				temp.push(copyRecipe.pop());
			}
			copyRecipe.pop();
			copyRecipe.push(patty);
			while (!temp.isEmpty()) {
				copyRecipe.push(temp.pop());
			}
		}

		// counts patties in myBurger.
		int pattyCount = 0;
		while (!myBurger.isEmpty()) {
			String pop = myBurger.pop();
			if (pop.equals(patty)) {
				pattyCount++;
			}
			temp.push(pop);
		}
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}

		// adds patty to copied recipe stack
		int size = copyRecipe.size();
		for (int i = 0; i < size / 2; i++) {
			temp.push(copyRecipe.pop());
		}
		if (pattyCount == 2) {
			copyRecipe.push(patty);
		}
		copyRecipe.push(patty);
		while (!temp.isEmpty()) {
			copyRecipe.push(temp.pop());
		}

		// removes unnecessary ingredients
		while (!copyRecipe.isEmpty()) {
			String recipePeek = copyRecipe.peek();
			String burgerPeek = myBurger.peek();
			if (recipePeek.equals(burgerPeek)) {
				temp.push(recipePeek);
				myBurger.pop();
			} else if (recipePeek.equals(patty)) {
				temp.push(recipePeek);
			}
			copyRecipe.pop();
		}
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}
	}

	/**
	 * Adds category with given type of category. 
	 * [Categories: Cheese, Sauce, Veggies].
	 * 
	 * @param type
	 *            Category.
	 */
	public void addCategory(String type) {
		if (type.equals(CHEESE_CATEGORY)) {
			addIngredient(PEPPERJACK);
			addIngredient(MOZZARELLA);
			addIngredient(CHEDDAR);
		} else if (type.equals(SAUCE_CATEGORY)) {
			addIngredient(MAYO);
			addIngredient(BARON_SAUCE);
			addIngredient(MUSTARD);
			addIngredient(KETCHUP);
		} else if (type.equals(VEGGIES_CATEGORY)) {
			addIngredient(PICKLE);
			addIngredient(LETTUCE);
			addIngredient(TOMATO);
			addIngredient(ONIONS);
			addIngredient(MUSHROOMS);
		}
	}

	/**
	 * Removes category with given type of category.
	 * 
	 * @param type
	 *            Category.
	 */
	public void removeCategory(String type) {
		if (type.equals(CHEESE_CATEGORY)) {
			removeIngredient(CHEDDAR);
			removeIngredient(MOZZARELLA);
			removeIngredient(PEPPERJACK);
		} else if (type.equals(SAUCE_CATEGORY)) {
			removeIngredient(KETCHUP);
			removeIngredient(MUSTARD);
			removeIngredient(BARON_SAUCE);
			removeIngredient(MAYO);
		} else if (type.equals(VEGGIES_CATEGORY)) {
			removeIngredient(MUSHROOMS);
			removeIngredient(ONIONS);
			removeIngredient(TOMATO);
			removeIngredient(LETTUCE);
			removeIngredient(PICKLE);
		}
	}

	/**
	 * Adds ingredient with given.
	 * 
	 * @param type
	 *            Ingredient.
	 */
	public void addIngredient(String type) {
		MyStack<String> copyRecipe = new MyStack<String>();
		MyStack<String> temp = new MyStack<String>();

		// copies official recipe.
		while (!myOfficialRecipe.isEmpty()) {
			temp.push(myOfficialRecipe.pop());
		}
		while (!temp.isEmpty()) {
			String pop = temp.pop();
			copyRecipe.push(pop);
			myOfficialRecipe.push(pop);
		}

		// compares copied official recipe with myBurger
		// and compares copied official recipe with type.
		while (!copyRecipe.isEmpty()) {
			String recipePeek = copyRecipe.peek();
			String burgerPeek = myBurger.peek();
			if (recipePeek.equals(type)) {
				temp.push(recipePeek); // adds type
			} else if (recipePeek.equals(burgerPeek)) {
				temp.push(recipePeek);
				myBurger.pop();
			}
			copyRecipe.pop();
		}
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}
	}

	/**
	 * Removes ingredient with given.
	 * 
	 * @param type
	 *            Ingredient.
	 */
	public void removeIngredient(String type) {
		MyStack<String> temp = new MyStack<String>();
		String pop = myBurger.pop();
		while (!pop.equals(type)) {
			temp.push(pop);
			pop = myBurger.pop();
		}
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}
	}

	/**
	 * Returns string.
	 */
	public String toString() {
		MyStack<String> temp = new MyStack<String>();
		String result = "[";
		String pop = myBurger.pop();
		result += pop;
		temp.push(pop);
		while (!myBurger.isEmpty()) {
			result += ", ";
			String p = myBurger.pop();
			result += p;
			temp.push(p);
		}
		result += "]";
		while (!temp.isEmpty()) {
			myBurger.push(temp.pop());
		}
		return result;
	}

}
