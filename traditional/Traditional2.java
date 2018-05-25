package traditional;

import searcher.Alphabet;
import searcher.Searcher;
import shiftlist.ShiftList;

public class Traditional2 extends Traditional
{
	Traditional2Iterator iterator;

	/**
	 * <p>
	 * In order to speed up testing, this constructor allows you to pass in a
	 * pre-built ShiftList rather than building it all over again.
	 * </p>
	 * 
	 * @param shiftList
	 * @param substring
	 */
	public Traditional2(ShiftList shiftList, String substring)
	{
		super(shiftList, substring);
	}

	public Traditional2(String text, String substring)
	{
		super(text, substring);
	}

	public Searcher createNew(ShiftList list, String substring)
	{
		return new Traditional2(list, substring);
	}

	public Searcher createNew(String text, String substring)
	{
		return new Traditional2(text, substring);
	}

	@Override
	public void generateSkip(Alphabet badCharacter, int offset)
	{
		if (iterator.hasNext())
		{
			index = iterator.next();
		} else
		{
			index = text.length();
		}
	}

	@Override
	protected void setPositionIterator()
	{
		iterator = new Traditional2Iterator(shiftList, substring,
				substring.length() - 1);
	}
}
