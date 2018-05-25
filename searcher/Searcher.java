package searcher;

import java.util.*;
import shiftlist.ShiftList;

/**
 * <p>
 * This is the foundation of all the searching algorithms, and handles some
 * basic error checking in terms of making sure that valid text and substrings
 * are passed in, as well as giving implementational tools for the inheriting
 * classes to use.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Searcher
{
	public static final String dummyText = "ACGTACGT";
	public static final String dummySubstring = "ACGT";
	protected String text;
	protected String substring;
	protected int comparisons;
	protected int index;

	/**
	 * <p>
	 * <p>
	 * This protected constructor sets the text that will be searched through
	 * and leaves the substring blank.
	 * </p>
	 * <p>
	 * This also incorporates some basic error handling. A
	 * <b>NullPointerException</b> will be thrown if the value of text that is
	 * passed in is equal to null.
	 * </p>
	 * 
	 * @param text
	 *            - This string is the source text that the algorithm will
	 *            search through.
	 * @throws NullPointerException
	 *             - Thrown if the text passed in is equal to null or the empty
	 *             string.
	 */
	protected Searcher(String text) throws NullPointerException
	{
		// Exception handling: this is to prevent null texts from ever being
		// set.
		if (text == null || text.length() < 1)
		{
			throw new NullPointerException(this.getClass().getName()
					+ " :: Attempted to set null as the text in constructor method.");
		}

		this.text = text;
		comparisons = -1;
	}

	/**
	 * <p>
	 * This is the primary constructor for the class. This sets the text that
	 * will be searched through, as well as the substring that is being sought
	 * in the text.
	 * </p>
	 * <p>
	 * This throws exceptions under three conditions:
	 * </p>
	 * <ul>
	 * <li><b>NullPointerException</b> is thrown if the text passed in is equal
	 * to null.</li>
	 * <li><b>NullPointerException</b> is thrown if the substring passed in is
	 * equal to null.</li>
	 * <li><b>IndexOutOfBoundsException</b> is thrown if the substring has a
	 * length() longer than the text. The substring should be shorter than the
	 * text, as a substring longer than the text can never have a match in the
	 * text.</li>
	 * </ul>
	 * 
	 * @param text
	 *            - This string is the source text that the algorithm will
	 *            search through.
	 * @param substring
	 *            - This is the substring that is being sought in the text.
	 * @throws NullPointerException
	 *             - Thrown under 2 conditions: if the text or if the substring
	 *             passed in is equal to null or an empty string.
	 * @throws IndexOutOfBoundsException
	 *             - Thrown if the substring has a length() that is longer than
	 *             the text.
	 */
	public Searcher(String text, String substring)
			throws NullPointerException, IndexOutOfBoundsException
	{
		this(text);

		// Exception handling: this is to prevent null substrings from ever
		// being set.
		if (substring == null || substring.length() < 1)
		{
			throw new NullPointerException(this.getClass().getName()
					+ " :: Attempted to set null as the substring in constructor method.");
		} else if (substring.length() > text.length())
		{ // Exception handling: this is to ensure that the substring is not
			// longer than
			// the text, in which case the substring will never be found in the
			// text.
			throw new IndexOutOfBoundsException(this.getClass().getName()
					+ " :: Attempted to set a substring that was longer than the text.");
		}

		this.substring = substring;
	}

	public Searcher createNew(ShiftList list, String substring)
	{
		return createNew(list.getText(), substring);
	}

	public Searcher createNew(String text, String substring)
	{
		return new Searcher(text, substring);
	}

	protected void generateSkip(Alphabet lastChar, int offset)
	{
		index++;
	}

	/**
	 * <p>
	 * Getter method for the number of character comparisons this algorithm has
	 * to go through before all matches of the substring in the source text are
	 * found.
	 * </p>
	 * <p>
	 * If the search method has not been called yet, the number of comparisons
	 * output is equal to -1.
	 * </p>
	 * 
	 * @return Returns the number of character comparisons the search method
	 *         needed to make to find find all instances of the substring in the
	 *         source text.
	 */
	public int getComparisons()
	{
		return comparisons;
	}

	/**
	 * <p>
	 * Getter method for the substring, who is sought for in the source text by
	 * an instance of this algorithm.
	 * </p>
	 * 
	 * @return Returns the substring that we are searching for in the source
	 *         text.
	 */
	public String getSubstring()
	{
		return substring;
	}

	/**
	 * <p>
	 * Getter method for the text this instance of the algorithm searches
	 * through.
	 * </p>
	 * 
	 * @return Returns the text that is searched through by the algorithm.
	 */
	public String getText()
	{
		return text;
	}

	protected void initializeIndex()
	{
		index = substring.length() - 1;
	}

	/**
	 * <p>
	 * This is the main search method that will be used or overwritten by all
	 * the other algorithms.
	 * </p>
	 * <p>
	 * This one will skip forwards by 1 character each time. Classes that
	 * inherit from this will overwrite this method with larger skips
	 * incorporated.
	 * </p>
	 * 
	 * @return Returns a list of integer indices that represent all the places
	 *         that the substring exists in the text.
	 */
	public ArrayList<Integer> search()
	{
		ArrayList<Integer> results = new ArrayList<Integer>();
		comparisons = 0;
		initializeIndex();
		int offset = -1;

		while (index < text.length())
		{
			/*
			 * verify if there's a match at this index
			 */
			offset = verifyMatch(results);

			/*
			 * This resets the searcher so it doesn't break if a match was
			 * previously found.
			 */
			if (offset == substring.length())
			{
				offset--;
			}

			/*
			 * increment index to check for the next possible match of the
			 * substring in the text. In a naive implementation, it only
			 * increments by 1 each time. Classes that inherit from this will
			 * overwrite this method with more intelligent skips, that will skip
			 * more letters in the text.
			 */
			generateSkip(Alphabet.valueOf(text.substring(index, index + 1)),
					offset);
		}

		return results;
	}

	@Override
	public String toString()
	{
		return this.getClass().getName() + "\nText: " + text + "\nSubstring: "
				+ substring;
	}

	/**
	 * <p>
	 * Internal method that goes character by character through the string in
	 * order to verify if an index actually contains a valid instance of the
	 * substring. If it does, it is added to the list that is passed in as a
	 * paramter. If it doesn't, then it exits without modifying the list.
	 * </p>
	 * 
	 * @param index
	 *            position index in the source text (from the right side of a
	 *            potential match of the substring) that must be checked to
	 *            verify if there is a match to the substring here.
	 * @param results
	 *            - Results list that will have a positional index value added
	 *            if there is a valid match to the substring found here. Please
	 *            note that the positional index value added will be from the
	 *            LEFT side of the match, so will correspond to the parameter
	 *            passed <b>index - substring.length() + 1</b>.
	 * @return Returns the integer offset value, corresponding to how far into
	 *         the string the checker got before exiting. This is important for
	 *         the Boyer Moore implementation.
	 */
	protected int verifyMatch(List<Integer> results)
	{
		int offset = 0;

		while (offset < substring.length()
				&& substring.charAt(substring.length() - 1 - offset) == text
						.charAt(index - offset))
		{
			comparisons++;
			offset++;
		}

		if (offset == substring.length())
		{
			results.add(index - offset + 1);
		}

		return offset;
	}
}
