package horspools;

import searcher.Alphabet;
import searcher.ShiftTable;

/**
 * <p>
 * This builds the bad character shift table that is used by the Horspools and
 * Boyer Moore Algorithms.
 * </p>
 * 
 * <p>
 * Indices in this implementation of ShiftTable correspond to the total ordering
 * of letters in the alphabet defined in the enum Alphabet. The method
 * getShift(int) will return an offset based on the Alphabet character in the
 * ith position in the alphabet.
 * </p>
 * <p>
 * Since the shifts stored in this table logically correspond to alphabet
 * letters, a secondary getShift method has been defined that allows you to
 * query directly based on the Alphabet letter.
 * </p>
 * 
 * @author Emily Bruckart
 *
 */
public class MismatchShiftTable extends ShiftTable
{

	/**
	 * <p>
	 * This initializes the mismatch shift table and builds the shift table
	 * based on the substring.
	 * </p>
	 * 
	 * @param substring
	 *            - substring the table will be built using.
	 */
	public MismatchShiftTable(String substring)
	{
		super(substring);
	}

	/**
	 * <p>
	 * This implementation of this method is called by the constructor in the
	 * abstract class to build a bad character mismatch shift table for use by
	 * the Horspools and Boyer Moore Algorithm.
	 * </p>
	 * 
	 * <p>
	 * Indices in the shift table correspond to the total ordering of letters in
	 * the Alphabet, as defined in the enum Alphabet.
	 * </p>
	 */
	protected void buildTable()
	{
		shiftTable = new int[Alphabet.getSize()];
		int lastIndexOf = -1;
		for (int i = 0; i < shiftTable.length; i++)
		{
			/*
			 * we check from the last index starting from the second to last
			 * letter in the substring because we would have skipped the last
			 * character anyways.
			 */
			lastIndexOf = substring.lastIndexOf(
					Alphabet.getLetter(i).getLetter(), substring.length() - 2);

			shiftTable[i] = substring.length() - 1 - lastIndexOf;
		}
	}

	/**
	 * <p>
	 * Method that outputs the correct amount of characters that can be shifted
	 * over by a pattern matching algorithm while positioning for another check
	 * for a potential match of the substring in the source text.
	 * </p>
	 * 
	 * <p>
	 * Because bad character mismatch shift tables have a logical connection to
	 * alphabet characters outside of their positions in the total ordering of
	 * alphabet letters, this method has been written to follow the logic. The
	 * built-in shift table getShift method will also return the correct shift,
	 * based on the position of the alphabet letter in the total ordering of
	 * letters in the alphabet.
	 * </p>
	 * 
	 * @param letter
	 *            - This is the rightmost letter of the previous index, which
	 *            determines how far over we can shift before starting another
	 *            check to see if the substring is present in the source text.
	 * @return Returns the number of characters to the right we can shift, given
	 *         the letter that was passed in.
	 */
	public int getShift(Alphabet letter)
	{
		return getShift(letter.getPosition());
	}

}
