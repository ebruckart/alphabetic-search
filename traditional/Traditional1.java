package traditional;

import java.util.Iterator;
import searcher.Alphabet;
import searcher.Searcher;
import shiftlist.ShiftList;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class Traditional1 extends Traditional
{
	protected Iterator<Integer> positionIterator;
	protected int positionOffset;

	public Searcher createNew(ShiftList list, String substring)
	{
		return new Traditional1(list, substring);
	}

	public Searcher createNew(String text, String substring)
	{
		return new Traditional1(text, substring);
	}

	public Traditional1(ShiftList shiftList, String substring)
	{
		super(shiftList, substring);
	}

	public Traditional1(String text, String substring)
	{
		super(text, substring);
	}

	@Override
	public void generateSkip(Alphabet badCharacter, int offset)
	{
		if (positionIterator.hasNext())
		{
			index = positionIterator.next() + positionOffset;
		} else
		{
			index = text.length();
		}
	}

	protected void initializeIndex()
	{
		index = positionIterator.next() + positionOffset;
	}

	protected void setPositionIterator()
	{
		/*
		 * Grab the iterator of the least occurring character.
		 */
		int k = 0;
		while (!substring.contains(
				"" + shiftList.getKthLeastOccurringLetter(k).getLetter()))
		{
			k++;
		}

		positionOffset = substring.length() - 1 - substring.lastIndexOf(
				"" + shiftList.getKthLeastOccurringLetter(k).getLetter());

		positionIterator = shiftList.getKthLeastOccurringIterator(k,
				substring.length() - 1 - positionOffset);
	}
}
