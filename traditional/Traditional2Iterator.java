package traditional;

import java.util.ArrayList;
import java.util.Iterator;
import searcher.Alphabet;
import shiftlist.RichLetterListIterator;
import shiftlist.ShiftList;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Traditional2Iterator implements Iterator<Integer>
{
	private int index;
	private ShiftList list;
	private boolean cachedHasNext;
	private int cachedNextIndex;
	private boolean cachedAvailable;

	/**
	 * This is used to generate the lettersToCheck array. Mostly saved for error
	 * checking.
	 */
	private String substring;
	/**
	 * subset of the total letters in the alphabet: specifically the letters of
	 * the alphabet that occur in the substring.
	 */
	private ArrayList<RichLetterListIterator> lettersToCheck;

	/**
	 * <p>
	 * Basic constructor that constructs a shift list iterator starting at index
	 * 0.
	 * </p>
	 * 
	 * @param list
	 * @param substring
	 */
	public Traditional2Iterator(ShiftList list, String substring)
	{
		this.list = list;
		this.substring = substring;
		calculateLettersToCheck();
		index = 0;
		cachedNextIndex = -1;
		cachedHasNext = false;
		cachedAvailable = false;
	}

	public Traditional2Iterator(ShiftList list, String substring, int index)
	{
		this(list, substring);
		this.index = index;
	}

	/**
	 * <p>
	 * This calculates what letters of the alphabet occur in the substring and
	 * saves them in the array list. This is so that the algorithm doesn't use a
	 * list of characters for a letter that does not occur in the substring
	 * while checking for valid positions.
	 * </p>
	 * 
	 * @param substring
	 */
	protected void calculateLettersToCheck()
	{
		lettersToCheck = new ArrayList<RichLetterListIterator>(
				Alphabet.getSize());

		for (int k = 0; k < Alphabet.getSize(); k++)
		{
			Alphabet letter = list.getKthLeastOccurringLetter(k);

			if (substring.contains("" + letter.getLetter()))
			{
				lettersToCheck.add(new RichLetterListIterator(
						list.getLetterList(letter), substring));
			}
		}
	}

	/**
	 * <p>
	 * Checks to make sure that each iterator of the lettersToCheck still
	 * hasNext. If one of them does not, then that means all possible matches
	 * have been found, so there isn't anymore checks next.
	 * </p>
	 */
	public boolean hasNext()
	{
		if (cachedAvailable)
		{
			return cachedHasNext;
		} else
		{
			calculateCachedNext();
			return cachedHasNext;
		}
	}

	public Integer next()
	{
		index = viewNext();
		cachedAvailable = false;
		return index;
	}

	/**
	 * <p>
	 * This method allows you to view what the next index position would be
	 * without actually iterating forwards.
	 * </p>
	 * 
	 * @return
	 */
	public Integer viewNext()
	{
		if (cachedAvailable)
		{
			return cachedNextIndex;
		} else
		{
			return calculateCachedNext();
		}
	}

	private Integer calculateCachedNext()
	{
		boolean commit = true;

		if (cachedNextIndex == -1)
		{
			commit = false;
		}

		int temp = -2;

		for (RichLetterListIterator iterator : lettersToCheck)
		{
			if (iterator.hasNext())
			{
				if (commit)
				{
					temp = iterator.next(index + 1 - iterator.getOffset())
							+ iterator.getOffset();
				} else
				{
					temp = iterator.viewNext(index + 1 - iterator.getOffset())
							+ iterator.getOffset();
				}

				if (temp > cachedNextIndex)
				{
					cachedNextIndex = temp;
				}
			}

			else
			{
				cachedHasNext = false;
				cachedNextIndex = -2;
				cachedAvailable = true;
				return cachedNextIndex;
			}
		}

		cachedHasNext = true;
		cachedAvailable = true;
		return cachedNextIndex;
	}
}
