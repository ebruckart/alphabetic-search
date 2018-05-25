package shiftlist;

import searcher.Alphabet;

/**
 * <p>
 * Storage container for ShiftListIterator. Associates each letter with a number
 * that represents the offset from the end of the search substring, the number
 * of steps forwards you can go to properly align the next search position.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class RichLetterListIterator extends LetterListIterator
{
	private int offset;
	private boolean isContainedInSubstring;

	public static int generateOffset(Alphabet letter, String substring)
	{
		return substring.length() - 1
				- substring.lastIndexOf("" + letter.getLetter());
	}

	public static boolean isContainedInSubstring(Alphabet letter,
			String substring)
	{
		return substring.contains("" + letter.getLetter());
	}

	public RichLetterListIterator(LetterList list, String substring)
	{
		super(list);
		setSubstringData(substring);
	}

	public RichLetterListIterator(LetterList list, String substring, int key)
	{
		super(list, key);
		setSubstringData(substring);
	}

	public int getOffset()
	{
		return offset;
	}

	private void setSubstringData(String substring)
	{
		offset = generateOffset(list.getLetter(), substring);
		isContainedInSubstring = isContainedInSubstring(list.getLetter(),
				substring);
	}

	public boolean isContainedInSubstring()
	{
		return isContainedInSubstring;
	}

	public String toString()
	{
		return list.toString();
	}
}
