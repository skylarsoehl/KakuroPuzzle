import java.util.ArrayList;
import java.util.Collections;

/**
 * This class generates all the possible numbers between 1 and 9 that could be
 * added to produce the sum of a particular target number.
 * 
 * @author skyso, matthewri
 * @version 1.0
 * @since 11-16-2017
 *
 */
public class SumGenerator {
	private ArrayList<Integer> range;
	private ArrayList<Integer> partial;

	/**
	 * This method constructs this SumGenerator, intializing an ArrayList of numbers
	 * 1-9, as well as an ArrayList of partial sums
	 */
	public SumGenerator() {
		range = new ArrayList<Integer>();
		partial = new ArrayList<Integer>();

		for (int i = 1; i < 10; i++) {
			range.add(i); // # 1-9 can be only values
		}
	}

	/**
	 * This method returns all the possible sets of numbers between 1 and 9 that
	 * could add up to a given target number based on the number of open spaces for
	 * that target value
	 * 
	 * @param target
	 *            - an int representing the target sum
	 * @param groupLength
	 *            - an int representing the number of open tiles for this target
	 *            length
	 * 
	 * @return returns a 2-d ArrayList representing the possible sums for this
	 *         target value
	 */
	public ArrayList<ArrayList<Integer>> genSums(int target, int groupLength) {
		return findNums(range, target, groupLength, partial);
	}

	/**
	 * This method returns all the possible sets of numbers between 1 and 9 that
	 * could add up to a given target number based on the allowed number of operands
	 * 
	 * @param numbers
	 *            - an array of integers
	 * @param target
	 *            - an int representing the sum that sets of returned numbers will
	 *            add up to
	 * @param groupLength
	 *            - an int representing the number of operands allowed to perform
	 *            the sum
	 * @param partial
	 *            - an array representing a partial sum
	 * 
	 * @return - returns a 2-d ArrayList of ints that represent the different sets
	 *         of numbers that can add up to the given target value
	 */
	private ArrayList<ArrayList<Integer>> findNums(ArrayList<Integer> numbers, int target, int groupLength,
			ArrayList<Integer> partial) {
		ArrayList<ArrayList<Integer>> resultList = new ArrayList<ArrayList<Integer>>();

		subset_sum(numbers, target, groupLength, partial, resultList); //use recursion to build sets
		return resultList;
	}

	/**
	 * This method builds the partial sum of a target value based on the number of
	 * open spaces
	 * 
	 * @param numbers
	 *            - an ArrayList of ints from 1 to 9
	 * @param target
	 *            - an int representing the sum that the numbers in resultList will
	 *            add up to
	 * @param groupLength
	 *            - an int representing the number of open spaces
	 * @param partial
	 *            - an ArrayList of ints representing the partial sum of the target
	 *            value
	 * @param resultList
	 *            - an 2-d ArrayList of ints representing a set of numbers that add
	 *            up to the targey value
	 */
	private void subset_sum(ArrayList<Integer> numbers, int target, int groupLength, ArrayList<Integer> partial,
			ArrayList<ArrayList<Integer>> resultList) {
		int s = sum(partial);
		
		// check if the partial sum is equal to target and partial has right length
		if (s == target && partial.size() == groupLength)
			resultList.add(partial);

		for (int i = 0; i < numbers.size(); i++) {
			int n = numbers.get(i);
			ArrayList<Integer> remaining = new ArrayList<Integer>(numbers.subList(i + 1, numbers.size()));
			@SuppressWarnings("unchecked")
			ArrayList<Integer> tmpPartial = (ArrayList<Integer>) partial.clone();
			tmpPartial.add(n);

			subset_sum(remaining, target, groupLength, tmpPartial, resultList);
		}
	}

	/**
	 * This method calculates the sum of an array list of integers
	 * 
	 * @param partial
	 *            - an ArrayList of ints that represent a partial sum
	 * 
	 * @return returns the sum of an ArrayList of integers
	 */
	private int sum(ArrayList<Integer> partial) {
		int sum = 0;

		for (int i = 0; i < partial.size(); i++) {
			sum += partial.get(i); //sums up numbers
		}

		return sum;
	}

	/**
	 * This method takes finds union of all the sublists in a 2-d ArrayList
	 * 
	 * @param listOfLists
	 *            - a 2-d ArrayList representing all the possible values that could
	 *            add up to a target value
	 * 
	 * @return returns a 1-d ArrayList representing the values that can add up to a
	 *         target value without repeats
	 */
	public ArrayList<Integer> coalesce(ArrayList<ArrayList<Integer>> listOfLists) {
		ArrayList<Integer> resultList = new ArrayList<Integer>();

		for (ArrayList<Integer> list : listOfLists) {
			for (int item : list) {
				if (!resultList.contains(item))
					resultList.add(item);
			}
		}
		Collections.sort(resultList);
		return resultList;
	}
}