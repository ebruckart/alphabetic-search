package shiftlist;

import java.util.*;
import searcher.Alphabet;

/**
 * <p>
 * Wrapper for an int array that keeps track of the logical size of the list as
 * well as the Alphabet letter associated with the list.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class LetterList implements Iterable<Integer>
{
	/**
	 * Arbitrarily large number that can allow a letter list to store all of the
	 * instances of indices for a letter in the alphabet without overflowing.
	 */
	public static final int largeNum = 50000000;
	/**
	 * Smaller number used for testing instances.
	 */
	public static final int defaultNum = 10000;

	/**
	 * <p>
	 * This method creates a letter list from a scanner containing a list of
	 * integers (presumably a scanner reading from an input file).
	 * </p>
	 * 
	 * @param letter
	 * @param list
	 * @return
	 */
	public static LetterList createLetterList(Alphabet letter, Scanner list)
	{
		LetterList output = new LetterList(letter, largeNum);

		while (list.hasNextInt())
		{
			output.add(list.nextInt());
		}

		return output;
	}

	/**
	 * <p>
	 * This represents the letter associated with this LetterList.
	 * </p>
	 */
	private Alphabet letter;
	private int[] list;

	private int size;

	/**
	 * <p>
	 * Default constructor, list is instantiated with the default size.
	 * </p>
	 * 
	 * @param letter
	 */
	public LetterList(Alphabet letter)
	{
		this(letter, defaultNum);
	}

	/**
	 * <p>
	 * This constructor creates the list with the specified size. Please be
	 * aware that this doesn't have error checking, so if the size chosen to
	 * instantiate is too small, the program will crash.
	 * </p>
	 * 
	 * @param letter
	 * @param size
	 */
	public LetterList(Alphabet letter, int size)
	{
		this.letter = letter;
		list = new int[size];
		this.size = 0;
	}

	protected void add(Integer arg0) throws IndexOutOfBoundsException
	{
		if (size < list.length)
		{
			list[size] = arg0.intValue();
			size++;
		} else
		{
			throw new IndexOutOfBoundsException(this.getClass()
					+ " :: Letter List does not have enough space to store all values.");
		}
	}

	public boolean contains(int arg0)
	{
		return indexOf(arg0) >= 0;
	}

	public boolean containsAll(Collection<?> arg0)
	{
		for (Object i : arg0)
		{
			/*
			 * If the object is not an instance of an Integer, OR if the letter
			 * list does not contain the object, return false because one of the
			 * elements was not contained.
			 */
			if (!i.getClass().equals(Integer.class) || !contains((Integer) i))
			{
				return false;
			}
		}

		return true;
	}

	public Integer get(int index)
	{
		return list[index];
	}

	/**
	 * <p>
	 * Getter method for the letter associated with this list.
	 * </p>
	 * 
	 * @return Returns the letter in the alphabet associated with this list.
	 */
	public Alphabet getLetter()
	{
		return letter;
	}

	/**
	 * <p>
	 * Wrapper for Arrays.binarySearch.
	 * </p>
	 * 
	 * @param key
	 *            - index in the source text being sought
	 * @return Returns exact output of Arrays.binarySearch. Please refer to the
	 *         api documentation for further information.
	 */
	public int indexOf(int key)
	{
		return indexOf(0, key);
	}

	public int indexOf(int start, int key)
	{
		return Arrays.binarySearch(list, start, size, key);
	}

	public boolean isEmpty()
	{
		return size <= 0;
	}

	public Iterator<Integer> iterator()
	{
		return new LetterListIterator(this);
	}

	public Iterator<Integer> iterator(int key)
	{
		return new LetterListIterator(this, key);
	}

	/**
	 * <p>
	 * Returns the static length of the array used to store indices for this
	 * letter list.
	 * </p>
	 * 
	 * @return Returns list.length, where list is the array used to store
	 *         indices for this letter list.
	 */
	public int length()
	{
		return list.length;
	}

	/**
	 * <p>
	 * Returns the logical number of elements stored in the letter list.
	 * </p>
	 * 
	 * @return Returns size
	 */
	public int size()
	{
		return size;
	}

	public String toString()
	{
		String output = "[";
		for (int i = 0; i < size(); i++)
		{
			output += list[i] + ", ";
		}

		output += "]";

		return output;
	}
}
