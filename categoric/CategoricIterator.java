package categoric;

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
public class CategoricIterator implements Iterator<RichLetterListIterator>
{
	private ShiftList list;
	private int position;

	/**
	 * This is used to generate the lettersToCheck array. Mostly saved for error
	 * checking.
	 */
	private String substring;
	/**
	 * subset of the total letters in the alphabet: specifically the letters of
	 * the alphabet that occur in the substring.
	 */
	private RichLetterListIterator[] lettersToCheck;

	/**
	 * <p>
	 * Basic constructor that constructs a shift list iterator starting at index
	 * 0.
	 * </p>
	 * 
	 * @param list
	 * @param substring
	 */
	public CategoricIterator(ShiftList list, String substring)
	{
		this.list = list;
		this.substring = substring;
		position = 0;
		calculateLettersToCheck();
	}

	protected void calculateLettersToCheck()
	{
		lettersToCheck = new RichLetterListIterator[Alphabet.getSize()];

		for (int k = 0; k < Alphabet.getSize(); k++)
		{
			Alphabet letter = list.getKthLeastOccurringLetter(k);

			lettersToCheck[k] = new RichLetterListIterator(
					list.getLetterList(letter), substring);
		}

		makeSureFirstLetterToCheckIsContainedInSubstring();
		makeSureLastLetterToCheckIsContainedInSubstring();
	}

	/**
	 * <p>
	 * The first iterator is important for building the initial list of values
	 * to check, and so much be a positive check. Therefore, this step is
	 * important to ensure that the first iterator is a letter contained in the
	 * substring.
	 * </p>
	 */
	private void makeSureFirstLetterToCheckIsContainedInSubstring()
	{
		int k = 0;
		while (!lettersToCheck[k].isContainedInSubstring())
		{
			k++;
		}

		RichLetterListIterator temp;

		while (k > 0)
		{
			temp = lettersToCheck[k];
			lettersToCheck[k] = lettersToCheck[k - 1];
			lettersToCheck[k - 1] = temp;
			k--;
		}
	}

	/**
	 * <p>
	 * While not necessary for proper execution, ensuring that the last
	 * iterator, or the iterator that is skipped in a categorical search, is a
	 * positive check helps keep runtime down.
	 * </p>
	 */
	private void makeSureLastLetterToCheckIsContainedInSubstring()
	{
		int k = lettersToCheck.length - 1;
		while (!lettersToCheck[k].isContainedInSubstring() && k > 0)
		{
			k--;
		}

		RichLetterListIterator temp;

		while (k != 0 && k < lettersToCheck.length - 1)
		{
			temp = lettersToCheck[k];
			lettersToCheck[k] = lettersToCheck[k + 1];
			lettersToCheck[k + 1] = temp;
			k++;
		}
	}

	public boolean hasNext()
	{
		return position < lettersToCheck.length;
	}

	public RichLetterListIterator next()
	{
		RichLetterListIterator output = lettersToCheck[position];
		position++;
		return output;
	}

	public int getSize()
	{
		return lettersToCheck.length;
	}
}
