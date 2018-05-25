package shiftlist;

import java.util.Arrays;
import java.util.Iterator;
import searcher.Alphabet;

/**
 * <p>
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class ShiftList
{
	private String text;
	private LetterList[] shiftList;
	private Alphabet[] orderOfLeastOccurring;

	public ShiftList(String text)
	{
		this.text = text;
		buildShiftList();
		setOrderOfLeastOccurring();
	}

	/**
	 * <p>
	 * Due to the sheer size of DNA sequences, this method is available so that
	 * I can create letter lists from previously compiled text files.
	 * </p>
	 * 
	 * @param text
	 * @param shiftList
	 */
	public ShiftList(String text, LetterList[] shiftList)
	{
		this.text = text;
		this.shiftList = shiftList;
		setOrderOfLeastOccurring();
	}

	private void buildShiftList()
	{
		/*
		 * instantiate the shift list
		 */
		shiftList = new LetterList[Alphabet.getSize()];

		/*
		 * Add a letter list for each letter in the Alphabet, in proper
		 * ordering.
		 */
		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			shiftList[i] = new LetterList(Alphabet.getLetter(i));
		}

		/*
		 * Iterate through the text string, placing each instance of each letter
		 * in the proper list.
		 */
		for (int i = 0; i < text.length(); i++)
		{
			int position = Alphabet.valueOf(text.substring(i, i + 1))
					.getPosition();
			shiftList[position].add(i);
		}
	}

	public Iterator<Integer> getKthLeastOccurringIterator(int k)
	{
		return shiftList[orderOfLeastOccurring[k].getPosition()].iterator();
	}

	public Iterator<Integer> getKthLeastOccurringIterator(int k, int key)
	{
		return shiftList[orderOfLeastOccurring[k].getPosition()].iterator(key);
	}

	public Alphabet getKthLeastOccurringLetter(int k)
	{
		return orderOfLeastOccurring[k];
	}

	public LetterList getLetterList(Alphabet letter)
	{
		return shiftList[letter.getPosition()];
	}

	public Iterator<Integer> getPositionIterator(Alphabet letter)
	{
		return shiftList[letter.getPosition()].iterator();
	}

	public Iterator<Integer> getPositionIterator(Alphabet letter, int key)
	{
		return shiftList[letter.getPosition()].iterator(key);
	}

	public int getSize()
	{
		return shiftList.length;
	}

	/**
	 * <p>
	 * Getter method for the text this shift list was built using.
	 * </p>
	 * 
	 * @return Returns the text that was used to build this list.
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * <p>
	 * Set the order of which character occurs least in the text, followed by
	 * the next least occurring character, and so on.
	 * </p>
	 * <p>
	 * The most occurring character is the last character in the list.
	 * </p>
	 */
	private void setOrderOfLeastOccurring()
	{
		orderOfLeastOccurring = new Alphabet[Alphabet.getSize()];

		for (int i = 0; i < Alphabet.getSize(); i++)
		{
			orderOfLeastOccurring[i] = shiftList[i].getLetter();
		}

		Arrays.sort(orderOfLeastOccurring, new LeastOccurringComparator(this));
	}
}
