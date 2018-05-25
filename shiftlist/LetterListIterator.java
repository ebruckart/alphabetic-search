package shiftlist;

import java.util.Iterator;
import searcher.Alphabet;

/**
 * <p>
 * This iterator is used by the Traditional algorithm to iterate through the
 * LetterList for the least occurring character to find all matches of a
 * substring in a source text. In this context, it uses the letter list's binary
 * search in indexOf to find the initial place to check, and iterates through
 * the rest of the list using the next() method.
 * </p>
 * <p>
 * This iterator is also extended by LetterOffsetIterator and used within the
 * context of the ShiftListIterator class to iterate for the Traditional and
 * Categorical algorithms. Within this context, this uses the binary search to
 * find indices based on the key passed into the next method, next(int), in
 * order to use the binary search to skip to the next valid instance.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class LetterListIterator implements Iterator<Integer>
{
	private int position;
	protected LetterList list;
	private int cachedPosition;
	private int cachedKey;

	public LetterListIterator(LetterList list)
	{
		this.list = list;
		position = 0;
		cachedPosition = position;
		cachedKey = -1;
	}

	public LetterListIterator(LetterList list, int key)
	{
		this(list);
		setPosition(key);
	}

	public Alphabet getLetter()
	{
		return list.getLetter();
	}

	public boolean hasNext()
	{
		return position < list.size();
	}

	public boolean hasNext(int key)
	{
		return viewPosition(key) < list.size();
	}

	public Integer next() throws IndexOutOfBoundsException
	{
		int output = viewNext();
		position++;
		return output;
	}

	public Integer viewNext()
	{
		return list.get(position);
	}

	public Integer next(int key)
	{
		setPosition(key);
		return list.get(position);
	}

	public Integer viewNext(int key)
	{
		return list.get(viewPosition(key));
	}

	/**
	 * <p>
	 * Returns the element at the specified position in the array.
	 * </p>
	 * 
	 * @param position
	 * @return
	 */
	public Integer nextByPosition(int position)
	{
		return list.get(position);
	}

	protected void setPosition(int key)
	{
		position = viewPosition(key);
	}

	/**
	 * <p>
	 * Returns the next position without changing the current position.
	 * </p>
	 * 
	 * @param key
	 * @return Returns what the next found key would be.
	 */
	public int viewPosition(int key)
	{
		if (cachedKey == key)
		{
			return cachedPosition;
		}

		else
		{
			cachedKey = key;
			cachedPosition = list.indexOf(position, key);

			if (cachedPosition < 0)
			{
				cachedPosition++;
			}

			cachedPosition = Math.abs(cachedPosition);

			return cachedPosition;
		}
	}

	public int size()
	{
		return list.size();
	}
}
